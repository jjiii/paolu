package com.fc.pay.bill.download.impl;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.fc.pay.bill.business.LocalDataBusiness;
import com.fc.pay.bill.download.OutsideDailyBillDownload;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.utils.ExceptionUtil;
import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.common.pay.alipay.AliConfig;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;

/**
 * 支付宝日账单下载
 * @author zhanjq
 *
 */
@Component("alipayDailyBillDownload")
public class AlipayDailyBillDownload implements OutsideDailyBillDownload {
	
	@Autowired
	private LocalDataBusiness dataBusiness;
	
	private static final Logger log = LoggerFactory.getLogger(AlipayDailyBillDownload.class);

	public void downloadBizBill(BillBizBatch batch) throws Exception {
		
		String billType = "trade";//业务对账使用trade，财务对账使用signcustomer
		String billDate = DateUtil.makeHyphenDateFormat(batch.getBillDate());
		
		/**------------------1.查询应用私有配置--------------------*/		
		String channelAppId = batch.getChannelAppId();
		log.info("channelAppId=>"+channelAppId);
		MerchantAppConfig config = dataBusiness.getMerchantAppConfigByAppId(channelAppId);
		if(config==null || StringUtils.isEmpty(config.getPriKey()) || StringUtils.isEmpty(config.getPubKey())){
			batch.setHandleStatus(BatchStatusEnum.fail.name());	
			batch.setHandleRemark("支付宝支付接口配置错误");
			throw new Exception("支付宝支付接口配置错误");
		}
		
		/**------------------1.查询账单下载地址--------------------*/		
		AlipayDataDataserviceBillDownloadurlQueryRequest request = null;
		AlipayDataDataserviceBillDownloadurlQueryResponse response = null;
		try {
			//AlipayClient alipayClient = new DefaultAlipayClient(sandboxGateway, appId,appPrivateKey, "json", "utf-8", alipayPublicKey);
			//AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.getConfig().getGateway(), batch.getChannelAppId(), 
			AlipayClient alipayClient = new DefaultAlipayClient(AliConfig.url, batch.getChannelAppId(), 
					config.getPriKey(), PayConstants.JSON_STYLE, PayConstants.UTF8_ENCODING, config.getPubKey());
			request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
			request.setBizContent("{\"bill_type\":\""+billType+"\",\"bill_date\":\""+billDate+"\"}");			
			response = alipayClient.execute(request);		
		} catch (AlipayApiException e) {
			log.error("Alipay账单下载查询异常1："+e.getMessage());
			batch.setHandleStatus(BatchStatusEnum.fail.name());			
			batch.setHandleRemark(ExceptionUtil.descException(e));
			throw e;
		} catch(Exception e){
			log.error("Alipay账单下载查询异常2："+e.getMessage());
			batch.setHandleStatus(BatchStatusEnum.fail.name());			
			batch.setHandleRemark("download发生异常>>>"+e.getMessage());
			throw e;
		}
		if (!response.isSuccess()) {
			log.error("支付宝账单下载地址查询失败");
			/** 区分是否账单不存在，如果账单日期当日没有交易则账单不存在，应认为对账成功，而不是对账错误 */
			/*
			String code = response.getCode();
			String subCode = response.getSubCode();
			if("40004".equals(code) && "isp.bill_not_exist".equals(subCode)){
				batch.setHandleStatus(BatchStatusEnum.noBill.name());	
				batch.setHandleRemark("支付宝账单不存在");	
				log.info("账单不存在，正常返回");
				return;
			}
			*/
			batch.setHandleStatus(BatchStatusEnum.fail.name());
			batch.setHandleRemark("支付宝账单下载地址查询失败");
			throw new Exception(ExceptionUtil.descException(response));
		}
		log.info("支付宝账单下载地址（30秒内有效）>>>"+response.getBillDownloadUrl());
		
		/**------------------2.根据BillDownloadUrl下载对账单--------------------*/
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		InputStream inputStream = null;
		try {
			HttpGet httpGet = new HttpGet(response.getBillDownloadUrl()); 
			httpResponse = httpclient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			log.info("支付宝账单下载字符集["+httpEntity.getContentEncoding()+"]");
			inputStream = httpEntity.getContent();
			String channel = batch.getPayChannel();
			String mchId = batch.getChannelMerchantId();//支付宝接口无商户号mchId，运行取值为null
			String appId = batch.getChannelAppId();
			String dateStr = DateUtil.makeDefaultDateFormat(batch.getBillDate());
			//渠道日账单目录相对路径
			String outsideDailyBillDir = BillUtil.makeOutsideDailyBillDirPath(channel, mchId, appId, dateStr);
			//渠道日账单文件名
			String outsideBillName = appId+"_"+dateStr+".csv.zip";
			//渠道日账单文件完整路径
			String billFilePath = BillUtil.makeOutsideDailyBillDateDirExist(outsideDailyBillDir)+outsideBillName;
			log.info("alipay账单保存路径："+billFilePath);
			File billFile = new File(billFilePath);
			if(billFile.exists()){
				log.debug("删除旧文件>>>"+billFile.getAbsolutePath());
				billFile.delete();
			}
			billFile.createNewFile();
			FileUtils.copyInputStreamToFile(inputStream, billFile);
			//batch.setChannelBillStorePath(billFilePath);
			//数据库记录只存储相对相对路径文件
			batch.setChannelBillStorePath(outsideDailyBillDir+outsideBillName);
		} catch (Exception e) {
			log.error("Alipay账单下载操作异常："+e.getMessage());
			batch.setHandleStatus(BatchStatusEnum.fail.name());			
			batch.setHandleRemark("Alipay账单下载操作异常：["+e.getMessage()+"]");
			throw e;
		}finally{
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(httpResponse);
		}
		batch.setHandleStatus(BatchStatusEnum.hasDownload.name());	
		batch.setHandleRemark("支付宝支付业务日账单已下载");
	}

}
