package com.fc.pay.controller.merchantapp;

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
import com.fc.pay.common.security.RSAKey;
import com.fc.pay.common.security.RSAPem;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.service.merchant.IMerchantApp;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;

@Controller
@RequestMapping("merchantapp")
public class MerchantAppController extends BaseController {
	
	@Autowired
	private IMerchantApp merchantAppService;
	
	@Autowired
	private IMerchantAppConfig merchantAppConfigService;
	
	/**
	 * 商户应用列表数据
	 * @param request
	 * @param current
	 * @param pagesize
	 * @return
	 */
	@RequiresPermissions("mct:mrechant:list")
	@RequestMapping("listdata")
	@ResponseBody
	public String listData(HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<>();
		Page page = merchantAppService.page(params);
		result.put("data",page);
		result.put("pages",page.getTotalPages());
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd");
	}
	
	/**
	 * 商户应用新增界面
	 * @return
	 */
	@RequiresPermissions("mct:app:add")
	@RequestMapping("add")
	public String add(HttpServletRequest request,String merchantCode,String merchantName){
		request.setAttribute("merchantCode", merchantCode);
		request.setAttribute("merchantName", merchantName);
		String merchantAppCode = merchantAppService.generateCode();
		request.setAttribute("merchantAppCode", merchantAppCode);
		try {
			RSAKey reaKey = new RSAKey();
			RSAPem rsaPem = reaKey.makeRSAKeyPairOnOpenSSL(merchantCode);
			request.setAttribute("privateKey", rsaPem.getPrivateKey());
			request.setAttribute("publicKey", rsaPem.getPublicKey());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return "merchantapp/add";
	}
	
	/**
	 * 商户应用新增
	 * @return
	 */
	@RequiresPermissions("mct:app:add")
	@RequestMapping("adddata")
	@ResponseBody
	public String addData(HttpServletRequest request,MerchantApp merchantApp){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		try {
			// 检测试编号是否存在
			boolean codeIsExists = merchantAppService.codeIsExists(merchantApp.getMerchantAppCode());
			if(codeIsExists){
				errcode = ErrorEnum.SAVE_ERROR.getCode();
				errmsg = "编码已存在";
			}else{
				BossOperator bossOperator = super.getCurrentOperator();
				merchantApp.setCreater(bossOperator.getRealName());
				merchantApp.setCreateTime(new Date());
				merchantApp.setStatus(1);
				int result = merchantAppService.add(merchantApp);
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
	 * 商户应用修改/查看界面
	 * @return
	 */
	@RequiresPermissions("mct:app:view")
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model,Long merchantAppId){
		MerchantApp merchantApp = merchantAppService.get(merchantAppId);
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("merchant_app_code", merchantApp.getMerchantAppCode());
		List<MerchantAppConfig> merchantAppConfigs =  merchantAppConfigService.list(param);
		
		model.addAttribute("merchantApp", merchantApp);
		model.addAttribute("merchantAppConfigs", merchantAppConfigs);
		return "merchantapp/edit";
	}
	
	/**
	 * 商户应用编辑
	 * @return
	 */
	@RequiresPermissions("mct:app:view")
	@RequestMapping("editdata")
	@ResponseBody
	public String editData(HttpServletRequest request,MerchantApp merchantApp){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		try {
			BossOperator bossOperator = super.getCurrentOperator();
			merchantApp.setEditor(bossOperator.getRealName());
			merchantApp.setEditTime(new Date());
			int result = merchantAppService.modify(merchantApp);
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
