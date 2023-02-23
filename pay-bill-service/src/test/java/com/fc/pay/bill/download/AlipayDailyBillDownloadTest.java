package com.fc.pay.bill.download;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.utils.BatchUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.bill.config.AlipayConfig;
import com.fc.pay.trade.entity.MerchantAppConfig;

public class AlipayDailyBillDownloadTest extends BaseTestng{
	
	private static final Logger log = LoggerFactory.getLogger(AlipayDailyBillDownloadTest.class);
		
	/*
	 * 公钥私钥说明
	 * 1.使用支付宝工具生成应用的公钥与私钥
	 * 2.沙箱环境配置应用的公钥与私钥
	 * 3.alipayPublicKey[用于本系统验证数据是否来自支付宝]，复制沙箱环境“回显的支付宝支付宝公钥”作为alipayPublicKey参数值，而非应用的公钥。
	 * 4.appPrivateKey[用于支付宝验证数据是否来自应用]，复制应用私钥作为appPrivateKey参数值。
	 * 
	 */
	
	/**
	 * 业务日对账账单下载组件
	 */
	@Resource(name="alipayDailyBillDownload")
	private OutsideDailyBillDownload alipayDailyBillDownload;
	
	@Test
	public void downloadAlipayDailyBillTest(){		
		/*app_id*/
		//String appId = "2016101000649369";		
		String appId = "2017010904939710";		
		/*公钥*/
		String alipayPublicKey = 
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
		//"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCa1/eN6gnLXcgftykBB9sk6oGXU5T+EPlrH13s1WhNjJlarhrJkNldxdSmWCKWAaXTtqZztw+SATDE85l/O1tocTVWTPFcf1mfpQ8mb8j4snwULXDA2n8v3Hm+nr42XZ7FNpLPdXfZlHNdh8003s9mbQcEtVIlyxaayBUeLBtoJQIDAQAB";
		/*私钥*/
		String appPrivateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJrX943qCctdyB+3KQEH2yTqgZdTlP4Q+WsfXezVaE2MmVquGsmQ2V3F1KZYIpYBpdO2pnO3D5IBMMTzmX87W2hxNVZM8Vx/WZ+lDyZvyPiyfBQtcMDafy/ceb6evjZdnsU2ks91d9mUc12HzTTez2ZtBwS1UiXLFprIFR4sG2glAgMBAAECgYBM00JcAvaBFDKqqbGKdV9hXYiWkD2oILvfTlzHmMp7T3r25tMbluaCBBmRvsDKNF8WP9UuLHFpO3X7AfHyknyy+xGKGPA5gssIvaOk/lWhyqv4xsxggUXKT49aY3SF7hsCkrIPJm7sNd2hNKnPeYFdPva8LzLDTsJy6AvdCfQ/MQJBANUluv8Mu5yLFC5x78K9M/I/KMQa8DuHpCjVlNrasSd1yFg4sitgg1l594EHPDcsJTGzCRrVORozn0r8JK/i+p8CQQC5+XVeQDA/AvuiGg8zXvhNHsEgjDUZfCmf2d+cR7e0T3dD6Mp+GZVdshN3pinBrNaYu+nrzyGF2r/y41W2J+q7AkB8Jp72AlehBg16RBkwZ/5C4vD+0OYO9qHyuv0aQPmhD2Tjphp5U50OWBGHEUzMoiUD/tGV1I6PKXRmO9murVnnAkBvWq/tHFAHGrki6amaX84bF0QaQfl1ZgPiY+lhQQv9GevWrKe6c4UdEghYBxVPkzb3QuUgvehbpoxyWa6zoBkLAkB2EK7dpIozIy5Y1TEiqnwI9rPlTdcwVqauC+Vf6E5HBTB4niZTw074hdaPyW4zcXA3ZIZzJ8f7fP80869F8SMn";
		//static String privateKey = "";
		/**正式网关*/
		String productGateway = "https://openapi.alipay.com/gateway.do";		
		/**沙箱网关*/
		String sandboxGateway = "https://openapi.alipaydev.com/gateway.do";
					
		/** 初始化对账批次信息 */
		BillBizBatch batch = new BillBizBatch();
		batch.setBatchNo(BatchUtil.makeBatchNo());
		batch.setPayChannel(PayChannelEnum.alipay.name());
		batch.setChannelAppId(appId);
		batch.setChannelMerchantId(null);
		/**	构造指定日期 */
		/* 直接指定
		Calendar calendar=Calendar.getInstance(); 
		calendar.set(Calendar.YEAR, 2016);//2016年
		calendar.set(Calendar.MONTH, 3);//4月
		calendar.set(Calendar.DAY_OF_MONTH, 28);//28日
		batch.setBillDate(calendar.getTime());
		*/
		/* 前推几天 */
		batch.setBillDate(DateUtil.addDay(new Date(), -3));
		
		log.info("appId>>>"+batch.getChannelAppId());
		log.info("billDate>>>"+DateUtil.makeHyphenDateFormat(batch.getBillDate()));//eg: "2016-11-21";
		
		try {
			alipayDailyBillDownload.downloadBizBill(batch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("账单存储路径>>>"+batch.getChannelBillStorePath());
	}

}
