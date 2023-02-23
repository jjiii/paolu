/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fc.pay.bill.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账差错类型枚举 .
 * 
 * @author：zhanjq
 */
public enum MistakeTypeEnum {

	channel_miss("渠道漏单"), // 渠道不存在该交易
	local_miss("本地漏单"), // 本地不存在该交易
	local_status_less("本地短款，状态不符"), // 本地支付不成功但渠道支付成功（比较常见）
	local_status_more("本地长款,状态不符"), // 本地支付成功但渠道支付不成功（基本不会出现）
	local_cash_less("本地短款，金额不符"), // 本地交易金额比渠道交易金额少（基本不会出现）
	local_cash_more("本地长款,金额不符"); // 本地交易金额比渠道交易金额多
	
	/** 描述 */
	private String desc;

	private MistakeTypeEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static Map<String, Map<String, Object>> toMap() {
		MistakeTypeEnum[] ary = MistakeTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = ary[num].name();
			map.put("desc", ary[num].getDesc());
			map.put("name", ary[num].name());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		MistakeTypeEnum[] ary = MistakeTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("desc", ary[i].getDesc());
			map.put("name", ary[i].name());
			list.add(map);
		}
		return list;
	}

	public static MistakeTypeEnum getEnum(String name) {
		MistakeTypeEnum[] arry = MistakeTypeEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equalsIgnoreCase(name)) {
				return arry[i];
			}
		}
		return null;
	}

	/**
	 * 取枚举的json字符串
	 *
	 * @return
	 */
	public static String getJsonStr() {
		MistakeTypeEnum[] enums = MistakeTypeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (MistakeTypeEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
}
