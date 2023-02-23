package com.fc.pay.boss.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fc.pay.boss.dao.BossRoleMapper;
import com.fc.pay.boss.entity.BossRole;
import com.fc.pay.boss.service.IBossRoleService;

/**
 *
 * BossRole 表数据服务层接口实现类
 *
 */
@Service
public class BossRoleServiceImpl extends BaseServiceImpl<BossRoleMapper, BossRole> implements IBossRoleService {

	/**
	 * 获取当前用户的角色
	 */
	@Override
	public Set<String> selectRoleOperatorByOperatorId(Long operatorId) {
		Set<String> result = new HashSet<>();
		List<BossRole> bossRoles = baseMapper.selectRoleOperatorByOperatorId(operatorId);
		if(bossRoles!=null && bossRoles.size()>0){
			for(BossRole bossRole : bossRoles){
				result.add(bossRole.getRoleCode());
			}
		}
		return result;
	}

	/**
	 * 获取指定权限下关联的角色
	 */
	@Override
	public List<BossRole> selectRoleByPermisId(Long permissionId) {
		return baseMapper.selectRoleByPermisId(permissionId);
	}
	
}