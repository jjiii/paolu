package com.fc.pay.trade.entity;

import com.fc.pay.common.core.entity.Entity;
import java.math.BigDecimal;
import java.util.Date;

public class PayTradeRecord extends Entity {
    private String merchantCode;

    private String merchantName;

    private String merchantAppCode;

    private String merchantAppName;

    private String channelAppId;

    private String channelMerchantId;

    private String orderNo;

    private String merchantOrderNo;

    private String productName;

    private String channel;

    private String payWay;

    private BigDecimal amount;

    private Date orderTime;

    private String remark;

    private String tradeNo;

    private String buyerId;

    private Date finishTime;

    private String refundNo;

    private String refundMerchantNo;

    private String refundReason;

    private BigDecimal refundAmount;

    private Date refundTime;

    private Date refundSuccessTime;

    private String refundBuyerId;

    private String refundTradeNo;

    private String refundRemark;

    private String notifiyParam;

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

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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

    public Date getRefundSuccessTime() {
        return refundSuccessTime;
    }

    public void setRefundSuccessTime(Date refundSuccessTime) {
        this.refundSuccessTime = refundSuccessTime;
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

    public String getNotifiyParam() {
        return notifiyParam;
    }

    public void setNotifiyParam(String notifiyParam) {
        this.notifiyParam = notifiyParam == null ? null : notifiyParam.trim();
    }
}