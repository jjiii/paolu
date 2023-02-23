package com.fc.pay.trade.service.getway;

import java.util.Map;

import javax.json.JsonObject;

import com.fc.pay.trade.entity.PayPaymentOrder;

public interface IQuerySuccess {
	public void payQuery_aliPay(PayPaymentOrder order, JsonObject json);
	
	public void payQuery_union(PayPaymentOrder order, Map<String, String> rspData);
}
