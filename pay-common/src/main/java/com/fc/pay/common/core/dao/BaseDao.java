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
package com.fc.pay.common.core.dao;

/**
 * 基础DAO接口<br/>
 * 目的是使用mybatis建议的无接口实现方式作为DAO <br/>
 * 减少每个dao类crud相同方法名称，在MGB默认生成xml的情况下<br/>
 * 你可以选择不继承，继承的耦合度相对高一点<br/>
 * 
 * <p>
 * 	尝试过在接口里注入一个具有crud静态方法的类，来代替耦合较高的接口方式<br/>
 *  但是mybatis自动生成dao实现的时候，会依赖spring-mybatis的创建<br/>
 *  而该静态类如果不使用用spring-mybatis的SqlSessionTemplate的话，等于需要重新配置整个mybatis<br/>
 *  代价太大，所以z只能使用接口<br/>
 * </p>
 * <p>
 * 	在当前开源社区，对mybatis几乎枯竭的挖掘下，mybatis所能做的orm，也就是映射xml的结果
 *  过度深入的包装或写插件，都是在浪费青春，orm已经太多，用一个东东简单易懂是关键
 * </p>
 * @author XDou </br>
 * @date 201-11-16 </br>
 */
public interface BaseDao<T> {

	// public static final String insert = "insert";
	// public static final String insertSelective = "insertSelective";
	// public static final String selectByPrimaryKey = "selectByPrimaryKey";
	// public static final String updateByPrimaryKeySelective =
	// "updateByPrimaryKeySelective";
	// public static final String updateByPrimaryKey = "updateByPrimaryKey";
	// public static final String deleteByPrimaryKey = "deleteByPrimaryKey";

	int deleteByPrimaryKey(Long id);

	int insert(T record);

	int insertSelective(T record);

	T selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);

}
