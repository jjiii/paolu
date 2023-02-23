package com.fc.pay.boss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.boss.dao.BossPermissionMapper;
import com.fc.pay.boss.dao.BossRolePermissionMapper;
import com.fc.pay.boss.entity.BossPermission;
import com.fc.pay.boss.entity.BossRolePermission;
import com.fc.pay.boss.enums.StatusEnum;
import com.fc.pay.boss.service.IBossPermissionService;
import com.fc.pay.common.system.mybatis.Page;

/**
 *
 * BossPermission 表数据服务层接口实现类
 *
 */
@Service
public class BossPermissionServiceImpl extends BaseServiceImpl<BossPermissionMapper, BossPermission> implements IBossPermissionService {

	@Autowired
	private BossRolePermissionMapper rolePermissionMapper;
	/**
	 * 取当前用户的所有权限
	 */
	@Override
	public Set<String> selectRolePermissionByOperatorId(Long operatorId) {
		Set<String> result = new HashSet<>();
		List<BossPermission> permissions = baseMapper.selectRolePermissionByOperatorId(operatorId);
		if(permissions!=null && permissions.size()>0){
			for(BossPermission permission : permissions){
				result.add(permission.getPermission());
			}
		}
		return result;
	}

	/**
	 * 根据菜单ID查找权限
	 */
	@Override
	public List<BossPermission> selectPermissionByMenuID(Long menuId) {
		return baseMapper.selectPermissionByMenuID(menuId);
	}
	
	/**
	 * 查询所有权限，并把指定角色下的权限置于勾选状态
	 */
	@Override
	public List<BossPermission> selectAllPermisWithChecked(Map<String, Object> params,Long roleId) {
		List<BossPermission> allPermissions = baseMapper.selectAllPermis(params);
		List<Long> chekedRole = rolePermissionMapper.selectListPermissionIds(roleId);
		for(Long pid : chekedRole){
			for(BossPermission permis : allPermissions){
				if(pid == permis.getId()){
					permis.setIschecked(true);
				}
			}
		}
		return allPermissions;
	}	

	@Override
	public Page selectPermissionsWithPage(int current,int size,Map<String,Object> params) {
		Page page = new Page();
		page.setPageSize(size);
		page.setCurrNum(current);
		params.put("page", page);
		return baseMapper.selectPageList(params);
	}
	
	/**
	 * 删除权限及权限与角色的关联
	 * @param permissionId
	 * @return
	 */
	@Override
	@Transactional
	public boolean delPermissionAndRole(Long permissionId){
		boolean result = false;
		result = baseMapper.deleteById(permissionId)>0;
		if(result){
			// 删除该用户关联的角色
			Map<String,Object> params = new HashMap<>();
			params.put("permissionId", permissionId);
			rolePermissionMapper.deleteByMap(params);
		}
		return result;
	}
	
	/**
	 * 新增权限，并将此权限授权给超级管理员
	 * @param permission
	 * @return
	 */
	@Override
	@Transactional
	public boolean insertAndAsignAdmin(BossPermission permission){
		boolean result = false;
		permission.setCreateTime(new Date());
		permission.setVersion(0L);
		permission.setStatus(StatusEnum.ACTIVE.name());
		result = this.insert(permission);
		// 分配菜单给超级管理员
		if(result){
			List<BossRolePermission> bossRolePermissions = new ArrayList<>();
			BossRolePermission bossRolePermission = new BossRolePermission();
			bossRolePermission.setRoleId(1L);
			bossRolePermission.setPermissionId(permission.getId());
			bossRolePermissions.add(bossRolePermission);
			result = rolePermissionMapper.insertBatchRolePermission(bossRolePermissions)>0;
		}
		return result;
	}

}