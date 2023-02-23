package com.fc.pay.boss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.boss.dao.BossMenuRoleMapper;
import com.fc.pay.boss.entity.BossMenuRole;
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.service.IBossMenuRoleService;

/**
 *
 * BossMenuRole 表数据服务层接口实现类
 *
 */
@Service
public class BossMenuRoleServiceImpl extends BaseServiceImpl<BossMenuRoleMapper, BossMenuRole> implements IBossMenuRoleService {

	/**
	 * 分配菜单
	 */
	@Transactional
	public boolean assignRoleMenu(BossOperator operator,Long roleId,String menuIds){
		int result = 0;
		// 删除此角色下的旧关联的菜单
		Map<String,Object> params = new HashMap<>();
		params.put("roleId", roleId);
		result = baseMapper.deleteByMap(params);
		
		if(StringUtils.isNotBlank(menuIds)){
			String[] menus = menuIds.split(",");
			// 重新分配菜单
			List<BossMenuRole> bossMenuRoles = new ArrayList<BossMenuRole>();
			for(String menuId : menus){
				BossMenuRole menuRole = new BossMenuRole();
				menuRole.setRoleId(roleId);
				menuRole.setMenuId(Long.parseLong(menuId));
				menuRole.setCreater(operator.getLoginName());
				menuRole.setCreateTime(new Date());
				bossMenuRoles.add(menuRole);
			}
			result = baseMapper.insertBatchMenuRole(bossMenuRoles);
		}
		
		return result>0;
	}

	/**
	 * 查询当前角色下所有关联的菜单
	 */
	@Override
	public List<Long> selectListMenuIds(Long roleId) {
		return baseMapper.selectListMenuIds(roleId);
	}

}