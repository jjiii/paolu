package com.fc.pay.common.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易状态枚举
 * @author zhanjq
 *
 */
public enum TradeStatusEnum {
	
	pay_wait("待支付"),
	
	success("成功"),
	
//	finish("结束(不能退款)"),

	close("关闭(超时/未收到通知)");

	private String desc;

	private TradeStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
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

