package com.fc.pay.geteway.controller.union;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
import com.fc.pay.common.core.utils.PathUtil;
import com.fc.pay.common.pay.union.UnionConfig;
import com.fc.pay.common.vo.bean.PayOrder;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.utils.Return;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConfig;

/**
 * 银联网页支付
 */
@Controller
@RequestMapping("/trade")
public class UnionTradeController {

	 @Autowired
	 private IPaymentOrder iPaymentOrder;
	 @Autowired
	 private IMerchantAppConfig iMerchantAppConfig;

	/**
	 * 银联
	 */
	@RequestMapping(value="/pay", params="channel=unionpay")
	@ResponseBody
	public Return trade_union(HttpServletRequest request, String merchantAppCode,
			String merchantOrderNo, String productName, BigDecimal amount,
			String channel, String sign, String returnUrl, String notifyUrl,
			String payWay, String orderIp, String remark) {
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
		MerchantAppConfig config = iMerchantAppConfig.getByMerchantAppCode(merchantAppCode, PayChannelEnum.unionpay.name(), payWay);
		if(config == null){
			Return obj = new Return(CodeEnum._00009.getName(),CodeEnum._00009.getValue(),null);
			return obj;
		}
		
		String order_no = IDHelp.nextId().toString();
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
		
		
		PayOrder view = new PayOrder();
		BeanUtil.copyProperties(view, entity);
		Map result = BeanUtil.bean2Map(view);
		
		
		
		if(PayWayEnum.web.name().equals(payWay)){
			Object[] strs = this.getHtml(entity, config);
			if(strs == null){
				Return obj = new Return(CodeEnum._00009.getName(),CodeEnum._00009.getValue(),null);
				return obj;
			}
			iPaymentOrder.add(entity);
			Map credential = new HashMap();
			credential.put("url", strs[0]);
			credential.put("body", strs[1]);//strs[1].replaceAll("\"", "&quot;")
			result.put("credential", credential);
			Return obj = new Return(CodeEnum._10000.getName(),CodeEnum._10000.getValue(),result);
			return obj;
		}else{
			
			String tn = this.getTn(entity, config);
			if(tn == null){
				return new Return(CodeEnum._00002.getName(),CodeEnum._00002.getValue(),null);
			}
			iPaymentOrder.add(entity);
			Map credential = new HashMap();
			credential.put("tn", tn);
			result.put("credential", credential);
			Return obj = new Return(CodeEnum._10000.getName(),CodeEnum._10000.getValue(),result);
			return obj;
		}
		
	}
	
	public Object[] getHtml(PayPaymentOrder order, MerchantAppConfig config){
			try{
				Properties properties = new Properties();
				properties.putAll(UnionConfig.properties);
				properties.put("acpsdk.signCert.path", PathUtil.readCertBasicPath() + config.getCertPath());
				properties.put("acpsdk.signCert.pwd", config.getCertPwd());
				properties.put("acpsdk.validateCert.dir", PathUtil.readCertBasicPath() +  config.getCertValidate());
				properties.put("acpsdk.encryptCert.path", PathUtil.readCertBasicPath() +  config.getCertEncPath());
				SDKConfig.getConfig().loadProperties(properties);
				
				//前台页面传过来的
				String merId = order.getChannelMerchantId();
				//金额
				BigDecimal amount = order.getAmount().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN);//乘100，取整
				String txnAmt = amount.toString();
				Map<String, String> requestData = new HashMap<String, String>();
				
				/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
				requestData.put("version", "5.0.0");   			  //版本号，全渠道默认值
				requestData.put("encoding", "UTF-8"); 			  //字符集编码，可以使用UTF-8,GBK两种方式
				requestData.put("signMethod", "01");            			  //签名方法，只支持 01：RSA方式证书加密
				requestData.put("txnType", "01");               			  //交易类型 ，01：消费
				requestData.put("txnSubType", "01");            			  //交易子类型， 01：自助消费
				requestData.put("bizType", "000201");           			  //业务类型，B2C网关支付，手机wap支付
				requestData.put("channelType", "08");           			  //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
				
				/***商户接入参数***/
				requestData.put("merId", merId);    	          			  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
				requestData.put("accessType", "0");             			  //接入类型，0：直连商户 
				requestData.put("orderId", order.getOrderNo());             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则		
				requestData.put("txnTime", DateFormatUtils.format(order.getOrderTime(), "yyyyMMddHHmmss"));        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
				requestData.put("payTimeout", DateFormatUtils.format(order.getOrderTime().getTime() +3600*1000 , "yyyyMMddHHmmss"));  //超时时间，为一个小时
				requestData.put("currencyCode", "156");         			  //交易币种（境内商户一般是156 人民币）		
				requestData.put("txnAmt", txnAmt);             			      //交易金额，单位分，不要带小数点
				//requestData.put("reqReserved", "透传字段");        		      //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节		
				//前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
				//如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
				//异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
				requestData.put("frontUrl", order.getReturnUrl());
//				requestData.put("frontUrl", "http://127.0.0.1:8080/ACPSample_B2C/frontRcvResponse6666666");
				
				//后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
				//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
				//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
				//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
				//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
				requestData.put("backUrl", UnionConfig.notifyUrl);
				
				//////////////////////////////////////////////////
				//
				//       报文中特殊用法请查看 PCwap网关跳转支付特殊用法.txt
				//
				//////////////////////////////////////////////////
				
				
				/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
				Map<String, String> submitFromData = AcpService.sign(requestData,"UTF-8");  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
				
				String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
				
				
//				String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,"UTF-8");   //生成自动跳转的Html表单				
//				LogUtil.writeLog("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);
				//将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
//				String data = JsonUtil.toJson(submitFromData);
				Object[] strs = {SDKConfig.getConfig().getFrontRequestUrl(),submitFromData};
				return strs;
			} catch(Throwable e){

				return null;
			}
	}
	
	public String getTn(PayPaymentOrder order, MerchantAppConfig config){
		try{
			Properties properties = new Properties();
			properties.putAll(UnionConfig.properties);
			properties.put("acpsdk.signCert.path", PathUtil.readCertBasicPath() + config.getCertPath());
			properties.put("acpsdk.signCert.pwd", config.getCertPwd());
			properties.put("acpsdk.validateCert.dir", PathUtil.readCertBasicPath() +  config.getCertValidate());
			properties.put("acpsdk.encryptCert.path", PathUtil.readCertBasicPath() +  config.getCertEncPath());
			SDKConfig.getConfig().loadProperties(properties);
			
			//前台页面传过来的
			String merId = order.getChannelMerchantId();
			//金额
			BigDecimal amount = order.getAmount().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN);//乘100，取整
			String txnAmt = amount.toString();
			Map<String, String> requestData = new HashMap<String, String>();
			
			/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
			requestData.put("version", "5.0.0");   			  //版本号，全渠道默认值
			requestData.put("encoding", "UTF-8"); 			  //字符集编码，可以使用UTF-8,GBK两种方式
			requestData.put("signMethod", "01");            			  //签名方法，只支持 01：RSA方式证书加密
			requestData.put("txnType", "01");               			  //交易类型 ，01：消费
			requestData.put("txnSubType", "01");            			  //交易子类型， 01：自助消费
			requestData.put("bizType", "000201");           			  //业务类型，B2C网关支付，手机wap支付
			requestData.put("channelType", "08");           			  //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
			
			/***商户接入参数***/
			requestData.put("merId", merId);    	          			  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
			requestData.put("accessType", "0");             			  //接入类型，0：直连商户 
			requestData.put("orderId", order.getOrderNo());             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则		
			requestData.put("txnTime", DateFormatUtils.format(order.getOrderTime(), "yyyyMMddHHmmss"));        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
			requestData.put("currencyCode", "156");         			  //交易币种（境内商户一般是156 人民币）		
			requestData.put("txnAmt", txnAmt);             			      //交易金额，单位分，不要带小数点
			//requestData.put("reqReserved", "透传字段");        		      //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节		
			
			
			requestData.put("accType", "01");					 	//账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
			//后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
			//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
			//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
			//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
			//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
			requestData.put("backUrl", UnionConfig.notifyUrl);
			
			//////////////////////////////////////////////////
			//
			//       报文中特殊用法请查看 PCwap网关跳转支付特殊用法.txt
			//
			//////////////////////////////////////////////////
			
			
			/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
			Map<String, String> submitFromData = AcpService.sign(requestData,"UTF-8");	//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();	//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
			Map<String, String> rspData = AcpService.post(submitFromData,requestAppUrl,UnionConfig.encoding);		//发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			if(!rspData.isEmpty() || AcpService.validate(rspData, UnionConfig.encoding)){
				if(("00").equals(rspData.get("respCode"))){
					return rspData.get("tn");
				}
			}
			return null;
			
		} catch(Throwable e){

			return null;
		}
	}


}
