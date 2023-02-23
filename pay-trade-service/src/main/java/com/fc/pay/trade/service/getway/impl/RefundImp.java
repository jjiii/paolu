package com.fc.pay.trade.service.getway.impl;

import java.math.BigDecimal;
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
import com.fc.pay.common.pay.alipay.AliConfig;
import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.common.pay.alipay.AliResponse;
import com.fc.pay.common.pay.union.UnionConfig;
import com.fc.pay.common.pay.weixin.RefundReqData;
import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.config.IAliConfig;
import com.fc.pay.trade.service.getway.IRefund;
import com.fc.pay.trade.service.trade.IRefundOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.tencent.protocol.refund_protocol.RefundResData;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;
@Service
public class RefundImp implements IRefund {
	
	public static Logger logger = LoggerFactory.getLogger(RefundImp.class);
	
	@Autowired
	private IRefundOrder iRefundOrder;
	@Autowired
	private ITradeRecord iTradeRecord;
	@Autowired
	private IAliConfig iAliConfig;
	
	@Override
	public String refund_alipay(PayRefundOrder refund, AliRequest request) {
		
		Map<String, String>   params = new HashMap<String, String>();
		params.put("out_trade_no", refund.getOrderNo());//原支付订单号
		params.put("refund_amount", refund.getRefundAmount().toString());//退款金额
		params.put("out_request_no", refund.getRefundNo());//退款订单号
//		params.put("refund_reason", refund.getRefundReason());//退款原因
		
		
		JsonObject json = request.refund(params);
		if(json == null){
			return CodeEnum._00002.getName();//超时
		}
		String code = AliResponse.alipay_trade_refund_response.code(json);
		String msg =  AliResponse.alipay_trade_refund_response.msg(json);
		
		if(AliResponse.code_success_value.equals(code)){//第三方返回成功，返回给商户成功，系统本身没有成功忽略
			try{
				this.refund_alipay(refund, json);
			}catch(Exception e){
				logger.error("调用支付宝退款接口返回成功结果，本地写入失败："+e.getMessage());
			}
			return CodeEnum._10000.getName();//退款成功
		}else{
//			if(AliResponse.code_faile_value.equals(code)){
			try{
				PayRefundOrder entity = new PayRefundOrder();
				entity.setId(refund.getId());
				entity.setRefundFaileReason("支付宝返回结果为不成功:code="+code+",msg="+msg);
				entity.setRefundStatus(RefundStatusEnum.faile.name());
				entity.setRefundFinishTime(new Date());
				iRefundOrder.modify(entity);
			}catch(Exception e){
				logger.error("调用支付宝退款接口返回信息为失败的退款，本地写入失败："+e.getMessage());
			}
			return CodeEnum._00008.getName();//退款失败
		}
		
	}
	
	@Transactional
	public void refund_alipay(PayRefundOrder refund, JsonObject json) {
		
		String refund_fee = AliResponse.alipay_trade_refund_response.refund_fee(json);//退款金额
		String trade_no = AliResponse.alipay_trade_refund_response.trade_no(json);//订单号
		String buyer_user_id = AliResponse.alipay_trade_refund_response.buyer_user_id(json);//付款人id
		
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
		entity.setRefundTradeNo("");
		entity.setRefundStatus(RefundStatusEnum.success.name());
		entity.setRefundSuccessReason("支付宝退款返回结果为成功");
		entity.setRefundBuyerId(buyer_user_id);
		entity.setRefundFinishTime(now);
		iRefundOrder.modify(entity);
	}
	
	
	public String refund_uninon(PayRefundOrder refund, MerchantAppConfig config){
		
		
		
		Properties properties = new Properties();
		properties.putAll(UnionConfig.properties);
		properties.put("acpsdk.signCert.path", PathUtil.readCertBasicPath() + config.getCertPath());
		properties.put("acpsdk.signCert.pwd", config.getCertPwd());
		properties.put("acpsdk.validateCert.dir", PathUtil.readCertBasicPath() + config.getCertValidate());
		properties.put("acpsdk.encryptCert.path", PathUtil.readCertBasicPath() + config.getCertEncPath());
		SDKConfig.getConfig().loadProperties(properties);
		
		
		
		
		String origQryId = refund.getTradeNo();
		String txnAmt = refund.getRefundAmount().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString();
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", UnionConfig.version);               //版本号
		data.put("encoding", UnionConfig.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", "01");                        //签名方法 目前只支持01-RSA方式证书加密
		data.put("txnType", "04");                           //交易类型 04-退货		
		data.put("txnSubType", "00");                        //交易子类型  默认00		
		data.put("bizType", "000201");                       //业务类型 B2C网关支付，手机wap支付	
		data.put("channelType", "07");                       //渠道类型，07-PC，08-手机		
		
		/***商户接入参数***/
		data.put("merId", refund.getChannelMerchantId());                //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0");                         //接入类型，商户接入固定填0，不需修改		
		data.put("orderId", refund.getRefundNo());          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费		
		data.put("txnTime", DateFormatUtils.format(refund.getRefundTime(), "yyyyMMddHHmmss"));      //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效		
		data.put("currencyCode", "156");                     //交易币种（境内商户一般是156 人民币）		
		data.put("txnAmt", txnAmt);                          //****退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额		
		//data.put("reqReserved", "透传信息");                  //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节		
		data.put("backUrl", "http://222.222.222.222:8080/ACPSample_B2C/BackRcvResponse");               //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 退货交易 商户通知,其他说明同消费交易的后台通知
		
		/***要调通交易以下字段必须修改***/
		data.put("origQryId", origQryId);      //****原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取
		
		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
		Map<String, String> reqData  = AcpService.sign(data, UnionConfig.encoding);//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getBackRequestUrl();//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl

		 Map<String, String> rspData = AcpService.post(reqData,url, UnionConfig.encoding);//这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(rspData.isEmpty()){//未返回正确的http状态200
			return CodeEnum._00002.getName();//超时
		}
		System.err.println(rspData);
		if(!AcpService.validate(rspData, UnionConfig.encoding)){
			new RuntimeException("退款接口：银联渠道返回签名验证失败");
		}
		
		LogUtil.writeLog("验证签名成功");
		String respCode = rspData.get("respCode");
		if("00".equals(respCode) || "12".equals(respCode)){//00为退款成功，12为重复退款
			try{
				this.refund_union(refund, rspData);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("银联退款接口返回成功结果，本地写入失败："+e.getMessage());
			}
			return CodeEnum._10000.getName();//退款成功
		}
		
		return CodeEnum._00008.getName();//退款失败

	}
	
	@Transactional
	public void refund_union(PayRefundOrder refund, Map<String, String> rspData) {
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
		entity.setRefundSuccessReason("银联退款返回结果为成功");
		entity.setRefundBuyerId(buyer_user_id);
		entity.setRefundFinishTime(now);
		iRefundOrder.modify(entity);
	}

	@Override
	public String refund_weixin(PayRefundOrder refund, WeixinRequest request) {
		
		int totalFee = refund.getAmount().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).intValue();
		int refundFee = refund.getRefundAmount().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).intValue();
		// TODO Auto-generated method stub
		RefundReqData refundReqData = new RefundReqData
				(null,refund.getOrderNo(),null, refund.getRefundNo(), 
						totalFee, refundFee,refund.getChannelMerchantId(),"CNY");
		RefundResData refundResData = request.refund(refundReqData);
		if(refundResData == null){
			return CodeEnum._00002.getName();//超时
		}
		
		if(refundResData.getReturn_code().equals("FAIL")){//输入微信的数据格式错误或者其他
			return CodeEnum._00007.getName();//退款申请中
		}
	
		if(refundResData.getResult_code().equals("FAIL")){
			try{
				PayRefundOrder entity = new PayRefundOrder();
				entity.setId(refund.getId());
				entity.setRefundFaileReason("微信返回结果为不成功:code=FAIL");
				entity.setRefundStatus(RefundStatusEnum.faile.name());
				entity.setRefundFinishTime(new Date());
				iRefundOrder.modify(entity);
			}catch(Exception e){
				logger.error("调用微信退款接口返回信息为失败的退款，本地写入失败："+e.getMessage());
			}
			return CodeEnum._00008.getName();//退款失败
			
		}else{
			try{
				this.refund_weixin(refund, refundResData);
			}catch(Exception e){
				logger.error("调用微信退款接口返回成功结果，本地写入失败："+e.getMessage());
			}
			return CodeEnum._10000.getName();//退款成功
		}
	}
	
	
	@Transactional
	public void refund_weixin(PayRefundOrder refund, RefundResData refundResData) {
		
		String refund_fee = refundResData.getRefund_fee();
		String trade_no = refundResData.getTransaction_id();
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
		record.setNotifiyParam(BeanUtil.bean2JsonString(refundResData));
		iTradeRecord.add(record);
		
		PayRefundOrder entity = new PayRefundOrder();
		entity.setId(refund.getId());
		entity.setRefundTradeNo(trade_no);
		entity.setRefundStatus(RefundStatusEnum.success.name());
		entity.setRefundSuccessReason("微信退款返回结果为成功");
		entity.setRefundBuyerId(buyer_user_id);
		entity.setRefundFinishTime(now);
		iRefundOrder.modify(entity);
	}
	
}
