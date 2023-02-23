package com.fc.pay.trade.service.getway;

import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayRefundOrder;

/**
 * 各渠道退款接口
 */
public interface IRefund {
	/**
	 * _10000：成功
	 * _00002:超时
	 * _00008：失败
	 */
	public String refund_alipay(PayRefundOrder refund, AliRequest request);
	
	public String refund_uninon(PayRefundOrder refund, MerchantAppConfig config);
	
	public String refund_weixin(PayRefundOrder refund, WeixinRequest request);
}
