package com.fc.pay.trade.service.trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.PayPaymentOrder;

public interface IPaymentOrder {

	public int add(PayPaymentOrder entity);

	public int delete(Long id);

	public int modify(PayPaymentOrder entity);

	public PayPaymentOrder get(Long id);

	public Page page(Map<String, Object> parm);

	/**
	 * 如果一条结果都没有，返回null
	 */
	public List<PayPaymentOrder> list(Map<String, Object> parm);

	/**
	 * 对账记录查询
	 * @param params
	 * @return
	 */
	public List<PayPaymentOrder> listForBill(Map<String, Object> params);

	public PayPaymentOrder get(Map<String, Object> parm);



	/**
	 * 对账状态改
	 * @param payMap
	 * @return
	 */
	public int modifyBillStatusByMap(Map<String, ArrayList<String>> payMap);

	/**
	 * 对账状态改
	 * @param order_no
	 * @param bill_status
	 * @return 返回影响条数
	 */
	public int modifyBillStatus(String orderNo, String billStatus);

	/**
	 * @param merchant_order_no	按照商户号查
	 */
	public PayPaymentOrder getByMerchantOrderNo(String merchant_order_no);

	public PayPaymentOrder getByOrderNo(String order_no);

	public PayPaymentOrder getByTradeNo(String trade_no);

	/**
	 * 对账修改订单状态为成功
	 */
	public Boolean changStatusSuccess(String order_no, Map<String, Object> data);

	/**
	 * 对账修改订单状态为失败
	 */
	public Boolean changStatusFail(String order_no, Map<String, Object> data);

	/**
	 * 对账修改订单金额
	 */
	public Boolean changAmount(String order_no, BigDecimal amount, Map<String, Object> data);

//	public PayPaymentOrder getByMerchantOrderNo(String merchant_order_no, String channel, String merchant_app_code,
//			String status);

	public Page pageByMap(Map<String, Object> params);
}