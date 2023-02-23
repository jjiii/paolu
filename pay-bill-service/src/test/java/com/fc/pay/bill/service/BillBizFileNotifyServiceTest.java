package com.fc.pay.bill.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.business.BillBizEntityBusiness;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.enums.BillMakeStatusEnum;
import com.fc.pay.bill.enums.MistakeNotifyStatusEnum;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.MerchantApp;

public class BillBizFileNotifyServiceTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory
			.getLogger(BillBizFileNotifyServiceTest.class);
	
	@Autowired
	private BillBizFileNotifyService fnService;
	
	@Autowired
	private BillBizEntityBusiness entityBusiness;
	
	//@DataProvider
	public Object[][] addTestData(){
		MerchantApp merchantApp = new MerchantApp();
		merchantApp.setMerchantCode("fc");
		merchantApp.setMerchantName("枫车");
		merchantApp.setMerchantAppCode("kuaishou");
		merchantApp.setMerchantAppName("快手");
		merchantApp.setDownloadNotifyUrl("https://kuaishou.carisok.com/bill/download/notify.php");
		BillBizFileNotify fn_1 = entityBusiness.makeBillBizFileNotify(merchantApp , DateUtil.addDay(new Date(), -1));	
		BillBizFileNotify fn_2 = entityBusiness.makeBillBizFileNotify(merchantApp , DateUtil.addDay(new Date(), -2));
		BillBizFileNotify fn_3 = entityBusiness.makeBillBizFileNotify(merchantApp , DateUtil.addDay(new Date(), -3));
		BillBizFileNotify fn_4 = entityBusiness.makeBillBizFileNotify(merchantApp , DateUtil.addDay(new Date(), -4));
		BillBizFileNotify fn_5 = entityBusiness.makeBillBizFileNotify(merchantApp , DateUtil.addDay(new Date(), -5));
		BillBizFileNotify fn_6 = entityBusiness.makeBillBizFileNotify(merchantApp , DateUtil.addDay(new Date(), -6));
		return new Object[][]{{fn_1},{fn_2},{fn_3},{fn_4},{fn_5},{fn_6}};
	}
	
	//@Test(dataProvider="addTestData")
	public void addTest(BillBizFileNotify fn){
		fnService.add(fn);
		log.info("新增账单文件通知："+fn.toString());
	}
	
	//@Test
	public void addBatchTest(){
		MerchantApp merchantApp = new MerchantApp();
		merchantApp.setMerchantCode("fc");
		merchantApp.setMerchantName("枫车");
		merchantApp.setMerchantAppCode("shangcheng");
		merchantApp.setMerchantAppName("商城");
		merchantApp.setDownloadNotifyUrl("https://mall.carisok.com/bill/download/notify.php");
		List<BillBizFileNotify> list = new ArrayList<BillBizFileNotify>();
		for(int i=7;i<21;i++){
			log.info("序号："+i);
			list.add(entityBusiness.makeBillBizFileNotify(merchantApp , DateUtil.addDay(new Date(), -i)));
		}
		
		log.info("批量新增账单文件通知个数："+fnService.addBatch(list).size());
	}

	//@DataProvider
	public static Object[][] getTestData(){	
		return new Object[][]{{Long.valueOf(1)},{Long.valueOf(2)}};
	}
	
	//@Test(dataProvider="getTestData")
	public void getTest(Long id){
		BillBizFileNotify fn = fnService.get(id);
		log.info("查询账单文件通知："+fn.toString());
	}
	
	//@Test(dataProvider="getTestData")
	public void modifyFileFieldTest(Long fnId){
		String filePath = "/data/pay/bill/inside/fc/kuaishou/xxxx.zip";
		String fileStatus = BillMakeStatusEnum.success.name();
		String fileRemark = "账单生成成功";
		fnService.modifyFileField(fnId, filePath, fileStatus, fileRemark);
	}	
	
	//@Test(dataProvider="getTestData")
	public void modifyDownloadNotifySuccessCountTest(Long fnId){
		String notifyUrl = "https://kuaishou.carisok.com/bill/download/notify.php";
		String notifyStatus = MistakeNotifyStatusEnum.send_fail.name();
		String notifyRemark = "发送下载通知失败";
		fnService.modifyNotifyField(fnId, notifyUrl, notifyStatus, notifyRemark);
	}

	@Test
	public void pageTest(){		
		Page page = new Page();
		page.setCurrNum(1);
		page.setPageSize(10);
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("page", page);
		paramMap.put("merchantAppCode", "shangcheng");
		page = fnService.page(paramMap);
		log.info("记录总数:"+page.getTotalItem());
		log.info("每页记录个数:"+page.getPageSize());
		log.info("总页数:"+page.totalPages());	
		log.info("当前页号:"+page.getCurrNum());
		log.info("当前页记录个数:"+page.size());
	}
	
	@Test
	public void listTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("merchantAppCode", "shangcheng");
		List<BillBizFileNotify> list = fnService.list(paramMap);
		log.info("条件查询记录总数:"+list.size());
		for(BillBizFileNotify fn : list){
			log.info(fn.toString());
		}		
	}
	
}
