package com.fc.pay.trade.service.merchant;

import java.util.List;
import java.util.Map;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.MerchantAppConfig;

public interface IMerchantAppConfig {
	
	public int add(MerchantAppConfig record);

	public int delete(Long id);

	public int modify(MerchantAppConfig record);

	public MerchantAppConfig get(Long id);

	public Page page(Map<String, Object> parm);

	/**
	 * 如果一条结果都没有，返回null
	 */
	public List<MerchantAppConfig> list(Map<String, Object> parm);
	
	public List<MerchantAppConfig> list(String merchant_app_code);
	
	public MerchantAppConfig get(Map<String, Object> parm);
	
	public MerchantAppConfig getByMerchantAppCode(String merchant_app_code, String channel, String pay_way);
	
	public List<MerchantAppConfig>  getByMerchantAppCode(String merchant_app_code, String channel);
	
	public MerchantAppConfig getByChannelAppId(String channel_app_id);
	
	public MerchantAppConfig getByChannelMerchantId(String channelMerchantId);
}