package com.fc.pay.bill.download;

import com.fc.pay.bill.entity.BillBizBatch;

/**
 * 外部日账单下载接口 
 * @author zhanjq
 *
 */
public interface OutsideDailyBillDownload {
	
	/**
	 * 下载每日业务账单
	 * @param batch
	 * @return
	 */
	public void downloadBizBill(BillBizBatch batch) throws Exception ;

}
