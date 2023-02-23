//package com.fc.notify.service;
//
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.Session;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
//
//import com.fc.pay.trade.entity.NotifyRecord;
//import com.fc.pay.trade.service.getway.impl.ReceiveImp;
//
//public class SuccessReceive {
//	private static Logger logger = LoggerFactory.getLogger(SuccessReceive.class);
//	
//	@Autowired
//	private JmsTemplate jmsTemplate;
//	
//	public void mq(NotifyRecord notify){
//		try{
//			//写mq
//			jmsTemplate.send("oderSuccess", new MessageCreator(){
//				public Message createMessage(Session session)
//						throws JMSException {
//					return session.createObjectMessage(notify);
//				}
//			});
//		}catch(Exception e){
//			logger.info(e.getMessage());
//		}
//	}
//}
