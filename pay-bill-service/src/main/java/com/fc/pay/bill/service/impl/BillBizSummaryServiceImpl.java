package com.fc.pay.bill.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.bill.dao.BillBizSummaryMapper;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.service.BillBizSummaryService;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账汇总数据服务接口实现
 * 
 * @author zhanjq
 *
 */
@Service("billBizSummaryService")
public class BillBizSummaryServiceImpl implements BillBizSummaryService {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizSummaryServiceImpl.class);
	
	@Autowired
	private BillBizSummaryMapper summaryMapper;

	@Transactional
	public int add(BillBizSummary summary) {
		return summaryMapper.insertSelective(summary);
	}

	@Transactional
	public int modify(BillBizSummary summary) {
		return summaryMapper.updateByPrimaryKeySelective(summary);
	}

	public BillBizSummary get(Long id) {
		return summaryMapper.selectByPrimaryKey(id);
	}

	public BillBizSummary getByBillDate(Date billDate) {
		return summaryMapper.selectByBillDate(billDate);
	}

	public Page page(Map<String, Object> paramMap) {
		return summaryMapper.selectPageByMap(paramMap);
	}

	public List<BillBizSummary> list(Map<String, Object> paramMap) {
		return summaryMapper.selectAllByMap(paramMap);
	}

	@Transactional
	public int modifyBatchRunSuccessCount(Long summaryId,
			Integer batchRunSuccessCount) {
		return summaryMapper.updateBatchRunSuccessCount(summaryId, batchRunSuccessCount);
	}

	@Transactional
	public int modifyBillMakeSuccessCount(Long summaryId,
			Integer billMakeSuccessCount) {
		return summaryMapper.updateBillMakeSuccessCount(summaryId, billMakeSuccessCount);
	}

	@Transactional
	public int modifyDownloadNotifySuccessCount(Long summaryId,
			Integer downloadNotifySuccessCount) {
		return summaryMapper.updateDownloadNotifySuccessCount(summaryId, downloadNotifySuccessCount);
	}

	@Override
	public Page pageList(Map<String, Object> paramMap, Integer current,Integer pagesize) {
		Page page = new Page();
		page.setCurrNum(current);
		page.setPageSize(pagesize);
		paramMap.put("page", page);
		return summaryMapper.selectPageForBoss(paramMap);
	}

	@Override
	public List<BillBizSummary> listByBatchCheckFail() {
		return summaryMapper.selectByBatchCheckFail();
	}

	@Override
	public Date findMaxBillDate() {
		return summaryMapper.findMaxBillDate();
	}

	@Override
	public void deleteAll() {
		summaryMapper.deleteAll();
	}

}
