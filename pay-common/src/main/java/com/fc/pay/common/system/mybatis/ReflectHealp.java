package com.fc.pay.common.system.mybatis;

import java.lang.reflect.Field;

public class ReflectHealp {
	// 获取对象的属性
	public static Object getFieldValue(Object obj, String fieldName) {
		try {
			Class<?> clazz = obj.getClass();
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 获取对象父类属性
	public static Object getSuperFieldValue(Object obj, String fieldName) {
		try {
			Class<?> clazz = obj.getClass().getSuperclass();
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 设置对象属性值
	public static void setFieldValue(Object obj, String fieldName,
			Object fieldValue) {
		try {
			Class<?> clazz = obj.getClass();
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, fieldValue);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
