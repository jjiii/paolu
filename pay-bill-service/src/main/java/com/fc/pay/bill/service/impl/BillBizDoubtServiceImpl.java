package com.fc.pay.bill.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.bill.dao.BillBizDoubtMapper;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.service.BillBizDoubtService;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账存疑数据服务接口实现
 * 
 * @author zhanjq
 *
 */
@Service("billBizDoubtService")
public class BillBizDoubtServiceImpl implements BillBizDoubtService {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizDoubtServiceImpl.class);
	
	@Autowired
	private BillBizDoubtMapper doubtMapper;

	@Transactional
	public int add(BillBizDoubt doubt) {
		return doubtMapper.insertSelective(doubt);
	}

	@Transactional
	public int addBatch(List<BillBizDoubt> list) {
		if(CollectionUtils.isEmpty(list)){
			return 0;
		}
		return doubtMapper.insertBatch(list);
	}

	@Transactional
	public int deleteBatch(List<BillBizDoubt> toDeleteList) {
		if(CollectionUtils.isEmpty(toDeleteList)){
			return 0;
		}
		return doubtMapper.deleteBatch(toDeleteList);
	}

	@Transactional
	public int modify(BillBizDoubt doubt) {
		return doubtMapper.updateByPrimaryKeySelective(doubt);
	}

	public BillBizDoubt get(Long id) {
		return doubtMapper.selectByPrimaryKey(id);
	}

	public List<BillBizDoubt> list(Map<String, Object> paramMap) {
		return doubtMapper.selectAllByMap(paramMap);
	}

	public Page page(Map<String, Object> paramMap){
		return doubtMapper.selectPageByMap(paramMap);
	}

	public List<BillBizDoubt> listByExpireDate(String expireDate) {
		return doubtMapper.selectByExpireDate(expireDate);
	}

	public List<BillBizDoubt> listInPeriodByBillType(String billType) {
		return doubtMapper.selectInPeriodByBillType(billType);
	}

	@Override
	public Page selectPageList(Map<String, Object> paramMap,Integer current,Integer pagesize) {
		if(paramMap.get("orderNoKey")!=null){
			paramMap.put(paramMap.get("orderNoKey").toString(), paramMap.get("orderNo"));
		}
		Page page = new Page();
		page.setCurrNum(current);
		page.setPageSize(pagesize);
		paramMap.put("page", page);
		return doubtMapper.selectPageForBoss(paramMap);
	}

	@Override
	public void deleteAll() {
		doubtMapper.deleteAll();
	}

	

}
