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
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.service.BillBizSummaryService;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;


/**
 * 业务日对账作业
 * 
 * @author zhanjq
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class BillBizDailyCheckMainJob extends QuartzJobBean {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizDailyCheckMainJob.class);
	
	@Autowired
	private BillBizCheckCoreBusiness billCheckBusiness;
	
	@Autowired
	private BillBizSummaryService summaryService;

	@Override
	protected void executeInternal(JobExecutionContext context)	throws JobExecutionException {
		try {
			//日志记录开始标记， LogConfig.BACKGROUND,"BusinessMonitorTask"是定位信息
			//LogUtil.logStart(log, LogConfig.BACKGROUND,"BusinessMonitorTask");
			LogUtil.logStart(log, "payBill",BillBizDailyCheckMainJob.class.getSimpleName());
			/**
			log.info("BusinessMonitorTask start detection");
			int i=1/0;
			statisticBefore();
			statistic();
			*/
			log.info("业务日对账作业开始>>>");
			
			/** 查找需要对账的账单日期 */
			List<String> toCheckBillDateList = findToCheckBillDateList();
			
			try {
				//Date billDate = DateUtil.addDay(new Date(), - Integer.valueOf(BillUtil.readBillPeriod().trim()));
				//billCheckBusiness.checkBiz(billDate);
				/***/
				for(String billDate : toCheckBillDateList){
					log.info("业务日对账-账单日期=>"+billDate);
					billCheckBusiness.checkBiz(DateUtil.parseDefaultDateContent(billDate));					
				}
				/***/
				/** 
				//mock test start				
				long sleepSecond = Math.abs((new Random().nextInt(3)+1) * 100000);
				System.out.println("休眠"+sleepSecond+"毫秒");
				Thread.sleep(sleepSecond);
				//mock test end
				*/
			} catch (Exception e) {
				log.error("业务日对账定时作业任务异常:"+e.getMessage());
			}		
			log.info("<<<业务日对账作业结束");				
		} catch (Exception e) {
			log.error(BillBizDailyCheckMainJob.class.getSimpleName()+" execute failure , reason: {}", e.getMessage(), e);
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
		boolean isFirstRun = false;
		if(maxHistoryBillDate!=null){
			log.info("最大对账日期:"+DateUtil.makeDefaultDateFormat(maxHistoryBillDate));	
		}else{
			isFirstRun = true;
			log.info("首次运行，不存在历史账单日期");	
		}
				
		Date newestBillDate = DateUtil.addDay(new Date(), -billPeriod);
		//非首次运行
		if(!isFirstRun){
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
			}else{
				log.info("不存在断档情况");
			}
		}else{
			log.info("首次运行，加入首个账单日期");
			toCheckBillDateSet.add(DateUtil.makeDefaultDateFormat(newestBillDate));
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
