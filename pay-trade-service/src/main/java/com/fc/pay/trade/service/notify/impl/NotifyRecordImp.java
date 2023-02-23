package com.fc.pay.trade.service.notify.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.dao.NotifyRecordMapper;
import com.fc.pay.trade.entity.NotifyRecord;
import com.fc.pay.trade.service.notify.INotifyRecord;
@Service
public class NotifyRecordImp implements INotifyRecord{
	@Autowired
	private NotifyRecordMapper notifyRecordMapper;
	
	@Override
	public int add(NotifyRecord record) {
		return notifyRecordMapper.insertSelective(record);
	}

	@Override
	public int delete(Long id) {
		return notifyRecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int modify(NotifyRecord record) {
		return notifyRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public NotifyRecord get(Long id) {
		return notifyRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page page(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotifyRecord> list(Map<String, Object> parm) {
		return notifyRecordMapper.selectByMap(parm);
	}

	@Override
	public NotifyRecord get(Map<String, Object> parm) {
		List<NotifyRecord> list = notifyRecordMapper.selectByMap(parm);
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

}
