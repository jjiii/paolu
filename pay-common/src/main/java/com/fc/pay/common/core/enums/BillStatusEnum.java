package com.fc.pay.common.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账状态枚举
 * @author zhanjq
 *
 */
public enum BillStatusEnum {
	
	wait("待对账"),
	
	normal("正常"),
	
	doubt("存疑"),

	mistake("差错"),
	
	balance("平账");

	private String desc;

	private BillStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static BillStatusEnum getEnum(String name) {
		BillStatusEnum[] arry = BillStatusEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		BillStatusEnum[] ary = BillStatusEnum.values();
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

