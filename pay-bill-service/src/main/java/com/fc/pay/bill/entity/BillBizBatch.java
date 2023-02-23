package com.fc.pay.bill.entity;

import com.fc.pay.common.core.entity.Entity;
import java.math.BigDecimal;
import java.util.Date;

public class BillBizBatch extends Entity {

    private String batchNo;

    private Date billDate;

    private String payChannel;

    private String channelAppId;

    private String channelMerchantId;

    private String handleStatus;

    private String handleRemark;

    private Date startTime;

    private Date endTime;

    private Integer restartCount;

    private String channelBillStorePath;

    private Integer tradeCount;

    private BigDecimal tradeAmount;

    private Integer refundCount;

    private BigDecimal refundAmount;

    private Integer channelTradeCount;

    private BigDecimal channelTradeAmount;

    private Integer channelRefundCount;

    private BigDecimal channelRefundAmount;

    private Integer mistakeCount;

    private Integer mistakeUnhandleCount;

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

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus == null ? null : handleStatus.trim();
    }

    public String getHandleRemark() {
        return handleRemark;
    }

    public void setHandleRemark(String handleRemark) {
        this.handleRemark = handleRemark == null ? null : handleRemark.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getRestartCount() {
        return restartCount;
    }

    public void setRestartCount(Integer restartCount) {
        this.restartCount = restartCount;
    }

    public String getChannelBillStorePath() {
        return channelBillStorePath;
    }

    public void setChannelBillStorePath(String channelBillStorePath) {
        this.channelBillStorePath = channelBillStorePath == null ? null : channelBillStorePath.trim();
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Integer getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Integer getChannelTradeCount() {
        return channelTradeCount;
    }

    public void setChannelTradeCount(Integer channelTradeCount) {
        this.channelTradeCount = channelTradeCount;
    }

    public BigDecimal getChannelTradeAmount() {
        return channelTradeAmount;
    }

    public void setChannelTradeAmount(BigDecimal channelTradeAmount) {
        this.channelTradeAmount = channelTradeAmount;
    }

    public Integer getChannelRefundCount() {
        return channelRefundCount;
    }

    public void setChannelRefundCount(Integer channelRefundCount) {
        this.channelRefundCount = channelRefundCount;
    }

    public BigDecimal getChannelRefundAmount() {
        return channelRefundAmount;
    }

    public void setChannelRefundAmount(BigDecimal channelRefundAmount) {
        this.channelRefundAmount = channelRefundAmount;
    }

    public Integer getMistakeCount() {
        return mistakeCount;
    }

    public void setMistakeCount(Integer mistakeCount) {
        this.mistakeCount = mistakeCount;
    }

    public Integer getMistakeUnhandleCount() {
        return mistakeUnhandleCount;
    }

    public void setMistakeUnhandleCount(Integer mistakeUnhandleCount) {
        this.mistakeUnhandleCount = mistakeUnhandleCount;
    }

	@Override
	public String toString() {
		return "BillBizBatch [batchNo=" + batchNo + ", billDate=" + billDate
				+ ", payChannel=" + payChannel + ", channelAppId="
				+ channelAppId + ", channelMerchantId=" + channelMerchantId
				+ ", handleStatus=" + handleStatus + ", handleRemark="
				+ handleRemark + ", startTime=" + startTime + ", endTime="
				+ endTime + ", restartCount=" + restartCount
				+ ", channelBillStorePath=" + channelBillStorePath
				+ ", tradeCount=" + tradeCount + ", tradeAmount=" + tradeAmount
				+ ", refundCount=" + refundCount + ", refundAmount="
				+ refundAmount + ", channelTradeCount=" + channelTradeCount
				+ ", channelTradeAmount=" + channelTradeAmount
				+ ", channelRefundCount=" + channelRefundCount
				+ ", channelRefundAmount=" + channelRefundAmount
				+ ", mistakeCount=" + mistakeCount + ", mistakeUnhandleCount="
				+ mistakeUnhandleCount + ", toString()=" + super.toString()
				+ "]";
	}


    
    
}