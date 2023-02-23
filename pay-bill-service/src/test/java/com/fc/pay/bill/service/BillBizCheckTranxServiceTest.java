package com.fc.pay.bill.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.fc.pay.bill.BaseTestng;
import com.fc.pay.bill.business.BillBizEntityBusiness;
import com.fc.pay.bill.business.LocalDataBusiness;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.enums.MistakeHandleWayEnum;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.vo.BillBizCheckProgressData;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.MerchantAppConfig;

public class BillBizCheckTranxServiceTest extends BaseTestng {
	
	private static final Logger log = LoggerFactory
			.getLogger(BillBizCheckTranxServiceTest.class);
	
	@Autowired
	private BillBizEntityBusiness entityBusiness;
	
	@Autowired
	private LocalDataBusiness dataBusiness;
	
	@Autowired
	private BillBizCheckTranxService tranxService;
	
	@Autowired
	private BillBizBatchService batchService;

	@Autowired
	private BillBizMistakeService mistakeService;
	
	@Autowired 
	private BillBizDoubtService doubtService;
	
	@Test
	public void prepareProgressDataTest(){
		Date billDate = DateUtil.addDay(new Date(), -2);
		List<MerchantApp> merchantAppList = dataBusiness.findMerchantAppList();
		List<MerchantAppConfig> macList = dataBusiness.findMerchantAppConfigList();
		BillBizCheckProgressData data = tranxService.prepareProgressData(billDate, merchantAppList, macList);
		log.info("data.summary>>>"+data.getSummary().toString());
		log.info("data.batchList>>>"+data.getBatchList().size());
		log.info("data.fileNotifyList>>>"+data.getFileNotifyList().size());
	}
	
	//@Test
	public void modifyBillBizBatchTest(){
		BillBizBatch billBizBatch = batchService.get(Long.valueOf(1));
		tranxService.modifyBillBizBatch(billBizBatch);
		
	}
	
	//@Test
	public void modifyTradeAmountTest() throws Exception{
		BillBizMistake mistake = mistakeService.get(Long.valueOf(1));
		tranxService.modifyTradeAmount(mistake);
	}
	
	//@Test
	public void handleBatchCheckResultTest(){
		BillBizSummary summary = new BillBizSummary();
		BillBizBatch batch = new BillBizBatch();
		List<BillBizMistake> mistakeList = new ArrayList<BillBizMistake>();
		List<BillBizDoubt> insertDoubtList = new ArrayList<BillBizDoubt>();
		List<BillBizDoubt> removeDoubtList = new ArrayList<BillBizDoubt>();
		List<BillBizItem> bizItemList = new ArrayList<BillBizItem>();
		tranxService.handleBatchCheckResult(summary, batch, mistakeList, insertDoubtList, removeDoubtList, bizItemList);
		
	}
	
	//@Test
	public void handleDoubtInPeriodTest(){
		List<BillBizDoubt> removeDoubtlist = new ArrayList<BillBizDoubt>();
		List<BillBizMistake> insertMistakeList = new ArrayList<BillBizMistake>();
		tranxService.handleDoubtInPeriod(removeDoubtlist, insertMistakeList);
		
	}
	
	//@Test
	public void handleMistakeTest() throws Exception{
		String userName = "zhangsan";
		String mistakeId = "1";
		String handleType = MistakeHandleWayEnum.channel.name();
		String handleRemark = "人工备注";
		tranxService.handleMistake(userName, mistakeId, handleType, handleRemark);
		
	}
	
	//@Test
	public void handleTradeToFailTest(){
		BillBizMistake mistake = mistakeService.get(Long.valueOf(1));
		tranxService.handleTradeToFail(mistake);
		
	}
	
	//@Test
	public void handleTradeToSuccessTest(){
		BillBizMistake mistake = mistakeService.get(Long.valueOf(2));
		tranxService.handleTradeToSuccess(mistake);
		
	}
	
	//@Test
	public void addBillBizBatchTest(){
		BillBizBatch billBizBatch = new BillBizBatch();
		tranxService.addBillBizBatch(billBizBatch);
	}
	
	//@Test
	public void addBillBizBatchListTest(){
		List<BillBizBatch> batchList = new ArrayList<BillBizBatch>();
		tranxService.addBillBizBatchList(batchList );
	}
	
	

}
