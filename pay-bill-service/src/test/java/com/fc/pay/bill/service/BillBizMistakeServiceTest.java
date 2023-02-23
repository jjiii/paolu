package com.fc.pay.bill.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.business.BillBizEntityBusiness;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.enums.BillTypeEnum;
import com.fc.pay.bill.enums.CurrencyEnum;
import com.fc.pay.bill.enums.MistakeTypeEnum;
import com.fc.pay.bill.utils.BatchUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.enums.PayWayEnum;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.system.mybatis.Page;

public class BillBizMistakeServiceTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory
			.getLogger(BillBizMistakeServiceTest.class);
	
	@Autowired
	private BillBizDoubtService doubtService;
	
	@Autowired
	private BillBizMistakeService mistakeService;
	
	@Autowired
	private BillBizEntityBusiness entityBusiness;
	
	//@Test
	public void addBatchTest(){
		//entityBusiness.makeBizDoubt(payOrder, refundOrder, batch);
		List<BillBizMistake> mistakeList = new ArrayList<BillBizMistake>();
		for(int i=6; i<10; i++){
			BillBizDoubt doubt = new BillBizDoubt();
			entityBusiness.initEntityValue(doubt);		
			doubt.setCreateTime(DateUtil.addDay(new Date(), -5));
			doubt.setBatchNo(BatchUtil.makeBatchNo());
			doubt.setBillDate(DateUtil.addDay(new Date(), -5));
			doubt.setCurrency(CurrencyEnum.CNY.name());		
			doubt.setBillType(BillTypeEnum.pay.name());
		    doubt.setPayChannel(PayChannelEnum.alipay.name());
		    doubt.setPayWay(PayWayEnum.web.name());
		    doubt.setChannelAppId("16987654321");
		    doubt.setChannelMerchantId("1600000001");
		    doubt.setMerchantCode("01");
		    doubt.setMerchantName("枫车");
		    doubt.setMerchantAppCode("0101");
		    doubt.setMerchantAppName("枫车快手");
		    doubt.setMerchantCommodity("测试商品名称");	
		    doubt.setPayMerchOrderNo("20161214000"+i);
		    doubt.setPayOrderNo("201612140001000"+i);
		    doubt.setPayTradeNo("alipay000"+i);
		    doubt.setPayTradeAmount(new BigDecimal(i+"00.00"));
		    doubt.setPayTradeStatus(TradeStatusEnum.success.name());
		    doubt.setPayOrderTime(DateUtil.addDay(new Date(), -5));
		    doubt.setPaySuccessTime(null);
			doubtService.add(doubt);
			log.info("新增对账汇总："+doubt.toString());
			
	        BillBizBatch batch = new BillBizBatch(); 
	        batch.setBatchNo(doubt.getBatchNo());
	        batch.setBillDate(doubt.getBillDate());		
	        batch.setPayChannel(doubt.getPayChannel());
	        
			OutsideDailyBizBillItem item = new OutsideDailyBizBillItem();
			item = new OutsideDailyBizBillItem();
	        item.setBatchNo(batch.getBatchNo());
	        item.setPayChannel(batch.getPayChannel());
	        item.setMerchantAppId(batch.getChannelAppId());
	        item.setMerchantId(doubt.getChannelMerchantId());
	        item.setBillDate(doubt.getBillDate());           	
	          	
	    	item.setPayOrderNo("201612140001000"+i);
	    	item.setPayTradeNo("alipay000"+i);       
	        item.setPayOrderTime(DateUtil.addDay(new Date(), -5));
	        item.setPaySuccessTime(DateUtil.addDay(new Date(), -5));
	        item.setPayTradeStatus(TradeStatusEnum.success.name());
	        item.setPayTradeAmount(new BigDecimal(i+"01.03"));
		    
	        mistakeList.add(entityBusiness.makePayMisktake(doubt, null, item, MistakeTypeEnum.local_cash_less, batch));
		}
		
		mistakeService.addBatch(mistakeList);
		
	}
	
	//@Test
	public void addTest(){
		//entityBusiness.makeBizDoubt(payOrder, refundOrder, batch);
		BillBizDoubt doubt = new BillBizDoubt();
		entityBusiness.initEntityValue(doubt);		
		doubt.setCreateTime(DateUtil.addDay(new Date(), -5));
		doubt.setBatchNo(BatchUtil.makeBatchNo());
		doubt.setBillDate(DateUtil.addDay(new Date(), -5));
		doubt.setCurrency(CurrencyEnum.CNY.name());		
		doubt.setBillType(BillTypeEnum.pay.name());
	    doubt.setPayChannel(PayChannelEnum.alipay.name());
	    doubt.setPayWay(PayWayEnum.web.name());
	    doubt.setChannelAppId("16987654321");
	    doubt.setChannelMerchantId("1600000001");
	    doubt.setMerchantCode("01");
	    doubt.setMerchantName("枫车");
	    doubt.setMerchantAppCode("0101");
	    doubt.setMerchantAppName("枫车快手");
	    doubt.setMerchantCommodity("测试商品名称");	
	    doubt.setPayMerchOrderNo("201612140005");
	    doubt.setPayOrderNo("2016121400010005");
	    doubt.setPayTradeNo("alipay0005");
	    doubt.setPayTradeAmount(new BigDecimal("500.00"));
	    doubt.setPayTradeStatus(TradeStatusEnum.success.name());
	    doubt.setPayOrderTime(DateUtil.addDay(new Date(), -5));
	    doubt.setPaySuccessTime(null);
		doubtService.add(doubt);
		log.info("新增对账汇总："+doubt.toString());
		
        BillBizBatch batch = new BillBizBatch(); 
        batch.setBatchNo(doubt.getBatchNo());
        batch.setBillDate(doubt.getBillDate());		
        batch.setPayChannel(doubt.getPayChannel());
        
		OutsideDailyBizBillItem item = new OutsideDailyBizBillItem();
		item = new OutsideDailyBizBillItem();
        item.setBatchNo(batch.getBatchNo());
        item.setPayChannel(batch.getPayChannel());
        item.setMerchantAppId(batch.getChannelAppId());
        item.setMerchantId(doubt.getChannelMerchantId());
        item.setBillDate(doubt.getBillDate());           	
          	
    	item.setPayOrderNo("2016121400010005");
    	item.setPayTradeNo("alipay0005");       
        item.setPayOrderTime(DateUtil.addDay(new Date(), -5));
        item.setPaySuccessTime(DateUtil.addDay(new Date(), -5));
        item.setPayTradeStatus(TradeStatusEnum.success.name());
        item.setPayTradeAmount(new BigDecimal("501.03"));
	    
        BillBizMistake mistake = entityBusiness.makePayMisktake(doubt, null, item, MistakeTypeEnum.local_cash_less, batch);
		
		mistakeService.add(mistake);
		log.info("差错记录>>>"+mistake);
	}
	
	//@Test
	public void pageTest(){		
		Page page = new Page();
		page.setCurrNum(1);
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("page", page);
		paramMap.put("payChannel", "alipay");
		page = mistakeService.page(paramMap);
		log.info("记录总数:"+page.getTotalItem());
		log.info("每页记录个数:"+page.getPageSize());
		log.info("总页数:"+page.totalPages());	
		log.info("当前页号:"+page.getCurrNum());
		log.info("当前页记录个数:"+page.size());		
	}
	
	//@Test
	public void listTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("payChannel", "alipay");
		List<BillBizMistake> list = mistakeService.list(paramMap);
		log.info("条件查询记录总数:"+list.size());
		for(BillBizMistake mistake : list){
			log.info(mistake.toString());
		}		
	}
	
	@Test
	public void hasMoreMistakeToHandleTest(){
		String payOrderNo = "813321983600234496";
		boolean hasMorePayMistake = mistakeService.hasMoreMistakeToHandle(BillTypeEnum.pay.name(), payOrderNo);
		String refundOrderNo = "123456780";
		boolean hasMoreRefundMistake = mistakeService.hasMoreMistakeToHandle(BillTypeEnum.refund.name(), refundOrderNo);
		log.info("hasMorePayMistake>>>"+hasMorePayMistake+"  hasMoreRefundMistake>>>"+hasMoreRefundMistake);
	}

}
