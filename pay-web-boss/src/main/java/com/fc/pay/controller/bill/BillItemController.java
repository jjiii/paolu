package com.fc.pay.controller.bill;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.bill.service.BillBizItemService;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;

/**
 * 对账批次管理
 * @author cjw
 *
 */
@Controller
@RequestMapping("billitem")
public class BillItemController extends BaseController {
	
	@Autowired
	private BillBizItemService billBizItemService;
	
	/**
	 * 对账批次界面
	 * @return
	 */
	@RequiresPermissions("bill:item:list")
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model,String batchNo,String billType){
		model.addAttribute("batchNo", batchNo);
		model.addAttribute("billType", billType);
		return "billitem/list";
	}
	
	/**
	 * 商户列表数据
	 * @param request
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("bill:item:list")
	@RequestMapping("listdata")
	@ResponseBody
	public String listData(HttpServletRequest request,Integer current,Integer size){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> paramMap = this.getRequestParams(request);
			Page page = new Page();
			page.setCurrNum(current);
			page.setPageSize(size);
			paramMap.put("page", page);
			
			page = billBizItemService.page(paramMap);
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
	
}
