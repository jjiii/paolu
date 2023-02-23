package com.fc.pay.trade.service.getway;

import com.fc.pay.trade.entity.PayPaymentOrder;

public interface IClose {
	public String close_aliPay(PayPaymentOrder order);
}
