package com.fc.pay.geteway.security;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.common.security.RSASignature;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.service.merchant.IMerchantApp;
import com.fc.pay.trade.utils.Return;


/**
 * 安全拦截器
 * @author zhanjq
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);
	public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	private static final String SIGN = "sign";
	
	private static final String MERCHANT_APP_CODE = "merchantAppCode";

	public static final String merchantApp = "merchantApp";
	
	@Autowired
	private IMerchantApp merchantAppService;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		//取得所有请求参数key
		List<String> paramKeyList = new ArrayList<String>(request.getParameterMap().keySet());
		//排序
		Collections.sort(paramKeyList);
		
		//按已排序key列表组装待签名字符串
		StringBuffer strb = new StringBuffer();
		for(int i=0; i<paramKeyList.size(); i++){
			String key = paramKeyList.get(i);
			if(!SIGN.equals(key)){
				strb.append(key).append("=").append(request.getParameter(key)).append("&");
			}
		}
		String validStr = StringUtils.removeEnd(strb.toString(), "&");
		log.debug(validStr);
		
	    String sign = request.getParameter(SIGN);
	    String merchant_app_code = request.getParameter(MERCHANT_APP_CODE);
	    if(StringUtils.isBlank(merchant_app_code) || StringUtils.isBlank(sign)){
	    	return this.onAccessDenied(response);
	    }
	    
        MerchantApp merchant = merchantAppService.getByMerchantAppCode(merchant_app_code);
        String bizPublicKey  = merchant.getMctPubKey();
        if(merchant == null || StringUtils.isBlank(bizPublicKey)){
        	return this.onAccessDenied(response);
        }
        
        //return true;
		//验签
        RSASignature rsaSignature = new RSASignature();
        
        System.out.println("validStr=>\n"+validStr);
        System.out.println("sign=>\n"+sign);
        System.out.println("bizPublicKey\n"+bizPublicKey);
        
        boolean check = false;
        try{
        	check = rsaSignature.doCheck(validStr, sign, bizPublicKey,"utf-8");
        }catch(Exception e){
        	e.printStackTrace();
        }
        if(check == false){
        	this.onAccessDenied(response);
        }else{
        	request.setAttribute(merchantApp, merchant);
        }        
        System.out.println("check=>"+check);
        System.out.println("merchantApp config is not null ? "+(request.getAttribute(merchantApp)!=null));
		return check;
	}
	
	public boolean onAccessDenied(HttpServletResponse response) throws Exception {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		Return obj = new Return(CodeEnum._00010.name(),CodeEnum._00010.getValue(),null);
		out.print(OBJECT_MAPPER.writeValueAsString(obj).toString());
		return false;
	}

}
