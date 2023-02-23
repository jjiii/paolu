package com.fc.pay.bill.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fc.pay.bill.constant.BillConstant;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.enums.BillTypeEnum;
import com.fc.pay.bill.enums.MistakeTypeEnum;
import com.fc.pay.bill.service.BillBizBatchService;
import com.fc.pay.bill.service.BillBizCheckTranxService;
import com.fc.pay.bill.service.BillBizSummaryService;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;
import com.fc.pay.common.core.enums.TradeStatusEnum;

/**
 * 业务日对账-批次逻辑
 * @author zhanjq
 *
 */
@Service
public class BillBizCheckBatchBusiness {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizCheckBatchBusiness.class);
	
	/**
	 * 业务日对账数据加载服务
	 */
	@Autowired
	private LocalDataBusiness dataBusiness;
	
	/**
	 * 实体数据制作组件
	 */
	@Autowired
	private BillBizEntityBusiness entityBusiness;
	
	/**
	 * 对账存疑缓冲组件
	 */
	@Autowired
	private BillBizDoubtBusiness poolBusiness;
	
	/**
	 * 业务日对账账单下载组件
	 */
	@Autowired
	private BillBizDownloadBusiness billBizDownloadBusiness;
	
	/**
	 * 业务日对账账单解析组件
	 */
	@Autowired
	private BillBizParserBusiness parserBusiness;
	
	/**
	 * 业务日对账数据事务服务
	 */
	@Autowired
	private BillBizCheckTranxService tranxService;
	
	@Autowired
	private BillBizSummaryService summaryService;
	
	@Autowired
	private BillBizBatchService batchService;
	

	/**
	 * 验证对账批次是否可以执行
	 * @param batch
	 * @return
	 * @throws Exception
	 */
	public boolean canExecuteBillBizBatch(BillBizBatch batch){
		if(batch==null){
			return false;
		}
		if(BatchStatusEnum.success.name().equals(batch.getHandleStatus())){
			log.info("该对账批次处理状态为已成功，不允许重复对账");
			return false;
		}
		return true;
	}
	
	/**
	 * 重启对账批次
	 * @param batch
	 * @return
	 */
	public boolean recheckBatch(String batchNo) {
		BillBizBatch batch = batchService.getByBatchNo(batchNo);
		batch = entityBusiness.resetBillBizBatch(batch);
		BillBizSummary summary = summaryService.getByBillDate(batch.getBillDate());
		return checkBatch(batch, summary);
	}
	
	/**
	 * 模拟错误账单对账，仅供测试验证第三方账单渠道错误场景
	 * @param channel	支付渠道
	 * @param channelMerchantId	渠道对应mchId
	 * @param channelAppId	渠道对应appId
	 * @param billDate	账单日期
	 * @param channelBillStorePath	账单文件路径
	 * @return
	 */
	public boolean checkBatchForTestVerify(String channel, String channelMerchantId, String channelAppId, Date billDate, String channelBillStorePath){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payChannel", channel);
		paramMap.put("channelMerchantId", channelMerchantId);
		paramMap.put("channelAppId", channelAppId);
		paramMap.put("billDate", billDate);		
		BillBizBatch batch = batchService.getByMap(paramMap);
		if(batch==null){
			batch = entityBusiness.makeBillBizBatchForTestVerify(channel, channelMerchantId, channelAppId, billDate, channelBillStorePath);
		}
		/** 0.检查是否可运行该对账批次*/
		if(!canExecuteBillBizBatch(batch)){
			return false;
		}
		
		/** 1.下载对账单 */
		/**
		log.info("对账文件下载开始>>>");
		try {
			billBizDownloadBusiness.downloadOutsideBizBill(batch);
		} catch (Exception e) {
			log.error("账单下载查询异常："+e.getMessage());
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			if(StringUtils.isEmpty(batch.getHandleRemark()) || BatchStatusEnum.init.getDesc().equals(batch.getHandleRemark())){
				batch.setHandleRemark("BillBizCheckBatchBusiness>>>"+e.getMessage());
			}
			tranxService.modifyBillBizBatch(batch);
			return false;
		}		
		log.info("是否完成账单下载>>>"+!BatchStatusEnum.fail.name().equals(batch.getHandleStatus()));
		tranxService.modifyBillBizBatch(batch);
		if(BatchStatusEnum.fail.name().equals(batch.getHandleStatus())){
			return false;
		}
		log.info("<<<对账文件下载结束");
		*/
		/** 2.解析对账单 */
		log.info("对账文件解析开始>>>");
		Map<String, List<OutsideDailyBizBillItem>> billDataMap = null;
		try {
			billDataMap = parserBusiness.parseBizBill(batch);
		} catch (Exception e) {
			log.error("账单解析异常："+e.getMessage());
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			if(StringUtils.isEmpty(batch.getHandleRemark()) || BatchStatusEnum.init.getDesc().equals(batch.getHandleRemark())){
				batch.setHandleRemark(e.getMessage());
			}
			tranxService.modifyBillBizBatch(batch);
			return false;
		}		
		log.info("是否完成账单解析>>>"+!BatchStatusEnum.fail.name().equals(batch.getHandleStatus()));
		tranxService.modifyBillBizBatch(batch);
		if(BatchStatusEnum.fail.name().equals(batch.getHandleStatus())){
			return false;
		}
		log.info("<<<对账文件解析结束");	
		
		/** 账单明细数据-支付 **/
		List<OutsideDailyBizBillItem> payBillItemList = billDataMap.get(BillConstant.KEY_PAY);		
		/** 账单明细数据-退款 **/
		List<OutsideDailyBizBillItem> refundBillItemList = billDataMap.get(BillConstant.KEY_REFUND);
		
		/** 本地数据-支付 **/
		// 查询本地成功支付订单
		List<PayPaymentOrder> sucessPayList = dataBusiness.findSuccessPayDataByBatch(batch);
		// 查询本地所有支付订单
		List<PayPaymentOrder> allPayList = dataBusiness.findAllPayDataByBatch(batch);		
		/** 本地数据-退款 **/
		// 查询本地成功退款订单
		List<PayRefundOrder> sucessRefundList = dataBusiness.findSuccessRefundDataByBatch(batch);
		// 查询本地所有退款订单
		List<PayRefundOrder> allRefundList = dataBusiness.findAllRefundDataByBatch(batch);
		/** 本地数据-存疑 */
		// 查询所有支付存疑记录
		List<BillBizDoubt> payDoubtInPeriodList = poolBusiness.findDoubtInPeriodList(BillTypeEnum.pay.name());
		// 查询所有退款存疑记录
		List<BillBizDoubt> refundDoubtInPeriodList = poolBusiness.findDoubtInPeriodList(BillTypeEnum.refund.name());
		// 待插入存疑list
		List<BillBizDoubt> insertDoubtList = new ArrayList<BillBizDoubt>();
		// 待删除存疑list
		List<BillBizDoubt> removeDoubtList = new ArrayList<BillBizDoubt>();
		/** 本地数据-差错 */
		// 差错list
		List<BillBizMistake> mistakeList = new ArrayList<BillBizMistake>();
		/** 本地数据-内部账单明细 */
		// 待插入账单明细list 
		List<BillBizItem> bizItemList = new ArrayList<BillBizItem>();
		
		/** 3.支付对账 */
		/** 3.1.以本地订单为基准与支付渠道对账单逐笔勾兑 */
		log.info("--------------------支付对账:以本地订单为基准:start-----------------");
		checkPayOnLocal(batch, sucessPayList, payBillItemList, mistakeList, insertDoubtList, bizItemList);
		log.info("--------------------支付对账:以本地订单为基准:end-----------------");
		/** 3.2.以支付渠道对账单为基准与本地订单逐笔勾兑 */
		log.info("--------------------支付对账:以支付渠道账单为基准:start-----------------");
		checkPayOnChannel(batch, allPayList, payBillItemList, payDoubtInPeriodList, mistakeList, removeDoubtList, bizItemList);
		log.info("--------------------支付对账:以支付渠道账单为基准:end-----------------");
		
		/** 4.退款对账 */
		/** 4.1.以本地订单为基准与支付渠道对账单逐笔勾兑 */
		log.info("--------------------退款对账:以本地订单为基准:start-----------------");
		checkRefundOnLocal(batch, sucessRefundList, refundBillItemList, mistakeList, insertDoubtList, bizItemList);
		log.info("--------------------退款对账:以本地订单为基准:end-----------------");	
		/** 4.2.以支付渠道对账单为基准与本地订单逐笔勾兑 */
		log.info("--------------------退款对账:以支付渠道账单为基准:start-----------------");
		checkRefundOnChannel(batch, allRefundList, refundBillItemList, refundDoubtInPeriodList, mistakeList, removeDoubtList, bizItemList);
		log.info("--------------------退款对账:以支付渠道账单为基准:end-----------------");
		
		batch.setEndTime(new Date());
	    batch.setHandleStatus(BatchStatusEnum.success.name());
	    batch.setHandleRemark(BatchStatusEnum.success.getDesc());
	    
	    log.info("待新增存疑记录列表size>>>"+(insertDoubtList!=null?insertDoubtList.size():0));
	    
		// 保存批次对账数据
		tranxService.handleBatchCheckResultForTestVerify(batch, mistakeList, insertDoubtList, removeDoubtList, bizItemList);

		return true;
	}
	
	
	/**
	 * 业务日对账-按渠道分批次
	 * @param summary       对账汇总
	 * @param batch			对账批次
	 */
	public boolean checkBatch(BillBizBatch batch, BillBizSummary summary) {
		/** 0.检查是否可运行该对账批次*/
		if(!canExecuteBillBizBatch(batch)){
			return false;
		}		
		/** 1.下载对账单 */
		log.info("对账文件下载开始>>>");
		try {
			billBizDownloadBusiness.downloadOutsideBizBill(batch);
		} catch (Exception e) {
			log.error("账单下载查询异常："+e.getMessage());
			batch.setHandleStatus(BatchStatusEnum.fail.name());
//			if(StringUtils.isEmpty(batch.getHandleRemark()) || BatchStatusEnum.init.getDesc().equals(batch.getHandleRemark())){
//				batch.setHandleRemark("BillBizCheckBatchBusiness>>>"+e.getMessage());
//			}
			if(StringUtils.isEmpty(batch.getHandleRemark())){
				batch.setHandleRemark("BillBizCheckBatchBusiness>>>"+e.getMessage());
			}
			tranxService.modifyBillBizBatch(batch);
			return false;
		}		
		log.info("是否完成账单下载>>>"+!BatchStatusEnum.fail.name().equals(batch.getHandleStatus()));
		tranxService.modifyBillBizBatch(batch);
		if(BatchStatusEnum.fail.name().equals(batch.getHandleStatus())){
			return false;
		}
		log.info("<<<对账文件下载结束");
		
		/** 账单明细数据-支付 **/
		List<OutsideDailyBizBillItem> payBillItemList = new ArrayList<OutsideDailyBizBillItem>();		
		/** 账单明细数据-退款 **/
		List<OutsideDailyBizBillItem> refundBillItemList = new ArrayList<OutsideDailyBizBillItem>();
		//log.info("当前对账状态=>"+batch.getHandleStatus());
		/** 账单存在则解析 */
		if(BatchStatusEnum.hasDownload.name().equals(batch.getHandleStatus())){
			/** 2.解析对账单 */
			log.info("对账文件解析开始>>>");
			Map<String, List<OutsideDailyBizBillItem>> billDataMap = null;
			try {
				billDataMap = parserBusiness.parseBizBill(batch);
			} catch (Exception e) {
				log.error("账单解析异常："+e.getMessage());
				batch.setHandleStatus(BatchStatusEnum.fail.name());
//				if(StringUtils.isEmpty(batch.getHandleRemark()) || BatchStatusEnum.init.getDesc().equals(batch.getHandleRemark())){
//					batch.setHandleRemark(e.getMessage());
//				}
				if(StringUtils.isEmpty(batch.getHandleRemark())){
					batch.setHandleRemark(e.getMessage());
				}
				tranxService.modifyBillBizBatch(batch);
				return false;
			}		
			log.info("是否完成账单解析>>>"+!BatchStatusEnum.fail.name().equals(batch.getHandleStatus()));
			tranxService.modifyBillBizBatch(batch);
			if(BatchStatusEnum.fail.name().equals(batch.getHandleStatus())){
				return false;
			}
			log.info("<<<对账文件解析结束");	
			/** 账单明细数据-支付 **/
			payBillItemList.addAll(billDataMap.get(BillConstant.KEY_PAY));		
			/** 账单明细数据-退款 **/
			refundBillItemList.addAll(billDataMap.get(BillConstant.KEY_REFUND));
		}
		
		/** 本地数据-支付 **/
		// 查询本地成功支付订单
		List<PayPaymentOrder> sucessPayList = dataBusiness.findSuccessPayDataByBatch(batch);
		// 查询本地所有支付订单
		List<PayPaymentOrder> allPayList = dataBusiness.findAllPayDataByBatch(batch);		
		/** 本地数据-退款 **/
		// 查询本地成功退款订单
		List<PayRefundOrder> sucessRefundList = dataBusiness.findSuccessRefundDataByBatch(batch);
		// 查询本地所有退款订单
		List<PayRefundOrder> allRefundList = dataBusiness.findAllRefundDataByBatch(batch);
		/** 本地数据-存疑 */
		// 查询所有支付存疑记录
		List<BillBizDoubt> payDoubtInPeriodList = poolBusiness.findDoubtInPeriodList(BillTypeEnum.pay.name());
		// 查询所有退款存疑记录
		List<BillBizDoubt> refundDoubtInPeriodList = poolBusiness.findDoubtInPeriodList(BillTypeEnum.refund.name());
		// 待插入存疑list
		List<BillBizDoubt> insertDoubtList = new ArrayList<BillBizDoubt>();
		// 待删除存疑list
		List<BillBizDoubt> removeDoubtList = new ArrayList<BillBizDoubt>();
		/** 本地数据-差错 */
		// 差错list
		List<BillBizMistake> mistakeList = new ArrayList<BillBizMistake>();
		/** 本地数据-内部账单明细 */
		// 待插入账单明细list
		List<BillBizItem> bizItemList = new ArrayList<BillBizItem>();
		
		/** 3.支付对账 */
		/** 3.1.以本地订单为基准与支付渠道对账单逐笔勾兑 */
		log.info("--------------------支付对账:以本地订单为基准:start-----------------");
		checkPayOnLocal(batch, sucessPayList, payBillItemList, mistakeList, insertDoubtList, bizItemList);
		log.info("--------------------支付对账:以本地订单为基准:end-----------------");
		/** 3.2.以支付渠道对账单为基准与本地订单逐笔勾兑 */
		log.info("--------------------支付对账:以支付渠道账单为基准:start-----------------");
		checkPayOnChannel(batch, allPayList, payBillItemList, payDoubtInPeriodList, mistakeList, removeDoubtList, bizItemList);
		log.info("--------------------支付对账:以支付渠道账单为基准:end-----------------");
		
		/** 4.退款对账 */
		/** 4.1.以本地订单为基准与支付渠道对账单逐笔勾兑 */
		log.info("--------------------退款对账:以本地订单为基准:start-----------------");
		checkRefundOnLocal(batch, sucessRefundList, refundBillItemList, mistakeList, insertDoubtList, bizItemList);
		log.info("--------------------退款对账:以本地订单为基准:end-----------------");	
		/** 4.2.以支付渠道对账单为基准与本地订单逐笔勾兑 */
		log.info("--------------------退款对账:以支付渠道账单为基准:start-----------------");
		checkRefundOnChannel(batch, allRefundList, refundBillItemList, refundDoubtInPeriodList, mistakeList, removeDoubtList, bizItemList);
		log.info("--------------------退款对账:以支付渠道账单为基准:end-----------------");
		
		batch.setEndTime(new Date());
	    batch.setHandleStatus(BatchStatusEnum.success.name());
	    batch.setHandleRemark(BatchStatusEnum.success.getDesc());
	    /**
	    if(!StringUtils.isEmpty(batch.getChannelBillStorePath())){
	    	batch.setHandleRemark(BatchStatusEnum.success.getDesc());
	    }else{
	    	batch.setHandleRemark(BatchStatusEnum.success.getDesc()+"[账单不存在]");
	    }
	    */	    
	    //log.info(batch.toString());
	    //log.info("待新增存疑记录列表size>>>"+(insertDoubtList!=null?insertDoubtList.size():0));	    
		// 保存批次对账数据
		tranxService.handleBatchCheckResult(summary, batch, mistakeList, insertDoubtList, removeDoubtList, bizItemList);

		return true;
	}
	
	/**
	 * 支付对账:以本地订单为基准与支付渠道对账单逐笔勾兑
	 * 
	 * @param batch
	 * @param sucessPayList
	 * @param billItemList
	 * @param mistakeList
	 * @param insertDoubtList
	 * @param bizItemList
	 */
	private void checkPayOnLocal(BillBizBatch batch, List<PayPaymentOrder> sucessPayList,
			List<OutsideDailyBizBillItem> billItemList,
			List<BillBizMistake> mistakeList,
			List<BillBizDoubt> insertDoubtList, List<BillBizItem> bizItemList) {
		BigDecimal nativeTradeAmount = BigDecimal.ZERO;// 本地订单总金额
		Integer nativeTradeCount = 0;// 本地订单总笔数
		Integer mistakeCount = 0;//差错总个数
		if(sucessPayList==null || sucessPayList.size()<=0){
			log.info("当日成功支付订单列表为空，结束此步骤.");
			return;
		}
		for (PayPaymentOrder payOrder : sucessPayList) {
			Boolean flag = false;// 用于标记是否有匹配
			// 累计本地订单金额以及笔数
			nativeTradeAmount = nativeTradeAmount.add(payOrder.getAmount());
			nativeTradeCount++;
			if(billItemList==null || billItemList.size()<=0){
				log.info("渠道账单为空，本地成功支付订单逐条计入存疑");
				//批量放入存疑记录
				BillBizDoubt doubt = entityBusiness.makeBizDoubt(payOrder, null, batch);
				insertDoubtList.add(doubt);				
			}else{
				log.info("渠道账单不空，逐条比对");
				for (OutsideDailyBizBillItem billItem : billItemList) {
					// 如果支付渠道账单中有匹配数据：进行金额校验
					if (payOrder.getOrderNo().equalsIgnoreCase(billItem.getPayOrderNo())) {
						flag = true;// 标记已经找到匹配
						/** 匹配订单金额 **/
						// 本地订单金额多
						if (payOrder.getAmount().compareTo(billItem.getPayTradeAmount()) == 1) {
							BillBizMistake misktake = entityBusiness.makePayMisktake(null, payOrder, billItem, MistakeTypeEnum.local_cash_more, batch);
							mistakeList.add(misktake);
							mistakeCount++;
							break;
						}
						// 本地订单金额少
						else if (payOrder.getAmount().compareTo(billItem.getPayTradeAmount()) == -1) {
							BillBizMistake misktake = entityBusiness.makePayMisktake(null, payOrder, billItem, MistakeTypeEnum.local_cash_less, batch);
							mistakeList.add(misktake);
							mistakeCount++;
							break;
						}
						//对账成功，加入日账单明细，交易数据来自支付订单
						BillBizItem bizItem = entityBusiness.makeBillBizItem(payOrder,null,null,batch);
						bizItemList.add(bizItem);
					}
				}
				// 没有找到匹配的记录，则计入存疑表，待来日再次查找比对
				if (!flag) {
					BillBizDoubt doubt = entityBusiness.makeBizDoubt(payOrder, null, batch);
					insertDoubtList.add(doubt);
				}//enf for
			}//end if
		}
		// 记录统计数据
		batch.setTradeAmount(batch.getTradeAmount().add(nativeTradeAmount));
		batch.setTradeCount(batch.getTradeCount() + nativeTradeCount);
		batch.setMistakeCount(batch.getMistakeCount() + mistakeCount);
	}

	/**
	 * 支付对账:以支付渠道对账单为基准与本地订单逐笔勾兑
	 * 
	 * @param batch        对账批次
	 * @param allPayList   本地订单
	 * @param billItemList 账单明细列表
	 * @param doubtList    存疑记录
	 * @param mistakeList  错误记录
	 * @param removeDoubtList 待删除存疑记录
	 * @param bizItemList  业务对账明细列表
	 */
	private void checkPayOnChannel(BillBizBatch batch, List<PayPaymentOrder> allPayList,
			List<OutsideDailyBizBillItem> billItemList,
			List<BillBizDoubt> payDoubtInPeriodList,
			List<BillBizMistake> mistakeList, 
			List<BillBizDoubt> removeDoubtList, List<BillBizItem> bizItemList) {
		BigDecimal nativeTradeAmount = BigDecimal.ZERO;// 本地订单总金额
		Integer nativeTradeCount = 0;// 本地订单总笔数
		Integer mistakeCount = 0;//差错记录总数
		if(billItemList==null || billItemList.size()<=0){
			log.info("渠道账单为空，结束此步骤.");
			return;
		}
		for (OutsideDailyBizBillItem billItem : billItemList) {
			boolean flag = false;// 用于标记是否有匹配
			if(allPayList==null || allPayList.size()<=0){
				log.info("当日支付订单列表为空，结束此步骤.");
			}else{
				for (PayPaymentOrder payOrder : allPayList) {
					/** 检查有匹配的数据 **/
					if (billItem.getPayOrderNo().equals(payOrder.getOrderNo())) {
						flag = true;
						/**
						 * 注意：此处逻辑表达最常见问题，即本地系统未收到支付渠道异步通知的情况
						 * 在此记录支付渠道账单明细存在（账单明细必然是成功的），且本地订单不成功的差错，然后再验证订单金额是否正确，不正确则再记录金额错误 
						 */
						if (!TradeStatusEnum.success.name().equals(payOrder.getStatus())) {
							BillBizMistake misktake1 = entityBusiness.makePayMisktake(null, payOrder, billItem, MistakeTypeEnum.local_status_less, batch);
							mistakeList.add(misktake1);
							mistakeCount++;
							/** 
							 * 验证订单金额，
							 * 注意：金额差错处理之前必须先处理状态不符的情况 
							 */
							// 本地订单金额多
							if (payOrder.getAmount().compareTo(billItem.getPayTradeAmount()) == 1) {
								BillBizMistake misktake = entityBusiness.makePayMisktake(null, payOrder, billItem, MistakeTypeEnum.local_cash_more, batch);
								mistakeList.add(misktake);
								mistakeCount++;
								break;
							}
							// 本地订单金额少
							else if (payOrder.getAmount().compareTo(billItem.getPayTradeAmount()) == -1) {
								BillBizMistake misktake = entityBusiness.makePayMisktake(null, payOrder, billItem, MistakeTypeEnum.local_cash_less, batch);
								mistakeList.add(misktake);
								mistakeCount++;
								break;
							}
						}
					}
				}//end for
			}//end if			

			/** 若当日本地订单没有匹配的数据，则取往日存疑表查找记录对账。 **/			
			if (!flag) {
				log.info("未匹配，查找存疑记录进行比对.");
				if (payDoubtInPeriodList != null){
					for (BillBizDoubt doubt : payDoubtInPeriodList) {
						if (doubt.getPayOrderNo().equals(billItem.getPayOrderNo())) {
							/** 累计本地订单总金额以及笔数 **/
							nativeTradeAmount = nativeTradeAmount.add(doubt.getPayTradeAmount());
							nativeTradeCount++;
							flag = true;

							/** 验证订单金额 **/
							// 本地订单金额多
							if (doubt.getPayTradeAmount().compareTo(billItem.getPayTradeAmount()) == 1) {
								BillBizMistake misktake = entityBusiness.makePayMisktake(doubt, null, billItem, MistakeTypeEnum.local_cash_more, batch);
								mistakeList.add(misktake);
								mistakeCount++;
								break;
							}
							// 本地订单金额少
							else if (doubt.getPayTradeAmount().compareTo(billItem.getPayTradeAmount()) == -1) {
								BillBizMistake misktake = entityBusiness.makePayMisktake(doubt, null, billItem, MistakeTypeEnum.local_cash_less, batch);
								mistakeList.add(misktake);
								mistakeCount++;
								break;
							}

							/** 将已匹配的存疑记录放入待删除存疑列表 **/
							removeDoubtList.add(doubt);
							
							/** 对账成功，加入日账单明细，交易数据来自存疑记录 **/
							BillBizItem bizItem = entityBusiness.makeBillBizItem(null,null,doubt,batch);
							bizItemList.add(bizItem);
						}
					}
				}
			}

			/** 
			 * 若往日存疑表还是没有订单记录,则直接记录差错，差错类型为 PLATFORM_MISS("系统漏单")
			 */
			if (!flag) {
				log.info("存疑也未匹配，记录本地漏单错误.");
				BillBizMistake misktake = entityBusiness.makePayMisktake(null, null, billItem, MistakeTypeEnum.local_miss, batch);
				mistakeList.add(misktake);
				mistakeCount++;
			}
		}

		// 记录统计数据
		batch.setTradeAmount(batch.getTradeAmount().add(nativeTradeAmount));
		batch.setTradeCount(batch.getTradeCount() + nativeTradeCount);
		batch.setMistakeCount(batch.getMistakeCount() + mistakeCount);
	}

	/**
	 * 退款对账:以本地订单为基准与支付渠道对账单逐笔勾兑
	 * @param batch	对账批次
	 * @param sucessRefundList	本地成功退款订单
	 * @param billItemList		外部账单明细列表
	 * @param mistakeList   	错误列表
	 * @param insertDoubtList	待新增存疑列表
	 * @param bizItemList		内部账单明细列表
	 */
	private void checkRefundOnLocal(BillBizBatch batch,
			List<PayRefundOrder> sucessRefundList,
			List<OutsideDailyBizBillItem> billItemList,
			List<BillBizMistake> mistakeList,
			List<BillBizDoubt> insertDoubtList, List<BillBizItem> bizItemList) {
		BigDecimal nativeRefundAmount = BigDecimal.ZERO;// 本地退款订单总金额
		Integer nativeRefundCount = 0;// 本地退款订单总笔数
		Integer mistakeCount = 0; //差错总个数
		if(sucessRefundList==null || sucessRefundList.size()<=0){
			log.info("当日成功退款订单列表为空，结束此步骤.");
			return;
		}
		for (PayRefundOrder refundOrder : sucessRefundList) {
			Boolean flag = false;// 用于标记是否有匹配
			// 累计本地订单金额以及笔数
			nativeRefundAmount = nativeRefundAmount.add(refundOrder.getRefundAmount());
			nativeRefundCount++;
			if(billItemList==null || billItemList.size()<=0){
				log.info("渠道账单为空，本地成功退款订单逐条计入存疑");
				//批量放入存疑记录
				BillBizDoubt doubt = entityBusiness.makeBizDoubt(null, refundOrder, batch);
				insertDoubtList.add(doubt);
			}else{
				log.info("渠道账单不空，逐条比对");
				for (OutsideDailyBizBillItem billItem : billItemList) {
					// 如果支付渠道账单中有匹配数据：进行金额校验
					if (refundOrder.getRefundNo().equalsIgnoreCase(billItem.getRefundOrderNo())) {
						flag = true;// 标记已经找到匹配
						/** 匹配订单金额 **/
						// 本地订单金额多
						if (refundOrder.getRefundAmount().compareTo(billItem.getRefundAmount()) == 1) {
							BillBizMistake misktake = entityBusiness.makeRefundMisktake(null, refundOrder, billItem, MistakeTypeEnum.local_cash_more, batch);
							mistakeList.add(misktake);
							mistakeCount++;
							break;
						}
						// 本地订单金额少
						else if (refundOrder.getRefundAmount().compareTo(billItem.getRefundAmount()) == -1) {
							BillBizMistake misktake = entityBusiness.makeRefundMisktake(null, refundOrder, billItem, MistakeTypeEnum.local_cash_less, batch);
							mistakeList.add(misktake);
							mistakeCount++;
							break;
						}
						//对账成功，加入日账单明细，交易数据来自退款订单
						BillBizItem bizItem = entityBusiness.makeBillBizItem(null,refundOrder,null,batch);
						bizItemList.add(bizItem);
					}
				}
				// 没有找到匹配的记录，则计入存疑表，待来日再次查找比对
				if (!flag) {
					BillBizDoubt doubt = entityBusiness.makeBizDoubt(null, refundOrder, batch);
					insertDoubtList.add(doubt);
				}
			}
			
		}
		// 记录统计数据
		batch.setRefundAmount(batch.getRefundAmount().add(nativeRefundAmount));
		batch.setRefundCount(batch.getRefundCount() + nativeRefundCount);
		batch.setMistakeCount(batch.getMistakeCount() + mistakeCount);
	}
	
	/**
	 * 退款对账:以支付渠道对账单为基准与本地订单逐笔勾兑
	 * @param batch	对账批次
	 * @param allRefundList 退款订单
	 * @param billItemList  外部账单明细列表
	 * @param refundDoubtInPeriodList 存疑列表
	 * @param mistakeList	错误列表
	 * @param removeDoubtList 待删除存疑记录
	 * @param bizItemList 内部账单明细列表
	 */
	private void checkRefundOnChannel(BillBizBatch batch,
			List<PayRefundOrder> allRefundList,
			List<OutsideDailyBizBillItem> billItemList,
			List<BillBizDoubt> refundDoubtInPeriodList,
			List<BillBizMistake> mistakeList,
			List<BillBizDoubt> removeDoubtList, List<BillBizItem> bizItemList) {
		BigDecimal nativeRefundAmount = BigDecimal.ZERO;// 本地订单总金额
		Integer nativeRefundCount = 0;// 本地订单总笔数
		Integer mistakeCount = 0;//差错记录总数
		if(billItemList==null || billItemList.size()<=0){
			log.info("渠道账单为空，结束此步骤.");
			return;
		}
		for (OutsideDailyBizBillItem billItem : billItemList) {
			boolean flag = false;// 用于标记是否有匹配
			if(allRefundList==null || allRefundList.size()<=0){
				log.info("当日退款订单列表为空，结束此步骤.");
			}else{
				log.info("与渠道账单记录比对......");
				for (PayRefundOrder refundOrder : allRefundList) {
					/** 检查有匹配的数据 **/
					log.info("channel-RefundOrderNo=> "+billItem.getRefundOrderNo()+"     local-refundNo=> "+refundOrder.getRefundNo());
					if (billItem.getRefundOrderNo().equals(refundOrder.getRefundNo())) {
						
						flag = true;
						/**
						 * 此处表示本地系统没有及时通过退款查询接口更新退款状态导致的问题场景
						 * 在此记录支付渠道账单明细存在（账单明细必然是成功的），且本地订单不成功的差错，然后再验证订单金额是否正确，不正确则再记录金额错误 
						 */
						if (!TradeStatusEnum.success.name().equals(refundOrder.getRefundStatus())) {
							BillBizMistake misktake1 = entityBusiness.makeRefundMisktake(null, refundOrder, billItem, MistakeTypeEnum.local_status_less, batch);
							mistakeList.add(misktake1);
							mistakeCount++;
							/** 
							 * 验证订单金额，
							 * 注意：金额差错处理之前必须先处理状态不符的情况 
							 */
							// 本地订单金额多
							if (refundOrder.getRefundAmount().compareTo(billItem.getRefundAmount()) == 1) {
								BillBizMistake misktake = entityBusiness.makeRefundMisktake(null, refundOrder, billItem, MistakeTypeEnum.local_cash_more, batch);
								mistakeList.add(misktake);
								mistakeCount++;
								break;
							}
							// 本地订单金额少
							else if (refundOrder.getRefundAmount().compareTo(billItem.getRefundAmount()) == -1) {
								BillBizMistake misktake = entityBusiness.makeRefundMisktake(null, refundOrder, billItem, MistakeTypeEnum.local_cash_less, batch);
								mistakeList.add(misktake);
								mistakeCount++;
								break;
							}
						}
					}
				}//end for
			}//end if			

			/** 若当日本地订单没有匹配的数据，则取往日存疑表查找记录对账。 **/
			if (!flag) {
				if (refundDoubtInPeriodList != null){
					for (BillBizDoubt doubt : refundDoubtInPeriodList) {
						if (doubt.getPayOrderNo().equals(billItem.getPayOrderNo())) {
							/** 累计本地订单总金额以及笔数 **/
							nativeRefundAmount = nativeRefundAmount.add(doubt.getRefundTradeAmount());
							nativeRefundCount++;
							flag = true;

							/** 验证订单金额 **/
							// 本地订单金额多
							if (doubt.getRefundTradeAmount().compareTo(billItem.getRefundAmount()) == 1) {
								BillBizMistake misktake = entityBusiness.makeRefundMisktake(doubt, null, billItem, MistakeTypeEnum.local_cash_more, batch);
								mistakeList.add(misktake);
								mistakeCount++;
								break;
							}
							// 本地订单金额少
							else if (doubt.getRefundTradeAmount().compareTo(billItem.getRefundAmount()) == -1) {
								BillBizMistake misktake = entityBusiness.makeRefundMisktake(doubt, null, billItem, MistakeTypeEnum.local_cash_less, batch);
								mistakeList.add(misktake);
								mistakeCount++;
								break;
							}

							/** 将已匹配的存疑记录放入待删除存疑列表 **/
							removeDoubtList.add(doubt);
							
							/** 对账成功，加入内部日账单明细，交易数据来自存疑记录 **/
							BillBizItem bizItem = entityBusiness.makeBillBizItem(null,null,doubt,batch);
							bizItemList.add(bizItem);
						}
					}
				}
			}

			/** 
			 * 若往日存疑表还是没有订单记录,则直接记录差错，差错类型为 PLATFORM_MISS("系统漏单")
			 */
			if (!flag) {
				BillBizMistake misktake = entityBusiness.makeRefundMisktake(null, null, billItem, MistakeTypeEnum.local_miss, batch);
				mistakeList.add(misktake);
				mistakeCount++;
			}
		}

		// 记录统计数据
		batch.setRefundAmount(batch.getRefundAmount().add(nativeRefundAmount));
		batch.setRefundCount(batch.getRefundCount() + nativeRefundCount);
		batch.setMistakeCount(batch.getMistakeCount() + mistakeCount);
	}

}
