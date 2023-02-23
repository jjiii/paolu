package com.fc.pay.bill.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.bill.dao.BillBizFileNotifyMapper;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.service.BillBizFileNotifyService;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账文件通知数据服务接口实现
 * 
 * @author zhanjq
 *
 */
@Service("billBizFileNotifyService")
public class BillBizFileNotifySrviceImpl implements BillBizFileNotifyService {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizFileNotifySrviceImpl.class);
	
	@Autowired
	private BillBizFileNotifyMapper fileNotifyMapper;
	

	@Transactional
	public int add(BillBizFileNotify fileNotify) {
		return fileNotifyMapper.insertSelective(fileNotify);
	}

	@Transactional
	public int modify(BillBizFileNotify fileNotify) {
		return fileNotifyMapper.updateByPrimaryKeySelective(fileNotify);
	}

	public BillBizFileNotify get(Long id) {
		return fileNotifyMapper.selectByPrimaryKey(id);
	}

	public List<BillBizFileNotify> list(Map<String, Object> paramMap) {
		return fileNotifyMapper.selectAllByMap(paramMap);
	}

	public Page page(Map<String, Object> paramMap) {
		return fileNotifyMapper.selectPageByMap(paramMap);
	}

	@Transactional
	public int modifyFileField(Long id, String filePath, String fileStatus,
			String fileRemark) {
		return fileNotifyMapper.updateFileField(id, filePath, fileStatus, fileRemark);
	}

	@Transactional
	public int modifyNotifyField(Long id, String notifyUrl,
			String notifyStatus, String notifyRemark) {
		return fileNotifyMapper.updateNotifyField(id, notifyUrl, notifyStatus, notifyRemark);
	}

	@Transactional
	public List<BillBizFileNotify> addBatch(List<BillBizFileNotify> list) {
		for(BillBizFileNotify fn : list){
			fileNotifyMapper.insertSelective(fn);
		}
		//fileNotifyMapper.insertBatch(list);
		return list;
	}

	public List<BillBizFileNotify> listByBillDate(Date billDate) {
		return fileNotifyMapper.selectAllByBillDate(billDate);
	}

	@Override
	public BillBizFileNotify getBy(String merchantAppCode, Date billDate) {
		return fileNotifyMapper.selectByMerchantAppCodeBillDate(merchantAppCode, billDate);
	}

	@Override
	public void deleteAll() {
		fileNotifyMapper.deleteAll();
	}

	
}
