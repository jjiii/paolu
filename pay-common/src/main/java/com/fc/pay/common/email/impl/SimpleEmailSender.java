package com.fc.pay.common.email.impl;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.common.email.EmailSender;

@Component("simpleEmailSender")
public class SimpleEmailSender implements EmailSender {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleEmailSender.class);

	private static Properties properties = new Properties();

	static {
		try {
			properties.load(SimpleEmailSender.class.getClassLoader().getResourceAsStream("properties/email_config.properties"));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void sendEmail(String subject, String text)  throws Exception {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding(PayConstants.UTF8_ENCODING);
		//mailSender.setProtocol(properties.getProperty("email.protocol"));
		mailSender.setHost(properties.getProperty("email.host"));
		mailSender.setPort(Integer.parseInt(properties.getProperty("email.port")));
		//默认系统属性
		//Properties mailProperties = new Properties();
		//mailSender.setJavaMailProperties(mailProperties);
		mailSender.setUsername(properties.getProperty("email.username"));
		mailSender.setPassword(properties.getProperty("email.password"));
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(mailSender.getUsername());
		mail.setTo(properties.getProperty("email.to"));
		mail.setSubject(subject);
		mail.setText(text);

		mailSender.send(mail);
	}
	
	public static void main(String[] args) throws Exception {
		
		/*
		String subject = "测试主题";
		String text = "这是一封测试邮件，具体内容都在这里了";
		new SimpleEmailSender().sendEmail(subject, text);
		*/
//		BillBizSummary summary = new BillBizSummary();
//		summary.setBillDate(new Date());
//		summary.setBatchCount(3);
//		summary.setBatchRunSuccessCount(2);
//		summary.setMerchantAppCount(2);
//		summary.setBillMakeSuccessCount(2);
//		summary.setDownloadNotifySuccessCount(2);
//		
//		String billDateStr = DateUtil.makeHyphenDateFormat(summary.getBillDate());
//		String subject = "枫车支付业务日对账报告["+billDateStr+"]";
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("账单日期["+billDateStr+"], ");
//		buffer.append("对账批次失败个数["+(summary.getBatchCount() - summary.getBatchRunSuccessCount())+"], ");
//		buffer.append("生成账单失败个数["+(summary.getMerchantAppCount() - summary.getBillMakeSuccessCount())+"], ");
//		buffer.append("通知下载失败个数["+(summary.getMerchantAppCount() - summary.getDownloadNotifySuccessCount())+"]");
//		new SimpleEmailSender().sendEmail(subject, buffer.toString());

	}

}
