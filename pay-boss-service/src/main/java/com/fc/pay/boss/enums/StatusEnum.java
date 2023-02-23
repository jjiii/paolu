package com.fc.pay.boss.enums;

public enum StatusEnum {
	
	ACTIVE("激活"),

	UNACTIVE("冻结");
	
	StatusEnum(){}

	/** 描述 */
	private String desc;

	private StatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
