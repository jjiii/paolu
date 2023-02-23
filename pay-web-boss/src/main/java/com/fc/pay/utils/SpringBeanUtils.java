/**
 * 
 */
package com.fc.pay.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * @ClassName: SpringBeanUtils 
 * @Description: 获取容器中的spring 中的 bean
 * @author rongyz 
 * @date 2017年1月19日 上午11:04:14 
 *
 */
public class SpringBeanUtils {

	public static Object getBean(String beanName) {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		return context.getBean(beanName);
	}
}
