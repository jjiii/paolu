package com.fc.pay.bill.business;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;

public class BillBizFileBusinessTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizFileBusinessTest.class);
	
	@Autowired
	private BillBizFileBusiness billBizFileBusiness;
	
	
	@DataProvider(name="makeMerchantAppBillTestData")
	public Object[][] makeMerchantAppBillTestData(){
		
		Calendar calendar=Calendar.getInstance(); 
		calendar.set(Calendar.YEAR, 2016);//设置当前年份
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DAY_OF_MONTH, 14);
		
		Object[] kuaishouConfig = new Object[]{"01","0101",calendar.getTime()};
		
		Object[][] data = new Object[][]{kuaishouConfig};
		
		return data;
	}
	
	@Test(dataProvider="makeMerchantAppBillTestData")
	public void makeMerchantAppBillTest(String merchantCode, String merchantAppCode, Date billDate){
		try {
			billBizFileBusiness.makeMerchantAppBill(merchantCode, merchantAppCode, billDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
