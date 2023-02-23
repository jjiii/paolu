package com.fc.pay.bill.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.service.BillBizCheckTranxService;
import com.fc.pay.bill.service.BillBizDoubtService;
import com.fc.pay.bill.utils.DateUtil;

/**
 * 业务对账存疑缓冲业务
 * 
 * @author zhanjq
 *
 */
@Service("billBizDoubtBusiness")
public class BillBizDoubtBusiness {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizDoubtBusiness.class);
	
	/**
	 * 业务日对账数据制作
	 */
	@Autowired
	private BillBizEntityBusiness entityBusiness;
	
	/**
	 * 业务日对账存疑服务
	 */
	@Autowired
	private BillBizDoubtService doubtService;
	
	
	/**
	 * 业务日对账数据事务服务
	 */
	@Autowired
	private BillBizCheckTranxService tranxService;
	
	/**
	 * 查找符合条件的存疑记录
	 * @param billType
	 * @return
	 */
	public List<BillBizDoubt> findDoubtInPeriodList(String billType){
		return doubtService.listInPeriodByBillType(billType);
	}
	
	/**
	 * 处理到期的存疑数据
	 * 提取存疑为错误之后，删除存疑
	 * 
	 */
	/**
	public void handleExpireDoubtData(int doubleClearPeriod) {
		String expireDate = DateUtil.makeDefaultDateFormat(DateUtil.addDay(new Date(), -doubleClearPeriod));
		// 查询是否有创建时间是三天前的数据
		List<BillBizDoubt> doubtList = doubtService.listByExpireDate(expireDate);
		List<BillBizMistake> mistakeList = null;
		// 如果有数据
		if (!doubtList.isEmpty()) {
			mistakeList = new ArrayList<BillBizMistake>();
			for (BillBizDoubt doubt : doubtList) {			    
				mistakeList.add(entityBusiness.makeMistakeInPeriod(doubt));
			}
			tranxService.handleDoubtInPeriod(doubtList, mistakeList);
		}
	}
	*/
	
	/**
	 * 清理指定账单日期往期存疑数据
	 * @param billDate
	 * @param doubleClearPeriod
	 */
	public void handleExpireDoubtData(Date billDate, int doubleClearPeriod) {
		String expireDate = DateUtil.makeDefaultDateFormat(DateUtil.addDay(billDate, -doubleClearPeriod));
		log.info("打印边界日期，"+expireDate+"，注意：createTime小于边界日期的存疑记录认定为过期的存疑记录，可转化为channel_miss差错，且删除这些过期的存疑记录:");
		// 查询是否有创建时间是三天前的数据
		List<BillBizDoubt> doubtList = doubtService.listByExpireDate(expireDate);
		List<BillBizMistake> mistakeList = null;
		// 如果有数据
		if (!doubtList.isEmpty()) {
			mistakeList = new ArrayList<BillBizMistake>();
			for (BillBizDoubt doubt : doubtList) {
				mistakeList.add(entityBusiness.makeMistakeInPeriod(doubt));
			}
			tranxService.handleDoubtInPeriod(doubtList, mistakeList);
		}
	}


}
