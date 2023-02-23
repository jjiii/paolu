package com.fc.pay.common.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.fc.pay.common.core.utils.PathUtil;

public class RSAKey {
	
	/**
	 * 加载算法提供者
	 * BouncyCastleProvider
	 */
	static{
		Security.addProvider(new BouncyCastleProvider());
	}
		
    /** 
     * 随机生成密钥对 
     * @throws Exception 
     */  
    public void makeRSAKeyPairOnJDK(String keyPairDirPath, String publicKeyFile, String privateKeyFile) throws Exception {  
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
        KeyPairGenerator keyPairGen = null;
        try {  
            keyPairGen = KeyPairGenerator.getInstance("RSA","BC");
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        // 初始化密钥对生成器，密钥大小为96-1024位  
        keyPairGen.initialize(1024,new SecureRandom());  
        // 生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        // 公钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        try {  
            // 得到公钥字符串  
            String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());  
            // 得到私钥字符串  
            String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
            // 将密钥对写入到文件  
            FileWriter pubfw = new FileWriter(keyPairDirPath + publicKeyFile);  
            FileWriter prifw = new FileWriter(keyPairDirPath + privateKeyFile);
            BufferedWriter pubbw = new BufferedWriter(pubfw);  
            BufferedWriter pribw = new BufferedWriter(prifw);  
            pubbw.write(publicKeyString);  
            pribw.write(privateKeyString);  
            pubbw.flush();  
            pubbw.close();  
            pubfw.close();  
            pribw.flush();  
            pribw.close();  
            prifw.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    public RSAPem makeRSAKeyPairOnOpenSSL(String merchantCode) throws Exception {
    	//Process process = Runtime.getRuntime().exec("cmd /c start /b F:\\com_svn\\pay\\trunk\\pay\\pay-common\\target\\classes\\com\\fc\\pay\\common\\security\\gen_pay_key.bat");
    	//int exitValue = process.exitValue();
    	//int exitValue = process.waitFor();
    	//System.out.println("exitValue>>>"+exitValue);
    	
    	//System.out.println(SystemUtils.IS_OS_WINDOWS);
    	//System.out.println(SystemUtils.IS_OS_LINUX);
    	
    	String randomNumeric = RandomStringUtils.randomNumeric(20);
    	
    	String rsaKeyPath = PathUtil.makeKeyPathDirExist(PathUtil.buildKeyRelativePath(merchantCode, randomNumeric));
    	System.out.println("rsaKeyPath>>>"+rsaKeyPath);
    	
    	String priPemPath = rsaKeyPath+"rsa_private_key.pem";
    	String priPkcs8PemPath = rsaKeyPath+"rsa_private_key_pkcs8.pem";
    	String pubPemPath = rsaKeyPath+"rsa_public_key.pem";
    	
    	String priKeyCmd = "openssl genrsa -out "+priPemPath+" 1024";
    	String pkcs8KeyCmd = "openssl pkcs8 -topk8 -inform PEM -in "+priPemPath+" -outform PEM -nocrypt -out "+priPkcs8PemPath;
    	String pubKeyCmd = "openssl rsa -in "+priPemPath+" -pubout -out "+pubPemPath;
    	
    	Runtime runtime = Runtime.getRuntime();
    	//System.out.println("priKeyCmd>>>"+priKeyCmd);
    	/** 生成密钥 */
    	Process priKeyProcess = runtime.exec(priKeyCmd);
    	int priKeyProcessExitValue = priKeyProcess.waitFor();
    	if(priKeyProcessExitValue!=0){
    		throw new Exception("生成私钥失败");
    	}
    	/** 生成密钥格式PKCS8 */
    	Process priKeyPkcs8Process = runtime.exec(pkcs8KeyCmd);
    	int priKeyPkcs8ProcessValue = priKeyPkcs8Process.waitFor();
    	if(priKeyPkcs8ProcessValue!=0){
    		throw new Exception("私钥格式转换失败");
    	}
    	/** 生成公钥 */
    	Process pubKeyProcess = runtime.exec(pubKeyCmd);
    	int pubKeyProcessValue = pubKeyProcess.waitFor();
    	if(pubKeyProcessValue!=0){
    		throw new Exception("生成公钥失败");
    	}
    	
    	RSAPem pem = new RSAPem();
    	pem.setPrivateKey(readRSAKeyTextBase64FromOpenSSL(priPemPath));
    	pem.setPrivateKeyPKCS8(readRSAKeyTextBase64FromOpenSSL(priPkcs8PemPath));
    	pem.setPublicKey(readRSAKeyTextBase64FromOpenSSL(pubPemPath));
    	return pem;    	
    }
    
    /** 
     * 从文件中输入流中加载公钥 
     *  
     * @param in 
     *            公钥输入流 
     * @throws Exception 
     *             加载公钥时产生的异常 
     */  
    public String readRSAKeyTextBase64FromJavaBC(String keyFilePath) throws Exception {  
        BufferedReader br = new BufferedReader(new FileReader(keyFilePath));
        String readLine = null;  
        StringBuilder sb = new StringBuilder();  
        while ((readLine = br.readLine()) != null) {  
            sb.append(readLine);  
        }  
        br.close();
        return sb.toString();
    }
    
    /**
     * 从OPENSSL生成的pem文件读取密钥文本(base64)
     * @param pemFilePath
     * @return
     * @throws Exception
     */
    public String readRSAKeyTextBase64FromOpenSSL(String pemFilePath) throws Exception {
    	BufferedReader br = new BufferedReader(new FileReader(pemFilePath));
        String readLine = null;  
        StringBuilder sb = new StringBuilder();  
        while ((readLine = br.readLine()) != null) {
        	if(readLine.startsWith("-")){
        		continue;
        	}
            sb.append(readLine);
        }  
        br.close();
        return sb.toString();
    }
	
    /**
     * 加载公钥
     * @param publicKeyBase64Str
     * @return
     * @throws Exception
     */
    public RSAPublicKey buildRSAPublicKey(String publicKeyBase64Str) throws Exception {  
        try {  
            byte[] buffer = Base64.decodeBase64(publicKeyBase64Str);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA","BC");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("公钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥数据为null");  
        }  
    } 
    
    /**
     * 加载私钥
     * @param privateKeyBase64Str
     * @return
     * @throws Exception
     */
    public RSAPrivateKey buildRSAPrivateKey(String privateKeyBase64Str)
            throws Exception {  
        try {  
            byte[] buffer = Base64.decodeBase64(privateKeyBase64Str);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA","BC");  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    }  
    
    public static void main(String[] args) throws Exception{
    	
    	/**
    	RSAKey key = new RSAKey();
    	String privatePemFilePath = "E:/study/workspace/openssl/src/main/resources/openssl/biz/biz_rsa_private_key_pkcs8.pem";
		String privatePemTextBase64 = key.readRSAKeyTextBase64FromOpenSSL(privatePemFilePath);
    	String publicPemFilePath = "E:/study/workspace/openssl/src/main/resources/openssl/biz/biz_rsa_public_key.pem";
		String publicPemTextBase64 = key.readRSAKeyTextBase64FromOpenSSL(publicPemFilePath);
    	System.out.println("私钥:\n"+privatePemTextBase64);
    	System.out.println("公钥:\n"+publicPemTextBase64);
    	*/   	
    	
    	RSAKey key = new RSAKey();
    	try{
    		RSAPem pem = key.makeRSAKeyPairOnOpenSSL("fc");
    		System.out.println("私钥:\n"+pem.getPrivateKey());
    		System.out.println("私钥PKCS8:\n"+pem.getPrivateKeyPKCS8());
    		System.out.println("公钥:\n"+pem.getPublicKey());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

}
