package com.fc.pay.trade.service.boss;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.fc.pay.trade.entity.PayPaymentOrder;

public interface IPayOder {
	public int add(PayPaymentOrder entity);

	public int delete(Long id);

	public int modify(PayPaymentOrder entity);

	public PayPaymentOrder get(Long id);

	public Page<PayPaymentOrder> page(Map<String, Object> parm, int current, int size);
}
