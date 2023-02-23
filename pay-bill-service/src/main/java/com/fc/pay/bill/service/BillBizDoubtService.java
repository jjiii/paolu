package com.fc.pay.bill.service;

import java.util.List;
import java.util.Map;

import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账存疑数据服务接口
 * 
 * @author zhanjq
 *
 */
public interface BillBizDoubtService {
	
	public void deleteAll();
	
	/**
	 * 新增
	 */
	int add(BillBizDoubt doubt);

	/**
	 * 批量新增
	 * @param list
	 * @return
	 */
	public int addBatch(List<BillBizDoubt> list);
	
	/**
	 * 批量删除
	 * @param list
	 */
	int deleteBatch(List<BillBizDoubt> list);

	/**
	 * 修改
	 */
	int modify(BillBizDoubt doubt);

	/**
	 * ID查询
	 * @param id
	 * @return
	 */
	BillBizDoubt get(Long id);
	
	/**
	 * 列表查询
	 * @param paramMap
	 * @return
	 */
	List<BillBizDoubt> list(Map<String, Object> paramMap);

	/**
	 * 分页查询
	 * @param paramMap
	 * @return
	 */
	public Page page(Map<String, Object> paramMap);

	/**
	 * 查找过期记录
	 * @param expireDate
	 * @return
	 */
	List<BillBizDoubt> listByExpireDate(String expireDate);

	/**
	 * 查询缓存器内的存疑记录
	 * @param billType
	 * @return
	 */
	List<BillBizDoubt> listInPeriodByBillType(String billType);
	
	/**
	 * Boss系统查询分页数据
	 * @param paramMap
	 * @return
	 */
	Page selectPageList(Map<String, Object> paramMap,Integer current,Integer pagesize);

}
