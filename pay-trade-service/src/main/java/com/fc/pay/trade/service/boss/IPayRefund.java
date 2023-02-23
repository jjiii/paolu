package com.fc.pay.trade.service.boss;

import java.util.Map;



import com.baomidou.mybatisplus.plugins.Page;
import com.fc.pay.trade.entity.PayRefundOrder;

public interface IPayRefund {
	public int add(PayRefundOrder record);

	public int delete(Long id);

	public int modify(PayRefundOrder record);

	public PayRefundOrder get(Long id);

	public Page<PayRefundOrder> page(Map<String, Object> parm, int current, int size);
	
}
