package com.fc.pay.common.pay.alipay;

import com.fc.pay.common.core.utils.Property;

/**
 * @该类对应properties/pay/alipay.properties文件
 * 不使用spring的@value，因为@value必须在spring管理的bean内使用
 * @author XDou
 */
public class AliConfig {
	
	private static Property property = new Property("properties/pay/alipay_config.properties");
	
	
	public String app_id = null;
	public String publicKey = null;
	public String privateKey = null;
	
	
	public AliConfig(String app_id, String privateKey,String publicKey){
		this.app_id = app_id;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	
	public String mobile_returnUrl = property.getProperty("mobile_returnUrl");
	public static String mobile_notifyUrl = property.getProperty("mobile_notifyUrl");
	
	
	
	public static String url = property.getProperty("alipay.gateway");//蚂蚁金服统一API地址
	public String charset = property.getProperty("charset");
	public String sign_type = property.getProperty("sign_type");
	public String version = property.getProperty("version");
	public String format = property.getProperty("format");
	public static String timeout_express = property.getProperty("timeout_express");
//	public String timestamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	
	
	
	//手机网站支付支付
	public String pay_method = property.getProperty("pay_method");
	//关闭订单
	public String close_method = property.getProperty("close_method");
	//交易查询接口
	public String query_method = property.getProperty("query_method");
	//退款接口
	public String refund_method =  property.getProperty("refund_method");
	//退款查询
	public String refund_query_method =  property.getProperty("refund_query_method");
	//对账
	public String bill_method = property.getProperty("bill_method");
	//对账备注
	public static String TRANS_MEMO = property.getProperty("TRANS_MEMO");
	
	
//	public void reload(){
//		property = new Property("properties/pay/alipay.properties");
//		publicKey = null;
//		privateKey = null;
//		app_id = null;
//		mobile_returnUrl = property.getProperty("returnUrl");
//		mobile_notifyUrl = property.getProperty("notifyUrl");
//		url = property.getProperty("url");//蚂蚁金服统一API地址
//		charset = property.getProperty("charset");
//		sign_type = property.getProperty("sign_type");
//		version = property.getProperty("version");
//		pay_method = property.getProperty("pay_method");
//		close_method = property.getProperty("close_method");
//		query_method = property.getProperty("query_method");
//		refund_method =  property.getProperty("refund_method");
//		refund_query_method =  property.getProperty("refund_query_method");
//		bill_method = property.getProperty("bill_method");
//	}
	
	
}
