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
import com.fc.pay.common.core.enums.RefundStatusEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.dao.PayPaymentOrderMapper;
import com.fc.pay.trade.dao.PayRefundOrderMapper;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.trade.IRefundOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;

@Service
public class RefundOrderImp implements IRefundOrder {
	@Autowired
	private ITradeRecord iTradeRecord;
	
	@Autowired
	private PayRefundOrderMapper refundOrderMapper;
	@Autowired
	private PayPaymentOrderMapper paymentOrderMapper;

	@Override
	public int add(PayRefundOrder record) {
		return refundOrderMapper.insertSelective(record);
	}

	@Override
	public int delete(Long id) {
		return refundOrderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int modify(PayRefundOrder record) {
		return refundOrderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public PayRefundOrder get(Long id) {
		return refundOrderMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page page(Map<String, Object> parm) {
		Page page = new Page();
		page.setPageSize(Integer.valueOf(parm.get("size").toString()));
		page.setCurrNum(Integer.valueOf(parm.get("current").toString()));
		parm.put("page", page);
		return refundOrderMapper.pageByMap(parm);
	}

	@Override
	public List<PayRefundOrder> list(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PayRefundOrder get(Map<String, Object> parm) {
		List<PayRefundOrder> list = refundOrderMapper.selectByMap(parm);
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}
	
	public PayRefundOrder getByRefundNo(String refund_no){
		if (StringUtils.isNotBlank(refund_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("refund_no", refund_no);
			return this.get(parm);
	}
	return null;
	}
	
	@Override
	public PayRefundOrder getRefundByOrderNo(String order_no) {
		if (StringUtils.isNotBlank(order_no)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("order_no", order_no);
			return this.get(parm);
		}
		return null;
	}	
	
	public PayRefundOrder getByRefundNo(String refund_no, String merchant_app_code){
		
		if (StringUtils.isNotBlank(refund_no) && StringUtils.isNotBlank(merchant_app_code)) {
				Map<String, Object> parm = new HashMap<String, Object>();
				parm.put("refund_no", refund_no);
				parm.put("merchant_app_code", merchant_app_code);
				return this.get(parm);
		}
		return null;
	}
	public PayRefundOrder getByRefundMerchantNo(String refund_merchant_no, String merchant_app_code){
		if (StringUtils.isNotBlank(refund_merchant_no) && StringUtils.isNotBlank(merchant_app_code)) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("refund_merchant_no", refund_merchant_no);
			parm.put("merchant_app_code", merchant_app_code);
			return this.get(parm);
		}
		return null;
	}
	
	
	public PayRefundOrder getByOrderNoRefundMerchantNo(String refund_merchant_no, String merchant_app_code) {
		if (StringUtils.isNotBlank(refund_merchant_no)
			&& StringUtils.isNotBlank(merchant_app_code)) {
			
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("refund_merchant_no", refund_merchant_no);
			parm.put("merchant_app_code", merchant_app_code);
			return this.get(parm);
		}
		return null;
	}

	@Transactional
	public Boolean changStatusSuccess(String order_no, Map<String, Object> data) {
		PayRefundOrder refund  = this.getByRefundNo(order_no);
		if (refund == null) {
			return false;
		}
		
		Date now = new Date();
		
		PayTradeRecord record = new PayTradeRecord();
		BeanUtil.copyProperties(record, refund);
		record.setId(null);
		record.setEditor(null);
		record.setEditTime(null);
//		record.setRefundTradeNo(trade_no);
//		record.setRefundBuyerId(buyer_user_id);
		record.setRefundSuccessTime(now);
		record.setNotifiyParam("对账模块修改退款状态为成功");
		iTradeRecord.add(record);
		
		PayRefundOrder entity = new PayRefundOrder();
		entity.setId(refund.getId());
		entity.setRefundTradeNo("");
		entity.setRefundStatus(RefundStatusEnum.success.name());
		entity.setRefundSuccessReason("对账模块修改退款状态为成功");
		entity.setRefundBuyerId("");
		entity.setRefundFinishTime(now);
		this.modify(entity);
		return true;
		
	}

	@Transactional
	public Boolean changStatusFail(String order_no, Map<String, Object> data) {
		PayRefundOrder refund  = this.getByRefundNo(order_no);
		if (refund == null) {
			return false;
		}
		PayRefundOrder refundOrder = new PayRefundOrder();
		refundOrder.setId(refund.getId());
		
		
		refundOrder.setEditTime(new Date());
		refundOrder.setStatus(RefundStatusEnum.faile.name());
		refundOrder.setRefundFaileReason("对账模块修改退款状态为失败");
		this.modify(refundOrder);
		
		PayTradeRecord tradeRecord = iTradeRecord.getByRefundNo(order_no);
		if( tradeRecord != null ){
			iTradeRecord.delete(tradeRecord.getId());
		}
		
		return true;
	}

	@Transactional
	public Boolean changAmount(String order_no, BigDecimal amount, Map<String, Object> data) {
		PayRefundOrder refund  = this.getByRefundNo(order_no);
		if (refund == null) {
			return false;
		}
		
		Date now = new Date();
		
		PayRefundOrder refundOrder = new PayRefundOrder();
		refundOrder.setId(refund.getId());
		refundOrder.setAmount(amount);
		refundOrder.setEditTime(now);
		refundOrder.setBillStatus(BillStatusEnum.balance.name());
		refundOrder.setRefundRemark("对账模块修改退款订单金额，不修改状态");
		
		PayTradeRecord trade = iTradeRecord.getByRefundNo(order_no);
		if( trade != null ){
			PayTradeRecord tradeRecord = new PayTradeRecord();
			tradeRecord.setId(trade.getId());
			tradeRecord.setAmount(amount);
			tradeRecord.setEditTime(now);
			tradeRecord.setRemark("对账模块修改退款订单金额，不修改状态");
			iTradeRecord.modify(tradeRecord);
		}
		
		
		return null;
	}

	@Override
	public int modifyBillStatus(String refundNo, String billStatus) {
		return refundOrderMapper.updateBillStatusSingle(refundNo, billStatus);
	}

	@Override
	public int modifyBillStatusByMap(Map<String, ArrayList<String>> refundMap) {
		/***
		 * refundMap的KEY包括
		 * 1.正常：BillStatusEnum.normal.name()
		 * 2.存疑：BillStatusEnum.doubt.name()
		 * 3.差错：BillStatusEnum.mistake.name()
		 */
		int updateCount = 0;
		Set<String> billStatusSet = refundMap.keySet();
		StringBuffer inCodition = new StringBuffer();
		if(billStatusSet!=null && billStatusSet.size()>0){
			for(String billStatus : billStatusSet){
				ArrayList<String> refundOrderNoList = refundMap.get(billStatus);				
				if(refundOrderNoList==null || refundOrderNoList.size()<=0){
					continue;
				}
				//避免in条件过长，每次100条
				int batchSize = 100;
				int refundOrderNoListSize = refundOrderNoList.size();				
				int batchCount = refundOrderNoListSize / batchSize;
				if(refundOrderNoListSize % 100 >0){
					batchCount++;
				}
				//StringBuffer inCodition = new StringBuffer();
				String orderNo = null;
				int start = 0;
				int end = 0;
				for(int i=0; i<batchCount; i++){
					start = i*batchSize;
					end = (i == (batchCount-1)) ? refundOrderNoListSize : start+batchSize;
					for(int j=start; j<end; j++){
						orderNo = refundOrderNoList.get(j);
						inCodition.append(orderNo).append(",");						
					}
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("billStatus", billStatus);
					param.put("refundOrderNoInCondition", inCodition.toString().substring(0, inCodition.length()-1));
					//System.out.println(param.get("orderNoInCondition"));
					updateCount = updateCount + refundOrderMapper.updateBillStatus(param);
					inCodition.delete(0, inCodition.length());
				}				
			}
		}
		return updateCount;
	}

	@Override
	public List<PayRefundOrder> listForBill(Map<String, Object> params) {
		return refundOrderMapper.selectForBill(params);
	}

	@Override
	public int modify(PayRefundOrder payRefundOrder, PayPaymentOrder payPaymentOrder) {
		int refundResult = refundOrderMapper.updateByPrimaryKeySelective(payRefundOrder);
		int paymentResult = paymentOrderMapper.updateByPrimaryKeySelective(payPaymentOrder);
		return refundResult+paymentResult;
	}


}
