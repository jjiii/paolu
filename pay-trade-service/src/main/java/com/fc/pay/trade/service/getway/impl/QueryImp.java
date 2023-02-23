package com.fc.pay.trade.service.getway.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.json.JsonObject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.core.utils.PathUtil;
import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.common.pay.alipay.AliResponse;
import com.fc.pay.common.pay.union.UnionConfig;
import com.fc.pay.common.pay.weixin.PayQueryReqData;
import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.getway.IQuery;
import com.fc.pay.trade.service.getway.IQuerySuccess;
import com.fc.pay.trade.service.notify.INotifyRecord;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConfig;
@Service
public class QueryImp implements IQuery{
	public static Logger logger = LoggerFactory.getLogger(QueryImp.class);
	
	
	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private ITradeRecord iTradeRecord;
	@Autowired
	private INotifyRecord iNotifyRecord;
	
	@Autowired
	private IQuerySuccess iQuerySuccess;
	
	
	public String payQuery_aliPay(PayPaymentOrder order, AliRequest request) {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("out_trade_no", order.getOrderNo());
		JsonObject json = request.payQuery(param);
		if(json == null){
			return CodeEnum._00002.getName();
		}
		
		String code = AliResponse.alipay_trade_query_response.code(json);
		
		if(AliResponse.code_success_value.equals(code)){//第三方返回成功，返回给商户成功，系统本身没有成功忽略
			try{
				iQuerySuccess.payQuery_aliPay(order, json);
			}catch(Exception e){
				logger.error("调用支付宝查询支付接口成功，本地写入失败" + e.getMessage());
				
			}
			return CodeEnum._10000.getName();
		}
		
		return CodeEnum._00005.getName();
	}
	

	

	@Override
	public String payQuery_union(PayPaymentOrder order, MerchantAppConfig config) {
		
		Properties properties = new Properties();
		properties.putAll(UnionConfig.properties);
		properties.put("acpsdk.signCert.path", PathUtil.readCertBasicPath() + config.getCertPath());
		properties.put("acpsdk.signCert.pwd", config.getCertPwd());
		properties.put("acpsdk.validateCert.dir", PathUtil.readCertBasicPath() + config.getCertValidate());
		properties.put("acpsdk.encryptCert.path", PathUtil.readCertBasicPath() + config.getCertEncPath());
		SDKConfig.getConfig().loadProperties(properties);
		
		String merId= order.getChannelMerchantId();
		String orderId = order.getOrderNo();
		String txnTime = DateFormatUtils.format(order.getOrderTime(), "yyyyMMddHHmmss");
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", UnionConfig.version);                 //版本号
		data.put("encoding", UnionConfig.encoding);               //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", "01");                          //签名方法 目前只支持01-RSA方式证书加密
		data.put("txnType", "00");                             //交易类型 00-默认
		data.put("txnSubType", "00");                          //交易子类型  默认00
		data.put("bizType", "000201");                         //业务类型 B2C网关支付，手机wap支付
		
		/***商户接入参数***/
		data.put("merId", merId);                  //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0");                           //接入类型，商户接入固定填0，不需修改
		
		/***要调通交易以下字段必须修改***/
		data.put("orderId", orderId);                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
		data.put("txnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
		
		Map<String, String> reqData = AcpService.sign(data,UnionConfig.encoding);//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		
		String url = SDKConfig.getConfig().getSingleQueryUrl();// 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
		//这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		Map<String, String> rspData = AcpService.post(reqData,url,UnionConfig.encoding);
		
		
		//http返回状态不等于200
		if(rspData.isEmpty()){
			return CodeEnum._00002.getName();
		}
		//签名是否正确
		if(!AcpService.validate(rspData, UnionConfig.encoding)){
			new RuntimeException("查询支付接接口：银联渠道返回签名验证失败");
		}
		
		
		if("00".equals(rspData.get("respCode")) && "00".equals(rspData.get("origRespCode")) ){//如果查询交易成功， 并且处理被查询交易的应答码逻辑成功
			try{
				iQuerySuccess.payQuery_union(order, rspData);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("银联查询接口返回成功结果，本地写入失败："+e.getMessage());
			}
			return CodeEnum._10000.getName();
		}

		return CodeEnum._00005.getName();
	}
	


	@Override
	public String payQuery_weixin(PayPaymentOrder order,
			 WeixinRequest request) {
		// TODO Auto-generated method stub
		PayQueryReqData data = new PayQueryReqData(null,order.getOrderNo());
		ScanPayQueryResData resData = request.payQuery(data);
		
		//超时，或者异常
		if(resData == null){
			return CodeEnum._00002.getName();
		}
		
		if(resData.getResult_code().equals("SUCCESS") && resData.getTrade_state().equals("SUCCESS")){
			try{
				this.payQuery_weixin(order, resData);
				
			}catch(Exception e){
				e.printStackTrace();
				logger.error("微信查询接口返回成功结果，本地写入失败："+e.getMessage());
			}
			return CodeEnum._10000.getName();
		}
		return CodeEnum._00005.getName();
	}
	
	@Transactional
	public void payQuery_weixin(PayPaymentOrder order, ScanPayQueryResData resData) {
		
		String trade_no=  resData.getTransaction_id();
		String buyer_user_id = resData.getOpenid();
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
		entity.setNotifiyParam(BeanUtil.bean2JsonString(resData));
		iTradeRecord.add(entity);
		
		
		PayPaymentOrder payOrder = new PayPaymentOrder();
		payOrder.setId(order.getId());
		payOrder.setEditor("系统");
		payOrder.setEditTime(new Date());
		payOrder.setTradeNo(trade_no);
		payOrder.setBuyerId(buyer_user_id);
		payOrder.setFinishTime(now);
		payOrder.setSuccessReason("查询微信接口，状态为成功");
		payOrder.setStatus(TradeStatusEnum.success.name());
		iPaymentOrder.modify(payOrder);
	}
}
