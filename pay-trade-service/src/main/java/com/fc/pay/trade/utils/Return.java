package com.fc.pay.trade.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.pay.common.security.RSASignature;
import com.fc.pay.trade.entity.MerchantApp;

public class Return {
	public static Logger logger = LoggerFactory.getLogger(Return.class);
	
	private String code = "10000";
	private String msg = "成功";
	private String version = "1.0";
	
	private Map result = new HashMap();
	private String sign;

	public Return() {
	}
	public Return( Map result){
		
		this.setResult(result);
	}
	
	public Return(String code, String msg, Map result, String... version) {
		this.code = code;
		this.msg = msg;
		this.setResult(result);
		if(version.length>0) this.version = version[0];
	}

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map getResult() {
		return result;
	}

	/**
	 * web实时环境使用
	 * @param result
	 */
	public void setResult(Map result) {
		try{
			if(result == null){
				result = new HashMap();
				result.put("timestamp", System.currentTimeMillis());
			}
			RSASignature RSA = new RSASignature();
			ObjectMapper mapper = new ObjectMapper();
			
			String content = mapper.writeValueAsString(result).toString();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			MerchantApp merchant = (MerchantApp)request.getAttribute("merchantApp");
			String sign = RSA.sign(content, merchant.getPriKey(), "utf-8");
			this.setSign(sign);
		}catch(Exception e){
			
			logger.info("签名失败"+e.getMessage());
        }
		this.result = result;
	}

	/**
	 * 批量作业环境使用
	 * @param result
	 * @param merchantApp
	 */
	public void setResult(Map result, MerchantApp merchantApp) {
		try{
			if(result == null){
				result = new HashMap();
				result.put("timestamp", System.currentTimeMillis());
			}
			RSASignature RSA = new RSASignature();
			ObjectMapper mapper = new ObjectMapper();
			
			String content = mapper.writeValueAsString(result).toString();
			String sign = RSA.sign(content, merchantApp.getPriKey(), "utf-8");
			this.setSign(sign);
		}catch(Exception e){
			
			logger.info("签名失败"+e.getMessage());
        }
		this.result = result;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	

}
