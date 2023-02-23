package com.fc.pay.trade.entity;

import com.fc.pay.common.core.entity.Entity;

public class MerchantAppConfig extends Entity {
    private String merchantCode;

    private String merchantName;

    private String merchantAppCode;

    private String merchantAppName;

    private String channel;

    private String channelAppId;

    private String channelMerchantId;

    private String certPath;

    private String certPwd;

    private String certValidate;

    private String subMchId;

    private String priKey;

    private String pubKey;

    private String certEncPath;

    private Integer status;

    private String appSecret;

    private String partnerId;
    
    private String payKey;
    
    private String payWay;

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
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

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath == null ? null : certPath.trim();
    }

    public String getCertPwd() {
        return certPwd;
    }

    public void setCertPwd(String certPwd) {
        this.certPwd = certPwd == null ? null : certPwd.trim();
    }

    public String getCertValidate() {
        return certValidate;
    }

    public void setCertValidate(String certValidate) {
        this.certValidate = certValidate == null ? null : certValidate.trim();
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId == null ? null : subMchId.trim();
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey == null ? null : priKey.trim();
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey == null ? null : pubKey.trim();
    }

    public String getCertEncPath() {
        return certEncPath;
    }

    public void setCertEncPath(String certEncPath) {
        this.certEncPath = certEncPath == null ? null : certEncPath.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId == null ? null : partnerId.trim();
    }

	public String getPayKey() {
		return payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
    
}