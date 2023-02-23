package com.fc.pay.trade.dao;

import java.util.List;
import java.util.Map;

import com.fc.pay.common.core.dao.BaseDao;
import com.fc.pay.trade.entity.MerchantApp;

public interface MerchantAppMapper extends BaseDao<MerchantApp> {
	List<MerchantApp> selectByMap(Map<String, Object> prame);
	
	String selectMaxCode();
	
	int selectCountCode(String merchantCode);
	
	List<MerchantApp> selectMerchantApp();
}