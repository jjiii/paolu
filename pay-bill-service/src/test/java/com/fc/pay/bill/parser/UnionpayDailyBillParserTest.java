package com.fc.pay.bill.parser;

import java.util.Calendar;
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
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;
import com.fc.pay.common.core.enums.PayChannelEnum;

public class UnionpayDailyBillParserTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory.getLogger(UnionpayDailyBillParserTest.class);
	
	@Resource(name="unionpayDailyBillParser")
	private UnionpayDailyBillParser unionpayDailyBillParser;
		
	@Test
	public void unionpayDailyBillParserTest(){
		String mchId = "700000000000001";
		
		BillBizBatch batch = new BillBizBatch();
		batch.setBatchNo(BatchUtil.makeBatchNo());
		batch.setPayChannel(PayChannelEnum.unionpay.name());
		batch.setChannelMerchantId(mchId);
		Calendar calendar=Calendar.getInstance(); 
		calendar.set(Calendar.YEAR, 2016);//设置当前年份
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 19);
		batch.setBillDate(calendar.getTime());
		String channelBillStorePath = "F:/data/bill/daily/outside/unionpay/700000000000001/20160119/700000000000001_20160119.zip";
		batch.setChannelBillStorePath(channelBillStorePath);
		try {
			Map<String, List<OutsideDailyBizBillItem>> dataMap = unionpayDailyBillParser.parseBizBill(batch);
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
