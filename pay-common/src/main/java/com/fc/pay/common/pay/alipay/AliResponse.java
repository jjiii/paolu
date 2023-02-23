package com.fc.pay.common.pay.alipay;

import javax.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对应支付宝返回的字段
 * 
 * @author XDou
 */
public enum AliResponse {
	
	
	alipay_trade_close_response, 
	alipay_trade_query_response, 
	alipay_trade_refund_response, 
	alipay_trade_fastpay_refund_query_response;
	
	public static Logger logger = LoggerFactory.getLogger(AliResponse.class);
	
	// 公共参数，返回码
	private String code = "code";
	// 公共参数，返回码描述
	private String msg = "msg";
	// 公共参数，签名
	private String sign = "sign";

	// 支付宝交易号
	private String trade_no = "trade_no";
	// 自定义交易号
	private String out_trade_no = "out_trade_no";
	// 买家在支付宝的用户id
	private String buyer_user_id = "buyer_user_id";
	
	//查询支付接口总金额
	private String total_amount = "total_amount";
	
	//退款总金额
	private String refund_fee = "refund_fee";
	
	public static String code_success_value = "10000";
	public static String code_faile_value = "40004";

	public String code(JsonObject json) {
		if (json == null)  return null;
		return json.getJsonObject(this.name()).getString(code);
	}

	public String msg(JsonObject json) {
		try{
			if (json == null)  return null;
			return json.getJsonObject(this.name()).getString(msg);
		}catch(Exception e){
			logger.error("msg:"+e.getMessage());
		}
		return null;
	}

	public String sign(JsonObject json) {
		try{
			if (json == null)  return null;
			return json.getJsonObject(this.name()).getString(sign);
		}catch(Exception e){
			logger.error("sign:"+e.getMessage());
		}
		return null;
	}

	public String trade_no(JsonObject json) {
		try{
			if (json == null)  return null;
			return json.getJsonObject(this.name()).getString(trade_no);
		}catch(Exception e){
			logger.error("trade_no:"+e.getMessage());
		}
		return null;
	}

	public String out_trade_no(JsonObject json) {
		try{
			if (json == null)  return null;
			return json.getJsonObject(this.name()).getString(out_trade_no);
		}catch(Exception e){
			logger.error("out_trade_no:"+e.getMessage());
		}
		return null;
	}
	
	public String buyer_user_id(JsonObject json) {
		try{
			if (json == null)  return null;
			return json.getJsonObject(this.name()).getString(buyer_user_id);
		}catch(Exception e){
			logger.error("buyer_user_id:"+e.getMessage());
		}
		return null;

	}

	public String total_amount(JsonObject json){
		try{
			if (json == null)  return null;
			return json.getJsonObject(this.name()).getString(total_amount);
		}catch(Exception e){
			logger.error("total_amount:"+e.getMessage());
		}
		return null;
	}
	public String refund_fee(JsonObject json){
		try{
			if (json == null)  return null;
			return json.getJsonObject(this.name()).getString(refund_fee);
		}catch(Exception e){
			logger.error("refund_fee:"+e.getMessage());
		}
		return null;
	}

}
