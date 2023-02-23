package com.fc.pay.bill.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.common.system.mybatis.Page;

public interface BillBizMistakeMapper {
	
	void deleteAll();
	
    int deleteByPrimaryKey(Long id);

    int insert(BillBizMistake record);

    int insertSelective(BillBizMistake record);

    BillBizMistake selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillBizMistake record);

    int updateByPrimaryKey(BillBizMistake record);

	int insertBatch(List<BillBizMistake> mistakeList);
	
	List<BillBizMistake> selectAllByMap(Map<String, Object> paramMap);

	Page selectPageByMap(Map<String, Object> paramMap);
	
	Page selectPageForBoss(Map<String, Object> paramMap);

	int selectCountForPay(@Param("billType") String billType, @Param("payOrderNo") String payOrderNo);

	int selectCountForRefund(@Param("billType") String billType, @Param("refundOrderNo") String refundOrderNo);
	
}