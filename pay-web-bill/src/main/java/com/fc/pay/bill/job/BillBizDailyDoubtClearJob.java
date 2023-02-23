package com.fc.pay.bill.job;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.fc.log.client.util.LogUtil;
import com.fc.pay.bill.business.BillBizCheckCoreBusiness;
import com.fc.pay.bill.business.BillBizDoubtBusiness;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.service.BillBizBatchService;
import com.fc.pay.bill.service.BillBizSummaryService;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;

/**
 * 对账存疑清理作业
 * 
 * @author zhanjq
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class BillBizDailyDoubtClearJob extends QuartzJobBean {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizDailyDoubtClearJob.class);
	
	@Autowired
	private BillBizSummaryService summaryService;
	
	/**
	 * 对账存疑缓存组件
	 */
	@Autowired
	private BillBizDoubtBusiness doubtBusiness;
	

	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		try {
			//日志记录开始标记， LogConfig.BACKGROUND,"BusinessMonitorTask"是定位信息
			//LogUtil.logStart(log, LogConfig.BACKGROUND,"BusinessMonitorTask");
			LogUtil.logStart(log, "payBill",BillBizDailyDoubtClearJob.class.getSimpleName());
			log.info("对账存疑清理作业开始>>>");			
			try {
				/** 查找跑批未完成的对账汇总 */
				/** 考虑到不存在交易的情况支付宝会返回账单不存在，暂时不检查此项。
				List<BillBizSummary> batchFailSummaryList = summaryService.listByBatchCheckFail();
				if(batchFailSummaryList!=null && !batchFailSummaryList.isEmpty()){
					log.info("对账没有全部完成,不允许进行存疑清理工作");
					return;
				}
				*/
				/** 确定存疑清理日期 */
				int doubtClearPeriod = Integer.valueOf(BillUtil.readDoubtClearPeriod().trim());
				Date maxBillDate = summaryService.findMaxBillDate();
				if(maxBillDate!=null){
					log.info("最大对账日期:"+DateUtil.makeDefaultDateFormat(maxBillDate));
					doubtBusiness.handleExpireDoubtData(maxBillDate, doubtClearPeriod);	
				}else{
					log.info("缺少对账记录，无需执行对账存疑清理工作.");
				}
							
			} catch (Exception e) {
				log.error("对账存疑清理作业任务异常:"+e.getMessage());
			}
			log.info("<<<对账存疑清理作业结束");				
		} catch (Exception e) {
			log.error(BillBizDailyDoubtClearJob.class.getSimpleName()+" execute failure , reason: {}", e.getMessage(), e);
		} finally {
			LogUtil.logEnd(log);//日志记录结束标记
		}		
		
	}
	
	/**
	 * 查找对账日期列表(账单日期按升序排列)
	 * 包含：
	 * 1.往期对账失败的账单日期
	 * 2.系统停机断档的对账日期
	 * 3.当日索要运行的对账日期
	 * @return
	 */
	public List<String> findToCheckBillDateList(){
		Set<String> toCheckBillDateSet = new HashSet<String>();
		int billPeriod = Integer.valueOf(BillUtil.readBillPeriod().trim());
		/** 查找跑批未完成的对账汇总 */
		List<BillBizSummary> batchFailSummaryList = summaryService.listByBatchCheckFail();
		if(batchFailSummaryList!=null && !batchFailSummaryList.isEmpty()){
			log.info("跑批未完成记录总数:"+batchFailSummaryList.size());
			for(BillBizSummary summary : batchFailSummaryList){
				log.info(DateUtil.makeDefaultDateFormat(summary.getBillDate()));
				toCheckBillDateSet.add(DateUtil.makeDefaultDateFormat(summary.getBillDate()));
			}
		}else{
			log.info("不存在跑批未完成的情况");
		}			
		/** 查找断档的对账汇总（系统停机） */
		Date maxHistoryBillDate = summaryService.findMaxBillDate();
		log.info("最大对账日期:"+DateUtil.makeDefaultDateFormat(maxHistoryBillDate));			
		Date newestBillDate = DateUtil.addDay(new Date(), -billPeriod);
		//存在断档情况
		if(!DateUtils.isSameDay(maxHistoryBillDate, newestBillDate)){
			/** 存在断档， */
			//toCheckBillDateSet.add(DateUtil.makeDefaultDateFormat(newestBillDate));
			//填补日期空挡
			int intervalDays = DateUtil.intervalDays(maxHistoryBillDate, newestBillDate);
			if(intervalDays>0){
				/**
				for(int i=1; i<=intervalDays; i++){
					toCheckBillDateSet.add(DateUtil.makeDefaultDateFormat(DateUtil.addDay(maxHistoryBillDate, i)));
				}
				*/
				log.info("intervalDays取值"+intervalDays);
				for(int i=billPeriod; i<=intervalDays;){
					toCheckBillDateSet.add(DateUtil.makeDefaultDateFormat(DateUtil.addDay(maxHistoryBillDate, i)));
					i=i+billPeriod;
					//log.info("i取值"+i);
				}
			}
		}
		
		List<String> toCheckBillDateList = new ArrayList<String>(toCheckBillDateSet);
		Collections.sort(toCheckBillDateList);
		
		log.info("打印账单日期列表");
		for(String billDate : toCheckBillDateList){
			log.info(billDate);
		}
		
		return toCheckBillDateList;
	}

}
