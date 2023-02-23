package com.fc.pay.boss.dao;

import java.util.List;
import java.util.Map;

import com.fc.pay.boss.entity.BossPermission;
import com.fc.pay.common.system.mybatis.Page;

/**
 *
 * BossPermission 表数据库控制层接口
 *
 */
public interface BossPermissionMapper extends BaseMapper<BossPermission> {


	List<BossPermission> selectRolePermissionByOperatorId(Long operatorId);
	
	List<BossPermission> selectPermissionByMenuID(Long menuId);
	
	List<BossPermission> selectAllPermis(Map<String,Object> params);
	
	Page selectPageList(Map<String,Object> params);
	
}