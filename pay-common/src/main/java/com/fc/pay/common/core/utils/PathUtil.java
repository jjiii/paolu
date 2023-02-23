package com.fc.pay.common.core.utils;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 路径工具类
 * @author zhanjq
 *
 */
public class PathUtil {
	
	private static final Logger log = LoggerFactory.getLogger(PathUtil.class);
	
	public static String readBillBasicPath(){
		log.info("ContextClassLoader.Resource.Path>>>"+Thread.currentThread().getContextClassLoader().getResource("").getPath());		
		//return Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("classes/", "data/bill/");//针对web路径修改
		String billBasicPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("classes/", "data/bill/");//针对web路径修改
		if(SystemUtils.IS_OS_WINDOWS){
			billBasicPath = billBasicPath.substring(1);
		}
		return billBasicPath;
	}
	
	/**
	 * 读取证书存放目录
	 * @return
	 */
	public static String readCertBasicPath(){
		log.info("ContextClassLoader.Resource.Path>>>"+Thread.currentThread().getContextClassLoader().getResource("").getPath());
		//return Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("classes/", "data/cert/");//针对web路径修改
		String certBasicPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("classes/", "data/cert/");//针对web路径修改
		if(SystemUtils.IS_OS_WINDOWS){
			certBasicPath = certBasicPath.substring(1);
		}
		return certBasicPath;
	}
	
	/**
	 * 读取密钥存放目录
	 * @return
	 */
	public static String readKeyBasicPath(){
		log.info("ContextClassLoader.Resource.Path>>>"+Thread.currentThread().getContextClassLoader().getResource("").getPath());
		String keyBasicPath =Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("classes/", "data/key/");//针对web路径修改;
		//去掉/F:/abc/defkey/字符串第一个/
		if(SystemUtils.IS_OS_WINDOWS){
			keyBasicPath = keyBasicPath.substring(1);
		}
		return keyBasicPath;
		
	}
	
	/**
	 * 构建密钥相对路径
	 * @param merchantCode		商户号
	 * @param merchantAppCode	商户应用号
	 * @return
	 */
	public static String buildKeyRelativePath(String merchantCode, String merchantAppCode){
		StringBuffer buffer = new StringBuffer();
		if(!StringUtils.isEmpty(merchantCode)){
			buffer.append(merchantCode+"/");
		}
		if(!StringUtils.isEmpty(merchantAppCode)){
			buffer.append(merchantAppCode+"/");
		}			
		return buffer.toString();
	}
	
	/**
	 * 确保密钥目录存在
	 * @param keyRelativePath
	 * @return
	 */
	public static String makeKeyPathDirExist(String keyRelativePath){
		String fullKeyPath = PathUtil.readKeyBasicPath()+keyRelativePath;
		File dir = new File(fullKeyPath);
		if(!dir.exists()){
			if(!dir.mkdirs()){
				log.error("创建密钥目录["+fullKeyPath+"]失败");
				return null;
			}
		}
		return fullKeyPath;
	}
	
}
