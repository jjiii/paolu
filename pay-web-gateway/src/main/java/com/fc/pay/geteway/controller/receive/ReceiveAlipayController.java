package com.fc.pay.geteway.controller.receive;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.utils.RSA;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.service.getway.IReceive;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;

@Controller
@RequestMapping(value = "/receive")
public class ReceiveAlipayController {
	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private IMerchantAppConfig iMerchantAppConfig;
	
	@Autowired
	private IReceive iReceive;
	

	
	@RequestMapping("/alipay")
	@ResponseBody
	public String alipay(Model model, String out_trade_no,String trade_no,
			String trade_status,String buyer_id, 
			@RequestParam Map<String,Object> param) throws Exception {
		
		if( !("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) ){
			return "faile";
		}
		
		//验证签名
		String sign = (String)param.get("sign");
//		String sign_type = (String)param.get("sign_type");
		param.remove("sign");
		param.remove("sign_type");
		List<String> list = new ArrayList<String>(param.keySet());
		Collections.sort(list);
		StringBuffer content = new StringBuffer();
		int i = 0;
		for (String key : list) {
			content.append(key).append("=").append(URLDecoder.decode(param.get(key).toString(), "utf-8") );
			i++;
			if (i < list.size()) {
				content.append("&");
			}
		}

		
		PayPaymentOrder order = iPaymentOrder.getByOrderNo(out_trade_no);

		
		
		
		if(order==null  || StringUtils.isBlank(order.getOrderNo())){
			return "faile";
		}
		MerchantAppConfig config = iMerchantAppConfig.getByMerchantAppCode(order.getMerchantAppCode(), PayChannelEnum.alipay.name(), order.getPayWay());
		boolean check = RSA.verify(content.toString(), config.getPubKey(), sign);
		if(check == false){
			return "faile";
		}
		
		try{
			iReceive.alipayReceive(order, trade_no, buyer_id, content.toString());
			return "success";
		}catch(Exception e){
			return "faile";
		}
		
		
	}
}
