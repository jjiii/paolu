package com.fc.pay.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	public static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	
	public static ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	public static String toJson(Object o) {
		try {
			return OBJECT_MAPPER.writeValueAsString(o);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
