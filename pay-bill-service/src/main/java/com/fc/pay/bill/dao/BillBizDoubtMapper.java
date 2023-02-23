package com.fc.pay.bill.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.common.system.mybatis.Page;

public interface BillBizDoubtMapper {
	
	void deleteAll();
	
    int deleteByPrimaryKey(Long id);

    int insert(BillBizDoubt record);

    int insertSelective(BillBizDoubt record);

    BillBizDoubt selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillBizDoubt record);

    int updateByPrimaryKey(BillBizDoubt record);

	List<BillBizDoubt> selectByExpireDate(@Param("expireDate") String expireDate);

	int deleteBatch(List<BillBizDoubt> list);

	List<BillBizDoubt> selectInPeriodByBillType(@Param("billType") String billType);
	
	List<BillBizDoubt> selectAllByMap(Map<String, Object> paramMap);

	Page selectPageByMap(Map<String, Object> paramMap);

	int insertBatch(List<BillBizDoubt> list);
	
	Page selectPageForBoss(Map<String, Object> paramMap);
	
}