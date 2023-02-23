package com.fc.pay.common.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * RSA签名
 * @author zhanjq
 *
 */
public class RSASignature {
	
	/**
	 * 默认签名算法
	 */
	public static final String DefaultSignAlgorithm = "SHA1WithRSA";//1024
	//public static final String DefaultSignAlgorithm = "SHA256WithRSA";//2048
	
	/**
	 * 加载算法提供者
	 * BouncyCastleProvider
	 */
	static{
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * 签名
	 * @param content		待签名数据
	 * @param privateKey	私钥
	 * @param encoding		字符集编码
	 * @return 签名值
	 * @throws Exception
	 */
	public String sign(String content, String privateKey, String encoding) throws Exception {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
				Base64.decodeBase64(privateKey));
		KeyFactory keyf = KeyFactory.getInstance("RSA", "BC");
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);
		//java.security.Signature signature = java.security.Signature.getInstance(DefaultSignAlgorithm);
		java.security.Signature signature = java.security.Signature.getInstance(DefaultSignAlgorithm,"BC");
		signature.initSign(priKey);
		signature.update(content.getBytes(encoding));
		byte[] signed = signature.sign();
		return Base64.encodeBase64String(signed);
	}

	/**
	 * 签名
	 * @param content		待签名数据
	 * @param privateKey	私钥
	 * @return 签名值
	 * @throws Exception
	 */
	public String sign(String content, String privateKey)  throws Exception {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
				Base64.decodeBase64(privateKey));
		KeyFactory keyf = KeyFactory.getInstance("RSA", "BC");
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);
		//java.security.Signature signature = java.security.Signature.getInstance(DefaultSignAlgorithm);
		java.security.Signature signature = java.security.Signature.getInstance(DefaultSignAlgorithm,"BC");
		signature.initSign(priKey);
		signature.update(content.getBytes());
		byte[] signed = signature.sign();
		return Base64.encodeBase64String(signed);
	}

	/**
	 * 验签
	 * @param content	待签名数据
	 * @param sign		签名值
	 * @param publicKey	公钥
	 * @param encoding	字符集编码
	 * @return
	 * @throws Exception
	 */
	public boolean doCheck(String content, String sign, String publicKey, String encoding)  throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
		byte[] encodedKey = Base64.decodeBase64(publicKey);
		PublicKey pubKey = keyFactory
				.generatePublic(new X509EncodedKeySpec(encodedKey));
		//java.security.Signature signature = java.security.Signature.getInstance(DefaultSignAlgorithm);
		java.security.Signature signature = java.security.Signature.getInstance(DefaultSignAlgorithm,"BC");
		signature.initVerify(pubKey);
		signature.update(content.getBytes(encoding));
		return signature.verify(Base64.decodeBase64(sign));
	}

	/**
	 * 验签
	 * @param content	待签名数据
	 * @param sign		签名值
	 * @param publicKey	公钥
	 * @return
	 * @throws Exception
	 */
	public boolean doCheck(String content, String sign, String publicKey)  throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
		byte[] encodedKey = Base64.decodeBase64(publicKey);
		PublicKey pubKey = keyFactory
				.generatePublic(new X509EncodedKeySpec(encodedKey));
		//java.security.Signature signature = java.security.Signature.getInstance(DefaultSignAlgorithm);
		java.security.Signature signature = java.security.Signature.getInstance(DefaultSignAlgorithm,"BC");
		signature.initVerify(pubKey);
		signature.update(content.getBytes());
		return signature.verify(Base64.decodeBase64(sign));
	}

	public static void main(String[] args) throws Exception{
		
		/**
		validStr=>
		amount=8000.00&channel=alipay&merchantAppCode=1481083760950&merchantOrderNo=2017
		02280001&notifyUrl=&orderIp=127.0.0.1&payWay=app&productName=iphone7&remark=&ret
		urnUrl=&timestamp=1488265420749&version=1.0
		sign=>
		ekPH6ZF+XF/dYwNLbaefRl6VhMBuIySF56g9R/Mpi7S2ThhGRI/szWjEuy6gsslYDRqs2rMiliY4YaEC
		1vrMMkhNPXIKr9PU3D541WSWWVO2oYAwD+yKV9Jj5a1+V+nB5uUIn/xtn9o8EVWVZtrS0bQ9qjzcDqpg
		RixVSi67ep8=
		bizPublicKey
		MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkNfBPmxxQ5KjkSRHOEU3E9WTVnD1qFyLB8LgcLljR
		PTIvVgMqcf8kM0XaXhxbOhi4B7LmlHRVXyPibVQqSYjax3gwgy8OrYfZlUACjtQA3o/eRiVMMcONkZWj
		n+QxrJE+KzxnZ0WG4ZrB7y0jOPHn0HFrHmuHy3piFtGbt1nzkQIDAQAB
		*/
		
		RSAKey key = new RSAKey();
		RSASignature signature = new RSASignature();
	    System.out.println("---------------私钥签名过程------------------");  
	    String content="amount=8000.00&channel=alipay&merchantAppCode=1481083760950&merchantOrderNo=201702280001&notifyUrl=&orderIp=127.0.0.1&payWay=app&productName=iphone7&remark=&returnUrl=&timestamp=1488265420749&version=1.0";  
	    String mchPriKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM891r14b7Za8TQzXa8nlSTQS7Lhk7zLTp1jDV2VQpJRvvUk8T8gm8iNZUqmAXOGBbD3XLvD4IFIR4Kfb7TxYSUc5I9SF67GyLTfNDo9nWZA0d/JY7+S2RMnNecR8zzwVRZ9s+PnofdpO1y1becrZKiuvTCCyNM/7LyPoy1jBrBRAgMBAAECgYBJ41xatUPvGkGBQQcX2d78/x7eKjsIRtxClPxA72SK0oZo1MEGptOkD3WbxdAiphgFXkw7zbyPm3uTgNDNDHp7onvbJiX2ZccGFB3XOIHcImA9qNLDv0zBunObgR0A4A/dX5LVs45A7qyX4lUemSG8UY+TLhPoG+vbOvCpTXIkAQJBAPItmU8s4rX/14cfReES3Fx7uLg13CDxhOcTnKa5LKMg6bMKUOgCctA3KFp4F4zbG1foWndgeUOWflfe5GyYgCECQQDbEcp+Zux0Yb9bEBNMNGqvuKXAbZ/uzGUsggaG6xIFXhliNGrwVDNQtiATjy+ivaS0jQsvdpIJ93XSfWV9uOoxAkEAryCKXNb4muQH146l2bzp3XcafRt1s9zzXqawAWCnBtAmL3KmTyB2Jlu8sq9lWFaZz1gXE3yQNG//rUtho6YkAQJAX0lbxKNMEks6CofxfOIHzasJHawRNrNdBoEACctRqVgGvnqCUUquj1rIlPlhBO5sJ8R5qwlt+DvU2duFO6wk4QJAUiWGgPCJgAfFAFTX2p35xFbA5QGqiOsLZxyD5E1LpgghzchRWEVmL3P8anhJFAjMKVIut+7d3mMqr1FTQ6dNag==";
	    String signstr=signature.sign(content,mchPriKey);
	    System.out.println("签名原串："+content);  
	    System.out.println("签名串1："+signstr);  
	    System.out.println();
	    System.out.println("---------------公钥校验签名------------------");
	    System.out.println("签名原串："+content);  
	    String signStr = "ekPH6ZF+XF/dYwNLbaefRl6VhMBuIySF56g9R/Mpi7S2ThhGRI/szWjEuy6gsslYDRqs2rMiliY4YaEC1vrMMkhNPXIKr9PU3D541WSWWVO2oYAwD+yKV9Jj5a1+V+nB5uUIn/xtn9o8EVWVZtrS0bQ9qjzcDqpgRixVSi67ep8=";
	    System.out.println("签名串2："+signStr);	      
	    System.err.println("验签结果："+signature.doCheck(content, signstr, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPPda9eG+2WvE0M12vJ5Uk0Euy4ZO8y06dYw1dlUKSUb71JPE/IJvIjWVKpgFzhgWw91y7w+CBSEeCn2+08WElHOSPUheuxsi03zQ6PZ1mQNHfyWO/ktkTJzXnEfM88FUWfbPj56H3aTtctW3nK2Sorr0wgsjTP+y8j6MtYwawUQIDAQAB"));
		
		/** 2048长度密钥 */
	    /**
		RSAKey key = new RSAKey();
		RSASignature signature = new RSASignature();
	    System.out.println("---------------私钥签名过程------------------");  
	    String content="ihep_这是用于签名的原始数据";  
	    String signstr=signature.sign(content,key.readRSAKeyTextBase64FromOpenSSL("F:/com_svn/pay/trunk/pay/pay-common/src/main/java/com/fc/pay/common/security/pem/mch_rsa_private_key_2048_pkcs8.pem"));  
	    System.out.println("签名原串："+content);  
	    System.out.println("签名串："+signstr);  
	    System.out.println();
	    System.out.println("---------------公钥校验签名------------------");
	    System.out.println("签名原串："+content);  
	    System.out.println("签名串："+signstr);	      
	    System.err.println("验签结果："+signature.doCheck(content, signstr, key.readRSAKeyTextBase64FromOpenSSL("F:/com_svn/pay/trunk/pay/pay-common/src/main/java/com/fc/pay/common/security/pem/mch_rsa_public_key_2048.pem")));
	    */
	    
		/**
		RSAKey key = new RSAKey();
		RSASignature signature = new RSASignature();
	    System.out.println("---------------私钥签名过程------------------");  
	    String content="ihep_这是用于签名的原始数据";  
	    String signstr=signature.sign(content,key.readRSAKeyTextBase64FromJavaBC("E:/study/workspace/openssl/src/main/resources/javabc/rsa_private_key.keystore"));  
	    System.out.println("签名原串："+content);  
	    System.out.println("签名串："+signstr);  
	    System.out.println();
	    System.out.println("---------------公钥校验签名------------------");
	    System.out.println("签名原串："+content);  
	    System.out.println("签名串："+signstr);	      
	    System.err.println("验签结果："+signature.doCheck(content, signstr, key.readRSAKeyTextBase64FromJavaBC("E:/study/workspace/openssl/src/main/resources/javabc/rsa_public_key.keystore")));  
		*/
	}

}
