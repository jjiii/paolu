package com.fc.pay.bill.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.business.BillBizEntityBusiness;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.MerchantAppConfig;

public class BillBizBatchServiceTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory
			.getLogger(BillBizBatchServiceTest.class);
	
	@Autowired
	private BillBizBatchService batchService;
	
	@Autowired
	private BillBizEntityBusiness entityBusiness;
	
	@DataProvider
	public Object[][] addTestData(){		
		MerchantAppConfig macWeixin = new MerchantAppConfig();
		macWeixin.setChannel(PayChannelEnum.weixin.name());
		macWeixin.setChannelAppId("16987654323");
		macWeixin.setChannelMerchantId("1600000003");
		Date billDateWeixin = DateUtil.addDay(new Date(), -5);//账单日期
		BillBizBatch batchWeixin = entityBusiness.makeBillBizBatch(macWeixin, billDateWeixin);
		
		MerchantAppConfig macAlipay = new MerchantAppConfig();
		macAlipay.setChannel(PayChannelEnum.unionpay.name());
		macAlipay.setChannelAppId(null);
		macAlipay.setChannelMerchantId("1600000002");
		Date billDateAlipay = DateUtil.addDay(new Date(), -3);//账单日期
		BillBizBatch batchAlipay = entityBusiness.makeBillBizBatch(macAlipay, billDateAlipay);
		
		MerchantAppConfig macUnionpay = new MerchantAppConfig();
		macUnionpay.setChannel(PayChannelEnum.unionpay.name());
		macUnionpay.setChannelAppId(null);
		macUnionpay.setChannelMerchantId("1600000002");
		Date billDateUnionpay = DateUtil.addDay(new Date(), -3);//账单日期
		BillBizBatch batchUnionpay = entityBusiness.makeBillBizBatch(macUnionpay, billDateUnionpay);
		
		return new Object[][]{{batchWeixin},{batchAlipay},{batchUnionpay}};
	}
	
	//@Test(dataProvider="addTestData")
	public void addTest(BillBizBatch batch){
		batchService.add(batch);
		log.info("新增对账批次："+batch.toString());
		BillBizBatch rtnBatch = batchService.get(batch.getId());
		log.info("查询对账批次："+rtnBatch.toString());
	}
	
	//@Test
	public void addBatchTest(){		
		List<BillBizBatch> batchList = new ArrayList<BillBizBatch>();
		for(int i=20; i<30; i++){
			MerchantAppConfig macWeixin = new MerchantAppConfig();
			macWeixin.setChannel(PayChannelEnum.weixin.name());
			macWeixin.setChannelAppId("16987654323");
			macWeixin.setChannelMerchantId("1600000003");
			Date billDateWeixin = DateUtil.addDay(new Date(), -i);//账单日期		
			batchList.add(entityBusiness.makeBillBizBatch(macWeixin, billDateWeixin));
		}
		List<BillBizBatch> rtnBatchList = batchService.addBatch(batchList);
		for(BillBizBatch batch : rtnBatchList){
			log.info("批次id["+batch.getId()+"]>>>"+batch.getBatchNo());
		}
	}	
	
	@Test
	public void pageTest(){		
		Page page = new Page();
		page.setCurrNum(2);
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("page", page);
		paramMap.put("payChannel", "weixin");
		page = batchService.page(paramMap);
		log.info("记录总数:"+page.getTotalItem());
		log.info("每页记录个数:"+page.getPageSize());
		log.info("总页数:"+page.totalPages());	
		log.info("当前页号:"+page.getCurrNum());
		log.info("当前页记录个数:"+page.size());		
	}
	
	@Test
	public void listTest(){		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("payChannel", "weixin");
		List<BillBizBatch> list = batchService.list(paramMap);
		log.info("条件查询记录总数:"+list.size());
		for(BillBizBatch batch : list){
			log.info(batch.toString());
		}		
	}

}
