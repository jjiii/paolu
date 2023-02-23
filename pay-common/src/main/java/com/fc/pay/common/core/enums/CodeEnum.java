package com.fc.pay.common.core.enums;

public enum CodeEnum {
	
	_00000("00000","系统异常"),
	_10000("10000","成功"),
	
	_00002("00002","请求超时"),
	_00003("00003","请求参数错误"),
	
	_00004("00004","原订单不存在"),
	_00005("00005","订单等待支付"),
	_00006("00006","订单关闭"),
	
	_00007("00007","退款申请中"),
	_00008("00008","退款失败"),
	_00009("00009","商户渠道配置错误"),
	_00010("00010","无效的签名"),
	_00011("00011","对账单不存在"),
	_00012("00012","账单日期错误"),
	_00013("00013","账单数据异常"),
	_00014("00014","一个退款订单只能退一个支付交易");
	
	private CodeEnum(String name,String value){
		this.name=name;
		this.value=value;
	}
	
	private String name;

	private String value;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
