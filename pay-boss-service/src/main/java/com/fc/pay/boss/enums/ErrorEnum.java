package com.fc.pay.boss.enums;

public enum ErrorEnum {

	SERVER_ERROR(500,"操作异常,请联系管理员"),SUCCESS(0,"操作成功"),UPDATE_ERROR(1,"更新失败"),SAVE_ERROR(2,"保存失败"),DEL_ERROR(3,"删除失败"),
	ROLE_EXISTS(4,"角色已存在"),ROLE_CODE_EXISTS(5,"角色编码已存在"),OPERATOR_EXISTS(6,"操作员已存在"),PERMISS_EXISTS(7,"权限已存在"),NO_DATA_ERROR(8,"获取记录失败"),EMPTY_PWD(9,"密码不能为空"),
	MENU_EXISTS(10,"菜单名称已存在");
	
	private int code;
	private String msg;
	
	ErrorEnum(int code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
