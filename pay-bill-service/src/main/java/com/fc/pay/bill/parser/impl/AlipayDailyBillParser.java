package com.fc.pay.bill.parser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fc.pay.bill.constant.BillConstant;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.filter.AlipayBizItemBillFileFilter;
import com.fc.pay.bill.filter.UnionpayBizItemBillFileFilter;
import com.fc.pay.bill.parser.OutsideDailyBillParser;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.utils.ZipUtil;
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;
import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.pay.alipay.AliConfig;

/**
 * 支付宝日账单解析
 * 
 * @author zhanjq
 *
 */
@Component("alipayDailyBillParser")
public class AlipayDailyBillParser implements OutsideDailyBillParser {
	
	private static final MathContext mathContext = new MathContext(2, RoundingMode.HALF_DOWN);
	
	private static final Logger log = LoggerFactory.getLogger(AlipayDailyBillParser.class);
	
	//业务类型-在线支付
	private static final String AlipayTranTagPay = BillUtil.readAlipayTranTagPay();
	
	//业务类型-退款
	private static final String AlipayTranTagRefund = BillUtil.readAlipayTranTagRefund();
	
	/**
	 * 解析业务明细
	 * 
	 * 行记录说明：
	 * 第6行至倒数第5行
	 * 
	 * 明细字段说明：
	 * 支付宝交易号(0),	商户订单号(1),业务类型(2),商品名称(3),创建时间(4),完成时间(5),门店编号(6),门店名称(7),操作员(8),终端号(9),对方账户(10),
	 * 订单金额（元）(11),商家实收（元）(12),支付宝红包（元）(13),集分宝（元）(14),支付宝优惠（元）(15),商家优惠（元）(16),券核销金额（元）(17),券名称(18),
	 * 商家红包消费金额（元）(19),卡消费金额（元）(20),退款批次号/请求号(21),服务费（元）(22),分润（元）(23),备注(24)
	 * @param batch 
	 * 
	*/	
	private Map<String, List<OutsideDailyBizBillItem>> parseBizDetailBill(File billFile, BillBizBatch batch)  throws Exception {
		Map<String, List<OutsideDailyBizBillItem>> dataMap = new HashMap<String, List<OutsideDailyBizBillItem>>();
		//List<OutsideDailyBizBillItem> list = new ArrayList<OutsideDailyBizBillItem>();
		List<OutsideDailyBizBillItem> payList = new ArrayList<OutsideDailyBizBillItem>();
		List<OutsideDailyBizBillItem> refundList = new ArrayList<OutsideDailyBizBillItem>();
		InputStreamReader inputReader = null;
        CSVParser csvFileParser = null;
        BigDecimal payTotalAmount = BigDecimal.ZERO;// 支付订单总金额
		Integer payTotalCount = 0;// 支付订单总笔数
		BigDecimal refundTotalAmount = BigDecimal.ZERO;// 退款订单总金额
		Integer refundTotalCount = 0;// 退款订单总笔数
        try {
        	inputReader = new InputStreamReader(new FileInputStream(billFile), PayConstants.GBK_ENCODING); //PayConstants.GBK_ENCODING   PayConstants.UTF8_ENCODING
            csvFileParser = new CSVParser(inputReader, CSVFormat.DEFAULT);
            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
            int totalCount = csvRecords.size();            
            int start = 5;
            int end = totalCount - 4;
            log.info("支付宝明细账单文件总行数>>>"+totalCount+"  start>>>"+start+"   end>>>"+end);
            OutsideDailyBizBillItem item = null;
            log.info("支付类型标识>>>"+AlipayTranTagPay);
            log.info("退款类型标识>>>"+AlipayTranTagRefund);
            for (int i = start; i < end; i++) {
        		CSVRecord record = csvRecords.get(i);
        		log.info(record.toString()); 
        		//只处理备注为fcpay的账单记录
        		/**
        		由于来自枫车支付的账单记录缺少备注信息，暂时注释
        		if(!AliConfig.TRANS_MEMO.equals(record.get(24).trim())){
        			continue;
        		}
        		*/
        		//暂时处理方法：加入商户订单号以8开头，则认为是枫车支付账单记录，若不是则丢弃之
        		if(!record.get(1).trim().startsWith("8")){
        			continue;
        		}
        		
                item = new OutsideDailyBizBillItem();
                /**--------------------------公共字段----------------------------*/
                item.setBatchNo(batch.getBatchNo());
                item.setPayChannel(batch.getPayChannel());
                item.setMerchantAppId(batch.getChannelAppId());
                item.setMerchantId(batch.getChannelMerchantId());
                item.setBillDate(batch.getBillDate());           	
                //String bizType = record.get(2);
                String bizType = record.get(2) !=null ? record.get(2).trim() : "";
                /** -------------------------交易------------------------------*/
                if(AlipayTranTagPay.equals(bizType)){
                	BigDecimal payAmount = new BigDecimal(record.get(11), mathContext);                	
                	payTotalAmount = payTotalAmount.add(payAmount);
                	payTotalCount++;                	
                	item.setPayOrderNo(record.get(1).trim());
                	item.setPayTradeNo(record.get(0).trim());
	                item.setPayOrderTime(DateUtil.parseHyphenTimeContent(record.get(4).trim()));
	                item.setPaySuccessTime(DateUtil.parseHyphenTimeContent(record.get(5).trim()));
	                item.setPayTradeStatus(TradeStatusEnum.success.name());//日账单存在此交易记录，说明是成功的。
	                item.setPayTradeAmount(payAmount);
	                payList.add(item);
                }
                /** -------------------------退款------------------------------*/
                else if(AlipayTranTagRefund.equals(bizType)){ 
                	//BigDecimal refundAmount = new BigDecimal(record.get(11).trim());
                	BigDecimal refundAmount = new BigDecimal(Math.abs(Double.valueOf(record.get(11).trim())), mathContext);
                	refundTotalAmount = refundTotalAmount.add(refundAmount);
                	refundTotalCount++;
                	item.setPayOrderNo(record.get(1).trim());
                	item.setPayTradeNo(record.get(0).trim());              
	                item.setPayOrderTime(null);
	                item.setPaySuccessTime(null);
	                item.setPayTradeStatus(TradeStatusEnum.success.name());//只有交易成功，才可以退款，所以交易状态肯定是成功的
	                item.setPayTradeAmount(null);          	
                	item.setRefundOrderNo(record.get(21).trim());//退款请求号
                    item.setRefundTradeNo(null);//支付宝无退款交易号字段，只有退款请求号
                    item.setRefundAmount(refundAmount);
                    item.setRefundApplyTime(DateUtil.parseHyphenTimeContent(record.get(4).trim()));
                    item.setRefundSuccessTime(DateUtil.parseHyphenTimeContent(record.get(5).trim()));
                    item.setRefundStatus(TradeStatusEnum.success.name());//日账单存在此退款记录，说明是成功的。
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
        } finally {
        	IOUtils.closeQuietly(inputReader);
            IOUtils.closeQuietly(csvFileParser);                      
        }
        
        return dataMap;
	}

	/**
	 * 解析业务汇总
	 * 
	 * 行记录说明：
	 * 倒数第2行
	 * 
	 * 汇总字段说明：
	 * 门店编号(0),门店名称(1),
	 * 交易订单总笔数(2),退款订单总笔数(3),
	 * 订单金额（元）(4),
	 * 商家实收（元）(5),支付宝优惠（元）(6),商家优惠（元）(7),卡消费金额（元）(8),服务费（元）(9),分润（元）(10),实收净额（元）(11)
	 * @param batch 
	 */
	private boolean parseBizSummarytBill(String summaryBillPath, BillBizBatch batch) throws Exception {
		InputStreamReader inputReader = null;
        CSVParser csvFileParser = null;
        try {
        	inputReader = new InputStreamReader(new FileInputStream(summaryBillPath), PayConstants.UTF8_ENCODING);//"gbk"
            csvFileParser = new CSVParser(inputReader, CSVFormat.DEFAULT);
            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
            int targetIndex = csvRecords.size()-2;
    		CSVRecord record = csvRecords.get(targetIndex);
    		log.info(record.toString());     
            batch.setChannelTradeCount(Integer.parseInt(record.get(2)));
            batch.setChannelTradeAmount(new BigDecimal(record.get(4)));
            batch.setChannelRefundCount(Integer.parseInt(record.get(3)));
            //batch.setChannelRefundAmount(new BigDecimal(record.get(4)));//缺少退款总金额，只好以业务明细为准汇总
        } finally {
        	IOUtils.closeQuietly(inputReader);
        	IOUtils.closeQuietly(csvFileParser);
        }
		return true;
	}
	
	/**
	 * 解析业务账单
	 */
	public Map<String, List<OutsideDailyBizBillItem>> parseBizBill(BillBizBatch batch)  throws Exception {
		/**----------------解压缩-----------------*/
		//String billZip = batch.getChannelBillStorePath();
		String billZip = BillUtil.readBillOutsidePath() + batch.getChannelBillStorePath();
		log.info("alipay账单压缩文件："+billZip);
		int index = billZip.lastIndexOf("/");		
		String outDirStr = billZip.substring(0, index);
		//ZipUtil.unzip(billZip, outDirStr, Charset.forName(PayConstants.GBK_ENCODING));
		log.info("file.encoding>>>"+System.getProperty("file.encoding"));
		ZipUtil.unzip(billZip, outDirStr, Charset.forName(PayConstants.GBK_ENCODING));
		//ZipUtil.unzip(billZip, outDirStr, Charset.forName(PayConstants.UTF8_ENCODING));
		/**----------------文件提取-----------------*/
		File itemFile = BillUtil.extractChannelBizItemBillFile(new File(outDirStr), new AlipayBizItemBillFileFilter());
		/**----------------账单解析-----------------*/
		batch.setHandleStatus(BatchStatusEnum.hasParse.name());	
		batch.setHandleRemark("支付宝支付业务日账单已解析");
		return parseBizDetailBill(itemFile,batch);
	}
	
	public static void main(String[] args) {
		try{
//		new AlipayDailyBillParser().parseBizBill(batch);
		String billZip = "D:/Documents/Downloads/download-20170401.zip";
		log.info("alipay账单压缩文件："+billZip);
		int index = billZip.lastIndexOf("/");		
		String outDirStr = billZip.substring(0, index);
		
		//ZipUtil.unzip(billZip, outDirStr, Charset.forName(PayConstants.GBK_ENCODING));
		log.info("file.encoding>>>"+System.getProperty("file.encoding"));
		ZipUtil.unzip(billZip, outDirStr, Charset.forName(PayConstants.GBK_ENCODING));
		//ZipUtil.unzip(billZip, outDirStr, Charset.forName(PayConstants.UTF8_ENCODING));
		/**----------------文件提取-----------------*/
		File itemFile = BillUtil.extractChannelBizItemBillFile(new File(outDirStr), new AlipayBizItemBillFileFilter());
		
		Map<String, List<OutsideDailyBizBillItem>> dataMap = new HashMap<String, List<OutsideDailyBizBillItem>>();
		//List<OutsideDailyBizBillItem> list = new ArrayList<OutsideDailyBizBillItem>();
		List<OutsideDailyBizBillItem> payList = new ArrayList<OutsideDailyBizBillItem>();
		List<OutsideDailyBizBillItem> refundList = new ArrayList<OutsideDailyBizBillItem>();
		InputStreamReader inputReader = null;
        CSVParser csvFileParser = null;
        BigDecimal payTotalAmount = BigDecimal.ZERO;// 支付订单总金额
		Integer payTotalCount = 0;// 支付订单总笔数
		BigDecimal refundTotalAmount = BigDecimal.ZERO;// 退款订单总金额
		Integer refundTotalCount = 0;// 退款订单总笔数
        try {
        	inputReader = new InputStreamReader(new FileInputStream(itemFile), PayConstants.GBK_ENCODING); //PayConstants.GBK_ENCODING   PayConstants.UTF8_ENCODING
            csvFileParser = new CSVParser(inputReader, CSVFormat.DEFAULT);
            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
            int totalCount = csvRecords.size();            
            int start = 5;
            int end = totalCount - 4;
            log.info("支付宝明细账单文件总行数>>>"+totalCount+"  start>>>"+start+"   end>>>"+end);
            OutsideDailyBizBillItem item = null;
            log.info("支付类型标识>>>"+AlipayTranTagPay);
            log.info("退款类型标识>>>"+AlipayTranTagRefund);
            for (int i = start; i < end; i++) {
        		CSVRecord record = csvRecords.get(i);
        		log.info(record.toString()); 
        		//只处理备注为fcpay的账单记录
        		/**
        		由于来自枫车支付的账单记录缺少备注信息，暂时注释
        		if(!AliConfig.TRANS_MEMO.equals(record.get(24).trim())){
        			continue;
        		}
        		*/
        		//暂时处理方法：加入商户订单号以8开头，则认为是枫车支付账单记录，若不是则丢弃之
        		if(!record.get(1).trim().startsWith("8")){
        			continue;
        		}
                item = new OutsideDailyBizBillItem();
                /**--------------------------公共字段----------------------------*/
//                item.setBatchNo(batch.getBatchNo());
//                item.setPayChannel(batch.getPayChannel());
//                item.setMerchantAppId(batch.getChannelAppId());
//                item.setMerchantId(batch.getChannelMerchantId());
//                item.setBillDate(batch.getBillDate());           	
                String bizType = record.get(2) !=null ? record.get(2).trim() : "";
                /** -------------------------交易------------------------------*/
                System.out.println("AlipayTranTagPay=>"+AlipayTranTagPay);
                System.out.println("bizType=>"+bizType);
                System.out.println("==========================>"+AlipayTranTagPay.equals(bizType));
                if(AlipayTranTagPay.equals(bizType)){
                	BigDecimal payAmount = new BigDecimal(record.get(11), mathContext);                 	
                	payTotalAmount = payTotalAmount.add(payAmount);
                	payTotalCount++;                	
                	item.setPayOrderNo(record.get(1).trim());
                	item.setPayTradeNo(record.get(0).trim());	                
	                item.setPayOrderTime(DateUtil.parseHyphenTimeContent(record.get(4).trim()));
	                item.setPaySuccessTime(DateUtil.parseHyphenTimeContent(record.get(5).trim()));
	                item.setPayTradeStatus(TradeStatusEnum.success.name());//日账单存在此交易记录，说明是成功的。
	                item.setPayTradeAmount(payAmount);
	                payList.add(item);
                }
                /** -------------------------退款------------------------------*/
                else if(AlipayTranTagRefund.equals(bizType)){ 
                	//BigDecimal refundAmount = new BigDecimal(record.get(11).trim());
                	BigDecimal refundAmount = new BigDecimal(Math.abs(Double.valueOf(record.get(11).trim())), mathContext);
                	System.out.println("============"+refundAmount);
                	refundTotalAmount = refundTotalAmount.add(refundAmount);
                	refundTotalCount++;
                	item.setPayOrderNo(record.get(1).trim());
                	item.setPayTradeNo(record.get(0).trim());              
	                item.setPayOrderTime(null);
	                item.setPaySuccessTime(null);
	                item.setPayTradeStatus(TradeStatusEnum.success.name());//只有交易成功，才可以退款，所以交易状态肯定是成功的
	                item.setPayTradeAmount(null);          	
                	item.setRefundOrderNo(record.get(21).trim());//退款请求号
                    item.setRefundTradeNo(null);//支付宝无退款交易号字段，只有退款请求号
                    item.setRefundAmount(refundAmount);
                    item.setRefundApplyTime(DateUtil.parseHyphenTimeContent(record.get(4).trim()));
                    item.setRefundSuccessTime(DateUtil.parseHyphenTimeContent(record.get(5).trim()));
                    item.setRefundStatus(TradeStatusEnum.success.name());//日账单存在此退款记录，说明是成功的。
                    refundList.add(item);
                }                
                
            }
            //明细列表
            dataMap.put(BillConstant.KEY_PAY, payList);
            dataMap.put(BillConstant.KEY_REFUND, refundList);
            //统计数据
//            batch.setChannelTradeAmount(payTotalAmount);
//            batch.setChannelTradeCount(payTotalCount);
//            batch.setChannelRefundAmount(refundTotalAmount);
//            batch.setChannelRefundCount(refundTotalCount);            
        } finally {
        	IOUtils.closeQuietly(inputReader);
            IOUtils.closeQuietly(csvFileParser);                      
        }
        
        System.out.println("----------"+dataMap.get(BillConstant.KEY_PAY).size());
        List<OutsideDailyBizBillItem> payListData = dataMap.get(BillConstant.KEY_PAY);
	    for(OutsideDailyBizBillItem item : payListData){
	    	log.info("支付记录>>>"+item.toString());
	    }
	    List<OutsideDailyBizBillItem> refundListData = dataMap.get(BillConstant.KEY_REFUND);
	    for(OutsideDailyBizBillItem item : refundListData){
	    	log.info("退款记录>>>"+item.toString());
	    }		    
	    
		}catch(Exception e){
			e.printStackTrace();
		}
        
	}
	
}
