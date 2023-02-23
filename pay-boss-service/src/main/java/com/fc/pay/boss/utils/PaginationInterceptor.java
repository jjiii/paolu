package com.fc.pay.boss.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.plugins.pagination.IDialect;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.toolkit.StringUtils;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class}) })
public class PaginationInterceptor implements Interceptor {

	/* 婧㈠嚭鎬婚〉鏁帮紝璁剧疆绗竴椤� */
	private boolean overflowCurrent = false;
	
	/* 鏂硅█绫诲瀷 */
	private String dialectType;
	
	/* 鏂硅█瀹炵幇绫� */
	private String dialectClazz;

	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		if (target instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) target;
			MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
			RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
			
			/* 涓嶉渶瑕佸垎椤电殑鍦哄悎 */
			if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
				return invocation.proceed();
			}
			
			/* 瀹氫箟鏁版嵁搴撴柟瑷� */
			IDialect dialect = null;
			if (StringUtils.isNotEmpty(dialectType)) {
				dialect = DialectFactory.getDialectByDbtype(dialectType);
			} else {
				if (StringUtils.isNotEmpty(dialectClazz)) {
					try {
						Class<?> clazz = Class.forName(dialectClazz);
						if (IDialect.class.isAssignableFrom(clazz)) {
							dialect = (IDialect) clazz.newInstance();
						}
					} catch (ClassNotFoundException e) {
						throw new MybatisPlusException("Class :" + dialectClazz + " is not found");
					}
				}
			}
			
			/* 鏈厤缃柟瑷�鍒欐姏鍑哄紓甯� */
			if (dialect == null) {
				throw new MybatisPlusException("The value of the dialect property in mybatis configuration.xml is not defined.");
			}

			/*
			 * <p>
			 * 绂佺敤鍐呭瓨鍒嗛〉
			 * </p>
			 * <p>
			 * 鍐呭瓨鍒嗛〉浼氭煡璇㈡墍鏈夌粨鏋滃嚭鏉ュ鐞嗭紙杩欎釜寰堝悡浜虹殑锛夛紝濡傛灉缁撴灉鍙樺寲棰戠箒杩欎釜鏁版嵁杩樹細涓嶅噯銆�
			 * </p>
			 */
			BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
			String originalSql = (String) boundSql.getSql();
			metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
			metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

			/**
			 * <p>
			 * 鍒嗛〉閫昏緫
			 * </p>
			 * <p>
			 * 鏌ヨ鎬昏褰曟暟 count
			 * </p>
			 */
			if (rowBounds instanceof Pagination) {
				MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
				Connection connection = (Connection) invocation.getArgs()[0];
				Pagination page = (Pagination) rowBounds;
				if (page.isSearchCount()) {
					page = this.count(originalSql, connection, mappedStatement, boundSql, page);
					/** 鎬绘暟 0 璺冲嚭鎵ц */
					if (page.getTotal() <= 0) {
						return invocation.proceed();
					}
				}
				originalSql = dialect.buildPaginationSql(originalSql, page.getOffsetCurrent(), page.getSize());
			}

			/**
			 * 鏌ヨ SQL 璁剧疆
			 */
			metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
		}

		return invocation.proceed();
	}

	/**
	 * 鏌ヨ鎬昏褰曟潯鏁�
	 * 
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param page
	 */
	public Pagination count(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql,
			Pagination page) {
		/*
		 * 鍘绘帀 ORDER BY
		 */
		String sqlUse = sql;
		int order_by = sql.toUpperCase().lastIndexOf("ORDER BY");
		if ( order_by > -1 ) {
			sqlUse = sql.substring(0, order_by);
		}

		/*
		 * COUNT SQL
		 */
		StringBuffer countSql = new StringBuffer();
		countSql.append("SELECT COUNT(1) AS TOTAL FROM (").append(sqlUse).append(") A");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql.toString());
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql.toString(),
					boundSql.getParameterMappings(), boundSql.getParameterObject());
			ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,
					boundSql.getParameterObject(), countBS);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int total = 0;
			if (rs.next()) {
				total = rs.getInt(1);
			}
			page.setTotal(total);
			/*
			 * 婧㈠嚭鎬婚〉鏁帮紝璁剧疆绗竴椤�
			 */
			if (overflowCurrent && (page.getCurrent() > page.getPages())) {
				page = new Pagination(1, page.getSize());
				page.setTotal(total);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return page;
	}

	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	public void setProperties(Properties prop) {
		String dialectType = prop.getProperty("dialectType");
		String dialectClazz = prop.getProperty("dialectClazz");
		if (StringUtils.isNotEmpty(dialectType)) {
			this.dialectType = dialectType;
		}
		if (StringUtils.isNotEmpty(dialectClazz)) {
			this.dialectClazz = dialectClazz;
		}
	}

	public void setDialectType(String dialectType) {
		this.dialectType = dialectType;
	}

	public void setDialectClazz(String dialectClazz) {
		this.dialectClazz = dialectClazz;
	}

	public void setOverflowCurrent(boolean overflowCurrent) {
		this.overflowCurrent = overflowCurrent;
	}


}
