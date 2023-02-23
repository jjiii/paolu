package com.fc.pay.trade.dao;

import java.util.Map;

import com.fc.pay.common.core.dao.BaseDao;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.Merchant;

public interface MerchantMapper extends BaseDao<Merchant> {
	
	Page pageList(Map<String,Object> params);
	
	String selectMaxCode();
	
	int selectCountCode(String merchantCode);
  
}