package com.fc.pay.common.pay.alipay;

import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fc.pay.common.core.utils.RSA;

/**
 * 调用淘宝接口类
 * https://openapi.alipaydev.com/gateway.do
 * https://hc.apache.org/httpcomponents-client-4.5.x/tutorial/html/fluent.html
 * @author XDou
 */
public class AliRequest {
	
	private static Logger logger = LoggerFactory.getLogger(AliRequest.class);
	private AliConfig aliConfig;
	private AliParam aliParam;
	
	public AliRequest(){
	}
	
	public AliRequest(String app_id, String privateKey,String publicKey){
		this.aliConfig = new AliConfig(app_id, privateKey, publicKey);
		this.aliParam = new AliParam(aliConfig);
	}
	
	public AliParam getAliprarm(){
		return this.aliParam;
	} 
	
	
	
	
	
	private JsonObject post(String url, String biz_content)
			throws Exception {
		//连接3s超时,read5s超时
		
		Form form = Form.form().add("biz_content", biz_content);
//		
//		ContentType.APPLICATION_FORM_URLENCODED.withCharset(Consts.UTF_8)
//		String parm = "biz_content=" + biz_content;
		String respData = Request.Post(url)
				.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
				.connectTimeout(5000)
		        .socketTimeout(5000)
    			.bodyForm(form.build())
    			.execute()
    			.returnContent()
    			.asString();
		
		JsonReader reader = Json.createReader(new StringReader(respData));
		return reader.readObject();
	}
	
	private JsonObject post(Map<String, String> param){
		try{
			
			String url = aliParam.buildUrl(param);
			
			String biz_content  = aliParam.build(param).get("biz_content");
			
			return post( url ,biz_content);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("http请求网络异常：" + e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * @说明：交易关闭接口：method=alipay.trade.close，异常返回null
	 */
	public JsonObject close(Map<String, String> param){
		param.put("method", this.aliConfig.close_method);
		return  post(param);
	}
	
	
	
	/**
	 * @说明：支付查询接口：method=alipay.trade.query，异常返回null
	 */
	public JsonObject payQuery(Map<String, String> param){
		param.put("method", this.aliConfig.query_method);
		JsonObject response = post(param);
		if(response == null){
			return null;
		}
		
		//验签
		String sign = response.getString("sign");
		if(StringUtils.isNotBlank(sign)){
			JsonObject json = response.getJsonObject(AliResponse.alipay_trade_query_response.name());
			Boolean bool = verifySign(json,sign);
			if(bool==false){
				logger.error("支付宝查询,验签：失败");
				throw new RuntimeException("支付宝查询接口，返回验证签名错误");
			}
			logger.info("支付宝查询，验签:sign=true");
		}
		
		
		return  response;
	}
	
	
	/**
	 * @说明：退款接口：
	 */
	public JsonObject refund(Map<String, String> param){
		param.put("method", this.aliConfig.refund_method);
		return  post(param);
	}
	
	/**
	 * @说明：退款查询接口：
	 */
	public JsonObject refundQuery(Map<String, String> param){
		param.put("method", this.aliConfig.refund_query_method);
		return  post(param);
	}
	
	public JsonObject bill(Map<String, String> param){
		param.put("method", this.aliConfig.bill_method);
		return  post(param);
	}
	
	public Boolean verifySign(JsonObject json, String sign){
		if(json == null || sign == null) {
			return false;
		}
		return RSA.verify(json.toString(), this.aliConfig.publicKey, sign);
	}
	
	public static void main(String[] args) throws Exception {
		
//		Map<String, String>   params = new HashMap<String, String>();
//		params.put("method", AliConfig.query_method);
//		params.put("out_trade_no", "811425303086895104");
//		AliRequest request = new AliRequest();
//		JsonObject o = request.payQuery(params);
		
		
		
//		List<String> list = new ArrayList<String>(jo.keySet());
//		Collections.sort(list);
//		StringBuffer str = new StringBuffer();
//		int i = 0;
//		str.append("{");
//		for (String key : list) {
//			if (!"sign".equals(key)) {
//				
//				str.append("\"").append(key).append("\"")
//				.append(":")
//				.append("\"").append( jo.getString(key) ).append("\"");
//				i++;
//				if (i < list.size()) {
//					str.append(",");
//				}
//			}
//
//		}
//		str.append("}");

		
		
		
		
//		AlipayClient alipayClient = new DefaultAlipayClient(
//				"https://openapi.alipaydev.com/gateway.do", AliConfig.app_id,
//				AliConfig.privateKey, "json", "utf-8", AliConfig.publicKey); // 获得初始化的AlipayClient
//		
//		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();// 创建API对应的request
//
//		alipayRequest.setBizContent("{"
//				+ "\"out_trade_no\":\"811425303086895104\""
//				+ "}");// 填充业务参数
//		AlipayTradeQueryResponse response = alipayClient.execute(alipayRequest);
//		
//		String form = response.getBody();
//		
//		System.out.println("api"+form);
		
		
		
		
		
		//退款
//		Map<String, String>   params = new HashMap<String, String>();
//		params.put("out_trade_no", "20160005");
//		params.put("out_request_no", "1001");
//		params.put("refund_amount", "50");
//		JsonObject o = AliRequest.refund(params);
//		
//		System.err.println(o);
//		
//		JsonObject payquery = AliRequest.payQuery(params);
//		System.err.println(payquery);
//		JsonObject refund = AliRequest.refundQuery(params);
//		System.err.println(refund);
//		
//		JsonObject close = AliRequest.close(params);
//		System.err.println(close);
		
//		Map<String, String>   params = new HashMap<String, String>();
//		params.put("bill_type", "trade");
//		params.put("bill_date", "2016-12-20");
//		JsonObject o = AliRequest.bill(params);
//		System.err.println(o);
		
	}

}
