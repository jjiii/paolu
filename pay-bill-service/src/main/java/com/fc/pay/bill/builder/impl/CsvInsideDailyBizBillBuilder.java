package com.fc.pay.bill.builder.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fc.pay.bill.builder.InsideDailyBizBillBuilder;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.common.core.enums.TradeStatusEnum;

/**
 * CSV内部业务对账日账单生成器
 * 
 * @author zhanjq
 *
 */
@Component("insideDailyBizBillBuilder")
public class CsvInsideDailyBizBillBuilder implements InsideDailyBizBillBuilder {

	private static final Logger log = LoggerFactory
			.getLogger(CsvInsideDailyBizBillBuilder.class);

	// 设置Quote为null，避免每行记录首个中文数据域被添加多余双引号问题，如"商户编号"
	private static final CSVFormat DEFAULT_CSV_FORMAT = CSVFormat.DEFAULT
			.withQuote(null);// 参考CSVFormat.MYSQL

	// 成功支付明细行标题
	private static final String[] PAY_SUCCESS_ITEM_TITLE = { "商户编号", "商户应用编号", "支付渠道", "支付方式", "支付订单下单时间", "支付订单成功时间", 
		"商户支付订单号","枫车支付订单号", "渠道支付流水号", "支付订单金额", "支付订单状态","货币种类", "商品名称"};

	// 退款明细行标题
	private static final String[] REFUND_ITEM_TITLE      = { "商户编号", "商户应用编号", "支付渠道", "支付方式", "支付订单下单时间", "支付订单成功时间", 
		"商户支付订单号","枫车支付订单号", "渠道支付流水号", "支付订单金额", "支付订单状态","货币种类", "商品名称",
		"商户退款订单号","枫车退款订单号", "渠道退款流水号",	"退款订单金额", "退款申请时间", "退款成功时间", "退款订单状态"};

	// 成功支付汇总行标题
	private static final String[] PAY_SUCCESS_SUMMARY_TITLE = { "支付订单总笔数",
			"支付订单总金额" };

	// 退款汇总行标题
	private static final String[] REFUND_SUMMARY_TITLE = { "退款订单总笔数",
			"退款订单总金额" };
	
	/**
	 * 生成当日成功支付账单
	 * 
	 * @return
	 */
	@SuppressWarnings("resource")
	public void buildInsidePaySuccessBill(List<BillBizItem> paySuccessItemList, String billPath) throws Exception {

		log.info("*************************** 当日成功支付账单开始 ***************************");

		OutputStreamWriter billWriter = null;
		
		CSVPrinter billPrinter = null;
		try {
			/**
			billWriter = new FileWriter(
					"C:/z0001_PAY_SUCCESS_BILL_20161121.csv");
			billPrinter = new CSVPrinter(billWriter, DEFAULT_CSV_FORMAT);
			*/
			billWriter  = new OutputStreamWriter(new FileOutputStream(billPath),PayConstants.UTF8_ENCODING);
			billPrinter = new CSVPrinter(billWriter, DEFAULT_CSV_FORMAT);
			
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}
		try {
			// 明细行标题
			billPrinter.printRecord(PAY_SUCCESS_ITEM_TITLE);
			BigDecimal payAmount = BigDecimal.ZERO;// 支付订单总金额
			Integer payCount = 0;// 支付订单总笔数
			if(paySuccessItemList!=null){
				int listSize = paySuccessItemList.size();
				if(listSize>0){
					// 明细行数据
					List<String> itemRecord = null;
					for (BillBizItem item : paySuccessItemList) {
						
						//统计订单金额与笔数
						payAmount = payAmount.add(item.getPayTradeAmount());
						payCount++;
						
						itemRecord = new ArrayList<String>();
						//"商户编号", "商户应用编号", "支付渠道", "支付方式", "支付订单下单时间", "支付订单成功时间", "商户支付订单号","枫车支付订单号", "渠道支付流水号", "支付订单金额", "支付订单状态","货币种类", "商品名称"
						//商户编号
						itemRecord.add(item.getMerchantCode());
						//商户应用编号
						itemRecord.add(item.getMerchantAppCode());
						//支付渠道
						itemRecord.add(item.getPayChannel());
						//支付方式
						itemRecord.add(item.getPayWay());
						//支付订单下单时间
						itemRecord.add(DateUtil.makeHyphenTimeFormat(item.getPayOrderTime()));
						//支付订单成功时间
						itemRecord.add(DateUtil.makeHyphenTimeFormat(item.getPaySuccessTime()));
						//商户支付订单号
						itemRecord.add(item.getPayMerchOrderNo());
						//枫车支付订单号
						itemRecord.add(item.getPayOrderNo());
						//渠道支付流水号
						itemRecord.add(item.getPayTradeNo());
						//支付订单金额
						itemRecord.add(item.getPayTradeAmount().toString());
						//支付订单状态（success）
						itemRecord.add(TradeStatusEnum.success.name());
						//货币种类（人民币CNY）
						itemRecord.add(item.getCurrency());
						//商品名称
						itemRecord.add(item.getMerchantCommodity());
						
						billPrinter.printRecord(itemRecord);
					}
			    }
			}			
			// 汇总行标题
			billPrinter.printRecord(PAY_SUCCESS_SUMMARY_TITLE);
			// 汇总行数据
			billPrinter.printRecord(new String[] { String.valueOf(payCount), payAmount.toString() });
			billWriter.flush();
			billPrinter.flush();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			IOUtils.closeQuietly(billWriter);
			IOUtils.closeQuietly(billPrinter);
		}

		log.info("*************************** 当日成功支付账单结束 ***************************");

	}

	/**
	 * 生成当日退款账单
	 * 
	 * @return
	 */
	public void buildInsideRefundBill(List<BillBizItem> refundItemList, String billPath) throws Exception {

		log.info("*************************** 当日退款账单开始 ***************************");

		OutputStreamWriter billWriter = null;
		CSVPrinter billPrinter = null;
		try {			
			/**
			billWriter = new FileWriter(
					"C:/z0002_REFUND_20161123.csv");
			billPrinter = new CSVPrinter(billWriter, DEFAULT_CSV_FORMAT);
			*/
			billWriter  = new OutputStreamWriter(new FileOutputStream(billPath),PayConstants.UTF8_ENCODING);
			billPrinter = new CSVPrinter(billWriter, DEFAULT_CSV_FORMAT);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw e;
		}
		try {
			// 明细行标题
			billPrinter.printRecord(REFUND_ITEM_TITLE);
			BigDecimal refundAmount = BigDecimal.ZERO;// 支付订单总金额
			Integer refundCount = 0;// 支付订单总笔数
			if(refundItemList!=null){
				int listSize = refundItemList.size();
				if(listSize>0){
					// 明细行数据
					List<String> itemRecord = null;
					for (BillBizItem item : refundItemList) {
						
						//统计订单金额与笔数
						refundAmount = refundAmount.add(item.getPayTradeAmount());
						refundCount++;
						
						itemRecord = new ArrayList<String>();
						//"商户编号", "商户应用编号", "支付渠道", "支付方式", "支付订单下单时间", "支付订单成功时间", "商户支付订单号","枫车支付订单号", "渠道支付流水号", "支付订单金额", "支付订单状态","货币种类", "商品名称"
						//商户编号
						itemRecord.add(item.getMerchantCode());
						//商户应用编号
						itemRecord.add(item.getMerchantAppCode());
						//支付渠道
						itemRecord.add(item.getPayChannel());
						//支付方式
						itemRecord.add(item.getPayWay());
						//支付订单下单时间
						itemRecord.add(DateUtil.makeHyphenTimeFormat(item.getPayOrderTime()));
						//支付订单成功时间
						itemRecord.add(DateUtil.makeHyphenTimeFormat(item.getPaySuccessTime()));
						//商户支付订单号
						itemRecord.add(item.getPayMerchOrderNo());
						//枫车支付订单号
						itemRecord.add(item.getPayOrderNo());
						//渠道支付流水号
						itemRecord.add(item.getPayTradeNo());
						//支付订单金额
						itemRecord.add(item.getPayTradeAmount().toString());
						//支付订单状态（success）
						itemRecord.add(TradeStatusEnum.success.name());
						//货币种类（人民币CNY）
						itemRecord.add(item.getCurrency());
						//商品名称
						itemRecord.add(item.getMerchantCommodity());		
						/**-------退款-------*/
						//"商户退款订单号","枫车退款订单号", "渠道退款流水号",	"退款订单金额", "退款申请时间", "退款成功时间", "退款订单状态"
						itemRecord.add(item.getRefundMerchOrderNo());
						itemRecord.add(item.getRefundOrderNo());
						itemRecord.add(item.getRefundTradeNo());
						itemRecord.add(item.getRefundTradeAmount().toString());
						itemRecord.add(DateUtil.makeHyphenTimeFormat(item.getRefundApplyTime()));
						itemRecord.add(DateUtil.makeHyphenTimeFormat(item.getRefundSuccessTime()));
						itemRecord.add(TradeStatusEnum.success.name());
						billPrinter.printRecord(itemRecord);
					}
			    }
			}
			// 汇总行标题
			billPrinter.printRecord(REFUND_SUMMARY_TITLE);
			// 汇总行数据
			billPrinter.printRecord(new String[] { String.valueOf(refundCount), refundAmount.toString() });
			billWriter.flush();
			billPrinter.flush();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			IOUtils.closeQuietly(billWriter);
			IOUtils.closeQuietly(billPrinter);
		}

		log.info("*************************** 当日退款账单结束 ***************************");

	}
	

}
