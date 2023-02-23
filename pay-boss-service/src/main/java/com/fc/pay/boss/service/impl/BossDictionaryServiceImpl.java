package com.fc.pay.boss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.pay.boss.dao.BossDictionaryMapper;
import com.fc.pay.boss.entity.BossDictionary;
import com.fc.pay.boss.service.IBossDictionaryService;
import com.fc.pay.boss.utils.RedisUtil;

@Service
public class BossDictionaryServiceImpl extends BaseServiceImpl<BossDictionaryMapper, BossDictionary> implements IBossDictionaryService  {
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 检测该字典是否存在
	 */
	@Override
	public boolean isExisted(BossDictionary bossDictionary) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("code", bossDictionary.getCode());
		params.put("id", bossDictionary.getId());
		params.put("parent", bossDictionary.getParent());
		return baseMapper.selectCount(params)>0;
	}

	/**
	 * 查询指定父字典下的子字典 
	 */
	@Override
	public String selectNameByCode(String parentCode, String childCode) {
		String name = "";
		// 读缓存内数据，如果没有则查询数据库并缓存
		List<BossDictionary> dictionarys = redisUtil.getCacheList("dict");
		if(dictionarys.isEmpty()){
			Map<String,Object> params = new HashMap<>();
			params.put("id","0");
			dictionarys = baseMapper.selectList(params);
			redisUtil.setCacheList("dict", dictionarys);
		}
		for(BossDictionary dictionary : dictionarys){
			if(parentCode.equals(dictionary.getCode()) && dictionary.getChilds()!=null){
				for(BossDictionary child : dictionary.getChilds()){
					if(childCode.equals(child.getCode())){
						name = child.getName();
						break;
					}
				}
				break;
			}
		}		
		return name;
	}

}
