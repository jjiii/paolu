package com.fc.pay.boss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.boss.dao.BossMenuMapper;
import com.fc.pay.boss.dao.BossMenuRoleMapper;
import com.fc.pay.boss.entity.BossMenu;
import com.fc.pay.boss.entity.BossMenuRole;
import com.fc.pay.boss.service.IBossMenuService;

/**
 *
 * BossMenu 表数据服务层接口实现类
 *
 */
@Service
public class BossMenuServiceImpl extends BaseServiceImpl<BossMenuMapper, BossMenu> implements IBossMenuService {

	@Autowired
	private BossMenuRoleMapper bossMenuRoleMapper;
	
	@Override
	public List<BossMenu> findMenuByOperatorId(Long operatorId) {
		return this.baseMapper.findMenuByOperatorId(operatorId);
	}

	/**
	 * 查询带权限的菜单
	 */
	@Override
	public List<BossMenu> findMenuWithPermission() {
		return baseMapper.findMenuWithPermission();
	}

	/**
	 * 查询所有菜单
	 */
	@Override
	public List<BossMenu> selectAllMenus() {
		return baseMapper.selectAllMenus();
	}

	/**
	 * 删除菜单及菜单关联的角色
	 */
	@Override
	@Transactional
	public boolean delMenuAndRole(Long menuId){
		// 删除用户
		int result = baseMapper.deleteById(menuId);
		// 删除该用户关联的角色
		Map<String,Object> params = new HashMap<>();
		params.put("menuId", menuId);
		bossMenuRoleMapper.deleteByMap(params);
		
		return result > 0;
	}
	
	/**
	 * 新增菜单，并将此菜单授权给超级管理员
	 * @param menu
	 * @return
	 */
	@Override
	@Transactional
	public boolean insertAndAsignAdmin(BossMenu menu){
		boolean result = false;
		menu.setCreateTime(new Date());
		//menu.setStatus("ACTIVE");
		menu.setVersion(0L);
		if(menu.getParentId()==null){
			menu.setParentId(0L);
			menu.setIsLeaf("NO");
			menu.setLevel(1);
		}else{
			menu.setIsLeaf("YES");
			menu.setLevel(2);
		}
		result = this.insert(menu);
		// 分配菜单给超级管理员
		if(result){
			List<BossMenuRole> bossMenuRoles = new ArrayList<>();
			BossMenuRole bossMenuRole = new BossMenuRole();
			bossMenuRole.setRoleId(1L);
			bossMenuRole.setMenuId(menu.getId());
			bossMenuRoles.add(bossMenuRole);
			result = bossMenuRoleMapper.insertBatchMenuRole(bossMenuRoles)>0;
		}
		return result;
	}
}