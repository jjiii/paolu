package com.fc.pay.trade.dao;

import java.util.List;
import java.util.Map;

import com.fc.pay.common.core.dao.BaseDao;
import com.fc.pay.trade.entity.MerchantAppConfig;

public interface MerchantAppConfigMapper extends BaseDao<MerchantAppConfig> {
	List<MerchantAppConfig> selectByMap(Map<String, Object> prame);
}