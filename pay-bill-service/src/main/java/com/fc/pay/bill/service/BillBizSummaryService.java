package com.fc.pay.bill.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账汇总数据服务接口
 * 
 * @author zhanjq
 *
 */
public interface BillBizSummaryService {
	
	public void deleteAll();
	
	/**
	 * 新增
	 */
	public int add(BillBizSummary summary);

	/**
	 * 修改
	 */
	public int modify(BillBizSummary summary);

	/**
	 * ID查询
	 * @param id
	 * @return
	 */
	public BillBizSummary get(Long id);
	
	/**
	 * 条件查询
	 * @param billDate
	 * @return
	 */
	public BillBizSummary getByBillDate(Date billDate);
	
	/**
	 * 分页查询
	 * @param paramMap
	 * @return
	 */
	public Page page(Map<String, Object> paramMap);

	/**
	 * 列表查询
	 * @param paramMap
	 * @return
	 */
	public List<BillBizSummary> list(Map<String, Object> paramMap);

	/**
	 * 修改成功跑批次数
	 * @param summaryId	汇总ID
	 * @param batchRunSuccessCount	成功跑批次数
	 * @return
	 */
	int modifyBatchRunSuccessCount(Long summaryId, Integer batchRunSuccessCount);
	
	/**
	 * 修改成功生成账单个数
	 * @param summaryId
	 * @param billMakeSuccessCount
	 * @return
	 */
    int modifyBillMakeSuccessCount(Long summaryId, Integer billMakeSuccessCount);
    
    /**
     * 修改成功发送下载通知个数
     * @param summaryId
     * @param downloadNotifySuccessCount
     * @return
     */
    int modifyDownloadNotifySuccessCount(Long summaryId, Integer downloadNotifySuccessCount);
    
    /**
	 * 分页查询，Boss系统调用
	 * @param paramMap
	 * @return
	 */
	public Page pageList(Map<String, Object> paramMap,Integer current,Integer pagesize);
	
	/**
	 * 查找对账跑批未完成的对账汇总信息
	 * @return
	 */
	public List<BillBizSummary> listByBatchCheckFail();
	
	/**
	 * 查找最大的对账日期
	 * @return
	 */
	public Date findMaxBillDate();

}
