package com.fc.pay.trade.service.trade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.common.core.enums.BillStatusEnum;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.dao.PayPaymentOrderMapper;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;

@Service
public class PaymentOrderImp implements IPaymentOrder {
	@Autowired
	private PayPaymentOrderMapper paymentOrderMapper;

	@Autowired
	private ITradeRecord iTradeRecord;

	@Override
	public int add(PayPaymentOrder record) {
		return paymentOrderMapper.insertSelective(record);
	}

	@Override
	public int delete(Long id) {
		return paymentOrderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int modify(PayPaymentOrder record) {
		return paymentOrderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public PayPaymentOrder get(Long id) {
		return paymentOrderMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page page(Map<String, Object> parm) {
		List list = paymentOrderMapper.selectByMap(parm);
		Page p = (Page) list;
		return p;
	}

	@Override
	public List<PayPaymentOrder> list(Map<String, Object> parm) {
		return paymentOrderMapper.selectByMap(parm);
	}

	@Override
	public List<PayPaymentOrder> listForBill(Map<String, Object> params) {
		return paymentOrderMapper.selectForBill(params);
	}

	@Override
	public PayPaymentOrder get(Map<String, Object> parm) {
		List<PayPaymentOrder> list = paymentOrderMapper.selectByMap(parm);
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public PayPaymentOrder getByMerchantOrderNo(String merchant_order_no) {
		if (StringUtils.isNotBlank(merchant_order_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("merchant_order_no", merchant_order_no);
			return this.get(parm);
		}
		return null;
	}

	@Override
	public PayPaymentOrder getByOrderNo(String order_no) {
		if (StringUtils.isNotBlank(order_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("order_no", order_no);
			return this.get(parm);
		}
		return null;
	}

	@Override
	public PayPaymentOrder getByTradeNo(String trade_no) {
		if (StringUtils.isNotBlank(trade_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("trade_no", trade_no);
			return this.get(parm);
		}
		return null;
	}

	

	@Override
	@Transactional
	public int modifyBillStatusByMap(Map<String, ArrayList<String>> payMap) {
		/***
		 * payMap的KEY包括
		 * 1.正常：BillStatusEnum.normal.name()
		 * 2.存疑：BillStatusEnum.doubt.name()
		 * 3.差错：BillStatusEnum.mistake.name()
		 */
		int updateCount = 0;
		Set<String> billStatusSet = payMap.keySet();
		StringBuffer inCodition = new StringBuffer();
		if(billStatusSet!=null && billStatusSet.size()>0){
			for(String billStatus : billStatusSet){
				ArrayList<String> orderNoList = payMap.get(billStatus);				
				if(orderNoList==null || orderNoList.size()<=0){
					continue;
				}
				//避免in条件过长，每次100条
				int batchSize = 100;
				int orderNoListSize = orderNoList.size();				
				int batchCount = orderNoListSize / batchSize;
				if(orderNoListSize % 100 >0){
					batchCount++;
				}
				//StringBuffer inCodition = new StringBuffer();
				String orderNo = null;
				int start = 0;
				int end = 0;
				for(int i=0; i<batchCount; i++){
					start = i*batchSize;
					end = (i == (batchCount-1)) ? orderNoListSize : start+batchSize;
					for(int j=start; j<end; j++){
						orderNo = orderNoList.get(j);
						inCodition.append(orderNo).append(",");						
					}
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("billStatus", billStatus);
					param.put("orderNoInCondition", inCodition.toString().substring(0, inCodition.length()-1));
					//System.out.println(param.get("orderNoInCondition"));
					updateCount = updateCount + paymentOrderMapper.updateBillStatus(param);
					inCodition.delete(0, inCodition.length());
				}				
			}
		}
		return updateCount;
	}
	
	/**
	public static void main(String[] args){
		Map<String, ArrayList<String>> payMap = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> normalPayOrderNoList = new ArrayList<String>();
		for(int i=0; i<511; i++){
			normalPayOrderNoList.add("100_"+i);
		}
		payMap.put(BillStatusEnum.normal.name(), normalPayOrderNoList);
		
		ArrayList<String> doubtPayOrderNoList = new ArrayList<String>();
		for(int i=0; i<300; i++){
			doubtPayOrderNoList.add("200_"+i);
		}
		payMap.put(BillStatusEnum.doubt.name(), doubtPayOrderNoList);
		
		//payMap.put(BillStatusEnum.mistake.name(), new ArrayList<String>());
		payMap.put(BillStatusEnum.mistake.name(), null);
		
		int updateCount = 0;
		Set<String> billStatusSet = payMap.keySet();
		StringBuffer inCodition = new StringBuffer();
		if(billStatusSet!=null && billStatusSet.size()>0){
			for(String billStatus : billStatusSet){
				System.out.println("====================================="+billStatus);
				ArrayList<String> orderNoList = payMap.get(billStatus);				
				if(orderNoList==null || orderNoList.size()<=0){
					continue;
				}
				//避免in条件过长，每次100条
				int batchSize = 100;
				int orderNoListSize = orderNoList.size();				
				int batchCount = orderNoListSize / batchSize;
				if(orderNoListSize % 100 >0){
					batchCount++;
				}
				//StringBuffer inCodition = new StringBuffer();
				String orderNo = null;
				int start = 0;
				int end = 0;
				for(int i=0; i<batchCount; i++){
					start = i*batchSize;
					end = (i == (batchCount-1)) ? orderNoListSize : start+batchSize;
					for(int j=start; j<end; j++){
						orderNo = orderNoList.get(j);
						inCodition.append(orderNo).append(",");						
					}
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("billStatus", billStatus);
					param.put("orderNoInCondition", inCodition.toString().substring(0, inCodition.length()-1));
					System.out.println(param.get("orderNoInCondition"));
					//updateCount = updateCount + paymentOrderMapper.updateBillStatus(param);
					inCodition.delete(0, inCodition.length());
				}				
			}
		}
		System.out.println(updateCount);
	}
	*/

	@Override
	public int modifyBillStatus(String orderNo, String billStatus) {
		return paymentOrderMapper.updateBillStatusSingle(orderNo, billStatus);
	}

	public static void test(String... fileNames) {
		for (String name : fileNames) {
			System.out.println(name);
		}
	}


	@Transactional
	public Boolean changStatusSuccess(String order_no, Map<String, Object> data) {
		
		
		PayPaymentOrder order = this.getByOrderNo(order_no);
		if (order == null) {
			return false;
		}
		
		if(data.get("trade_no") == null){
			return false;
		}
		Date now = new Date();
		
		PayPaymentOrder record = new PayPaymentOrder();
		record.setId(order.getId());
		record.setTradeNo(data.get("trade_no").toString());
		
		if(data.get("buyer_id") != null){
			record.setBuyerId(data.get("buyer_id").toString());
		}
		record.setEditTime(new Date());
		record.setStatus(TradeStatusEnum.success.name());
		record.setBillStatus(BillStatusEnum.balance.name());
		record.setSuccessReason("对账模块修改支付状态为成功");
		paymentOrderMapper.updateByPrimaryKeySelective(record);
	
		
		PayTradeRecord tradeRecord = new PayTradeRecord();
		BeanUtil.copyProperties(tradeRecord, order);
		tradeRecord.setFinishTime(now);
		tradeRecord.setCreateTime(now);
		tradeRecord.setEditTime(null);
		if(data.get("buyer_id") != null){
			tradeRecord.setBuyerId(data.get("buyer_id").toString());
		}
		tradeRecord.setTradeNo(data.get("trade_no").toString());
		
		//tradeRecord.setNotifiyParam(notifiy_param);
		iTradeRecord.add(tradeRecord);
		return true;
	}

	@Transactional
	public Boolean changStatusFail(String order_no, Map<String, Object> data) {
		PayPaymentOrder order = this.getByOrderNo(order_no);
		if (order == null) {
			return false;
		}
		
		PayPaymentOrder record = new PayPaymentOrder();
		record.setId(order.getId());
		if(data.get("trade_no") != null){
			record.setTradeNo(data.get("trade_no").toString());
		}
		
		if(data.get("buyer_id") != null){
			record.setBuyerId(data.get("buyer_id").toString());
		}
		record.setEditTime(new Date());
		record.setStatus(TradeStatusEnum.close.name());
		record.setBillStatus(BillStatusEnum.balance.name());
		record.setSuccessReason("对账模块修改支付状态为失败");
		paymentOrderMapper.updateByPrimaryKeySelective(record);
	
		PayTradeRecord tradeRecord = iTradeRecord.getFristByOrderNo(order_no);
		if( tradeRecord != null ){
			iTradeRecord.delete(tradeRecord.getId());
		}
		
		return true;
	}

	@Transactional
	public Boolean changAmount(String order_no, BigDecimal amount, Map<String, Object> data) {

		
		PayPaymentOrder order = this.getByOrderNo(order_no);
		if (order == null) {
			return false;
		}
		
		PayPaymentOrder record = new PayPaymentOrder();
		record.setId(order.getId());
		record.setAmount(amount);
		record.setEditTime(new Date());
		record.setBillStatus(BillStatusEnum.balance.name());
		record.setRemark("对账模块修改支付订单金额，不修改状态");
		paymentOrderMapper.updateByPrimaryKeySelective(record);
	
		PayTradeRecord trade = iTradeRecord.getFristByOrderNo(order_no);
		if( trade != null ){
			PayTradeRecord tradeRecord = new PayTradeRecord();
			tradeRecord.setId(trade.getId());
			tradeRecord.setAmount(amount);
			tradeRecord.setRemark("对账模块修改支付订单金额，不修改状态");
			iTradeRecord.modify(tradeRecord);
		}
		return true;
		
	}



	@Override
	public Page pageByMap(Map<String, Object> params) {
		Page page = new Page();
		page.setPageSize(Integer.valueOf(params.get("size").toString()));
		page.setCurrNum(Integer.valueOf(params.get("current").toString()));
		params.put("page", page);
		return paymentOrderMapper.pageByMap(params);
	}

}
