package com.fc.pay.geteway.controller.alipay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.enums.RefundStatusEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.core.utils.IDHelp;
import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.common.vo.bean.RefundOrder;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.service.config.IAliConfig;
import com.fc.pay.trade.service.getway.IRefund;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.IRefundOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.fc.pay.trade.utils.Return;

/**
 * 支付宝退款接口：
 */
@Controller
@RequestMapping(value = "/trade")
public class AliPayRefundController {

	
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
	private IAliConfig iAliConfig;

	@RequestMapping(value = "/refund", params = "channel=alipay")
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
		
		//2、退款订单是否存在
		PayRefundOrder refund = iRefundOrder.getByOrderNoRefundMerchantNo(refundMerchantNo, merchantAppCode);
		
		if(refund == null){
			
			//添加一天退款中的记录
			PayRefundOrder refundOrder = new PayRefundOrder();
			BeanUtil.copyProperties(refundOrder, order);
			refundOrder.setCreateTime(null);
			refundOrder.setId(null);
			refundOrder.setEditor(null);
			refundOrder.setEditTime(null);
//			refundOrder.setFinishTime(null);
			refundOrder.setRefundNo(IDHelp.nextId().toString());
			refundOrder.setRefundMerchantNo(refundMerchantNo);
			refundOrder.setRefundAmount(refundAmount);
			refundOrder.setRefundTime(new Date());
			refundOrder.setRefundReason(refundReason);
			refundOrder.setRefundStatus(RefundStatusEnum.application.name());
			iRefundOrder.add(refundOrder);
			
			//调支付宝
			String code = iRefund.refund_alipay(refundOrder, request);
			
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
		if(!PayChannelEnum.alipay.name().endsWith(refund.getChannel()) || !refund.getMerchantAppCode().equals(merchantAppCode)){
			return new Return(CodeEnum._00004.getName(), CodeEnum._00004.getValue(),null);
		}
		
		//3、退款订单存在，判断状态
		String status = refund.getRefundStatus();
		//不成功的，都再调用一次支付宝
		if(!RefundStatusEnum.success.name().equals(status)){
			//调支付宝接口
			String code = iRefund.refund_alipay(refund, request);

			if(CodeEnum._00002.getName().equals(code)){//请求超时
				return new Return(CodeEnum._00002.getName(), CodeEnum._00002.getValue(),null);
			}
			
			if(CodeEnum._00008.getName().equals(code)){//退款失败
				RefundOrder view = new RefundOrder();
				BeanUtil.copyProperties(view, refund);
				Map result = BeanUtil.bean2Map(view);
				view.setRefundStatus(RefundStatusEnum.faile.name());
				return new Return(CodeEnum._00008.getName(), CodeEnum._00008.getValue(),result);
			}
		}
		//成功
		RefundOrder view = new RefundOrder();
		BeanUtil.copyProperties(view, refund);
		Map result = BeanUtil.bean2Map(view);
		return new Return(result);
	}
	
}
