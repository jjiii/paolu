package com.fc.notify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class HomeController {
	
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@RequestMapping("/")
	@ResponseBody
	public String list(Model model) throws Exception {
		return "home";
	}
	
	
}
