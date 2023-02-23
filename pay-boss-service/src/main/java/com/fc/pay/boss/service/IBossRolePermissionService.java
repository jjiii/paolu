package com.fc.pay.boss.service;

import java.util.List;
import java.util.Map;

import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.entity.BossRolePermission;

/**
 *
 * BossRolePermission 表数据服务层接口
 *
 */
public interface IBossRolePermissionService extends IBaseService<BossRolePermission> {

	boolean insertBatchRolePermission(BossOperator operator,Long roleId,Long[] permissionIds,Long[] permisIds);

	List<Long> selectListPermissionIds(Long roleId);
	
	boolean deleteBatchByRoleIdAndPermisId(Map<String,Object> params);
}