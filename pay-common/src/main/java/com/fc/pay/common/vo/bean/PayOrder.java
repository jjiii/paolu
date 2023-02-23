package com.fc.pay.common.vo.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PayOrder{
	
	/**原订单时间*/
	private Date orderTime;
	/**商户在本系统的appID*/
	private String merchantAppCode;
	/**商户在本系统app名称*/
	private String merchantAppName;
	/**系统生成的支付流水号*/
	private String orderNo;
	/**商户订单号*/
	private String merchantOrderNo;
	/**商品名称*/
	private String productName;
	/**渠道编号:支付宝：alipay,微信：weixin,银联:union*/
	private String channel;
	/**订单金额*/
	private BigDecimal amount;
	/**关闭(超时未收到通知):close, 待支付:pay_wait, 成功:success, 结束(不能退款):finish*/
	private String status;
	/**支付凭据。查询的时候，没有就是null*/
	private String credential;
	/**自定义支付类型编号(包括支付宝、微信、银联所有支付方式.具体见枚举类)*/
	private String payWay;
	
	/**当前时间*/
	private long timestamp;
	
	
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getMerchantAppCode() {
		return merchantAppCode;
	}
	public void setMerchantAppCode(String merchantAppCode) {
		this.merchantAppCode = merchantAppCode;
	}
	public String getMerchantAppName() {
		return merchantAppName;
	}
	public void setMerchantAppName(String merchantAppName) {
		this.merchantAppName = merchantAppName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCredential() {
		return credential;
	}
	public void setCredential(String credential) {
		this.credential = credential;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	
	
	

}
