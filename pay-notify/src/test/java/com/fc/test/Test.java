package com.fc.test;

import java.io.IOException;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fc.pay.trade.entity.NotifyRecord;



@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:/spring/context.xml","classpath:/spring/mvc.xml"})  
public class Test {
	@Autowired
	private JmsTemplate jmsTemplate;
	
	
	@org.junit.Test
    public void sendNotify(){
		
		for(int i=0; i<100; i++){
			
			System.out.println("发送消息："+i);
			NotifyRecord notifyRecord = new NotifyRecord();
			notifyRecord.setUrl("http://localhost:8081/pay-shop/test");
			notifyRecord.setNotifyTimesLimit(1);
			notifyRecord.setId(0L);
			jmsTemplate.send("oderSuccess", new MessageCreator(){
				public Message createMessage(Session session)
						throws JMSException {
					return session.createObjectMessage(notifyRecord);
				}
			});
	        
		}
		
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		System.out.println(line);
    }
	
	public static void main(String[] args) throws Exception{
//		Integer return_code = 
		try {
//				Form from = Form.form().add("username", "vip").add("password", "secret");
//			    Form.form();
			    
//			    String data = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><xml><time_expire>20170919121156</time_expire><nonce_str>88d4a70ad87c4854bb3210a936b2fb08</nonce_str><openid>oRADx0EmOZ18cesxhAzTST2oo1iw</openid><sign>CB140D99D16A82C500C0B7A2869570BE690EF129B663860316E32B89227726B3</sign><fee_type>CNY</fee_type><body>输入金额支付</body><notify_url>http://192.168.1.205:7980/api/ssms-open/service/wechat/receive/weixin<;/notify_url><mch_id>10058454</mch_id><spbill_create_ip>192.168.0.1</spbill_create_ip><out_trade_no>107483449442570240</out_trade_no><total_fee>2700</total_fee><appid>wxf34e581d4a394f92</appid><trade_type>JSAPI</trade_type><sign_type>HMAC-SHA256</sign_type></xml>";
			String data = "11111111111111111111<?  s//> <<<<11111111111";
			    StringEntity postEntity = new StringEntity(data, "UTF-8");
			    
			  
			    String s = Request.Post("http://test.carisok.com/ssms-services/api/ssms-open/service/wechat/receive/weixin")
//		    			.bodyForm(from.build())
			    		.body(postEntity)
		    			.execute()
		    			.returnContent()
		    			.asString();
			    System.out.println(s);
			
		} catch (Exception e) {
		
		
			e.printStackTrace();
		}
		
		
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//        try {
//            HttpHost target = new HttpHost("httpbin.org", 443, "https");
//            HttpHost proxy = new HttpHost("127.0.0.1", 8080, "http");
//
//            RequestConfig config = RequestConfig.custom()
//                    .setProxy(proxy)
//                    .build();
//            HttpGet request = new HttpGet("/");
//            request.setConfig(config);
//
//            System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);
//
//            CloseableHttpResponse response = httpclient.execute(target, request);
//            try {
//                System.out.println("----------------------------------------");
//                System.out.println(response.getStatusLine());
//                System.out.println(EntityUtils.toString(response.getEntity()));
//            } finally {
//                response.close();
//            }
//        } finally {
//            httpclient.close();
//        }
		
	}
}
