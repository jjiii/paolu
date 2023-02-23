package com.fc.pay.common.pay.union;

import java.io.InputStream;
import java.util.Properties;

public class UnionConfig {
	
	public static Properties properties = new Properties();
	static {
		try {
			InputStream stream =  UnionConfig.class.getClassLoader().getResourceAsStream("properties/pay/acp_sdk.properties");
			properties.load(stream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String acpsdk_frontTransUrl = properties.getProperty(properties.getProperty("acpsdk.frontTransUrl"));
	public String acpsdk_backTransUrl = properties.getProperty("acpsdk2backTransUrl");
	public String acpsdk_singleQueryUrl = properties.getProperty("acpsdk.singleQueryUrl");
	public String acpsdk_batchTransUrl = properties.getProperty("acpsdk.batchTransUrl");
	public String acpsdk_fileTransUrl = properties.getProperty("acpsdk.fileTransUrl");
	public String acpsdk_appTransUrl = properties.getProperty("acpsdk.appTransUrl");
	public String acpsdk_cardTransUrl = properties.getProperty("acpsdk.cardTransUrl");

	/**
	 * 以下缴费产品使用，其余产品用不到
	 */
	public String acpsdk_jfFrontTransUrl = properties.getProperty("acpsdk.jfFrontTransUrl");
	public String acpsdk_jfBackTransUrl = properties.getProperty("acpsdk.jfBackTransUrl");
	public String acpsdk_jfSingleQueryUrl = properties.getProperty("acpsdk.jfSingleQueryUrl");
	public String acpsdk_jfCardTransUrl = properties.getProperty("acpsdk.jfCardTransUrl");
	public String acpsdk_jfAppTransUrl = properties.getProperty("acpsdk.jfAppTransUrl");

//	public String acpsdk_signCert_path = properties.getProperty("acpsdk.signCert.path");//数据库获取

//	public String acpsdk_signCert_pwd = properties.getProperty("acpsdk.signCert.pwd");//数据库获取
	public String acpsdk_signCert_type = properties.getProperty("acpsdk.signCert.type");
//	public String acpsdk_validateCert_dir = properties.getProperty("acpsdk.validateCert.dir");//数据库获取

//	public String acpsdk_encryptCert_path = properties.getProperty("acpsdk.encryptCert.path");数据库获取

	public String acpsdk_singleMode = properties.getProperty("acpsdk.singleMode");
	
	
	public static String version = "5.0.0";			//版本号，全渠道默认值
	public static String encoding = "UTF-8";		//字符集编码，可以使用UTF-8,GBK两种方式
	public static String signMethod = "01";			//签名方法，只支持 01：RSA方式证书加密
	
	public static String accessType = "0";           		//接入类型，商户接入固定填0，不需修改
	
	
	
	
	public static String notifyUrl= properties.getProperty("notifyUrl");
}
