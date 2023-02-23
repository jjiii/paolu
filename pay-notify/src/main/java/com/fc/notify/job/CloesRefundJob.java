package com.fc.notify.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.fc.pay.common.core.enums.TradeStatusEnum;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class CloesRefundJob extends QuartzJobBean{
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		System.err.println("任务4:CloesRefundJob:");
		Map<String,Object> parm = new HashMap<String,Object>();
		parm.put("status",TradeStatusEnum.pay_wait.name());
		parm.put("time_expire",DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
	}
}
