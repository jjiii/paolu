package com.fc.pay.boss.dao;

import java.util.List;

import com.fc.pay.boss.entity.BossRoleOperator;

/**
 *
 * BossRoleOperator 表数据库控制层接口
 *
 */
public interface BossRoleOperatorMapper extends BaseMapper<BossRoleOperator> {


	public int insertBatchRecord(List<BossRoleOperator> bossRoleOperators);
}