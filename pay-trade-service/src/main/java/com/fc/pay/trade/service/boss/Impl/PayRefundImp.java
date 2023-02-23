package com.fc.pay.trade.service.boss.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.service.boss.IPayRefund;
import com.fc.pay.trade.service.trade.IRefundOrder;

@Service
public class PayRefundImp implements IPayRefund {
	@Autowired
	private IRefundOrder IRefundOrder;

	@Override
	public int add(PayRefundOrder record) {
		return IRefundOrder.add(record);
	}

	@Override
	public int delete(Long id) {
		return IRefundOrder.delete(id);
	}

	@Override
	public int modify(PayRefundOrder record) {
		return IRefundOrder.modify(record);
	}

	@Override
	public PayRefundOrder get(Long id) {
		return IRefundOrder.get(id);
	}

	@Override
	public Page<PayRefundOrder> page(Map<String, Object> parm, int current, int size) {
		
		Page<PayRefundOrder> outPage = new Page<PayRefundOrder>(current, size);

		com.fc.pay.common.system.mybatis.Page page = new com.fc.pay.common.system.mybatis.Page();
		page.setCurrNum(current);
		page.setPageSize(size);
		parm.put("page", page);
		page = IRefundOrder.page(parm);

		if (page == null) {
			return outPage;
		}
		List list = new ArrayList<PayRefundOrder>();
		list.addAll(page);
		outPage.setRecords(list);

		return outPage;
	}

}
