package com.fc.pay.trade.service.getway;

import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayRefundOrder;

public interface IRefundQuery {
	/**
	 * _10000:退款成功
	 * _00002：超时
	 * _00008：失败
	 * 查询成功则修改订单状态为true
	 * 否则返回退款失败
	 */
	public String refundQuery_alipy(PayRefundOrder refund, AliRequest request);
	
	public String refundQuery_union(PayRefundOrder refund, MerchantAppConfig config);
	
	public String refundQuery_weixin(PayRefundOrder refund, WeixinRequest request);
	
}
