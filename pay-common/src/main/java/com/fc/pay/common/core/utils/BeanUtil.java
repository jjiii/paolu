package com.fc.pay.common.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 该类用于bean和Map之间的互相转化
 * 依赖apache的BeanUtils
 * @author XDou
 */
public class BeanUtil {

	public static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	/**
	 * @此方法就连时间类型Date也不能强制转换
	 * @需要注意map的类型和bean属性的类型
	 * @如果转换失败抛运行时异常
	 * @param bean
	 * @param map
	 */
	public static void map2Bean(Object bean, Map<String, ? extends Object> map) {
		if (map == null || bean == null)
			return;
		try {
			BeanUtils.populate(bean, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	};

	/**
	 * Bean转map
	 * 没有经过太多测试
	 * @注意抛运行时异常
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> bean2Map(Object obj) {

		if (obj == null) return null;

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return map;

	}
	
	/**
	 * 把orig对象属性，赋值给dest对象
	 * @param dest
	 * @param orig
	 */
	public static void copyProperties(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	public static String bean2JsonString(Object dest) {
		try {
			return OBJECT_MAPPER.writeValueAsString(dest).toString();
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
//	public static Object cloneBean(Object o) throws Exception{
//		return BeanUtils.cloneBean(o);
//		
//	}
//	public static void main(String[] args) throws Exception {
//		ArrayList al = new ArrayList();
//		al.add("aa");
//		ArrayList ss = BeanUtils.cloneBean(al);
//		System.out.println(ss);
//	}

}
