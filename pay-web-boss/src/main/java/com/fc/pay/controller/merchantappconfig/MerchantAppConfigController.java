package com.fc.pay.controller.merchantappconfig;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.common.core.enums.PayWayEnum;
import com.fc.pay.common.core.utils.PathUtil;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;

@Controller
@RequestMapping("merchantappconfig")
public class MerchantAppConfigController extends BaseController {
	
	@Autowired
	private IMerchantAppConfig merchantAppConfigService;
	
	/**
	 * 商户应用支付接口列表数据
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
		Page page = merchantAppConfigService.page(params);
		result.put("data",page);
		result.put("pages",page.getTotalPages());
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd");
	}
	
	/**
	 * 商户应用支付接口新增界面
	 * @return
	 */
	@RequiresPermissions("mct:config:add")
	@RequestMapping("add")
	public String add(HttpServletRequest request,String merchantCode,String merchantName,String merchantAppCode,String merchantAppName){
		request.setAttribute("merchantCode", merchantCode);
		request.setAttribute("merchantName", merchantName);
		request.setAttribute("merchantAppCode", merchantAppCode);
		request.setAttribute("merchantAppName", merchantAppName);
		
		request.setAttribute("payWay", PayWayEnum.values());
		return "merchantappconfig/add";
	}
	
	/**
	 * 商户应用支付接口新增
	 * @return
	 */
	@RequiresPermissions("mct:config:add")
	@RequestMapping("adddata")
	@ResponseBody
	public String addData(HttpServletRequest request,MultipartFile certFile,MultipartFile  certValidateFile,MultipartFile certEncFile,MerchantAppConfig merchantAppConfig){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		try {
			
			// 检查merchantAppCode与channel联合查询是否唯一，如果存在，则不允许再新增此渠道的支付接口
			List<MerchantAppConfig> list = merchantAppConfigService.getByMerchantAppCode(merchantAppConfig.getMerchantAppCode(), merchantAppConfig.getChannel());
			for(MerchantAppConfig config : list){
				
				if( config.getChannel().equals(merchantAppConfig.getChannel()) &&  config.getPayWay().equals(merchantAppConfig.getPayWay()) ){
					info.put("errcode", ErrorEnum.SAVE_ERROR.getCode());
					info.put("errmsg", "支付接口类型已存在");
					return JSON.toJSONString(info);
				}
			}

			BossOperator bossOperator = super.getCurrentOperator();
			merchantAppConfig.setCreater(bossOperator.getRealName());
			merchantAppConfig.setCreateTime(new Date());
			merchantAppConfig.setStatus(1); // 默认为开启状态
			
			StringBuffer uploadPath = new StringBuffer();
			if(merchantAppConfig.getChannel().equals("alipay")){
				uploadPath.append("alipay/").append(merchantAppConfig.getMerchantAppCode()).append("/");
			}else if(merchantAppConfig.getChannel().equals("weixin")){
				uploadPath.append("weixin/").append(merchantAppConfig.getMerchantCode()).append("/")
						.append(merchantAppConfig.getMerchantAppCode()).append("/");
			}else if(merchantAppConfig.getChannel().equals("unionpay")){
				uploadPath.append("weixin/").append(merchantAppConfig.getMerchantAppCode()).append("/");
			}
			
			String certPath = upload(request,certFile,uploadPath.toString());
			merchantAppConfig.setCertPath(certPath);
			String certValidatePath = upload(request,certValidateFile,uploadPath.toString());
			merchantAppConfig.setCertValidate(certValidatePath);
			String certEncPath = upload(request,certEncFile,uploadPath.toString());
			merchantAppConfig.setCertEncPath(certEncPath);
			
			int result = merchantAppConfigService.add(merchantAppConfig);
			if(result>0){
				errmsg = ErrorEnum.SUCCESS.getMsg();
			}else{
				errcode = ErrorEnum.SAVE_ERROR.getCode();
				errmsg = ErrorEnum.SAVE_ERROR.getMsg();
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
	 * 商户应用支付接口修改/查看界面
	 * @return
	 */
	@RequiresPermissions("mct:config:view")
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model,Long merchantConfigAppId){
		MerchantAppConfig merchantAppConfig = merchantAppConfigService.get(merchantConfigAppId);
		model.addAttribute("merchantAppConfig", merchantAppConfig);
		return "merchantappconfig/edit";
	}
	
	/**
	 * 商户应用编辑
	 * @return
	 */
	@RequiresPermissions("mct:config:view")
	@RequestMapping("editdata")
	@ResponseBody
	public String editData(HttpServletRequest request,MultipartFile certFile,MultipartFile  certValidateFile,MultipartFile certEncFile,MerchantAppConfig merchantAppConfig){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		try {
			BossOperator bossOperator = super.getCurrentOperator();
			merchantAppConfig.setEditor(bossOperator.getRealName());
			merchantAppConfig.setEditTime(new Date());
			
			StringBuffer childPath = new StringBuffer();
			if(merchantAppConfig.getChannel().equals("alipay")){
				childPath.append("alipay/").append(merchantAppConfig.getMerchantAppCode()).append("/");
			}else if(merchantAppConfig.getChannel().equals("weixin")){
				childPath.append("weixin/").append(merchantAppConfig.getMerchantCode()).append("/")
						.append(merchantAppConfig.getMerchantAppCode()).append("/");
			}else if(merchantAppConfig.getChannel().equals("unionpay")){
				childPath.append("unionpay/").append(merchantAppConfig.getMerchantCode()).append("/");
			}
			
			String certPath = upload(request,certFile,childPath.toString());
			if(StringUtils.isNotBlank(certPath))
				merchantAppConfig.setCertPath(certPath);
			String certValidatePath = upload(request,certValidateFile,childPath.toString());
			if(StringUtils.isNotBlank(certValidatePath))
				merchantAppConfig.setCertValidate(certValidatePath);
			String certEncPath = upload(request,certEncFile,childPath.toString());
			if(StringUtils.isNotBlank(certEncPath))
				merchantAppConfig.setCertEncPath(certEncPath);
			int result = merchantAppConfigService.modify(merchantAppConfig);
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
	
	/**
	 * 上传文件
	 * @param request
	 * @param uploadFile
	 * @return
	 */
	private String upload(HttpServletRequest request,MultipartFile uploadFile,String childPath){
		try {
			
			if(uploadFile != null && !uploadFile.isEmpty()){
				String fileName = uploadFile.getOriginalFilename();
				String path = PathUtil.readCertBasicPath()+childPath; 
				File file = new File(path,fileName);
				if(!file.exists()){
					file.mkdirs();
				}
				uploadFile.transferTo(file);
				return childPath+fileName;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
}
