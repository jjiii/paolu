package com.fc.pay.boss.service;

import java.util.List;
import java.util.Set;

import com.fc.pay.boss.entity.BossRole;

/**
 *
 * BossRole 表数据服务层接口
 *
 */
public interface IBossRoleService extends IBaseService<BossRole> {


	Set<String> selectRoleOperatorByOperatorId(Long operatorId);
	
	List<BossRole> selectRoleByPermisId(Long permissionId);
}