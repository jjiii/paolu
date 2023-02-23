package com.fc.pay.bill.business;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.config.AlipayConfig;
import com.fc.pay.bill.config.UnionpayConfig;
import com.fc.pay.bill.config.WeixinConfig;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.utils.BatchUtil;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.utils.PathUtil;
import com.fc.pay.trade.entity.MerchantAppConfig;

public class BillBizDownloadBusinessTest  extends BaseTestng {
	
private static final Logger log = LoggerFactory.getLogger(BillBizParserBusinessTest.class);
	
	@Autowired
	private BillBizDownloadBusiness billBizDownloadBusiness;
	
	@DataProvider(name="provideBillBizBatch")
	public Object[][] provideBillBizBatch(){

		BillBizBatch alipayBatch = new BillBizBatch();	
		/** 初始化银联商户私有属性 */
		MerchantAppConfig alipayAppConfig = new MerchantAppConfig();
		alipayAppConfig.setChannel(PayChannelEnum.alipay.name());
		alipayAppConfig.setChannelAppId("2016101000649369");
		alipayAppConfig.setPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB");
		alipayAppConfig.setPriKey("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJrX943qCctdyB+3KQEH2yTqgZdTlP4Q+WsfXezVaE2MmVquGsmQ2V3F1KZYIpYBpdO2pnO3D5IBMMTzmX87W2hxNVZM8Vx/WZ+lDyZvyPiyfBQtcMDafy/ceb6evjZdnsU2ks91d9mUc12HzTTez2ZtBwS1UiXLFprIFR4sG2glAgMBAAECgYBM00JcAvaBFDKqqbGKdV9hXYiWkD2oILvfTlzHmMp7T3r25tMbluaCBBmRvsDKNF8WP9UuLHFpO3X7AfHyknyy+xGKGPA5gssIvaOk/lWhyqv4xsxggUXKT49aY3SF7hsCkrIPJm7sNd2hNKnPeYFdPva8LzLDTsJy6AvdCfQ/MQJBANUluv8Mu5yLFC5x78K9M/I/KMQa8DuHpCjVlNrasSd1yFg4sitgg1l594EHPDcsJTGzCRrVORozn0r8JK/i+p8CQQC5+XVeQDA/AvuiGg8zXvhNHsEgjDUZfCmf2d+cR7e0T3dD6Mp+GZVdshN3pinBrNaYu+nrzyGF2r/y41W2J+q7AkB8Jp72AlehBg16RBkwZ/5C4vD+0OYO9qHyuv0aQPmhD2Tjphp5U50OWBGHEUzMoiUD/tGV1I6PKXRmO9murVnnAkBvWq/tHFAHGrki6amaX84bF0QaQfl1ZgPiY+lhQQv9GevWrKe6c4UdEghYBxVPkzb3QuUgvehbpoxyWa6zoBkLAkB2EK7dpIozIy5Y1TEiqnwI9rPlTdcwVqauC+Vf6E5HBTB4niZTw074hdaPyW4zcXA3ZIZzJ8f7fP80869F8SMn");

		/** 初始化对账批次信息 */
		alipayBatch.setBatchNo(BatchUtil.makeBatchNo());
		alipayBatch.setPayChannel(PayChannelEnum.alipay.name());
		alipayBatch.setChannelAppId("2016101000649369");
		alipayBatch.setChannelMerchantId(null);
		alipayBatch.setBillDate(DateUtil.addDay(new Date(), -2));

		BillBizBatch weixinBatch = new BillBizBatch();
		weixinBatch.setBatchNo(BatchUtil.makeBatchNo());
		weixinBatch.setPayChannel(PayChannelEnum.weixin.name());
		weixinBatch.setChannelMerchantId("111111");
		MerchantAppConfig weixinAppConfig = new MerchantAppConfig();
		weixinAppConfig.setChannel(PayChannelEnum.weixin.name());
		weixinAppConfig.setPubKey("111111");
		weixinAppConfig.setChannelMerchantId("222222");
		weixinAppConfig.setSubMchId("222222");
		weixinAppConfig.setChannelAppId("333333");
		weixinAppConfig.setCertPath("d:/abcdef/");
		weixinAppConfig.setCertPwd("000000");		
//		WeixinConfig.getConfig().initPrivateProperties(weixinAppConfig);				
		/** 初始化对账批次信息 */
		weixinBatch.setPayChannel(PayChannelEnum.weixin.name());
		weixinBatch.setChannelAppId(weixinAppConfig.getChannelAppId());
		weixinBatch.setChannelMerchantId(weixinAppConfig.getChannelMerchantId());
		weixinBatch.setBillDate(DateUtil.addDay(new Date(), -2));
		
		BillBizBatch unionpayBatch = new BillBizBatch();
		/** 初始化银联商户私有属性 */
		MerchantAppConfig unionAppConfig = new MerchantAppConfig();
		unionAppConfig.setChannel(PayChannelEnum.unionpay.name());
		unionAppConfig.setChannelMerchantId("700000000000001");
		unionAppConfig.setCertPath(PathUtil.readCertBasicPath()+"acp_test_sign.pfx");
		unionAppConfig.setCertPwd("000000");
		unionAppConfig.setCertEncPath(PathUtil.readCertBasicPath()+"acp_test_enc.cer");
		unionAppConfig.setCertValidate(PathUtil.readCertBasicPath());
		//初始化银联商户私有属性
//		UnionpayConfig.getConfig().initPrivateProperties(unionAppConfig);	
		/** 初始化对账批次信息 */
		unionpayBatch.setBatchNo(BatchUtil.makeBatchNo());
		unionpayBatch.setPayChannel(PayChannelEnum.unionpay.name());
		unionpayBatch.setChannelMerchantId(unionAppConfig.getChannelMerchantId());
		Calendar calendar=Calendar.getInstance(); 
		calendar.set(Calendar.YEAR, 2016);//设置当前年份
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 19);
		unionpayBatch.setBillDate(calendar.getTime());

		return new Object[][]{{alipayBatch}, {weixinBatch}, {unionpayBatch}};
	}
	
	@Test(dataProvider="provideBillBizBatch")
	public void downloadOutsideBizBillTest(BillBizBatch batch) {
		try {
			billBizDownloadBusiness.downloadOutsideBizBill(batch);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		log.info("账单存储路径>>>"+batch.getChannelBillStorePath());
	}
	

}
