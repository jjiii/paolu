package t.ttttt.t;

import java.util.Arrays;

import com.fc.pay.common.core.utils.RSA;

public class TTTTT {

//	static String publicKey;  
//    static String privateKey;  
//  
//    static {
//        try { 
//            Map<String, Object> keyMap = RSAUtil.genKeyPair();  
//            publicKey = RSAUtil.getPublicKey(keyMap);  
//            privateKey = RSAUtil.getPrivateKey(keyMap);  
//            System.err.println("公钥: \n\r" + publicKey);  
//            System.err.println("私钥： \n\r" + privateKey);  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//    }  
//	
	private static String publicKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCa1/eN6gnLXcgftykBB9sk6oGXU5T+EPlrH13s1WhNjJlarhrJkNldxdSmWCKWAaXTtqZztw+SATDE85l/O1tocTVWTPFcf1mfpQ8mb8j4snwULXDA2n8v3Hm+nr42XZ7FNpLPdXfZlHNdh8003s9mbQcEtVIlyxaayBUeLBtoJQIDAQAB";
	private static String privateKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJrX943qCctdyB+3KQEH2yTqgZdTlP4Q+WsfXezVaE2MmVquGsmQ2V3F1KZYIpYBpdO2pnO3D5IBMMTzmX87W2hxNVZM8Vx/WZ+lDyZvyPiyfBQtcMDafy/ceb6evjZdnsU2ks91d9mUc12HzTTez2ZtBwS1UiXLFprIFR4sG2glAgMBAAECgYBM00JcAvaBFDKqqbGKdV9hXYiWkD2oILvfTlzHmMp7T3r25tMbluaCBBmRvsDKNF8WP9UuLHFpO3X7AfHyknyy+xGKGPA5gssIvaOk/lWhyqv4xsxggUXKT49aY3SF7hsCkrIPJm7sNd2hNKnPeYFdPva8LzLDTsJy6AvdCfQ/MQJBANUluv8Mu5yLFC5x78K9M/I/KMQa8DuHpCjVlNrasSd1yFg4sitgg1l594EHPDcsJTGzCRrVORozn0r8JK/i+p8CQQC5+XVeQDA/AvuiGg8zXvhNHsEgjDUZfCmf2d+cR7e0T3dD6Mp+GZVdshN3pinBrNaYu+nrzyGF2r/y41W2J+q7AkB8Jp72AlehBg16RBkwZ/5C4vD+0OYO9qHyuv0aQPmhD2Tjphp5U50OWBGHEUzMoiUD/tGV1I6PKXRmO9murVnnAkBvWq/tHFAHGrki6amaX84bF0QaQfl1ZgPiY+lhQQv9GevWrKe6c4UdEghYBxVPkzb3QuUgvehbpoxyWa6zoBkLAkB2EK7dpIozIy5Y1TEiqnwI9rPlTdcwVqauC+Vf6E5HBTB4niZTw074hdaPyW4zcXA3ZIZzJ8f7fP80869F8SMn";
	
	
	public static void main(String[] args) throws Exception  {
		
		 	System.err.println("私钥加密——公钥解密");  
	        String source = "a=123";  
	        System.out.println("原文字：\r\n" + source);
	        
	  
	        String encodedData = RSA.encryptByPrivateKey(source, privateKEY);
		    System.out.println("加密后：\r\n" + encodedData);
	   
	        
	        
	        
	        String decodedData = RSA.decryptByPublicKey(encodedData, publicKEY);  
	        
	        System.out.println("解密后: \r\n" + decodedData);  
	        System.err.println("私钥签名——公钥验证签名");  
	        String sign = RSA.sign(source, privateKEY);  
	        System.err.println("签名:\r" + sign);  
	        boolean status = RSA.verify(source, publicKEY, sign);  
	        System.err.println("验证结果:\r" + status);  
		 

	}
	
}
