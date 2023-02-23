package com.fc.pay.geteway.controller;


import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.pay.common.core.utils.PathUtil;



@Controller
public class TestController {
	
	@Autowired 
	@Qualifier("jdbcTemplate_pay")
	private JdbcTemplate jdbcTemplate;
	
//	@RequestMapping("/test")
//	@ResponseBody
//	public String test() {
//		jdbcTemplate.update("delete from merchant_app where merchant_app_code = '1481083760950' limit 10000");
//		jdbcTemplate.update("INSERT INTO merchant_app (id,creater,create_time,editor,edit_time,version,merchant_code,merchant_name,merchant_app_code,merchant_app_name,pri_key,pub_key,mct_pub_key,status) VALUES (1,'系统',now(),null,null,0, 	'1','枫车电子商务有限责任公司','1481083760950','商城','MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKQ18E+bHFDkqORJEc4RTcT1ZNWcPWoXIsHwuBwuWNE9Mi9WAypx/yQzRdpeHFs6GLgHsuaUdFVfI+JtVCpJiNrHeDCDLw6th9mVQAKO1ADej95GJUwxw42RlaOf5DGskT4rPGdnRYbhmsHvLSM48efQcWsea4fLemIW0Zu3WfORAgMBAAECgYEAi0MjVp/ZV9FUWqaNbxHFvq/OoEos23hbhda8jUnREUKw+OwdKzYah3zkVNlTexq+M/RziJGGR5C0QicuJd7PLDnJXbuj+DHFohQ7xhDdalFvAPS90A0OerMPS5S67gdN4+hQ2CplI6iTYSx7fXSlLz2JndmnmmGZQIVoYwELKAECQQD34xLKFEzfIFtYyZ0rQ083B0QCVOqVKCHGAxFdNq7Y3U/rDj3seWjGtNGVw0xWtOm617b7qGBKSkE7pNO+NNfxAkEAqZXIFYw7BwfpH33Y3fBo7BUz5yXBBBBlP2T1xI3+VJjHCNTb9Jd4mGf8LZR2nkddnj8ZB0Rl079FsRJ4TT11oQJAIDId/qEkWtaZBq8o+rWDVGJdMvBUh4ru2AkN+E1h6EjWWHFBGbWrJiPWFN2IzLoyoVHjMkxsNTnvvw9pcQrsAQJAG1FKatYtCAPpNK50CcRZUnZT5w842Wu+s4iS4nexGXWG0lBfojXoMjzF7Z21wedjGEO3x2joMkTkeBKumFqnIQJBAJroElS6g3aJZS7IKRPArcFPMAW7LUm+C5urx1rbcw3iXF8atNPczcIDkvloXI/8OB82cQ7JLbb2BtLOVm9+GMk=','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkNfBPmxxQ5KjkSRHOEU3E9WTVnD1qFyLB8LgcLljRPTIvVgMqcf8kM0XaXhxbOhi4B7LmlHRVXyPibVQqSYjax3gwgy8OrYfZlUACjtQA3o/eRiVMMcONkZWjn+QxrJE+KzxnZ0WG4ZrB7y0jOPHn0HFrHmuHy3piFtGbt1nzkQIDAQAB','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkNfBPmxxQ5KjkSRHOEU3E9WTVnD1qFyLB8LgcLljRPTIvVgMqcf8kM0XaXhxbOhi4B7LmlHRVXyPibVQqSYjax3gwgy8OrYfZlUACjtQA3o/eRiVMMcONkZWjn+QxrJE+KzxnZ0WG4ZrB7y0jOPHn0HFrHmuHy3piFtGbt1nzkQIDAQAB',1)");
//		return "ok";
//	}
//	
//	
//	@RequestMapping("/test1")
//	@ResponseBody
//	public String test1() {
//		jdbcTemplate.update("delete from pay_payment_order limit 10000");
//		jdbcTemplate.update("delete from pay_trade_record limit 10000");
//		jdbcTemplate.update("delete from pay_refund_order limit 10000");
//		jdbcTemplate.update("delete from notify_record limit 10000");
//		return "oktest1";
//	}
//	
//	@RequestMapping("/test4")
//	@ResponseBody
//	public String test4() {
//		
//		jdbcTemplate.update("update merchant_app_config set status=1 where 1=1");
//		
//		return "Ok";
//	}
	
	@RequestMapping("/test2")
	@ResponseBody
	public String test2() {
		
		Integer i = jdbcTemplate.queryForObject("select count(1) from pay_payment_order", Integer.class);
		return i.toString();
	}
	
	@RequestMapping("/test3")
	@ResponseBody
	public List<Map<String, Object>> test3() {
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from notify_record");
		
		return list;
	}
	

	
	
	@RequestMapping("/test5")
	@ResponseBody
	public List<Map<String, Object>>  test5() {
		List<Map<String, Object>> list =  jdbcTemplate.queryForList("select * from pay_refund_order WHERE channel = 'alipay' and channel_app_id = '2017010904939710' and date_format(create_time,'%Y%m%d%H%i%s') between '20170331000000' AND '20170331235959'");
		return list;
	}
	
	
	@RequestMapping("/test6")
	@ResponseBody
	public List<Map<String, Object>>  test6() {
		return jdbcTemplate.queryForList("select * from merchant_app_config");
	}
	
	@RequestMapping("/test7")
	public void  test7(HttpServletResponse res,@RequestParam String path) {
		    try {
		    	path = PathUtil.readCertBasicPath() + path;
		    	System.err.println(path);
		    	File file = new File(path);
		    	String filename = file.getName();
		    	OutputStream os = res.getOutputStream(); 
		        res.reset();  
		        res.setHeader("Content-Disposition", "attachment; filename="+filename);  
		        res.setContentType("application/octet-stream; charset=utf-8");  
		        os.write(FileUtils.readFileToByteArray(file));  
		        os.flush();  
		    } catch (Exception e){
		    	 e.printStackTrace();
		    }
	}

	
}
