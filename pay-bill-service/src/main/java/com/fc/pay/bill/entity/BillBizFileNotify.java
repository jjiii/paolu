package com.fc.pay.bill.entity;

import com.fc.pay.common.core.entity.Entity;
import java.util.Date;

public class BillBizFileNotify extends Entity {

    private Date billDate;

    private String merchantCode;

    private String merchantName;

    private String merchantAppCode;

    private String merchantAppName;

    private String filePath;

    private String fileStatus;

    private String fileRemark;

    private String notifyUrl;

    private String notifyStatus;

    private String notifyRemark;

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus == null ? null : fileStatus.trim();
    }

    public String getFileRemark() {
        return fileRemark;
    }

    public void setFileRemark(String fileRemark) {
        this.fileRemark = fileRemark == null ? null : fileRemark.trim();
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus == null ? null : notifyStatus.trim();
    }

    public String getNotifyRemark() {
        return notifyRemark;
    }

    public void setNotifyRemark(String notifyRemark) {
        this.notifyRemark = notifyRemark == null ? null : notifyRemark.trim();
    }

	@Override
	public String toString() {
		return "BillBizFileNotify [billDate=" + billDate + ", merchantCode="
				+ merchantCode + ", merchantName=" + merchantName
				+ ", merchantAppCode=" + merchantAppCode + ", merchantAppName="
				+ merchantAppName + ", filePath=" + filePath + ", fileStatus="
				+ fileStatus + ", fileRemark=" + fileRemark + ", notifyUrl="
				+ notifyUrl + ", notifyStatus=" + notifyStatus
				+ ", notifyRemark=" + notifyRemark + ", toString()="
				+ super.toString() + "]";
	}
    
    
}