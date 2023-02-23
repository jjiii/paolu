package com.fc.pay.trade.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fc.pay.common.core.dao.BaseDao;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.PayPaymentOrder;

public interface PayPaymentOrderMapper extends BaseDao<PayPaymentOrder> {

	List<PayPaymentOrder> selectByMap(Map<String, Object> prame);

	List<PayPaymentOrder> selectForBill(Map<String, Object> params);

	int updateBillStatus(Map<String, Object> prame);
	
	int updateBillStatusSingle(@Param("orderNo") String orderNo, @Param("billStatus") String billStatus);

	Page pageByMap(Map<String, Object> params);

}