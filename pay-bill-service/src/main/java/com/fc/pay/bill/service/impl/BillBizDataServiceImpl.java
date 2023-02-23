package com.fc.pay.bill.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.service.BillBizBatchService;
import com.fc.pay.bill.service.BillBizDataService;
import com.fc.pay.bill.service.BillBizDoubtService;
import com.fc.pay.bill.service.BillBizFileNotifyService;
import com.fc.pay.bill.service.BillBizItemService;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.bill.service.BillBizSummaryService;

/**
 * 对账数据查询服务
 * @author zhanjq
 *
 */
@Service
public class BillBizDataServiceImpl implements BillBizDataService {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizDataServiceImpl.class);
	
	@Autowired
	private BillBizSummaryService summaryService;
	
	@Autowired
	private BillBizBatchService batchService;
	
	@Autowired
	private BillBizItemService itemService;
	
	@Autowired
	private BillBizDoubtService doubtService;
	
	@Autowired
	private BillBizMistakeService mistakeService;
	
	@Autowired
	private BillBizFileNotifyService notifyService;

	@Override
	public Page<BillBizSummary> pageBillBizSummary(Integer pageNo,
			Integer pageSize, Map<String, Object> paramMap) {
		Page<BillBizSummary> outPage = new Page<BillBizSummary>(pageNo,pageSize);
		com.fc.pay.common.system.mybatis.Page inPage = new com.fc.pay.common.system.mybatis.Page();
		inPage.setCurrNum(pageNo);
		inPage.setPageSize(pageSize);
		paramMap.put("page", inPage);
		inPage = summaryService.page(paramMap);
		outPage.setTotal(inPage.getTotalItem());
	    List recordList = new ArrayList<BillBizSummary>();
	    recordList.addAll(inPage);
		outPage.setRecords(recordList);
		return outPage;
	}
	
	@Override
	public Page<BillBizBatch> pageBillBizBatch(Integer pageNo, Integer pageSize, Map<String, Object> paramMap) {
		Page<BillBizBatch> outPage = new Page<BillBizBatch>(pageNo,pageSize);
		com.fc.pay.common.system.mybatis.Page inPage = new com.fc.pay.common.system.mybatis.Page();
		inPage.setCurrNum(pageNo);
		inPage.setPageSize(pageSize);
		paramMap.put("page", inPage);
		inPage = batchService.page(paramMap);
		outPage.setTotal(inPage.getTotalItem());
	    List recordList = new ArrayList<BillBizBatch>();
	    recordList.addAll(inPage);
		outPage.setRecords(recordList);
		return outPage;
	}

	@Override
	public Page<BillBizItem> pageBillBizItem(Integer pageNo, Integer pageSize,
			Map<String, Object> paramMap) {
		Page<BillBizItem> outPage = new Page<BillBizItem>(pageNo,pageSize);
		com.fc.pay.common.system.mybatis.Page inPage = new com.fc.pay.common.system.mybatis.Page();
		inPage.setCurrNum(pageNo);
		inPage.setPageSize(pageSize);
		paramMap.put("page", inPage);
		inPage = itemService.page(paramMap);
		outPage.setTotal(inPage.getTotalItem());
	    List recordList = new ArrayList<BillBizItem>();
	    recordList.addAll(inPage);
		outPage.setRecords(recordList);
		return outPage;
	}

	@Override
	public Page<BillBizDoubt> pageBillBizDoubt(Integer pageNo,
			Integer pageSize, Map<String, Object> paramMap) {
		Page<BillBizDoubt> outPage = new Page<BillBizDoubt>(pageNo,pageSize);
		com.fc.pay.common.system.mybatis.Page inPage = new com.fc.pay.common.system.mybatis.Page();
		inPage.setCurrNum(pageNo);
		inPage.setPageSize(pageSize);
		paramMap.put("page", inPage);
		inPage = doubtService.page(paramMap);
		outPage.setTotal(inPage.getTotalItem());
	    List recordList = new ArrayList<BillBizDoubt>();
	    recordList.addAll(inPage);
		outPage.setRecords(recordList);
		return outPage;
	}

	@Override
	public Page<BillBizMistake> pageBillBizMistake(Integer pageNo,
			Integer pageSize, Map<String, Object> paramMap) {
		Page<BillBizMistake> outPage = new Page<BillBizMistake>(pageNo,pageSize);
		com.fc.pay.common.system.mybatis.Page inPage = new com.fc.pay.common.system.mybatis.Page();
		inPage.setCurrNum(pageNo);
		inPage.setPageSize(pageSize);
		paramMap.put("page", inPage);
		inPage = mistakeService.page(paramMap);
		outPage.setTotal(inPage.getTotalItem());
	    List recordList = new ArrayList<BillBizMistake>();
	    recordList.addAll(inPage);
		outPage.setRecords(recordList);
		return outPage;
	}

	@Override
	public Page<BillBizFileNotify> pageBillBizFileNotify(Integer pageNo,
			Integer pageSize, Map<String, Object> paramMap) {
		Page<BillBizFileNotify> outPage = new Page<BillBizFileNotify>(pageNo,pageSize);
		com.fc.pay.common.system.mybatis.Page inPage = new com.fc.pay.common.system.mybatis.Page();
		inPage.setCurrNum(pageNo);
		inPage.setPageSize(pageSize);
		paramMap.put("page", inPage);
		inPage = notifyService.page(paramMap);
		outPage.setTotal(inPage.getTotalItem());
	    List recordList = new ArrayList<BillBizFileNotify>();
	    recordList.addAll(inPage);
		outPage.setRecords(recordList);
		return outPage;
	}
	
	

}
