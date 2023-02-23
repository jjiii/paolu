package com.fc.pay.bill.parser;

import java.util.Calendar;
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

public class WeixinDailyBillParserTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory.getLogger(WeixinDailyBillParserTest.class);
	
	@Resource(name="weixinDailyBillParser")
	private WeixinDailyBillParser weixinDailyBillParser;
	
	@Test
	public void weixinDailyBillParserTest(){
		String detailBillPath = "D:/drilldata/zip/target/weixin/WEIXIN_123456789_20161122.txt";
		String summaryBillPath = "";
		BillBizBatch batch = new BillBizBatch();
		batch.setBatchNo(BatchUtil.makeBatchNo());
		batch.setPayChannel(PayChannelEnum.weixin.name());
		batch.setChannelMerchantId("111111");
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
		String channelBillStorePath = "F:/data/bill/daily/outside/weixin/mchid/appid/20161221/mchid_appid_20161221.zip";
		batch.setChannelBillStorePath(channelBillStorePath);
		try {
			Map<String, List<OutsideDailyBizBillItem>> dataMap = weixinDailyBillParser.parseBizBill(batch);
			log.info("批次记录："+batch);
			
		    List<OutsideDailyBizBillItem> payList = dataMap.get(BillConstant.KEY_PAY);
		    for(OutsideDailyBizBillItem item : payList){
		    	log.info("支付记录>>>"+item.toString());
		    }
		    List<OutsideDailyBizBillItem> refundList = dataMap.get(BillConstant.KEY_REFUND);
		    for(OutsideDailyBizBillItem item : refundList){
		    	log.info("退款记录>>>"+item.toString());
		    }		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
