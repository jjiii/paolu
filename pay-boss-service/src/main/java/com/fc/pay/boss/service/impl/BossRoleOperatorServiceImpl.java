package com.fc.pay.boss.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fc.pay.boss.dao.BossRoleOperatorMapper;
import com.fc.pay.boss.entity.BossRoleOperator;
import com.fc.pay.boss.service.IBossRoleOperatorService;

/**
 *
 * BossRoleOperator 表数据服务层接口实现类
 *
 */
@Service
public class BossRoleOperatorServiceImpl extends BaseServiceImpl<BossRoleOperatorMapper, BossRoleOperator> implements IBossRoleOperatorService {

	@Override
	public boolean insertBatchRecord(List<BossRoleOperator> bossRoleOperators) {
		return baseMapper.insertBatchRecord(bossRoleOperators)>0;
	}


}