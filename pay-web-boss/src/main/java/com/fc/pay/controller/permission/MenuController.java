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
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.boss.service.IBossMenuRoleService;
import com.fc.pay.boss.service.IBossMenuService;
import com.fc.pay.boss.service.IBossRolePermissionService;
import com.fc.pay.controller.base.BaseController;

@Controller
@RequestMapping("menu")
public class MenuController extends BaseController {
	
	@Autowired
	private IBossMenuService bossMenuService;
	
	@Autowired
	private IBossMenuRoleService bossMenuRoleService;
	
	@Autowired
	private IBossRolePermissionService bossRolePermissionService;
	
	
	/**
	 * 查询菜单列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("pms:menu:view")
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(HttpServletRequest request,Model model){
		// 分页查询出所有的菜单
		model.addAttribute("menus", bossMenuService.selectList(null));
		return "menu/list";
	}
	
	/**
	 * 查询菜单列表JSON
	 * @param request
	 * @return
	 */
	@RequiresPermissions("pms:menu:view")
	@RequestMapping(value="listdata",method=RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request,Long roleId){
		Map<String,Object> data = new HashMap<>();
		// 查询出所有的菜单
		List<BossMenu> menus = bossMenuService.selectAllMenus();
		if(roleId != null){
			// 查询该角色的所有绑定过的菜单
			List<Long> menuIds = bossMenuRoleService.selectListMenuIds(roleId);
			for(Long menuId : menuIds){
				for(BossMenu menu : menus){
					// 如果已关联的菜单在菜单中找到，则设为选中,一级菜单除外
					if(menuId == menu.getId()){
						//if(menu.getParentId()>0){
							menu.setIschecked(true);
						//}
					}
				}
			}
		}
		data.put("menus", menus);
		return JSON.toJSONString(data);
	}
	
	/**
	 * 查询菜单列表带权限JSON
	 * @param request
	 * @return
	 */
	@RequiresPermissions("pms:menu:view")
	@RequestMapping(value="listwithpermis",method=RequestMethod.GET)
	@ResponseBody
	public String listWithPermis(HttpServletRequest request,Long roleId){
		Map<String,Object> data = new HashMap<>();
		// 查询所有菜单及权限
		List<BossMenu> menus = bossMenuService.findMenuWithPermission();
		// 查询角色下绑定的权限
		List<Long> permissionIds = bossRolePermissionService.selectListPermissionIds(roleId);
		for(BossMenu bossMenu : menus){
			List<BossPermission> brp = bossMenu.getPermissions();
			for(BossPermission perm : brp){
				if(permissionIds.contains(perm.getId())){
					perm.setIschecked(true);
				}
			}
		}
		data.put("menus", menus);
		return JSON.toJSONString(data);
	}	
	
	/**
	 * 跳至添加菜单页面
	 * @param request
	 * @param model
	 * @param parentMenu
	 * @return
	 */
	@RequiresPermissions("pms:menu:add")
	@RequestMapping(value="addUI",method=RequestMethod.GET)
	public String addUI(HttpServletRequest request,Model model,BossMenu parentMenu){
		model.addAttribute("parentMenu", parentMenu);
		return "menu/add";
	}
	
	/**
	 * 新增菜单
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("pms:menu:add")
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,Model model,BossMenu menu){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		try {
			BossOperator bossOperator = getCurrentOperator();
			Map<String,Object> params = new HashMap<>();
			params.put("name", menu.getName());
			// 统计是否有相同的菜单名
			int count = bossMenuService.selectCount(params);
			if(count>0){
				errcode = ErrorEnum.MENU_EXISTS.getCode();
				errmsg = ErrorEnum.MENU_EXISTS.getMsg();
			}else{
				menu.setCreater(bossOperator.getRealName());
				
				// 新增菜单并分配给超给管理员
				boolean result = bossMenuService.insertAndAsignAdmin(menu); //bossMenuService.insert(menu);
				
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
	 * 跳至修改页面
	 * @param request
	 * @param model
	 * @param menuId
	 * @param parentId
	 * @param parentName
	 * @return
	 */
	@RequiresPermissions("pms:menu:edit")
	@RequestMapping(value="editUI",method=RequestMethod.GET)
	public String editUI(HttpServletRequest request,Model model,Long menuId,Long parentId,String parentName){
		BossMenu menu = bossMenuService.selectById(menuId);
		model.addAttribute("menu", menu);
		model.addAttribute("parentId", parentId);
		model.addAttribute("parentName", parentName);
		return "menu/edit";
	}
	
	/**
	 * 修改菜单
	 * @param request
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("pms:menu:edit")
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public String edit(HttpServletRequest request,BossMenu menu){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		try {
			BossOperator bossOperator = getCurrentOperator();
			// 统计是否有相同的菜单名
			Map<String,Object> params = new HashMap<>();
			params.put("name", menu.getName());
			params.put("id", menu.getId());
			int count = bossMenuService.selectCount(params);
			if(count>0){
				errcode = ErrorEnum.MENU_EXISTS.getCode();
				errmsg = ErrorEnum.MENU_EXISTS.getMsg();
			}else{
				// 如果更新的是父级菜单，走此设置
				if(menu.getParentId()==null){
					menu.setParentId(0L);
				}
				menu.setEditor(bossOperator.getRealName());
				menu.setEditTime(new Date());
				//menu.setStatus("ACTIVE");
				menu.setVersion(0L);
				boolean result = bossMenuService.updateSelectiveById(menu);
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
	 * 删除菜单
	 * @param request
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequiresPermissions("pms:menu:delete")
	@RequestMapping(value="delete",method=RequestMethod.GET)
	@ResponseBody
	public String delete(HttpServletRequest request,Model model,Long menuId){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = "";
		try {
			if(menuId == null){
				errcode = ErrorEnum.DEL_ERROR.getCode();
				errmsg = "无法获取要删除的数据";
			}
			// 查看该菜单下是否还有子菜单，没有则通过删除，有则提示禁止删除
			Map<String,Object> params = new HashMap<>();
			params.put("parentId", menuId);
			
			int count = bossMenuService.selectCount(params);
			if(count>0){
				errcode = ErrorEnum.DEL_ERROR.getCode();
				errmsg = "此菜单关联有"+count+"个子菜单，不能直接删除";
			}else{
				boolean result = bossMenuService.delMenuAndRole(menuId);
				if(result){
					errmsg = ErrorEnum.SUCCESS.getMsg();
				}else{
					errcode = ErrorEnum.DEL_ERROR.getCode();
					errmsg = ErrorEnum.DEL_ERROR.getMsg();
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
}
