package com.fc.pay.bill.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.tencent.protocol.downloadbill_protocol.DownloadBillResData;

/**
 * 异常工具类
 * 
 * @author zhanjq
 *
 */
public class ExceptionUtil {
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionUtil.class);
	
	/**
	 * 描述alipay异常信息
	 * @param e
	 * @return
	 */
	public static String descException(AlipayApiException e){
		return "errCode["+e.getErrCode()+"]errMsg["+e.getErrMsg()+"]";
	}
	
	/**
	 * 描述alipay异常信息
	 * @param e
	 * @return
	 */
	public static String descException(AlipayDataDataserviceBillDownloadurlQueryResponse response){
		return "code["+response.getCode()+"]msg["+response.getMsg()+"]sub_code["+response.getSubCode()+"]sub_msg["+response.getSubMsg()+"]";
	}
	
	
	
	/**
	 * 描述unionpay异常信息
	 * @param rspData
	 * @return
	 */
	public static String descException(Map<String, String> rspData){
		return "respCode["+rspData.get("respCode")+"]respMsg["+rspData.get("respMsg")+"]";
	}
	
	
	/**
	 * 描述weixin异常信息
	 * @param data
	 * @return
	 */
	public static String descException(DownloadBillResData data){
		return "return_code["+data.getReturn_code()+"]return_msg["+data.getReturn_msg()+"]";
	}
	

}
