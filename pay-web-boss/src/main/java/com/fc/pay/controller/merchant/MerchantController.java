package com.fc.pay.controller.merchant;

import java.util.Date;
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
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;
import com.fc.pay.trade.entity.Merchant;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.service.merchant.IMerchant;
import com.fc.pay.trade.service.merchant.IMerchantApp;

@Controller
@RequestMapping("merchant")
public class MerchantController extends BaseController {
	
	@Autowired
	private IMerchant merchantService;
	@Autowired
	private IMerchantApp merchantAppService;
	
	/**
	 * 商户管理界面
	 * @return
	 */
	@RequiresPermissions("mct:mrechant:list")
	@RequestMapping("list")
	public String list(HttpServletRequest request){
		return "merchant/list";
	}
	
	/**
	 * 商户列表数据
	 * @param request
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("mct:mrechant:list")
	@RequestMapping("listdata")
	@ResponseBody
	public String listData(HttpServletRequest request,Integer current,Integer size){
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<>();
		Page page = merchantService.page(params,current,size);
		result.put("data",page);
		result.put("total", page.getTotalItem());
		result.put("pages",page.getTotalPages());
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd");
	}
	
	/**
	 * 商户新增界面
	 * @return
	 */
	@RequiresPermissions("mct:mrechant:add")
	@RequestMapping("add")
	public String add(HttpServletRequest request){
		request.setAttribute("merchantCode", merchantService.generateCode());
		return "merchant/add";
	}
	
	/**
	 * 商户新增
	 * @return
	 */
	@RequiresPermissions("mct:mrechant:add")
	@RequestMapping("adddata")
	@ResponseBody
	public String addData(HttpServletRequest request,Merchant merchant){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		try {
			boolean codeIsExists = merchantService.codeIsExists(merchant.getMerchantCode());
			if(codeIsExists){
				errcode = ErrorEnum.SAVE_ERROR.getCode();
				errmsg = "商户编码已存在";
			}else{
				BossOperator bossOperator = super.getCurrentOperator();
				merchant.setCreater(bossOperator.getRealName());
				merchant.setCreateTime(new Date());
				merchant.setStatus(1);
				int result = merchantService.add(merchant);
				if(result>0){
					errmsg = ErrorEnum.SUCCESS.getMsg();
				}else{
					errcode = ErrorEnum.SAVE_ERROR.getCode();
					errmsg = ErrorEnum.SAVE_ERROR.getMsg();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			errcode = ErrorEnum.SERVER_ERROR.getCode();
			errmsg = ErrorEnum.SERVER_ERROR.getMsg();
		}
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}
	
	/**
	 * 商户修改/查看界面
	 * @return
	 */
	@RequiresPermissions("mct:mrechant:view")
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model,Long merchantId){
		Merchant merchant = merchantService.get(merchantId);
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("merchant_code", merchant.getMerchantCode());
		List<MerchantApp> merchantApps =  merchantAppService.list(param);
		
		model.addAttribute("merchant", merchant);
		model.addAttribute("merchantApps", merchantApps);
		return "merchant/edit";
	}
	
	/**
	 * 商户新增
	 * @return
	 */
	@RequiresPermissions("mct:mrechant:view")
	@RequestMapping("editdata")
	@ResponseBody
	public String editData(HttpServletRequest request,Merchant merchant){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		try {
			BossOperator bossOperator = super.getCurrentOperator();
			merchant.setEditor(bossOperator.getRealName());
			merchant.setEditTime(new Date());
			int result = merchantService.modify(merchant);
			if(result>0){
				errmsg = ErrorEnum.SUCCESS.getMsg();
			}else{
				errcode = ErrorEnum.UPDATE_ERROR.getCode();
				errmsg = ErrorEnum.UPDATE_ERROR.getMsg();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			errcode = ErrorEnum.SERVER_ERROR.getCode();
			errmsg = ErrorEnum.SERVER_ERROR.getMsg();
		}
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}
	
}
