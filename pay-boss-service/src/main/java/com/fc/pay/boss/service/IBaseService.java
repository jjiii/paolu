package com.fc.pay.boss.service;

import java.util.List;
import java.util.Map;

import com.fc.pay.common.system.mybatis.Page;

public interface IBaseService<T> {
	
	/**
	 * 插入有值的字段
	 * @param entity
	 * @return
	 */
	boolean insert(T entity );
	
	/**
	 * 更新有值的字段
	 * @param entity
	 * @return
	 */
	boolean updateSelectiveById(T entity);
	
	/**
	 * 按主键删除记录
	 * @param id
	 * @return
	 */
	boolean deleteById( Long id );
	
	/**
	 * 按主键删除记录
	 * @param id
	 * @return
	 */
	boolean deleteByMap( Map<String,Object> params );
	
	/**
	 * 按主键查询记录
	 * @param id
	 * @return
	 */
	T selectById( Long id );
	
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
	Page selectPageList(Map<String,Object> params,int current,int size);

}
