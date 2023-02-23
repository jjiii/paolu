package com.fc.pay.bill.business;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.config.UnionpayConfig;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.service.BillBizBatchService;
import com.fc.pay.bill.service.BillBizSummaryService;
import com.fc.pay.bill.utils.BatchUtil;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.trade.entity.MerchantAppConfig;

public class BillBizCheckBatchBusinessTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizCheckBatchBusinessTest.class);
	
	@Autowired
	private BillBizCheckBatchBusiness billBizCheckBatchBusiness;
	
	@Autowired
	private BillBizBatchService billBizBatchService;
	
	@Autowired
	private BillBizSummaryService billBizSummaryService;
	
	//@Test
	public void checkBatchTest(){
		BillBizSummary summary = billBizSummaryService.get(Long.valueOf(13));
		BillBizBatch batch = billBizBatchService.get(Long.valueOf(40));
		
		/** 初始化银联商户私有属性 */
//		MerchantAppConfig appConfig = new MerchantAppConfig();
//		appConfig.setChannel(PayChannelEnum.unionpay.name());
//		appConfig.setChannelMerchantId("700000000000001");
//		appConfig.setCertPath(BillUtil.readCertBasicPath()+"acp_test_sign.pfx");
//		appConfig.setCertPwd("000000");
//		appConfig.setCertEncPath(BillUtil.readCertBasicPath()+"acp_test_enc.cer");
//		appConfig.setCertValidate(BillUtil.readCertBasicPath());
		//初始化银联商户私有属性
//		UnionpayConfig.getConfig().initPrivateProperties(appConfig);
				
		/** 初始化对账批次信息 */
		batch.setPayChannel(PayChannelEnum.unionpay.name());
		batch.setChannelMerchantId("700000000000001");
		Calendar calendar=Calendar.getInstance(); 
		calendar.set(Calendar.YEAR, 2016);//设置当前年份
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 19);
		batch.setBillDate(calendar.getTime());
		String merId = batch.getChannelMerchantId();
		String settleDate = DateUtil.makeMMddDateFormat(batch.getBillDate());//eg: "0119";
		
		log.info("merId>>>"+merId);
		log.info("settleDate>>>"+settleDate);
		
		batch.setHandleStatus(BatchStatusEnum.init.name());
		batch.setHandleRemark(BatchStatusEnum.init.getDesc());
		try{
			billBizCheckBatchBusiness.checkBatch(batch, summary);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void canExecuteBillBizBatchTest(){
		BillBizBatch batch = billBizBatchService.get(Long.valueOf(12));
		boolean canExecute = billBizCheckBatchBusiness.canExecuteBillBizBatch(batch);
		log.debug("打印canExecute>>>"+canExecute);
	}
	
	@Test
	public void test(){
		BillBizBatch batch = new BillBizBatch();
		batch.setRefundAmount(BigDecimal.ZERO);
		batch.setRefundCount(0);
		batch.setMistakeCount(0);
		batch.setPayChannel("alipay");
		batch.setChannelAppId("2017010904939710");
		batch.setChannelMerchantId(null);
		batch.setBillDate(DateUtil.addDay(new Date(), -2));
		
		BillBizSummary summary = new BillBizSummary();
		
		try{
		billBizCheckBatchBusiness.checkBatch(batch, summary);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
