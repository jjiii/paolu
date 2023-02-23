package com.fc.pay.bill.parser.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fc.pay.bill.constant.BillConstant;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.filter.WeixinPayItemBillFileFilter;
import com.fc.pay.bill.filter.WeixinRefundItemBillFileFilter;
import com.fc.pay.bill.parser.OutsideDailyBillParser;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.utils.ZipUtil;
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;
import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.common.core.enums.TradeStatusEnum;

/**
 * 微信日账单解析
 * @author zhanjq
 *
 */
@Component("weixinDailyBillParser")
public class WeixinDailyBillParser implements OutsideDailyBillParser {
	
	private static final Logger log = LoggerFactory.getLogger(WeixinDailyBillParser.class);
	
	//对账类型-当日成功支付
	private static final String WeixinTranTagPay = BillUtil.readWeixinTranTagPay();
	
	//对账类型-当日退款
	private static final String WeixinTranTagRefund = BillUtil.readWeixinTranTagRefund();
	
	
	/******************* 对账类型：ALL *******************/
	private static final String ALL_TITLE_ITEM  = "交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,代金券或立减优惠金额,微信退款单号,商户退款单号,退款金额,代金券或立减优惠退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率";
	private static final String ALL_TITLE_SUMMARY = "总交易单数,总交易额,总退款金额,总代金券或立减优惠退款金额,手续费总金额";
	private static final Pattern ALL_TITLE_ITEM_PATTERN = Pattern.compile("(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?)$");
	private static final Pattern ALL_DATA_ITEM_PATTERN = Pattern.compile("^`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?)$");
	private static final Pattern ALL_TITLE_SUMMARY_PATTERN = Pattern.compile("(.*?),(.*?),(.*?),(.*?),(.*?)$");
	private static final Pattern ALL_DATA_SUMMARY_PATTERN = Pattern.compile("^`(.*?),`(.*?),`(.*?),`(.*?),`(.*?)$");

	/******************* 对账类型：SUCCESS *******************/
	private static final String SUCCESS_TITLE_ITEM = "交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,代金券或立减优惠金额,商品名称,商户数据包,手续费,费率";
	private static final String SUCCESS_TITLE_SUMMARY = "总交易单数,总交易额,总退款金额,总代金券或立减优惠退款金额,手续费总金额";
	private static final Pattern SUCCESS_TITLE_ITEM_PATTERN = Pattern.compile("(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?)$");
	private static final Pattern SUCCESS_DATA_ITEM_PATTERN = Pattern.compile("^`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?)$");
	private static final Pattern SUCCESS_TITLE_SUMMARY_PATTERN = Pattern.compile("(.*?),(.*?),(.*?),(.*?),(.*?)$");
	private static final Pattern SUCCESS_DATA_SUMMARY_PATTERN = Pattern.compile("^`(.*?),`(.*?),`(.*?),`(.*?),`(.*?)$");
	
	/******************* 对账类型：REFUND  *******************/
	private static final String REFUND_TITLE_ITEM  = "交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,代金券或立减优惠金额,退款申请时间,退款成功时间,微信退款单号,商户退款单号,退款金额,代金券或立减优惠退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率";
	private static final String REFUND_TITLE_SUMMARY = "总交易单数,总交易额,总退款金额,总代金券或立减优惠退款金额,手续费总金额";
	private static final Pattern REFUND_TITLE_ITEM_PATTERN = Pattern.compile("(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?)$");
	private static final Pattern REFUND_DATA_ITEM_PATTERN = Pattern.compile("^`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?),`(.*?)$");
	private static final Pattern REFUND_TITLE_SUMMARY_PATTERN = Pattern.compile("(.*?),(.*?),(.*?),(.*?),(.*?)$");
	private static final Pattern REFUND_DATA_SUMMARY_PATTERN = Pattern.compile("^`(.*?),`(.*?),`(.*?),`(.*?),`(.*?)$");
	
	/**
	 * 解析当日成功支付账单
	 * @param billPath
	 * @param batch
	 * @return
	 * @throws Exception 
	 */
	private List<OutsideDailyBizBillItem> parseBizPaySuccessBill(File payFile, BillBizBatch batch) throws Exception {
		List<String> lineList = null;
		try {
			lineList = FileUtils.readLines(payFile, PayConstants.UTF8_ENCODING);
		} catch (IOException e) {
			log.error(e.getMessage());
			return null;
		}
		/** 校验标题行*/
		String titleRawData = lineList.remove(0);
		if (!SUCCESS_TITLE_ITEM_PATTERN.matcher(titleRawData).find()) {
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			batch.setHandleRemark("校验标题行不通过, rawdata[" + titleRawData + "], 期望值[" + SUCCESS_TITLE_ITEM + "]");
			log.error("校验标题行不通过, rawdata[" + titleRawData + "], 期望值[" + SUCCESS_TITLE_ITEM + "]");
			return null;
		}
		/** 校验统计行 */
		String totalRawData = lineList.remove(lineList.size() - 1);//注意顺序-统计数据行
		String totalTitleRawData = lineList.remove(lineList.size() - 1);//注意顺序-统计标题行
		if (!SUCCESS_TITLE_SUMMARY_PATTERN.matcher(totalTitleRawData).find()) {
			log.error("校验统计标题行不通过, rawdata[" + totalTitleRawData + "], 期望值[" + SUCCESS_TITLE_SUMMARY + "]");
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			batch.setHandleRemark("校验统计标题行不通过, rawdata[" + totalTitleRawData + "], 期望值[" + SUCCESS_TITLE_SUMMARY + "]");	
			return null;
		}
		Matcher totalMatcher = SUCCESS_DATA_SUMMARY_PATTERN.matcher(totalRawData);
		if (!totalMatcher.find()) {
			log.error("匹配统计行失败, rawdata[" + totalRawData + "]");
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			batch.setHandleRemark("匹配统计行失败, rawdata[" + totalRawData + "]");
			return null;
		}

		/** 解析账单数据 */
		BigDecimal payTotalAmount = BigDecimal.ZERO;// 支付订单总金额
		Integer payTotalCount = 0;// 支付订单总笔数
		List<OutsideDailyBizBillItem> payList = new ArrayList<OutsideDailyBizBillItem>();
		OutsideDailyBizBillItem item = null;
		for (String rawData : lineList) {
			Matcher matcher = SUCCESS_DATA_ITEM_PATTERN.matcher(rawData);
			if (!matcher.find()) {			
				log.error("匹配账单明细失败, rawdata[" + rawData + "]");
				batch.setHandleStatus(BatchStatusEnum.fail.name());
				batch.setHandleRemark("匹配账单明细失败, rawdata[" + rawData + "]");
				return null;
			}
			item = new OutsideDailyBizBillItem();
            /**--------------------------公共字段----------------------------*/
            item.setBatchNo(batch.getBatchNo());
            item.setPayChannel(batch.getPayChannel());
            item.setMerchantAppId(batch.getChannelAppId());
            item.setMerchantId(batch.getChannelMerchantId());
            item.setBillDate(batch.getBillDate());            
            /** 
             * 明细记录模式参考,从1开始计算
             * 交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额, 代金券或立减优惠金额,商品名称,商户数据包,手续费,费率
             */
            String txnType = matcher.group(9);//交易类型
            log.info("交易类型："+txnType);
            /** -------------------------交易类型 ：SUCCESS 当日成功支付------------------------------*/		
            //if("SUCCESS".equals(txnType)){
			BigDecimal payAmount = new BigDecimal(matcher.group(13));//总金额   	
        	payTotalAmount = payTotalAmount.add(payAmount);
        	payTotalCount++;        	
        	item.setPayOrderNo(matcher.group(7));
        	item.setPayTradeNo(matcher.group(6));    
        	//交易时间，格式yyyy-MM-dd HH:mm:ss
        	Date tradeTime = DateUtil.parseHyphenTimeContent(matcher.group(1));
            item.setPayOrderTime(tradeTime);//取交易时间
            item.setPaySuccessTime(tradeTime);//取交易时间
            log.info("交易状态："+matcher.group(10));
            item.setPayTradeStatus(TradeStatusEnum.success.name());//交易类型SUCCESS则为成功，统一记为success
            item.setPayTradeAmount(payAmount);
            payList.add(item);
            //}			
		}        
        //统计数据
		batch.setChannelTradeAmount(payTotalAmount);
        batch.setChannelTradeCount(payTotalCount);
		return payList;
	}

	/**
	 * 解析当日退款账单
	 * @param billPath
	 * @param batch
	 * @return
	 * @throws Exception 
	 */
	private List<OutsideDailyBizBillItem> parseBizRefundBill(File refundFile, BillBizBatch batch) throws Exception {
		List<String> lineList = null;
		try {
			lineList = FileUtils.readLines(refundFile, PayConstants.UTF8_ENCODING);
		} catch (IOException e) {
			log.error(e.getMessage());
			return null;
		}
		/** 校验标题行*/
		String titleRawData = lineList.remove(0);
		if (!REFUND_TITLE_ITEM_PATTERN.matcher(titleRawData).find()) {
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			batch.setHandleRemark("校验标题行不通过, rawdata[" + titleRawData + "], 期望值[" + REFUND_TITLE_ITEM + "]");
			log.error("校验标题行不通过, rawdata[" + titleRawData + "], 期望值[" + REFUND_TITLE_ITEM + "]");
			return null;
		}
		/** 校验统计行 */
		String totalRawData = lineList.remove(lineList.size() - 1);//注意顺序-统计数据行
		String totalTitleRawData = lineList.remove(lineList.size() - 1);//注意顺序-统计标题行
		if (!REFUND_TITLE_SUMMARY_PATTERN.matcher(totalTitleRawData).find()) {
			log.error("校验统计标题行不通过, rawdata[" + totalTitleRawData + "], 期望值[" + REFUND_TITLE_SUMMARY + "]");
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			batch.setHandleRemark("校验统计标题行不通过, rawdata[" + totalTitleRawData + "], 期望值[" + REFUND_TITLE_SUMMARY + "]");	
			return null;
		}
		Matcher totalMatcher = REFUND_DATA_SUMMARY_PATTERN.matcher(totalRawData);
		if (!totalMatcher.find()) {
			log.error("匹配统计行失败, rawdata[" + totalRawData + "]");
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			batch.setHandleRemark("匹配统计行失败, rawdata[" + totalRawData + "]");
			return null;
		}

		/** 解析账单数据 */
		BigDecimal refundTotalAmount = BigDecimal.ZERO;// 退款订单总金额
		Integer refundTotalCount = 0;// 退款订单总笔数
		List<OutsideDailyBizBillItem> refundList = new ArrayList<OutsideDailyBizBillItem>();
		OutsideDailyBizBillItem item = null;
		for (String rawData : lineList) {
			Matcher matcher = REFUND_DATA_ITEM_PATTERN.matcher(rawData);
			if (!matcher.find()) {			
				log.error("匹配账单明细失败, rawdata[" + rawData + "]");
				batch.setHandleStatus(BatchStatusEnum.fail.name());
				batch.setHandleRemark("匹配账单明细失败, rawdata[" + rawData + "]");
				return null;
			}
			item = new OutsideDailyBizBillItem();
            /**--------------------------公共字段----------------------------*/
            item.setBatchNo(batch.getBatchNo());
            item.setPayChannel(batch.getPayChannel());
            item.setMerchantAppId(batch.getChannelAppId());
            item.setMerchantId(batch.getChannelMerchantId());
            item.setBillDate(batch.getBillDate());            
            /** 
             * 明细记录模式参考,从1开始计算
             * 交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额, 代金券或立减优惠金额,退款申请时间,退款成功时间,微信退款单号,商户退款单号,退款金额, 代金券或立减优惠退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率
             */
            String txnType = matcher.group(9);//交易类型
            log.info("交易类型："+txnType);
            /** -------------------------交易类型 ：REFUND 当日退款------------------------------*/
            //if("REFUND".equals(txnType)){
			BigDecimal refundAmount = new BigDecimal(matcher.group(19));//退款金额
        	refundTotalAmount = refundTotalAmount.add(refundAmount);
        	refundTotalCount++;
        	item.setPayOrderNo(matcher.group(7));
        	item.setPayTradeNo(matcher.group(6));
        	//交易时间，格式yyyy-MM-dd HH:mm:ss
        	Date tradeTime = DateUtil.parseHyphenTimeContent(matcher.group(1));
            item.setPayOrderTime(tradeTime);//取交易时间
            item.setPaySuccessTime(tradeTime);//取交易时间
            log.info("交易状态："+matcher.group(10));
            item.setPayTradeStatus(TradeStatusEnum.success.name());//交易类型SUCCESS则为成功，统一记为success
            item.setPayTradeAmount(new BigDecimal(matcher.group(13)));//总金额   	       	
            item.setRefundOrderNo(matcher.group(18));
			item.setRefundTradeNo(matcher.group(17));
			item.setRefundAmount(refundAmount);//退款金额
            item.setRefundApplyTime(DateUtil.parseHyphenTimeContent(matcher.group(15)));//退款申请时间
            item.setRefundSuccessTime(DateUtil.parseHyphenTimeContent(matcher.group(16)));//退款成功时间
            log.info("退款状态："+matcher.group(22));
            item.setRefundStatus(TradeStatusEnum.success.name());
            refundList.add(item);
            //}	
		}        
        //统计数据
        batch.setChannelRefundAmount(refundTotalAmount);
        batch.setChannelRefundCount(refundTotalCount);
		return refundList;
	}
	
	/**
	 * 解析当日所有账单
	 */
	private Map<String, List<OutsideDailyBizBillItem>> parseBizAllBill(String billPath, BillBizBatch batch) throws Exception {		
		List<String> lineList = null;
		try {
			lineList = FileUtils.readLines(new File(billPath), PayConstants.UTF8_ENCODING);
		} catch (IOException e) {
			log.error(e.getMessage());
			return null;
		}
		/** 校验标题行*/
		String titleRawData = lineList.remove(0);
		if (!ALL_TITLE_ITEM_PATTERN.matcher(titleRawData).find()) {
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			batch.setHandleRemark("校验标题行不通过, rawdata[" + titleRawData + "], 期望值[" + ALL_TITLE_ITEM + "]");
			log.error("校验标题行不通过, rawdata[" + titleRawData + "], 期望值[" + ALL_TITLE_ITEM + "]");
			return null;
		}
		/** 校验统计行 */
		String totalRawData = lineList.remove(lineList.size() - 1);//注意顺序-统计数据行
		String totalTitleRawData = lineList.remove(lineList.size() - 1);//注意顺序-统计标题行
		if (!ALL_TITLE_SUMMARY_PATTERN.matcher(totalTitleRawData).find()) {
			log.error("校验统计标题行不通过, rawdata[" + totalTitleRawData + "], 期望值[" + ALL_TITLE_SUMMARY + "]");
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			batch.setHandleRemark("校验统计标题行不通过, rawdata[" + totalTitleRawData + "], 期望值[" + ALL_TITLE_SUMMARY + "]");	
			return null;
		}
		Matcher totalMatcher = ALL_DATA_SUMMARY_PATTERN.matcher(totalRawData);
		if (!totalMatcher.find()) {
			log.error("匹配统计行失败, rawdata[" + totalRawData + "]");
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			batch.setHandleRemark("匹配统计行失败, rawdata[" + totalRawData + "]");
			return null;
		}

		/** 解析账单数据 */
		Map<String, List<OutsideDailyBizBillItem>> dataMap = new HashMap<String, List<OutsideDailyBizBillItem>>();
		BigDecimal payTotalAmount = BigDecimal.ZERO;// 支付订单总金额
		Integer payTotalCount = 0;// 支付订单总笔数
		BigDecimal refundTotalAmount = BigDecimal.ZERO;// 退款订单总金额
		Integer refundTotalCount = 0;// 退款订单总笔数
		//List<OutsideDailyBizBillItem> list = new ArrayList<OutsideDailyBizBillItem>();
		List<OutsideDailyBizBillItem> payList = new ArrayList<OutsideDailyBizBillItem>();
		List<OutsideDailyBizBillItem> refundList = new ArrayList<OutsideDailyBizBillItem>();
		OutsideDailyBizBillItem item = null;
        log.info("支付类型标识>>>"+WeixinTranTagPay);
        log.info("退款类型标识>>>"+WeixinTranTagRefund);
		for (String rawData : lineList) {
			Matcher matcher = ALL_DATA_ITEM_PATTERN.matcher(rawData);
			if (!matcher.find()) {			
				log.error("匹配账单明细失败, rawdata[" + rawData + "]");
				batch.setHandleStatus(BatchStatusEnum.fail.name());
				batch.setHandleRemark("匹配账单明细失败, rawdata[" + rawData + "]");
				return null;
			}
			item = new OutsideDailyBizBillItem();
            /**--------------------------公共字段----------------------------*/
            item.setBatchNo(batch.getBatchNo());
            item.setPayChannel(batch.getPayChannel());
            item.setMerchantAppId(batch.getChannelAppId());
            item.setMerchantId(batch.getChannelMerchantId());
            item.setBillDate(batch.getBillDate());            
            /** 
             * 明细记录模式参考,从1开始计算
             * 交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,代金券或立减优惠金额,微信退款单号,商户退款单号,退款金额,代金券或立减优惠退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率 
             */
            String txnType = matcher.group(9);//交易类型
            log.info("交易类型："+txnType);
            /** -------------------------交易类型 ：SUCCESS 当日成功支付------------------------------*/		
            if(WeixinTranTagPay.equals(txnType)){
    			BigDecimal payAmount = new BigDecimal(matcher.group(13));//总金额   	
            	payTotalAmount = payTotalAmount.add(payAmount);
            	payTotalCount++;        	
            	item.setPayOrderNo(matcher.group(7));
            	item.setPayTradeNo(matcher.group(6));    
            	//交易时间，格式yyyy-MM-dd HH:mm:ss
            	Date tradeTime = DateUtil.parseHyphenTimeContent(matcher.group(1));
                item.setPayOrderTime(tradeTime);//取交易时间
                item.setPaySuccessTime(tradeTime);//取交易时间
                log.info("交易状态："+matcher.group(10));
                item.setPayTradeStatus(TradeStatusEnum.success.name());//交易类型SUCCESS则为成功，统一记为success
                item.setPayTradeAmount(payAmount);
                payList.add(item);
            }
            /** -------------------------交易类型 ：REFUND 当日退款------------------------------*/
            else if(WeixinTranTagRefund.equals(txnType)){
    			BigDecimal refundAmount = new BigDecimal(matcher.group(17));//退款金额
            	refundTotalAmount = refundTotalAmount.add(refundAmount);
            	refundTotalCount++;
            	item.setPayOrderNo(matcher.group(7));
            	item.setPayTradeNo(matcher.group(6));    
            	//交易时间，格式yyyy-MM-dd HH:mm:ss
            	Date tradeTime = DateUtil.parseHyphenTimeContent(matcher.group(1));
                //item.setPayOrderTime(tradeTime);//取交易时间
                //item.setPaySuccessTime(tradeTime);//取交易时间
                log.info("交易状态："+matcher.group(10));
                item.setPayTradeStatus(TradeStatusEnum.success.name());//交易类型SUCCESS则为成功，统一记为success
                item.setPayTradeAmount(new BigDecimal(matcher.group(13)));//总金额   	       	
                item.setRefundOrderNo(matcher.group(16));
    			item.setRefundTradeNo(matcher.group(15));
    			item.setRefundAmount(refundAmount);
                item.setRefundApplyTime(tradeTime);//取交易时间?
                item.setRefundSuccessTime(tradeTime);//取交易时间?
                log.info("退款状态："+matcher.group(20));
                item.setRefundStatus(TradeStatusEnum.success.name());
                refundList.add(item);
            }
			
		}
		
		//明细列表
        dataMap.put(BillConstant.KEY_PAY, payList);
        dataMap.put(BillConstant.KEY_REFUND, refundList);
        
        //统计数据
		batch.setChannelTradeAmount(payTotalAmount);
        batch.setChannelTradeCount(payTotalCount);
        batch.setChannelRefundAmount(refundTotalAmount);
        batch.setChannelRefundCount(refundTotalCount);
        
		return dataMap;
	}

	/**
	 * 解析账单
	 */
	public Map<String, List<OutsideDailyBizBillItem>> parseBizBill(BillBizBatch batch) throws Exception {
		/**----------------解压缩-----------------*/
		//String billZip = batch.getChannelBillStorePath();
		String billZip = BillUtil.readBillOutsidePath() + batch.getChannelBillStorePath();
		log.info("weixin账单压缩文件："+billZip);
		int index = billZip.lastIndexOf("/");		
		String outDirStr = billZip.substring(0, index);
		log.info("解压缩目录:"+outDirStr);		
		ZipUtil.unzip(billZip, outDirStr);
		File payFile = BillUtil.extractChannelBizItemBillFile(new File(outDirStr), new WeixinPayItemBillFileFilter());
		File refundFile = BillUtil.extractChannelBizItemBillFile(new File(outDirStr), new WeixinRefundItemBillFileFilter());
		/**----------------账单解析-----------------*/
		Map<String, List<OutsideDailyBizBillItem>> dataMap = new HashMap<String, List<OutsideDailyBizBillItem>>();
		List<OutsideDailyBizBillItem> payList = parseBizPaySuccessBill(payFile, batch);
		List<OutsideDailyBizBillItem> refundList = parseBizRefundBill(refundFile, batch);
        dataMap.put(BillConstant.KEY_PAY, payList);
        dataMap.put(BillConstant.KEY_REFUND, refundList);
        batch.setHandleStatus(BatchStatusEnum.hasParse.name());	
		batch.setHandleRemark("微信支付业务日账单已解析");
		return dataMap;
	}	

}
