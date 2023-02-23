package com.fc.pay.bill.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.bill.business.BillBizEntityBusiness;
import com.fc.pay.bill.business.BillBizNotifyBusiness;
import com.fc.pay.bill.constant.BillConstant;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.enums.BillTypeEnum;
import com.fc.pay.bill.enums.MistakeHandleWayEnum;
import com.fc.pay.bill.enums.MistakeHandleStatusEnum;
import com.fc.pay.bill.enums.MistakeNotifyStatusEnum;
import com.fc.pay.bill.enums.MistakeTypeEnum;
import com.fc.pay.bill.service.BillBizBatchService;
import com.fc.pay.bill.service.BillBizCheckTranxService;
import com.fc.pay.bill.service.BillBizDoubtService;
import com.fc.pay.bill.service.BillBizFileNotifyService;
import com.fc.pay.bill.service.BillBizItemService;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.bill.service.BillBizSummaryService;
import com.fc.pay.bill.vo.BillBizCheckProgressData;
import com.fc.pay.common.core.enums.BillStatusEnum;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.IRefundOrder;

/**
 * 业务对账数据事务服务接口实现
 * 
 * @author zhanjq
 *
 */
@Service("billBizCheckTranxService")
public class BillCheckTranxServiceImpl implements BillBizCheckTranxService {
	
	private static final Logger log = LoggerFactory.getLogger(BillCheckTranxServiceImpl.class);
	
	@Autowired
	private BillBizEntityBusiness entityBusiness;
	
	@Autowired
	private BillBizSummaryService summaryService;
	
	@Autowired
	private BillBizFileNotifyService fileNotifyService;
	
	@Autowired
	private BillBizBatchService batchService;
	
	@Autowired
	private BillBizDoubtService doubtService;
	
	@Autowired
	private BillBizMistakeService mistakeService;
	
	@Autowired
	private BillBizItemService itemService;
	
	@Autowired
	private BillBizNotifyBusiness notifyBusiness;
	
	@Autowired
	private IPaymentOrder payOrderService;
	
	@Autowired
	private IRefundOrder refundOrderService;

	@Transactional
	public void handleBatchCheckResult(BillBizSummary summary, BillBizBatch batch,
			List<BillBizMistake> mistakeList,
			List<BillBizDoubt> insertDoubtList,	List<BillBizDoubt> removeDoubtList, 
			List<BillBizItem> bizItemList) {
		if(BatchStatusEnum.success.name().equals(batch.getHandleStatus())){
			summary.setBatchRunSuccessCount(summary.getBatchRunSuccessCount()+1);
			summaryService.modifyBatchRunSuccessCount(summary.getId(), summary.getBatchRunSuccessCount());
		}
		batchService.modify(batch);
		mistakeService.addBatch(mistakeList);
		doubtService.addBatch(insertDoubtList);
		doubtService.deleteBatch(removeDoubtList);
		itemService.addBatch(bizItemList);		
		/** 修改 对账状态-支付 */
		Map<String, ArrayList<String>> payMap = entityBusiness.makePayBillStatusMap(bizItemList,insertDoubtList,mistakeList);
		payOrderService.modifyBillStatusByMap(payMap);
		/** 修改 对账状态-退款 */
		Map<String, ArrayList<String>> refundMap = entityBusiness.makeRefundBillStatusMap(bizItemList,insertDoubtList,mistakeList);
		refundOrderService.modifyBillStatusByMap(refundMap);
	}

	/**
	 * 处理缓存周期外的存疑数据
	 */
	@Transactional
	public void handleDoubtInPeriod(List<BillBizDoubt> removeDoubtlist,
			List<BillBizMistake> insertMistakeList) {
		doubtService.deleteBatch(removeDoubtlist);
		mistakeService.addBatch(insertMistakeList);
		//修改交易记录对账状态
	}
	
	/**
	 * 对账差错处理
	 * @param userName
	 * @param mistakeId
	 * @param handleType
	 * @param handleRemark
	 * @throws Exception
	 */
	@Transactional
	public void handleMistake(String userName, String mistakeId, String handleType,
			String handleRemark) throws Exception {
		BillBizMistake mistake = mistakeService.get(Long.valueOf(mistakeId));
		mistake.setHandleUser(userName);//取当前登录用户名
		mistake.setHandleTime(new Date());
		mistake.setHandleWay(handleType);	
		mistake.setHandleStatus(MistakeHandleStatusEnum.handled.name());
		mistake.setHandleRemark(handleRemark);
		/**--------以本地系统为基准则就此结束--------**/
		if(MistakeHandleWayEnum.channel.name().equals(handleType)){			
			handleMistakeOnChannel(mistake);
		}else if(MistakeHandleWayEnum.local.name().equals(handleType)){
			handleMistakeOnLocal(mistake);
		}		
	}

	/**
	 * 差错处理：以支付渠道为准
	 * @param mistake
	 * @throws Exception 
	 */
	@Transactional
	public void handleMistakeOnChannel(BillBizMistake mistake) throws Exception{
		/**--------以支付渠道为基准调整数据---------**/
		switch(mistake.getMistakeType()){
		//渠道漏单[渠道不存在该交易]
		case "channel_miss":
			/**>>>>将交易状态改为失败>>>>*/
			handleTradeToFail(mistake);
			break;
		//本地漏单[本地不存在该交易]
		case "local_miss":
			/**>>>>支付渠道订单来源是否安全？本地系统是否存在误删除订单情况？是否补单?>>>>*/
			break;
		//本地短款，状态不符[本地支付不成功但渠道支付成功(比较常见)]
		case "local_status_less":
			/**>>>>将交易状态改为成功>>>>*/
			handleTradeToSuccess(mistake);
			//差错处理产生一条账单明细记录
			itemService.add(entityBusiness.makeBillBizItem(mistake, MistakeHandleWayEnum.channel.name()));	
			break;
		//本地长款,状态不符[本地支付成功但渠道支付不成功(基本不会出现)]
		case "local_status_more":
			/**>>>>将交易状态改为失败>>>>*/
			handleTradeToFail(mistake);
			break;
		//本地短款，金额不符[本地交易金额比渠道交易金额少(基本不会出现)]
		case "local_cash_less":
			/**>>>>增加交易金额>>>>*/
			//必须先处理交易状态不符的情况
			modifyTradeAmount(mistake);			
			break;
		//本地长款,金额不符[本地交易金额比渠道交易金额多]
		case "local_cash_more":
			/**>>>>减少交易金额>>>>*/
			//必须先处理交易状态不符的情况
			modifyTradeAmount(mistake);
			break;
		default:
			break;		
		}		
		try {
			notifyBusiness.notifyMistakeHandled(mistake);
		} catch (Exception e) {
			log.error("发送差错处理通知错误["+e.getMessage()+"]");
			mistake.setNotifyStatus(MistakeNotifyStatusEnum.send_fail.name());
			mistakeService.modify(mistake);
			return;
		}
		mistake.setNotifyStatus(MistakeNotifyStatusEnum.send_success.name());
		mistakeService.modify(mistake);
	}
	
	/**
	 * 差错处理：以本地系统为准
	 * @param mistake
	 * @throws Exception 
	 */
	@Transactional
	public void handleMistakeOnLocal(BillBizMistake mistake) throws Exception{
		
		/**--------以本地系统为基准调整数据---------**/
		/** 处理状态，金额，内部账单明细记录 */
		switch(mistake.getMistakeType()){
		//渠道漏单[渠道不存在该交易]
		case "channel_miss":
			//差错处理产生一条账单明细记录
			itemService.add(entityBusiness.makeBillBizItem(mistake, MistakeHandleWayEnum.local.name()));
			break;
		//本地漏单[本地不存在该交易]
		case "local_miss":
			break;
		//本地短款，状态不符[本地支付不成功但渠道支付成功(比较常见)]
		case "local_status_less":	
			break;
		//本地长款,状态不符[本地支付成功但渠道支付不成功(基本不会出现)]
		case "local_status_more":
			//差错处理产生一条账单明细记录
			itemService.add(entityBusiness.makeBillBizItem(mistake, MistakeHandleWayEnum.local.name()));
			break;
		//本地短款，金额不符[本地交易金额比渠道交易金额少(基本不会出现)]
		case "local_cash_less":
			break;
		//本地长款,金额不符[本地交易金额比渠道交易金额多]
		case "local_cash_more":
			break;
		default:
			break;		
		}	
		/** 处理交易记录对账状态  */
		switch(mistake.getBillType()){
		//支付差错
		case BillConstant.KEY_PAY:
			//枫车支付订单号
			String payOrderNo = mistake.getPayOrderNo();
			if(!mistakeService.hasMoreMistakeToHandle(BillTypeEnum.pay.name(), payOrderNo)){
				payOrderService.modifyBillStatus(payOrderNo, BillStatusEnum.balance.name());
			}			
			break;
		//退款差错
		case BillConstant.KEY_REFUND:
			//枫车退款订单号
			String refundOrderNo = mistake.getRefundOrderNo();
			if(!mistakeService.hasMoreMistakeToHandle(BillTypeEnum.refund.name(), refundOrderNo)){
				refundOrderService.modifyBillStatus(refundOrderNo, BillStatusEnum.balance.name());
			}			
			break;
		default:
			break;
		}
		
		try {
			notifyBusiness.notifyMistakeHandled(mistake);
		} catch (Exception e) {
			log.error("发送差错处理通知错误["+e.getMessage()+"]");
			mistake.setNotifyStatus(MistakeNotifyStatusEnum.send_fail.name());
			mistakeService.modify(mistake);
			return;
		}
		mistake.setNotifyStatus(MistakeNotifyStatusEnum.send_success.name());
		mistakeService.modify(mistake);
	}	
	
	@Override
	@Transactional
	public void handleTradeToFail(BillBizMistake mistake) {
		switch(mistake.getBillType()){
		//支付差错
		case BillConstant.KEY_PAY:
			//枫车支付订单号
			String payOrderNo = mistake.getPayOrderNo();
			//修改交易状态为失败
			payOrderService.changStatusFail(payOrderNo, null);
			if(!mistakeService.hasMoreMistakeToHandle(BillTypeEnum.pay.name(), payOrderNo)){
				payOrderService.modifyBillStatus(payOrderNo, BillStatusEnum.balance.name());
			}			
			break;
		//退款差错
		case BillConstant.KEY_REFUND:
			//枫车退款订单号
			String refundOrderNo = mistake.getRefundOrderNo();
			//修改交易状态为失败
			refundOrderService.changStatusFail(refundOrderNo, null);
			if(!mistakeService.hasMoreMistakeToHandle(BillTypeEnum.refund.name(), refundOrderNo)){
				refundOrderService.modifyBillStatus(refundOrderNo, BillStatusEnum.balance.name());
			}			
			break;
		default:
			break;
		}
	}

	@Transactional
	public void handleTradeToSuccess(BillBizMistake mistake) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("trade_no", mistake.getChannelTradeNo());
		data.put("buyer_id", null);
		data.put("merchant_code", mistake.getMerchantCode());
		switch(mistake.getBillType()){
		//支付差错
		case BillConstant.KEY_PAY:
			//枫车支付订单号
			String payOrderNo = mistake.getPayOrderNo();
			//修改交易状态为成功
			payOrderService.changStatusSuccess(payOrderNo, data);
			if(!mistakeService.hasMoreMistakeToHandle(BillTypeEnum.pay.name(), payOrderNo)){
				payOrderService.modifyBillStatus(payOrderNo, BillStatusEnum.balance.name());
			}			
			break;
		//退款差错
		case BillConstant.KEY_REFUND:
			//枫车退款订单号
			String refundOrderNo = mistake.getRefundOrderNo();
			//修改交易状态为成功
			refundOrderService.changStatusSuccess(refundOrderNo, data);
			if(!mistakeService.hasMoreMistakeToHandle(BillTypeEnum.refund.name(), refundOrderNo)){
				refundOrderService.modifyBillStatus(refundOrderNo, BillStatusEnum.balance.name());
			}			
			break;
		default:
			break;
		}
	}

	@Transactional
	public void modifyTradeAmount(BillBizMistake mistake) throws Exception {
		String mistakeType= mistake.getMistakeType();
		switch(mistake.getBillType()){
		/** 支付差错 */
		case BillConstant.KEY_PAY:
			//枫车支付订单号
			String payOrderNo = mistake.getPayOrderNo();
			PayPaymentOrder payOrder = payOrderService.getByOrderNo(payOrderNo);
			if(!TradeStatusEnum.success.name().equals(payOrder.getStatus())){
				throw new Exception("请先处理该订单状态不符的差错");
			}
			BigDecimal payAmount = mistake.getPayTradeAmount();
			BigDecimal channelPayAmount = mistake.getChannelTradeAmount();
			BigDecimal subPayAmount = channelPayAmount.subtract(payAmount).abs();			
			/** 本地长款,金额不符  --- 本地交易金额比渠道交易金额多 >>> 减少本地订单金额 */
			if(MistakeTypeEnum.local_cash_more.name().equals(mistakeType)){
				log.info("本地支付订单金额减少["+subPayAmount+"]");
			}
			/** 本地短款，金额不符 --- 本地交易金额比渠道交易金额少 >>> 增加本地订单金额 */
			else if(MistakeTypeEnum.local_cash_less.name().equals(mistakeType)){
				log.info("本地支付订单金额增加["+subPayAmount+"]");
			}			
			payOrderService.changAmount(payOrderNo, channelPayAmount, null);//使用渠道账单金额
			if(!mistakeService.hasMoreMistakeToHandle(BillTypeEnum.pay.name(), payOrderNo)){
				payOrderService.modifyBillStatus(payOrderNo, BillStatusEnum.balance.name());
			}
			itemService.modifyPayAmount(payOrderNo, channelPayAmount);
			break;
		/** 退款差错 */
		case BillConstant.KEY_REFUND:
			//枫车退款订单号
			String refundOrderNo = mistake.getRefundOrderNo();
			PayRefundOrder refundOrder = refundOrderService.getByRefundNo(refundOrderNo);
			if(!TradeStatusEnum.success.name().equals(refundOrder.getRefundStatus())){
				throw new Exception("请先处理该订单状态不符的差错");
			}
			BigDecimal refundAmount = mistake.getRefundTradeAmount();
			BigDecimal channelRefundAmount = mistake.getChannelRefundTradeAmount();
			BigDecimal subRefundAmount = channelRefundAmount.subtract(refundAmount).abs();
			/** 本地长款,金额不符  --- 本地交易金额比渠道交易金额多 >>> 减少本地订单金额 */
			if(MistakeTypeEnum.local_cash_more.name().equals(mistakeType)){
				log.info("本地退款订单金额减少["+subRefundAmount+"]");
			}
			/** 本地短款，金额不符 --- 本地交易金额比渠道交易金额少 >>> 增加本地订单金额 */
			else if(MistakeTypeEnum.local_cash_less.name().equals(mistakeType)){
				log.info("本地退款订单金额增加["+subRefundAmount+"]");
			}			
			refundOrderService.changAmount(refundOrderNo, channelRefundAmount, null);//使用渠道账单金额
			if(!mistakeService.hasMoreMistakeToHandle(BillTypeEnum.refund.name(), refundOrderNo)){
				refundOrderService.modifyBillStatus(refundOrderNo, BillStatusEnum.balance.name());
			}
			itemService.modifyRefundAmount(refundOrderNo, channelRefundAmount);
			break;
		default:
			break;
		}
	}


	@Transactional
	public int addBillBizBatch(BillBizBatch billBizBatch) {
		return batchService.add(billBizBatch);
	}

	@Transactional
	public int modifyBillBizBatch(BillBizBatch billBizBatch) {
		return batchService.modify(billBizBatch);
	}

	@Transactional
	public List<BillBizBatch> addBillBizBatchList(List<BillBizBatch> batchList) {
		/**
		for(BillBizBatch batch : batchList){
			batchService.add(batch);
		}
		*/
		return batchService.addBatch(batchList);
	}

	@Transactional
	public BillBizCheckProgressData prepareProgressData(Date billDate, List<MerchantApp> merchantAppList, List<MerchantAppConfig> macList) {		
		BillBizCheckProgressData data = new BillBizCheckProgressData();				
		//查询是否存在，如果存在，则直接返回
		BillBizSummary summary = summaryService.getByBillDate(billDate);
		if(summary!=null){
			data.setSummary(summary);
			data.setBatchList(batchService.listByBillDate(billDate));
			data.setFileNotifyList(fileNotifyService.listByBillDate(billDate));
			return data;
		}
		//如果不存在，则创建新的过程记录
		//对账批次
		List<BillBizBatch> batchList = new ArrayList<BillBizBatch>();
		for(MerchantAppConfig mac : macList){
			batchList.add(entityBusiness.makeBillBizBatch(mac, billDate));
		}
		batchService.addBatch(batchList);
		data.setBatchList(batchList);		
		//对账文件通知
	    List<BillBizFileNotify> fileNotifyList = new ArrayList<BillBizFileNotify>();
	    for(MerchantApp merchantApp : merchantAppList){
	    	fileNotifyList.add(entityBusiness.makeBillBizFileNotify(merchantApp, billDate));
	    }
	    fileNotifyService.addBatch(fileNotifyList);
	    data.setFileNotifyList(fileNotifyList);		
	    //对账汇总
		summary = entityBusiness.makeBillBizSummary(billDate,batchList.size(),merchantAppList.size());
		summaryService.add(summary);		
		data.setSummary(summary);
		
		return data;
	}

	@Override
	public void handleBatchCheckResultForTestVerify(BillBizBatch batch,
			List<BillBizMistake> mistakeList,
			List<BillBizDoubt> insertDoubtList,
			List<BillBizDoubt> removeDoubtList, List<BillBizItem> bizItemList) {
		/**
		if(BatchStatusEnum.success.name().equals(batch.getHandleStatus())){
			summary.setBatchRunSuccessCount(summary.getBatchRunSuccessCount()+1);
			summaryService.modifyBatchRunSuccessCount(summary.getId(), summary.getBatchRunSuccessCount());
		}
		*/
		batchService.modify(batch);
		mistakeService.addBatch(mistakeList);
		doubtService.addBatch(insertDoubtList);
		doubtService.deleteBatch(removeDoubtList);
		itemService.addBatch(bizItemList);
		/** 修改 对账状态-支付 */
		Map<String, ArrayList<String>> payMap = entityBusiness.makePayBillStatusMap(bizItemList,insertDoubtList,mistakeList);
		payOrderService.modifyBillStatusByMap(payMap);
		/** 修改 对账状态-退款 */
		Map<String, ArrayList<String>> refundMap = entityBusiness.makeRefundBillStatusMap(bizItemList,insertDoubtList,mistakeList);
		refundOrderService.modifyBillStatusByMap(refundMap);
		
	}
	
	
	

}
