package com.fc.pay.bill.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;

public class LocalDataBusinessTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory.getLogger(LocalDataBusinessTest.class);
	
	@Autowired
	private LocalDataBusiness localDataBusiness;
	
	//@DataProvider(name="provideBillBizBatch")
	public Object[][] provideBillBizBatch(){
		String channelMerchantId = "777290058110048";
		String channelAppId = "";
		BillBizBatch batch = new BillBizBatch();
		batch.setPayChannel(PayChannelEnum.unionpay.name());
		batch.setChannelMerchantId(channelMerchantId);		
		batch.setChannelAppId(channelAppId);
		//batch.setBillDate(DateUtil.addDay(new Date(), -1));
		batch.setBillDate(new Date());
		return new Object[][]{{batch}};
	}
	
	//@Test(dataProvider="provideBillBizBatch")
	public void findSuccessPayDataByBatchTest(BillBizBatch batch){		
		log.info("查询成功支付记录列表开始>>>>>>>");
		List<PayPaymentOrder> list = localDataBusiness.findSuccessPayDataByBatch(batch);		
		log.info("查询条件，channelMerchantId>>>"+batch.getChannelMerchantId()+"  channelAppId>>>"+batch.getChannelAppId()+"  "
				+ "billDate>>>"+DateUtil.makeDefaultDateFormat(batch.getBillDate()));
		log.info("统计结果，总记录数>>>"+(list!=null?list.size():0));
		if(!CollectionUtils.isEmpty(list)){
			log.info("统计结果，具体明细记录......");
			for(PayPaymentOrder order : list){
				log.info("成功支付记录>>>"+order.toString());
			}
		}		
		log.info("<<<<<<查询成功支付记录列表结束");
	}
	
	//@Test(dataProvider="provideBillBizBatch")
	public void findAllPayDataByBatchTest(BillBizBatch batch){		
		log.info("查询所有支付记录列表开始>>>>>>>");
		List<PayPaymentOrder> list = localDataBusiness.findAllPayDataByBatch(batch);		
		log.info("查询条件，channelMerchantId>>>"+batch.getChannelMerchantId()+"  channelAppId>>>"+batch.getChannelAppId()+"  "
				+ "billDate>>>"+DateUtil.makeDefaultDateFormat(batch.getBillDate()));
		log.info("统计结果，总记录数>>>"+(list!=null?list.size():0));
		if(!CollectionUtils.isEmpty(list)){
			log.info("统计结果，具体明细记录......");
			for(PayPaymentOrder order : list){
				log.info("支付记录>>>"+order.toString());
			}
		}		
		log.info("<<<<<<查询所有支付记录列表结束");
	}
	
	//@Test(dataProvider="provideBillBizBatch")
	public void findSuccessRefundDataByBatchTest(BillBizBatch batch){		
		log.info("查询成功退款记录列表开始>>>>>>>");
		List<PayRefundOrder> list = localDataBusiness.findSuccessRefundDataByBatch(batch);		
		log.info("查询条件，channelMerchantId>>>"+batch.getChannelMerchantId()+"  channelAppId>>>"+batch.getChannelAppId()+"  "
				+ "billDate>>>"+DateUtil.makeDefaultDateFormat(batch.getBillDate()));
		log.info("统计结果，总记录数>>>"+(list!=null?list.size():0));
		if(!CollectionUtils.isEmpty(list)){
			log.info("统计结果，具体明细记录......");
			for(PayRefundOrder order : list){
				log.info("成功退款记录>>>"+order.toString());
			}
		}		
		log.info("<<<<<<查询成功退款记录列表结束");
	}
	
	//@Test(dataProvider="provideBillBizBatch")
	public void findAllRefundDataByBatchTest(BillBizBatch batch){		
		log.info("查询所有退款记录列表开始>>>>>>>");
		List<PayRefundOrder> list = localDataBusiness.findAllRefundDataByBatch(batch);		
		log.info("查询条件，channelMerchantId>>>"+batch.getChannelMerchantId()+"  channelAppId>>>"+batch.getChannelAppId()+"  "
				+ "billDate>>>"+DateUtil.makeDefaultDateFormat(batch.getBillDate()));
		log.info("统计结果，总记录数>>>"+(list!=null?list.size():0));
		if(!CollectionUtils.isEmpty(list)){
			log.info("统计结果，具体明细记录......");
			for(PayRefundOrder order : list){
				log.info("退款记录>>>"+order.toString());
			}	
		}		
		log.info("<<<<<<查询所有退款记录列表结束");
	}
	
	//@Test
	public void findMerchantAppListTest(){
		log.info("查找有效商户应用列表开始>>>>>>>");
		List<MerchantApp> list = localDataBusiness.findMerchantAppList();		
		log.info("统计结果，总记录数>>>"+(list!=null?list.size():0));
		if(!CollectionUtils.isEmpty(list)){
			log.info("统计结果，具体明细记录......");
			for(MerchantApp app : list){
				log.info("商户应用>>>"+app.toString());
			}	
		}		
		log.info("<<<<<<查找有效商户应用列表结束");
	}
	
	//@Test
	public void findMerchantAppConfigListTest(){
		log.info("查找有效商户应用配置列表开始>>>>>>>");
		List<MerchantAppConfig> list = localDataBusiness.findMerchantAppConfigList();		
		log.info("统计结果，总记录数>>>"+(list!=null?list.size():0));
		if(!CollectionUtils.isEmpty(list)){
			log.info("统计结果，具体明细记录......");
			for(MerchantAppConfig config : list){
				log.info("商户应用配置>>>"+config.toString());
			}	
		}		
		log.info("<<<<<<查找有效商户应用配置列表结束");
	}
	
	//@Test
	public void getMerchantAppConfigByMchIdTest(){
		log.info("查找商户号对应的支付配置>>>");
		String channelMerchantId = "700000000000001";
		MerchantAppConfig config = localDataBusiness.getMerchantAppConfigByMchId(channelMerchantId );
		log.info("查询结果>>>"+config.toString());
	}
	
	//@Test
	public void getMerchantAppConfigByAppIdTest(){
		log.info("查找AppId对应的支付配置>>>");
		String channelAppId = "2016080100144012";
		MerchantAppConfig config = localDataBusiness.getMerchantAppConfigByAppId(channelAppId );
		log.info("查询结果>>>"+config.toString());
	}
	
	@Test
	public void queryAllRefundDataByBatchTest(){		
		log.info("查询所有退款记录列表开始>>>>>>>");
		BillBizBatch batch = new BillBizBatch();
		batch.setPayChannel("alipay");
		batch.setChannelAppId("2017010904939710");
		batch.setChannelMerchantId(null);
		batch.setBillDate(DateUtil.addDay(new Date(), -2));
		
		
		//List<PayRefundOrder> list = new  ArrayList<PayRefundOrder>();
		List<PayRefundOrder> list = localDataBusiness.findAllRefundDataByBatch(batch);
		//List<PayRefundOrder> list = localDataBusiness.findSuccessRefundDataByBatch(batch);
		
		/**
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", batch.getPayChannel());
		map.put("channelMerchantId", batch.getChannelMerchantId());
		map.put("channelAppId", batch.getChannelAppId());
		String yyyyMMdd = DateUtil.makeDefaultDateFormat(batch.getBillDate());
		map.put("startTime", yyyyMMdd+"000000");
		map.put("endTime",   yyyyMMdd+"235959");
		*/
		
		//List<PayRefundOrder> list = localDataBusiness.findAllRefundDataByBatch(batch);		
		log.info("查询条件，channelMerchantId>>>"+batch.getChannelMerchantId()+"  channelAppId>>>"+batch.getChannelAppId()+"  "
				+ "billDate>>>"+DateUtil.makeDefaultDateFormat(batch.getBillDate()));
		log.info("统计结果，总记录数>>>"+(list!=null?list.size():0));
		if(!CollectionUtils.isEmpty(list)){
			log.info("统计结果，具体明细记录......");
			for(PayRefundOrder order : list){
				log.info("退款记录>>>"+order.toString());
			}	
		}		
		log.info("<<<<<<查询所有退款记录列表结束");
	}

}
