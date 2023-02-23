package com.fc.pay.geteway.controller.union;

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
import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.common.vo.bean.RefundOrder;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.config.IAliConfig;
import com.fc.pay.trade.service.getway.IRefundQuery;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.IRefundOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.fc.pay.trade.utils.Return;
/**
 * 支付宝退款查询
 */
@Controller
@RequestMapping(value = "/trade")
public class UnionRefundQueryController {

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


	@RequestMapping(value="/refund/query", params="channel=unionpay")
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
		MerchantAppConfig config = iMerchantAppConfig.getByMerchantAppCode(merchantAppCode,PayChannelEnum.unionpay.name(), refund.getPayWay());
		if(config == null){
			return new Return(CodeEnum._00009.getName(), CodeEnum._00009.getValue(), null);
		}
		
		
		//渠道是否为银联，商户id是否一致
		if(!PayChannelEnum.unionpay.name().endsWith(refund.getChannel()) || !refund.getMerchantAppCode().equals(merchantAppCode)){
			return new Return(CodeEnum._00004.getName(), CodeEnum._00004.getValue(),null);
		}
		

		if(RefundStatusEnum.faile.name().equals(refund.getRefundStatus())){
			RefundOrder view = new RefundOrder();
			BeanUtil.copyProperties(view, refund);
			Map result = BeanUtil.bean2Map(view);
			return new Return(result);
		}
		
		
		if(RefundStatusEnum.application.name().equals(refund.getRefundStatus())){//申请中
			
			String code  = iRefundQuery.refundQuery_union(refund, config);
			
			if(CodeEnum._10000.getName().equals(code)){//退款成功
				return new Return();
			}
			if(CodeEnum._00002.getName().equals(code)){//请求超时
				return new Return(CodeEnum._00002.getName(), CodeEnum._00002.getValue(),null);
			}
			if(CodeEnum._00008.getName().equals(code)){//退款失败
				return new Return(CodeEnum._00008.getName(), CodeEnum._00008.getValue(),null);
			}
		}
		
		//成功退款
		RefundOrder view = new RefundOrder();
		BeanUtil.copyProperties(view, refund);
		Map result = BeanUtil.bean2Map(view);
		return new Return(result);
	}

}
