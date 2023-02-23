package com.fc.pay.bill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.common.system.mybatis.Page;

public interface BillBizFileNotifyMapper {
	
	void deleteAll();
	
    int deleteByPrimaryKey(Long id);

    int insert(BillBizFileNotify record);

    int insertSelective(BillBizFileNotify record);

    BillBizFileNotify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillBizFileNotify record);

    int updateByPrimaryKey(BillBizFileNotify record);
    
    int updateFileField(@Param("id") Long id, @Param("filePath") String filePath, @Param("fileStatus") String fileStatus, @Param("fileRemark") String fileRemark);
    
    int updateNotifyField(@Param("id") Long id, @Param("notifyUrl") String notifyUrl, @Param("notifyStatus") String notifyStatus, @Param("notifyRemark") String notifyRemark);
    
    int insertBatch(List<BillBizFileNotify> list);
    
	List<BillBizFileNotify> selectAllByMap(Map<String, Object> paramMap);

	Page selectPageByMap(Map<String, Object> paramMap);

	List<BillBizFileNotify> selectAllByBillDate(Date billDate);

	BillBizFileNotify selectByMerchantAppCodeBillDate(@Param("merchantAppCode") String merchantAppCode, @Param("billDate") Date billDate);

    
}