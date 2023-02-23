package com.fc.pay.common.core.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 重复造轮，获取Properties文件的值
 * 
 * @author XDou
 * @用法：Property p = new Property("文件路径和名称"); p.getProperty("");
 */
public class Property {
	private Properties prop;

	public Property(String fileName) {
		read(fileName);
	}

	private void read(String fileName) {
		try {
			prop = new Properties();
			ClassLoader loder = Property.class.getClassLoader();
			InputStream stream = loder.getResourceAsStream(fileName);
			prop.load(stream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}

}
