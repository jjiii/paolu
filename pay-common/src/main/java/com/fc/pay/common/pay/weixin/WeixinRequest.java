package com.fc.pay.common.pay.weixin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tencent.common.Configure;
import com.tencent.common.Util;
import com.tencent.protocol.pay_protocol.ScanPayResData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tencent.protocol.refund_protocol.RefundResData;
import com.tencent.protocol.refund_query_protocol.RefundQueryResData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * 不想重复写渠道的sdk，实在微信SDK写得太烂，多商户下不可能公用一个Configure类的静态属性
 * 可以霸道和垄断，SDK也可以针对一个商户，但是特么写得像一坨屎的样子，实在浪费程序员生命
 * 该类直接复制微信SDK的类(com.tencent.common.HttpsRequest)来修改
 * 带有支付退款查询功能
 * @author XDou
 */
public class WeixinRequest {
	
	private static Logger log = LoggerFactory.getLogger(WeixinRequest.class);

	//=====腾讯的httpclient===========================================
    //连接超时时间，默认10秒
    private int socketTimeout = 10000;
    //传输超时时间，默认30秒
    private int connectTimeout = 30000;
    //请求器的配置
    private RequestConfig requestConfig;
    //HTTP请求器
    private CloseableHttpClient httpClient;
    
    private String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //=============================================================
    
    
    //支付
    private String appid;
    private String mchId;
    private String key;
    
    
    public WeixinRequest(String appid, String mchId, String key, String certLocalPath, String certPassword) throws Exception {
    	this.appid = appid;
    	this.mchId = mchId;
    	this.key = key;
    	KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certLocalPath));//加载本地的证书进行https加密传输
        
        try {
            keyStore.load(instream, certPassword.toCharArray());//设置证书密码
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, certPassword.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();
    }


    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param xmlObj 要提交的XML数据对象
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */

    public String sendPost(String url, Object xmlObj){
    	
    	HttpPost httpPost = new HttpPost(url);
    	try {
       
		    //解决XStream对出现双下划线的bug
		    XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		
		    //将要提交给API的数据对象转换成XML格式数据Post给API
		    String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
		
		    Util.log("API，POST过去的数据是：");
		    Util.log(postDataXML);
		
		    //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		    StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
		    httpPost.addHeader("Content-Type", "text/xml");
		    httpPost.setEntity(postEntity);
		
		    //设置请求器的配置
		    httpPost.setConfig(requestConfig);
		
		    Util.log("executing request" + httpPost.getRequestLine());
		
		   
	        HttpResponse response = httpClient.execute(httpPost);
	
	        HttpEntity entity = response.getEntity();
	
//	        System.out.println(EntityUtils.toString(entity, "UTF-8"));
	        return EntityUtils.toString(entity, "UTF-8");
	        
	        
        } catch (Exception e) {
            e.printStackTrace();
            httpPost.abort();
            return null;
        }
    	
        
    }


    
    /**
     * 支付
     */
    public ScanPayResData pay(PayReqData payReqData){
    	try {
    		
    		//签名
            payReqData.setAppid(appid);
            payReqData.setMch_id(mchId);
            String sign = Signature.getSign(payReqData.toMap(), key);
            payReqData.setSign(sign);
            
            
			String  responseString = this.sendPost(payUrl, payReqData);
			log.debug(responseString);
			System.err.println(responseString);
			//验证签名
			ScanPayResData result = (ScanPayResData) Util.getObjectFromXML(responseString, ScanPayResData.class);
			if(!result.getReturn_code().equals("FAIL") && StringUtils.isNotBlank(result.getSign())){
	    		if (Signature.checkIsSignValidFromResponseString(responseString, key)) {
	    			return result;
	            }
			}
		
    	} catch (Exception e) {
            e.printStackTrace();
        }
    	return null;
    }
    
    
    /**
     * 支付查询
     */
    public ScanPayQueryResData payQuery(PayQueryReqData payQueryReqData){
    	try {
    		//签名
            payQueryReqData.setAppid(appid);
            payQueryReqData.setMch_id(mchId);
            String sign = Signature.getSign(payQueryReqData.toMap(), key);
            payQueryReqData.setSign(sign);
            
	    	String  responseString = this.sendPost(Configure.PAY_QUERY_API, payQueryReqData);
	    	log.debug(responseString);
	    	System.err.println(responseString);
	    	ScanPayQueryResData result = (ScanPayQueryResData) Util.getObjectFromXML(responseString, ScanPayQueryResData.class);

	    	//验证签名
			if(StringUtils.isNotBlank(result.getSign())){
	    		if (Signature.checkIsSignValidFromResponseString(responseString, key)) {
	    			return result;
	            }
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
    }
    
    
    /**
     * 退款
     */
    public RefundResData refund(RefundReqData refundReqData){
    	try {
    		//签名
            refundReqData.setAppid(appid);
            refundReqData.setMch_id(mchId);
            String sign = Signature.getSign(refundReqData.toMap(), key);
            refundReqData.setSign(sign);
            
			String  responseString = this.sendPost(Configure.REFUND_API, refundReqData);
			log.debug(responseString);
			System.err.println(responseString);
			RefundResData result = (RefundResData) Util.getObjectFromXML(responseString, RefundResData.class);
			
			//验证签名
			if(StringUtils.isNotBlank(result.getSign())){
	    		if (Signature.checkIsSignValidFromResponseString(responseString, key)) {
	    			return result;
	            }
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
    }
    
    /**
     * 退款查询
     */
    public RefundQueryResData refundQuery(RefundQueryReqData refundQueryReqData){
    	try {
    		
    		//签名
            refundQueryReqData.setAppid(appid);
            refundQueryReqData.setMch_id(mchId);
            String sign = Signature.getSign(refundQueryReqData.toMap(), key);
            refundQueryReqData.setSign(sign);
            
	    	String  responseString = this.sendPost(Configure.REFUND_QUERY_API, refundQueryReqData);
	    	log.info(responseString);
	    	System.err.println(responseString);
	    	RefundQueryResData result = (RefundQueryResData) Util.getObjectFromXML(responseString, RefundQueryResData.class);
	    	
	    	//验证签名
	    	if( StringUtils.isNotBlank(result.getSign())){
	    		if (Signature.checkIsSignValidFromResponseString(responseString, key)) {
	    			return result;
	            }
			}
    	} catch (Exception e) {
	        e.printStackTrace();
	    }
    	return null;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 设置连接超时时间
     * @param socketTimeout 连接时长，默认10秒
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     * @param connectTimeout 传输时长，默认30秒
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private void resetRequestConfig(){
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * 允许商户自己做更高级更复杂的请求器配置
     *
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }
}
