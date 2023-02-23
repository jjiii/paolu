package com.fc.pay.bill.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 外部业务账单明细记录
 * 
 * @author zhanjq
 *
 */
public class OutsideDailyBizBillItem {
	
	//对账批次号
	private String batchNo;

	//支付渠道类型 包括微信WEIXIN、支付宝ALIPAY、银联UNIONPAY
	private String payChannel;
	
	//商户应用编号 微信公众号、支付宝应用ID。
	private String merchantAppId;
	
	//商户号
	private String merchantId;
	
	//账单日期 统一使用yyyyMMdd 格式
	private Date billDate;
	
	//支付订单号
	private String payOrderNo;
	
	//支付流水号
	private String payTradeNo;
	
	//支付创建时间
	private Date payOrderTime;
	
	//支付完成时间
	private Date paySuccessTime;
	
	//支付交易状态
	private String payTradeStatus;
	
	//支付交易金额
	private BigDecimal payTradeAmount;
	
	//退款订单号
	private String refundOrderNo;
	
	//退款流水号
	private String refundTradeNo;
	
	//退款金额
	private BigDecimal refundAmount;
	
	//退款状态
	private String refundStatus;
	
	//退款申请时间
	private Date refundApplyTime;
	
	//退款成功时间
	private Date refundSuccessTime;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getMerchantAppId() {
		return merchantAppId;
	}

	public void setMerchantAppId(String merchantAppId) {
		this.merchantAppId = merchantAppId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
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

	public String getPayTradeStatus() {
		return payTradeStatus;
	}

	public void setPayTradeStatus(String payTradeStatus) {
		this.payTradeStatus = payTradeStatus;
	}

	public BigDecimal getPayTradeAmount() {
		return payTradeAmount;
	}

	public void setPayTradeAmount(BigDecimal payTradeAmount) {
		this.payTradeAmount = payTradeAmount;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getRefundTradeNo() {
		return refundTradeNo;
	}

	public void setRefundTradeNo(String refundTradeNo) {
		this.refundTradeNo = refundTradeNo;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
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

	@Override
	public String toString() {
		return "OutsideDailyBizBillItem [batchNo=" + batchNo + ", payChannel="
				+ payChannel + ", merchantAppId=" + merchantAppId
				+ ", merchantId=" + merchantId + ", billDate=" + billDate
				+ ", payOrderNo=" + payOrderNo + ", payTradeNo=" + payTradeNo
				+ ", payOrderTime=" + payOrderTime + ", paySuccessTime="
				+ paySuccessTime + ", payTradeStatus=" + payTradeStatus
				+ ", payTradeAmount=" + payTradeAmount + ", refundOrderNo="
				+ refundOrderNo + ", refundTradeNo=" + refundTradeNo
				+ ", refundAmount=" + refundAmount + ", refundStatus="
				+ refundStatus + ", refundApplyTime=" + refundApplyTime
				+ ", refundSuccessTime=" + refundSuccessTime + "]";
	}
	
	
}
