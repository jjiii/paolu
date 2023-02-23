package com.fc.pay.boss.service;

import java.util.List;

import com.fc.pay.boss.dao.BossOperatorMapper;
import com.fc.pay.boss.entity.BossOperator;

/**
 *
 * BossOperator 表数据服务层接口
 *
 */
public interface IBossOperatorService extends IBaseService<BossOperator> {

	BossOperatorMapper getBossOperatorMapper();
	
	boolean insertOperatorAndRole(BossOperator bossOperator,Long[] rolds);
	
	boolean updateOperatorAndRole(BossOperator bossOperator,Long[] roleIds);
	
	boolean delOperatorAndRole(Long operatorId);
	
	List<BossOperator> selectOperatorByRoleId(Long roleId);
	
	BossOperator selectByName(String loginName);

}