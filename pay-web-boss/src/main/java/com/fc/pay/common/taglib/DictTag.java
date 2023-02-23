package com.fc.pay.common.taglib;

import com.fc.pay.boss.service.IBossDictionaryService;
import com.fc.pay.utils.SpringBeanUtils;

/**
 * 
 * @ClassName: PayStatusTag 
 * @Description: 支付状态标签类
 * @author rongyz 
 * @date 2017年1月16日 上午11:16:42 
 *
 */
public class DictTag {

	/**
	 * 编码转换成对应名称
	 * @param parentCode 	父编码
	 * @param childCode 	子编码
	 * @return
	 */
	public static String codeToName(String parentCode, String childCode) {
		IBossDictionaryService bossDictionaryService = (IBossDictionaryService) SpringBeanUtils
				.getBean("bossDictionaryServiceImpl");
		return bossDictionaryService.selectNameByCode(parentCode, childCode);
	}
}
