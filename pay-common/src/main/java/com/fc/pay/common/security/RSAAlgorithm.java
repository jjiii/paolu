package com.fc.pay.common.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * RSA算法
 * @author zhanjq
 *
 */
public class RSAAlgorithm {
	
    /** 
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  //1024
	//private static final int MAX_ENCRYPT_BLOCK = 255;  //2048
	
    /** 密钥长度与加密快必须匹配，1024对128，2048对256，否则报错
    Exception in thread "main" javax.crypto.BadPaddingException: unknown block type
	at org.bouncycastle.jce.provider.JCERSACipher.engineDoFinal(Unknown Source)
	at javax.crypto.Cipher.doFinal(Cipher.java:2179)
	at com.fc.pay.common.security.RSAAlgorithm.decrypt(RSAAlgorithm.java:300)
	at com.fc.pay.common.security.RSAMain2048.main(RSAMain2048.java:33)
     */
      
    /** 
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128; //1024
	//private static final int MAX_DECRYPT_BLOCK = 256; //2048
    
	/**
	 * 加载算法提供者
	 * BouncyCastleProvider
	 */
	static{
		Security.addProvider(new BouncyCastleProvider());
	}  
    
    /**
     * 公钥加密
     * @param publicKey
     * @param plainTextData
     * @return
     * @throws Exception
     */
    public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)  
            throws Exception {  
        /**
    	if (publicKey == null) {  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
        */
    	
        //Cipher cipher = Cipher.getInstance("RSA", "BC");
    	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        int inputLen = plainTextData.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(plainTextData, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
        return encryptedData;
        
    }  
  
    /**
     * 私钥加密
     * @param privateKey
     * @param plainTextData
     * @return
     * @throws Exception
     */
    public byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)  
            throws Exception {  
        /**
    	if (privateKey == null) {  
            throw new Exception("加密私钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("加密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
        */

        //Cipher cipher = Cipher.getInstance("RSA", "BC"); 
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
        int inputLen = plainTextData.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(plainTextData, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();       
        return encryptedData;
        
    }  
  
    /**
     * 私钥解密
     * @param privateKey
     * @param cipherData
     * @return
     * @throws Exception
     */
    public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {  
    	
    	/**
        if (privateKey == null) {  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }
        */
    	
        //Cipher cipher = Cipher.getInstance("RSA", "BC");
    	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        int inputLen = cipherData.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher  
                        .doFinal(cipherData, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher  
                        .doFinal(cipherData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;
    }  
  
    /**
     * 公钥解密
     * @param publicKey
     * @param cipherData
     * @return
     * @throws Exception
     */
    public byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception { 
    	
    	/*
    	javax.crypto.IllegalBlockSizeException: Data must not be longer than 128 bytes
			at com.sun.crypto.provider.RSACipher.doFinal(RSACipher.java:335)
			at com.sun.crypto.provider.RSACipher.engineDoFinal(RSACipher.java:380)
			at javax.crypto.Cipher.doFinal(Cipher.java:2121)
			at me.zhanjq.java.algorithm.RSAAlgorithm.decrypt(RSAAlgorithm.java:190)
			at me.zhanjq.java.openssl.php.OpenSSLPhpMain.main(OpenSSLPhpMain.java:45)
		Exception in thread "main" java.lang.Exception: 密文长度非法
			at me.zhanjq.java.algorithm.RSAAlgorithm.decrypt(RSAAlgorithm.java:201)
			at me.zhanjq.java.openssl.php.OpenSSLPhpMain.main(OpenSSLPhpMain.java:45)
    	 */
    	
    	/**
        if (publicKey == null) {  
            throw new Exception("解密公钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("解密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) { 
        	e.printStackTrace();
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }  
        */
    	
    	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
    	//Cipher cipher = Cipher.getInstance("RSA", "BC");
    	//Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);  
        int inputLen = cipherData.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(cipherData, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(cipherData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;  
    }  
    
    
    public static void main(String[] args) throws Exception{
    	
    	RSAKey key = new RSAKey();
    	
    	RSAAlgorithm rsa = new RSAAlgorithm();
    	
    	/** 预备：生成RSA密钥对 */
    	//rsa.makeRSAKeyPair("E:/study/workspace/openssl/src/main/resources/javabc/", "rsa_public_key.keystore", "rsa_private_key.keystore");
    	
    	/** 演示：公钥加密私钥解密过程 */
    	/** 2048长度*/
    	/**
        System.out.println("--------------公钥加密私钥解密过程-------------------");  
        String plainText="ihep_公钥加密私钥解密";  
        //公钥加密过程  
        String publicKeyTextBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvWO3LAygNLjDdxvTO9SSKW83fUNuZo3yy4swiSw2klHH/qZhRefkbv9OXNsbd6ce56XbjMdsUmA//Okas2gV0NWuLprd+70mDaJtU6DNCLgv6YA83a4W+IQpyldT8p7zvTrJXcYoWMx6R6hsl9boR/5+0DonlV5b5+7Bl1kbQcHu3obcotLlDPba5jic6UlcE1eW+vRRDaHXT8b1SHHUC5EzhA7ApY5RJFdt7fsefzV4u9BuN3tHU9hfDzkifUdlpmSjroQ2RoXhCVIQHon4vsAHIG4pwEup2sMvz3B+oJxc1T0OfuTvhWP5snGvwsGWdC32Cui/ESe60FcoiobTEQIDAQAB";
        //来自java-keystore
        //String publicKeyTextBase64 = rsa.readRSAKeyTextBase64("E:/study/workspace/openssl/src/main/resources/javabc/rsa_public_key.keystore");
        byte[] cipherData=rsa.encrypt(key.buildRSAPublicKey(publicKeyTextBase64), plainText.getBytes("utf-8"));
        String cipherTextBase64=Base64.encodeBase64String(cipherData);  
		//私钥解密过程  
        String privateKeyTextBase64 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9Y7csDKA0uMN3G9M71JIpbzd9Q25mjfLLizCJLDaSUcf+pmFF5+Ru/05c2xt3px7npduMx2xSYD/86RqzaBXQ1a4umt37vSYNom1ToM0IuC/pgDzdrhb4hCnKV1PynvO9OsldxihYzHpHqGyX1uhH/n7QOieVXlvn7sGXWRtBwe7ehtyi0uUM9trmOJzpSVwTV5b69FENoddPxvVIcdQLkTOEDsCljlEkV23t+x5/NXi70G43e0dT2F8POSJ9R2WmZKOuhDZGheEJUhAeifi+wAcgbinAS6nawy/PcH6gnFzVPQ5+5O+FY/myca/CwZZ0LfYK6L8RJ7rQVyiKhtMRAgMBAAECggEAZYrLMQDr/Jh7pKb0kIWegDyzIaQ/0xiUbvGH6mQcNkNG2Y/XY6W7Yo/cMgkAJx0hgs34cviRaXFVnYJV4I8T8049/5rlHg4CExldSJNUmzI17hnliG3IvyINTgGYbuMuh+y2/mI6S7IGddklsBt3mvImh/pGMt3OpQHsJfuOk6g89wacSVQZ15ZrjCmuY3C9rKVUqFG2kuf0GLblhhLl7QBHdcbDAQiNuXN4I2rCi4LJcvfEBeBZu/2bjU9njVLEHhS9zi65ExTpsJ59VrsHqpReUBYxcCjsByHN9mMM8p6S/e2rJtzB/NxugYNhRppjjnj4Y71goaqeCgjc2q4IAQKBgQDic4jxwXgJZca520Y2NL6/CIIti2OGCHow0WhS2ZCIQSLlY8XBVMl4UFDfMN2CZkjFs0D7w/TUEGYLV+vvESdSwrYcMfgeyD7LFArQl6uwmb7lBTQ1ZgYV3Oh20h5ndaKnIxodhur7oj8NYjp/e4I0bN+fMErIJD0ofNlBBzJx0QKBgQDWGieF5yMHDFMBClNw2mdQZxc0njg5KKG4S2+1yoPHU0974QfBuZFRZ3oFqWix7ETcuSOOJ1DF6IrLlf/lEXJnmYXn4d72kGAGoflSddxxZfKZbQoe+HfCeRzaWyMAYYw5pfNBt8qPq357WAbKymPeKw9IpFdhHLcM3VA/Hi9dQQKBgQDQMKAKg3A0U5msbWfTlhJ31v63mJrbMxgDocJjyRoT6q6Gbn/1ClpffpebWnWL2TW2bOKllLaV8K2gaCjFFeQtNk3vBhGzWy4/ziFTPN3f8rmKHIqvPSkDGKGv++VwALqVJrRvyQZoafXF/DUD1y0Rvbapx+A4uPhxLLcgDdnWwQKBgEPrLBqIRk5YFITB99JXFmslKPFiN8e6IgNKuvdadQ9oWFCeBE3qRjUitEzTfwjiQL4cZTEVO8Rsjosi3HiYXkiLJTV4K7jf9/Zir6r/BJ1cj+cn9gv5GlSxAvwp6SKXz1oCUPbKDRIifw81z4HJToEN+905dRaze0Yzdt9u/euBAoGAA5NIssNqnH6PFLdSPWUdRDoZxjC5iE7vjFXGt+lnuopaMLuxfAVlKfr0WwKA/O36GCn1peTfk3vf+b+rhkvljJ7dZtekKUZxmzor11LWZgowXFYMJt5e9TpW8aIahxsd4OuCLDL/0/NTyiHKpF3XrHxT+T9iNWk8k9OoB/neFqQ=";
        //String privateKeyTextBase64 = rsa.readRSAKeyTextBase64("E:/study/workspace/openssl/src/main/resources/javabc/rsa_private_key.keystore");
        byte[] decryptData = rsa.decrypt(key.buildRSAPrivateKey(privateKeyTextBase64), Base64.decodeBase64(cipherTextBase64));
        String decryptText = new String(decryptData, "utf-8");  
        System.out.println("原文："+plainText);  
        System.out.println("原文byte[]>>>"+Arrays.toString(plainText.getBytes("utf-8")));  
        System.out.println();
        System.out.println("密文byte[]>>>"+Arrays.toString(cipherData));
        System.out.println("加密："+cipherTextBase64);  
        System.out.println();
        System.out.println("明文byte[]>>>"+Arrays.toString(decryptData));
        System.out.println("解密："+decryptText);  
        System.out.println(); 
        System.err.println("是否解密成功>>>"+plainText.equals(decryptText));
    	*/
    	
    	/** 1024长度*/
    	/***/
        System.out.println("--------------公钥加密私钥解密过程-------------------");  
        String plainText="ihep_公钥加密私钥解密";  
        //公钥加密过程  
        String publicKeyTextBase64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCt5g5fHYRXEGMTORhr9ykreG0iLWvBX84154W56kuPWpKJsQB3bE0pBH75rQI0PlX/B70INuQ5vuQk2bXu8XRD2MfSwMTJaMIUMRTZVfjZjV+5tcXuFzxT7R1m3hk0GURFkiBVm8QIhxVrtTfLjPhMjpvkYAF6U3mt6MtDASx8pQIDAQAB";
        //来自java-keystore
        //String publicKeyTextBase64 = rsa.readRSAKeyTextBase64("E:/study/workspace/openssl/src/main/resources/javabc/rsa_public_key.keystore");
        byte[] cipherData=rsa.encrypt(key.buildRSAPublicKey(publicKeyTextBase64), plainText.getBytes("utf-8"));
        String cipherTextBase64=Base64.encodeBase64String(cipherData);  
		//私钥解密过程  
        String privateKeyTextBase64 = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK3mDl8dhFcQYxM5GGv3KSt4bSIta8FfzjXnhbnqS49akomxAHdsTSkEfvmtAjQ+Vf8HvQg25Dm+5CTZte7xdEPYx9LAxMlowhQxFNlV+NmNX7m1xe4XPFPtHWbeGTQZREWSIFWbxAiHFWu1N8uM+EyOm+RgAXpTea3oy0MBLHylAgMBAAECgYAr1mffEGjRLd9b9DO029y0CaqxR/P3DnjEW/X5FRgxLirsrQMqF9Inuetsc3Gvy9sP7Fp53KSR7g5DP+K+Nn4RRgMBJUdxxCysweLPyDqbCedWvZeOT+lGTpUkPDPV6p2vKOZPTEnIHJZV1bglD0pdlSzpcZQfMzXnLkPNFkIIYQJBANlIp2yAGLmggdF2wDhyqIzfXiPQVDkKiDrBLwrdxZOWR/0F1ecMtD2v6TC1/mVFuxLwtz2XmHYHzPG3ylTqDWkCQQDM4mZI7pjLynUf/AlbGNYFOL5K6IJG03i8hK3xA2X+Ib6xZBFuYZJDQN9BxL680adyfNnSyhDbhZU4i2aK1YHdAkEAwCEsWhaSxmEVKYCVy9QnEhnZBlYKgz265Ck6TaN4N16lXSix0dI79mf8DKSAxE/6cW8EuKO5nnSbVgmsEZW8MQJAUlEoHWdXlpFqS4Z8z9ADtSdeNUSDiydN3BhGto3R/a/bKPR2mG21UTYQPSFqLPzjFPmanrk22qwqDwTbFy6/NQJBAKW9WV+oICFAmts0KgzRf59rCHxIKTXhqK99J8iXg0Z4KTHKV8vCqmX3Kes0xAnJyHwvWgJet1JeGsBeRg5viNk=";
        //String privateKeyTextBase64 = rsa.readRSAKeyTextBase64("E:/study/workspace/openssl/src/main/resources/javabc/rsa_private_key.keystore");
        byte[] decryptData = rsa.decrypt(key.buildRSAPrivateKey(privateKeyTextBase64), Base64.decodeBase64(cipherTextBase64));
        String decryptText = new String(decryptData, "utf-8");  
        System.out.println("原文："+plainText);  
        System.out.println("原文byte[]>>>"+Arrays.toString(plainText.getBytes("utf-8")));  
        System.out.println();
        System.out.println("密文byte[]>>>"+Arrays.toString(cipherData));
        System.out.println("加密："+cipherTextBase64);  
        System.out.println();
        System.out.println("明文byte[]>>>"+Arrays.toString(decryptData));
        System.out.println("解密："+decryptText);  
        System.out.println(); 
        System.err.println("是否解密成功>>>"+plainText.equals(decryptText));
        /***/
        
        /** 演示：私钥加密公钥解密过程  */
    	/**
        System.out.println("--------------私钥加密公钥解密过程-------------------");  
        String plainText="ihep_私钥加密公钥解密";  
        //私钥加密过程  
        String privateKeyTextBase64 = key.readRSAKeyTextBase64FromJavaBC("E:/study/workspace/openssl/src/main/resources/javabc/rsa_private_key.keystore");
        byte[] cipherData=rsa.encrypt(key.buildRSAPrivateKey(privateKeyTextBase64), plainText.getBytes("utf-8"));  
        String cipherTextBase64=Base64.encodeBase64String(cipherData);  
        //公钥解密过程  
        String publicKeyTextBase64 = key.readRSAKeyTextBase64FromJavaBC("E:/study/workspace/openssl/src/main/resources/javabc/rsa_public_key.keystore");
        byte[] decryptData=rsa.decrypt(key.buildRSAPublicKey(publicKeyTextBase64), Base64.decodeBase64(cipherTextBase64));  
        String decryptText=new String(decryptData,"utf-8");  
        System.out.println("原文："+plainText);  
        System.out.println("加密："+cipherTextBase64);  
        System.out.println("解密："+decryptText);  
        System.out.println();
        System.err.println("是否解密成功>>>"+plainText.equals(decryptText));
        */ 
        
    }
    

}
