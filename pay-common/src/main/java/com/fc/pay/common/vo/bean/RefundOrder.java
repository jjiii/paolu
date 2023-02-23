package com.fc.pay.common.vo.bean;

import java.math.BigDecimal;
import java.util.Date;

public class RefundOrder {
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
	
	/**退款金额*/
	private BigDecimal refundAmount;
	
	/**系统生成退款订单号*/
	private String refundNo;
	/**商户退款订单号*/
	private String refundMerchantNo;
	/**第三方返回流水号*/
	private String refundTradeNo;
	/**申请退款时间*/
	private Date refundTime;
	/**向第三方申请退款中:application, 成功退款:success, 退款失败:faile */
	private String refundStatus;
	
	/**当前时间*/
	private long timestamp = System.currentTimeMillis();

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
	
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public String getRefundMerchantNo() {
		return refundMerchantNo;
	}

	public void setRefundMerchantNo(String refundMerchantNo) {
		this.refundMerchantNo = refundMerchantNo;
	}

	public String getRefundTradeNo() {
		return refundTradeNo;
	}

	public void setRefundTradeNo(String refundTradeNo) {
		this.refundTradeNo = refundTradeNo;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	
	
}
