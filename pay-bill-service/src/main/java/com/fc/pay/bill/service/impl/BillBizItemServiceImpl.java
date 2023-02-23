package com.fc.pay.bill.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.bill.dao.BillBizItemMapper;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.service.BillBizItemService;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账明细数据服务接口实现
 * 
 * @author zhanjq
 *
 */
@Service("billBizItemService")
public class BillBizItemServiceImpl implements BillBizItemService {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizItemServiceImpl.class);
	
	@Autowired
	private BillBizItemMapper itemMapper;

	@Transactional
	public int add(BillBizItem item) {
		return itemMapper.insertSelective(item);
	}

	@Transactional
	public int addBatch(List<BillBizItem> itemList) {
		if(CollectionUtils.isEmpty(itemList)){
			return 0;
		}
		return itemMapper.insertBatch(itemList);
	}

	@Transactional
	public int modify(BillBizItem item) {
		return itemMapper.updateByPrimaryKeySelective(item);
	}

	public BillBizItem get(Long id) {
		return itemMapper.selectByPrimaryKey(id);
	}

	public List<BillBizItem> list(Map<String, Object> paramMap) {
		return itemMapper.selectAllByMap(paramMap);
	}

	public Page page(Map<String, Object> paramMap){
		return itemMapper.selectPageByMap(paramMap);
	}

	@Transactional
	public int modifyPayAmount(String payOrderNo, BigDecimal payAmount) {
		return itemMapper.updatePayAmount(payOrderNo, payAmount);
	}
	
	@Transactional
	public int modifyRefundAmount(String refundOrderNo, BigDecimal refundAmount) {
		return itemMapper.updateRefundAmount(refundOrderNo, refundAmount);
	}

	@Override
	public void deleteAll() {
		itemMapper.deleteAll();
	}
	
}
