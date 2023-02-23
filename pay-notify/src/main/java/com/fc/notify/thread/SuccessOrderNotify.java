package com.fc.notify.thread;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fc.pay.common.core.enums.NotifyStatusEnum;
import com.fc.pay.trade.entity.NotifyRecord;
import com.fc.pay.trade.service.notify.INotifyRecord;

/**
 * 该线程用于给商户发送成功的http通知
 * 流程：
 * 1、发送http
 * 2、更新通知次数,成功状态
 * @author XDou
 */
public class SuccessOrderNotify extends Thread {
	
	private static Logger logger = LoggerFactory.getLogger(SuccessOrderNotify.class);
	
	private NotifyRecord notifyRecord;
	private INotifyRecord iNotifyRecord;
	
	public SuccessOrderNotify(NotifyRecord notifyRecord, INotifyRecord iNotifyRecord){
		this.notifyRecord = notifyRecord;
		this.iNotifyRecord = iNotifyRecord;
	}
	
	public void run() {
		
		Integer return_code = 0;
		try{
			return_code = 	Request.Post(notifyRecord.getUrl())
					        .connectTimeout(2000)
					        .socketTimeout(2000)
							.bodyString(notifyRecord.getRequest(), ContentType.APPLICATION_JSON)
							.execute()
					        .returnResponse().getStatusLine().getStatusCode();
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		System.err.println("支付成功通知线程执行，返回码为："+ return_code);
		
		NotifyRecord entity = new NotifyRecord();
		entity.setId(notifyRecord.getId());
		entity.setNotifyTimesLimit(notifyRecord.getNotifyTimes()+1);
		if(return_code == 200){
			entity.setStatus(NotifyStatusEnum.OK.value());
		}
		iNotifyRecord.modify(entity);
		
	}
	
}