package com.fc.pay.bill.download;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.bill.config.WeixinConfig;
import com.fc.pay.trade.entity.MerchantAppConfig;

public class WeixinDailyBillDownloadTest extends BaseTestng{
	
	private static final Logger log = LoggerFactory.getLogger(WeixinDailyBillDownloadTest.class);
	
	/**
	 * 业务日对账账单下载组件
	 */
	@Resource(name="weixinDailyBillDownload")
	private OutsideDailyBillDownload weixinDailyBillDownload;
	
	@Test
	public void downloadWeixinDailyBillTest(){		
		/** 初始化银联商户私有属性 */
//		MerchantAppConfig appConfig = new MerchantAppConfig();
//		appConfig.setChannel(PayChannelEnum.weixin.name());
//		appConfig.setPubKey("111111");
//		appConfig.setChannelMerchantId("222222");
//		appConfig.setSubMchId("222222");
//		appConfig.setChannelAppId("333333");
//		appConfig.setCertPath("d:/abcdef/");
//		appConfig.setCertPwd("000000");		
//		WeixinConfig.getConfig().initPrivateProperties(appConfig);
				
		/** 初始化对账批次信息 */
		BillBizBatch batch = new BillBizBatch();
		batch.setPayChannel(PayChannelEnum.weixin.name());
		batch.setChannelAppId("333333");
		batch.setChannelMerchantId("222222");
		/**	构造指定日期 */
		/* 直接指定
		Calendar calendar=Calendar.getInstance(); 
		calendar.set(Calendar.YEAR, 2016);//2016年
		calendar.set(Calendar.MONTH, 3);//4月
		calendar.set(Calendar.DAY_OF_MONTH, 28);//28日
		batch.setBillDate(calendar.getTime());
		*/
		/* 前推几天 */
		batch.setBillDate(DateUtil.addDay(new Date(), -2));
		String merId = batch.getChannelMerchantId();
		String billDate = DateUtil.makeDefaultDateFormat(batch.getBillDate());//eg: "20161121";
		log.info("merId>>>"+merId);
		log.info("billDate>>>"+billDate);

		try {
			weixinDailyBillDownload.downloadBizBill(batch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("账单存储路径>>>"+batch.getChannelBillStorePath());

	}

}
