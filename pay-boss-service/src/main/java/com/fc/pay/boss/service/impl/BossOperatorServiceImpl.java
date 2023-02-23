package com.fc.pay.boss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.boss.dao.BossOperatorMapper;
import com.fc.pay.boss.dao.BossRoleOperatorMapper;
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.entity.BossRoleOperator;
import com.fc.pay.boss.service.IBossOperatorService;

/**
 *
 * BossOperator 表数据服务层接口实现类
 *
 */
@Service
public class BossOperatorServiceImpl extends BaseServiceImpl<BossOperatorMapper, BossOperator> implements IBossOperatorService {
	
	@Autowired
	private BossRoleOperatorMapper bossRoldOperatorMapper;
	
	public BossOperatorMapper getBossOperatorMapper(){
		return super.baseMapper;
	}

	@Override
	@Transactional
	public boolean insertOperatorAndRole(BossOperator bossOperator,Long[] roleIds) {
		int result = baseMapper.insertSelective(bossOperator);
		// 保存角色信息
		if(roleIds != null && roleIds.length>0){
			List<BossRoleOperator> bossRoleOperators = new ArrayList<BossRoleOperator>();
			for(long rid : roleIds){
				BossRoleOperator bro = new BossRoleOperator();
				bro.setId(0L);
				bro.setOperatorId(bossOperator.getId());
				bro.setRoleId(rid);
				bro.setCreater(bossOperator.getCreater());
				bro.setCreateTime(bossOperator.getCreateTime());
				bossRoleOperators.add(bro);
			}
			bossRoldOperatorMapper.insertBatchRecord(bossRoleOperators);
		}
		
		return result>0;
	}
	
	@Override
	@Transactional
	public boolean updateOperatorAndRole(BossOperator bossOperator,Long[] roleIds) {
		int result = baseMapper.updateSelectiveById(bossOperator);
		// 非超级管理员才可能做此删除操作
		if(bossOperator.getType()==1){
			// 删除该用户原有的绑定的所有角色
			Map<String,Object> params = new HashMap<>();
			params.put("operatorId", bossOperator.getId());
			bossRoldOperatorMapper.deleteByMap(params);
		}
		
		if(roleIds != null && roleIds.length>0){
			List<BossRoleOperator> bossRoleOperators = new ArrayList<BossRoleOperator>();
			// 创建新选择的角色列表
			for(long rid : roleIds){
				BossRoleOperator bro = new BossRoleOperator();
				bro.setId(0L);
				bro.setOperatorId(bossOperator.getId());
				bro.setRoleId(rid);
				bro.setCreater(bossOperator.getEditor());
				bro.setCreateTime(bossOperator.getEditTime());
				bossRoleOperators.add(bro);
			}
			// 保存选择的角色
			bossRoldOperatorMapper.insertBatchRecord(bossRoleOperators);
		}
		
		return result>0;
	}
	
	/**
	 * 删除用户及用户关联的角色
	 */
	@Override
	@Transactional
	public boolean delOperatorAndRole(Long operatorId){
		// 删除用户
		int result = baseMapper.deleteById(operatorId);
		// 删除该用户关联的角色
		Map<String,Object> params = new HashMap<>();
		params.put("operatorId", operatorId);
		bossRoldOperatorMapper.deleteByMap(params);
		
		return result > 0;
	}

	/**
	 * 按角色ID查询用户
	 */
	@Override
	public List<BossOperator> selectOperatorByRoleId(Long roleId) {
		
		return baseMapper.selectOperatorByRoleId(roleId);
	}

	/**
	 * 按名登录名查询用户
	 */
	@Override
	public BossOperator selectByName(String loginName) {
		return baseMapper.selectByName(loginName);
	}
}