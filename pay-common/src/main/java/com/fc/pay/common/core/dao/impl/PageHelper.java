//package com.fc.pay.common.core.dao.impl;
//
//import java.util.Map;
//
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.fc.pay.common.system.mybatis.Page;
//
///**
// * 一、写该基类的原因：
// * 1、龙果的BaseDao写得实在太失败，几乎没有任何看的价值
// * 2、方便调用MGB默认生成的xml方法
// * 3、基于mybatis拦截器分页插件，需要骇客mybatis对象；并在原有sql的基础上count出总数
// * 4、保留龙果原有的分页方法
// * <p>
// * 2、好处：并提供其他分页方法名称，减少代码量
// * 3、待定，该类为开发完毕
// */
//
//public class PageHelper{
//	
////	private WebApplicationContext WebApplicationContext =ContextLoader.getCurrentWebApplicationContext();
//	public static final String listPageCount = "listPageCount";
//	public static final String listPage = "listPage"; 
//	@Autowired
//	private SqlSessionTemplate sessionTemplate;
//
//	
//	
//	
//	private static PageHelper mapperTemp = null;
//	
//	private PageHelper(){}
//	
//	
//	
//	
//	
//
//	/**
//	 * 分页查询数据 .
//	 */
//	public Page listPage(Page pageParam, Map<String, Object> paramMap) {
//		Long totalCount = sessionTemplate.selectOne(getStatement(listPageCount), paramMap);
//		sessionTemplate.selectOne(getStatement(listPage), paramMap);
//		return null;
//	}
//
//	/**
//	 * 函数功能说明 ： 获取Mapper命名空间.
//	 */
//	public String getStatement(String sqlId) {
//		return this.getClass().getName() + sqlId;
//	}
//	
//	
//
//	
//}
