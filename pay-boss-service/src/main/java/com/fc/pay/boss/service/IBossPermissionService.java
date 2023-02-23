package com.fc.pay.boss.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fc.pay.boss.entity.BossPermission;
import com.fc.pay.common.system.mybatis.Page;

/**
 *
 * BossPermission 表数据服务层接口
 *
 */
public interface IBossPermissionService extends IBaseService<BossPermission> {
	
	
	/**
	 * 查询指定操作员的角色权限关联
	 * @param operatorId
	 * @return
	 */
	Set<String> selectRolePermissionByOperatorId(Long operatorId);
	
	/**
	 * 按菜单ID查询权限信息
	 * @param menuId
	 * @return
	 */
	List<BossPermission> selectPermissionByMenuID(Long menuId);
	
	/**
	 * 查询所有权限,并把指定角色拥有的权限置于勾选状态
	 * @param params
	 * @return
	 */
	List<BossPermission> selectAllPermisWithChecked(Map<String,Object> params,Long roleId);
	
	/**
	 * 查询分页数据
	 * @param current
	 * @param size
	 * @param params
	 * @return
	 */
	Page selectPermissionsWithPage(int current,int size,Map<String,Object> params);

	/**
	 * 删除权限及权限与角色的关联
	 * @param permissionId
	 * @return
	 */
	boolean delPermissionAndRole(Long permissionId);
	
	/**
	 * 新增权限，并将此权限授权给超级管理员
	 * @param permission
	 * @return
	 */
	boolean insertAndAsignAdmin(BossPermission permission);

}