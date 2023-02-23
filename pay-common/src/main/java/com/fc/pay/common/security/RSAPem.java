package com.fc.pay.common.security;

public class RSAPem {
	
	private String privateKey;
	
	private String publicKey;
	
	private String privateKeyPKCS8;

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKeyPKCS8() {
		return privateKeyPKCS8;
	}

	public void setPrivateKeyPKCS8(String privateKeyPKCS8) {
		this.privateKeyPKCS8 = privateKeyPKCS8;
	}
	
	

}
