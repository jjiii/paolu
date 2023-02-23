package com.fc.pay.controller.permission;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.entity.BossRole;
import com.fc.pay.boss.entity.BossRoleOperator;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.boss.enums.StatusEnum;
import com.fc.pay.boss.service.IBossOperatorService;
import com.fc.pay.boss.service.IBossRoleOperatorService;
import com.fc.pay.boss.service.IBossRoleService;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;
import com.fc.pay.utils.PasswordHelper;

@Controller
@RequestMapping("operator")
public class OperatorController extends BaseController {

	@Autowired
	private IBossOperatorService bossOperatorService;
	
	@Autowired
	private IBossRoleService bossRoleService;
	
	@Autowired
	private IBossRoleOperatorService bossRoleOperatorService;
	
	/**
	 * 查询操作员UI
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("pms:operator:view")
	@RequestMapping(value="listUI")
	public String listUI(HttpServletRequest request,Model model){

		return "operator/list";
	}
	
	/**
	 * 查询操作员列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("pms:operator:view")
	@RequestMapping(value="list")
	@ResponseBody
	public String list(HttpServletRequest request,@RequestParam(defaultValue="1")Integer current,@RequestParam(defaultValue="15")Integer size){
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> params = this.getRequestParams(request);
		Page page = bossOperatorService.selectPageList(params,current,size);
		result.put("data",page);
		result.put("total", page.getTotalItem());
		result.put("pages",page.getTotalPages());
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd");
	}
	
	/**
	 * 跳至添加操作员页面
	 * @param request
	 * @param model
	 * @param parentMenu
	 * @return
	 */
	@RequiresPermissions("pms:operator:add")
	@RequestMapping(value="addUI",method=RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model){
		// 查出所有的角色
		List<BossRole> roles = bossRoleService.selectList(null);
		model.addAttribute("roles", roles);
		return "operator/add";
	}
	
	/**
	 * 新增操作员
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("pms:operator:add")
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,BossOperator operator,Long[] roleId){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg="";
		try {
			BossOperator bossOperator = super.getCurrentOperator();
			// 检测该用户名是否存在
			Map<String,Object> params = new HashMap<>();
			params.put("loginName", operator.getLoginName());
			int count = bossOperatorService.selectCount(params);
			if(count > 0){
				errcode = ErrorEnum.OPERATOR_EXISTS.getCode();
				errmsg = ErrorEnum.OPERATOR_EXISTS.getMsg();
			}else{
				operator.setCreater(bossOperator.getRealName());
				operator.setCreateTime(new Date());
				operator.setVersion(0L);
				operator.setType(1);	// 除了初始的超级管理员，其它的均为普通账户
				PasswordHelper.encryptPassword(operator);
				//msg = bossOperatorService.insert(operator);
				boolean result = bossOperatorService.insertOperatorAndRole(operator, roleId);
				if(result){
					errcode = ErrorEnum.SUCCESS.getCode();
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
	 * 跳至修改/显示页面
	 * @param request
	 * @param model
	 * @param menuId
	 * @param parentId
	 * @param parentName
	 * @return
	 */
	@RequiresPermissions("pms:operator:edit")
	@RequestMapping(value="editUI/{view}",method=RequestMethod.GET)
	public String editUI(HttpServletRequest request,Model model,Long operatorId,@PathVariable String view){
		BossOperator operator = bossOperatorService.selectById(operatorId);
		model.addAttribute("operator", operator);
		// 查出所有的角色
		List<BossRole> roles = bossRoleService.selectList(null);
		// 列出该用户所拥有的角色
		Map<String,Object> params = new HashMap<>();
		params.put("operatorId", operatorId);
		List<BossRoleOperator> roleOperators = bossRoleOperatorService.selectList(params);
		for(BossRole role: roles){
			for(BossRoleOperator bro : roleOperators){
				if(role.getId()==bro.getRoleId()){
					role.setIsChecked(1);	// 表示该角色已绑定到此用户
				}
			}
		}
		model.addAttribute("roles", roles);
		model.addAttribute("roleOperators", roleOperators);
		
		return "operator/"+view;
	}
	
	/**
	 * 修改账户
	 * @param request
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("pms:operator:edit")
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public String edit(HttpServletRequest request,Model model,BossOperator operator,Long[] roleId){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg="";
		try {
			BossOperator bossOperator = super.getCurrentOperator();
			operator.setEditor(bossOperator.getRealName());
			operator.setEditTime(new Date());
			operator.setVersion(0L);
			//boolean result = bossOperatorService.updateSelectiveById(bossOperator);
			boolean result = bossOperatorService.updateOperatorAndRole(operator,roleId);
			if(result){
				errcode = ErrorEnum.SUCCESS.getCode();
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
		// 更新
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}

	/**
	 * 登录用户修改个人信息界面
	 * @param request
	 * @param model
	 * @param operatorId
	 * @param view
	 * @return
	 */
	@RequestMapping(value="editview/{view}",method=RequestMethod.GET)
	public String editSelf(HttpServletRequest request,Model model,Long operatorId,@PathVariable String view){
		BossOperator operator = bossOperatorService.selectById(operatorId);
		model.addAttribute("operator", operator);
		
		return "operator/"+view;
	}	
	
	/**
	 * 登录用户修改个人信息
	 * @param request
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="editselfdata",method=RequestMethod.POST)
	@ResponseBody
	public String editSelfData(HttpServletRequest request,BossOperator operator){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg="";
		try {
			BossOperator currentOperator = super.getCurrentOperator();
			if(StringUtils.isNotBlank(operator.getLoginPwd())){
				operator.setLoginName(currentOperator.getLoginName());
				PasswordHelper.encryptPassword(operator);
			}
			boolean result = bossOperatorService.updateSelectiveById(operator);
			if(result){
				errcode = ErrorEnum.SUCCESS.getCode();
				errmsg = ErrorEnum.SUCCESS.getMsg();
			}else{
				errcode = ErrorEnum.UPDATE_ERROR.getCode();
				errmsg = ErrorEnum.UPDATE_ERROR.getMsg();
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			errcode = ErrorEnum.SERVER_ERROR.getCode();
			errmsg = ErrorEnum.SERVER_ERROR.getMsg();
		}
		// 更新
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}
	
	/**
	 * 删除操作员
	 * @param request
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequiresPermissions("pms:operator:delete")
	@RequestMapping(value="delete",method=RequestMethod.GET)
	@ResponseBody
	public String delete(HttpServletRequest request,Long operatorId){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg="";
		try {
			if(operatorId == null){
				errcode = ErrorEnum.DEL_ERROR.getCode();
				errmsg = "无法获取要删除的数据";
			}
			boolean result = bossOperatorService.delOperatorAndRole(operatorId);
			if(result){
				errcode = ErrorEnum.SUCCESS.getCode();
				errmsg = ErrorEnum.SUCCESS.getMsg();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			errcode = ErrorEnum.SERVER_ERROR.getCode();
			errmsg = ErrorEnum.SERVER_ERROR.getMsg();
		}
		// 更新
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}
	
	/**
	 * 冻结/结冻账户
	 * @param request
	 * @param model
	 * @param operatorId
	 * @return
	 */
	@RequiresPermissions("pms:operator:changestatus")
	@RequestMapping(value="freeze",method=RequestMethod.GET)
	@ResponseBody
	public String freeze(HttpServletRequest request,Long operatorId,Boolean isfreeze){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg="";
		try {
			BossOperator operator = new BossOperator();
			operator.setId(operatorId);
			if(isfreeze){
				operator.setStatus(StatusEnum.UNACTIVE.name());
			}else{
				operator.setStatus(StatusEnum.ACTIVE.name());
			}
			boolean result = bossOperatorService.updateSelectiveById(operator);
			if(result){
				errcode = ErrorEnum.SUCCESS.getCode();
				errmsg = ErrorEnum.SUCCESS.getMsg();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			errcode = ErrorEnum.SERVER_ERROR.getCode();
			errmsg = ErrorEnum.SERVER_ERROR.getMsg();
		}
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}
	
	/**
	 * 重置密码UI
	 * @param request
	 * @param model
	 * @param operatorId
	 * @return
	 */
	@RequiresPermissions("pms:operator:resetpwd")
	@RequestMapping(value="resetUI",method=RequestMethod.GET)
	public String resetPwdUI(HttpServletRequest request,Model model,Long operatorId){
		model.addAttribute("operatorId", operatorId);
		return "operator/resetpwd";
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @param model
	 * @param operatorId
	 * @param loginPwd
	 * @return
	 */
	@RequiresPermissions("pms:operator:resetpwd")
	@RequestMapping(value="reset",method=RequestMethod.GET)
	@ResponseBody
	public String resetPwd(HttpServletRequest request,Model model,Long operatorId,String loginPwd){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg="";
		try {
			
			BossOperator operator = bossOperatorService.selectById(operatorId);
			operator.setLoginPwd(loginPwd);
			PasswordHelper.encryptPassword(operator);
			if(StringUtils.isBlank(loginPwd)){
				errcode = ErrorEnum.EMPTY_PWD.getCode();
				errmsg = ErrorEnum.EMPTY_PWD.getMsg();
			}else{
				boolean result = bossOperatorService.updateSelectiveById(operator);
				if(result){
					errcode = ErrorEnum.SUCCESS.getCode();
					errmsg = ErrorEnum.SUCCESS.getMsg();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			errcode = ErrorEnum.SERVER_ERROR.getCode();
			errmsg = ErrorEnum.SERVER_ERROR.getMsg();
		}
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}
	
}
