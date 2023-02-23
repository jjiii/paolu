package com.fc.pay.geteway.controller.receive;

import java.io.File;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.pay.union.CerFilter;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.service.getway.IReceive;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.CertUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;

@Controller
@RequestMapping(value = "/receive")
public class ReceiveUnionController {
	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private IMerchantAppConfig iMerchantAppConfig;
	
	@Autowired
	private IReceive iReceive;
	
	

	@RequestMapping("/unionpay")
	public String alipay(Model model, @RequestParam Map<String,String> param,
			 String encoding, String orderId, String queryId, String signature,String certId) throws Exception {
		
		if(StringUtils.isBlank(encoding)){
			encoding = "utf-8";
		}
		
		for(String key : param.keySet()){
			String value = param.get(key);
			if(StringUtils.isBlank(value)){
				param.remove(key);
			}else{
				param.put(key, new String(value.getBytes(encoding), encoding));
			}
		}
		

		PayPaymentOrder order = iPaymentOrder.getByOrderNo(orderId);
		MerchantAppConfig config = iMerchantAppConfig.getByMerchantAppCode(order.getMerchantAppCode(), PayChannelEnum.unionpay.name(), order.getPayWay());
		
		AcpService.validate(param, encoding);
		
		
		
		String dir = SDKConfig.getConfig().getValidateCertDir();
		CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
		File fileDir = new File(dir);
		File[] files = fileDir.listFiles(new CerFilter());
		HashMap<String, PublicKey> certMap = new HashMap<String, PublicKey>();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			FileInputStream in = new FileInputStream(file.getAbsolutePath());
			X509Certificate validateCert = (X509Certificate) cf.generateCertificate(in);
			certMap.put(validateCert.getSerialNumber().toString(),
					validateCert.getPublicKey());
		}
		
		PublicKey publicKey = certMap.get(certId);
		
		String stringData = SDKUtil.coverMap2String(param);
		boolean validate = SecureUtil.validateSignBySoft(
				publicKey, 
				SecureUtil.base64Decode(signature.getBytes(encoding)), 
				SecureUtil.sha1X16(stringData,encoding));
		
		try{
			iReceive.unionpayReceive(order, queryId, "", stringData);
			return "success";
		}catch(Exception e){
			return "faile";
		}

	}
	

}
