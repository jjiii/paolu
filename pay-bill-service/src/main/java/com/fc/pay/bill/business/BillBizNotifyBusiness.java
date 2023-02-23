package com.fc.pay.bill.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.common.email.EmailSender;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.utils.Return;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.enums.BillTypeEnum;
import com.fc.pay.bill.utils.DateUtil;

/**
 * 业务对账通知报告
 * 
 * @author zhanjq
 *
 */
@Service("billBizNotifyBusiness")
public class BillBizNotifyBusiness {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizNotifyBusiness.class);
	
	/**
	 * 对账报告邮件通知
	 */
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private LocalDataBusiness dataBusiness;
	
	/**
	 * 发送差错处理结果通知
	 * @param mistake
	 */
	public void notifyMistakeHandled(BillBizMistake mistake) throws Exception {
		log.info("通知对账差错已处理......["+mistake.toString()+"]");  
		
		MerchantApp merchantApp = dataBusiness.findMerchantAppByCode(mistake.getMerchantAppCode());
		String handleMistakeNotifyUrl = merchantApp.getHandleMistakeNotifyUrl();
		
		Map bizData = new HashMap();
		bizData.put("notifyId", mistake.getId());
		String billType = mistake.getBillType();
		bizData.put("billType", billType);
		if(BillTypeEnum.pay.name().equals(billType)){
			bizData.put("orderNo", mistake.getPayOrderNo()); 
        }else if(BillTypeEnum.refund.name().equals(billType)){
        	bizData.put("orderNo", mistake.getRefundOrderNo()); 
        }
		bizData.put("mistakeType", mistake.getMistakeType());  
		bizData.put("suggestion", "查询订单更正状态与金额");	
		String postDataStr = buildNotifyData(merchantApp, bizData);
		
    	CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		try {
			HttpPost httpPost = new HttpPost(handleMistakeNotifyUrl);
			/***
	        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
	        String billType = mistake.getBillType();
	        nameValuePairList.add(new BasicNameValuePair("billType", billType));
	        if(BillTypeEnum.pay.name().equals(billType)){
	        	nameValuePairList.add(new BasicNameValuePair("orderNo", mistake.getPayOrderNo())); 
	        }else if(BillTypeEnum.refund.name().equals(billType)){
	        	nameValuePairList.add(new BasicNameValuePair("orderNo", mistake.getRefundOrderNo())); 
	        }	        
	        nameValuePairList.add(new BasicNameValuePair("mistakeId", mistake.getId().toString()));  
	        nameValuePairList.add(new BasicNameValuePair("mistakeType", mistake.getMistakeType()));  
	        nameValuePairList.add(new BasicNameValuePair("suggestion", "查询订单更正状态与金额"));   
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList)); 
	        */
			/** json格式 */
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");	        
			StringEntity entity = new StringEntity(postDataStr, PayConstants.UTF8_ENCODING);
			entity.setContentType("text/json");
			httpPost.setEntity(entity);
	        httpResponse = httpclient.execute(httpPost);
	        StatusLine statusLine = httpResponse.getStatusLine();
	        log.debug("StatusLine.ReasonPhrase>>>"+statusLine.getReasonPhrase());
			if(statusLine.getStatusCode() != 200){
				throw new Exception("发送对账差错已处理通知失败，HTTP状态码["+statusLine.getStatusCode()+"]");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("发送对账差错已处理通知异常["+e.getMessage()+"]");
		}finally{
			IOUtils.closeQuietly(httpResponse);
			IOUtils.closeQuietly(httpclient);
		}
	}

	/**
	 * 发送日账单下载通知
	 * @param downloadNotifyUrl
	 * @param yyyyMMdd
	 * @throws Exception
	 */
	public void notifyBillDownload(BillBizFileNotify fileNotify, String yyyyMMdd) throws Exception {
		String merchantAppCode = fileNotify.getMerchantAppCode();
		MerchantApp merchantApp = dataBusiness.findMerchantAppByCode(merchantAppCode);

		Map bizData = new HashMap();
		bizData.put("notifyId", fileNotify.getId());
		bizData.put("billDate", yyyyMMdd);
		String postDataStr = buildNotifyData(merchantApp, bizData);
		
		String downloadNotifyUrl = fileNotify.getNotifyUrl();
    	log.info("通知下载商户应用日账单......["+downloadNotifyUrl+"]");
    	CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		try {
			HttpPost httpPost = new HttpPost(downloadNotifyUrl);  
			/**
			----普通表单格式
	        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();  
	        nameValuePairList.add(new BasicNameValuePair("billDate", yyyyMMdd));  
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
	        */
			/** json格式 */			
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");	        
			StringEntity entity = new StringEntity(postDataStr, PayConstants.UTF8_ENCODING);
			entity.setContentType("text/json");			
			httpPost.setEntity(entity);			
	        httpResponse = httpclient.execute(httpPost);
	        StatusLine statusLine = httpResponse.getStatusLine();
	        log.debug("StatusLine.ReasonPhrase>>>"+statusLine.getReasonPhrase());
			if(statusLine.getStatusCode() != 200){
				throw new Exception("发送商户应用账单下载通知失败，HTTP状态码["+statusLine.getStatusCode()+"]");
			}
		} catch (IOException e) {
			throw new Exception("发送商户应用账单下载通知异常["+e.getMessage()+"]");
		}finally{
			IOUtils.closeQuietly(httpResponse);
			IOUtils.closeQuietly(httpclient);
		}
	}
	
	private String buildNotifyData(MerchantApp merchantApp, Map bizData) throws JsonProcessingException{
		Return notifyData = new Return();
		notifyData.setCode(CodeEnum._10000.getName());
		notifyData.setMsg(CodeEnum._10000.getValue());
		notifyData.setResult(bizData , merchantApp);
		return new ObjectMapper().writeValueAsString(notifyData).toString();
	}


	/**
	 * 报告业务日对账结果
	 * @param summary
	 */
	public void reportBillCheckResult(BillBizSummary summary){
		String billDateStr = DateUtil.makeHyphenDateFormat(summary.getBillDate());
		String subject = "枫车支付业务日对账报告["+billDateStr+"]";
		StringBuffer buffer = new StringBuffer();
		buffer.append("账单日期["+billDateStr+"], ");
		buffer.append("对账批次失败个数["+(summary.getBatchCount() - summary.getBatchRunSuccessCount())+"], ");
		buffer.append("生成账单失败个数["+(summary.getMerchantAppCount() - summary.getBillMakeSuccessCount())+"], ");
		buffer.append("通知下载失败个数["+(summary.getMerchantAppCount() - summary.getDownloadNotifySuccessCount())+"]");
		try {
			emailSender.sendEmail(subject, buffer.toString());
		} catch (Exception e) {
			log.error("发送对账报告邮件失败："+e.getMessage());
		}
	}	

}
