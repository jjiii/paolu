package com.fc.pay.trade.service.getway;

import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;

/**
 * 查询各渠道的接口
 */
public interface IQuery {
	/**
	 * 支付宝渠道，查询
	 * @return 返回码：
	 * 		_00002:超时
	 * 		_00005:等待支付
	 * 		_10000:成功
	 */
	public String payQuery_aliPay(PayPaymentOrder order, AliRequest request);
	
	/**
	 * 银联渠道查询
	 * @param order
	 * @param config
	 * @return
	 */
	public String payQuery_union(PayPaymentOrder order, MerchantAppConfig config);
	
	/**
	 * 微信渠道查询
	 * @param order
	 * @param config
	 * @return
	 */
	public String payQuery_weixin(PayPaymentOrder order, WeixinRequest request);
	
	

	
}
