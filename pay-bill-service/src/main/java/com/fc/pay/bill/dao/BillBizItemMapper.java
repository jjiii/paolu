package com.fc.pay.bill.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.common.system.mybatis.Page;

public interface BillBizItemMapper {
	
	void deleteAll();
	
    int deleteByPrimaryKey(Long id);

    int insert(BillBizItem record);

    int insertSelective(BillBizItem record);

    BillBizItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillBizItem record);

    int updateByPrimaryKey(BillBizItem record);

	int insertBatch(List<BillBizItem> itemList);
	
	List<BillBizItem> selectAllByMap(Map<String, Object> paramMap);

	Page selectPageByMap(Map<String, Object> paramMap);

	int updatePayAmount(@Param("payOrderNo") String payOrderNo, @Param("payAmount")  BigDecimal payAmount);

	int updateRefundAmount(@Param("refundOrderNo") String refundOrderNo, @Param("refundAmount") BigDecimal refundAmount);
	
}