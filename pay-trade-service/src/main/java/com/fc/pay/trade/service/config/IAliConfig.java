package com.fc.pay.trade.service.config;

import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.trade.entity.MerchantAppConfig;

public interface IAliConfig {
	public AliRequest getAliRequest(MerchantAppConfig config);
}
