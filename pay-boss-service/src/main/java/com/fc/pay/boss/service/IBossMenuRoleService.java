package com.fc.pay.boss.service;

import java.util.List;

import com.fc.pay.boss.entity.BossMenuRole;
import com.fc.pay.boss.entity.BossOperator;

/**
 *
 * BossMenuRole 表数据服务层接口
 *
 */
public interface IBossMenuRoleService extends IBaseService<BossMenuRole> {

	boolean assignRoleMenu(BossOperator operator,Long roleId,String menuIds);
	
	List<Long> selectListMenuIds(Long roleId);
	
}