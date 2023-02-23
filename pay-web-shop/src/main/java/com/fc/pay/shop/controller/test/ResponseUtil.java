package com.fc.pay.shop.controller.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.compress.utils.IOUtils;

import com.fc.pay.sdk.exception.FcPayException;

public class ResponseUtil {
	
	public static Response success(Object object){
		Response rtn = new Response();
		rtn.setData(new Data("00000","交易成功",object));
		rtn.setSign("签名值");		
		return rtn;		
	}
	
	public static Response fail(String errCode, String errMesg){
		Response rtn = new Response();
		rtn.setData(new Data(errCode,errMesg,null));
		rtn.setSign("签名值");		
		return rtn;		
	}
	
	public static Response fail(FcPayException e){
		Response rtn = new Response();
		//rtn.setData(new Data(e.getCode(),e.getMsg(),e.getCause()));
		String errorDetail = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(baos));
		try {
			errorDetail = baos.toString("utf-8");
		}catch (UnsupportedEncodingException ex){	
		}finally{
			IOUtils.closeQuietly(baos);
		}
		rtn.setData(new Data(e.getCode(),e.getMsg(), errorDetail));
		rtn.setSign("签名值");		
		return rtn;		
	}


}
