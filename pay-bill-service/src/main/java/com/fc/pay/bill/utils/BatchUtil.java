package com.fc.pay.bill.utils;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 批次工具类
 * @author zhanjq
 *
 */
public class BatchUtil {

	private static final Logger log = LoggerFactory.getLogger(BatchUtil.class);
	
	private static final char[] charDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	
	/**
	 * 生成对账批次号
	 * @return
	 */
	public static String makeBatchNo(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("B");//B：业务对账；F：财务对账
		buffer.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		buffer.append(ESAPI.randomizer().getRandomString(6,charDigits));//随机数
		return buffer.toString();
	}
	
	public static void main(String[] args){
		log.info(BatchUtil.makeBatchNo().toString());
	}
	
}
