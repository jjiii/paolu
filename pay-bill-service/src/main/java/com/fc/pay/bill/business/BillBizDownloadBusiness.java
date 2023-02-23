package com.fc.pay.bill.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fc.pay.bill.download.OutsideDailyBillDownload;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.utils.BillUtil;

/**
 * 业务对账单下载业务
 * 
 * @author zhanjq
 *
 */
@Service("billBizDownloadBusiness")
public class BillBizDownloadBusiness{
	
	private static final Logger log = LoggerFactory.getLogger(BillBizDownloadBusiness.class);
	
	private static final int DOWNLOAD_TRY_TIMES = BillUtil.getBillDownloadRetryTimes();
	
	@Autowired
	private BeanFactory beanFactory;
	
	/**
	 * 请求下载对账文件 .
	 * @param batch
	 * @return
	 */
	public void downloadOutsideBizBill(BillBizBatch batch) throws Exception {
		log.info("支付渠道[" + batch.getPayChannel() + "],进入下载业务对账文件操作>>>");		
		String downloadComponentName = batch.getPayChannel()+"DailyBillDownload";
		OutsideDailyBillDownload download = (OutsideDailyBillDownload) beanFactory.getBean(downloadComponentName);
		int downloadTrytimes = 0;
		// 默认尝试三次
		while (downloadTrytimes < DOWNLOAD_TRY_TIMES) {
			downloadTrytimes++;
			try {
				download.downloadBizBill(batch);
				break;
			} catch (Exception e) {
				//e.printStackTrace();
				if(downloadTrytimes==DOWNLOAD_TRY_TIMES){
					batch.setHandleStatus(BatchStatusEnum.fail.name());
					batch.setHandleRemark("BillBizDownloadBusiness>>>"+downloadComponentName+">>>"+e.getMessage());
					throw e;
				}
				log.error("本次账单下载失败，休眠一秒，再次尝试");
				Thread.sleep(10000);				
				continue;
			}	
		}
		//log.info("业务日账单下载状态>>>"+batch.getHandleStatus()+"  状态描述>>>"+batch.getHandleRemark());
	}

}
