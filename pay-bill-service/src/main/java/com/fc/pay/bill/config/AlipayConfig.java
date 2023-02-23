package com.fc.pay.bill.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * 支付宝配置
 * @author zhanjq
 *
 */
public class AlipayConfig {
	
	private static final Logger log = LoggerFactory.getLogger(AlipayConfig.class);
	
	/** 版本号 */
	public static final String VERSION = "alipay.version";
	
	/** 网关地址 */
	public static final String GATEWAY = "alipay.gateway";
	
	/**
	 * 获取支付宝配置单例
	 * 
	 * @return
	 */
	public static AlipayConfig getConfig() {
		if (null == config) {
			config = new AlipayConfig();
		}
		return config;
	}
	
	/**
	 * 私有构造方法
	 */
	private AlipayConfig() {
		super();
	}
	
	/** 配置对象. */
	private static AlipayConfig config;
	
	/**
	 * 公共属性
	 */	
	private static final Properties publicProperties = new Properties();
	
	/**
	 * 初始化公共属性
	 */
	static {
		try {
			publicProperties.load(AlipayConfig.class.getClassLoader().getResourceAsStream("properties/alipay_config.properties"));
		} catch (IOException e) {
			log.error("读取支付宝接口公共属性错误["+e.getMessage()+"]");
		}
	}	
		
	public String getVersion() {
		return handleValue(publicProperties.getProperty(VERSION));
	}

	public String getGateway() {
		return handleValue(publicProperties.getProperty(GATEWAY));
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
