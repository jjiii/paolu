package com.fc.pay.bill.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账批次状态枚举
 * 
 * @author zhanjq
 *
 */
public enum BatchStatusEnum {
	
	init("初始化"),
	
//	noBill("账单不存在"),//账单日期无交易，所以账单不存在
	
	hasDownload("已下载"),
//	
	hasParse("已解析"),
	
	success("成功"),

	fail("失败");

//	restart("重新启动");

	private String desc;

	private BatchStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static BatchStatusEnum getEnum(String name) {
		BatchStatusEnum[] arry = BatchStatusEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		BatchStatusEnum[] ary = BatchStatusEnum.values();
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

