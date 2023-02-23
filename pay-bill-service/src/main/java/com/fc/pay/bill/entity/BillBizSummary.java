package com.fc.pay.bill.entity;

import com.fc.pay.common.core.entity.Entity;
import java.util.Date;

public class BillBizSummary extends Entity {

    private Date billDate;

    private Integer batchCount;

    private Integer batchRunSuccessCount;

    private Integer merchantAppCount;

    private Integer billMakeSuccessCount;

    private Integer downloadNotifySuccessCount;

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Integer getBatchCount() {
        return batchCount;
    }

    public void setBatchCount(Integer batchCount) {
        this.batchCount = batchCount;
    }

    public Integer getBatchRunSuccessCount() {
        return batchRunSuccessCount;
    }

    public void setBatchRunSuccessCount(Integer batchRunSuccessCount) {
        this.batchRunSuccessCount = batchRunSuccessCount;
    }

    public Integer getMerchantAppCount() {
        return merchantAppCount;
    }

    public void setMerchantAppCount(Integer merchantAppCount) {
        this.merchantAppCount = merchantAppCount;
    }

    public Integer getBillMakeSuccessCount() {
        return billMakeSuccessCount;
    }

    public void setBillMakeSuccessCount(Integer billMakeSuccessCount) {
        this.billMakeSuccessCount = billMakeSuccessCount;
    }

    public Integer getDownloadNotifySuccessCount() {
        return downloadNotifySuccessCount;
    }

    public void setDownloadNotifySuccessCount(Integer downloadNotifySuccessCount) {
        this.downloadNotifySuccessCount = downloadNotifySuccessCount;
    }

	@Override
	public String toString() {
		return "BillBizSummary [billDate=" + billDate + ", batchCount=" + batchCount
				+ ", batchRunSuccessCount=" + batchRunSuccessCount
				+ ", merchantAppCount=" + merchantAppCount
				+ ", billMakeSuccessCount=" + billMakeSuccessCount
				+ ", downloadNotifySuccessCount=" + downloadNotifySuccessCount
				+ ", toString()=" + super.toString() + "]";
	}

  
    
}