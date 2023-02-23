package com.fc.pay.boss.service;

import com.fc.pay.boss.entity.BossDictionary;

public interface IBossDictionaryService extends IBaseService<BossDictionary> {

	/**
	 * 检测该字典是否存在
	 * @param bossDictionary
	 * @return
	 */
	boolean isExisted(BossDictionary bossDictionary);
	
	/**
	 * 根据code查询name
	 * @param parentCode
	 * @param childCode
	 * @return
	 */
	String selectNameByCode(String parentCode,String childCode);
}
