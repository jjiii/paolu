package com.fc.pay.bill.service;

import java.math.BigDecimal;
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
import com.fc.pay.bill.enums.BillTypeEnum;
import com.fc.pay.bill.enums.CurrencyEnum;
import com.fc.pay.bill.utils.BatchUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.enums.PayWayEnum;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.system.mybatis.Page;

public class BillBizDoubtServiceTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory
			.getLogger(BillBizDoubtServiceTest.class);
	
	@Autowired
	private BillBizDoubtService doubtService;
	
	@Autowired
	private BillBizEntityBusiness entityBusiness;
	
	//@Test
	public void addTest(){
		BillBizDoubt doubt = new BillBizDoubt();		
		entityBusiness.initEntityValue(doubt);		
		doubt.setCreateTime(DateUtil.addDay(new Date(), -1));
		doubt.setBatchNo(BatchUtil.makeBatchNo());
		doubt.setBillDate(DateUtil.addDay(new Date(), -1));
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
	    doubt.setPayMerchOrderNo("201612140004");
	    doubt.setPayOrderNo("2016121400010004");
	    doubt.setPayTradeNo("alipay0004");
	    doubt.setPayTradeAmount(new BigDecimal("400.00"));
	    doubt.setPayTradeStatus(TradeStatusEnum.success.name());
	    doubt.setPayOrderTime(DateUtil.addDay(new Date(), -1));
	    doubt.setPaySuccessTime(null);
		doubtService.add(doubt);
		log.info("新增对账汇总："+doubt.toString());
	}
	
	//@Test
	public void getByBillDateTest(){
		String expireDate = DateUtil.makeDefaultDateFormat(DateUtil.addDay(new Date(), -1));
		log.info("过期日期："+expireDate);
		List<BillBizDoubt> doubtList = doubtService.listByExpireDate(expireDate);
		for(BillBizDoubt doubt: doubtList){
			log.info("存疑记录>>>"+doubt.toString());
		}
	}
	
	//@Test
	public void deleteBatchTest(){
		String expireDate = DateUtil.makeDefaultDateFormat(DateUtil.addDay(new Date(), -1));
		log.info("过期日期："+expireDate);
		List<BillBizDoubt> doubtList = doubtService.listByExpireDate(expireDate);
		for(BillBizDoubt doubt: doubtList){
			log.info("存疑记录>>>"+doubt.toString());
		}
		doubtService.deleteBatch(doubtList);
		log.info("是否批量删除成功:"+(doubtService.listByExpireDate(expireDate).size()==0));
	}

	//@Test
	public void listInPeriodByBillTypeTest(){
		String billType = BillTypeEnum.pay.name();
		log.info("对账类型："+billType);
		List<BillBizDoubt> doubtList = doubtService.listInPeriodByBillType(billType);
		for(BillBizDoubt doubt: doubtList){
			log.info("存疑记录>>>"+doubt.toString());
		}
	}
	
	@Test
	public void pageTest(){		
		Page page = new Page();
		page.setCurrNum(2);
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("page", page);
		paramMap.put("payChannel", "alipay");
		page = doubtService.page(paramMap);
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
		List<BillBizDoubt> list = doubtService.list(paramMap);
		log.info("条件查询记录总数:"+list.size());
		for(BillBizDoubt doubt : list){
			log.info(doubt.toString());
		}		
	}
	
}
