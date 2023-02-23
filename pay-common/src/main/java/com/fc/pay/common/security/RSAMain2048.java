package com.fc.pay.common.security;

import org.apache.commons.codec.binary.Base64;

public class RSAMain2048 {

	public static void main(String[] args) throws Exception {

		RSAKey key = new RSAKey();
		
		RSAAlgorithm algorithm = new RSAAlgorithm();
		
		RSASignature signature = new RSASignature();
		
		
    	String privatePemFilePath = "F:/com_svn/pay/trunk/pay/pay-common/src/main/java/com/fc/pay/common/security/pem/mch_rsa_private_key_2048_pkcs8.pem";
		String privatePemTextBase64 = key.readRSAKeyTextBase64FromOpenSSL(privatePemFilePath);
    	String publicPemFilePath = "F:/com_svn/pay/trunk/pay/pay-common/src/main/java/com/fc/pay/common/security/pem/mch_rsa_public_key_2048.pem";
		String publicPemTextBase64 = key.readRSAKeyTextBase64FromOpenSSL(publicPemFilePath);
    	System.out.println("私钥:\n"+privatePemTextBase64);
    	System.out.println("公钥:\n"+publicPemTextBase64);
    	
    	/** 演示：私钥加密公钥解密过程  */
        System.out.println("--------------私钥加密公钥解密过程-------------------");  
        //String plainText="ihep_私钥加密公钥解密";
        String plainText = "加密前hahahaha";
        //私钥加密过程  
        //String privateKeyTextBase64 = key.readRSAKeyTextBase64FromJavaBC("E:/study/workspace/openssl/src/main/resources/javabc/rsa_private_key.keystore");
        byte[] cipherData=algorithm.encrypt(key.buildRSAPrivateKey(privatePemTextBase64), plainText.getBytes("utf-8"));  
        String cipherTextBase64=Base64.encodeBase64String(cipherData);  
        //公钥解密过程  
        //String publicKeyTextBase64 = key.readRSAKeyTextBase64FromJavaBC("E:/study/workspace/openssl/src/main/resources/javabc/rsa_public_key.keystore");
        byte[] decryptData=algorithm.decrypt(key.buildRSAPublicKey(publicPemTextBase64), Base64.decodeBase64(cipherTextBase64.getBytes("utf-8")));  
        //byte[] decryptData=algorithm.decrypt(key.buildRSAPublicKey(publicPemTextBase64), Base64.decodeBase64(cipherTextBase64)); //不加加入utf-8，效果一样
        String decryptText=new String(decryptData,"utf-8");  
        System.out.println("原文："+plainText);  
        System.out.println("加密："+cipherTextBase64);  
        System.out.println("解密："+decryptText);  
        System.out.println();
        System.err.println("是否解密成功>>>"+plainText.equals(decryptText)); 
        
		
	    System.out.println("---------------私钥签名过程------------------");  
	    String content="ihep_这是用于签名的原始数据";  
	    //String signstr=signature.sign(content,key.readRSAKeyTextBase64FromJavaBC("E:/study/workspace/openssl/src/main/resources/javabc/rsa_private_key.keystore"));  
	    String signstr=signature.sign(content,privatePemTextBase64);
	    System.out.println("签名原串："+content);  
	    System.out.println("签名串："+signstr);  
	    System.out.println();
	    System.out.println("---------------公钥校验签名------------------");
	    System.out.println("签名原串："+content);  
	    System.out.println("签名串："+signstr);	      
	    //System.err.println("验签结果："+signature.doCheck(content, signstr, key.readRSAKeyTextBase64FromJavaBC("E:/study/workspace/openssl/src/main/resources/javabc/rsa_public_key.keystore")));  
	    System.err.println("验签结果："+signature.doCheck(content, signstr, publicPemTextBase64));

		
	}

}
