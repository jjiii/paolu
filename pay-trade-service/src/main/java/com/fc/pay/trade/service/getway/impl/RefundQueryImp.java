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
import com.fc.pay.common.core.enums.RefundStatusEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.core.utils.PathUtil;
import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.common.pay.alipay.AliResponse;
import com.fc.pay.common.pay.union.UnionConfig;
import com.fc.pay.common.pay.weixin.RefundQueryReqData;
import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.getway.IRefundQuery;
import com.fc.pay.trade.service.trade.IRefundOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.tencent.protocol.refund_query_protocol.RefundQueryResData;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConfig;
@Service
public class RefundQueryImp implements IRefundQuery{
	public static Logger logger = LoggerFactory.getLogger(RefundQueryImp.class);
	@Autowired
	private IRefundOrder iRefundOrder;
	@Autowired
	private ITradeRecord iTradeRecord;
	
	
	
	
	
	@Override
	public String refundQuery_alipy(PayRefundOrder refund, AliRequest request) {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("out_request_no", refund.getRefundNo());
		param.put("out_trade_no", refund.getOrderNo());
		
		JsonObject json = request.refundQuery(param);
		if(json == null){
			return CodeEnum._00002.getName();
		}
		
		String code = AliResponse.alipay_trade_fastpay_refund_query_response.code(json);
		String out_trade_no = AliResponse.alipay_trade_fastpay_refund_query_response.out_trade_no(json);
		
		if(AliResponse.code_success_value.equals(code) && out_trade_no!=null){
			try{
				this.refundQuery_alipy(refund, json);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("调用支付宝退款接口返回成功结果，本地写入失败："+e.getMessage());
			}
			return CodeEnum._10000.getName();//退款成功
		}else{
			PayRefundOrder entity = new PayRefundOrder();
			entity.setId(refund.getId());
//			entity.setRefundTradeNo(trade_no);
			entity.setRefundFaileReason("支付宝返回结果为不成功:code="+code+" || out_trade_no=null");
			entity.setRefundStatus(RefundStatusEnum.faile.name());
			entity.setRefundFinishTime(new Date());
			iRefundOrder.modify(entity);
			return CodeEnum._00008.getName();//退款失败
		}
		
		
	}
	public void refundQuery_alipy(PayRefundOrder refund, JsonObject json) {
		String trade_no = AliResponse.alipay_trade_fastpay_refund_query_response.trade_no(json);
		String buyer_user_id = AliResponse.alipay_trade_fastpay_refund_query_response.buyer_user_id(json);//查询时候没有这个字段
		
		Date now = new Date();
		
		PayTradeRecord record = new PayTradeRecord();
		BeanUtil.copyProperties(record, refund);
		record.setId(null);
		record.setEditor(null);
		record.setEditTime(null);
		record.setRefundTradeNo(trade_no);
		record.setRefundBuyerId(buyer_user_id);
		record.setRefundSuccessTime(now);
		record.setNotifiyParam(json.toString());
		iTradeRecord.add(record);
		
		PayRefundOrder entity = new PayRefundOrder();
		entity.setId(refund.getId());
		entity.setRefundTradeNo(trade_no);
		entity.setRefundSuccessReason("支付宝查询退款返回结果为成功");
		entity.setRefundBuyerId(buyer_user_id);
		entity.setRefundFinishTime(now);
		entity.setRefundStatus(RefundStatusEnum.success.name());
		iRefundOrder.modify(entity);
	}
	
	
	
	
	public String refundQuery_union(PayRefundOrder refund,
			MerchantAppConfig config) {
		Properties properties = new Properties();
		properties.putAll(UnionConfig.properties);
		properties.put("acpsdk.signCert.path", PathUtil.readCertBasicPath() + config.getCertPath());
		properties.put("acpsdk.signCert.pwd", config.getCertPwd());
		properties.put("acpsdk.validateCert.dir", PathUtil.readCertBasicPath() + config.getCertValidate());
		properties.put("acpsdk.encryptCert.path", PathUtil.readCertBasicPath() + config.getCertEncPath());
		SDKConfig.getConfig().loadProperties(properties);
		
		String merId= refund.getChannelMerchantId();
		String orderId = refund.getOrderNo();
		String txnTime = DateFormatUtils.format(refund.getOrderTime(), "yyyyMMddHHmmss");
		
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
		
		
		if("00".equals(rspData.get("respCode")) || "00".equals(rspData.get("origRespCode")) ){//如果查询交易成功， 并且处理被查询交易的应答码逻辑成功
			try{
				this.refundQuery_union(refund, rspData);
				
			}catch(Exception e){
				e.printStackTrace();
				logger.error("银联查询接口返回成功结果，本地写入失败："+e.getMessage());
			}
			return CodeEnum._10000.getName();
		}

		return CodeEnum._00005.getName();
	}

	@Transactional
	public void refundQuery_union(PayRefundOrder refund, Map<String, String> rspData) {
		
		System.out.println(rspData);
		String refund_fee = rspData.get("txnAmt");
		String trade_no = rspData.get("orderId");
		String buyer_user_id = "";
		
		Date now = new Date();
		
		PayTradeRecord record = new PayTradeRecord();
		BeanUtil.copyProperties(record, refund);
		record.setId(null);
		record.setEditor(null);
		record.setEditTime(null);
		record.setRefundTradeNo(trade_no);
		record.setRefundBuyerId(buyer_user_id);
		record.setRefundSuccessTime(now);
		record.setNotifiyParam(rspData.toString());
		iTradeRecord.add(record);
		
		PayRefundOrder entity = new PayRefundOrder();
		entity.setId(refund.getId());
		entity.setRefundTradeNo(trade_no);
		entity.setRefundStatus(RefundStatusEnum.success.name());
		entity.setRefundSuccessReason("银联查询退款返回结果为成功");
		entity.setRefundBuyerId(buyer_user_id);
		entity.setRefundFinishTime(now);
		iRefundOrder.modify(entity);
	}
	
	
	@Override
	public String refundQuery_weixin(PayRefundOrder refund,
			WeixinRequest request) {
		
		RefundQueryReqData refundQueryReqData = new RefundQueryReqData
				(null, refund.getOrderNo(), null,refund.getRefundNo(),null);
		RefundQueryResData resData = request.refundQuery(refundQueryReqData);
		
		if(resData == null){
			return CodeEnum._00002.getName();
		}
		if (resData.getReturn_code().equals("FAIL")) {
			return CodeEnum._00005.getName();
		}
		if (resData.getResult_code().equals("FAIL")) {
			PayRefundOrder entity = new PayRefundOrder();
			entity.setId(refund.getId());
//			entity.setRefundTradeNo(trade_no);
			entity.setRefundFaileReason("微信查询退款返回结果为不成功:code=FAIL");
			entity.setRefundStatus(RefundStatusEnum.faile.name());
			entity.setRefundFinishTime(new Date());
			iRefundOrder.modify(entity);
			return CodeEnum._00008.getName();//退款失败
		}else{
			try{
				this.refundQuery_weixin(refund, resData);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("微信查询接口返回成功结果，本地写入失败："+e.getMessage());
			}
			return CodeEnum._10000.getName();
		}
		
	}
	@Transactional
	public void refundQuery_weixin(PayRefundOrder refund, RefundQueryResData resData) {
		
		System.out.println(resData);
		String refund_fee = resData.getRefund_fee();
		String trade_no = resData.getTransaction_id();
		String buyer_user_id = "";
		
		Date now = new Date();
		
		PayTradeRecord record = new PayTradeRecord();
		BeanUtil.copyProperties(record, refund);
		record.setId(null);
		record.setEditor(null);
		record.setEditTime(null);
		record.setRefundTradeNo(trade_no);
		record.setRefundBuyerId(buyer_user_id);
		record.setRefundSuccessTime(now);
		record.setNotifiyParam(BeanUtil.bean2JsonString(resData));
		iTradeRecord.add(record);
		
		PayRefundOrder entity = new PayRefundOrder();
		entity.setId(refund.getId());
		entity.setRefundTradeNo(trade_no);
		entity.setRefundStatus(RefundStatusEnum.success.name());
		entity.setRefundSuccessReason("微信退款查询返回结果为成功");
		entity.setRefundBuyerId(buyer_user_id);
		entity.setRefundFinishTime(now);
		iRefundOrder.modify(entity);
	}

}
