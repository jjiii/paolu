package com.fc.pay.bill.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections.CollectionUtils;

import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.utils.PathUtil;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * 银联支付配置
 * 
 * @author zhanjq
 *
 */
public class UnionpayConfig {
	
	private static final Logger log = LoggerFactory.getLogger(UnionpayConfig.class);
	
	/** 银联接口版本号常量  */
	public static final String SDK_VERSION = "acpsdk.version";
	
	/**
	 * 私有构造方法
	 */
	private UnionpayConfig() {
		super();
	}
	
	/** 配置对象. */
	private static UnionpayConfig config;
	
	/**
	 * 获取银联配置单例
	 * 
	 * @return
	 */
	public static UnionpayConfig getConfig() {
		if (null == config) {
			config = new UnionpayConfig();
		}
		return config;
	}
	
	/**
	 * 公共属性
	 */	
	private static final Properties publicProperties = new Properties();
	
	/**
	 * 初始化公共属性
	 * 来自配置文件
	 */
	static {
		try {
			publicProperties.load(UnionpayConfig.class.getClassLoader().getResourceAsStream("properties/unionpay_config.properties"));
		} catch (IOException e) {
			log.error("读取银联接口公共属性错误["+e.getMessage()+"]");
		}
	}
	
	/**
	 * 初始化交易配置
	 * @param channelMerchantId
	 */
	public void initSDKConfig(MerchantAppConfig config) {
		Properties properties = new Properties();
		//公共属性
		properties.putAll(publicProperties);
		//私有属性
		properties.put(SDKConfig.SDK_SIGNCERT_PATH, PathUtil.readCertBasicPath()+config.getCertPath());
		properties.put(SDKConfig.SDK_SIGNCERT_PWD, config.getCertPwd());
		properties.put(SDKConfig.SDK_SIGNCERT_TYPE, "PKCS12");
		properties.put(SDKConfig.SDK_VALIDATECERT_DIR, PathUtil.readCertBasicPath()+config.getCertValidate());
		properties.put(SDKConfig.SDK_ENCRYPTCERT_PATH, PathUtil.readCertBasicPath()+config.getCertEncPath());
		properties.put(SDKConfig.SDK_SINGLEMODE, "true");	
		SDKConfig.getConfig().loadProperties(properties);
	}
	
	/**
	 * 获取接口版本号
	 * @return
	 */
	public String getVersion() {
		return handleValue(publicProperties.getProperty(SDK_VERSION));
	}

	/**
	 * 过滤属性值
	 * @return
	 */
	private static String handleValue(String value){
		if (SDKUtil.isEmpty(value)) {
			return null;
		}
		return value.trim();
	}
	
}