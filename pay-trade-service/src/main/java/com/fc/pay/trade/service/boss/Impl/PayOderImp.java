package com.fc.pay.trade.service.boss.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.service.boss.IPayOder;
import com.fc.pay.trade.service.trade.IPaymentOrder;
@Service
public class PayOderImp implements IPayOder{
	@Autowired
	private IPaymentOrder iPaymentOrder;
	
	
	@Override
	public int add(PayPaymentOrder entity) {
		return iPaymentOrder.add(entity);
	}

	@Override
	public int delete(Long id) {
		return iPaymentOrder.delete(id);
	}

	@Override
	public int modify(PayPaymentOrder entity) {
		return iPaymentOrder.modify(entity);
	}

	@Override
	public PayPaymentOrder get(Long id) {
		return iPaymentOrder.get(id);
	}

	@Override
	public Page<PayPaymentOrder> page(Map<String, Object> parm, int current, int size) {
		Page<PayPaymentOrder> outPage = new Page<PayPaymentOrder>(current, size);
		
		com.fc.pay.common.system.mybatis.Page page = new com.fc.pay.common.system.mybatis.Page();
		page.setCurrNum(current);
		page.setPageSize(size);
		parm.put("page", page);
		page = iPaymentOrder.page(parm);
		
		if(page == null){
			return outPage;
		}
		List list = new ArrayList<PayPaymentOrder>();
		list.addAll(page);
		outPage.setRecords(list);
		
		return outPage;
	}

}
