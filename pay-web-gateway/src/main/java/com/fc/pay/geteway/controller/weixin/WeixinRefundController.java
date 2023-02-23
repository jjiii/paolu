package com.fc.pay.geteway.controller.weixin;

import java.math.BigDecimal;
import java.util.Date;
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
import com.fc.pay.common.core.utils.IDHelp;
import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.common.vo.bean.RefundOrder;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.config.IWeixinConfig;
import com.fc.pay.trade.service.getway.IRefund;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.IRefundOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.fc.pay.trade.utils.Return;

/**
 * 微信退款接口：
 */
@Controller
@RequestMapping(value = "/trade")
public class WeixinRefundController {

	
	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private ITradeRecord iTradeRecord;
	@Autowired
	private IRefundOrder iRefundOrder;
	@Autowired
	private IRefund iRefund;
	
	@Autowired
	private IMerchantAppConfig iMerchantAppConfig;
	@Autowired
	private IWeixinConfig iWeixinConfig;

	@RequestMapping(value = "/refund", params = "channel=weixin")
	@ResponseBody
	public Return refund(String merchantAppCode,
			String refundMerchantNo, String orderNo, BigDecimal refundAmount,
			String refundReason, String channel) {
		
		//金额
		if(refundAmount.compareTo(BigDecimal.ZERO) == 1){
			refundAmount = refundAmount.setScale(2,   BigDecimal.ROUND_DOWN);
		}else{
			Return obj = new Return(CodeEnum._00003.getName(),CodeEnum._00003.getValue()+":refundAmount",null);
			return obj;
		}
		
		//1、原订单是否存在
		PayPaymentOrder order = iPaymentOrder.getByOrderNo(orderNo);
		if (order == null
				|| !PayChannelEnum.weixin.name().endsWith(order.getChannel())
				|| !order.getMerchantAppCode().equals(merchantAppCode)) {
			return new Return(CodeEnum._00004.getName(), CodeEnum._00004.getValue(),null);
		}
		
		//商户配置信息
		MerchantAppConfig config = iMerchantAppConfig.getByMerchantAppCode(merchantAppCode, PayChannelEnum.weixin.name(), order.getPayWay());
		WeixinRequest request = iWeixinConfig.getRequest(config);
		if(request == null){
			return new Return(CodeEnum._00009.getName(), CodeEnum._00009.getValue(),null);
		}
		
		//2、退款订单是否存在
		PayRefundOrder refund = iRefundOrder.getByOrderNoRefundMerchantNo(refundMerchantNo, merchantAppCode);
		if(refund == null){
			
			PayRefundOrder refundOrder = new PayRefundOrder();
			BeanUtil.copyProperties(refundOrder, order);
			refundOrder.setId(null);
			refundOrder.setEditor(null);
			refundOrder.setEditTime(null);
//			refundOrder.setFinishTime(null);
			refundOrder.setRefundNo(IDHelp.nextId().toString());
			refundOrder.setRefundMerchantNo(refundMerchantNo);
			refundOrder.setRefundAmount(refundAmount);
			refundOrder.setRefundTime(new Date());
			refundOrder.setRefundReason(refundReason);
			iRefundOrder.add(refundOrder);
			
			//调微信退款接口
			String code = iRefund.refund_weixin(refundOrder, request);

			if(CodeEnum._00002.getName().equals(code)){//请求超时
				return new Return(CodeEnum._00002.getName(), CodeEnum._00002.getValue(),null);
			}
			
			if(CodeEnum._10000.getName().equals(code)){//退款成功
				RefundOrder view = new RefundOrder();
				BeanUtil.copyProperties(view, refundOrder);
				Map result = BeanUtil.bean2Map(view);
				return new Return(result);
			}
			
			return new Return(CodeEnum._00008.getName(), CodeEnum._00008.getValue(),null);
		}
		
		//退款订单不能重复，意思是一个退款订单只能退一个支付交易
		if(!refund.getOrderNo().equals(orderNo)){
			return new Return(CodeEnum._00014.getName(), CodeEnum._00014.getValue(),null);
		}
		
		//不能修改金额
		if(refund.getRefundAmount().compareTo(refundAmount) != 0){
			Return obj = new Return(CodeEnum._00003.getName(),CodeEnum._00003.getValue()+"相同退款单号退款金额不能修改:refundAmount="+refund.getRefundAmount(),null);
			return obj;
		}
				
		//渠道是否为支付宝，商户id是否一致
		if(!PayChannelEnum.weixin.name().endsWith(refund.getChannel()) || !refund.getMerchantAppCode().equals(merchantAppCode)){
			return new Return(CodeEnum._00004.getName(), CodeEnum._00004.getValue(),null);
		}
		
		//3、退款订单存在，判断状态
		String status = refund.getRefundStatus();
		if(!RefundStatusEnum.success.name().equals(status)){
			//调微信接口
			String code = iRefund.refund_weixin(refund, request);

			if(CodeEnum._00002.getName().equals(code)){//请求超时
				return new Return(CodeEnum._00002.getName(), CodeEnum._00002.getValue(),null);
			}
			
			if(CodeEnum._00008.getName().equals(code)){//退款失败
				return new Return(CodeEnum._00008.getName(), CodeEnum._00008.getValue(),null);
			}
		}
		
		if(RefundStatusEnum.faile.name().equals(status)){//退款失败
			return new Return(CodeEnum._00008.getName(), CodeEnum._00008.getValue(),null);
		}
		
		//成功
		RefundOrder view = new RefundOrder();
		BeanUtil.copyProperties(view, refund);
		Map result = BeanUtil.bean2Map(view);
		return new Return(result);
	}

}
