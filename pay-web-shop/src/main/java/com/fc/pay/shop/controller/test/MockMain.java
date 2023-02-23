package com.fc.pay.shop.controller.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.utils.Return;

public class MockMain {

	public static void main(String[] args) throws Exception {
		notifyMistakeHandled();
	}
	
	public static void notifyMistakeHandled() throws Exception {	
		Map bizData = new HashMap();
		bizData.put("notifyId", "1000001");
		bizData.put("billType", "pay");
		bizData.put("orderNo", "2017022810001");
		bizData.put("mistakeType", "channel_miss");  
		bizData.put("suggestion", "查询订单更正状态与金额");	
		MerchantApp merchantApp = new MerchantApp();
		merchantApp.setPriKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALK5gcD0ehR4Zh4RGTpNZq3AtVPblXYcOXJxPOn1MzUiEJw3yvhgesQ0tmNzBRq74maJpfrm400SL53uRlP3/nJfY3OW2pfyjzoy57bG5tLTgaiwxtLuyaB07ghJzls4mkxUFzUztUHIBCff0eiKUmFXTCVUJGNsC/SOcqlNz6M5AgMBAAECgYEAmetLRNsHnEOIZpKBMIXiTPdu8lZk5MAv9VByjma+gB7jQTaHldq5P+rJvOIc3kY7F+WpzWg1D3X/Djtb1Ar61SKpamVDqOD/LavwbwkbCR+E7eamzGSu/GgWzTQ1oLzGxakfvfBt5nAcZKqHe8LGd8umCVZJGDKoVulTpSLHVAECQQDbIMcN5955E0pDLkvHdWY7MVdF9xU9z+AJOCLzKWbx551hUpEBbA46ta2ZDX5mSjI/vu/eEIWD0shnJLFpHnWBAkEA0MxM/d6SWwkgV0rOQ5CHcHegi2pgcaoYWFsP+V0b2lnmQLCWtyyEpVi5jfIJuoDLgoy8uBxtlNYeetmNfiM5uQJAcnQ5ZEsDCeyIcnShAiqQ3kQUWKgJAUMtusMGhknOynV235mXwc1l1UaFyRaiOd1xR5h8g1nP0x3qrO0eeVB+gQJBAMbAssk2BLsDhPWTD6Tg/wvf08LiD8wizenNRPdp2gmPac5KSi0zA1Ehk/+6VehikVZEAaB/7w+ugnUOgdGAVoECQFRg8tSJU7Wb8UD1h1IC+jnYT8uLGTbqs3JZmXMifrqkkZm1OGx1uyp6I6aMrIbKjKP9FPAB48547D9SGkTaDeA=");
		String postDataStr = buildNotifyData(merchantApp, bizData);
		System.out.println("postDataStr=>"+postDataStr);
    	CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		try {
			HttpPost httpPost = new HttpPost("http://localhost:8081/pay-shop/mch/notify/bill/mistake");
			/***
	        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
	        String billType = mistake.getBillType();
	        nameValuePairList.add(new BasicNameValuePair("billType", billType));
	        if(BillTypeEnum.pay.name().equals(billType)){
	        	nameValuePairList.add(new BasicNameValuePair("orderNo", mistake.getPayOrderNo())); 
	        }else if(BillTypeEnum.refund.name().equals(billType)){
	        	nameValuePairList.add(new BasicNameValuePair("orderNo", mistake.getRefundOrderNo())); 
	        }	        
	        nameValuePairList.add(new BasicNameValuePair("mistakeId", mistake.getId().toString()));  
	        nameValuePairList.add(new BasicNameValuePair("mistakeType", mistake.getMistakeType()));  
	        nameValuePairList.add(new BasicNameValuePair("suggestion", "查询订单更正状态与金额"));   
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList)); 
	        */
			/** json格式 */
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");	        
			StringEntity entity = new StringEntity(postDataStr, PayConstants.UTF8_ENCODING);
			entity.setContentType("text/json");
			httpPost.setEntity(entity);
	        httpResponse = httpclient.execute(httpPost);
	        StatusLine statusLine = httpResponse.getStatusLine();
	        System.out.println("StatusLine.ReasonPhrase>>>"+statusLine.getReasonPhrase());
			if(statusLine.getStatusCode() != 200){
				throw new Exception("发送对账差错已处理通知失败，HTTP状态码["+statusLine.getStatusCode()+"]");
			}
			String responseData = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("responseData=>"+responseData);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("发送对账差错已处理通知异常["+e.getMessage()+"]");
		}finally{
			IOUtils.closeQuietly(httpResponse);
			IOUtils.closeQuietly(httpclient);
		}
	}
	
	private static String buildNotifyData(MerchantApp merchantApp, Map bizData) throws JsonProcessingException{
		Return notifyData = new Return();
		notifyData.setCode(CodeEnum._10000.getName());
		notifyData.setMsg(CodeEnum._10000.getValue());
		notifyData.setResult(bizData , merchantApp);
		return new ObjectMapper().writeValueAsString(notifyData).toString();
	}

}
