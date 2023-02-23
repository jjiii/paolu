package com.fc.pay.trade.service.getway;

import com.fc.pay.trade.entity.PayPaymentOrder;

public interface IReceive {
	public void alipayReceive(PayPaymentOrder order, String trade_no, String buyer_id, String content);
	
	public void unionpayReceive(PayPaymentOrder order, String trade_no, String buyer_id, String content);
	
	public void weixinpayReceive(PayPaymentOrder order, String trade_no, String buyer_id, String content);
}
