package com.fc.pay.boss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.boss.dao.BaseMapper;
import com.fc.pay.boss.service.IBaseService;
import com.fc.pay.common.system.mybatis.Page;

public class BaseServiceImpl<M extends BaseMapper<T>,T> implements IBaseService<T> {
	
	@Autowired
	protected M baseMapper;

	@Transactional
	@Override
	public boolean insert(T entity) {
		return baseMapper.insertSelective(entity)>0;
	}

	@Transactional
	@Override
	public boolean updateSelectiveById(T entity) {
		
		return baseMapper.updateSelectiveById(entity)>0;
	}

	@Transactional
	@Override
	public boolean deleteById(Long id) {
		return baseMapper.deleteById(id)>0;
	}
	
	@Transactional
	@Override
	public boolean deleteByMap(Map<String, Object> params) {
		return baseMapper.deleteByMap(params)>0;
	}	

	@Override
	public T selectById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public int selectCount(Map<String, Object> params) {
		
		return baseMapper.selectCount(params);
	}

	@Override
	public List<T> selectList(Map<String, Object> params) {
		return baseMapper.selectList(params);
	}
	
	@Override
	public Page selectPageList(Map<String, Object> params,int current,int size) {
		Page page = new Page();
		page.setPageSize(size);
		page.setCurrNum(current);
		params.put("page", page);
		return baseMapper.selectPageList(params);
	}

}
