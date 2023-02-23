package com.fc.pay.boss.service;

import java.util.List;

import com.fc.pay.boss.entity.BossRoleOperator;

/**
 *
 * BossRoleOperator 表数据服务层接口
 *
 */
public interface IBossRoleOperatorService extends IBaseService<BossRoleOperator> {

	public boolean insertBatchRecord(List<BossRoleOperator> bossRoleOperators);
}