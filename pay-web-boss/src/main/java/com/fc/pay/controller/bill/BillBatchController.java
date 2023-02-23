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
import com.fc.pay.bill.business.BillBizCheckBatchBusiness;
import com.fc.pay.bill.service.BillBizBatchService;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;

/**
 * 对账批次管理
 * @author cjw
 *
 */
@Controller
@RequestMapping("billbatch")
public class BillBatchController extends BaseController {
	
	@Autowired
	private BillBizBatchService billBizBatchService;
	
	@Autowired
	private BillBizCheckBatchBusiness billBizCheckBatchBusiness;
	
	/**
	 * 对账批次界面
	 * @return
	 */
	@RequiresPermissions("bill:batch:list")
	@RequestMapping("list")
	public String list(HttpServletRequest request){

		return "billbatch/list";
	}
	
	/**
	 * 对账批次列表数据
	 * @param request
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("bill:batch:list")
	@RequestMapping("listdata")
	@ResponseBody
	public String listData(HttpServletRequest request,String startDate,String endDate,String payChannel,Integer current,Integer size){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> paramMap = new HashMap<>();
			Page page = new Page();
			page.setCurrNum(current);
			page.setPageSize(size);
			paramMap.put("page", page);
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			paramMap.put("payChannel", payChannel);
			
			page = billBizBatchService.page(paramMap);
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
	 * 重启对账批次
	 * @param batchNo
	 * @return
	 */
	@RequiresPermissions("bill:batch:recheck")
	@RequestMapping("recheck")
	@ResponseBody
	public String recheckBatch(String batchNo){
		Map<String,Object> info = new HashMap<>();
		boolean result = false;
		try {
			result = billBizCheckBatchBusiness.recheckBatch(batchNo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		info.put("result", result);
		return JSON.toJSONString(info);
	}
}
