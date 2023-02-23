package com.fc.pay.geteway.controller.alipay;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.common.vo.bean.PayOrder;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.service.config.IAliConfig;
import com.fc.pay.trade.service.getway.IQuery;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.fc.pay.trade.utils.Return;
/**
 * 支付宝支付查询
 */
@Controller
@RequestMapping(value = "/trade")
public class AliPayTradeQueryController {

	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private ITradeRecord iTradeRecord;
	@Autowired
	private IQuery iQuery;
	@Autowired
	private IMerchantAppConfig iMerchantAppConfig;
	@Autowired
	private IAliConfig iAliConfig;
	
	
	
	/**
	 */
	@RequestMapping(value = "/pay/query", params = "channel=alipay")
	@ResponseBody
	public Return payQuery(Model model,String merchantAppCode,String channel, String orderNo) {
		
		PayPaymentOrder order = iPaymentOrder.getByOrderNo(orderNo);
		if (order == null
				|| !PayChannelEnum.alipay.name().endsWith(order.getChannel())
				|| !order.getMerchantAppCode().equals(merchantAppCode)) {
			return new Return(CodeEnum._00004.getName(), CodeEnum._00004.getValue(),null);
		}
		//商户配置信息
		MerchantAppConfig config = iMerchantAppConfig.getByMerchantAppCode(merchantAppCode, PayChannelEnum.alipay.name(), order.getPayWay());
		AliRequest request = iAliConfig.getAliRequest(config);
		if(request == null){
			return new Return(CodeEnum._00009.getName(), CodeEnum._00009.getValue(),null);
		}
		
		PayOrder view = new PayOrder();
		BeanUtil.copyProperties(view, order);
		
		//1、成功支付
		if(TradeStatusEnum.success.name().equals(order.getStatus())){
			Map result = BeanUtil.bean2Map(view);
			return new Return(CodeEnum._10000.getName(), CodeEnum._10000.getValue(),result);
		}
		
		//2、订单关闭
		if(TradeStatusEnum.close.name().equals(order.getStatus())){
			view.setStatus(TradeStatusEnum.close.name());
			Map result = BeanUtil.bean2Map(view);
			return new Return(CodeEnum._10000.getName(), CodeEnum._10000.getValue(),result);
		}
		
		
		//3、待支付，去第三方查询
		String code  = iQuery.payQuery_aliPay(order, request);
		if(CodeEnum._00002.getName().equals(code)){//超时
			return new Return(CodeEnum._00002.getName(), CodeEnum._00002.getValue(),null);
		}
		if(CodeEnum._00005.getName().equals(code)){//等待支付
			view.setStatus(TradeStatusEnum.pay_wait.name());
			Map result = BeanUtil.bean2Map(view);
			return new Return(CodeEnum._10000.getName(), CodeEnum._10000.getValue(),result);
		}
		
		//成功
		view.setStatus(TradeStatusEnum.success.name());
		Map result = BeanUtil.bean2Map(view);
		return new Return(result);

	}

}
