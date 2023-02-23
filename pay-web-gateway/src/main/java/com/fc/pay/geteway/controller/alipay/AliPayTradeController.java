package com.fc.pay.geteway.controller.alipay;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.fc.pay.common.pay.alipay.AliConfig;
import com.fc.pay.common.pay.alipay.AliRequest;
import com.fc.pay.common.vo.bean.PayOrder;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.service.config.IAliConfig;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;
import com.fc.pay.trade.utils.Return;

/**
 * 支付宝手机网站支付
 */
@Controller
@RequestMapping("/trade")
public class AliPayTradeController {

	 @Autowired
	 private IPaymentOrder iPaymentOrder;
	 @Autowired
	 private IMerchantAppConfig iMerchantAppConfig;
	 @Autowired
	 private IAliConfig iAliConfig;
	 @Autowired
	 private ITradeRecord iTradeRecord;

	/**
	 * 支付宝 
	 */
	@RequestMapping(value="/pay", params="channel=alipay")
	@ResponseBody
	public Return trade_alipay(HttpServletRequest request, String merchantAppCode,
			String merchantOrderNo, String productName, BigDecimal amount,
			String channel, String sign, String returnUrl, String notifyUrl,
			String payWay, String orderIp, String remark) throws Exception{
		
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
		
		//notifyUrl
//		if(notifyUrl ==null || returnUrl.length()>295 || !notifyUrl.matches(Regex.url)){
//			Return obj = new Return(CodeEnum._00003.getName(),CodeEnum._00003.getValue()+":notifyUrl",null);
//			return obj;
//		}
		
		
		//商户配置信息
		MerchantAppConfig config = iMerchantAppConfig.getByMerchantAppCode(merchantAppCode, PayChannelEnum.alipay.name(), payWay);
		
		AliRequest aliRequest = iAliConfig.getAliRequest(config);
		if(request == null){
			Return obj = new Return(CodeEnum._00009.getName(),CodeEnum._00009.getValue(),null);
			return obj;
		}
		
		
		
		String order_no = IDHelp.nextId().toString();
		
		Map<String, String> mappp = new HashMap<String, String>();
		mappp.put("subject", productName);
		mappp.put("out_trade_no", order_no);
		mappp.put("total_amount", amount.toString());
		mappp.put("method", "alipay.trade.wap.pay");//支付宝手机网站支付接口方法名
		mappp.put("product_code", "QUICK_WAP_PAY");//商家和支付宝签约的值，不填也没错
		mappp.put("return_url",returnUrl);
		mappp.put("timeout_express","1h");//设置超过一小时，就不能再继续支付
		mappp.put("extend_params", "{\"TRANS_MEMO\":\"" +AliConfig.TRANS_MEMO + "\"}");
		mappp.put("timeout_express", AliConfig.timeout_express);
//		model.addAttribute("url", request.getAliprarm().buildUrl(mappp));
//		model.addAttribute("biz_content", request.getAliprarm().buildBizContent(mappp));
		String url_param = aliRequest.getAliprarm().urlParam(mappp);
		String biz_content = aliRequest.getAliprarm().bizContent(mappp);
		Map<String, String> aliMap = aliRequest.getAliprarm().build(mappp);
		
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
		
	
		if(PayWayEnum.web.name().equals(payWay)){
			
			Map urlMap = new HashMap();
			biz_content = URLEncoder.encode(biz_content, "utf-8");
			String url = AliConfig.url+"?"+ url_param + "&biz_content=" +biz_content;
			urlMap.put("url", url);
			result.put("credential", urlMap);
			Return obj = new Return(CodeEnum._10000.getName(),CodeEnum._10000.getValue(),result);
			return obj;
		}else{
			result.put("credential", aliMap);
			Return obj = new Return(CodeEnum._10000.getName(),CodeEnum._10000.getValue(),result);
			return obj;
		}

	}
	

	
	
}
