package com.fc.pay.trade.dao;

import java.util.List;
import java.util.Map;

import com.fc.pay.common.core.dao.BaseDao;
import com.fc.pay.trade.entity.NotifyRecord;

public interface NotifyRecordMapper extends BaseDao<NotifyRecord> {
	List<NotifyRecord> selectByMap(Map<String, Object> prame);
}