package com.fc.pay.bill.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.enums.BillMakeStatusEnum;
import com.fc.pay.bill.enums.MistakeNotifyStatusEnum;
import com.fc.pay.bill.service.BillBizCheckTranxService;
import com.fc.pay.bill.service.BillBizFileNotifyService;
import com.fc.pay.bill.service.BillBizSummaryService;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.vo.BillBizCheckProgressData;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.MerchantAppConfig;

/**
 * 业务日对账-核心流程
 * 
 * @author zhanjq
 *
 */
@Service("billBizCheckCoreBusiness")
public class BillBizCheckCoreBusiness {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizCheckCoreBusiness.class);
	
	/**
	 * 本地数据加载组件
	 */
	@Autowired
	private LocalDataBusiness localDataBusiness;
	
	/**
	 * 对账存疑缓存组件
	 */
//	@Autowired
//	private BillBizDoubtBusiness doubtBusiness;
	
	/**
	 * 批次对账业务组件
	 */
	@Autowired
	private BillBizCheckBatchBusiness batchBusiness;

	/**
	 * 内部账单生成组件
	 */
	@Autowired
	private BillBizFileBusiness fileBusiness;

	/**
	 * 对账结果通知报告
	 */
	@Autowired
	private BillBizNotifyBusiness notifyBusiness;
	
	/**
	 * 对账事务数据服务
	 */
	@Autowired
	private BillBizCheckTranxService tranxService;
	
	/**
	 * 业务日对账汇总服务
	 */
	@Autowired
	private BillBizSummaryService summaryService;
	
	/**
	 * 业务日对账文件通知服务
	 */
	@Autowired
	private BillBizFileNotifyService fileNotifyService;
	
	
	/**
	 * 业务日对账-核心流程
	 */
	public void checkBiz(Date billDate) {
		log.info("--------------------------------- 枫车支付日对账-start --------------------------------");		
		/*------------------------------ 1.确定账单日期 ----------------------------------*/
		log.info("1.业务账单日期 ......["+DateUtil.makeDefaultDateFormat(billDate)+"]");		
		
		/*-------------------------------2.查询商户应用 ----------------------------*/
	    log.info("2.查询商户应用......");
	    List<MerchantApp> merchantAppList = localDataBusiness.findMerchantAppList();//枫车商城，枫车快手
	    if(merchantAppList==null || merchantAppList.size()<=0){
	    	log.error("缺少商户应用配置，对账中止");
	    	return;
	    }		
		/*------------------------------ 3.查询对账接口 -------------------------------*/
		log.info("3.查询对账接口......");
		List<MerchantAppConfig> macList = localDataBusiness.findMerchantAppConfigList();
		if(CollectionUtils.isEmpty(macList)){
			log.info("缺少支付渠道接口配置，对账中止。");
			return;
		}
		//对账接口去重处理（同个商户不同系统使用同个对账接口，如appid相同或者mchId相同）
		Map<String,MerchantAppConfig> macMap = new HashMap<String,MerchantAppConfig>();
		for(MerchantAppConfig mac : macList){
			String channel = mac.getChannel();
			String appId = mac.getChannelAppId();
			String mchId = mac.getChannelMerchantId();
			String macKey = null;
			if(PayChannelEnum.alipay.name().equals(channel) || PayChannelEnum.weixin.name().equals(channel)){
				macKey = channel+appId;
			}else if(PayChannelEnum.unionpay.name().equals(channel)){
				macKey = channel+mchId;
			}
			macMap.put(macKey, mac);
		}
		macList.clear();
		macList.addAll(macMap.values());
		if(CollectionUtils.isEmpty(macList)){
			log.info("支付渠道接口配置经过去重处理之后为空集，对账中止。");
			return;
		}
		
	    /*-------------------------------4.准备过程数据 ----------------------------*/
		BillBizCheckProgressData data = tranxService.prepareProgressData(billDate, merchantAppList, macList);
		BillBizSummary summary = data.getSummary();
		List<BillBizBatch> batchList = data.getBatchList();
		List<BillBizFileNotify> fileNotifyList = data.getFileNotifyList();
		
		/*------------------------------ 5.对账批次逐个对账-------------------------------*/
		log.info("5.对账批次逐个对账......");		
		for(BillBizBatch batch : batchList){
			try {
				if(!batchBusiness.checkBatch(batch, summary)){
					log.error("本批次对账失败");
					continue;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				continue;
			}				
		}		
		
		//如果批次对账没有成功则不允许生成账单
	    if(summary.getBatchCount().compareTo(summary.getBatchRunSuccessCount()) != 0){
	    	//跑批没有全部完成，报告业务日对账情况
	    	log.info("跑批没有全部完成，报告业务日对账情况");
	    	notifyBusiness.reportBillCheckResult(summary);
	    	return;
	    }
	    
	    /*-------------------------------6.整理对账存疑表 ----------------------------*/
	    //log.info("6.整理对账存疑表......");
	    //doubtBusiness.handleExpireDoubtData(Integer.valueOf(BillUtil.readDoubtClearPeriod()));
	    
	    /*-------------------------------7.商户应用逐个生成日账单并通知下载 ----------------------------*/
	    log.info("7.商户应用逐个生成日账单并通知下载......");
	    for(BillBizFileNotify fileNotify : fileNotifyList){
	    	makeBillAndNotifyDownload(fileNotify, summary);	
	    }
	    
	    /*-------------------------------8.每日业务对账汇总提示 ----------------------------*/
	    log.info("8.每日业务对账汇总报告......");
	    notifyBusiness.reportBillCheckResult(summary); 
	    log.info("--------------------------------- 枫车支付日对账 -end --------------------------------");
	}

	/**
	 * 生成账单并通知下载
	 * @param fileNotify	账单通知
	 * @param summary		对账汇总
	 */
	private void makeBillAndNotifyDownload(BillBizFileNotify fileNotify, BillBizSummary summary) {	    
    	/** -----------------------生成商户应用日账单---------------------------*/
		buildInsideBill(fileNotify, summary);
    	/** -----------------------发送商户应用账单下载通知---------------------------*/
		sendDownloadNotify(fileNotify, summary);
	}
	
	private void buildInsideBill(BillBizFileNotify fileNotify, BillBizSummary summary){
		if(BillMakeStatusEnum.success.name().equals(fileNotify.getFileStatus())){
			log.info("内部账单已生成成功，不允许重复生成.");
			return;
		}
		//String zipBillPath = null;
		String zipRelativePath = null;
		try {
			zipRelativePath = fileBusiness.makeMerchantAppBill(fileNotify.getMerchantCode(), fileNotify.getMerchantAppCode(), summary.getBillDate());
		} catch (Exception e) {
	    	fileNotifyService.modifyFileField(fileNotify.getId(), null, BillMakeStatusEnum.fail.name(), e.getMessage());
	    	return;
		}
		//保存记录
    	fileNotifyService.modifyFileField(fileNotify.getId(), zipRelativePath, BillMakeStatusEnum.success.name(), "商户应用账单生成成功");
    	//累计账单生成成功个数
    	summary.setBillMakeSuccessCount(summary.getBillMakeSuccessCount()+1);
    	summaryService.modifyBillMakeSuccessCount(summary.getId(), summary.getBillMakeSuccessCount());
	}
	
	private void sendDownloadNotify(BillBizFileNotify fileNotify, BillBizSummary summary){
		if(MistakeNotifyStatusEnum.send_success.name().equals(fileNotify.getNotifyStatus())){
			log.info("账单应用已发送成功，不允许重复发送.");
			return;
		}
		String billDateStr = DateUtil.makeDefaultDateFormat(summary.getBillDate());
		String downloadNotifyUrl = fileNotify.getNotifyUrl();
    	try {
    		notifyBusiness.notifyBillDownload(fileNotify, billDateStr);
		} catch (Exception e) {
	    	fileNotifyService.modifyNotifyField(fileNotify.getId(), downloadNotifyUrl, MistakeNotifyStatusEnum.send_fail.name(), e.getMessage());
	    	return;
		}
    	//保存记录
    	fileNotifyService.modifyNotifyField(fileNotify.getId(), downloadNotifyUrl, MistakeNotifyStatusEnum.send_success.name(), "账单下载通知发送成功");
    	//累计账单通知成功个数
    	summary.setDownloadNotifySuccessCount(summary.getDownloadNotifySuccessCount()+1);
    	summaryService.modifyDownloadNotifySuccessCount(summary.getId(), summary.getDownloadNotifySuccessCount());
	}
	
	/**
	 * 重新生成内部账单
	 * @param fileNotifyId
	 */
	public void rebuildInsideBill(String fileNotifyId){
		BillBizFileNotify fileNotify = fileNotifyService.get(Long.valueOf(fileNotifyId));
		BillBizSummary summary = summaryService.getByBillDate(fileNotify.getBillDate());
		buildInsideBill(fileNotify, summary);
	}
	
	/**
	 * 重新发送下载通知
	 * @param fileNotifyId
	 */
	public void resendDownloadNotify(String fileNotifyId){
		BillBizFileNotify fileNotify = fileNotifyService.get(Long.valueOf(fileNotifyId));
		BillBizSummary summary = summaryService.getByBillDate(fileNotify.getBillDate());
		sendDownloadNotify(fileNotify, summary);
	}


}
