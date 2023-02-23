package com.fc.pay.bill.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.service.merchant.IMerchantApp;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.IRefundOrder;

/**
 * 本地系统数据业务
 * 
 * @author zhanjq
 *
 */
@Service
public class LocalDataBusiness {
	
	private static final Logger log = LoggerFactory.getLogger(LocalDataBusiness.class);
	
	@Autowired
	private IMerchantApp merchantAppService;
	
	@Autowired
	private IMerchantAppConfig merchantAppConfigService;
	
	@Autowired
	private IPaymentOrder payOrderService;
	
	@Autowired
	private IRefundOrder refundOrderService;
	
	/**
	 * 查找对账批次成功支付记录
	 * @param batch
	 * @return
	 */
	public List<PayPaymentOrder> findSuccessPayDataByBatch(BillBizBatch batch){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", batch.getPayChannel());
		map.put("channelMerchantId", batch.getChannelMerchantId());
		map.put("channelAppId", batch.getChannelAppId());
		String yyyyMMdd = DateUtil.makeDefaultDateFormat(batch.getBillDate());
		map.put("startTime", yyyyMMdd+"000000");
		map.put("endTime",   yyyyMMdd+"235959");
		map.put("status", TradeStatusEnum.success.name());
		return payOrderService.listForBill(map);
	}
	
    /**
     * 查找对账批次所有支付记录
     * @param batch
     * @return
     */
	public List<PayPaymentOrder> findAllPayDataByBatch(BillBizBatch batch){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", batch.getPayChannel());
		map.put("channelMerchantId", batch.getChannelMerchantId());
		map.put("channelAppId", batch.getChannelAppId());
		String yyyyMMdd = DateUtil.makeDefaultDateFormat(batch.getBillDate());
		map.put("startTime", yyyyMMdd+"000000");
		map.put("endTime",   yyyyMMdd+"235959");
		return payOrderService.listForBill(map);
	}
	
	/**
	 * 查找对账批次成功退款记录
	 * @param batch
	 * @return
	 */
	public List<PayRefundOrder> findSuccessRefundDataByBatch(BillBizBatch batch){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", batch.getPayChannel());
		map.put("channelMerchantId", batch.getChannelMerchantId());
		map.put("channelAppId", batch.getChannelAppId());
		String yyyyMMdd = DateUtil.makeDefaultDateFormat(batch.getBillDate());
		map.put("startTime", yyyyMMdd+"000000");
		map.put("endTime",   yyyyMMdd+"235959");
		map.put("refundStatus", TradeStatusEnum.success.name());
		return refundOrderService.listForBill(map);
	}
	
	/**
	 * 查找对账批次所有退款记录
	 * @param batch
	 * @return
	 */
	public List<PayRefundOrder> findAllRefundDataByBatch(BillBizBatch batch){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", batch.getPayChannel());
		map.put("channelMerchantId", batch.getChannelMerchantId());
		map.put("channelAppId", batch.getChannelAppId());
		String yyyyMMdd = DateUtil.makeDefaultDateFormat(batch.getBillDate());
		map.put("startTime", yyyyMMdd+"000000");
		map.put("endTime",   yyyyMMdd+"235959");
		return refundOrderService.listForBill(map);
	}	
	
	/**
	 * 查找有效商户应用
	 * @return
	 */
	public List<MerchantApp> findMerchantAppList(){
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("status", 1);
		return merchantAppService.list(params);
	}
	
	/**
	 * 查找有效渠道APP配置
	 * @return
	 */
	public List<MerchantAppConfig> findMerchantAppConfigList(){
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("status", 1);
		return merchantAppConfigService.list(params);
	}
	
	/**
	 * 查询商户应用信息
	 * @param merchantCode
	 * @return
	 */
	public MerchantApp findMerchantAppByCode(String merchantCode){
		return merchantAppService.getByMerchantAppCode(merchantCode);
	}
	
	/**
	 * 根据商户号取支付配置
	 * @param channelMerchantId
	 * @return
	 */
	public MerchantAppConfig getMerchantAppConfigByMchId(String channelMerchantId){
		return merchantAppConfigService.getByChannelMerchantId(channelMerchantId);
	}
	
	/**
	 * 根据AppId取支付配置
	 * @param channelAppId
	 * @return
	 */
	public MerchantAppConfig getMerchantAppConfigByAppId(String channelAppId){
		return merchantAppConfigService.getByChannelAppId(channelAppId);
	}
	
}
