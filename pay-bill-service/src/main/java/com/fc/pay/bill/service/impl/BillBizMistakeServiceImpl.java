package com.fc.pay.bill.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fc.pay.bill.dao.BillBizMistakeMapper;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.enums.BillTypeEnum;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账差错数据服务接口实现
 * 
 * @author zhanjq
 *
 */
@Service("billBizMistakeService")
public class BillBizMistakeServiceImpl implements BillBizMistakeService {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizMistakeServiceImpl.class);
	
	@Autowired
	private BillBizMistakeMapper mistakeMapper;
	
	@Transactional
	public int add(BillBizMistake mistake) {
		return mistakeMapper.insertSelective(mistake);
	}

	@Transactional
	public int addBatch(List<BillBizMistake> mistakeList) {
		if(CollectionUtils.isEmpty(mistakeList)){
			return 0;
		}		
		return mistakeMapper.insertBatch(mistakeList);
	}

	@Transactional
	public int modify(BillBizMistake mistake) {
		return mistakeMapper.updateByPrimaryKeySelective(mistake);
	}

	public BillBizMistake get(Long id) {
		return mistakeMapper.selectByPrimaryKey(id);
	}
	
	public List<BillBizMistake> list(Map<String, Object> paramMap) {
		return mistakeMapper.selectAllByMap(paramMap);
	}

	public Page page(Map<String, Object> paramMap){
		return mistakeMapper.selectPageByMap(paramMap);
	}

	@Override
	public Page selectPageList(Map<String, Object> paramMap, Integer current,Integer pagesize) {
		if(paramMap.get("orderNoKey")!=null){
			paramMap.put(paramMap.get("orderNoKey").toString(), paramMap.get("orderNo"));
		}		
		Page page = new Page();
		page.setCurrNum(current);
		page.setPageSize(pagesize);
		paramMap.put("page", page);
		return mistakeMapper.selectPageForBoss(paramMap);
	}

	@Override
	public boolean hasMoreMistakeToHandle(String type, String orderNo) {
		int count = 0;
		if(BillTypeEnum.pay.name().equals(type)){
			count = mistakeMapper.selectCountForPay(type, orderNo);
		}else if(BillTypeEnum.refund.name().equals(type)){
			count = mistakeMapper.selectCountForRefund(type, orderNo);
		}
		log.debug("hasMoreMistakeToHandle.count>>>"+count);
		return count > 1 ? true : false;
	}

	@Override
	public void deleteAll() {
		mistakeMapper.deleteAll();
	}

}
