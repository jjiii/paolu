package com.fc.pay.trade.service.merchant.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.dao.MerchantAppConfigMapper;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
@Service
public class MerchantAppConfigImp implements IMerchantAppConfig{
	@Autowired
	private MerchantAppConfigMapper merchantAppConfigMapper;
	
	@Override
	public int add(MerchantAppConfig record) {
		return merchantAppConfigMapper.insertSelective(record);
	}

	@Override
	public int delete(Long id) {
		return merchantAppConfigMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int modify(MerchantAppConfig record) {
		return merchantAppConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public MerchantAppConfig get(Long id) {
		return merchantAppConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page page(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MerchantAppConfig> list(Map<String, Object> parm) {
		
		return merchantAppConfigMapper.selectByMap(parm);
	}
	
	public List<MerchantAppConfig> list(String merchant_app_code){
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("merchant_app_code", merchant_app_code);
		return merchantAppConfigMapper.selectByMap(parm);
	}
	
	@Override
	public MerchantAppConfig get(Map<String, Object> parm) {
		List<MerchantAppConfig> list = merchantAppConfigMapper.selectByMap(parm);
		if(list==null || list.size()<1){
			return null;
		}
		return list.get(0);
	}
	
	public MerchantAppConfig getByMerchantAppCode(String merchant_app_code, String channel, String pay_way){
		if(StringUtils.isBlank(merchant_app_code) || StringUtils.isBlank(channel) || StringUtils.isBlank(pay_way)){
			return null;
		}
		
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("merchant_app_code", merchant_app_code);
		parm.put("channel", channel);
		parm.put("pay_way", pay_way);
		return this.get(parm);
	}
	
	public List<MerchantAppConfig>  getByMerchantAppCode(String merchant_app_code, String channel){
		if(StringUtils.isBlank(merchant_app_code)){
			return null;
		}
		
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("merchant_app_code", merchant_app_code);
		if(StringUtils.isNotBlank(channel)){
			parm.put("channel", channel);
		}
		return this.list(parm);
	}
	
	@Override
	public MerchantAppConfig getByChannelAppId(String channel_app_id) {
		if (StringUtils.isNotBlank(channel_app_id)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("channel_app_id", channel_app_id);
			return this.get(parm);
		}
		return null;
	}
	
	@Override
	public MerchantAppConfig getByChannelMerchantId(String channelMerchantId) {
		if (StringUtils.isNotBlank(channelMerchantId)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("channelMerchantId", channelMerchantId);
			return this.get(parm);
		}
		return null;
	}
	
	
}
