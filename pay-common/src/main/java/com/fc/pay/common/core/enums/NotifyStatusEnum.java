package com.fc.pay.common.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum NotifyStatusEnum {

	OK(1),

	NO(2);

	

	private Integer desc;

	private NotifyStatusEnum(Integer desc) {
		this.desc = desc;
	}

	public Integer value() {
		return desc;
	}

	public static TradeStatusEnum getEnum(String name) {
		TradeStatusEnum[] arry = TradeStatusEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		TradeStatusEnum[] ary = TradeStatusEnum.values();
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
