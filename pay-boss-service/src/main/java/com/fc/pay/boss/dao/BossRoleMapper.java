package com.fc.pay.boss.dao;

import java.util.List;

import com.fc.pay.boss.entity.BossRole;

/**
 *
 * BossRole 表数据库控制层接口
 *
 */
public interface BossRoleMapper extends BaseMapper<BossRole> {


	List<BossRole> selectRoleOperatorByOperatorId(Long operatorId);
	
	List<BossRole> selectRoleByPermisId(Long permissionId);
}