package com.fc.pay.bill.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.bill.dao.BillBizBatchMapper;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.service.BillBizBatchService;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账批次数据服务接口实现
 * 
 * @author zhanjq
 *
 */
@Service("billBizBatchService")
public class BillBizBatchServiceImpl implements BillBizBatchService {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizBatchServiceImpl.class);

	@Autowired
	private BillBizBatchMapper batchMapper;
	
	@Transactional
	public int add(BillBizBatch batch) {
		return batchMapper.insertSelective(batch);
	}

	@Transactional
	public int modify(BillBizBatch batch) {
		return batchMapper.updateByPrimaryKeySelective(batch);
	}

	public BillBizBatch get(Long id) {
		return batchMapper.selectByPrimaryKey(id);
	}

	public Page page(Map<String, Object> paramMap) {
		return batchMapper.selectPageByMap(paramMap);
	}

	public List<BillBizBatch> list(Map<String, Object> paramMap) {
		return batchMapper.selectAllByMap(paramMap);
	}

	@Transactional
	public List<BillBizBatch> addBatch(List<BillBizBatch> batchList) {
		for(BillBizBatch batch : batchList){
			batchMapper.insertSelective(batch);
		}
		//batchMapper.insertBatch(batchList);//无法返回记录id，不用此方法
		return batchList;
	}

	public BillBizBatch getByBatchNo(String batchNo) {
		return batchMapper.selectByBatchNo(batchNo);
	}

	public List<BillBizBatch> listByBillDate(Date billDate) {
		return batchMapper.selectAllByBillDate(billDate);
	}

	public BillBizBatch getByMap(Map<String, Object> paramMap) {
		return batchMapper.selectByMap(paramMap);
	}

	@Override
	public void deleteAll() {
		batchMapper.deleteAll();
	}

}
