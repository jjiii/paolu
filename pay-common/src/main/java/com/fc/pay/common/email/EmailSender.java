package com.fc.pay.common.email;

public interface EmailSender {
	
	public void sendEmail(String subject, String text) throws Exception;

}
