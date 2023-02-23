package com.fc.pay.bill.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.constant.BillConstant;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.utils.BatchUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;
import com.fc.pay.common.core.enums.PayChannelEnum;

public class BillBizParserBusinessTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizParserBusinessTest.class);
	
	@Autowired
	private BillBizParserBusiness billBizParserBusiness;
	
	
	@DataProvider(name="provideBillBizBatch")
	public Object[][] provideBillBizBatch(){

		BillBizBatch alipayBatch = new BillBizBatch();
		alipayBatch.setBatchNo(BatchUtil.makeBatchNo());
		alipayBatch.setPayChannel(PayChannelEnum.alipay.name());
		alipayBatch.setChannelMerchantId(null);		
		alipayBatch.setChannelAppId("2016101000649369");
		alipayBatch.setBillDate(DateUtil.addDay(new Date(), -1));
		alipayBatch.setChannelBillStorePath("F:/data/bill/daily/outside/alipay/2016101000649369/20161220/2016101000649369_20161220.csv.zip");//alipay

		BillBizBatch weixinBatch = new BillBizBatch();
		weixinBatch.setBatchNo(BatchUtil.makeBatchNo());
		weixinBatch.setPayChannel(PayChannelEnum.weixin.name());
		weixinBatch.setChannelMerchantId("111111");
		/* 前推几天 */
		weixinBatch.setBillDate(DateUtil.addDay(new Date(), -2));
		weixinBatch.setChannelBillStorePath("F:/data/bill/daily/outside/weixin/mchid/appid/20161221/mchid_appid_20161221.zip");
		
		BillBizBatch unionpayBatch = new BillBizBatch();
		unionpayBatch.setBatchNo(BatchUtil.makeBatchNo());
		unionpayBatch.setPayChannel(PayChannelEnum.unionpay.name());
		unionpayBatch.setChannelMerchantId("700000000000001");
		unionpayBatch.setChannelAppId(null);
		Calendar calendar=Calendar.getInstance(); 
		calendar.set(Calendar.YEAR, 2016);//设置当前年份
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 19);
		unionpayBatch.setBillDate(calendar.getTime());
		unionpayBatch.setChannelBillStorePath("F:/data/bill/daily/outside/unionpay/700000000000001/20160119/700000000000001_20160119.zip");

		return new Object[][]{{alipayBatch}, {weixinBatch}, {unionpayBatch}};
	}
	
	@Test(dataProvider="provideBillBizBatch")
	public void parseBizBillTest(BillBizBatch batch){
		log.info("解析条件，payChannel>>>"+batch.getPayChannel()+"  channelMerchantId>>>"+batch.getChannelMerchantId()+"  channelAppId>>>"+batch.getChannelAppId()+"  "
				+ "billDate>>>"+DateUtil.makeDefaultDateFormat(batch.getBillDate()));
		try {
			Map<String, List<OutsideDailyBizBillItem>> dataMap = billBizParserBusiness.parseBizBill(batch);
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
