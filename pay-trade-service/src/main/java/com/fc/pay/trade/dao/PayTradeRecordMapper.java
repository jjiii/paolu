package com.fc.pay.trade.dao;

import java.util.List;
import java.util.Map;

import com.fc.pay.common.core.dao.BaseDao;
import com.fc.pay.trade.entity.PayTradeRecord;

public interface PayTradeRecordMapper extends BaseDao<PayTradeRecord> {
	
	List<PayTradeRecord> selectByMap(Map<String, Object> prame);
	
}