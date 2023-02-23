package com.fc.pay.trade.service.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.dao.PayTradeRecordMapper;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.trade.ITradeRecord;

@Service
public class TradeRecordImp implements ITradeRecord{
	@Autowired
	private PayTradeRecordMapper tradeRecordMapper;
	
	@Override
	public int add(PayTradeRecord record) {
		return tradeRecordMapper.insertSelective(record);
	}

	@Override
	public int delete(Long id) {
		return tradeRecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int modify(PayTradeRecord record) {
		return tradeRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public PayTradeRecord get(Long id) {
		return tradeRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page page(Map<String, Object> parm) {
		return null;
	}

	@Override
	public List<PayTradeRecord> list(Map<String, Object> parm) {
		return tradeRecordMapper.selectByMap(parm);
	}
	
	
	public PayTradeRecord get(Map<String, Object> parm){
		List<PayTradeRecord> list = tradeRecordMapper.selectByMap(parm);
		if(list==null || list.size()<1){
			return null;
		}
		return list.get(0);
	}

	
	public List<PayTradeRecord> getByMerchantOrderNo(String merchant_order_no) {
		if (StringUtils.isNotBlank(merchant_order_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("merchant_order_no", merchant_order_no);
			return this.list(parm);
		}
		return null;
	}
	
	public PayTradeRecord getByMerchantOrderNo(String merchant_order_no, String channel, String merchant_app_code){
		
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("merchant_order_no", merchant_order_no);
		parm.put("channel", channel);
		parm.put("merchant_app_code", merchant_app_code);
		
		parm.put("refund_no", "0");
		return this.get(parm);
	}
	
	
	public List<PayTradeRecord> getByOrderNo(String order_no){
		if (StringUtils.isNotBlank(order_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("order_no", order_no);
			return this.list(parm);
		}
		return null;
	}
	public List<PayTradeRecord> getByTradeNo(String trade_no){
		if (StringUtils.isNotBlank(trade_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("trade_no", trade_no);
			return this.list(parm);
		}
		return null;
	}
	
	public PayTradeRecord getFristByMerchantOrderNo(String merchant_order_no){
		if (StringUtils.isNotBlank(merchant_order_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("merchant_order_no", merchant_order_no);
			parm.put("refund_no", "0");
			return this.get(parm);
		}
		return null;
	}
	public PayTradeRecord getFristByOrderNo(String order_no){
		if (StringUtils.isNotBlank(order_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("order_no", order_no);
			parm.put("refund_no", "0");
			return this.get(parm);
		}
		return null;
	}
	
	
	
	
	
	
	
	@Override
	public PayTradeRecord getByRefundNo(String refund_no) {
		if (StringUtils.isNotBlank(refund_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("refund_no", refund_no);
			return this.get(parm);
		}
		return null;
	}

	@Override
	public PayTradeRecord getByRefundMerchantNo(String refund_merchant_no) {
		if (StringUtils.isNotBlank(refund_merchant_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("refund_merchant_no", refund_merchant_no);
			return this.get(parm);
		}
		return null;
	}
	
	
	
}
