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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.boss.entity.BossMenu;
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.entity.BossPermission;
import com.fc.pay.boss.entity.BossRole;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.boss.enums.StatusEnum;
import com.fc.pay.boss.service.IBossMenuService;
import com.fc.pay.boss.service.IBossPermissionService;
import com.fc.pay.boss.service.IBossRolePermissionService;
import com.fc.pay.boss.service.IBossRoleService;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;

@Controller
@RequestMapping("permission")
public class PermissController extends BaseController {
	
	@Autowired
	private IBossPermissionService bossPermissionService;
	
	@Autowired
	private IBossRolePermissionService bossRolePermissionService;
	
	@Autowired
	private IBossRoleService bossRoleService;
	
	@Autowired
	private IBossMenuService bossMenuService;
	
	/**
	 * 查询操作员列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("pms:permission:view")
	@RequestMapping(value="listUI")
	public String listUI(HttpServletRequest request,Model model){
		// 查询所有菜单
		List<BossMenu> menus = bossMenuService.selectAllMenus();
		model.addAttribute("menus", menus);
		return "permission/list";
	}
	
	@RequiresPermissions("pms:permission:view")
	@RequestMapping(value="list")
	@ResponseBody
	public String list(HttpServletRequest request,Model model,Integer current,Integer size){
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> params = this.getRequestParams(request);
		Page page = bossPermissionService.selectPermissionsWithPage(current, size, params);
		result.put("data",page);
		result.put("pages",page.getTotalPages());
		result.put("total",page.getTotalItem());
		
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 跳至添加权限页面
	 * @param request
	 * @param model
	 * @param parentMenu
	 * @return
	 */
	@RequiresPermissions("pms:permission:add")
	@RequestMapping(value="addUI",method=RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model){
		// 查出所有的角色
		
		return "permission/add";
	}
	
	/**
	 * 新增权限
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("pms:permission:add")
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,BossPermission permission){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		BossOperator bossOperator = super.getCurrentOperator();
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("permission", permission.getPermission());
			// 检测该角色名是否存在
			int count = bossPermissionService.selectCount(params);
			if(count > 0){
				errcode = ErrorEnum.PERMISS_EXISTS.getCode();
				errmsg = ErrorEnum.PERMISS_EXISTS.getMsg();
			}else{
				permission.setCreater(bossOperator.getRealName());
				boolean result = bossPermissionService.insertAndAsignAdmin(permission);//bossPermissionService.insert(permission);
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
	@RequiresPermissions("pms:permission:edit")
	@RequestMapping(value="editUI",method=RequestMethod.GET)
	public String editUI(HttpServletRequest request,Model model,Long permissionId){
		BossPermission permission = bossPermissionService.selectById(permissionId);
		List<BossRole> roles = bossRoleService.selectRoleByPermisId(permissionId);
		model.addAttribute("permission", permission);
		model.addAttribute("roles", roles);
		return "permission/edit";
	}
	
	/**
	 * 修改权限
	 * @param request
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("pms:permission:edit")
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public String edit(HttpServletRequest request,BossPermission permission){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg = "";
		try {
			BossOperator bossOperator = super.getCurrentOperator();
			Map<String,Object> params = new HashMap<>();
			params.put("permission", permission.getPermission());
			params.put("id", permission.getId());
			// 检测权限名是否存在
			int count = bossPermissionService.selectCount(params);
			if(count > 0){
				errcode = ErrorEnum.ROLE_EXISTS.getCode();
				errmsg = ErrorEnum.ROLE_EXISTS.getMsg();
			}else{
				permission.setEditor(bossOperator.getRealName());
				permission.setEditTime(new Date());
				permission.setVersion(0L);
				permission.setStatus(StatusEnum.ACTIVE.name());
			}
			boolean result = bossPermissionService.updateSelectiveById(permission);
			if(result){
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
	 * 删除权限
	 * @param request
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequiresPermissions("pms:permission:delete")
	@RequestMapping(value="delete",method=RequestMethod.GET)
	@ResponseBody
	public String delete(HttpServletRequest request,Long permissionId){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg = "";
		try {
			if(permissionId == null){
				errcode = ErrorEnum.NO_DATA_ERROR.getCode();
				errmsg = ErrorEnum.NO_DATA_ERROR.getMsg();
			}
			// 查看该角色有无绑定用户
			/*BossRolePermission rolePermission = new BossRolePermission();
			rolePermission.setId(permissionId);
			Map<String,Object> params = new HashMap<>();
			params.put("permissionId", permissionId);
			int count = bossRolePermissionService.selectCount(params);
			if(count>0){
				errcode = 1;
				errmsg = "有【" + count + "】个角色关联到此权限，要先解除所有关联后才能删除";
			}*/
			
			boolean result = bossPermissionService.delPermissionAndRole(permissionId);//bossPermissionService.deleteById(permissionId);
			if(result){
				errcode = ErrorEnum.SUCCESS.getCode();
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
