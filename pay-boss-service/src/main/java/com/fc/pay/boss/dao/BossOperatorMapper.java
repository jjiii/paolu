package com.fc.pay.boss.dao;

import java.util.List;

import com.fc.pay.boss.entity.BossOperator;

/**
 *
 * BossOperator 表数据库控制层接口
 *
 */
public interface BossOperatorMapper extends BaseMapper<BossOperator> {

	List<BossOperator> selectOperatorByRoleId(Long roleId);
	
	BossOperator selectByName(String loginName);
}