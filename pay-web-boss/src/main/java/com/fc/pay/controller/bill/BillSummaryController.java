package com.fc.pay.controller.bill;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.bill.service.BillBizSummaryService;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;

@Controller
@RequestMapping("billsummary")
public class BillSummaryController extends BaseController {
	
	@Autowired
	private BillBizSummaryService billBizSummaryService;
	
	/**
	 * 对账批次界面
	 * @return
	 */
	@RequiresPermissions("bill:summary:list")
	@RequestMapping("list")
	public String list(HttpServletRequest request){

		return "billsummary/list";
	}
	
	/**
	 * 商户列表数据
	 * @param request
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("bill:summary:list")
	@RequestMapping("listdata")
	@ResponseBody
	public String listData(HttpServletRequest request,Integer current,Integer size){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> paramMap = this.getRequestParams(request);
			
			Page page = billBizSummaryService.pageList(paramMap, current, size);
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
