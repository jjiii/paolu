package com.fc.pay.controller.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.pay.boss.entity.BossMenu;
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.service.IBossMenuService;
import com.fc.pay.controller.base.BaseController;

/**
 * 系统首页
 * @author cjw
 *
 */
@Controller
public class HomeController extends BaseController{
	
	@Autowired
	private IBossMenuService bossMenuService;

	@RequestMapping("/")
	public String index(HttpServletRequest request,Model model){
		// 获取当前用户
		BossOperator operator = (BossOperator) this.getSession().getAttribute("BossOperator");
		// 根据用户ID获取用户的菜单
		List<BossMenu> menus = bossMenuService.findMenuByOperatorId(operator.getId());
		getSession().setAttribute("menus", menus);
		return "index";
	}
	
	@RequestMapping("main")
	public String main(HttpServletRequest request,Model model){
		
		return "main";
	}
	
	@RequestMapping("/clearhistoryurl")
	@ResponseBody
	public void clearHistoryUrlSession(HttpServletRequest request){
		getSession().removeAttribute("reurl");
	}
}
