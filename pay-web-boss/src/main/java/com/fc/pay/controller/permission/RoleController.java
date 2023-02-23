package com.fc.pay.controller.permission;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.fc.pay.boss.entity.BossPermission;
import com.fc.pay.boss.entity.BossRole;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.boss.service.IBossMenuRoleService;
import com.fc.pay.boss.service.IBossMenuService;
import com.fc.pay.boss.service.IBossOperatorService;
import com.fc.pay.boss.service.IBossPermissionService;
import com.fc.pay.boss.service.IBossRoleOperatorService;
import com.fc.pay.boss.service.IBossRolePermissionService;
import com.fc.pay.boss.service.IBossRoleService;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

	@Autowired
	private IBossRoleService bossRoleService;
	
	@Autowired
	private IBossRoleOperatorService bossRoleOperatorService;
	
	@Autowired
	private IBossMenuRoleService bossMenuRoleService;
	
	@Autowired
	private IBossMenuService bossMenuService;
	
	@Autowired
	private IBossPermissionService bossPermissionService;
	
	@Autowired
	private IBossRolePermissionService bossRolePermissionService;
	
	@Autowired
	private IBossOperatorService bossOperatorService;
	
	
	/**
	 * 查询角色列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("pms:role:view")
	@RequestMapping(value="listUI")
	public String listUI(HttpServletRequest request){
		return "role/list";
	}
	
	/**
	 * 查询角色列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("pms:role:view")
	@RequestMapping(value="list")
	@ResponseBody
	public String list(HttpServletRequest request,@RequestParam(defaultValue="1")Integer current,@RequestParam(defaultValue="15")Integer size){
		Map<String,Object> params = this.getRequestParams(request);
		Map<String,Object> result = new HashMap<>();
		// 分页查询出所有的菜单
		Page page = bossRoleService.selectPageList(params, current,size);
		result.put("data",page);
		result.put("total", page.getTotalItem());
		result.put("pages",page.getTotalPages());
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 跳至添加角色
	 * @param request
	 * @param model
	 * @param parentMenu
	 * @return
	 */
	@RequiresPermissions("pms:role:add")
	@RequestMapping(value="addUI",method=RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model){
		return "role/add";
	}
	
	/**
	 * 新增角色
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("pms:role:add")
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,BossRole role){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		try {
			BossOperator bossOperator = super.getCurrentOperator();
			
			// 统计角色是否存在
			Map<String,Object> params = new HashMap<>();
			params.put("roleName", role.getRoleName());
			int count = bossRoleService.selectCount(params);
			
			// 统计角色编码是否存在
			params.clear();
			params.put("roleCode", role.getRoleCode());
			int countbycode = bossRoleService.selectCount(params);
			
			if(count > 0){
				errcode = ErrorEnum.ROLE_EXISTS.getCode();
				errmsg = ErrorEnum.ROLE_EXISTS.getMsg();
			}else if(countbycode>0){
				errcode = ErrorEnum.ROLE_CODE_EXISTS.getCode();
				errmsg = ErrorEnum.ROLE_CODE_EXISTS.getMsg();
			}else{
				role.setCreater(bossOperator.getRealName());
				role.setCreateTime(new Date());
				role.setVersion(0L);
				boolean result = bossRoleService.insert(role);
				if(result){
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
	@RequiresPermissions("pms:role:edit")
	@RequestMapping(value="editUI/{view}",method=RequestMethod.GET)
	public String editUI(HttpServletRequest request,Model model,Long roleId,@PathVariable String view){
		BossRole role = bossRoleService.selectById(roleId);
		List<BossOperator> operators = bossOperatorService.selectOperatorByRoleId(roleId);
		model.addAttribute("role", role);
		model.addAttribute("operators", operators);
		return "role/"+view;
	}
	
	/**
	 * 修改角色
	 * @param request
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("pms:role:edit")
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public String edit(HttpServletRequest request,BossRole role){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg = "";
		try {
			BossOperator bossOperator = super.getCurrentOperator();
			// 统计角色是否存在
			Map<String,Object> params = new HashMap<>();
			params.put("roleName", role.getRoleName());
			params.put("id", role.getId());
			int count = bossRoleService.selectCount(params);
			
			// 统计角色编码是否存在
			params.clear();
			params.put("roleCode", role.getRoleCode());
			params.put("id", role.getId());
			int countbycode = bossRoleService.selectCount(params);
			
			if(count > 0){
				errcode = ErrorEnum.ROLE_EXISTS.getCode();
				errmsg = ErrorEnum.ROLE_EXISTS.getMsg();
			}else if(countbycode>0){
				errcode = ErrorEnum.ROLE_CODE_EXISTS.getCode();
				errmsg = ErrorEnum.ROLE_CODE_EXISTS.getMsg();
			}else{
				role.setEditor(bossOperator.getRealName());
				role.setEditTime(new Date());
				role.setVersion(0L);
				boolean result = bossRoleService.updateSelectiveById(role);
				if(result){
					errmsg = ErrorEnum.SUCCESS.getMsg();
				}else{
					errcode = ErrorEnum.UPDATE_ERROR.getCode();
					errmsg = ErrorEnum.UPDATE_ERROR.getMsg();
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
	 * 删除角色
	 * @param request
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequiresPermissions("pms:role:delete")
	@RequestMapping(value="delete",method=RequestMethod.GET)
	@ResponseBody
	public String delete(HttpServletRequest request,Long roleId){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg = "";
		try {
			if(roleId == null){
				errcode = ErrorEnum.NO_DATA_ERROR.getCode();
				errmsg = ErrorEnum.NO_DATA_ERROR.getMsg();
			}
			// 查看该角色有无绑定用户
			Map<String,Object> params = new HashMap<>();
			params.put("roleId", roleId);
			int count = bossRoleOperatorService.selectCount(params);
			if(count>0){
				errcode = 1;
				errmsg = "有【" + count + "】个操作员关联到此角色，要先解除所有关联后才能删除";
			}else{
				boolean result = bossRoleService.deleteById(roleId);
				if(result){
					errcode = ErrorEnum.SUCCESS.getCode();
					errmsg = ErrorEnum.SUCCESS.getMsg();
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
	 * 分配权限界面
	 * @param req
	 * @param model
	 * @param roleId
	 * @return
	 */
	@RequiresPermissions("pms:role:assignpermission")
	@RequestMapping("assignPermisUI")
	public String assignPermissionUI(HttpServletRequest req, Model model, Long roleId) {
		// 进入界面首先加载所有权限
		List<BossPermission> bossPermissions = bossPermissionService.selectAllPermisWithChecked(null, roleId);
		model.addAttribute("roleId", roleId);
		model.addAttribute("bossPermissions", bossPermissions);
		return "role/assignpermis";
	}
	
	/**
	 * 分配权限
	 * @param req
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	@RequiresPermissions("pms:role:assignpermission")
	@RequestMapping("assignPermis")
	@ResponseBody
	public String assignPermission(HttpServletRequest req,Long roleId,Long[] permissionId,Long[] permisIds){
		Map<String,Object> info = new HashMap<String, Object>();
		int errcode = 0;
		String errmsg = "";
		try {
			BossOperator operator = super.getCurrentOperator();
			bossRolePermissionService.insertBatchRolePermission(operator, roleId, permissionId,permisIds);
			errmsg = ErrorEnum.SUCCESS.getMsg();
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
	 * 分配菜单界面
	 * @param req
	 * @param model
	 * @param roleId
	 * @return
	 */
	@RequiresPermissions("pms:menu:view")
	@RequestMapping("assignMenuUI")
	public String assignMenuUI(HttpServletRequest req, Model model, Long roleId){
		model.addAttribute("roleId", roleId);
		return "role/assignmenu";
	}
	
	/**
	 * 分配菜单
	 * @param req
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	@RequiresPermissions("pms:menu:view")
	@RequestMapping("assignMenu")
	@ResponseBody
	public String assignMenu(HttpServletRequest req,Long roleId,String menuIds){
		Map<String,Object> info = new HashMap<String, Object>();
		int errcode = 0;
		String errmsg = "";
		try {
			BossOperator operator = super.getCurrentOperator();
			boolean result = bossMenuRoleService.assignRoleMenu(operator, roleId, menuIds);
			if(result){
				errmsg = ErrorEnum.SUCCESS.getMsg();
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
