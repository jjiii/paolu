
package com.fc.pay.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 账户信息
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String list( Model model) {
		return "home";
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String test( Model model, String id) {
		
		System.err.println("收到通知:"+id);
		
		return "OKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK";
		
	}
	
	@RequestMapping("/ttt")
	public String ttt() {
		return "ttt";
	}

}
