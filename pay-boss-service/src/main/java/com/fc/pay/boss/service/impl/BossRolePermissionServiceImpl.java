package com.fc.pay.boss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.boss.dao.BossRolePermissionMapper;
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.entity.BossRolePermission;
import com.fc.pay.boss.service.IBossRolePermissionService;

/**
 *
 * BossRolePermission 表数据服务层接口实现类
 *
 */
@Service
public class BossRolePermissionServiceImpl extends BaseServiceImpl<BossRolePermissionMapper, BossRolePermission> implements IBossRolePermissionService {

	/**
	 * 批量插入权限分配信息
	 */
	@Override
	@Transactional
	public boolean insertBatchRolePermission(BossOperator operator, Long roleId, Long[] permissionIds,Long[] permisIds) {
		int result = 0;
		List<BossRolePermission> bossRolePermissions = new ArrayList<>();
		// 删除此角色下的旧关联的权限
		BossRolePermission rolePermission = new BossRolePermission();
		rolePermission.setRoleId(roleId);
		
		if(permisIds != null && permisIds.length>0){
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("roleId", roleId);
			params.put("permisIds", permisIds);
			result = baseMapper.deleteBatchByRoleIdAndPermisId(params);
		}
		
		if(permissionIds != null && permissionIds.length>0){
			// 重新封装权限数据
			for(long pid : permissionIds){
				BossRolePermission bossRolePermission = new BossRolePermission();
				bossRolePermission.setCreater(operator.getRealName());
				bossRolePermission.setCreateTime(new Date());
				bossRolePermission.setRoleId(roleId);
				bossRolePermission.setPermissionId(pid);
				bossRolePermissions.add(bossRolePermission);
			}
			result = baseMapper.insertBatchRolePermission(bossRolePermissions);
		}
		return result>0;
	}
	
	/**
	 * 查询角色下的权限
	 */
	public List<Long> selectListPermissionIds(Long roleId){
		
		return baseMapper.selectListPermissionIds(roleId);
	}

	/**
	 * 删除指定角色下的多个权限
	 */
	@Override
	public boolean deleteBatchByRoleIdAndPermisId(Map<String, Object> params) {
		return baseMapper.deleteBatchByRoleIdAndPermisId(params)>0;
	}



}