package com.fc.pay.common.filters;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.service.IBossOperatorService;
import com.fc.pay.utils.ShiroFilterUtils;

public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {
	private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilter.class);
	//Logger logger = Logger.getLogger(LoginFormAuthenticationFilter.class);
	@Autowired
	private IBossOperatorService operatorService;

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,ServletResponse response) throws Exception {
		Session session = subject.getSession();
		SavedRequest req = WebUtils.getSavedRequest(request);
		if(req != null){
			String reurl = WebUtils.getSavedRequest(request).getRequestUrl();
			session.setAttribute("reurl", reurl);
		}
		String username = token.getPrincipal().toString();
		BossOperator operator = new BossOperator();
		operator = operatorService.selectByName(username);
		session.setAttribute("BossOperator", operator);
		WebUtils.getAndClearSavedRequest(request);
		WebUtils.redirectToSavedRequest(request, response, "/");
		//return super.onLoginSuccess(token, subject, request, response);
		return false;
	}
	
	
	@Override
	protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				if (log.isTraceEnabled()) {
					log.trace("Login submission detected.  Attempting to execute login.");
				}
				return executeLogin(request, response);
			} else {
				if (ShiroFilterUtils.isAjax(request)) {// ajax请求
					Map<String,String> resultMap = new HashMap<>();
					resultMap.put("errcode", "300");
					ShiroFilterUtils.out(response, resultMap);
					return false;
				}
				return true;
			}
		} else {
			if (log.isTraceEnabled()) {
				log.trace("Attempting to access a path which requires authentication.  Forwarding to the "
						+ "Authentication url [" + getLoginUrl() + "]");
			}
			if (!ShiroFilterUtils.isAjax(request)) {// 不是ajax请求
				saveRequestAndRedirectToLogin(request, response);
			} else {
				Map<String,String> resultMap = new HashMap<>();
				resultMap.put("errcode", "300");
				ShiroFilterUtils.out(response, resultMap);
			}
			return false;
		}
	}

}
