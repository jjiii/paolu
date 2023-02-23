package com.fc.pay.bill.business;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fc.pay.bill.builder.InsideDailyBizBillBuilder;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.enums.BillTypeEnum;
import com.fc.pay.bill.service.BillBizItemService;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.utils.ZipUtil;

/**
 * 商户应用账单制作业务
 * 
 * @author zhanjq
 *
 */
@Service("billBizFileBusiness")
public class BillBizFileBusiness {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizFileBusiness.class);
	
	/**
	 * 业务日对账账单数据服务
	 */
	@Autowired
	private BillBizItemService itemService;
	
	/**
	 * 内部账单生成组件
	 */
	@Autowired
	private InsideDailyBizBillBuilder billBuilder;
	
	/**
	 * 生成内部应用账单
	 * @param merchantAppCode 	商户应用编号
	 * @param yyyyMMdd			账单日期
	 * @return
	 * @throws Exception
	 */
	public String makeMerchantAppBill(String merchantCode, String merchantAppCode, Date billDate) throws Exception {
		
		Map<String, Object> baseParamMap = new HashMap<String,Object>();
		baseParamMap.put("merchantCode", merchantCode);
		baseParamMap.put("merchantAppCode", merchantAppCode);
		baseParamMap.put("billDate", billDate);
		
		Map<String, Object> payParamMap = new HashMap<String,Object>();
		payParamMap.putAll(baseParamMap);
		payParamMap.put("billType", BillTypeEnum.pay.name());
		
		Map<String, Object> refundParamMap = new HashMap<String,Object>();
		refundParamMap.putAll(baseParamMap);
		refundParamMap.put("billType", BillTypeEnum.refund.name());
		
    	List<BillBizItem> payItemList = itemService.list(payParamMap);//按商户应用编号以及对账类型加载
    	
    	List<BillBizItem> refundItemList = itemService.list(refundParamMap);//按商户应用编号以及对账类型加载
    	
    	String yyyyMMdd = DateUtil.makeDefaultDateFormat(billDate);
    	String insideDailyBillDir = BillUtil.makeInsideDailyBillDirPath(merchantCode, merchantAppCode, yyyyMMdd);
		String merchantAppBillPath = BillUtil.makeInsideDailyBillDateDirExist(insideDailyBillDir);
    	String merchantAppBillFilePrefix = merchantAppCode+"_"+yyyyMMdd;
    	String payBillPath = merchantAppBillPath + merchantAppBillFilePrefix +"_pay.csv";
    	String refundBillPath = merchantAppBillPath + merchantAppBillFilePrefix +"_refund.csv";    	
    	String zipBillPath = merchantAppBillPath + merchantAppBillFilePrefix +".csv.zip";   
    	String zipRelativePath = insideDailyBillDir + merchantAppBillFilePrefix +".csv.zip";
		//生成支付账单
    	log.info("6.1.商户应用日账单......支付：["+payBillPath+"]");
    	try {
			billBuilder.buildInsidePaySuccessBill(payItemList, payBillPath);
		} catch (Exception e) {
			log.error("生成支付账单异常>>>"+e.getMessage());
			//e.printStackTrace();
			throw new Exception("商户应用日账单(支付)生成失败["+e.getMessage()+"]");
		}
		//生成退款账单
    	log.info("6.2.商户应用日账单......退款：["+refundBillPath+"]");
    	try {
			billBuilder.buildInsideRefundBill(refundItemList, refundBillPath);
		} catch (Exception e) {
			log.error("生成退款账单异常>>>"+e.getMessage());
			//e.printStackTrace();
			throw new Exception("商户应用日账单(退款)生成失败["+e.getMessage()+"]");
		}
    	//打成zip压缩包
    	log.info("6.3.商户应用日账单......压缩：["+refundBillPath+"]");
    	try {
			ZipUtil.zip(new File(merchantAppBillPath), new File(zipBillPath));
		} catch (Exception e) {
			throw new Exception("商户应用日账单(压缩)失败["+e.getMessage()+"]");
		}    	
    	//return zipBillPath;
    	return zipRelativePath;
	}

}
