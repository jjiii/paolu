package com.fc.pay.trade.service.trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;

public interface IRefundOrder {
	
	public int add(PayRefundOrder record);

	public int delete(Long id);

	public int modify(PayRefundOrder record);
	
	public int modify(PayRefundOrder record,PayPaymentOrder payPaymentOrder);

	public PayRefundOrder get(Long id);

	public Page page(Map<String, Object> parm);

	/**
	 * 如果一条结果都没有，返回null
	 */
	public List<PayRefundOrder> list(Map<String, Object> parm);
	
	
	public PayRefundOrder get(Map<String, Object> parm);
	
	
	
	
	public PayRefundOrder getByRefundNo(String refund_no);
	public PayRefundOrder getByRefundNo(String refund_no, String merchant_app_code);
	public PayRefundOrder getByRefundMerchantNo(String refund_merchant_no, String merchant_app_code);
	
	public PayRefundOrder getRefundByOrderNo(String order_no);
	/**
	 * @param order_no 原订单号
	 * @param refund_merchant_no 商户退款订单号
	 * @param merchantAppCode  商户appcode
	 */
	public PayRefundOrder getByOrderNoRefundMerchantNo(String refund_merchant_no, String merchant_app_code);
	
	
	
	
	
	
	
	/**
	 * 对账状态改
	 * @param order_no
	 * @param bill_status
	 * @return 返回影响条数
	 */
	public int modifyBillStatus(String orderNo, String billStatus);
	
	/**
	 * 修改对账状态
	 * @param payMap
	 * @return
	 */
	public int modifyBillStatusByMap(Map<String, ArrayList<String>> payMap);
	
	/**
	 * 对账修改订单状态为成功
	 */
	public Boolean changStatusSuccess(String order_no, Map<String,Object> data);
	
	/**
	 * 对账修改订单状态为失败
	 */
	public Boolean changStatusFail(String order_no, Map<String,Object> data);
	/**
	 * 对账修改订单金额
	 */
	public Boolean changAmount(String order_no, BigDecimal amount, Map<String,Object> data);

	/**
	 * 对账记录查询
	 * @param map
	 * @return
	 */
	public List<PayRefundOrder> listForBill(Map<String, Object> map);
	
}