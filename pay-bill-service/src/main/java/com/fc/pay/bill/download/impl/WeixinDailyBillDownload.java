package com.fc.pay.bill.download.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fc.pay.bill.business.LocalDataBusiness;
import com.fc.pay.bill.config.WeixinConfig;
import com.fc.pay.bill.download.OutsideDailyBillDownload;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.utils.ExceptionUtil;
import com.fc.pay.bill.utils.ZipUtil;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.tencent.common.Util;
import com.tencent.protocol.downloadbill_protocol.DownloadBillReqData;
import com.tencent.protocol.downloadbill_protocol.DownloadBillResData;
import com.tencent.service.DownloadBillService;
import com.thoughtworks.xstream.io.StreamException;

/**
 * 微信日账单下载
 * @author zhanjq
 *
 */
@Component("weixinDailyBillDownload")
public class WeixinDailyBillDownload implements OutsideDailyBillDownload {
	
	private static final Logger log = LoggerFactory.getLogger(WeixinDailyBillDownload.class);
	
	@Autowired
	private LocalDataBusiness dataBusiness;

	public void downloadBizBill(BillBizBatch batch) throws Exception {
		
		/**------------------1.查询应用私有配置--------------------*/	
		String channelAppId = batch.getChannelAppId();
		MerchantAppConfig config = dataBusiness.getMerchantAppConfigByAppId(channelAppId);
		if(config==null || 
			StringUtils.isEmpty(config.getPubKey()) || 
			StringUtils.isEmpty(config.getChannelMerchantId()) ||
			StringUtils.isEmpty(config.getCertPath()) ||
			StringUtils.isEmpty(config.getCertPwd()) ){
			throw new Exception("微信支付接口配置错误");
		}	
		
		log.info("微信账单下载开始>>>");
		//初始化SDK依赖的几个关键配置
		//WXPay.initSDKConfiguration(key, appID, mchID, subMchID, certLocalPath, certPassword);
		try{
			WeixinConfig.getConfig().initSDKConfig(config);
		}catch(Exception e){
			log.error("环境配置异常："+e.getMessage());
			batch.setHandleStatus(BatchStatusEnum.fail.name());			
			batch.setHandleRemark("环境配置异常["+e.getMessage()+"]");
			throw e;
		}
		
        //--------------------------------------------------------------------
        //构造请求“对账单API”所需要提交的数据
        //--------------------------------------------------------------------
		String deviceInfo = "";
		String billDate = DateUtil.makeDefaultDateFormat(batch.getBillDate());//eg:"20161121"
		String mchId = batch.getChannelMerchantId();
		String appId = batch.getChannelAppId();	
		//渠道日账单目录相对路径
		String outsideDailyBillDir = BillUtil.makeOutsideDailyBillDirPath(batch.getPayChannel(), mchId, appId, billDate);
		//渠道日账单文件完整路径
		String filePath = BillUtil.makeOutsideDailyBillDateDirExist(outsideDailyBillDir);
		//String billType = "ALL";
		List<String> billTypeList = new ArrayList<String>();
		billTypeList.add("SUCCESS");//返回当日成功支付的订单
		billTypeList.add("REFUND");//返回当日退款订单		
		for(String billType : billTypeList){
			log.info("微信账单下载，对账类型["+billType+"]");
			DownloadBillReqData downloadBillReqData = new DownloadBillReqData(deviceInfo, billDate, billType);
	        //API返回的数据
	        String respStr = null;
	        try {
	        	respStr = new DownloadBillService().request(downloadBillReqData);
			} catch (Exception e) {
				log.error("weixin账单下载异常："+e.getMessage());
				batch.setHandleStatus(BatchStatusEnum.fail.name());			
				batch.setHandleRemark("weixin账单下载异常：["+e.getMessage()+"]");
				throw e;
			}	        
	        log.debug("对账单API返回的数据如下：");
	        log.debug(respStr);	        
	        DownloadBillResData downloadBillResData = null;
	        try {
	            //注意，这里失败的时候是返回xml数据，成功的时候反而返回非xml数据
	            downloadBillResData = (DownloadBillResData) Util.getObjectFromXML(respStr, DownloadBillResData.class);
	            if (downloadBillResData == null || downloadBillResData.getReturn_code() == null) {
	                log.error("对账单API请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
	                batch.setHandleStatus(BatchStatusEnum.fail.name());			
	    			batch.setHandleRemark("weixin对账单API请求逻辑错误");
	    			throw new Exception("weixin对账单API请求逻辑错误");
	            }
	            if (downloadBillResData.getReturn_code().equals("FAIL")) {
	                ///注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
	                log.error("对账单API系统返回失败，请检测Post给API的数据是否规范合法");
	                batch.setHandleStatus(BatchStatusEnum.fail.name());	
	    			batch.setHandleRemark(ExceptionUtil.descException(downloadBillResData));
	    			throw new Exception(ExceptionUtil.descException(downloadBillResData));
	            }	            
	        } catch (StreamException e) {	        	
	            //注意，这里成功的时候是直接返回纯文本的对账单文本数据，非XML格式
	            if (StringUtils.isEmpty(respStr)) {
	                log.error("对账单API系统返回数据为空");
	                batch.setHandleStatus(BatchStatusEnum.fail.name());			
	    			batch.setHandleRemark("对账单API系统返回数据为空");
	    			throw new Exception("对账单API系统返回数据为空");
	            }	            
	            log.info("对账单API系统成功返回数据");
	            /** 保存文件到指定目录...... */
	            try {
	            	String fileName = "";
	            	if("SUCCESS".equals(billType)){
	            		fileName = filePath+mchId+"_"+appId+"_"+billDate+"_pay";
	            	}else if("REFUND".equals(billType)){
	            		fileName = filePath+mchId+"_"+appId+"_"+billDate+"_refund";
	            	}
	            	//若重复下载，则删除旧文件
	            	File subFile = new File(fileName);
	        		if (subFile.exists()) {
	        			log.debug("删除旧文件>>>"+subFile.getAbsolutePath());
	        			subFile.delete();
	        		}
	        		subFile.createNewFile();		
					IOUtils.write(respStr, new FileOutputStream(subFile));
				} catch (IOException ex) {
					log.error(ex.getMessage());
					batch.setHandleStatus(BatchStatusEnum.fail.name());			
	    			batch.setHandleRemark("weixin账单写入错误:"+ex.getMessage());
	    			throw new Exception("weixin账单写入错误:"+ex.getMessage());
				}
	        }
		}//end of for...	
		
		//将下载的支付、退款两个账单文件汇总压缩为zip文件
		File source = new File(filePath);
		String fileName = mchId+"_"+appId+"_"+billDate+".zip";
		//渠道日账单文件名
		String zipRelativePath = outsideDailyBillDir+fileName;
		String zipFileName = filePath+fileName;
		File target = new File(zipFileName);
		if (target.exists()) {
			log.debug("删除旧文件>>>"+target.getAbsolutePath());
			target.delete();
		}
		target.createNewFile();		
		//压缩zip
		if(ZipUtil.zip(source, target)){
			log.info("微信账单压缩成功，zip文件["+zipFileName+"]");
			//batch.setChannelBillStorePath(zipFileName);
			batch.setChannelBillStorePath(zipRelativePath);
		}
		log.info("账单存储路径>>>"+batch.getChannelBillStorePath());
		log.info("<<<微信账单下载结束");
		batch.setHandleStatus(BatchStatusEnum.hasDownload.name());	
		batch.setHandleRemark("微信支付业务日账单已下载");
	}

	/*
	 *
	异步模式
	DownloadBillBusiness.ResultListener resultListener = new DownloadBillBusiness.ResultListener(){
		//下载对账单失败			 
		public void onDownloadBillFail(String response) {
			log.error("onDownloadBillFail=>");
		}
		//下载对账单成功
		public void onDownloadBillSuccess(String response) {
	
		}
		//API返回ReturnCode不合法，支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
		public void onFailByReturnCodeError(DownloadBillResData downloadBillResData) {
			
		}
		//API返回ReturnCode为FAIL，支付API系统返回失败，请检测Post给API的数据是否规范合法
		public void onFailByReturnCodeFail(DownloadBillResData downloadBillResData) {
			
		}		
	};
	try {
		WXPay.doDownloadBillBusiness(downloadBillReqData , resultListener);
	} catch (Exception e) {
		log.error(e.getMessage());
	}
	*/

}
