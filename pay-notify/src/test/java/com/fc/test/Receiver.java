package com.fc.test;


import java.util.Date;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;


public class Receiver {
	public static void main(String[] args) {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:/spring/activemq.xml");

        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("queueTemplate");
        while(true) {
            Object mm =   jmsTemplate.receiveAndConvert();
            System.out.println("收到消息：" + mm);
        }
    }
}
