package com.fc.pay.shop.controller.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.pay.common.core.utils.IDHelp;
import com.fc.pay.sdk.FcPay;
import com.fc.pay.sdk.config.FcPayConstant;
import com.fc.pay.sdk.exception.FcPayException;
import com.fc.pay.sdk.http.HttpConfig;
import com.fc.pay.sdk.model.FcPayBillFileNotify;
import com.fc.pay.sdk.model.FcPayBillMistakeNotify;
import com.fc.pay.sdk.model.FcPayChannel;
import com.fc.pay.sdk.model.FcPayCharge;
import com.fc.pay.sdk.model.FcPayRefund;
import com.fc.pay.sdk.model.FcPayTradeNotify;

@Controller
@RequestMapping("/mch")
public class MchServerController {
	
	private static final String RETURN_TRADE_URL = "http://test.carisok.com/pay-web-shop/mch/pay/return";
	
	private static final String NOTIFY_TRADE_URL = "http://test.carisok.com/pay-web-shop/mch/notify/trade";
	
	private static final String NOTIFY_BILL_DOWNLOAD_URL = "http://test.carisok.com/pay-web-shop/mch/notify/bill/download";
	
	private static final String NOTIFY_BILL_MISTAKE_URL = "http://test.carisok.com/pay-web-shop/mch/notify/bill/mistake";
	
	@RequestMapping(value = "/pay/return")
	@ResponseBody
	public Response payReturn(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("支付渠道回跳页面success");
		return ResponseUtil.success("支付渠道回跳页面success");
	}
	
	
	/**
	 * 创建支付
	 * @param request
	 * @param response
	 * @param productName
	 * @param amount
	 * @param channel
	 * @param payWay
	 * @param remark
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/pay/createCharge")
	@ResponseBody
	public Response createCharge(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="merchantOrderNo",required=true) String merchantOrderNo,
			@RequestParam(value="productName",required=true) String productName,
			@RequestParam(value="amount",required=true) String amount,
			@RequestParam(value="channel",required=true) String channel,
			@RequestParam(value="payWay",required=true) String payWay,
			@RequestParam(value="remark",required=true) String remark,
			@RequestParam(value="returnUrl") String returnUrl) throws Exception {
		
		try {
			FcPay.initConfig(ConfigProvider.getConfigProperties());
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		//创建支付
		//String merchantOrderNo = IDHelp.nextId().toString();
		if(returnUrl==null || returnUrl.trim().length()<=0){
			returnUrl = RETURN_TRADE_URL;
		}
		//String returnUrl = RETURN_TRADE_URL;
		String notifyUrl = NOTIFY_TRADE_URL;
		String orderIp = IPUtil.getRemoteHost(request);		
		FcPayCharge charge = null;
		try {
			charge = FcPay.createCharge(merchantOrderNo, productName, amount, channel, payWay, returnUrl, notifyUrl, orderIp, remark);
			/**
			//模拟异常测试
			try{
				int test = 1/0;
			}catch(Exception e){
				throw new FcPayException("00001","sysError",e);
			}			
			*/
		} catch (FcPayException e) {
			//e.printStackTrace();
			return ResponseUtil.fail(e.getCode(), e.getMsg());
			//return ResponseUtil.fail(e);
		}
		return ResponseUtil.success(charge);
	}
	/*
	{
	  "data": {
	    "code": "00000",
	    "msg": "交易成功",
	    "object": {
	      "merchantAppCode": "1481083760950",
	      "merchantAppName": "商城",
	      "merchantOrderNo": "836495852167106560",
	      "orderNo": "836495853630918656",
	      "tradeNo": null,
	      "productName": "iphone7",
	      "channel": "alipay",
	      "payWay": null,
	      "amount": "10000.0",
	      "orderTime": "1488271127505",
	      "status": "pay_wait",
	      "credential": {
	        "orderInfo": "charset=utf-8&alipay_sdk=alipay-sdk-java-dynamicVersionNo&method=alipay.trade.wap.pay&format=json&sign=G0O0jWo8%2B6KylP%2FTJjZtq6ShIE2ldaNpkKu6qvsXGYO9pxyGIjWVOCqO0l0cjI6qdW8mI%2F1eZ7XsgRnJi7NQZRM2fqAkI9wcY4BKwOiLHkiVWFs3HYME2dlHUt31MTwF6uB70psepMubZA7tIHCOis3L%2BNbtgFJ69g4yMBKWBAk%3D&notify_url=http%3A%2F%2Ftest.carisok.com%2Fpay-web-gateway%2Freceive%2Falipay&app_id=2016080100144012&sign_type=RSA&version=1.0&timestamp=2017-02-28+16%3A38%3A47&biz_content={\"out_trade_no\":\"836495853630918656\",\"total_amount\":\"10000.00\",\"subject\":\"iphone7\",\"return_url\":\"\",\"product_code\":\"QUICK_WAP_PAY\"}"
	      }
	    }
	  },
	  "sign": "签名值"
	}
	 */
	
	/**
	 * 查询支付
	 * @param request
	 * @param response
	 * @param orderNo
	 * @param channel
	 * @return
	 */
	@RequestMapping(value = "/pay/queryCharge")
	@ResponseBody
	public Response queryCharge(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="orderNo",required=true) String orderNo,
			@RequestParam(value="channel",required=true) String channel) {
		//初始化SDK
		try {
			FcPay.initConfig(ConfigProvider.getConfigProperties());
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		//支付查询
		FcPayCharge charge = null;
		try {
			charge = FcPay.queryCharge(orderNo, channel);
		} catch (FcPayException e) {
			//e.printStackTrace();
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		return ResponseUtil.success(charge);		
	}
	/*
	{
	  "data": {
	    "code": "00000",
	    "msg": "交易成功",
	    "object": {
	      "merchantAppCode": "1481083760950",
	      "merchantAppName": "商城",
	      "merchantOrderNo": "836495852167106560",
	      "orderNo": "836495853630918656",
	      "tradeNo": null,
	      "productName": "iphone7",
	      "channel": "alipay",
	      "payWay": null,
	      "amount": "10000.0",
	      "orderTime": "1488271128000",
	      "status": "pay_wait",
	      "credential": null
	    }
	  },
	  "sign": "签名值"
	}
	*/
	
	/**
	 * 创建退款
	 * @param request
	 * @param response
	 * @param orderNo
	 * @param channel
	 * @param refundAmount
	 * @param refundReason
	 * @return
	 */
	@RequestMapping(value = "/pay/createRefund")
	@ResponseBody
	public Response createRefund(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="orderNo",required=true) String orderNo,
			@RequestParam(value="refundMerchantNo",required=true) String refundMerchantNo,
			@RequestParam(value="channel",required=true) String channel,
			@RequestParam(value="refundAmount",required=true) String refundAmount,
			@RequestParam(value="refundReason",required=true) String refundReason) {
		//初始化SDK
		try {
			FcPay.initConfig(ConfigProvider.getConfigProperties());
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		//创建退款
		String notifyUrl = NOTIFY_TRADE_URL;
		//String refundMerchantNo = IDHelp.nextId().toString();
		FcPayRefund refund = null;
		try {
			refund = FcPay.createRefund(channel, orderNo, refundMerchantNo, refundAmount, refundReason, notifyUrl);
		} catch (FcPayException e) {
			//e.printStackTrace();
			//return ResponseUtil.fail(e.getCode(), e.getMsg());
			return ResponseUtil.fail(e);
		}						
		return ResponseUtil.success(refund);
	}
	/*
	{
	  "data": {
	    "code": "00000",
	    "msg": "交易成功",
	    "object": null
	  },
	  "sign": "签名值"
	}
	*/
	
	
	/*@RequestMapping(value = "/pay/retryRefund")
	@ResponseBody
	public Response retryRefund(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="refundMerchantNo",required=true) String refundMerchantNo,
			@RequestParam(value="orderNo",required=true) String orderNo,
			@RequestParam(value="channel",required=true) String channel) {
		//初始化SDK
		try {
			FcPay.initConfig(ConfigProvider.getConfigProperties());
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		//创建退款
		String notifyUrl = NOTIFY_TRADE_URL;
		if(StringUtils.isEmpty(refundMerchantNo)){
			refundMerchantNo = IDHelp.nextId().toString();
		}
		String refundAmount = "";
		String refundReason = "";
		FcPayRefund refund = null;
		try {
			refund = FcPay.createRefund(channel, orderNo, refundMerchantNo, refundAmount, refundReason, notifyUrl);
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}						
		return ResponseUtil.success(refund);
	}*/
	
	
	/**
	 * 查询退款
	 * @param request
	 * @param response
	 * @param channel
	 * @param refundNo
	 * @param refundMerchantNo
	 * @return
	 */
	@RequestMapping(value = "/pay/queryRefund")
	@ResponseBody
	public Response queryRefund(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="channel",required=true) String channel,
			@RequestParam(value="refundNo",required=true) String refundNo,
			@RequestParam(value="refundMerchantNo",required=true) String refundMerchantNo) {
		//初始化SDK
		try {
			FcPay.initConfig(ConfigProvider.getConfigProperties());
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		//退款查询
		FcPayRefund refund = null;
		try {
			refund = FcPay.queryRefund(channel, refundNo, refundMerchantNo);
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}		
		return ResponseUtil.success(refund);
	}
	/*
	{
	  "data": {
	    "code": "00004",
	    "msg": "原订单不存在",
	    "object": null
	  },
	  "sign": "签名值"
	}
	 */	
	
	@RequestMapping(value = "/notify/trade")
	@ResponseBody
	public Response notifyTrade(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) {
		//初始化SDK
		try {
			FcPay.initConfig(ConfigProvider.getConfigProperties());
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		System.out.println(data);
		//退款查询
		try {
			FcPayTradeNotify notify = FcPay.readFcPayTradeNotify(data);
			System.out.println(notify.toString());
			//store notify
		} catch (FcPayException e) {
			e.printStackTrace();
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		return ResponseUtil.success("OK");
	}
	
	@RequestMapping(value = "/notify/bill/download")
	@ResponseBody
	public Response notifyBillFileDownload(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) {
		//初始化SDK
		try {
			FcPay.initConfig(ConfigProvider.getConfigProperties());
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		System.out.println(data);
		//退款查询
		try {
			FcPayBillFileNotify notify = FcPay.readFcPayBillFileNotify(data);
			System.out.println(notify.toString());
			//store notify
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		return ResponseUtil.success("OK");
	}
	
	@RequestMapping(value = "/notify/bill/mistake")
	@ResponseBody
	public Response notifyBillMistake(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) {
		//初始化SDK
		try {
			FcPay.initConfig(ConfigProvider.getConfigProperties());
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		System.out.println(data);
		//退款查询
		try {
			FcPayBillMistakeNotify notify = FcPay.readFcPayBillMistakeNotify(data);
			System.out.println(notify.toString());
			//store notify
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		return ResponseUtil.success("OK");
	}
	
	
	@RequestMapping(value = "/channel/query")
	@ResponseBody
	public Response queryChannel(HttpServletRequest request, HttpServletResponse response) {
		//初始化SDK
		try {
			FcPay.initConfig(ConfigProvider.getConfigProperties());
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}
		//退款查询
		List<FcPayChannel> channelList = null;
		try {
			channelList = FcPay.queryChannelList();
		} catch (FcPayException e) {
			return ResponseUtil.fail(e.getCode(), e.getMsg());
		}		
		return ResponseUtil.success(channelList);
	}
	
	
	@RequestMapping(value = "/download/bill")
	public void downloadMerchantAppBill(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="yyyyMMdd",required=true) String yyyyMMdd) {
		//初始化SDK
		try {
			FcPay.initConfig(ConfigProvider.getConfigProperties());
		} catch (FcPayException e) {
			return ;
		}
		//下载账单
		String billPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("classes/", "bill/")+System.currentTimeMillis()+".zip";
		try {
			FcPay.downloadBill(yyyyMMdd, billPath);
		} catch (FcPayException e) {
			return ;
		}
		//响应文件
		InputStream input = null;
		OutputStream output = null;
		try{
			input = new FileInputStream(new File(billPath));
			output = response.getOutputStream();
			IOUtils.copyLarge(input, output);//内部byte[]buffer缓冲区大小为4096字节
		}catch(Exception e){
			return ;
		}finally{
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}
	
}
