package com.fc.pay.shop.controller;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alipay")
public class ZfbController {

	@RequestMapping(value = "/pay")
	public String list(Model model) {
		// 订单编号
		model.addAttribute("orderNo", System.currentTimeMillis());
		// 订单日期
		model.addAttribute("orderDate",  new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis()));
		//订单时间
		model.addAttribute("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()));
		//秘钥
		model.addAttribute("payKey","64efebf7eb5b439d8fa213de9028392e");
		return "/alipay/pay";
	}
	
	@RequestMapping(value = "/pay-query")
	public String query(Model model) {
		return "/alipay/pay-query";
	}
	
	@RequestMapping(value = "/refund")
	public String refund(Model model) {
		return "/alipay/refund";
	}
	
	@RequestMapping(value = "/refund-query")
	public String refundQuery(Model model) {
		return "/alipay/refund-query";
	}
	
}
