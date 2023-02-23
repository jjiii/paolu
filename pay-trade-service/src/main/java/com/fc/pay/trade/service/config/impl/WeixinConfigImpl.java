package com.fc.pay.trade.service.config.impl;

import org.springframework.stereotype.Service;

import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.service.config.IWeixinConfig;
@Service
public class WeixinConfigImpl implements IWeixinConfig{
	public WeixinRequest getRequest(MerchantAppConfig config){
		if(config == null){
			return null;
		}
		try {
			return new WeixinRequest(config.getChannelAppId(), config.getChannelMerchantId(), config.getPayKey(), config.getCertPath(), config.getCertPwd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
