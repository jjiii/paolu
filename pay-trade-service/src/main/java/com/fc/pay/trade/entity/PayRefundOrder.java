package com.fc.pay.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fc.pay.common.core.entity.Entity;

public class PayRefundOrder extends Entity {
	/**原商户编号*/
	private String merchantCode;
	/**原商户名称*/
	private String merchantName;
	/**原商户在本系统的appID*/
	private String merchantAppCode;
	/**原商户在本系统app名称*/
	private String merchantAppName;
	/**原支付宝、微信的app_id*/
	private String channelAppId;
	/**原微信、银联的商户id*/
	private String channelMerchantId;
	/**原支付流水号*/
	private String orderNo;
	/**原商户订单号*/
	private String merchantOrderNo;
	/**原商品名称*/
	private String productName;
	/**原渠道编号:支付宝：alipay,微信：weixin,银联:union*/
	private String channel;
	/**原支付类型编号(包括支付宝、微信、银联所有支付方式.具体见枚举类)*/
	private String payWay;
	/**原订单金额*/
	private BigDecimal amount;
	/**原订单时间*/
	private Date orderTime;
	/**原备注信息*/
	private String remark;
	/**原订单状态：close, wait, success, finish*/
	private String status;
	/**原订成功或关闭的成时间*/
	private Date finishTime;
	/**原第三方返回流水号*/
	private String tradeNo;
	/**原第三方返回付款买家ID*/
	private String buyerId;
	/**系统生成退款订单号*/
	private String refundNo;
	/**商户退款订单号*/
	private String refundMerchantNo;
	/**退款原因*/
	private String refundReason;
	/**退款金额*/
	private BigDecimal refundAmount;
	/**申请退款时间*/
	private Date refundTime;
	/**向第三方申请退款中:application, 成功退款:success, 退款失败:faile */
	private String refundStatus;
	/**失败原因*/
	private String refundFaileReason;
	/**成功原因*/
	private String refundSuccessReason;
	/**完成时间，即退款成功或退款失败时间*/
	private Date refundFinishTime;
	/**实际退款到账的买家ID*/
	private String refundBuyerId;
	/**第三方返回流水号*/
	private String refundTradeNo;
	/**退款备注*/
	private String refundRemark;
	/**对账状态,见枚举类BillStatusEum*/
	private String billStatus;

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode == null ? null : merchantCode.trim();
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName == null ? null : merchantName.trim();
	}

	public String getMerchantAppCode() {
		return merchantAppCode;
	}

	public void setMerchantAppCode(String merchantAppCode) {
		this.merchantAppCode = merchantAppCode == null ? null : merchantAppCode.trim();
	}

	public String getMerchantAppName() {
		return merchantAppName;
	}

	public void setMerchantAppName(String merchantAppName) {
		this.merchantAppName = merchantAppName == null ? null : merchantAppName.trim();
	}

	public String getChannelAppId() {
		return channelAppId;
	}

	public void setChannelAppId(String channelAppId) {
		this.channelAppId = channelAppId == null ? null : channelAppId.trim();
	}

	public String getChannelMerchantId() {
		return channelMerchantId;
	}

	public void setChannelMerchantId(String channelMerchantId) {
		this.channelMerchantId = channelMerchantId == null ? null : channelMerchantId.trim();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel == null ? null : channel.trim();
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay == null ? null : payWay.trim();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo == null ? null : tradeNo.trim();
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId == null ? null : buyerId.trim();
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo == null ? null : refundNo.trim();
	}

	public String getRefundMerchantNo() {
		return refundMerchantNo;
	}

	public void setRefundMerchantNo(String refundMerchantNo) {
		this.refundMerchantNo = refundMerchantNo == null ? null : refundMerchantNo.trim();
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason == null ? null : refundReason.trim();
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
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
		this.refundStatus = refundStatus == null ? null : refundStatus.trim();
	}

	public String getRefundFaileReason() {
		return refundFaileReason;
	}

	public void setRefundFaileReason(String refundFaileReason) {
		this.refundFaileReason = refundFaileReason == null ? null : refundFaileReason.trim();
	}

	public String getRefundSuccessReason() {
		return refundSuccessReason;
	}

	public void setRefundSuccessReason(String refundSuccessReason) {
		this.refundSuccessReason = refundSuccessReason == null ? null : refundSuccessReason.trim();
	}

	public Date getRefundFinishTime() {
		return refundFinishTime;
	}

	public void setRefundFinishTime(Date refundFinishTime) {
		this.refundFinishTime = refundFinishTime;
	}

	public String getRefundBuyerId() {
		return refundBuyerId;
	}

	public void setRefundBuyerId(String refundBuyerId) {
		this.refundBuyerId = refundBuyerId == null ? null : refundBuyerId.trim();
	}

	public String getRefundTradeNo() {
		return refundTradeNo;
	}

	public void setRefundTradeNo(String refundTradeNo) {
		this.refundTradeNo = refundTradeNo == null ? null : refundTradeNo.trim();
	}

	public String getRefundRemark() {
		return refundRemark;
	}

	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark == null ? null : refundRemark.trim();
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus == null ? null : billStatus.trim();
	}
}