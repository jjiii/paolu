package com.fc.pay.boss.service;

import java.util.List;

import com.fc.pay.boss.entity.BossMenu;

/**
 *
 * BossMenu 表数据服务层接口
 *
 */
public interface IBossMenuService extends IBaseService<BossMenu> {

	List<BossMenu> findMenuByOperatorId(Long operatorId);
	
	List<BossMenu> findMenuWithPermission();
	
	List<BossMenu> selectAllMenus();
	
	boolean delMenuAndRole(Long menuId);
	
	boolean insertAndAsignAdmin(BossMenu menu);
}