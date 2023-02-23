package com.fc.pay.bill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.common.system.mybatis.Page;

public interface BillBizBatchMapper {
	
	void deleteAll();
	
    int deleteByPrimaryKey(Long id);

    int insert(BillBizBatch record);
    
    int insertBatch(List<BillBizBatch> recordList);

    int insertSelective(BillBizBatch record);

    BillBizBatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillBizBatch record);

    int updateByPrimaryKey(BillBizBatch record);

	List<BillBizBatch> selectAllByMap(Map<String, Object> paramMap);
	
	BillBizBatch selectByMap(Map<String, Object> paramMap);

	Page selectPageByMap(Map<String, Object> paramMap);

	BillBizBatch selectByBatchNo(String batchNo);
	
	List<BillBizBatch> selectAllByBillDate(Date billDate);
	
}