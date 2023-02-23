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
import com.fc.pay.bill.enums.BillTypeEnum;
import com.fc.pay.bill.enums.CurrencyEnum;
import com.fc.pay.bill.utils.BatchUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.enums.PayWayEnum;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.system.mybatis.Page;

public class BillBizItemServiceTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory
			.getLogger(BillBizItemServiceTest.class);
	
	@Autowired
	private BillBizItemService itemService;
	
	@Autowired
	private BillBizEntityBusiness entityBusiness;
	
	//@Test
	public void addTest(){
		String batchNo = BatchUtil.makeBatchNo();
		BillBizDoubt doubt = new BillBizDoubt();		
		entityBusiness.initEntityValue(doubt);		
		doubt.setCreateTime(DateUtil.addDay(new Date(), -2));
		doubt.setBatchNo(batchNo);
		doubt.setBillDate(DateUtil.addDay(new Date(), -2));
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
	    doubt.setPayMerchOrderNo("201612140001");
	    doubt.setPayOrderNo("2016121400010001");
	    doubt.setPayTradeNo("alipay0001");
	    doubt.setPayTradeAmount(new BigDecimal("100.00"));
	    doubt.setPayTradeStatus(TradeStatusEnum.success.name());
	    doubt.setPayOrderTime(DateUtil.addDay(new Date(), -2));
	    doubt.setPaySuccessTime(null);
	    
	    BillBizBatch batch = new BillBizBatch();
	    batch.setPayChannel(PayChannelEnum.alipay.name());
	    batch.setBatchNo(batchNo);
	    batch.setBillDate(DateUtil.addDay(new Date(), -1));
	    
		BillBizItem bizItem = entityBusiness.makeBillBizItem(null, null, doubt, batch );
		
		itemService.add(bizItem);
		log.info("新增账单明细："+bizItem.toString());
	}
	
	//@Test
	public void addBatchTest(){
		String batchNo = BatchUtil.makeBatchNo();
		List<BillBizItem> list = new ArrayList<BillBizItem>();
		for(int i=1;i<10;i++){
			BillBizDoubt doubt = new BillBizDoubt();		
			entityBusiness.initEntityValue(doubt);		
			doubt.setCreateTime(DateUtil.addDay(new Date(), -i));
			doubt.setBatchNo(batchNo);
			doubt.setBillDate(DateUtil.addDay(new Date(), -i));
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
		    doubt.setPayOrderTime(DateUtil.addDay(new Date(), -i));
		    doubt.setPaySuccessTime(null);		    
		    BillBizBatch batch = new BillBizBatch();
		    batch.setPayChannel(PayChannelEnum.alipay.name());
		    batch.setBatchNo(batchNo);
		    batch.setBillDate(DateUtil.addDay(new Date(), -1));		    
			BillBizItem bizItem = entityBusiness.makeBillBizItem(null, null, doubt, batch);
			list.add(bizItem);
		}
		
		itemService.addBatch(list);
		for(BillBizItem item : list){
			log.info("新增账单明细："+item.toString());
		}		
	}
	
	//@Test
	public void getTest(){
		log.info(itemService.get(Long.valueOf(2)).toString());
	}

	
	@Test
	public void pageTest(){		
		Page page = new Page();
		page.setCurrNum(1);
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("page", page);
		paramMap.put("payChannel", "alipay");
		page = itemService.page(paramMap);
		log.info("记录总数:"+page.getTotalItem());
		log.info("每页记录个数:"+page.getPageSize());
		log.info("总页数:"+page.totalPages());	
		log.info("当前页号:"+page.getCurrNum());
		log.info("当前页记录个数:"+page.size());		
	}
	
	@Test
	public void listTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("payChannel", "alipay");
		List<BillBizItem> list = itemService.list(paramMap);
		log.info("条件查询记录总数:"+list.size());
		for(BillBizItem item : list){
			log.info(item.toString());
		}		
	}
	
}
