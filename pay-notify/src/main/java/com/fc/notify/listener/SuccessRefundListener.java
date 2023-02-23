package com.fc.notify.listener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.fc.notify.thread.SuccessOrderNotify;
import com.fc.pay.trade.entity.NotifyRecord;
import com.fc.pay.trade.service.notify.INotifyRecord;

@Component
public class SuccessRefundListener implements SessionAwareMessageListener<ObjectMessage>{
	@Autowired
	private ThreadPoolTaskExecutor poolTaskExecutor;
	@Autowired
	private INotifyRecord iNotifyRecord;

	public void onMessage(ObjectMessage message, Session session) throws JMSException {
		try{
	        NotifyRecord record = (NotifyRecord)message.getObject();
	        SuccessOrderNotify sn = new SuccessOrderNotify(record, iNotifyRecord);
	        poolTaskExecutor.execute(sn);
    	}catch(Exception e){
    		e.printStackTrace();
    		return;
    	}
	}
}
