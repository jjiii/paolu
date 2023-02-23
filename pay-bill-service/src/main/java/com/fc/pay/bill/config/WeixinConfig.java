package com.fc.pay.bill.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.utils.PathUtil;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.unionpay.acp.sdk.SDKUtil;
import com.tencent.WXPay;

/**
 * 微信配置
 * @author zhanjq
 *
 */
public class WeixinConfig {
	
	private static final Logger log = LoggerFactory.getLogger(WeixinConfig.class);
	
	/** 版本号 */
	public static final String VERSION = "weixin.pay.version";
	
	/** 统一下单 */
	public static final String UNIFIED_ORDER = "weixin.pay.unifiedorder";
	
	/** 查询订单 */
	public static final String ORDER_QUERY = "weixin.pay.orderquery";
	
	/** 关闭订单 */
	public static final String CLOSE_ORDER = "weixin.pay.closeorder";
	
	/** 申请退款 */
	public static final String REFUND = "weixin.pay.refund";
	
	/** 查询退款 */
	public static final String REFUND_QUERY = "weixin.pay.refundquery";
	
	/** 下载对账单 */
	public static final String DOWNLOAD_BILL = "weixin.pay.downloadbill";
	
	/** 交易保障 */
	public static final String REPORT = "weixin.pay.report";
	
	/** 支付结果通用通知 */
	public static final String NOTIFY_URL = "weixin.pay.notify_url";
	
	
	
	/** 签名算法需要用到的秘钥 */
	public static final String KEY = "weixin.pay.key";
	
	/** 公众账号ID */
	public static final String APP_ID = "weixin.pay.appId";
	
	/** 商户ID */
	public static final String MCH_ID = "weixin.pay.mchId";
	
	/** 子商户ID，受理模式必填 */
	public static final String SUB_MCH_ID = "weixin.pay.subMchId";
	
	/** HTTP证书在服务器中的路径，用来加载证书用 */
	public static final String CERT_LOCAL_PATH = "weixin.pay.certLocalPath";
	
	/** HTTP证书的密码，默认等于MCHID */
	public static final String CERT_PASSWORD = "weixin.pay.certPassword";
	
	
	/**
	 * 获取微信配置单例
	 * 
	 * @return
	 */
	public static WeixinConfig getConfig() {
		if (null == config) {
			config = new WeixinConfig();
		}
		return config;
	}
	
	/**
	 * 私有构造方法
	 */
	private WeixinConfig() {
		super();
	}
	
	/** 配置对象. */
	private static WeixinConfig config;
	
	/**
	 * 公共属性
	 */	
	private static final Properties publicProperties = new Properties();
	
	/**
	 * 初始化公共属性
	 */
	static {
		try {
			publicProperties.load(WeixinConfig.class.getClassLoader().getResourceAsStream("properties/weixin_config.properties"));
		} catch (IOException e) {
			log.error("读取微信接口公共属性错误["+e.getMessage()+"]");
		}
	}
	
	/**
	 * 初始化交易配置
	 * @param channelMerchantId
	 */
	public void initSDKConfig(MerchantAppConfig config) {
		WXPay.initSDKConfiguration(config.getPubKey(), config.getChannelAppId(), 
				config.getChannelMerchantId(), config.getSubMchId(), 
				PathUtil.readCertBasicPath()+config.getCertPath(), config.getCertPwd());		
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
