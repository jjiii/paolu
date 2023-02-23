package com.fc.pay.bill.job.controller;

public class Data {

	private String code;
	
	private String msg;		
	
	private Object object;

	public Data(String code, String msg, Object object) {
		super();
		this.code = code;
		this.msg = msg;
		this.object = object;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "Data [code=" + code + ", msg=" + msg + ", object=" + object.toString()
				+ "]";
	}
	
	
	
	
}
