package com.fc.pay.trade.service.config;

import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.trade.entity.MerchantAppConfig;

public interface IWeixinConfig {
	public WeixinRequest getRequest(MerchantAppConfig config);
}
