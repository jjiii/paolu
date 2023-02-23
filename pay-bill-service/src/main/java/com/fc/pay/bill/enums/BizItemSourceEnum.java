package com.fc.pay.bill.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内部账单明细来源
 * 
 * @author zhanjq
 *
 */
public enum BizItemSourceEnum {

	//常规方式：从交易记录产生账单明细
	regular("常规"),

	//存疑方式：由时差问题引起的存疑记录，隔天处理后生成账单明细
	doubt("存疑"),
	
	//差错方式：由差错记录的处理产生账单明细
	mistake("差错");

	private String desc;

	private BizItemSourceEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static BizItemSourceEnum getEnum(String name) {
		BizItemSourceEnum[] arry = BizItemSourceEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		BizItemSourceEnum[] ary = BizItemSourceEnum.values();
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

