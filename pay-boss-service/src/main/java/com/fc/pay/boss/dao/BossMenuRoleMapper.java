package com.fc.pay.boss.dao;

import java.util.List;

import com.fc.pay.boss.entity.BossMenuRole;

/**
 *
 * BossMenuRole 表数据库控制层接口
 *
 */
public interface BossMenuRoleMapper extends BaseMapper<BossMenuRole> {

	int insertBatchMenuRole(List<BossMenuRole> bossMenuRoles);
	
	List<Long> selectListMenuIds(Long roleId);
}