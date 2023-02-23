package com.fc.pay.boss.dao;

import java.util.List;
import java.util.Map;

import com.fc.pay.boss.entity.BossRolePermission;

/**
 *
 * BossRolePermission 表数据库控制层接口
 *
 */
public interface BossRolePermissionMapper extends BaseMapper<BossRolePermission> {

	int insertBatchRolePermission(List<BossRolePermission> bossRolePermissions);
	
	List<Long> selectListPermissionIds(Long roleId);
	
	int deleteBatchByRoleIdAndPermisId(Map<String,Object> params);

}