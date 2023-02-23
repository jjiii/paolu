package com.fc.pay.trade.service.notify;

import java.util.List;
import java.util.Map;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.NotifyRecord;

public interface INotifyRecord {

	public int add(NotifyRecord record);

	public int delete(Long id);

	public int modify(NotifyRecord record);

	public NotifyRecord get(Long id);

	public Page page(Map<String, Object> parm);

	public List<NotifyRecord> list(Map<String, Object> parm);

	public NotifyRecord get(Map<String, Object> parm);
}
