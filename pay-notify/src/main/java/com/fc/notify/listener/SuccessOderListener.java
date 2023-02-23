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
/**
 * 监听到消息并尝试处理后，无论成功或者失败，spring都会自动确认，并从mq删除该消息~
 * 如果想改用手动ACK的方式你得继续学习了,我木有时间学习
 * 实现过程：
 * 1、线程 SentOKNotify 负责消费mq队列消息：即发送http并更新数据库状态
 * 2、线程 SentOKNotify 消费能力：最大的时间为2s,即如果2s处理不完，会自动断开http链接
 * 3、线程池 poolTaskExecutor养了5条SentOKNotify这样的线程，线程池队列queueCapacity值满后，会提高到10条线程来处理，再超过就抛异常了
 * 4、即最糟糕的情况是：
 * 		每条线程都需要2s才处理消费完一条消息，即每分钟能处理150条消息
 * 		如果还继续有消息来到，在不断丢消息的情况下，每分钟处理300条消息
 * 		即：生产消息者，每5分钟可以发送1500条消息，单个消费都可以承担
 */
@Component
public class SuccessOderListener implements SessionAwareMessageListener<ObjectMessage> {
	@Autowired
	private ThreadPoolTaskExecutor poolTaskExecutor;
	@Autowired
	private INotifyRecord iNotifyRecord;

	public void onMessage(ObjectMessage message, Session session) throws JMSException {
		
		try{
			System.err.println("接收：");
	        NotifyRecord record = (NotifyRecord)message.getObject();
	        SuccessOrderNotify sn = new SuccessOrderNotify(record, iNotifyRecord);
	        poolTaskExecutor.execute(sn);
    	}catch(Exception e){
    		e.printStackTrace();
    		return;
    	}
	}

}
