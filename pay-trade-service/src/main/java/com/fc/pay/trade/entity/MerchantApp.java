package com.fc.pay.trade.entity;

import com.fc.pay.common.core.entity.Entity;

public class MerchantApp extends Entity {
    private String merchantCode;

    private String merchantName;

    private String merchantAppCode;

    private String merchantAppName;

    private String priKey;

    private String pubKey;
    
    private String mctPubKey;

    private Integer status;

    private String downloadNotifyUrl;

    private String handleMistakeNotifyUrl;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDownloadNotifyUrl() {
        return downloadNotifyUrl;
    }

    public void setDownloadNotifyUrl(String downloadNotifyUrl) {
        this.downloadNotifyUrl = downloadNotifyUrl == null ? null : downloadNotifyUrl.trim();
    }

    public String getHandleMistakeNotifyUrl() {
        return handleMistakeNotifyUrl;
    }

	public void setHandleMistakeNotifyUrl(String handleMistakeNotifyUrl) {
        this.handleMistakeNotifyUrl = handleMistakeNotifyUrl == null ? null : handleMistakeNotifyUrl.trim();
    }
	
    public String getMctPubKey() {
		return mctPubKey;
	}

	public void setMctPubKey(String mctPubKey) {
		this.mctPubKey = mctPubKey;
	}
}