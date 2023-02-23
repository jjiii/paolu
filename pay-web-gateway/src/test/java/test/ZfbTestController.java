package test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * 支付宝SDK调用测试
 */
@Controller
@RequestMapping(value = "/trade")
public class ZfbTestController {


	/**
	 * 支付宝sdk测试
	 * @param httpResponse
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/tttt")
	public void tttt(Model model, HttpServletResponse httpResponse,
			HttpServletRequest request)
			throws Exception {
		AlipayClient alipayClient = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do", 
				"2017010904939710",
				"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIqX/uro+BetDcTfuyCtc/AJSipGrJ+HwrJozN06meeN2uE2Se5FcbSehZJ6R5W4HW/nLrl8E8IimNKJ4vlXzHYy7Wn5ASethFayfubpskLGCYMgfg49aWQnZW6z/G5pMBhrSxDsb1gC/fM9zyP+bD3ZuAOgz045gpRakXYKUb+fAgMBAAECgYANgKt8VYA+RkTuJmID2AJQo4cPQV12GXL1kNJMx3XRInftsy1WwntGfp9C2xj4VDzB90MNHTKLmnVLJdXwNwabY4YoFWtJ+c/RmBS3/k9Eu13MfcfyoEQN/E1qDaD5AP8IBpOTv99aepepJhuzVl5n7w2yU8o6EIHJM/4dARn9EQJBAPNs14AQPyP78rpqnj1fvEa8k5YV70bEGlWDyWTDDp8Tt/cpH1ZyZOab9Jw19vzm7ixGiDvEVeWU0QlGFlu5rvcCQQCRwNGEZJqM6K4RFdg1WmHTb74zqnXMZkAklEpLX8JC8+GIJ8jK0+tf+cK9eD2kZIrWeWfE7OfHmxnPIv/PB8KZAkBMS7f7ppvzrls0vIgeWmxLi32ClBtsBbjvRqAP2ecySt7lM9ljofUoJodzkqJ4P/U0oWK/wNjfXVZrXrFRTJCfAkAHa+L/M8Qu0pYyZoNYzB5ZDsKHjPFsk/RGKIrT5a9Vi2d7cFMoCLxeabBBx4KUAU6UdyASheGTZ1FcG7Mczo9ZAkAXEG7X2zd+Isjsie1pITI2MKKclO8LC8swd070xiJpeBh1NEfJXzqpLWcQNugREyzkEF/4QrukobCcF4Ekf6QH", 
				"json", "utf-8", 
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB"); // 获得初始化的AlipayClient
//		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
////		alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
////		alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");// 在公共参数中设置回跳和通知地址
//		alipayRequest.setBizContent("{"
//				+ "\"out_trade_no\":\"2015032001010100123\","
//				+ "\"total_amount\":0.01,"
//				+ "\"subject\":\"二锅头\"," 
//				+ "\"product_code\":\"QUICK_WAP_PAY\","
//				+ "\"extend_params\":\"{TRANS_MEMO:fc_pay}\""
//				+ "}");
//		
//		String form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
//		 model.addAttribute("form", form);
//		httpResponse.setContentType("text/html;charset=" + "utf-8");
//		
//		httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
//		httpResponse.getWriter().flush();
		
//		AlipayTradeQueryRequest query = new AlipayTradeQueryRequest();
//		
//		query.setBizContent(
//				"{"
//				+ "\"out_trade_no\":\"2015032001010100123\"" 
//				+ "}");
//		AlipayTradeQueryResponse response = alipayClient.execute(query);
//		System.out.println(response.isSuccess());
		
		
		//退款
//		AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
//		refundRequest.setBizContent("{" +
//				"    \"out_trade_no\":\"848102733914443776\"," +
//				"    \"refund_amount\":0.01," +
//				"    \"refund_reason\":\"正常退款\"," +
//				"    \"out_request_no\":\"848103209397522432\"" +
//
//				"  }");
//		AlipayTradeRefundResponse response = alipayClient.execute(refundRequest);
//		System.out.println(response.isSuccess());
	}
	public static void main(String[] args) throws Exception {
		
		AlipayClient alipayClient = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do", 
				"2017010904939710",
				"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIqX/uro+BetDcTfuyCtc/AJSipGrJ+HwrJozN06meeN2uE2Se5FcbSehZJ6R5W4HW/nLrl8E8IimNKJ4vlXzHYy7Wn5ASethFayfubpskLGCYMgfg49aWQnZW6z/G5pMBhrSxDsb1gC/fM9zyP+bD3ZuAOgz045gpRakXYKUb+fAgMBAAECgYANgKt8VYA+RkTuJmID2AJQo4cPQV12GXL1kNJMx3XRInftsy1WwntGfp9C2xj4VDzB90MNHTKLmnVLJdXwNwabY4YoFWtJ+c/RmBS3/k9Eu13MfcfyoEQN/E1qDaD5AP8IBpOTv99aepepJhuzVl5n7w2yU8o6EIHJM/4dARn9EQJBAPNs14AQPyP78rpqnj1fvEa8k5YV70bEGlWDyWTDDp8Tt/cpH1ZyZOab9Jw19vzm7ixGiDvEVeWU0QlGFlu5rvcCQQCRwNGEZJqM6K4RFdg1WmHTb74zqnXMZkAklEpLX8JC8+GIJ8jK0+tf+cK9eD2kZIrWeWfE7OfHmxnPIv/PB8KZAkBMS7f7ppvzrls0vIgeWmxLi32ClBtsBbjvRqAP2ecySt7lM9ljofUoJodzkqJ4P/U0oWK/wNjfXVZrXrFRTJCfAkAHa+L/M8Qu0pYyZoNYzB5ZDsKHjPFsk/RGKIrT5a9Vi2d7cFMoCLxeabBBx4KUAU6UdyASheGTZ1FcG7Mczo9ZAkAXEG7X2zd+Isjsie1pITI2MKKclO8LC8swd070xiJpeBh1NEfJXzqpLWcQNugREyzkEF/4QrukobCcF4Ekf6QH", 
				"json", "utf-8", 
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB"); // 获得初始化的AlipayClient

		
		
		AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
		refundRequest.setBizContent("{" +
				"\"out_trade_no\":\"848102733914443776\"," +
				"\"refund_amount\":0.01," +
				"\"refund_reason\":\"正常退款\"," +
				"\"out_request_no\":\"848103209397522432\"" +
				"}");
		AlipayTradeRefundResponse response = alipayClient.execute(refundRequest);
		System.out.println(response.isSuccess());
	}
	
	
}
