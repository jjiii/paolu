package com.fc.notify.thread;

import org.apache.http.client.fluent.Request;

import com.fc.pay.common.core.enums.NotifyStatusEnum;
import com.fc.pay.trade.entity.NotifyRecord;
import com.fc.pay.trade.service.notify.INotifyRecord;

public class SuccessRefundNotify extends Thread {
	private NotifyRecord notifyRecord;
	private INotifyRecord iNotifyRecord;
	
	public SuccessRefundNotify(NotifyRecord notifyRecord, INotifyRecord iNotifyRecord){
		this.notifyRecord = notifyRecord;
		this.iNotifyRecord = iNotifyRecord;
	}
	
	public void run() {
		
		System.out.println("发送http线程执行...." + Thread.currentThread().toString());
		
		Integer return_code = 0;
		try{
			return_code = Request.Get(notifyRecord.getUrl())
			        .connectTimeout(2000)
			        .socketTimeout(2000)
			        .execute()
			        .returnResponse().getStatusLine().getStatusCode();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		NotifyRecord entity = new NotifyRecord();
		entity.setId(notifyRecord.getId());
		entity.setNotifyTimesLimit(notifyRecord.getNotifyTimesLimit()+1);
		if(return_code == 200){
			entity.setStatus(NotifyStatusEnum.OK.value());
		}
		iNotifyRecord.modify(entity);
		
	}
}
