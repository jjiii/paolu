package com.fc.pay.utils;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class ShiroFilterUtils {
	static Logger logger = LoggerFactory.getLogger(ShiroFilterUtils.class);
	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request){
		String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
		if("XMLHttpRequest".equalsIgnoreCase(header)){
			logger.debug("当前请求为Ajax请求");
			return Boolean.TRUE;
		}
		logger.debug("当前请求非Ajax请求");
		return Boolean.FALSE;
	}
	
	/**
	 *  使用	response 输出JSON
	 * @param hresponse
	 * @param resultMap
	 * @throws IOException
	 */
	public static void out(ServletResponse response, Map<String, String> resultMap){
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");//设置编码
			response.setContentType("application/json");//设置返回类型
			out = response.getWriter();
			out.println(JSON.toJSONString(resultMap));//输出
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
