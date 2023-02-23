package com.fc.pay.bill.parser.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fc.pay.bill.constant.BillConstant;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.filter.UnionpayBizItemBillFileFilter;
import com.fc.pay.bill.parser.OutsideDailyBillParser;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.utils.ZipUtil;
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;
import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.common.core.enums.TradeStatusEnum;

/**
 * 银联日账单解析
 * @author zhanjq
 *
 */
@Component("unionpayDailyBillParser")
public class UnionpayDailyBillParser implements OutsideDailyBillParser {
	
	private static final Logger log = LoggerFactory.getLogger(UnionpayDailyBillParser.class);
	
	//交易类型-消费
	private static final String UnionpayTranTagPay = BillUtil.readUnionpayTranTagPay();
	
	//交易类型-退货
	private static final String UnionpayTranTagRefund = BillUtil.readUnionpayTranTagRefund();

	// 商户一般交易明细流水文件(ZM)字段占位长度模式-保留字段合并为一个字段
	private int lengthArray[] = { 3, 11, 11, 6, 10, 19, 12, 4, 2, 21, 2, 32, 2,
			6, 10, 13, 13, 4, 15, 2, 2, 6, 2, 4, 32, 1, 21, 15, 1, 15, 32, 13,
			13, 8, 32, 13, 13, 12, 2, 1, 131 };
	/*
	private int lengthArray[] = { 3, 11, 11, 6, 10, 19, 12, 4, 2, 21, 2, 32, 2,
			6, 10, 13, 13, 4, 15, 2, 2, 6, 2, 4, 32, 1, 21, 15, 1, 15, 32, 13,
			13, 8, 32, 13, 13, 12, 2, 1, 32, 99 };
			*/

	/*
	 * 商户一般交易明细流水文件(ZM)字段说明
	 * 1 交易代码 n3 见注1 
	 * 2 代理机构标识码 acqInsCode n11 取值同交易接口说明 
	 * 3 发送机构标识码 n11 银联在线支付平台赋值
	 * 4 系统跟踪号 traceNo n6 取值同交易接口说明 
	 * 5 交易传输时间 txnTime n10 MMDDhhmmss 
	 * 6 帐号 payCardNo n19 见注2 
	 * 7 交易金额 txnAmt n12 左补零，补齐12位
	 * 8 商户类别  merCatCode n4 取值同交易接口说明 
	 * 9 终端类型 termType an2 取值同交易接口说明 
	 * 10 查询流水号 queryId an21 取值同交易接口说明 
	 * 11 支付方式（旧） n2 见注3 
	 * 12 商户订单号 orderId ans32 取值同交易接口说明 
	 * 13 支付卡类型 payCardType n2 取值同交易接口说明 
	 * 14 原始交易的系统跟踪号 n6 见注4 
	 * 15 原始交易日期时间 n10 见注4
	 * 16 商户手续费 X+n12 见注5 
	 * 17 结算金额 X+n12 取值为“交易金额”与“商户手续费”的扎差 
	 * 18 支付方式 payType an4 取值同交易接口说明 
	 * 19 集团商户代码 n15 
	 * 20 交易类型 txnType n2 取值同交易接口说明 
	 * 21 交易子类 txnSubType n2 取值同交易接口说明 
	 * 22 业务类型 bizType n6 取值同交易接口说明 
	 * 23 帐号类型 accType n2 取值同交易接口说明 
	 * 24 账单类型 billType an4 取值同交易接口说明 
	 * 25 账单号码 billNo an32 截取交易接口中“账单号码”的后32位 
	 * 26 交互方式 interactMode n1 取值同交易接口说明 
	 * 27 原交易查询流水号 origQryId an21 取值同交易接口说明 
	 * 28 商户代码 merId n15 取值同交易接口说明 
	 * 29 分账入账方式 ans1 “A”表示收单行入账，“L”表示银联分账系统入账。仅在商户有分账清算需求时出现，默认为空。 
	 * 30 二级商户代码 subMerId n15 取值同交易接口说明 
	 * 31 二级商户简称 subMerAbbr Ans32 取值同交易接口说明 
	 * 32 二级商户分账入账金额 X+n12 仅在商户开展分账清算业务时出现，默认为空。 
	 * 33 清算净额 X+n12 取值为“结算金额”与“二级商户分账入账净额”的扎差 
	 * 34 终端号 termId ans8 截取交易接口中“终端号”的后8位 
	 * 35 商户自定义域 merReserved ans32 截取交易接口中“商户自定义域”的前32位 
	 * 36 优惠金额 X+n12 U点抵扣金额，消费类正向交易为贷记C，退货类反向交易为借记D 。
	 * 37 发票金额 X+n12 因U点抵扣需商户开具发票金额。因消费等正向交易需商户开发票为贷记C，因退货等需商户退还发票为借记D 
	 * 38 分期付款附加手续费 X+n11 仅在分期付款业务中有效，表示开展分期付款业务而额外收入或支出的费用。符号位为C表示贷记，为D表示借记。 
	 * 39 分期付款期数 n2 默认为空，仅在分期付款业务中有效 
	 * 40 交易介质 N1 存量互联网机构该字段为空，只有新接入全渠道的商户才填写。 
	 * 41 原始交易订单号 ans32 默认为空，预授权完成、退货、消费撤销、预授权撤销、预授权完成撤销交易才填写。
	 * 42 保留使用 Ans99
	 */

	/**
	 * 解析业务明细
	 * @param batch 
	 */
	@SuppressWarnings("resource")
	private Map<String, List<OutsideDailyBizBillItem>> parseBizDetailBill(File file, BillBizBatch batch) throws Exception {
		Map<String, List<OutsideDailyBizBillItem>> dataMap = new HashMap<String, List<OutsideDailyBizBillItem>>();
		/**--------------- 读取明细行记录 ---------------*/
		List<Map<Integer, String>> ZmDataList = new ArrayList<Map<Integer, String>>();
		InputStreamReader reader = null;
		try {			
			reader = new InputStreamReader(new FileInputStream(file), PayConstants.UTF8_ENCODING);//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(reader);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				int totalLength = lineTxt.length();
				log.debug("totalLength>>>"+totalLength);
				// 解析的结果MAP，key为对账文件列序号，value为解析的值
				Map<Integer, String> ZmDataMap = new LinkedHashMap<Integer, String>();
				// 左侧游标
				int leftIndex = 0;
				// 右侧游标
				int rightIndex = 0;
				for (int i = 0; i < lengthArray.length; i++) {
					rightIndex = leftIndex + lengthArray[i];
					//log.debug("leftIndex>>>"+leftIndex+"  rightIndex>>>"+rightIndex);
					String filed = lineTxt.substring(leftIndex, rightIndex);
					leftIndex = rightIndex + 1;
					ZmDataMap.put(i, filed);
				}
				ZmDataList.add(ZmDataMap);
			}
		} finally {
			IOUtils.closeQuietly(reader);
		}
		
		/**--------------- 解析明细行记录 ---------------*/
		BigDecimal payTotalAmount = BigDecimal.ZERO;// 支付订单总金额
		Integer payTotalCount = 0;// 支付订单总笔数
		BigDecimal refundTotalAmount = BigDecimal.ZERO;// 退款订单总金额
		Integer refundTotalCount = 0;// 退款订单总笔数
		//List<OutsideDailyBizBillItem> list = new ArrayList<OutsideDailyBizBillItem>();
		List<OutsideDailyBizBillItem> payList = new ArrayList<OutsideDailyBizBillItem>();
		List<OutsideDailyBizBillItem> refundList = new ArrayList<OutsideDailyBizBillItem>();
		OutsideDailyBizBillItem item = null;
		String txnType = "";
        log.info("支付类型标识>>>"+UnionpayTranTagPay);
        log.info("退款类型标识>>>"+UnionpayTranTagRefund);
		for (int i = 0; i < ZmDataList.size(); i++) {
			Map<Integer, String> ZmDataMapTmp = ZmDataList.get(i);
			item = new OutsideDailyBizBillItem();		
			/**--------------------------公共字段----------------------------*/
            item.setBatchNo(batch.getBatchNo());
            item.setPayChannel(batch.getPayChannel());
            item.setMerchantAppId(batch.getChannelAppId());
            item.setMerchantId(batch.getChannelMerchantId());
            item.setBillDate(batch.getBillDate());           	
			/**-----------------------打印交易类型-----------------------------*/
			txnType = ZmDataMapTmp.get(19);
			log.info("交易类型："+txnType);
			/**-----------------------交易类型：消费---------------------------*/
			if(UnionpayTranTagPay.equals(txnType)){
				log.info("解析消费交易");
                BigDecimal payAmount = new BigDecimal(ZmDataMapTmp.get(6));             	
            	payTotalAmount = payTotalAmount.add(payAmount);
            	payTotalCount++;                	
            	item.setPayOrderNo(ZmDataMapTmp.get(11));
            	item.setPayTradeNo(ZmDataMapTmp.get(9));            
                item.setPayOrderTime(DateUtil.parseUnionpayTime(ZmDataMapTmp.get(4)));//取交易传输时间 txnTime n10 MMDDhhmmss
                item.setPaySuccessTime(DateUtil.parseUnionpayTime(ZmDataMapTmp.get(4)));//取交易传输时间 txnTime n10 MMDDhhmmss
                item.setPayTradeStatus(TradeStatusEnum.success.name());//账单记录默认为成功success
                item.setPayTradeAmount(payAmount);
                payList.add(item);	
			}
			/**-----------------------交易类型：退货---------------------------*/
			else if(UnionpayTranTagRefund.equals(txnType)){
				log.info("解析退货交易");                
                BigDecimal refundAmount = new BigDecimal(ZmDataMapTmp.get(6));
            	refundTotalAmount = refundTotalAmount.add(refundAmount);
            	refundTotalCount++;
            	item.setPayOrderNo(ZmDataMapTmp.get(40));//41 原始交易订单号 ans32 默认为空，预授权完成、退货、消费撤销、预授权撤销、预授权完成撤销交易才填写。
            	item.setPayTradeNo(ZmDataMapTmp.get(26));//27 原交易查询流水号 origQryId an21 取值同交易接口说明               
                item.setPayOrderTime(DateUtil.parseUnionpayTime(ZmDataMapTmp.get(14)));//15 原始交易日期时间 n10 见注4
                item.setPaySuccessTime(DateUtil.parseUnionpayTime(ZmDataMapTmp.get(14)));//15 原始交易日期时间 n10 见注4
                item.setPayTradeStatus(TradeStatusEnum.success.name());
                item.setPayTradeAmount(null);         	
                item.setRefundOrderNo(ZmDataMapTmp.get(11));
            	item.setRefundTradeNo(ZmDataMapTmp.get(9));
                item.setRefundAmount(refundAmount);
                item.setRefundApplyTime(DateUtil.parseUnionpayTime(ZmDataMapTmp.get(4)));//取交易传输时间 txnTime n10 MMDDhhmmss
                item.setRefundSuccessTime(DateUtil.parseUnionpayTime(ZmDataMapTmp.get(4)));//取交易传输时间 txnTime n10 MMDDhhmmss
                item.setRefundStatus(TradeStatusEnum.success.name());
                refundList.add(item);	
			}	
			/*
			log.info("行数: " + (i + 1));
			for (Iterator<Integer> it = ZmDataMapTmp.keySet().iterator(); it
					.hasNext();) {
				Integer key = it.next();
				String value = ZmDataMapTmp.get(key);
				System.out.println("序号：" + key + " 值: '" + value + "'");
			}*/
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
	 * 解析业务汇总
	 * @param batch 
	 */
	private boolean parseBizSummarytBill(File file, BillBizBatch batch) {
		return true;
	}

	/**
	 * 解析业务账单
	 */
	public Map<String, List<OutsideDailyBizBillItem>> parseBizBill(BillBizBatch batch) throws Exception {		
		/**----------------解压缩-----------------*/
		//String billZip = batch.getChannelBillStorePath();
		String billZip = BillUtil.readBillOutsidePath() + batch.getChannelBillStorePath();
		log.info("unionpay账单压缩文件："+billZip);
		int index = billZip.lastIndexOf("/");		
		String outDirStr = billZip.substring(0, index);
		ZipUtil.unzip(billZip, outDirStr);		
		/**----------------文件提取-----------------*/
		File itemFile = BillUtil.extractChannelBizItemBillFile(new File(outDirStr), new UnionpayBizItemBillFileFilter());
		batch.setHandleStatus(BatchStatusEnum.hasParse.name());	
		batch.setHandleRemark("银联支付业务日账单已解析");
		/**----------------明细解析-----------------*/			
		return parseBizDetailBill(itemFile, batch);		
	}	

}
