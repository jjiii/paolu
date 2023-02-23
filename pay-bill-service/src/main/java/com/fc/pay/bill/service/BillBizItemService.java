package com.fc.pay.bill.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.common.system.mybatis.Page;

/**
 * 业务对账明细数据服务接口
 * 
 * @author zhanjq
 *
 */
public interface BillBizItemService {
	
	public void deleteAll();
	
	/**
	 * 新增
	 */
	int add(BillBizItem item);

	/**
	 * 批量新增
	 * @param list
	 * @return
	 */
	public int addBatch(List<BillBizItem> list);

	/**
	 * 修改
	 */
	int modify(BillBizItem item);

	/**
	 * ID查询
	 * @param id
	 * @return
	 */
	BillBizItem get(Long id);
	
	/**
	 * 列表查询
	 * @param paramMap
	 * @return
	 */
	List<BillBizItem> list(Map<String, Object> paramMap);

	/**
	 * 分页查询
	 * @param paramMap
	 * @return
	 */
	public Page page(Map<String, Object> paramMap);

	/**
	 * 调整支付金额
	 * @param payOrderNo	支付订单号
	 * @param payAmount		支付金额
	 */
	public int modifyPayAmount(String payOrderNo, BigDecimal payAmount);
	
	/**
	 * 调整退款金额
	 * @param refundOrderNo	退款订单号
	 * @param refundAmount	退款金额
	 */
	public int modifyRefundAmount(String refundOrderNo, BigDecimal refundAmount);

}
