package com.fc.pay.bill.vo;

import java.util.List;

import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizSummary;

/**
 * 业务对账过程数据模型
 * 
 * @author zhanjq
 *
 */
public class BillBizCheckProgressData {
	
	/**
	 * 汇总对象
	 */
	private BillBizSummary summary;
	
	/**
	 * 账单通知列表
	 */
	private List<BillBizFileNotify> fileNotifyList;
	
	/**
	 * 对账批次列表
	 */
	private List<BillBizBatch> batchList;

	public BillBizSummary getSummary() {
		return summary;
	}

	public void setSummary(BillBizSummary summary) {
		this.summary = summary;
	}

	public List<BillBizFileNotify> getFileNotifyList() {
		return fileNotifyList;
	}

	public void setFileNotifyList(List<BillBizFileNotify> fileNotifyList) {
		this.fileNotifyList = fileNotifyList;
	}

	public List<BillBizBatch> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<BillBizBatch> batchList) {
		this.batchList = batchList;
	}

	@Override
	public String toString() {
		return "BillBizCheckProgressData [summary=" + summary
				+ ", fileNotifyList=" + fileNotifyList + ", batchList="
				+ batchList + "]";
	} 
	
	

}
