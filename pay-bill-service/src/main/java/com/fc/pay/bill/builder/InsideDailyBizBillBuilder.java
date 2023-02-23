package com.fc.pay.bill.builder;

import java.util.List;

import com.fc.pay.bill.entity.BillBizItem;

/**
 * 内部业务对账日账单生成器接口
 * 
 * @author zhanjq
 *
 */
public interface InsideDailyBizBillBuilder {
	
	/**
	 * 当日成功支付对账单
	 * @param paySuccessItemList
	 * @param billPath
	 */
	public void buildInsidePaySuccessBill(List<BillBizItem> paySuccessItemList, String billPath) throws Exception ;
	
	/**
	 * 当日退款对账单
	 * @param refundItemList
	 * @param billPath
	 */
	public void buildInsideRefundBill(List<BillBizItem> refundItemList, String billPath) throws Exception ;

}
