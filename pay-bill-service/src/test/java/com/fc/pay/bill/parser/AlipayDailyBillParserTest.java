package com.fc.pay.bill.parser;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.constant.BillConstant;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.parser.impl.AlipayDailyBillParser;
import com.fc.pay.bill.parser.impl.UnionpayDailyBillParser;
import com.fc.pay.bill.parser.impl.WeixinDailyBillParser;
import com.fc.pay.bill.utils.BatchUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;
import com.fc.pay.common.core.enums.PayChannelEnum;

public class AlipayDailyBillParserTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory.getLogger(AlipayDailyBillParserTest.class);
	
	@Resource(name="alipayDailyBillParser")
	private AlipayDailyBillParser alipayDailyBillParser;

	@Test
	public void alipayDailyBillParserTest(){
		//String detailBillPath = "D:/drilldata/zip/target/alipay/20881021788626270156_20161117_业务明细.csv";
		//String summaryBillPath = "D:/drilldata/zip/target/alipay/20881021788626270156_20161117_业务明细(汇总).csv";
		
		String detailBillPath = "F:/2017010904939710_20170323.csv/20886115458544250156_20170323_业务明细.csv";
		String summaryBillPath = "F:/2017010904939710_20170323.csv/20886115458544250156_20170323_业务明细(汇总).csv";

		/*app_id*/
		String appId = "2016101000649369";		
		
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
		batch.setBillDate(DateUtil.addDay(new Date(), -1));
		//batch.setChannelBillStorePath("F:/data/bill/daily/outside/alipay/2016101000649369/20161220/2016101000649369_20161220.csv.zip");//从下载功能取得的账单文件
		batch.setChannelBillStorePath("F:/2017010904939710_20170323.csv.zip");
		//batch.setChannelBillStorePath("D:/20881021788626270156_20161220.csv.zip");//浏览器URL直接下载账单
		try {
			Map<String, List<OutsideDailyBizBillItem>> dataMap = alipayDailyBillParser.parseBizBill(batch);
			log.info("批次记录："+batch);
			
		    List<OutsideDailyBizBillItem> payList = dataMap.get(BillConstant.KEY_PAY);
		    for(OutsideDailyBizBillItem item : payList){
		    	log.info("支付记录>>>"+item.toString());
		    }
		    List<OutsideDailyBizBillItem> refundList = dataMap.get(BillConstant.KEY_REFUND);
		    for(OutsideDailyBizBillItem item : refundList){
		    	log.info("退款记录>>>"+item.toString());
		    }		    
		    log.info(System.getProperty("file.encoding"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
