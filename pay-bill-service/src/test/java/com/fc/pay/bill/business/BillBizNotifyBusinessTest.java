package com.fc.pay.bill.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.service.BillBizMistakeService;

public class BillBizNotifyBusinessTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizNotifyBusinessTest.class);
	
	@Autowired
	private BillBizNotifyBusiness billBizNotifyBusiness;
	
	@Autowired
	private BillBizMistakeService billBizMistakeService;
	
	@Test
	public void notifyMistakeHandledTest(){
		BillBizMistake mistake = billBizMistakeService.get(Long.valueOf(20));
		mistake.setMerchantAppCode("1481083760951");//手工调整
		log.debug("差错记录>>>"+mistake.toString());
		try {			
			billBizNotifyBusiness.notifyMistakeHandled(mistake);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void notifyBillDownloadTest(){
		//String downloadNotifyUrl = "https://www.baidu.com";
		String yyyyMMdd = "20161222";
		try {
			BillBizFileNotify notify = new BillBizFileNotify();
			billBizNotifyBusiness.notifyBillDownload(notify , yyyyMMdd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
