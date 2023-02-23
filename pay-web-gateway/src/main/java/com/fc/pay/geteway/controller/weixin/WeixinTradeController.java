package com.fc.pay.geteway.controller.weixin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.enums.PayWayEnum;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.core.utils.IDHelp;
import com.fc.pay.common.core.utils.IpUtil;
import com.fc.pay.common.pay.weixin.PayReqData;
import com.fc.pay.common.pay.weixin.RefundReqData;
import com.fc.pay.common.pay.weixin.WeixinRequest;
import com.fc.pay.common.vo.bean.PayOrder;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.service.config.IWeixinConfig;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.utils.Return;
import com.tencent.WXPay;
import com.tencent.protocol.pay_protocol.ScanPayResData;
import com.tencent.protocol.refund_protocol.RefundResData;
import com.tencent.service.RefundService;
@Controller
@RequestMapping("/trade")
public class WeixinTradeController {
	 @Autowired
	 private IPaymentOrder iPaymentOrder;
	 @Autowired
	 private IMerchantAppConfig iMerchantAppConfig;
	 @Autowired
	 private IWeixinConfig iWeixinConfig;
	
	@RequestMapping(value="/pay", params="channel=weixin")
	@ResponseBody
	public Return trade_union(HttpServletRequest request, String merchantAppCode,
			String merchantOrderNo, String productName, BigDecimal amount,
			String channel, String sign, String returnUrl, String notifyUrl,
			String payWay, String orderIp, String remark, String openId) {
		
		//金额
		if(amount.compareTo(BigDecimal.ZERO) == 1){
			amount = amount.setScale(2,   BigDecimal.ROUND_DOWN);
		}else{
			Return obj = new Return(CodeEnum._00003.getName(),CodeEnum._00003.getValue()+":amount",null);
			return obj;
		}
		
		//商品名称
		if(StringUtils.isBlank(productName) || productName.length()>295){
			Return obj = new Return(CodeEnum._00009.getName(),CodeEnum._00009.getValue()+":productName",null);
			return obj;
		}
		
		//ip
		if(StringUtils.isBlank(orderIp)){
			orderIp = IpUtil.getIpAddr(request);
		}
		if(StringUtils.isBlank(orderIp) || orderIp.length()>45){
			Return obj = new Return(CodeEnum._00003.getName(),CodeEnum._00003.getValue()+":orderIp",null);
			return obj;
		}
		
		//支付类型
		if(!PayWayEnum.app.name().equals(payWay) && !PayWayEnum.web.name().equals(payWay)){
			Return obj = new Return(CodeEnum._00003.getName(),CodeEnum._00003.getValue()+":payWay",null);
			return obj;
		}
		
		//returnUrl
		if(returnUrl !=null && returnUrl.length()>295){
			Return obj = new Return(CodeEnum._00003.getName(),CodeEnum._00003.getValue()+":returnUrl",null);
			return obj;
		}
		
		//商户配置信息
		MerchantAppConfig config = iMerchantAppConfig.getByMerchantAppCode(merchantAppCode, PayChannelEnum.weixin.name(), payWay);
		WeixinRequest weixinRequest = iWeixinConfig.getRequest(config);
		if(weixinRequest == null){
			Return obj = new Return(CodeEnum._00009.getName(),CodeEnum._00009.getValue(),null);
			return obj;
		}
		
		
		
		PayReqData payReqData = null;
		String order_no = IDHelp.nextId().toString();
		BigDecimal wx_amount = amount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN);//乘100，取整
		Date now = new Date();
		//获取微信预支付订单
		if(PayWayEnum.web.name().equals(payWay)){//web即jsapi公众号支付
			if(StringUtils.isBlank(openId)){
				Return obj = new Return(CodeEnum._00003.getName(),CodeEnum._00003.getValue()+":openId",null);
				return obj;
			}
			
			payReqData = new PayReqData("", 
					productName,"",order_no, wx_amount.intValue(), "192.168.0.1",
					DateFormatUtils.format(now, "yyyyMMddHHmmss"),DateFormatUtils.format(now.getTime() +3600*1000 , "yyyyMMddHHmmss"), "" , "http://test.carisok.com/pay-web-gateway/receive/weixin", openId);
		}else{
			payReqData = new PayReqData("", 
					productName,"",order_no, wx_amount.intValue(), "192.168.0.1",
					DateFormatUtils.format(now, "yyyyMMddHHmmss"),DateFormatUtils.format(now.getTime() +3600*1000 , "yyyyMMddHHmmss"), "" , "http://test.carisok.com/pay-web-gateway/receive/weixin",null);
		}
		
		
		ScanPayResData scanPayResData = weixinRequest.pay(payReqData);
		
		if (scanPayResData!=null && "SUCCESS".equals(scanPayResData.getResult_code())) {
			PayPaymentOrder entity =  new PayPaymentOrder();
			entity.setCreater("系统");
			entity.setMerchantCode(config.getMerchantCode());
			entity.setMerchantName(config.getMerchantName());
			entity.setMerchantAppCode(config.getMerchantAppCode());
			entity.setMerchantAppName(config.getMerchantAppName());
			
			entity.setMerchantOrderNo(merchantOrderNo);
			entity.setProductName(productName);
			entity.setAmount(amount);
			entity.setChannel(channel);
			entity.setChannelAppId(config.getChannelAppId());
			entity.setChannelMerchantId(config.getChannelMerchantId());
			entity.setReturnUrl(returnUrl);
			entity.setNotifyUrl(notifyUrl);
			entity.setOrderIp(orderIp);
			entity.setRemark(remark);
			entity.setPayWay(payWay);
			entity.setOrderNo(order_no);
			entity.setOrderTime(new Date());
			entity.setTimeOut(60);
			entity.setTimeExpire(DateUtils.addMinutes(entity.getOrderTime(), 60));
			entity.setStatus(TradeStatusEnum.pay_wait.name());
			iPaymentOrder.add(entity);
			
			PayOrder view = new PayOrder();
			BeanUtil.copyProperties(view, entity);
			Map result = BeanUtil.bean2Map(view);
			result.put("credential", scanPayResData);
			Return obj = new Return(CodeEnum._10000.getName(),CodeEnum._10000.getValue(),result);
			return obj;
		}
		
		Return obj = new Return(CodeEnum._00002.getName(),CodeEnum._00002.getValue(),null);
		return obj;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		try {
				 WXPay.initSDKConfiguration(
			                //签名算法需要用到的秘钥
			                "x23456789012345678901234567890fc",
			                //公众账号ID，成功申请公众账号后获得
			                "wx066d014a25abfb88",
			                //商户ID，成功申请微信支付功能之后通过官方发出的邮件获得
			                "10058454",
			                //子商户ID，受理模式下必填；
			                "0",
			                //HTTP证书在服务器中的路径，用来加载证书用
			                "F:/weixin/apiclient_cert.p12",
			                //HTTP证书的密码，默认等于MCHID
			                "10058454"
			        );
			
	 

				  //支付
				  Date now = new Date();
//				  ScanPayReqData scanPayReqData = new ScanPayReqData(null,"JSAPI支付测试","fengche",IDHelp.nextId().toString(),1, "WEB" ,"192.168.3.45",
//						  DateFormatUtils.format(now, "yyyyMMddHHmmss"),DateFormatUtils.format(now.getTime() +3600*1000 , "yyyyMMddHHmmss"),"WXG");
//				  ScanPayService scanPayService = new ScanPayService();
//				  String payServiceResponseString = scanPayService.request(scanPayReqData);
//				  System.err.println(payServiceResponseString);
//				  ScanPayResData scanPayResData = (ScanPayResData) Util.getObjectFromXML(payServiceResponseString, ScanPayResData.class);
//				  
//				  if ("SUCCESS".equals(scanPayResData.getResult_code())) {
//		             System.out.println("成功");
//		          }else{
//		        	  System.err.println("失败");
//		          }
//
//				  //支付查询
//				  ScanPayQueryReqData scanPayQueryReqData = new ScanPayQueryReqData(null,"100111");
//				  ScanPayQueryService scanPayQueryService = new ScanPayQueryService();
//				  String s = scanPayQueryService.request(scanPayQueryReqData);
//				  System.err.println(s);
//				  //退款
//				  RefundService refundService = new RefundService();
//				  RefundReqData refundReqData = new RefundReqData("333","3333","3333","333333",100,1,"1111","CNY");
//				  String spayServiceResponseString = refundService.request(refundReqData);
//				  System.err.println(spayServiceResponseString);
//				  
//				  //退款查询
//				  RefundQueryReqData refundQueryReqData = new RefundQueryReqData("11","11","11","1","11");
//				  RefundQueryService refundQueryService = new RefundQueryService();
//				  String ss = refundQueryService.request(refundQueryReqData);
//				  System.out.println(ss);
//				  
//				  //对账
//				  
//				  DownloadBillReqData downloadBillReqData = new DownloadBillReqData(null, "20170501", "ALL");
//				  DownloadBillService downloadBillService = new DownloadBillService();
//				  
//				  String downloadBillServiceResponseString = downloadBillService.request(downloadBillReqData);
//				  System.err.println(downloadBillServiceResponseString);
//				  DownloadBillResData downloadBillResData = (DownloadBillResData) Util.getObjectFromXML(downloadBillServiceResponseString, DownloadBillResData.class);
//				  System.err.println(downloadBillResData);
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  System.out.println("==============");
				  
				  
				  
				  
				  
				  
				  

				
				 
//				  WeixinRequest request = new WeixinRequest("wx066d014a25abfb88", "10058454", "x23456789012345678901234567890fc", "F:/weixin/apiclient_cert.p12", "10058454");
//				  
//				  //支付jsapi
//				  PayReqData payReqData = new PayReqData(null, "二锅头一瓶","attach",IDHelp.nextId().toString(),1, "192.168.0.1",
//						  DateFormatUtils.format(now, "yyyyMMddHHmmss"),DateFormatUtils.format(now.getTime() +3600*1000 , "yyyyMMddHHmmss"),"goods_tag","http://test.carisok.com/pay-web-shop");
//				  ScanPayResData scanPayResDatas = request.pay(payReqData);
//				  if (scanPayResDatas!=null && "SUCCESS".equals(scanPayResDatas.getResult_code())) {
//		             System.out.println("支付成功");
//		          }else{
//		        	  System.err.println("支付失败");
//		          }
//				  
//				  
//				  System.err.println("====");
				  WeixinRequest request = new WeixinRequest("wx070ba35830306410", "1240359802", "x23456789012345678901234567890fc", "F:/weixin/apiclient_cert.p12", "10058454");
//				  PayReqData payReqData = new PayReqData(null, "二锅头一瓶","attach",IDHelp.nextId().toString(),1, "192.168.0.1",
//						  DateFormatUtils.format(now, "yyyyMMddHHmmss"),DateFormatUtils.format(now.getTime() +3600*1000 , "yyyyMMddHHmmss"),"goods_tag","http://test.carisok.com/pay-web-shop");
//				  ScanPayResData scanPayResDatas = request.pay(payReqData);
//				  if (scanPayResDatas!=null && "SUCCESS".equals(scanPayResDatas.getResult_code())) {
//		             System.out.println("支付成功");
//		          }else{
//		        	  System.err.println("支付失败");
//		          }
//				  
//				  //支付查询
//				  PayQueryReqData rdata = new PayQueryReqData(null,"123456");
//				  ScanPayQueryResData resData = request.payQuery(rdata);
//				  
//				  
				  //退款
				  RefundReqData refundReqData = new RefundReqData(null,"865143319691399168","deviceInfo","outRefundNo",100,100,"opUserID","CNY");
				  RefundResData refund = request.refund(refundReqData);
				  if ("SUCCESS".equals(refund.getResult_code())) {
		             System.out.println("退款成功");
		          }else{
		        	  System.err.println("退款失败");
		          }
//				  //退款查询
//				  RefundQueryReqData refundQueryReqData = new RefundQueryReqData("transactionID", "outTradeNo", "deviceInfo", "outRefundNo", "refundID");
//				  RefundQueryResData data = request.refundQuery(refundQueryReqData);
//				  System.out.println(data);
				  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
