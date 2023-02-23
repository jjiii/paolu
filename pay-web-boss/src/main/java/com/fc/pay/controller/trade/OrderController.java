package com.fc.pay.controller.trade;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fc.pay.controller.base.BaseController;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController {
	
	/**
	 * 支付订单界面
	 * @return
	 */
	@RequestMapping("payOrderUI")
	public String payOrderListUI(){
		
		return "order/payorderlist";
	}
	
	/**
	 * 退款订单界面
	 * @return
	 */
	@RequestMapping("payRefundUI")
	public String payRefundListUI(){
		
		return "order/payrefundlist";
	}

}
