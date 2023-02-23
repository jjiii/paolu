package com.fc.pay.bill.job.controller;

public class Response {
	
	private Data data;

	private String sign;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "Response [data=" + data + ", sign=" + sign + "]";
	}
	
	
	
}
