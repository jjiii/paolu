package com.fc.pay.bill.service;

import java.util.List;
import java.util.Map;

import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账差错数据服务接口
 * 
 * @author zhanjq
 *
 */
public interface BillBizMistakeService {
	
	public void deleteAll();
	
	/**
	 * 新增
	 */
	public int add(BillBizMistake mistake);
	
	/**
	 * 批量新增
	 * @param mistakeList
	 */
	public int addBatch(List<BillBizMistake> list);

	/**
	 * 修改
	 */
	public int modify(BillBizMistake mistake);

	/**
	 * ID查询
	 * @param id
	 * @return
	 */
	public BillBizMistake get(Long id);
	
	/**
	 * 列表查询
	 * @param paramMap
	 * @return
	 */
	public List<BillBizMistake> list(Map<String, Object> paramMap);

	/**
	 * 分页查询
	 * @param paramMap
	 * @return
	 */
	public Page page(Map<String, Object> paramMap);
	
	/**
	 * Boss系统查询分页数据
	 * @param paramMap
	 * @return
	 */
	Page selectPageList(Map<String, Object> paramMap,Integer current,Integer pagesize);

	/**
	 * 判断是否不止一个差错
	 * @param type
	 * @param orderNo
	 * @return
	 */
	public boolean hasMoreMistakeToHandle(String type, String orderNo);

}
