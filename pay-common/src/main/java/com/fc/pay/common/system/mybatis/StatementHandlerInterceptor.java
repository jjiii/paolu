package com.fc.pay.common.system.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
/**
 * 用法：
 * 1、Page selectAll(@Param("name")int name,Page p)
 * 或
 * 2、Page selectAll(Map map); Map需要添加进Page
 * 
 * 注意：
 * 不支持mybatis二级缓存(缓存在此拦截器前已经执行)
 * mabatis-config <setting name="cacheEnabled" value="false" />
 * 
 * 其他：这样的用法，要传入Page，对dao的侵入性太强。
 * 参考了开源中国上的PageHelper，以及许多baidu能早到的插件
 * 希望以后能优化mysql的分页查询。不是limi(10,5)这种全表扫描
 * 优化如：SELECT * FROM message WHERE id>1020 ORDER BY id ASC LIMIT 20之类
 * @author XDou
 * 2015年7月17日上午1:48:01
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }),
		@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class StatementHandlerInterceptor implements Interceptor {

	public static final ThreadLocal<Page> threadPage = new ThreadLocal<Page>();

	// public static final ThreadLocal<Boolean> threadIsNeed = new
	// ThreadLocal<Boolean>();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		if (invocation.getTarget() instanceof ResultSetHandler) {
			// if(!threadIsNeed.get()){
			// invocation.proceed();
			// }
			
			Page p = threadPage.get();
			if (p == null) 
			return invocation.proceed();
			
			
			Object result = invocation.proceed();
//			p.result = (List<?>)result;
			p.addAll((List)result);
			threadPage.remove();
			return p;
		}

		RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation
				.getTarget();
		BaseStatementHandler baseStatementHandler = (BaseStatementHandler) ReflectHealp
				.getFieldValue(routingStatementHandler, "delegate");

		BoundSql boundSql = baseStatementHandler.getBoundSql();

		// 是否需要分页(Mapper接口的参数中，如果有Page对象，说明需要分页)
		Page page = this.whetherNeedPaging(boundSql.getParameterObject());
		if (page == null) {
			return invocation.proceed();
		}

		// 统计总条数
		Connection connection = (Connection) invocation.getArgs()[0];
		int totalNum = this.buildCountSql(connection, boundSql,
				baseStatementHandler);

		// 把分页语句set进boudSql执行
		String pageSql = this.buidPageSql(boundSql.getSql(), page.getCurrNum(),
				page.getPageSize());
		ReflectHealp.setFieldValue(boundSql, "sql", pageSql);

		// 除了ThreadLocal，找不到更好的方法来映射结果集到page
		// .............
		page.setTotalItem(totalNum);
		threadPage.set(page);
		// threadIsNeed.set(false);
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler
				|| target instanceof ResultSetHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

	// 查询mapper参数中是否有Page类型
	private Page whetherNeedPaging(Object parameter) {
		
//		if(parameter instanceof Page) return (Page)parameter;
		
		if (parameter instanceof HashMap) {
			HashMap<String, Object> map = (HashMap<String, Object>) parameter;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() instanceof Page) {
					return (Page) entry.getValue();
				}
			}
		}
		return null;
	}

	// 构建分页语句 （第几页(pageNum)，每页大小（pageSize））
	private String buidPageSql(String sql, int pageNum, int pageSize) {
		int firstItem = (pageNum - 1) * pageSize, endItem = pageSize;
		sql = sql + " limit " + firstItem + "," + endItem;
		return sql;
	}

	// 构建总数查询语句，并执行
	private int buildCountSql(Connection connection, BoundSql boundSql,
			BaseStatementHandler baseStatementHandler) throws Throwable {

		MappedStatement mappedStatement = (MappedStatement) ReflectHealp
				.getSuperFieldValue(baseStatementHandler, "mappedStatement");

		String sql = boundSql.getSql();
		String countSql = "select count(1) from (" + sql + ") as countTmp";

		PreparedStatement countStmt = connection.prepareStatement(countSql);
		DefaultParameterHandler parameterHandler = new DefaultParameterHandler(
				mappedStatement, boundSql.getParameterObject(), boundSql);
		parameterHandler.setParameters(countStmt);
		ResultSet rs = countStmt.executeQuery();
		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}
		// connection.close();//不能关闭
		return totalCount;
	}

}
