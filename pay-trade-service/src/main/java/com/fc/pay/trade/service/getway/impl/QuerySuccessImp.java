package com.fc.pay.trade.service.getway.impl;

import java.util.Date;
import java.util.Map;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.core.utils.IDHelp;
import com.fc.pay.common.core.utils.JsonUtil;
import com.fc.pay.common.pay.alipay.AliResponse;
import com.fc.pay.common.vo.bean.PayOrder;
import com.fc.pay.trade.entity.NotifyRecord;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.getway.IQuerySuccess;
import com.fc.pay.trade.service.notify.INotifyRecord;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.fc.pay.trade.utils.Return;
@Service
public class QuerySuccessImp implements IQuerySuccess{

	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private ITradeRecord iTradeRecord;
	@Autowired
	private INotifyRecord iNotifyRecord;
	
	@Transactional
	public void payQuery_aliPay(PayPaymentOrder order, JsonObject json){
		
		
		String trade_no=  AliResponse.alipay_trade_query_response.trade_no(json);
		String buyer_user_id = AliResponse.alipay_trade_query_response.buyer_user_id(json);
		Date now = new Date();
		
		PayTradeRecord entity = new PayTradeRecord();
		BeanUtil.copyProperties(entity, order);
		entity.setId(null);
		entity.setCreater("系统");
		entity.setEditor(null);
		entity.setEditTime(null);
		entity.setFinishTime(now);
		entity.setTradeNo(trade_no);
		entity.setBuyerId(buyer_user_id);
		entity.setNotifiyParam(json.toString());
		iTradeRecord.add(entity);
		
		
		PayPaymentOrder payOrder = new PayPaymentOrder();
		payOrder.setId(order.getId());
		payOrder.setEditor("系统");
		payOrder.setEditTime(new Date());
		payOrder.setTradeNo(trade_no);
		payOrder.setBuyerId(buyer_user_id);
		payOrder.setFinishTime(now);
		payOrder.setSuccessReason("查询支付宝接口，状态为成功");
		payOrder.setStatus(TradeStatusEnum.success.name());
		iPaymentOrder.modify(payOrder);
		
		
		//通知参数
		PayOrder view = new PayOrder();
		BeanUtil.copyProperties(view, order);
		view.setStatus(TradeStatusEnum.success.name());
		Map result = BeanUtil.bean2Map(view);
		Return retrun = new Return(result);
		String request = JsonUtil.toJson(retrun);

		
		NotifyRecord notify = new NotifyRecord();
		BeanUtil.copyProperties(notify, order);
		notify.setPayType("pay");
		notify.setNotifyType("pay");
		notify.setNotifyId(IDHelp.nextId().toString());
		notify.setNotifyTimes(1);
		notify.setNotifyTimesLimit(5);
		notify.setUrl(order.getNotifyUrl());
		notify.setRequest(request);
		notify.setResponse("");
		notify.setStatus(0);
		iNotifyRecord.add(notify);
		
	}
	
	public void payQuery_union(PayPaymentOrder order, Map<String, String> rspData){
		String trade_no= rspData.get("queryId");
		String buyer_user_id = "";
		Date now = new Date();
		
		PayTradeRecord entity = new PayTradeRecord();
		BeanUtil.copyProperties(entity, order);
		entity.setId(null);
		entity.setCreater("系统");
		entity.setEditor(null);
		entity.setEditTime(null);
		entity.setFinishTime(now);
		entity.setTradeNo(trade_no);
		entity.setBuyerId(buyer_user_id);
		entity.setNotifiyParam(rspData.toString());
		iTradeRecord.add(entity);
		
		PayPaymentOrder payOrder = new PayPaymentOrder();
		payOrder.setId(order.getId());
		payOrder.setEditor("系统");
		payOrder.setEditTime(new Date());
		payOrder.setTradeNo(trade_no);
		payOrder.setBuyerId(buyer_user_id);
		payOrder.setFinishTime(now);
		payOrder.setSuccessReason("查询银联支付接口，状态为成功");
		payOrder.setStatus(TradeStatusEnum.success.name());
		iPaymentOrder.modify(payOrder);
		
		
		//通知参数
		PayOrder view = new PayOrder();
		BeanUtil.copyProperties(view, order);
		view.setStatus(TradeStatusEnum.success.name());
		Map result = BeanUtil.bean2Map(view);
		Return retrun = new Return(result);
		String request = JsonUtil.toJson(retrun);
		
		NotifyRecord notify = new NotifyRecord();
		BeanUtil.copyProperties(notify, order);
		notify.setPayType("pay");
		notify.setNotifyType("pay");
		notify.setNotifyId(IDHelp.nextId().toString());
		notify.setNotifyTimes(1);
		notify.setNotifyTimesLimit(5);
		notify.setUrl(order.getNotifyUrl());
		notify.setRequest(request);
		notify.setResponse("");
		notify.setStatus(0);
		iNotifyRecord.add(notify);
	}

}
