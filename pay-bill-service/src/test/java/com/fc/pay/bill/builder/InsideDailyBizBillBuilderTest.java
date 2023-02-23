package com.fc.pay.bill.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;

public class InsideDailyBizBillBuilderTest extends BaseTestng{
	
	private static final Logger log = LoggerFactory
			.getLogger(InsideDailyBizBillBuilderTest.class);
	
	@Autowired
	private InsideDailyBizBillBuilder insideDailyBizBillBuilder;
	
	@Test
	public void buildInsideDailyBizBill(){
		
		//InsideDailyBizBillBuilder builder = (InsideDailyBizBillBuilder) container.getBean("insideDailyBizBillBuilder");
		
		/*
		List<InsidePaySuccessDailyBillItem> paySuccessItemList = new ArrayList<InsidePaySuccessDailyBillItem>();
		InsidePaySuccessDailyBillItem payItem = null;
		for(int i=1; i<10; i++){
			payItem = new InsidePaySuccessDailyBillItem();			
			//"#商户编号", "商户应用编号",
			//"支付渠道类型", "支付订单生成时间", "支付订单号", "支付流水号", "支付订单金额", "支付订单状态（SUCCESS）",
			//"货币种类（人民币CNY）", "商品名称" };
			payItem.setMerchantId(i + "001");
			payItem.setMerchantAppId(i + "002");
			payItem.setChannelType("weixin");
			payItem.setPayTradeTime("2016-08-12 04:16:03");
			payItem.setPayOrderNo(i + "000000001");
			payItem.setPayTradeNo(i + "00000000000001");
			payItem.setPayTradeAmount(new BigDecimal(i + "00.00"));
			payItem.setPayTradeStatus("SUCCESS");
			payItem.setCurrency("CNY");				
			payItem.setCommodity("商品0" + i);
			paySuccessItemList.add(payItem);
		}

		log.info("成功支付账单生成结果=>"+insideDailyBizBillBuilder.buildInsidePaySuccessBill(paySuccessItemList, "D:/testdata/MerchCode_PAY_SUCCESS_20161121.csv"));

		List<InsideRefundDailyBillItem> refundItemList = new ArrayList<InsideRefundDailyBillItem>();
		InsideRefundDailyBillItem refundItem = null;
		for(int i=1; i<10; i++){
			refundItem = new InsideRefundDailyBillItem();			
			//"#商户编号", "商户应用编号",
			//"支付渠道类型", "支付订单生成时间", "支付订单号", "支付流水号", "支付订单金额", "支付订单状态（SUCCESS）",
			//"货币种类（人民币CNY）", "商品名称" };
			refundItem.setMerchantId(i + "001");
			refundItem.setMerchantAppId(i + "002");
			refundItem.setChannelType("weixin");
			refundItem.setPayTradeTime("2016-08-12 04:16:03");
			refundItem.setPayOrderNo(i + "000000001");
			refundItem.setPayTradeNo(i + "00000000000001");
			refundItem.setPayTradeAmount(new BigDecimal(i + "00.00"));
			refundItem.setPayTradeStatus("SUCCESS");
			refundItem.setCurrency("CNY");				
			refundItem.setCommodity("商品0" + i);
			//"退款订单号", "退款流水号",	"退款订单金额", "退款申请时间", "退款成功时间", "退款订单状态（SUCCESS）"
			refundItem.setRefundOrderNo(i+"0010000001");
			refundItem.setRefundTradeNo(i+"0060000001");
			refundItem.setRefundAmount(new BigDecimal(i+"0.01"));
			refundItem.setRefundApplyTime("2016-08-12 04:16:00");
			refundItem.setRefundSuccessTime("2016-08-20 04:16:09");
			refundItem.setRefundStatus("SUCCESS");
			refundItemList.add(refundItem);
		}
		log.info("退款账单生成结果=>" + insideDailyBizBillBuilder.buildInsideRefundBill(refundItemList, "D:/testdata/MerchCode_REFUND_20161121.csv"));
		*/
	}

}
