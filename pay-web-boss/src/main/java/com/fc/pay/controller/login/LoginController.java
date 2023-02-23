package com.fc.pay.controller.login;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fc.pay.boss.service.IBossMenuService;
import com.fc.pay.controller.base.BaseController;

@Controller
public class LoginController extends BaseController {
	
	private static final Log log = LogFactory.getLog(LoginController.class);	
	
	@Autowired
	private IBossMenuService bossMenuService;
	
	@RequestMapping("login")
	public String login(HttpServletRequest request,String username,String password,Model model){
		Subject subect = SecurityUtils.getSubject();
		
		if(subect.isAuthenticated()){
			return "redirect:/";
		}
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			log.warn("用户不存在");
			error = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			log.warn("用户名或密码不正确");
			error = "用户名/密码错误";
		} else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
			log.warn("用户已销定");
			error = "该账号已锁定，联系管理员";
		} else if (exceptionClassName != null) {
			error = "错误提示：" + exceptionClassName;
		}
		model.addAttribute("msg", error);
		
		return "login";
	}
	
}
