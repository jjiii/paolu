package com.fc.pay.bill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.common.system.mybatis.Page;

public interface BillBizSummaryMapper {
	
	void deleteAll();
	
    int deleteByPrimaryKey(Long id);

    int insert(BillBizSummary record);

    int insertSelective(BillBizSummary record);

    BillBizSummary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillBizSummary record);

    int updateByPrimaryKey(BillBizSummary record);
    
    int updateBatchRunSuccessCount(@Param("summaryId") Long summaryId, @Param("batchRunSuccessCount") Integer batchRunSuccessCount);
    
    int updateBillMakeSuccessCount(@Param("summaryId") Long summaryId, @Param("billMakeSuccessCount") Integer billMakeSuccessCount);
    
    int updateDownloadNotifySuccessCount(@Param("summaryId") Long summaryId, @Param("downloadNotifySuccessCount") Integer downloadNotifySuccessCount);

	BillBizSummary selectByBillDate(@Param("billDate") Date billDate);
	
	List<BillBizSummary> selectAllByMap(Map<String, Object> paramMap);

	Page selectPageByMap(Map<String, Object> paramMap);
	
	Page selectPageForBoss(Map<String, Object> paramMap);

	List<BillBizSummary> selectByBatchCheckFail();

	Date findMaxBillDate();
    
}