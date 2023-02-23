package com.fc.pay.trade.service.merchant.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.dao.MerchantAppMapper;
import com.fc.pay.trade.entity.Merchant;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.service.merchant.IMerchantApp;
import com.fc.pay.trade.utils.GernerateCodeUtil;
@Service
public class MerchantAppImp implements IMerchantApp{
	@Autowired
	private MerchantAppMapper merchantAppMapper;
	
	
	@Override
	public int add(MerchantApp entity) {
		return merchantAppMapper.insertSelective(entity);
	}

	@Override
	public int delete(Long id) {
		return merchantAppMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int modify(MerchantApp entity) {
		return merchantAppMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public MerchantApp get(Long id) {
		return merchantAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page page(Map<String, Object> parm) {
		return null;
	}

	@Override
	public List<MerchantApp> list(Map<String, Object> parm) {
		
		return merchantAppMapper.selectByMap(parm);
	}

	@Override
	public MerchantApp get(Map<String, Object> parm) {
		List<MerchantApp> list = merchantAppMapper.selectByMap(parm);
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}
	
	public MerchantApp getByMerchantAppCode(String merchantAppCode){
		if (StringUtils.isNotBlank(merchantAppCode)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("merchant_app_code", merchantAppCode);
			return this.get(parm);
		}
		return null;
	}
	
	@Override
	public String generateCode() {
		String newCode = "";
		String maxCode = merchantAppMapper.selectMaxCode();
		newCode = GernerateCodeUtil.generateCode(maxCode, "MA", 5);
		return newCode;
	}
	
	@Override
	public boolean codeIsExists(String merchantCode) {
		
		return merchantAppMapper.selectCountCode(merchantCode)>0;
	}

	@Override
	public List<MerchantApp> selectMerchantApp() {
		
		return merchantAppMapper.selectMerchantApp();
	}

}
