package com.fc.pay.trade.service.trade;

import java.util.List;
import java.util.Map;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.PayTradeRecord;


public interface ITradeRecord  {
	
	public int add(PayTradeRecord record);
	
	public int delete(Long id);
	
	public int modify(PayTradeRecord record);
	
	public PayTradeRecord get(Long id);

	public Page page(Map<String, Object> parm);
	
	/**
	 * 如果一条结果都没有，返回null
	 */
	public List<PayTradeRecord> list(Map<String, Object> parm);
	
	public PayTradeRecord get(Map<String, Object> parm);
	
	
	/**
	 * @param merchant_order_no	按照商户号查
	 */
	public List<PayTradeRecord> getByMerchantOrderNo(String merchant_order_no);
	public PayTradeRecord getByMerchantOrderNo(String merchant_order_no, String channel, String merchant_app_code);
	public List<PayTradeRecord> getByOrderNo(String order_no);
	public List<PayTradeRecord> getByTradeNo(String trade_no);
	
	/**
	 * 原订单，不包括退款
	 */
	public PayTradeRecord getFristByMerchantOrderNo(String merchant_order_no);
	/**
	 * 支付订单，不包括退款
	 */
	public PayTradeRecord getFristByOrderNo(String order_no);
	//退款
	public PayTradeRecord getByRefundNo(String refund_no);
	public PayTradeRecord getByRefundMerchantNo(String refund_merchant_no);
	
}