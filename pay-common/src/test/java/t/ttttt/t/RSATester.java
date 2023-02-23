package t.ttttt.t;

import java.util.Map;

import com.fc.pay.common.core.utils.RSA;

  
  
public class RSATester {
  
    static String publicKey;  
    static String privateKey;  
  
    static {
        try { 
            Map<String, Object> keyMap = RSA.genKeyPair();  
            publicKey = RSA.getPublicKey(keyMap);  
            privateKey = RSA.getPrivateKey(keyMap);  
            System.err.println("公钥: \n\r" + publicKey);  
            System.err.println("私钥： \n\r" + privateKey);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    public static void main(String[] args) throws Exception {
//        test();  
        testSign();  
    }  
  
    static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");  
        String source = "{\"a\":\"123\"}";  
        System.out.println("\r加密前文字：\r\n" + source);  
     
        String encodedData = RSA.encryptByPublicKey(source, publicKey);  
        System.out.println("加密后文字：\r\n" + new String(encodedData));  
        String decodedData = RSA.decryptByPrivateKey(encodedData, privateKey);  
        String target = new String(decodedData);  
        System.out.println("解密后文字: \r\n" + target);  
    }
  
    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");  
        String source = "{\"a\":\"123\"}";  
        System.out.println("原文字：\r\n" + source);   
        String encodedData = RSA.encryptByPrivateKey(source, privateKey);  
        System.out.println("加密后：\r\n" + encodedData);  
        String decodedData = RSA.decryptByPublicKey(encodedData, publicKey);  
        String target = new String(decodedData);  
        System.out.println("解密后: \r\n" + target);  
        System.err.println("私钥签名——公钥验证签名");  
        String sign = RSA.sign(encodedData, privateKey);  
        System.err.println("签名:\r" + sign);  
        boolean status = RSA.verify(encodedData, publicKey, sign);  
        System.err.println("验证结果:\r" + status);  
    }  
      
}  