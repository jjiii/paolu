package com.fc.pay.bill.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件存储模式枚举
 * 
 * @author zhanjq
 *
 */
public enum FileStoreModeEnum {
	
	batch("批处理应用程序"),

	web("web应用程序");

//	restart("重新启动");

	private String desc;

	private FileStoreModeEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static FileStoreModeEnum getEnum(String name) {
		FileStoreModeEnum[] arry = FileStoreModeEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		FileStoreModeEnum[] ary = FileStoreModeEnum.values();
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

