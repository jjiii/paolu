package com.fc.pay.bill.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账文件通知数据服务接口
 * 
 * @author zhanjq
 *
 */
public interface BillBizFileNotifyService {
	
	public void deleteAll();
	
	/**
	 * 新增
	 */
	int add(BillBizFileNotify fileNotify);
	
	/**
	 * 批量新增
	 * @param list
	 * @return
	 */
	List<BillBizFileNotify> addBatch(List<BillBizFileNotify> list);

	/**
	 * 修改
	 */
	int modify(BillBizFileNotify fileNotify);

	/**
	 * ID查询
	 * @param id
	 * @return
	 */
	BillBizFileNotify get(Long id);
	
	/**
	 * 列表查询
	 * @param paramMap
	 * @return
	 */
	List<BillBizFileNotify> list(Map<String, Object> paramMap);

	/**
	 * 分页查询
	 * @param paramMap
	 * @return
	 */
	public Page page(Map<String, Object> paramMap);
	
	/**
	 * 修改文件域
	 * @param id
	 * @param filePath
	 * @param fileStatus
	 * @param fileRemark
	 * @return
	 */
	int modifyFileField(Long id, String filePath, String fileStatus, String fileRemark);
    
	/**
	 * 修改通知域
	 * @param id
	 * @param notifyUrl
	 * @param notifyStatus
	 * @param notifyRemark
	 * @return
	 */
    int modifyNotifyField(Long id, String notifyUrl, String notifyStatus, String notifyRemark);

    /**
     * 根据账单日期查询文件通知列表
     * @param billDate
     * @return
     */
	List<BillBizFileNotify> listByBillDate(Date billDate);

	/**
	 * 根据条件查询
	 * @param merchantAppCode
	 * @param billDate
	 * @return
	 */
	BillBizFileNotify getBy(String merchantAppCode, Date billDate);



}
