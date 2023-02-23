package com.fc.pay.bill.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fc.pay.common.core.entity.Entity;

public class BillBizMistake extends Entity {
	/**批次号*/
	private String batchNo;
	/**账单日期*/
	private Date billDate;
	/**支付渠道：微信、支付宝、银联*/
	private String payChannel;
	/**支付方式 扫码支付、APP支付、公众号支付*/
	private String payWay;
	/**支付渠道应用ID：公众号ID、支付宝应用ID*/
	private String channelAppId;
	/**支付渠道商户号*/
	private String channelMerchantId;
	/**商户编号*/
	private String merchantCode;
	/**商户名称*/
	private String merchantName;
	/**商户系统编号*/
	private String merchantAppCode;
	/**商户系统名称*/
	private String merchantAppName;
	/**商户产品*/
	private String merchantCommodity;
	/**货币种类：人民币CNY*/
	private String currency;
	/**对账类型：当日成功支付订单、当日退款订单*/
	private String billType;
	/**商户支付订单号，来自商户应用系统订单号*/
	private String payMerchOrderNo;
	/**支付系统支付订单号*/
	private String payOrderNo;
	/**支付系统支付流水号*/
	private String payTradeNo;
	/**支付系统支付订单状态*/
	private String payTradeStatus;
	/**支付系统支付订单金额*/
	private BigDecimal payTradeAmount;
	/**支付系统支付订单生成时间*/
	private Date payOrderTime;
	/**支付系统支付成功时间*/
	private Date paySuccessTime;
	/**支付渠道支付订单号*/
	private String channelOrderNo;
	/**支付渠道支付流水号*/
	private String channelTradeNo;
	/**支付渠道支付订单状态*/
	private String channelTradeStatus;
	/**支付渠道支付订单金额*/
	private BigDecimal channelTradeAmount;
	/**支付渠道支付时间*/
	private Date channelTradeOrderTime;
	/**支付渠道支付时间*/
	private Date channelTradeSuccessTime;
	/**商户退款订单号，来自商户应用系统订单号*/
	private String refundMerchOrderNo;
	/**支付系统退款订单号*/
	private String refundOrderNo;
	/**支付系统退款流水号*/
	private String refundTradeNo;
	/**支付系统退款订单状态*/
	private String refundTradeStatus;
	/**支付系统退款订单金额*/
	private BigDecimal refundTradeAmount;
	/**支付系统退款订单申请时间*/
	private Date refundApplyTime;
	/**支付系统退款订单成功时间*/
	private Date refundSuccessTime;
	/**支付渠道退款订单号*/
	private String channelRefundOrderNo;
	/**支付渠道退款流水号*/
	private String channelRefundTradeNo;
	/**支付渠道退款订单状态*/
	private String channelRefundTradeStatus;
	/**支付渠道退款订单金额*/
	private BigDecimal channelRefundTradeAmount;
	/**支付系统退款订单申请时间*/
	private Date channelRefundApplyTime;
	/**支付系统退款订单成功时间*/
	private Date channelRefundSuccessTime;
	/**差错类型*/
	private String mistakeType;
	/**差错处理状态*/
	private String handleStatus;
	/**差错处理方式:以支付渠道为准、以支付系统为准*/
	private String handleWay;
	/**差错处理备注 操作员填入处理说明*/
	private String handleRemark;
	/**差错处理操作员ID*/
	private String handleUser;
	/**差错处理操作时间*/
	private Date handleTime;
	/**商户应用通知状态:已通知商户应用、商户应用已接收*/
	private String notifyStatus;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel == null ? null : payChannel.trim();
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay == null ? null : payWay.trim();
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

	public String getMerchantCommodity() {
		return merchantCommodity;
	}

	public void setMerchantCommodity(String merchantCommodity) {
		this.merchantCommodity = merchantCommodity == null ? null : merchantCommodity.trim();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType == null ? null : billType.trim();
	}

	public String getPayMerchOrderNo() {
		return payMerchOrderNo;
	}

	public void setPayMerchOrderNo(String payMerchOrderNo) {
		this.payMerchOrderNo = payMerchOrderNo == null ? null : payMerchOrderNo.trim();
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo == null ? null : payTradeNo.trim();
	}

	public String getPayTradeStatus() {
		return payTradeStatus;
	}

	public void setPayTradeStatus(String payTradeStatus) {
		this.payTradeStatus = payTradeStatus == null ? null : payTradeStatus.trim();
	}

	public BigDecimal getPayTradeAmount() {
		return payTradeAmount;
	}

	public void setPayTradeAmount(BigDecimal payTradeAmount) {
		this.payTradeAmount = payTradeAmount;
	}

	public Date getPayOrderTime() {
		return payOrderTime;
	}

	public void setPayOrderTime(Date payOrderTime) {
		this.payOrderTime = payOrderTime;
	}

	public Date getPaySuccessTime() {
		return paySuccessTime;
	}

	public void setPaySuccessTime(Date paySuccessTime) {
		this.paySuccessTime = paySuccessTime;
	}

	public String getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo == null ? null : channelOrderNo.trim();
	}

	public String getChannelTradeNo() {
		return channelTradeNo;
	}

	public void setChannelTradeNo(String channelTradeNo) {
		this.channelTradeNo = channelTradeNo == null ? null : channelTradeNo.trim();
	}

	public String getChannelTradeStatus() {
		return channelTradeStatus;
	}

	public void setChannelTradeStatus(String channelTradeStatus) {
		this.channelTradeStatus = channelTradeStatus == null ? null : channelTradeStatus.trim();
	}

	public BigDecimal getChannelTradeAmount() {
		return channelTradeAmount;
	}

	public void setChannelTradeAmount(BigDecimal channelTradeAmount) {
		this.channelTradeAmount = channelTradeAmount;
	}

	public Date getChannelTradeOrderTime() {
		return channelTradeOrderTime;
	}

	public void setChannelTradeOrderTime(Date channelTradeOrderTime) {
		this.channelTradeOrderTime = channelTradeOrderTime;
	}

	public Date getChannelTradeSuccessTime() {
		return channelTradeSuccessTime;
	}

	public void setChannelTradeSuccessTime(Date channelTradeSuccessTime) {
		this.channelTradeSuccessTime = channelTradeSuccessTime;
	}

	public String getRefundMerchOrderNo() {
		return refundMerchOrderNo;
	}

	public void setRefundMerchOrderNo(String refundMerchOrderNo) {
		this.refundMerchOrderNo = refundMerchOrderNo == null ? null : refundMerchOrderNo.trim();
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo == null ? null : refundOrderNo.trim();
	}

	public String getRefundTradeNo() {
		return refundTradeNo;
	}

	public void setRefundTradeNo(String refundTradeNo) {
		this.refundTradeNo = refundTradeNo == null ? null : refundTradeNo.trim();
	}

	public String getRefundTradeStatus() {
		return refundTradeStatus;
	}

	public void setRefundTradeStatus(String refundTradeStatus) {
		this.refundTradeStatus = refundTradeStatus == null ? null : refundTradeStatus.trim();
	}

	public BigDecimal getRefundTradeAmount() {
		return refundTradeAmount;
	}

	public void setRefundTradeAmount(BigDecimal refundTradeAmount) {
		this.refundTradeAmount = refundTradeAmount;
	}

	public Date getRefundApplyTime() {
		return refundApplyTime;
	}

	public void setRefundApplyTime(Date refundApplyTime) {
		this.refundApplyTime = refundApplyTime;
	}

	public Date getRefundSuccessTime() {
		return refundSuccessTime;
	}

	public void setRefundSuccessTime(Date refundSuccessTime) {
		this.refundSuccessTime = refundSuccessTime;
	}

	public String getChannelRefundOrderNo() {
		return channelRefundOrderNo;
	}

	public void setChannelRefundOrderNo(String channelRefundOrderNo) {
		this.channelRefundOrderNo = channelRefundOrderNo == null ? null : channelRefundOrderNo.trim();
	}

	public String getChannelRefundTradeNo() {
		return channelRefundTradeNo;
	}

	public void setChannelRefundTradeNo(String channelRefundTradeNo) {
		this.channelRefundTradeNo = channelRefundTradeNo == null ? null : channelRefundTradeNo.trim();
	}

	public String getChannelRefundTradeStatus() {
		return channelRefundTradeStatus;
	}

	public void setChannelRefundTradeStatus(String channelRefundTradeStatus) {
		this.channelRefundTradeStatus = channelRefundTradeStatus == null ? null : channelRefundTradeStatus.trim();
	}

	public BigDecimal getChannelRefundTradeAmount() {
		return channelRefundTradeAmount;
	}

	public void setChannelRefundTradeAmount(BigDecimal channelRefundTradeAmount) {
		this.channelRefundTradeAmount = channelRefundTradeAmount;
	}

	public Date getChannelRefundApplyTime() {
		return channelRefundApplyTime;
	}

	public void setChannelRefundApplyTime(Date channelRefundApplyTime) {
		this.channelRefundApplyTime = channelRefundApplyTime;
	}

	public Date getChannelRefundSuccessTime() {
		return channelRefundSuccessTime;
	}

	public void setChannelRefundSuccessTime(Date channelRefundSuccessTime) {
		this.channelRefundSuccessTime = channelRefundSuccessTime;
	}

	public String getMistakeType() {
		return mistakeType;
	}

	public void setMistakeType(String mistakeType) {
		this.mistakeType = mistakeType == null ? null : mistakeType.trim();
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus == null ? null : handleStatus.trim();
	}

	public String getHandleWay() {
		return handleWay;
	}

	public void setHandleWay(String handleWay) {
		this.handleWay = handleWay == null ? null : handleWay.trim();
	}

	public String getHandleRemark() {
		return handleRemark;
	}

	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark == null ? null : handleRemark.trim();
	}

	public String getHandleUser() {
		return handleUser;
	}

	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser == null ? null : handleUser.trim();
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus == null ? null : notifyStatus.trim();
	}

	@Override
	public String toString() {
		return "BillBizMistake [batchNo=" + batchNo + ", billDate=" + billDate + ", payChannel=" + payChannel
				+ ", payWay=" + payWay + ", channelAppId=" + channelAppId + ", channelMerchantId=" + channelMerchantId
				+ ", merchantCode=" + merchantCode + ", merchantName=" + merchantName + ", merchantAppCode="
				+ merchantAppCode + ", merchantAppName=" + merchantAppName + ", merchantCommodity=" + merchantCommodity
				+ ", currency=" + currency + ", billType=" + billType + ", payMerchOrderNo=" + payMerchOrderNo
				+ ", payOrderNo=" + payOrderNo + ", payTradeNo=" + payTradeNo + ", payTradeStatus=" + payTradeStatus
				+ ", payTradeAmount=" + payTradeAmount + ", payOrderTime=" + payOrderTime + ", paySuccessTime="
				+ paySuccessTime + ", channelOrderNo=" + channelOrderNo + ", channelTradeNo=" + channelTradeNo
				+ ", channelTradeStatus=" + channelTradeStatus + ", channelTradeAmount=" + channelTradeAmount
				+ ", channelTradeOrderTime=" + channelTradeOrderTime + ", channelTradeSuccessTime="
				+ channelTradeSuccessTime + ", refundMerchOrderNo=" + refundMerchOrderNo + ", refundOrderNo="
				+ refundOrderNo + ", refundTradeNo=" + refundTradeNo + ", refundTradeStatus=" + refundTradeStatus
				+ ", refundTradeAmount=" + refundTradeAmount + ", refundApplyTime=" + refundApplyTime
				+ ", refundSuccessTime=" + refundSuccessTime + ", channelRefundOrderNo=" + channelRefundOrderNo
				+ ", channelRefundTradeNo=" + channelRefundTradeNo + ", channelRefundTradeStatus="
				+ channelRefundTradeStatus + ", channelRefundTradeAmount=" + channelRefundTradeAmount
				+ ", channelRefundApplyTime=" + channelRefundApplyTime + ", channelRefundSuccessTime="
				+ channelRefundSuccessTime + ", mistakeType=" + mistakeType + ", handleStatus=" + handleStatus
				+ ", handleWay=" + handleWay + ", handleRemark=" + handleRemark + ", handleUser=" + handleUser
				+ ", handleTime=" + handleTime + ", notifyStatus=" + notifyStatus + ", toString()=" + super.toString()
				+ "]";
	}

}