package com.fc.pay.boss.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fc.pay.common.system.mybatis.Page;

public interface BaseMapper<T>{

	/**
	 * 插入有值的字段
	 * @param entity
	 * @return
	 */
	int insertSelective(T entity );
	
	/**
	 * 更新有值的字段
	 * @param entity
	 * @return
	 */
	int updateSelectiveById(T entity);
	
	/**
	 * 按主键删除记录
	 * @param id
	 * @return
	 */
	int deleteById( Serializable id );
	
	/**
	 * 按指定参数删除记录
	 * @param params
	 * @return
	 */
	int deleteByMap(Map<String,Object> params);
	
	/**
	 * 按主键查询记录
	 * @param id
	 * @return
	 */
	T selectById( Serializable id );
	
	/**
	 * 按条件统计记录总数
	 * @param params
	 * @return
	 */
	int selectCount(Map<String,Object> params);
	
	/**
	 * 查询列表数据
	 * @param params
	 * @return
	 */
	List<T> selectList(Map<String,Object> params);
	
	/**
	 * 查询分页数据
	 * @param params
	 * @return
	 */
	Page selectPageList(Map<String,Object> params);
}
