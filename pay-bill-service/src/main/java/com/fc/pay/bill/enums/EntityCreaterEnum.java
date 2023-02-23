package com.fc.pay.bill.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体生成者枚举
 * 
 * @author zhanjq
 *
 */
public enum EntityCreaterEnum {

	//定时作业程序
	job("定时作业");

	private String desc;

	private EntityCreaterEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static EntityCreaterEnum getEnum(String name) {
		EntityCreaterEnum[] arry = EntityCreaterEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		EntityCreaterEnum[] ary = EntityCreaterEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("desc", ary[i].getDesc());
			map.put("name", ary[i].name());
			list.add(map);
		}
		return list;
	}

}

