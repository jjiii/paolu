package com.fc.pay.boss.dao;

import java.util.List;

import com.fc.pay.boss.entity.BossMenu;

/**
 *
 * BossMenu 表数据库控制层接口
 *
 */
public interface BossMenuMapper extends BaseMapper<BossMenu> {

	List<BossMenu> findMenuByOperatorId(Long operatorId);
	
	List<BossMenu> findMenuWithPermission();
	
	List<BossMenu> selectAllMenus();

}