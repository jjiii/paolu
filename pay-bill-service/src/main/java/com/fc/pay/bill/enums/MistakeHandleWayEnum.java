package com.fc.pay.bill.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 差错处理基准枚举
 * 
 * @author zhanjq
 *
 */
public enum MistakeHandleWayEnum {

	local("以本地系统为准"),

	channel("以支付渠道为准");

	private String desc;

	private MistakeHandleWayEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static MistakeHandleWayEnum getEnum(String name) {
		MistakeHandleWayEnum[] arry = MistakeHandleWayEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		MistakeHandleWayEnum[] ary = MistakeHandleWayEnum.values();
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

