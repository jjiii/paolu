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
package com.fc.pay.common.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fc.pay.boss.enums.ErrorEnum;

/**
 * Spring异常拦截器.
 */
@ControllerAdvice
public class WebExceptionHandler {

	private static final Log LOG = LogFactory.getLog(WebExceptionHandler.class);

	/**
	 * shiro权限 异常
	 * <p/>
	 * 后续根据不同的需求定制即可
	 */
	@ExceptionHandler({ UnauthorizedException.class })
	@ResponseStatus(HttpStatus.OK)
	public String processUnauthorizedException(HttpServletRequest request, UnauthorizedException e) {
		LOG.error("UnauthorizedException", e);
		Map<String,Object> errMap = new HashMap<>();
		errMap.put("errcode", ErrorEnum.SERVER_ERROR);
		errMap.put("errmsg","您没有权限访问该资源");
		request.setAttribute("errmap", errMap);
		return "exception/error500";
	}

	/**
	 * 总异常
	 */
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.OK)
	public String processException(Exception e, HttpServletRequest request) {
		LOG.error("Exception", e);
		Map<String,Object> errMap = new HashMap<>();
		errMap.put("errcode", ErrorEnum.SERVER_ERROR);
		errMap.put("errmsg","系统异常");
		request.setAttribute("errmap", errMap);
		return "exception/error500";
	}

}
