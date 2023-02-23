package com.fc.pay.controller.bill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.service.BillBizDoubtService;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.service.merchant.IMerchantApp;

/**
 * 对账存疑管理
 * @author cjw
 *
 */
@Controller
@RequestMapping("billdoubt")
public class BillDoubtController extends BaseController {
	
	@Autowired
	private BillBizDoubtService billBizDoubtService;
	
	@Autowired
	private IMerchantApp merchantApp;
	
	/**
	 * 对账批次界面
	 * @return
	 */
	@RequiresPermissions("bill:doubt:list")
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		List<MerchantApp> merchantApps =  merchantApp.selectMerchantApp();
		model.addAttribute("merchantApps", merchantApps);
		return "billdoubt/list";
	}
	
	/**
	 * 商户列表数据
	 * @param request
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("bill:doubt:list")
	@RequestMapping("listdata")
	@ResponseBody
	public String listData(HttpServletRequest request,String startDate,String endDate,Integer current,Integer size){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> paramMap = this.getRequestParams(request);
			Page page = billBizDoubtService.selectPageList(paramMap, current, size);
			result.put("data",page);
			result.put("total", page.getTotalItem());
			result.put("pages",page.getTotalPages());
		} catch (Exception e) {
			result.put("errcode", ErrorEnum.SERVER_ERROR.getCode());
			result.put("errmsg", ErrorEnum.SERVER_ERROR.getMsg());
			logger.error(e.getMessage(), e);
		}
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd");
	}
	
	/**
	 * 查看存疑详情
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,Model model,Long id){
		try {
			BillBizDoubt billBizDoubt = billBizDoubtService.get(id);
			model.addAttribute("billBizDoubt", billBizDoubt);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "billdoubt/detail";
	}
}
