package com.fc.pay.bill.parser;

import java.util.List;
import java.util.Map;

import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;


/**
 * 外部日账单解析接口
 * @author zhanjq
 *
 */
public interface OutsideDailyBillParser {
	
	/**
	 * 解析业务账单
	 * @param detailBillPath
	 * @return
	 */
	public Map<String, List<OutsideDailyBizBillItem>> parseBizBill(BillBizBatch batch) throws Exception;

}
