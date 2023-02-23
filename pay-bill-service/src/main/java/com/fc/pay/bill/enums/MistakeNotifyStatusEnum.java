package com.fc.pay.bill.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 差错通知状态枚举
 * 
 * @author zhanjq
 *
 */
public enum MistakeNotifyStatusEnum {
	
	wait_send("待发送"),
	
    send_fail("发送失败"),
    
    send_success("发送成功"),
    
    confirm("已确认处理");

	private String desc;

	private MistakeNotifyStatusEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public static MistakeNotifyStatusEnum getEnum(String name) {
		MistakeNotifyStatusEnum[] arry = MistakeNotifyStatusEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equals(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		MistakeNotifyStatusEnum[] ary = MistakeNotifyStatusEnum.values();
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

