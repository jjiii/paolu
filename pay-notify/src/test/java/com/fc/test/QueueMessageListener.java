package com.fc.test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueMessageListener implements MessageListener
{
    @Override
    public void onMessage(Message message)
    {
    	TextMessage textMsg = (TextMessage) message;  
        System.err.println("监听：");  
        try {  
            System.out.println("消息内容是：" + textMsg.getText());  
        } catch (JMSException e) {  
            e.printStackTrace();  
        }  
    }
}
