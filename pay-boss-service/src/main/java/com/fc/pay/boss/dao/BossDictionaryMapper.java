package com.fc.pay.boss.dao;

import java.util.List;

import com.fc.pay.boss.entity.BossDictionary;


public interface BossDictionaryMapper extends BaseMapper<BossDictionary> {
	
	List<BossDictionary> selectChilds(Long parent);
}