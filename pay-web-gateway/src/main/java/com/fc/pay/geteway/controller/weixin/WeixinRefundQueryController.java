package com.fc.pay.geteway.controller.weixin;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.enums.RefundStatusEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.common.vo.bean.RefundOrder;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.config.IWeixinConfig;
import com.fc.pay.trade.service.getway.IRefundQuery;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.IRefundOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.fc.pay.trade.utils.Return;

@Controller
@RequestMapping(value = "/trade")
public class WeixinRefundQueryController {
	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private ITradeRecord iTradeRecord;
	@Autowired
	private IRefundOrder iRefundOrder;
	@Autowired
	private IRefundQuery iRefundQuery;
	
	@Autowired
	private IMerchantAppConfig iMerchantAppConfig;
	@Autowired
	private IWeixinConfig iWeixinConfig;
	
	@RequestMapping(value="/refund/query", params="channel=weixin")
	@ResponseBody
	public Return refundQuery(String merchantAppCode, String refundMerchantNo,String refundNo) {
		
		// 退款订单是否存在
		PayRefundOrder refund = null;
		if(StringUtils.isNotBlank(refundNo)){
			refund = iRefundOrder.getByRefundNo(refundNo, merchantAppCode);
		}else{
			refund = iRefundOrder.getByRefundMerchantNo(refundMerchantNo, merchantAppCode);
		};
		
		if(refund == null){
			return new Return(CodeEnum._00004.getName(), CodeEnum._00004.getValue(),null);
		}
		
		//商户配置信息
		MerchantAppConfig config = iMerchantAppConfig.getByMerchantAppCode(merchantAppCode, PayChannelEnum.weixin.name(), refund.getPayWay());
		WeixinRequest request = iWeixinConfig.getRequest(config);
		if(request == null){
			return new Return(CodeEnum._00009.getName(), CodeEnum._00009.getValue(),null);
		}
		
		
		
		//渠道是否为支付宝，商户id是否一致
		if(!PayChannelEnum.weixin.name().endsWith(refund.getChannel()) || !refund.getMerchantAppCode().equals(merchantAppCode)){
			return new Return(CodeEnum._00004.getName(), CodeEnum._00004.getValue(),null);
		}
		
		
		if(!RefundStatusEnum.success.name().equals(refund.getRefundStatus())){//申请中
			
			String code  = iRefundQuery.refundQuery_weixin(refund, request);
			
			if(CodeEnum._00002.getName().equals(code)){//请求超时
				return new Return(CodeEnum._00002.getName(), CodeEnum._00002.getValue(),null);
			}
			if(CodeEnum._00008.getName().equals(code)){//退款失败
				return new Return(CodeEnum._00008.getName(), CodeEnum._00008.getValue(),null);
			}
		}
		
		
		//成功
		RefundOrder view = new RefundOrder();
		BeanUtil.copyProperties(view, refund);
		Map result = BeanUtil.bean2Map(view);
		return new Return(result);
	}
}
