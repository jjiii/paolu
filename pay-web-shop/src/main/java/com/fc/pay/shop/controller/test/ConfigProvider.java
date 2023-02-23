package com.fc.pay.shop.controller.test;

import java.util.Properties;

public class ConfigProvider {
	
	public static Properties getConfigProperties(){
		String merchantAppCode = "1481083760950";
		String mchPriKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM891r14b7Za8TQzXa8nlSTQS7Lhk7zLTp1jDV2VQpJRvvUk8T8gm8iNZUqmAXOGBbD3XLvD4IFIR4Kfb7TxYSUc5I9SF67GyLTfNDo9nWZA0d/JY7+S2RMnNecR8zzwVRZ9s+PnofdpO1y1becrZKiuvTCCyNM/7LyPoy1jBrBRAgMBAAECgYBJ41xatUPvGkGBQQcX2d78/x7eKjsIRtxClPxA72SK0oZo1MEGptOkD3WbxdAiphgFXkw7zbyPm3uTgNDNDHp7onvbJiX2ZccGFB3XOIHcImA9qNLDv0zBunObgR0A4A/dX5LVs45A7qyX4lUemSG8UY+TLhPoG+vbOvCpTXIkAQJBAPItmU8s4rX/14cfReES3Fx7uLg13CDxhOcTnKa5LKMg6bMKUOgCctA3KFp4F4zbG1foWndgeUOWflfe5GyYgCECQQDbEcp+Zux0Yb9bEBNMNGqvuKXAbZ/uzGUsggaG6xIFXhliNGrwVDNQtiATjy+ivaS0jQsvdpIJ93XSfWV9uOoxAkEAryCKXNb4muQH146l2bzp3XcafRt1s9zzXqawAWCnBtAmL3KmTyB2Jlu8sq9lWFaZz1gXE3yQNG//rUtho6YkAQJAX0lbxKNMEks6CofxfOIHzasJHawRNrNdBoEACctRqVgGvnqCUUquj1rIlPlhBO5sJ8R5qwlt+DvU2duFO6wk4QJAUiWGgPCJgAfFAFTX2p35xFbA5QGqiOsLZxyD5E1LpgghzchRWEVmL3P8anhJFAjMKVIut+7d3mMqr1FTQ6dNag==";
		String fcpayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyuYHA9HoUeGYeERk6TWatwLVT25V2HDlycTzp9TM1IhCcN8r4YHrENLZjcwUau+JmiaX65uNNEi+d7kZT9/5yX2NzltqX8o86Mue2xubS04GosMbS7smgdO4ISc5bOJpMVBc1M7VByAQn39HoilJhV0wlVCRjbAv0jnKpTc+jOQIDAQAB";
		String version = "1.0";
		
		Properties prop = new Properties();
		prop.put("merchantAppCode", merchantAppCode);
		prop.put("mchPriKey", mchPriKey);
		prop.put("fcpayPublicKey", fcpayPublicKey);
		prop.put("version", version);

		String trustStorePath = "F:/work/201701-cipher/openssl/ca/certs/ca.jks";
		Object trustStoreType = "jks";
		Object trustStorePassword = "123456";
		Object keyStorePath = "F:/work/201701-cipher/openssl/ca/certs/client.p12";
		Object keyStoreType = "PKCS12";
		Object keyStorePassword = "123456";
		prop.put("trustStorePath", trustStorePath);		
		prop.put("trustStoreType", trustStoreType);		
		prop.put("trustStorePassword", trustStorePassword);		
		prop.put("keyStorePath", keyStorePath);		
		prop.put("keyStoreType", keyStoreType);		
		prop.put("keyStorePassword", keyStorePassword);
		
		return prop;
	}

}
