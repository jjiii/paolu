package com.fc.pay.bill.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账批次数据服务接口
 * 
 * @author zhanjq
 *
 */
public interface BillBizBatchService {
	
	public void deleteAll();
	
	/**
	 * 新增
	 */
	public int add(BillBizBatch batch);
	
	/**
	 * 新增
	 */
	public List<BillBizBatch> addBatch(List<BillBizBatch> batchList);

	/**
	 * 修改
	 */
	public int modify(BillBizBatch batch);

	/**
	 * ID查询
	 * @param id
	 * @return
	 */
	public BillBizBatch get(Long id);
		
	/**
	 * 分页查询
	 * @param paramMap
	 * @return
	 */
	Page page(Map<String, Object> paramMap);

	/**
	 * 列表查询
	 * @param paramMap
	 * @return
	 */
	public List<BillBizBatch> list(Map<String, Object> paramMap);

	/**
	 * 根据批次号加载对账批次
	 * @param batchNo
	 * @return
	 */
	public BillBizBatch getByBatchNo(String batchNo);
	
	/**
	 * 根据账单日期查询列表
	 * @param billDate
	 * @return
	 */
	public List<BillBizBatch> listByBillDate(Date billDate);
	
	/**
	 * 根据MAP查询单个记录
	 * @param paramMap
	 * @return
	 */
	public BillBizBatch getByMap(Map<String, Object> paramMap);

	

}
