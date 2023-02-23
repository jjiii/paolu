package com.fc.pay.bill.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.baomidou.mybatisplus.plugins.Page;
import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;

/**
 * 对账数据查询服务测试
 * 
 * @author zhanjq
 *
 */
public class BillBizDataServiceTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory
			.getLogger(BillBizDataServiceTest.class);
	
	@Autowired
	private BillBizDataService dataService;
	
	@Test
	public void pageBillBizSummaryTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("billDate", "20161213");
		Page<BillBizSummary> page = dataService.pageBillBizSummary(1, 30, paramMap);
		log.info("记录总数:"+page.getTotal());
		log.info("每页记录个数:"+page.getSize());
		log.info("总页数:"+page.getPages());	
		log.info("当前页号:"+page.getCurrent());
		log.info("当前页记录个数:"+page.getRecords().size());		
		for(BillBizSummary summary : page.getRecords()){
			log.info("对账汇总记录>>>"+summary.toString());
		}
	}
	
	@Test
	public void pageBillBizBatchTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("payChannel", "alipay");//weixin
		Page<BillBizBatch> page = dataService.pageBillBizBatch(1, 30, paramMap);
		log.info("记录总数:"+page.getTotal());
		log.info("每页记录个数:"+page.getSize());
		log.info("总页数:"+page.getPages());	
		log.info("当前页号:"+page.getCurrent());
		log.info("当前页记录个数:"+page.getRecords().size());		
		for(BillBizBatch batch : page.getRecords()){
			log.info("对账批次记录>>>"+batch.toString());
		}
	}
	
	@Test
	public void pageBillBizItemTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("payChannel", "alipay");//weixin
		Page<BillBizItem> page = dataService.pageBillBizItem(1, 30, paramMap);
		log.info("记录总数:"+page.getTotal());
		log.info("每页记录个数:"+page.getSize());
		log.info("总页数:"+page.getPages());	
		log.info("当前页号:"+page.getCurrent());
		log.info("当前页记录个数:"+page.getRecords().size());		
		for(BillBizItem item : page.getRecords()){
			log.info("账单明细记录>>>"+item.toString());
		}
	}
	
	@Test
	public void pageBillBizDoubtTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("payChannel", "alipay");//weixin
		Page<BillBizDoubt> page = dataService.pageBillBizDoubt(1, 30, paramMap);
		log.info("记录总数:"+page.getTotal());
		log.info("每页记录个数:"+page.getSize());
		log.info("总页数:"+page.getPages());	
		log.info("当前页号:"+page.getCurrent());
		log.info("当前页记录个数:"+page.getRecords().size());		
		for(BillBizDoubt doubt : page.getRecords()){
			log.info("存疑记录>>>"+doubt.toString());
		}
	}
	
	@Test
	public void pageBillBizMistakeTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("payChannel", "alipay");//weixin
		Page<BillBizMistake> page = dataService.pageBillBizMistake(1, 30, paramMap);
		log.info("记录总数:"+page.getTotal());
		log.info("每页记录个数:"+page.getSize());
		log.info("总页数:"+page.getPages());	
		log.info("当前页号:"+page.getCurrent());
		log.info("当前页记录个数:"+page.getRecords().size());		
		for(BillBizMistake mistake : page.getRecords()){
			log.info("差错记录>>>"+mistake.toString());
		}
	}
	
	@Test
	public void pageBillBizFileNotifyTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("payChannel", "alipay");//weixin
		Page<BillBizFileNotify> page = dataService.pageBillBizFileNotify(1, 30, paramMap);
		log.info("记录总数:"+page.getTotal());
		log.info("每页记录个数:"+page.getSize());
		log.info("总页数:"+page.getPages());	
		log.info("当前页号:"+page.getCurrent());
		log.info("当前页记录个数:"+page.getRecords().size());
		for(BillBizFileNotify notify : page.getRecords()){
			log.info("文件通知记录>>>"+notify.toString());
		}
	}

}
