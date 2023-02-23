package com.fc.pay.geteway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	

	
	@RequestMapping("/")
	public String refund() {
		return "home";
	}
}
