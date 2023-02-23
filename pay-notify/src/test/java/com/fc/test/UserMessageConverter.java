package com.fc.test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.pay.trade.entity.Admin;

public class UserMessageConverter implements MessageConverter{
	private ObjectMapper mapper = new ObjectMapper();
    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException
    {
        String json = null;
        try
        {
            json = mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        System.out.println("toMessage : " + json);
        return session.createTextMessage(json);
    }
    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException
    {
        String json = ((TextMessage) message).getText();
        Object object = null;
        try
        {
            object = mapper.readValue(json, Admin.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("fromMessage : " + object);
        return object;
    }
}
