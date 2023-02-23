package com.fc.pay.bill.business;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.enums.BillTypeEnum;

public class BillBizDoubtBusinessTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizDoubtBusinessTest.class);
	
	@Autowired
	private BillBizDoubtBusiness billBizDoubtBusiness;
	
	@Test
	public void handleExpireDoubtDataTest(){		
		int doubleClearPeriod = 3;		
		billBizDoubtBusiness.handleExpireDoubtData(new Date(), doubleClearPeriod);		
	}
	
	@Test
	public void findDoubtInPeriodListTest(){
		String billType = BillTypeEnum.refund.name();
		List<BillBizDoubt> doubtList = billBizDoubtBusiness.findDoubtInPeriodList(billType);
		log.debug("查询存疑记录总数>>>"+(doubtList!=null?doubtList.size():0));
		if(doubtList==null){
			return;
		}
		for(BillBizDoubt doubt : doubtList){
			log.debug("存疑记录>>>"+doubt.toString());
		}
	}

}
