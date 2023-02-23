package com.fc.test;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.fc.notify.thread.SuccessOrderNotify;
import com.fc.pay.trade.entity.NotifyRecord;
import com.fc.pay.trade.service.notify.INotifyRecord;
/**
 *  spring监听mq消息类
 *  
 *  TextMessage转换为String对象
 *	BytesMessage转换为byte数组
 *	MapMessage转换为Map对象
 *	ObjectMessage转换为对应的Serializable对象
 */
public class MessageListener {
	
	@Autowired
	private ThreadPoolTaskExecutor poolTaskExecutor;
	@Autowired 
	private INotifyRecord iNotifyRecord;
	
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
	 * 		即：生产消息者，每4分钟可以发送1200条消息，单个消费都可以承担
	 */
    public void handleMessage(Serializable message){
    	try{
	        NotifyRecord record = (NotifyRecord)message;
	        SuccessOrderNotify sn = new SuccessOrderNotify(record, iNotifyRecord);
	        poolTaskExecutor.execute(sn);
    	}catch(Exception e){
    		return;
    	}
    	
    }
    
    
    
    
    
    
    
    
//   public void handleMessage(String message){
//		 System.out.println("监听：String:"+message+","+Thread.currentThread().getName());  
//   }
//   
//   public void handleMessage(Map message){
//       System.out.println("监听：Map:"+message+","+Thread.currentThread().getName());  
//   }
//   
//   public void handleMessage(byte[] message){
//   	System.out.println("监听：byte[]:"+message+","+Thread.currentThread().getName()); 
//   }
}
