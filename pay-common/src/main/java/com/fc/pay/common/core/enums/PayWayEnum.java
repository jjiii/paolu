package com.fc.pay.common.core.enums;


/**
 * 支付方式枚举
 * @author XDou
 */
public enum PayWayEnum {

	web("网站端支付"), 
	app("app端支付");


	private String value;

	private PayWayEnum(String value) {
		this.value = value;
	}
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
