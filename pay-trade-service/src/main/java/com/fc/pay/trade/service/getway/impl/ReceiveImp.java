package com.fc.pay.trade.service.getway.impl;

import java.util.Date;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.core.utils.IDHelp;
import com.fc.pay.common.core.utils.JsonUtil;
import com.fc.pay.common.vo.bean.PayOrder;
import com.fc.pay.trade.entity.NotifyRecord;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.getway.IReceive;
import com.fc.pay.trade.service.notify.INotifyRecord;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.fc.pay.trade.utils.Return;

@Service
public class ReceiveImp implements IReceive{
	
	private static Logger logger = LoggerFactory.getLogger(ReceiveImp.class);
	
	@Autowired
	private ITradeRecord iTradeRecord;
	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private INotifyRecord iNotifyRecord;
//	@Autowired
//	private JmsTemplate jmsTemplate;
	
	
	public void mq(NotifyRecord notify){
		try{
//			//写mq
//			jmsTemplate.send("oderSuccess", new MessageCreator(){
//				public Message createMessage(Session session)
//						throws JMSException {
//					return session.createObjectMessage(notify);
//				}
//			});
		}catch(Exception e){
			logger.info(e.getMessage());
		}
	}
	
	@Transactional
	public void alipayReceive(PayPaymentOrder order, String trade_no, String buyer_id, String content){
			
			
			
			Date now = new Date();
			
			PayTradeRecord entity = new PayTradeRecord();
			BeanUtil.copyProperties(entity, order);
			entity.setId(null);
			entity.setCreater("系统");
			entity.setEditor(null);
			entity.setEditTime(null);
			entity.setFinishTime(now);
			entity.setTradeNo(trade_no);
			entity.setBuyerId(buyer_id);
			entity.setNotifiyParam(content);
			iTradeRecord.add(entity);
			
			
			PayPaymentOrder payOrder = new PayPaymentOrder();
			payOrder.setId(order.getId());
			payOrder.setEditor("系统");
			payOrder.setEditTime(new Date());
			payOrder.setTradeNo(trade_no);
			payOrder.setBuyerId(buyer_id);
			payOrder.setFinishTime(now);
			payOrder.setSuccessReason("支付宝通知，状态为成功");
			payOrder.setStatus(TradeStatusEnum.success.name());
			iPaymentOrder.modify(payOrder);
			
			
			//通知参数
			PayOrder view = new PayOrder();
			BeanUtil.copyProperties(view, order);
			view.setStatus(TradeStatusEnum.success.name());
			Map result = BeanUtil.bean2Map(view);
			Return retrun = new Return(result);
			ObjectMapper mapper = new ObjectMapper();
			String request = JsonUtil.toJson(result);
			
			
			NotifyRecord notify = new NotifyRecord();
			BeanUtil.copyProperties(notify, order);
			notify.setPayType("pay");
			notify.setNotifyId(IDHelp.nextId().toString());
			notify.setNotifyType("pay");
			notify.setNotifyTimes(1);
			notify.setNotifyTimesLimit(5);
			notify.setUrl(order.getNotifyUrl());
			notify.setRequest(request);
			notify.setResponse("");
			notify.setStatus(0);
			iNotifyRecord.add(notify);
			
			this.mq(notify);

	}

	

	@Transactional
	public void unionpayReceive(PayPaymentOrder order, String trade_no, String buyer_id, String content) {
		Date now = new Date();
		
		PayTradeRecord entity = new PayTradeRecord();
		BeanUtil.copyProperties(entity, order);
		entity.setId(null);
		entity.setCreater("系统");
		entity.setEditor(null);
		entity.setEditTime(null);
		entity.setFinishTime(now);
		entity.setTradeNo(trade_no);
		entity.setBuyerId(buyer_id);
		entity.setNotifiyParam(content);
		iTradeRecord.add(entity);
		
		
		PayPaymentOrder payOrder = new PayPaymentOrder();
		payOrder.setId(order.getId());
		payOrder.setEditor("系统");
		payOrder.setEditTime(new Date());
		payOrder.setTradeNo(trade_no);
		payOrder.setBuyerId(buyer_id);
		payOrder.setFinishTime(now);
		payOrder.setSuccessReason("银联通知，状态为成功");
		payOrder.setStatus(TradeStatusEnum.success.name());
		iPaymentOrder.modify(payOrder);
		
		
		//通知参数
		PayOrder view = new PayOrder();
		BeanUtil.copyProperties(view, order);
		view.setStatus(TradeStatusEnum.success.name());
		Map result = BeanUtil.bean2Map(view);
		Return retrun = new Return(result);
		ObjectMapper mapper = new ObjectMapper();
		String request = JsonUtil.toJson(result);
		
		
		NotifyRecord notify = new NotifyRecord();
		notify.setPayType("pay");
		BeanUtil.copyProperties(notify, order);
		notify.setNotifyId(IDHelp.nextId().toString());
		notify.setNotifyType("pay");
		notify.setNotifyTimes(1);
		notify.setNotifyTimesLimit(5);
		notify.setUrl(order.getNotifyUrl());
		notify.setRequest(request);
		notify.setStatus(0);
		iNotifyRecord.add(notify);
		
		this.mq(notify);
	}
	
	
	@Transactional
	public void weixinpayReceive(PayPaymentOrder order, String trade_no, String buyer_id, String content){

		Date now = new Date();
		
		PayTradeRecord entity = new PayTradeRecord();
		BeanUtil.copyProperties(entity, order);
		entity.setId(null);
		entity.setCreater("系统");
		entity.setEditor(null);
		entity.setEditTime(null);
		entity.setFinishTime(now);
		entity.setTradeNo(trade_no);
		entity.setBuyerId(buyer_id);
		entity.setNotifiyParam(content);
		iTradeRecord.add(entity);
		
		
		PayPaymentOrder payOrder = new PayPaymentOrder();
		payOrder.setId(order.getId());
		payOrder.setEditor("系统");
		payOrder.setEditTime(new Date());
		payOrder.setTradeNo(trade_no);
		payOrder.setBuyerId(buyer_id);
		payOrder.setFinishTime(now);
		payOrder.setSuccessReason("微信通知，状态为成功");
		payOrder.setStatus(TradeStatusEnum.success.name());
		iPaymentOrder.modify(payOrder);
		
		
		//通知参数
		PayOrder view = new PayOrder();
		BeanUtil.copyProperties(view, order);
		view.setStatus(TradeStatusEnum.success.name());
		Map result = BeanUtil.bean2Map(view);
		Return retrun = new Return(result);
		ObjectMapper mapper = new ObjectMapper();
		String request = JsonUtil.toJson(result);
		
		
		NotifyRecord notify = new NotifyRecord();
		notify.setPayType("pay");
		BeanUtil.copyProperties(notify, order);
		notify.setNotifyId(IDHelp.nextId().toString());
		notify.setNotifyType("pay");
		notify.setNotifyTimes(1);
		notify.setNotifyTimesLimit(5);
		notify.setUrl(order.getNotifyUrl());
		notify.setRequest(request);
		notify.setStatus(0);
		iNotifyRecord.add(notify);
		
		this.mq(notify);
	
	}
}
