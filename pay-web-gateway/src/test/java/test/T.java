package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.mybatis.generator.api.dom.java.Field;

import com.fc.pay.common.core.utils.PathUtil;

public class T {

	public static void main(String[] args) throws Exception  {
//		Cat cat = new Cat();
//		Map map = new HashMap<String, Object>();
//		map.put("name", "2");
//		map.put("date", new Date());
//		map.put("muney", new BigDecimal(5));
//		map.put("kao ", "...");
//		
//		BeanUtils.populate(cat, map);
//		System.out.println(cat.getName());
//		System.out.println(cat.getDate());
//		System.out.println(cat.getMuney());
		
		
		
		Calendar todayStart = Calendar.getInstance();  
		todayStart.set(Calendar.DATE, -1);
        todayStart.set(Calendar.HOUR, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
System.out.println(todayStart.getTime().getTime());		
		
		

		/*Form form = Form.form().add("productName", "555555").add("channel", "alipay");
		String respData = Request.Post("http://test.carisok.com/pay-web-gateway/trade/pay")
				.connectTimeout(2000)
		        .socketTimeout(2000)
    			.bodyForm(form.build())
    			.execute()
    			.returnContent()
    			.asString();
		System.err.println(respData);*/

//	String path = "F:/weixin/apiclient_cert.p12";
//	File file = new File(path);
//	FileInputStream in = new FileInputStream(file);
//	File ofile = new File("F:/weixin/55555.p12");
//	FileOutputStream out = new FileOutputStream(ofile);
//	byte[] buffer = new byte[1024];
//	while(true){
//		if(in.read(buffer)!=-1){
//			out.write(buffer);
//		}else{
//			break;
//		}
//	}
//	in.close();
//	out.flush();
	
	}


}
