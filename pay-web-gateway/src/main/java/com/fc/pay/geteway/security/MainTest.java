package com.fc.pay.geteway.security;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.pay.bill.enums.BillTypeEnum;
import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.utils.Return;

public class MainTest {
	
	public static void main(String[] args){
		
		
		/**
		ObjectMapper mapper = new ObjectMapper();

        // here we set BigDecimal as type for floating numbers
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        MyItem i = new MyItem();
        i.setDesc("Test");
        i.setValue(1.00);

        String s = null;

        try {
            s = mapper.writeValueAsString(i);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("JAVA to JSON: " + s);

        MyItem i2 = null;
        try {
            i2 = mapper.readValue(s, MyItem.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\nJSON to JAVA: " + i2);
        System.out.println("\n\"Value\" field type: : " + i2.getValue().getClass());
        
        */
		
        /**
        //String respData = "{\"code\":\"_00011\",\"msg\":\"对账单不存在\",\"version\":\"1.0\",\"result\":{\"timestamp\":1487559358848},\"sign\":\"\"}";
        String respData = "{\"code\":\"_00011\",\"msg\":\"对账单不存在\",\"version\":\"1.0\",\"result\":{\"timestamp\":1487559358848},\"sign\":null}";
        try{
		JsonReader jsonReader = Json.createReader(new StringReader(respData));
		JsonObject jsonObject = jsonReader.readObject();
		String data = jsonObject.getJsonObject("result").toString();
		//String sign = jsonObject.getString("sign");
		JsonValue signValue = jsonObject.get("sign");
		if(signValue == JsonValue.NULL){
			System.out.println("签名为null");
		}else{
			System.out.println("signValue取值=>"+signValue.toString());
		}
		JsonValue resultValue = jsonObject.getJsonObject("result");
		System.out.println("resultValue.getValueType()=>"+resultValue.getValueType());
		if(resultValue == JsonValue.NULL){
			System.out.println("resultValue为null");
		}else{
			System.out.println("resultValue取值=>"+resultValue.toString());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		Map bizData = new HashMap();
		bizData.put("notifyId", "1001");
		String billType = "pay";
		bizData.put("billType", billType);
		bizData.put("orderNo", "20170208000001"); 
		bizData.put("mistakeType", "local_status_less");
		bizData.put("suggestion", "查询订单更正状态与金额");	
        
		Return notifyData = new Return();
		notifyData.setCode("10000");
		notifyData.setMsg("通知成功");
		MerchantApp merchantApp = new MerchantApp();
		merchantApp.setPriKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALK5gcD0ehR4Zh4RGTpNZq3AtVPblXYcOXJxPOn1MzUiEJw3yvhgesQ0tmNzBRq74maJpfrm400SL53uRlP3/nJfY3OW2pfyjzoy57bG5tLTgaiwxtLuyaB07ghJzls4mkxUFzUztUHIBCff0eiKUmFXTCVUJGNsC/SOcqlNz6M5AgMBAAECgYEAmetLRNsHnEOIZpKBMIXiTPdu8lZk5MAv9VByjma+gB7jQTaHldq5P+rJvOIc3kY7F+WpzWg1D3X/Djtb1Ar61SKpamVDqOD/LavwbwkbCR+E7eamzGSu/GgWzTQ1oLzGxakfvfBt5nAcZKqHe8LGd8umCVZJGDKoVulTpSLHVAECQQDbIMcN5955E0pDLkvHdWY7MVdF9xU9z+AJOCLzKWbx551hUpEBbA46ta2ZDX5mSjI/vu/eEIWD0shnJLFpHnWBAkEA0MxM/d6SWwkgV0rOQ5CHcHegi2pgcaoYWFsP+V0b2lnmQLCWtyyEpVi5jfIJuoDLgoy8uBxtlNYeetmNfiM5uQJAcnQ5ZEsDCeyIcnShAiqQ3kQUWKgJAUMtusMGhknOynV235mXwc1l1UaFyRaiOd1xR5h8g1nP0x3qrO0eeVB+gQJBAMbAssk2BLsDhPWTD6Tg/wvf08LiD8wizenNRPdp2gmPac5KSi0zA1Ehk/+6VehikVZEAaB/7w+ugnUOgdGAVoECQFRg8tSJU7Wb8UD1h1IC+jnYT8uLGTbqs3JZmXMifrqkkZm1OGx1uyp6I6aMrIbKjKP9FPAB48547D9SGkTaDeA=");
		notifyData.setResult(bizData , merchantApp);
		try {
			String dataJson = new ObjectMapper().writeValueAsString(notifyData).toString();
			System.out.println(dataJson);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
