package com.fc.pay.trade.service.config.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.service.config.IAliConfig;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;

@Service
public class AliConfigImpl implements IAliConfig {
	@Autowired
	private IMerchantAppConfig iMerchantAppConfig;

	@Override
	public AliRequest getAliRequest(MerchantAppConfig config) {
		
		if(config == null){
			return null;
		}

		try {
			AliRequest aliRequest = new AliRequest(config.getChannelAppId(), config.getPriKey(), config.getPubKey());
			return aliRequest;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
