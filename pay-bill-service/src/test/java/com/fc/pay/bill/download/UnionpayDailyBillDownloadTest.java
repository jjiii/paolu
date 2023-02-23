package com.fc.pay.bill.download;

import java.util.Calendar;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.config.UnionpayConfig;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.utils.BatchUtil;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.trade.entity.MerchantAppConfig;

public class UnionpayDailyBillDownloadTest extends BaseTestng{
	
	private static final Logger log = LoggerFactory.getLogger(UnionpayDailyBillDownloadTest.class);
	
	/**
	 * 业务日对账账单下载组件
	 */
	@Resource(name="unionpayDailyBillDownload")
	private OutsideDailyBillDownload unionpayDailyBillDownload;
	
	@Test
	public void downloadUnionpayDailyBillTest() throws Exception{
		
		String mchId = "700000000000001";
		
		/** 初始化银联商户私有属性 */
//		MerchantAppConfig appConfig = new MerchantAppConfig();
//		appConfig.setChannel(PayChannelEnum.unionpay.name());
//		appConfig.setChannelMerchantId(mchId);
//		appConfig.setCertPath(BillUtil.readCertBasicPath()+"acp_test_sign.pfx");
//		appConfig.setCertPwd("000000");
//		appConfig.setCertEncPath(BillUtil.readCertBasicPath()+"acp_test_enc.cer");
//		appConfig.setCertValidate(BillUtil.readCertBasicPath());
				
		/** 初始化对账批次信息 */
		BillBizBatch batch = new BillBizBatch();
		batch.setBatchNo(BatchUtil.makeBatchNo());
		batch.setPayChannel(PayChannelEnum.unionpay.name());
		batch.setChannelMerchantId(mchId);
		Calendar calendar=Calendar.getInstance(); 
		calendar.set(Calendar.YEAR, 2016);//设置当前年份
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 19);
		batch.setBillDate(calendar.getTime());
		String merId = batch.getChannelMerchantId();
		String settleDate = DateUtil.makeMMddDateFormat(batch.getBillDate());//eg: "0119";
		
		log.info("merId>>>"+merId);
		log.info("settleDate>>>"+settleDate);
		
		/** 下载账单 */
		try {			
			unionpayDailyBillDownload.downloadBizBill(batch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("账单存储路径>>>"+batch.getChannelBillStorePath());
		
	}

}
