package com.fc.notify.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.fc.pay.trade.entity.NotifyRecord;
import com.fc.pay.trade.service.notify.INotifyRecord;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class SuccessRefundJob extends QuartzJobBean {

	@Autowired
	private INotifyRecord iNotifyRecord;
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		System.err.println("任务2:SuccessRefundJob:");
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("notify_times", 4);
		parm.put("status", 0);
		parm.put("pay_type", "refund");
		parm.put("limit", 1000);
		
		List<NotifyRecord> list = iNotifyRecord.list(parm);
		for(NotifyRecord record : list){
			
			jmsTemplate.send("refundSuccess", new MessageCreator(){
				public Message createMessage(Session session)
						throws JMSException {
					return session.createObjectMessage(record);
				}
			});
		}
		
	}
	
}
