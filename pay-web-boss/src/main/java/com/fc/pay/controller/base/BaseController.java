package com.fc.pay.controller.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.utils.PageData;

public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected Session getSession() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return session;
	}

	/**
	 * 取request中的参数，封状成map
	 * @param request
	 * @return
	 */
	protected Map<String, Object> getRequestParams(HttpServletRequest request) {
		Map<String, Object> queryStringMap = new HashMap<>();
		Map<String, String[]> params = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String[] value = entry.getValue();
			if (value != null && value.length > 0) {
				queryStringMap.put(entry.getKey(), value[0]);
			}

		}
		return queryStringMap;
	}

	/**
	 * 获取当前登录的用户
	 * @return
	 */
	protected BossOperator getCurrentOperator() {
		return (BossOperator) this.getSession().getAttribute("BossOperator");
	}

	/** 
	 * 创建PageData对象
	 * @return
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 获取request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}
}
