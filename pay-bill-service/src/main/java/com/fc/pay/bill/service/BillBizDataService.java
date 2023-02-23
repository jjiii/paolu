package com.fc.pay.bill.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;

/**
 * 业务对账数据服务接口
 * 
 * @author zhanjq
 *
 */
public interface BillBizDataService {
	
	/**
	 * 分页查询-汇总
	 * @param paramMap
	 * @return
	 */
	Page<BillBizSummary> pageBillBizSummary(Integer pageNo, Integer pageSize, Map<String, Object> paramMap);
	
	/**
	 * 分页查询-批次
	 * @param paramMap
	 * @return
	 */
	Page<BillBizBatch> pageBillBizBatch(Integer pageNo, Integer pageSize, Map<String, Object> paramMap);
	
	/**
	 * 分页查询-明细
	 * @param paramMap
	 * @return
	 */
	Page<BillBizItem> pageBillBizItem(Integer pageNo, Integer pageSize, Map<String, Object> paramMap);
	
	/**
	 * 分页查询-存疑
	 * @param paramMap
	 * @return
	 */
	Page<BillBizDoubt> pageBillBizDoubt(Integer pageNo, Integer pageSize, Map<String, Object> paramMap);
	
	/**
	 * 分页查询-错误
	 * @param paramMap
	 * @return
	 */
	Page<BillBizMistake> pageBillBizMistake(Integer pageNo, Integer pageSize, Map<String, Object> paramMap);
	
	/**
	 * 分页查询-文件通知
	 * @param paramMap
	 * @return
	 */
	Page<BillBizFileNotify> pageBillBizFileNotify(Integer pageNo, Integer pageSize, Map<String, Object> paramMap);
	

}
