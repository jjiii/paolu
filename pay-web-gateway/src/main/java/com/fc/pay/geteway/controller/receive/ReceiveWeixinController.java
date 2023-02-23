package com.fc.pay.geteway.controller.receive;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.pay.weixin.NotifyResData;
import com.fc.pay.common.pay.weixin.Signature;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.service.getway.IReceive;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.tencent.common.Util;

@Controller
@RequestMapping(value = "/receive")
public class ReceiveWeixinController {
	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private IMerchantAppConfig iMerchantAppConfig;
	
	@Autowired
	private IReceive iReceive;
	
	
	@RequestMapping("/weixin")
	public String weixin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		StringBuffer xmlStr = new StringBuffer();
		BufferedReader reader = request.getReader();
        String line = null;
        while ((line = reader.readLine()) != null) {
            xmlStr.append(line);
        }
        
        NotifyResData notifyResData = (NotifyResData)Util.getObjectFromXML(xmlStr.toString(), NotifyResData.class);
        if(notifyResData == null || !"SUCCESS".equals(notifyResData.getReturn_code()) || !"SUCCESS".equals(notifyResData.getResult_code()) ){
        	return "erro";
        }
        
        PayPaymentOrder order = iPaymentOrder.getByOrderNo(notifyResData.getOut_trade_no());
        if(order == null){
        	return "erro";
        }
        
        MerchantAppConfig config = iMerchantAppConfig.getByMerchantAppCode(order.getMerchantAppCode(), PayChannelEnum.weixin.name(), order.getPayWay());
        if(config == null){
        	return "erro";
        }
        
        
        
        
        if (!Signature.checkIsSignValidFromResponseString(xmlStr.toString(), config.getPayKey())) {
        	return "erro";
        }
        
        iReceive.weixinpayReceive(order, notifyResData.getTransaction_id(), notifyResData.getOpenid(), xmlStr.toString());
        
        String sb = "<xml><return_code><![CDATA[SUCCESS]]></return_code> <return_msg><![CDATA[OK]]></return_msg></xml>";
//        response.setContentType("text/xml");
//        PrintWriter out = response.getWriter();
//        out.println(sb.toString());
//        out.flush();
		return sb;
	}
}
