package com.fc.pay.trade.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fc.pay.common.core.dao.BaseDao;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.PayRefundOrder;

public interface PayRefundOrderMapper extends BaseDao<PayRefundOrder> {
	List<PayRefundOrder> selectByMap(Map<String, Object> prame);

	List<PayRefundOrder> selectForBill(Map<String, Object> params);
	
	int updateBillStatus(Map<String, Object> param);

	Page pageByMap(Map<String, Object> params);

	int updateBillStatusSingle(@Param("refundNo") String refundNo, @Param("billStatus") String billStatus);
	
}