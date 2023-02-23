package com.fc.pay.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fc.pay.common.core.entity.Entity;

public class PayPaymentOrder extends Entity {
	/**商户编号*/
	private String merchantCode;
	/**商户名称*/
	private String merchantName;
	/**商户在本系统的appID*/
	private String merchantAppCode;
	/**商户在本系统app名称*/
	private String merchantAppName;
	/**支付宝、微信的app_id*/
	private String channelAppId;
	/**微信、银联的商户id*/
	private String channelMerchantId;
	/**系统生成的支付流水号*/
	private String orderNo;
	/**商户订单号*/
	private String merchantOrderNo;
	/**商品名称*/
	private String productName;
	/**渠道编号:支付宝：alipay,微信：weixin,银联:union*/
	private String channel;
	/**自定义支付类型编号(包括支付宝、微信、银联所有支付方式.具体见枚举类)*/
	private String payWay;
	/**订单金额*/
	private BigDecimal amount;
	/**页面回调通知url*/
	private String returnUrl;
	/**后台异步通知url*/
	private String notifyUrl;
	/**收到订单时间*/
	private Date orderTime;
	/**下单ip*/
	private String orderIp;
	/**备注信息*/
	private String remark;
	/**关闭(超时未收到通知):close, 待支付:pay_wait, 成功:success, 结束(不能退款):finish*/
	private String status;
	/**关闭原因*/
	private String closeReason;
	/**成功原因*/
	private String successReason;
	/**完成时间，即关闭或成功的时间。(结束为不可退款状态，该订单已经完成，无需再记录)*/
	private Date finishTime;
	/**第三方返回流水号*/
	private String tradeNo;
	/**第三方返回付款买家ID*/
	private String buyerId;
	/**超时时间(单位分钟)*/
	private Integer timeOut;
	/**到期时间*/
	private Date timeExpire;
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

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl == null ? null : returnUrl.trim();
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderIp() {
		return orderIp;
	}

	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp == null ? null : orderIp.trim();
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

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason == null ? null : closeReason.trim();
	}

	public String getSuccessReason() {
		return successReason;
	}

	public void setSuccessReason(String successReason) {
		this.successReason = successReason == null ? null : successReason.trim();
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

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	public Date getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(Date timeExpire) {
		this.timeExpire = timeExpire;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus == null ? null : billStatus.trim();
	}
}