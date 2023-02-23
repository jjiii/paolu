package com.fc.pay.bill.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.enums.BillMakeStatusEnum;
import com.fc.pay.bill.enums.BillTypeEnum;
import com.fc.pay.bill.enums.BizItemSourceEnum;
import com.fc.pay.bill.enums.CurrencyEnum;
import com.fc.pay.bill.enums.EntityCreaterEnum;
import com.fc.pay.bill.enums.MistakeHandleStatusEnum;
import com.fc.pay.bill.enums.MistakeHandleWayEnum;
import com.fc.pay.bill.enums.MistakeNotifyStatusEnum;
import com.fc.pay.bill.enums.MistakeTypeEnum;
import com.fc.pay.bill.utils.BatchUtil;
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;
import com.fc.pay.common.core.entity.Entity;
import com.fc.pay.common.core.enums.BillStatusEnum;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;

/**
 * 业务账单数据制作业务
 * 
 * @author zhanjq
 *
 */
@Service("billBizEntityBusiness")
public class BillBizEntityBusiness {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizEntityBusiness.class);
	
	/**
	 * 计算支付账单状态集合
	 * @param bizItemList
	 * @param insertDoubtList
	 * @param mistakeList
	 * @return
	 */
	public Map<String, ArrayList<String>> makePayBillStatusMap(List<BillBizItem> bizItemList, List<BillBizDoubt> insertDoubtList, List<BillBizMistake> mistakeList){
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> normalPayOrderNoList = new ArrayList<String>();
		ArrayList<String> doubtPayOrderNoList = new ArrayList<String>();
		ArrayList<String> mistakePayOrderNoList = new ArrayList<String>();
		//normal
		for(BillBizItem bizItem : bizItemList){
			if(BillTypeEnum.pay.name().equals(bizItem.getBillType())){
				normalPayOrderNoList.add(bizItem.getPayOrderNo());
			}
		}
		map.put(BillStatusEnum.normal.name(), normalPayOrderNoList);
		
		//doubt
		for(BillBizDoubt doubt : insertDoubtList){
			if(BillTypeEnum.pay.name().equals(doubt.getBillType())){
				doubtPayOrderNoList.add(doubt.getPayOrderNo());
			}
		}
		map.put(BillStatusEnum.doubt.name(), doubtPayOrderNoList);
		
		//mistake-注意一个记录了能对应多个错误
		for(BillBizMistake mistake : mistakeList){
			if(BillTypeEnum.pay.name().equals(mistake.getBillType())){
				if(!mistakePayOrderNoList.contains(mistake.getPayOrderNo())){
					mistakePayOrderNoList.add(mistake.getPayOrderNo());
				}				
			}
		}	
		map.put(BillStatusEnum.mistake.name(), mistakePayOrderNoList);
		
		return map;
	}
	
	/**
	 * 计算退款账单状态集合
	 * @param bizItemList
	 * @param insertDoubtList
	 * @param mistakeList
	 * @return
	 */
	public Map<String, ArrayList<String>> makeRefundBillStatusMap(List<BillBizItem> bizItemList, List<BillBizDoubt> insertDoubtList, List<BillBizMistake> mistakeList){
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> normalRefundOrderNoList = new ArrayList<String>();
		ArrayList<String> doubtRefundOrderNoList = new ArrayList<String>();
		ArrayList<String> mistakeRefundOrderNoList = new ArrayList<String>();
		//normal
		for(BillBizItem bizItem : bizItemList){
			if(BillTypeEnum.refund.name().equals(bizItem.getBillType())){
				normalRefundOrderNoList.add(bizItem.getRefundOrderNo());
			}
		}
		map.put(BillStatusEnum.normal.name(), normalRefundOrderNoList);
		
		//doubt
		for(BillBizDoubt doubt : insertDoubtList){
			if(BillTypeEnum.refund.name().equals(doubt.getBillType())){
				doubtRefundOrderNoList.add(doubt.getRefundOrderNo());
			}
		}
		map.put(BillStatusEnum.doubt.name(), doubtRefundOrderNoList);
		
		//mistake-注意一个记录了能对应多个错误
		for(BillBizMistake mistake : mistakeList){
			if(BillTypeEnum.refund.name().equals(mistake.getBillType())){
				if(!mistakeRefundOrderNoList.contains(mistake.getRefundOrderNo())){
					mistakeRefundOrderNoList.add(mistake.getRefundOrderNo());
				}	
			}
		}
		map.put(BillStatusEnum.mistake.name(), mistakeRefundOrderNoList);
		
		return map;
	}
	
	/**
	 * 创建日对账汇总
	 * @param billDate	账单日期
	 * @param batchCount	对账批次个数
	 * @param merchantAppCount	商户应用个数
	 * @return
	 */
	public BillBizSummary makeBillBizSummary(Date billDate, Integer batchCount, Integer merchantAppCount){
		BillBizSummary summary = new BillBizSummary();
		initEntityValue(summary);
		summary.setBillDate(billDate);
		summary.setBatchCount(batchCount);
		summary.setBatchRunSuccessCount(0);		
		summary.setMerchantAppCount(merchantAppCount);
		summary.setBillMakeSuccessCount(0);
		summary.setDownloadNotifySuccessCount(0);
		return summary;
	}
	
	/**
	 * 创建账单通知
	 * @param merchantApp
	 * @param billDate
	 * @return
	 */
	public BillBizFileNotify makeBillBizFileNotify(MerchantApp merchantApp, Date billDate){
		BillBizFileNotify fn = new BillBizFileNotify();
		initEntityValue(fn);	    
		fn.setBillDate(billDate);
		fn.setMerchantCode(merchantApp.getMerchantCode());
		fn.setMerchantName(merchantApp.getMerchantName());
		fn.setMerchantAppCode(merchantApp.getMerchantAppCode());
		fn.setMerchantAppName(merchantApp.getMerchantAppName());		
		fn.setFilePath("");
		fn.setFileStatus(BillMakeStatusEnum.wait.name());
		fn.setFileRemark(BillMakeStatusEnum.wait.getDesc());
		fn.setNotifyUrl(merchantApp.getDownloadNotifyUrl());
		fn.setNotifyStatus(MistakeNotifyStatusEnum.wait_send.name());
		fn.setNotifyRemark(MistakeNotifyStatusEnum.wait_send.getDesc());
		return fn;
	}
	
	
	public BillBizBatch makeBillBizBatchForTestVerify(String channel, String channelMerchantId, String channelAppId, Date billDate, String channelBillStorePath){
		BillBizBatch batch = new BillBizBatch();
		initEntityValue(batch);
		
		//batch.setStartTime(new Date());
		batch.setRestartCount(0);
		
		batch.setBillDate(billDate);
		batch.setBatchNo(BatchUtil.makeBatchNo());
		batch.setHandleStatus(BatchStatusEnum.init.name());
		batch.setHandleRemark(BatchStatusEnum.init.getDesc());	
		
		batch.setPayChannel(channel);
		batch.setChannelAppId(channelAppId);
		batch.setChannelMerchantId(channelMerchantId);
		batch.setChannelBillStorePath(channelBillStorePath);
		
		batch.setTradeAmount(BigDecimal.ZERO);
		batch.setTradeCount(0);
		batch.setChannelTradeAmount(BigDecimal.ZERO);
		batch.setChannelTradeCount(0);
		
		batch.setRefundAmount(BigDecimal.ZERO);
		batch.setRefundCount(0);
		batch.setChannelRefundAmount(BigDecimal.ZERO);
		batch.setChannelRefundCount(0);
		
		batch.setMistakeCount(0);
		batch.setMistakeUnhandleCount(0);
		return batch;
	}
	
	/**
	 * 初始化对账批次
	 * @param mac
	 * @param billDate
	 * @return
	 */
	public BillBizBatch makeBillBizBatch(MerchantAppConfig mac, Date billDate){
		BillBizBatch batch = new BillBizBatch();
		initEntityValue(batch);
		
		//batch.setStartTime(new Date());
		batch.setRestartCount(0);
		
		batch.setBillDate(billDate);
		batch.setBatchNo(BatchUtil.makeBatchNo());
		batch.setHandleStatus(BatchStatusEnum.init.name());
		batch.setHandleRemark(BatchStatusEnum.init.getDesc());	
		
		batch.setPayChannel(mac.getChannel());
		batch.setChannelAppId(mac.getChannelAppId());
		batch.setChannelMerchantId(mac.getChannelMerchantId());
		
		batch.setTradeAmount(BigDecimal.ZERO);
		batch.setTradeCount(0);
		batch.setChannelTradeAmount(BigDecimal.ZERO);
		batch.setChannelTradeCount(0);
		
		batch.setRefundAmount(BigDecimal.ZERO);
		batch.setRefundCount(0);
		batch.setChannelRefundAmount(BigDecimal.ZERO);
		batch.setChannelRefundCount(0);
		
		batch.setMistakeCount(0);
		batch.setMistakeUnhandleCount(0);
		return batch;
	}
	
	/**
	 * 重设对账批次
	 * @param oldBatch
	 */
	public BillBizBatch resetBillBizBatch(BillBizBatch batch){
		batch.setRestartCount(batch.getRestartCount()+1);
		batch.setStartTime(new Date());
		
		batch.setHandleStatus(BatchStatusEnum.init.name());
		batch.setHandleRemark(BatchStatusEnum.init.getDesc());	
		
		batch.setTradeAmount(BigDecimal.ZERO);
		batch.setTradeCount(0);
		batch.setChannelTradeAmount(BigDecimal.ZERO);
		batch.setChannelTradeCount(0);
		
		batch.setRefundAmount(BigDecimal.ZERO);
		batch.setRefundCount(0);
		batch.setChannelRefundAmount(BigDecimal.ZERO);
		batch.setChannelRefundCount(0);
		
		batch.setMistakeCount(0);
		batch.setMistakeUnhandleCount(0);
		return batch;
	}
	
	/**
	 * 创建渠道漏单差错记录
	 * @param doubt
	 * @return
	 */
	public BillBizMistake makeMistakeInPeriod(BillBizDoubt doubt){
		BillBizMistake mistake = new BillBizMistake();				
		initEntityValue(mistake);
		mistake.setBatchNo(doubt.getBatchNo());
		mistake.setBillDate(doubt.getBillDate());		
	    mistake.setPayChannel(doubt.getPayChannel());
	    mistake.setBillType(doubt.getBillType());
	    mistake.setMistakeType(MistakeTypeEnum.channel_miss.name());
	    mistake.setHandleStatus(MistakeHandleStatusEnum.nohandle.name());	    
	    mistake.setCurrency(CurrencyEnum.CNY.name());	
	    /** 错误-支付系统侧数据 */
	    /** 支付订单信息 */
		mistake.setPayWay(doubt.getPayWay());
	    mistake.setChannelAppId(doubt.getChannelAppId());
	    mistake.setChannelMerchantId(doubt.getChannelMerchantId());
	    mistake.setMerchantAppCode(doubt.getMerchantAppCode());
	    mistake.setMerchantAppName(doubt.getMerchantAppName());
	    mistake.setMerchantCommodity(doubt.getMerchantCommodity());	
	    mistake.setPayMerchOrderNo(doubt.getPayMerchOrderNo());
	    mistake.setPayOrderNo(doubt.getPayOrderNo());
	    mistake.setPayTradeNo(doubt.getPayTradeNo());
	    mistake.setPayTradeStatus(doubt.getPayTradeStatus());
	    mistake.setPayTradeAmount(doubt.getPayTradeAmount());
	    mistake.setPayOrderTime(doubt.getPayOrderTime());
	    mistake.setPaySuccessTime(doubt.getPaySuccessTime());
	    /** 退款订单信息 */
	    if(BillTypeEnum.refund.name().equals(doubt.getBillType())){
	    	mistake.setRefundMerchOrderNo(doubt.getRefundMerchOrderNo());
		    mistake.setRefundOrderNo(doubt.getRefundOrderNo());
		    mistake.setRefundTradeNo(doubt.getRefundTradeNo());
		    mistake.setRefundTradeStatus(doubt.getRefundTradeStatus());
		    mistake.setRefundTradeAmount(doubt.getRefundTradeAmount());
		    mistake.setRefundApplyTime(doubt.getRefundApplyTime());
		    mistake.setRefundSuccessTime(doubt.getRefundSuccessTime()); 
	    }			    
	    /** 错误-支付渠道侧数据 */
	    //银行不存在该订单，所以无此数据可复制
	    mistake.setNotifyStatus(MistakeNotifyStatusEnum.wait_send.name());
	    return mistake;
	}
	
	/**
	 * 创建支付差错记录
	 * 注意：数据来自退款订单，也来自存疑订单（支付）
	 * @param doubt 存疑记录
	 * @param payOrder 支付订单
	 * @param billItem 账单明细记录
	 * @param mistakeType 错误类型
	 * @param batch 对账批次
	 * @return
	 */
	public BillBizMistake makePayMisktake(BillBizDoubt doubt, PayPaymentOrder payOrder, OutsideDailyBizBillItem billItem, MistakeTypeEnum mistakeType, BillBizBatch batch) {

		BillBizMistake mistake = new BillBizMistake();
		
		initEntityValue(mistake);
		mistake.setBatchNo(batch.getBatchNo());
		mistake.setBillDate(batch.getBillDate());		
	    mistake.setPayChannel(batch.getPayChannel());
	    mistake.setBillType(BillTypeEnum.pay.name());
	    mistake.setMistakeType(mistakeType.name());
	    mistake.setHandleStatus(MistakeHandleStatusEnum.nohandle.name());	    
	    mistake.setCurrency(CurrencyEnum.CNY.name());	    
		/** 错误-支付系统侧数据 */
		/** --来自于系统订单 */
		if (payOrder != null) {			
			mistake.setPayWay(payOrder.getPayWay());
		    mistake.setChannelAppId(payOrder.getChannelAppId());
		    mistake.setChannelMerchantId(payOrder.getChannelMerchantId());
		    mistake.setMerchantCode(payOrder.getMerchantCode());
		    mistake.setMerchantName(payOrder.getMerchantName());
		    mistake.setMerchantAppCode(payOrder.getMerchantAppCode());
		    mistake.setMerchantAppName(payOrder.getMerchantAppName());
		    mistake.setMerchantCommodity(payOrder.getProductName());
		    mistake.setPayMerchOrderNo(payOrder.getMerchantOrderNo());
		    mistake.setPayOrderNo(payOrder.getOrderNo());
		    mistake.setPayTradeNo(payOrder.getTradeNo());
		    mistake.setPayTradeStatus(payOrder.getStatus());
		    mistake.setPayTradeAmount(payOrder.getAmount());
		    mistake.setPayOrderTime(payOrder.getOrderTime());
		    mistake.setPaySuccessTime(payOrder.getFinishTime());
		}
		/** --来自于存疑记录 */
		if (doubt != null) {
			mistake.setPayWay(doubt.getPayWay());
		    mistake.setChannelAppId(doubt.getChannelAppId());
		    mistake.setChannelMerchantId(doubt.getChannelMerchantId());
		    mistake.setMerchantCode(doubt.getMerchantCode());
		    mistake.setMerchantName(doubt.getMerchantName());
		    mistake.setMerchantAppCode(doubt.getMerchantAppCode());
		    mistake.setMerchantAppName(doubt.getMerchantAppName());
		    mistake.setMerchantCommodity(doubt.getMerchantCommodity());		
		    mistake.setPayMerchOrderNo(doubt.getPayMerchOrderNo());
		    mistake.setPayOrderNo(doubt.getPayOrderNo());
		    mistake.setPayTradeNo(doubt.getPayTradeNo());
		    mistake.setPayTradeStatus(doubt.getPayTradeStatus());
		    mistake.setPayTradeAmount(doubt.getPayTradeAmount());
		    mistake.setPayOrderTime(doubt.getPayOrderTime());
		    mistake.setPaySuccessTime(doubt.getPaySuccessTime());
		}

		/** 错误-支付渠道侧数据 */
		if (billItem != null) {
			mistake.setChannelOrderNo(billItem.getPayOrderNo());
			mistake.setChannelTradeNo(billItem.getPayTradeNo());	
			mistake.setChannelTradeStatus(billItem.getPayTradeStatus());
			mistake.setChannelTradeAmount(billItem.getPayTradeAmount());
		    mistake.setChannelTradeOrderTime(billItem.getPayOrderTime());			
		    mistake.setChannelTradeSuccessTime(billItem.getPaySuccessTime());
		}
		mistake.setNotifyStatus(MistakeNotifyStatusEnum.wait_send.name());
		return mistake;
	}
	
	/**
	 * 创建退款差错记录
	 * 注意：数据来自退款订单，也来自存疑订单（退款）
	 * @param doubt 存疑记录（退款）
	 * @param refundOrder 退款订单
	 * @param billItem 账单明细记录
	 * @param mistakeType 错误类型
	 * @param batch 对账批次
	 * @return
	 */
	public BillBizMistake makeRefundMisktake(BillBizDoubt doubt, PayRefundOrder refundOrder, OutsideDailyBizBillItem billItem, MistakeTypeEnum mistakeType, BillBizBatch batch) {

		BillBizMistake mistake = new BillBizMistake();
		
		initEntityValue(mistake);
		mistake.setBatchNo(batch.getBatchNo());
		mistake.setBillDate(batch.getBillDate());
	    mistake.setPayChannel(batch.getPayChannel());
	    mistake.setBillType(BillTypeEnum.refund.name());
	    mistake.setMistakeType(mistakeType.name());
	    mistake.setHandleStatus(MistakeHandleStatusEnum.nohandle.name());	    
	    mistake.setCurrency(CurrencyEnum.CNY.name());		
		/** 错误-支付系统侧数据 */
		/** --来自于系统订单 */
		if (refundOrder != null) {	
			/** 原支付订单信息 */
			mistake.setPayWay(refundOrder.getPayWay());
		    mistake.setChannelAppId(refundOrder.getChannelAppId());
		    mistake.setChannelMerchantId(refundOrder.getChannelMerchantId());
		    mistake.setMerchantCode(refundOrder.getMerchantCode());
		    mistake.setMerchantName(refundOrder.getMerchantName());
		    mistake.setMerchantAppCode(refundOrder.getMerchantAppCode());
		    mistake.setMerchantAppName(refundOrder.getMerchantAppName());
		    mistake.setMerchantCommodity(refundOrder.getProductName());		
		    mistake.setPayMerchOrderNo(refundOrder.getMerchantOrderNo());
		    mistake.setPayOrderNo(refundOrder.getOrderNo());
		    mistake.setPayTradeNo(refundOrder.getTradeNo());
		    mistake.setPayTradeStatus(refundOrder.getStatus());
		    mistake.setPayTradeAmount(refundOrder.getAmount());
		    mistake.setPayOrderTime(refundOrder.getOrderTime());
		    mistake.setPaySuccessTime(refundOrder.getFinishTime());
		    /** 退款订单信息 */
		    mistake.setRefundMerchOrderNo(refundOrder.getRefundMerchantNo());
		    mistake.setRefundOrderNo(refundOrder.getRefundNo());
		    mistake.setRefundTradeNo(refundOrder.getRefundTradeNo());
		    mistake.setRefundTradeStatus(refundOrder.getRefundStatus());
		    mistake.setRefundTradeAmount(refundOrder.getRefundAmount());
		    mistake.setRefundApplyTime(refundOrder.getRefundTime());
		    mistake.setRefundSuccessTime(refundOrder.getRefundFinishTime());
		}
		/** --来自于存疑记录 */
		if (doubt != null) {
			/** 原支付订单信息 */
			mistake.setPayWay(doubt.getPayWay());
		    mistake.setChannelAppId(doubt.getChannelAppId());
		    mistake.setChannelMerchantId(doubt.getChannelMerchantId());
		    mistake.setMerchantCode(doubt.getMerchantCode());
		    mistake.setMerchantName(doubt.getMerchantName());
		    mistake.setMerchantAppCode(doubt.getMerchantAppCode());
		    mistake.setMerchantAppName(doubt.getMerchantAppName());
		    mistake.setMerchantCommodity(doubt.getMerchantCommodity());	
		    mistake.setPayMerchOrderNo(doubt.getPayMerchOrderNo());
		    mistake.setPayOrderNo(doubt.getPayOrderNo());
		    mistake.setPayTradeNo(doubt.getPayTradeNo());
		    mistake.setPayTradeStatus(doubt.getPayTradeStatus());
		    mistake.setPayTradeAmount(doubt.getPayTradeAmount());
		    mistake.setPayOrderTime(doubt.getPayOrderTime());
		    mistake.setPaySuccessTime(doubt.getPaySuccessTime());
		    /** 退款订单信息 */
		    mistake.setRefundMerchOrderNo(doubt.getRefundMerchOrderNo());
		    mistake.setRefundOrderNo(doubt.getRefundOrderNo());
		    mistake.setRefundTradeNo(doubt.getRefundTradeNo());
		    mistake.setRefundTradeStatus(doubt.getRefundTradeStatus());
		    mistake.setRefundTradeAmount(doubt.getRefundTradeAmount());
		    mistake.setRefundApplyTime(doubt.getRefundApplyTime());
		    mistake.setRefundSuccessTime(doubt.getRefundSuccessTime()); 
		}

		/** 错误-支付渠道侧数据 */
		if (billItem != null) {			
		    mistake.setChannelRefundOrderNo(billItem.getRefundOrderNo());
		    mistake.setChannelRefundTradeNo(billItem.getRefundTradeNo());
		    mistake.setChannelRefundTradeStatus(billItem.getRefundStatus());
		    mistake.setChannelRefundTradeAmount(billItem.getRefundAmount());
		    mistake.setChannelRefundApplyTime(billItem.getRefundApplyTime());
		    mistake.setChannelRefundSuccessTime(billItem.getRefundSuccessTime());  
		}
		mistake.setNotifyStatus(MistakeNotifyStatusEnum.wait_send.name());
		return mistake;
	}

	/**
	 * 创建对账存疑记录
	 * 注意：数据既来自支付订单，也可以来自退款订单
	 * @param payOrder 支付订单
	 * @param refundOrder 退款订单
	 * @param batch  对账批次
	 * @return
	 */
	public BillBizDoubt makeBizDoubt(PayPaymentOrder payOrder, PayRefundOrder refundOrder, BillBizBatch batch) {
		BillBizDoubt doubt = new BillBizDoubt();		
		initEntityValue(doubt);		
		doubt.setBatchNo(batch.getBatchNo());
		doubt.setBillDate(batch.getBillDate());
		doubt.setCurrency(CurrencyEnum.CNY.name());		
		if(payOrder!=null){
			doubt.setBillType(BillTypeEnum.pay.name());
		    doubt.setPayChannel(payOrder.getChannel());
		    doubt.setPayWay(payOrder.getPayWay());
		    doubt.setChannelAppId(payOrder.getChannelAppId());
		    doubt.setChannelMerchantId(payOrder.getChannelMerchantId());
		    doubt.setMerchantCode(payOrder.getMerchantCode());
		    doubt.setMerchantName(payOrder.getMerchantName());
		    doubt.setMerchantAppCode(payOrder.getMerchantAppCode());
		    doubt.setMerchantAppName(payOrder.getMerchantAppName());
		    doubt.setMerchantCommodity(payOrder.getProductName());	
		    doubt.setPayMerchOrderNo(payOrder.getMerchantOrderNo());
		    doubt.setPayOrderNo(payOrder.getOrderNo());
		    doubt.setPayTradeNo(payOrder.getTradeNo());
		    doubt.setPayTradeAmount(payOrder.getAmount());
		    doubt.setPayTradeStatus(payOrder.getStatus());
		    doubt.setPayOrderTime(payOrder.getOrderTime());
		    doubt.setPaySuccessTime(payOrder.getFinishTime());
		}
		if(refundOrder!=null){
			doubt.setBillType(BillTypeEnum.refund.name());
		    doubt.setPayChannel(refundOrder.getChannel());
		    doubt.setPayWay(refundOrder.getPayWay());
		    doubt.setChannelAppId(refundOrder.getChannelAppId());
		    doubt.setChannelMerchantId(refundOrder.getChannelMerchantId());	  
		    doubt.setMerchantCode(refundOrder.getMerchantCode());
		    doubt.setMerchantName(refundOrder.getMerchantName());
		    doubt.setMerchantAppCode(refundOrder.getMerchantAppCode());
		    doubt.setMerchantAppName(refundOrder.getMerchantAppName());
		    doubt.setMerchantCommodity(refundOrder.getProductName());
		    doubt.setPayMerchOrderNo(refundOrder.getMerchantOrderNo());
		    doubt.setPayOrderNo(refundOrder.getOrderNo());
		    doubt.setPayTradeNo(refundOrder.getTradeNo());
		    doubt.setPayTradeAmount(refundOrder.getAmount());
		    doubt.setPayTradeStatus(refundOrder.getStatus());
		    doubt.setPayOrderTime(refundOrder.getOrderTime());
		    doubt.setPaySuccessTime(refundOrder.getFinishTime());
		    doubt.setRefundMerchOrderNo(refundOrder.getRefundMerchantNo());
		    doubt.setRefundOrderNo(refundOrder.getRefundNo());
		    doubt.setRefundTradeNo(refundOrder.getRefundTradeNo());
		    doubt.setRefundTradeAmount(refundOrder.getRefundAmount());
		    doubt.setRefundTradeStatus(refundOrder.getRefundStatus());
		    doubt.setRefundApplyTime(refundOrder.getRefundTime());
		    doubt.setRefundSuccessTime(refundOrder.getRefundFinishTime());
		}
		return doubt;
	}

	/**
	 * 创建内部账单明细记录
	 * 注意：数据来自当日的支付订单、退款订单，也可能来自存疑订单（包含支付与退款两种类型）
	 * @param payOrder	支付订单
	 * @param refundOrder	退款订单
	 * @param doubt     存疑订单（包含支付订单、退款订单）
	 * @param batch     对账批次
	 * @return
	 */
	public BillBizItem makeBillBizItem(PayPaymentOrder payOrder, PayRefundOrder refundOrder, BillBizDoubt doubt, BillBizBatch batch) {
		
		BillBizItem bizItem = new BillBizItem();		
		initEntityValue(bizItem);
		bizItem.setBatchNo(batch.getBatchNo());
		bizItem.setBillDate(batch.getBillDate());
		bizItem.setPayChannel(batch.getPayChannel());
		bizItem.setCurrency(CurrencyEnum.CNY.name());	
		/** 数据来自支付订单记录 */
		if(payOrder!=null){
			bizItem.setBillType(BillTypeEnum.pay.name());
			bizItem.setPayWay(payOrder.getPayWay());
			bizItem.setChannelAppId(payOrder.getChannelAppId());
			bizItem.setChannelMerchantId(payOrder.getChannelMerchantId());	  
			bizItem.setMerchantCode(payOrder.getMerchantCode());
			bizItem.setMerchantName(payOrder.getMerchantName());
		    bizItem.setMerchantAppCode(payOrder.getMerchantAppCode());
		    bizItem.setMerchantAppName(payOrder.getMerchantAppName());
		    bizItem.setMerchantCommodity(payOrder.getProductName());	
		    bizItem.setPayMerchOrderNo(payOrder.getMerchantOrderNo());
		    bizItem.setPayOrderNo(payOrder.getOrderNo());
		    bizItem.setPayTradeNo(payOrder.getTradeNo());
		    bizItem.setPayTradeAmount(payOrder.getAmount());
		    bizItem.setPayOrderTime(payOrder.getOrderTime());
		    bizItem.setPaySuccessTime(payOrder.getFinishTime());
		    bizItem.setSource(BizItemSourceEnum.regular.name());
		}		
		/** 数据来自退款订单记录 */
		if(refundOrder!=null){
			bizItem.setBillType(BillTypeEnum.refund.name());
			bizItem.setPayWay(refundOrder.getPayWay());
			bizItem.setChannelAppId(refundOrder.getChannelAppId());
			bizItem.setChannelMerchantId(refundOrder.getChannelMerchantId());	  
			bizItem.setMerchantCode(refundOrder.getMerchantCode());
			bizItem.setMerchantName(refundOrder.getMerchantName());
		    bizItem.setMerchantAppCode(refundOrder.getMerchantAppCode());
		    bizItem.setMerchantAppName(refundOrder.getMerchantAppName());
		    bizItem.setMerchantCommodity(refundOrder.getProductName());	
		    bizItem.setPayMerchOrderNo(refundOrder.getMerchantOrderNo());
		    bizItem.setPayOrderNo(refundOrder.getOrderNo());
		    bizItem.setPayTradeNo(refundOrder.getTradeNo());
		    bizItem.setPayTradeAmount(refundOrder.getAmount());
		    bizItem.setPayOrderTime(refundOrder.getOrderTime());
		    bizItem.setPaySuccessTime(refundOrder.getFinishTime());		    
		    bizItem.setRefundMerchOrderNo(refundOrder.getRefundMerchantNo());
		    bizItem.setRefundOrderNo(refundOrder.getRefundNo());
		    bizItem.setRefundTradeNo(refundOrder.getRefundTradeNo());
		    bizItem.setRefundTradeAmount(refundOrder.getRefundAmount());
		    bizItem.setRefundApplyTime(refundOrder.getRefundTime());
		    bizItem.setRefundSuccessTime(refundOrder.getRefundFinishTime());
		    bizItem.setSource(BizItemSourceEnum.regular.name());
		}		
		/** 数据来自存疑记录 */
		if(doubt!=null){
			bizItem.setBillType(doubt.getBillType());
			bizItem.setPayWay(doubt.getPayWay());
			bizItem.setChannelAppId(doubt.getChannelAppId());
			bizItem.setChannelMerchantId(doubt.getChannelMerchantId());	  
			bizItem.setMerchantCode(doubt.getMerchantCode());
			bizItem.setMerchantName(doubt.getMerchantName());
		    bizItem.setMerchantAppCode(doubt.getMerchantAppCode());
		    bizItem.setMerchantAppName(doubt.getMerchantAppName());
		    bizItem.setMerchantCommodity(doubt.getMerchantCommodity());	 
		    bizItem.setPayMerchOrderNo(doubt.getPayMerchOrderNo());
		    bizItem.setPayOrderNo(doubt.getPayOrderNo());
		    bizItem.setPayTradeNo(doubt.getPayTradeNo());
		    bizItem.setPayTradeAmount(doubt.getPayTradeAmount());
		    bizItem.setPayOrderTime(doubt.getPayOrderTime());
		    bizItem.setPaySuccessTime(doubt.getPaySuccessTime());
		    bizItem.setRefundMerchOrderNo(doubt.getRefundMerchOrderNo());
		    bizItem.setRefundOrderNo(doubt.getRefundOrderNo());
		    bizItem.setRefundTradeNo(doubt.getRefundTradeNo());
		    bizItem.setRefundTradeAmount(doubt.getRefundTradeAmount());
		    bizItem.setRefundApplyTime(doubt.getRefundApplyTime());
		    bizItem.setRefundSuccessTime(doubt.getRefundSuccessTime());
		    bizItem.setSource(BizItemSourceEnum.doubt.name());
		}		
		
		return bizItem;
	}
	
	/**
	 * 创建内部账单明细记录
	 * 注意：数据来自差错记录
	 * @param mistake
	 * @param handleWay
	 * @return
	 */
	public BillBizItem makeBillBizItem(BillBizMistake mistake, String handleWay) {		
		BillBizItem bizItem = new BillBizItem();		
		initEntityValue(bizItem);
		bizItem.setBatchNo(mistake.getBatchNo());
		bizItem.setBillDate(mistake.getBillDate());
		bizItem.setPayChannel(mistake.getPayChannel());
		bizItem.setCurrency(CurrencyEnum.CNY.name());
		bizItem.setSource(BizItemSourceEnum.mistake.name());//来自差错处理
		bizItem.setBillType(mistake.getBillType());
		bizItem.setPayWay(mistake.getPayWay());
		bizItem.setChannelAppId(mistake.getChannelAppId());
		bizItem.setChannelMerchantId(mistake.getChannelMerchantId());	 
		bizItem.setMerchantCode(mistake.getMerchantCode());
		bizItem.setMerchantName(mistake.getMerchantName());
	    bizItem.setMerchantAppCode(mistake.getMerchantAppCode());
	    bizItem.setMerchantAppName(mistake.getMerchantAppName());
	    bizItem.setMerchantCommodity(mistake.getMerchantCommodity());	 
	    bizItem.setPayMerchOrderNo(mistake.getPayMerchOrderNo());
	    bizItem.setRefundMerchOrderNo(mistake.getRefundMerchOrderNo());
		/** 以支付渠道为准 */
		if(MistakeHandleWayEnum.channel.name().equals(handleWay)){		
			//支付
			bizItem.setPayOrderNo(mistake.getChannelOrderNo());
		    bizItem.setPayTradeNo(mistake.getChannelTradeNo());
		    bizItem.setPayOrderTime(mistake.getChannelTradeOrderTime());
		    bizItem.setPaySuccessTime(mistake.getChannelTradeSuccessTime());
		    bizItem.setPayTradeAmount(mistake.getChannelTradeAmount());	
		    //退款
		    bizItem.setRefundOrderNo(mistake.getChannelRefundOrderNo());
		    bizItem.setRefundTradeNo(mistake.getChannelRefundTradeNo());		    
		    bizItem.setRefundApplyTime(mistake.getChannelRefundApplyTime());
		    bizItem.setRefundSuccessTime(mistake.getChannelRefundSuccessTime());
		    bizItem.setRefundTradeAmount(mistake.getChannelRefundTradeAmount());
		}		
		/** 以本地系统为准 */
		else if(MistakeHandleWayEnum.local.name().equals(handleWay)){	
			//支付
			bizItem.setPayOrderNo(mistake.getPayOrderNo());
		    bizItem.setPayTradeNo(mistake.getPayTradeNo());
		    bizItem.setPayOrderTime(mistake.getPayOrderTime());
		    bizItem.setPaySuccessTime(mistake.getPaySuccessTime());
		    bizItem.setPayTradeAmount(mistake.getPayTradeAmount());	
		    //退款
		    bizItem.setRefundOrderNo(mistake.getRefundOrderNo());
		    bizItem.setRefundTradeNo(mistake.getRefundTradeNo());		    
		    bizItem.setRefundApplyTime(mistake.getRefundApplyTime());
		    bizItem.setRefundSuccessTime(mistake.getRefundSuccessTime());
		    bizItem.setRefundTradeAmount(mistake.getRefundTradeAmount());
		}
		
		return bizItem;
	}

	
	/**
	 * 初始化公共属性值
	 * @param entity
	 */
	public void initEntityValue(Entity entity){
		entity.setCreater(EntityCreaterEnum.job.name());
		entity.setCreateTime(new Date());
		entity.setVersion(0);
	}


}
