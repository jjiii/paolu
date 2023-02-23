package com.fc.pay.bill.entity;

import com.fc.pay.common.core.entity.Entity;
import java.math.BigDecimal;
import java.util.Date;

public class BillBizDoubt extends Entity {

    private String batchNo;

    private Date billDate;

    private String payChannel;

    private String payWay;

    private String channelAppId;

    private String channelMerchantId;

    private String billType;

    private String merchantCode;

    private String merchantName;

    private String merchantAppCode;

    private String merchantAppName;

    private String merchantCommodity;

    private String currency;

    private String payMerchOrderNo;

    private String payOrderNo;

    private String payTradeNo;

    private BigDecimal payTradeAmount;

    private String payTradeStatus;

    private Date payOrderTime;

    private Date paySuccessTime;

    private String refundMerchOrderNo;

    private String refundOrderNo;

    private String refundTradeNo;

    private BigDecimal refundTradeAmount;

    private String refundTradeStatus;

    private Date refundApplyTime;

    private Date refundSuccessTime;

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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
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

    public BigDecimal getPayTradeAmount() {
        return payTradeAmount;
    }

    public void setPayTradeAmount(BigDecimal payTradeAmount) {
        this.payTradeAmount = payTradeAmount;
    }

    public String getPayTradeStatus() {
        return payTradeStatus;
    }

    public void setPayTradeStatus(String payTradeStatus) {
        this.payTradeStatus = payTradeStatus == null ? null : payTradeStatus.trim();
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

    public BigDecimal getRefundTradeAmount() {
        return refundTradeAmount;
    }

    public void setRefundTradeAmount(BigDecimal refundTradeAmount) {
        this.refundTradeAmount = refundTradeAmount;
    }

    public String getRefundTradeStatus() {
        return refundTradeStatus;
    }

    public void setRefundTradeStatus(String refundTradeStatus) {
        this.refundTradeStatus = refundTradeStatus == null ? null : refundTradeStatus.trim();
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
		return "BillBizDoubt [batchNo=" + batchNo + ", billDate=" + billDate
				+ ", payChannel=" + payChannel + ", payWay=" + payWay
				+ ", channelAppId=" + channelAppId + ", channelMerchantId="
				+ channelMerchantId + ", billType=" + billType
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", merchantAppCode=" + merchantAppCode
				+ ", merchantAppName=" + merchantAppName
				+ ", merchantCommodity=" + merchantCommodity + ", currency="
				+ currency + ", payMerchOrderNo=" + payMerchOrderNo
				+ ", payOrderNo=" + payOrderNo + ", payTradeNo=" + payTradeNo
				+ ", payTradeAmount=" + payTradeAmount + ", payTradeStatus="
				+ payTradeStatus + ", payOrderTime=" + payOrderTime
				+ ", paySuccessTime=" + paySuccessTime
				+ ", refundMerchOrderNo=" + refundMerchOrderNo
				+ ", refundOrderNo=" + refundOrderNo + ", refundTradeNo="
				+ refundTradeNo + ", refundTradeAmount=" + refundTradeAmount
				+ ", refundTradeStatus=" + refundTradeStatus
				+ ", refundApplyTime=" + refundApplyTime
				+ ", refundSuccessTime=" + refundSuccessTime + ", toString()="
				+ super.toString() + "]";
	}
    
    
}