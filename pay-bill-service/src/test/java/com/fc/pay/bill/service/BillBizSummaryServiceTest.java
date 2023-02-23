package com.fc.pay.bill.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.business.BillBizEntityBusiness;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.system.mybatis.Page;

public class BillBizSummaryServiceTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory
			.getLogger(BillBizSummaryServiceTest.class);
	
	@Autowired
	private BillBizSummaryService summaryService;
	
	@Autowired
	private BillBizEntityBusiness entityBusiness;
	
	@DataProvider
	public Object[][] addTestData(){		
		BillBizSummary summary_1 = entityBusiness.makeBillBizSummary(DateUtil.addDay(new Date(), -1), 3, 2);	
//		BillBizSummary summary_2 = entityBusiness.makeBillBizSummary(DateUtil.addDay(new Date(), -2), 3, 2);	
//		BillBizSummary summary_3 = entityBusiness.makeBillBizSummary(DateUtil.addDay(new Date(), -3), 3, 2);	
//		BillBizSummary summary_4 = entityBusiness.makeBillBizSummary(DateUtil.addDay(new Date(), -4), 3, 2);	
//		BillBizSummary summary_5 = entityBusiness.makeBillBizSummary(DateUtil.addDay(new Date(), -5), 3, 2);	
//		BillBizSummary summary_6 = entityBusiness.makeBillBizSummary(DateUtil.addDay(new Date(), -6), 3, 2);	
//		return new Object[][]{{summary_1},{summary_2},{summary_3},{summary_4},{summary_5},{summary_6}};
//		return new Object[][]{{summary_1},{summary_2}};
		return new Object[][]{{summary_1}};
	}
	
	//@Test(dataProvider="addTestData")
	public void addTest(BillBizSummary summary){
		summaryService.add(summary);
		log.info("新增对账汇总："+summary.toString());
	}
	

	@DataProvider
	public static Object[][] getTestData(){	
		return new Object[][]{{Long.valueOf(17)},{Long.valueOf(18)}};
	}
	
	//@Test(dataProvider="getTestData")
	public void getTest(Long id){
		BillBizSummary summary = summaryService.get(id);
		log.info("查询对账汇总："+summary.toString());
	}
	
	//@Test
	public void getByBillDateTest(){
		Date billDate = DateUtil.addDay(new Date(), -1);
		log.info("账单日期："+billDate);		
		BillBizSummary summary = summaryService.getByBillDate(billDate);
		log.info("根据账单日期查询对账汇总："+summary.toString());
	}
	
	@DataProvider
	public static Object[][] modifyBatchRunSuccessCountTestData(){	
		return new Object[][]{{Long.valueOf(17)},{Long.valueOf(18)}};
	}
	
	//@Test(dataProvider="modifyBatchRunSuccessCountTestData")
	public void modifyBatchRunSuccessCountTest(Long summaryId){
		summaryService.modifyBatchRunSuccessCount(summaryId, 2);
	}	
	
	@DataProvider
	public static Object[][] modifyBillMakeSuccessCountTestData(){	
		return new Object[][]{{Long.valueOf(15)},{Long.valueOf(16)}};
	}
	
	//@Test(dataProvider="modifyBillMakeSuccessCountTestData")
	public void modifyBillMakeSuccessCountTest(Long summaryId){
		summaryService.modifyBillMakeSuccessCount(summaryId, 2);
	}
	
	@DataProvider
	public static Object[][] modifyDownloadNotifySuccessCountTestData(){	
		return new Object[][]{{Long.valueOf(13)},{Long.valueOf(14)}};
	}
	
	//@Test(dataProvider="modifyDownloadNotifySuccessCountTestData")
	public void modifyDownloadNotifySuccessCountTest(Long summaryId){
		summaryService.modifyDownloadNotifySuccessCount(summaryId, 2);
	}
	
	//@Test
	public void pageTest(){		
		Page page = new Page();
		page.setCurrNum(1);
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("page", page);
		paramMap.put("billDate", "20161213");
		page = summaryService.page(paramMap);
		log.info("记录总数:"+page.getTotalItem());
		log.info("每页记录个数:"+page.getPageSize());
		log.info("总页数:"+page.totalPages());	
		log.info("当前页号:"+page.getCurrNum());
		log.info("当前页记录个数:"+page.size());		
	}
	
	//@Test
	public void listTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("billDate", "20161213");
		List<BillBizSummary> list = summaryService.list(paramMap);
		log.info("条件查询记录总数:"+list.size());
		for(BillBizSummary summary : list){
			log.info(summary.toString());
		}		
	}
	
	//@Test
	public void findBillDate(){		
		List<BillBizSummary> list = summaryService.listByBatchCheckFail();
		log.info("跑批未完成记录总数:"+list.size());
		for(BillBizSummary summary : list){
			log.info(DateUtil.makeDefaultDateFormat(summary.getBillDate()));
		}		
		Date maxBillDate = summaryService.findMaxBillDate();
		log.info("最大对账日期:"+DateUtil.makeDefaultDateFormat(maxBillDate));
	}
	
	@Test
	public void printBillDateList(){
		Set<String> toCheckBillDateSet = new HashSet<String>();
		int billPeriod = Integer.valueOf(BillUtil.readBillPeriod().trim());
		/** 查找跑批未完成的对账汇总 */
		List<BillBizSummary> batchFailSummaryList = summaryService.listByBatchCheckFail();
		if(batchFailSummaryList!=null && !batchFailSummaryList.isEmpty()){
			log.info("跑批未完成记录总数:"+batchFailSummaryList.size());
			for(BillBizSummary summary : batchFailSummaryList){
				log.info(DateUtil.makeDefaultDateFormat(summary.getBillDate()));
				toCheckBillDateSet.add(DateUtil.makeDefaultDateFormat(summary.getBillDate()));
			}
		}else{
			log.info("不存在跑批未完成的情况");
		}			
		/** 查找断档的对账汇总（系统停机） */
		Date maxHistoryBillDate = summaryService.findMaxBillDate();
		log.info("最大对账日期:"+DateUtil.makeDefaultDateFormat(maxHistoryBillDate));			
		Date newestBillDate = DateUtil.addDay(new Date(), -billPeriod);
		//存在断档情况
		if(!DateUtils.isSameDay(maxHistoryBillDate, newestBillDate)){
			/** 存在断档， */
			//toCheckBillDateSet.add(DateUtil.makeDefaultDateFormat(newestBillDate));
			//填补日期空挡
			int intervalDays = DateUtil.intervalDays(maxHistoryBillDate, newestBillDate);
			if(intervalDays>0){
				/**
				for(int i=1; i<=intervalDays; i++){
					toCheckBillDateSet.add(DateUtil.makeDefaultDateFormat(DateUtil.addDay(maxHistoryBillDate, i)));
				}
				*/
				log.info("intervalDays取值"+intervalDays);
				for(int i=billPeriod; i<=intervalDays;){
					toCheckBillDateSet.add(DateUtil.makeDefaultDateFormat(DateUtil.addDay(maxHistoryBillDate, i)));
					i=i+billPeriod;
					//log.info("i取值"+i);
				}
			}
		}
		
		List<String> toCheckBillDateList = new ArrayList<String>(toCheckBillDateSet);
		Collections.sort(toCheckBillDateList);
		
		log.info("打印账单日期列表");
		for(String billDate : toCheckBillDateList){
			log.info(billDate);
		}
	}
	
	

}
