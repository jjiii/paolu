package com.fc.pay.bill.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内部账单生成状态枚举
 * 
 * @author zhanjq
 *
 */
public enum BillMakeStatusEnum {

	wait("待生成"),
	
	success("成功"),

	fail("失败");

	private String desc;

	private BillMakeStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static BillMakeStatusEnum getEnum(String name) {
		BillMakeStatusEnum[] arry = BillMakeStatusEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		BillMakeStatusEnum[] ary = BillMakeStatusEnum.values();
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

