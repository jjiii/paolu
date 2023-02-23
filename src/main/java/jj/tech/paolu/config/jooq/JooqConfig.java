package jj.tech.paolu.config.jooq;

import javax.sql.DataSource;

import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.ExecuteListenerProvider;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.boot.autoconfigure.jooq.JooqProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * jooq使用单个jdbc链接的时候：自己不管理数据库链接开关，也不管理事物的开关，由外部管理
 * jooq使用数据源的时候：每次执行先获取一个链接，执行完毕还给连接池
 * 
 * @注意该类参考：spring-boot-autoconfigure自动配置jooq包下的JooqAutoConfiguration
 * 源码复制直接用， 因为spring自动配置包下大多数类不是public类，只能重写
 * 
 * @在单数据源情况下不用配置任何东西，spring boot 默认配置非常优秀
 * 需要注意的是事务的传播特性，spring boot jooq默认是嵌入事务 
 * https://blog.jooq.org/nested-transactions-in-jooq/
 * @jooq代码中写sql
 * https://blog.jooq.org/using-java-13-text-blocks-for-plain-sql-with-jooq/
 * 
 * @关于嵌入事务: 错误只回滚自己，其他成功的所有事务都成功
 * https://stackoverflow.com/questions/6683929/propagation-nested-vs-propagation-required-in-spring
 * @jooq与jpa，按作者的表述，他不愿意支持ORM
 * https://github.com/jOOQ/jOOQ/issues/11685
 * @jooq与r2dbc，胶水写法，脱裤子放屁
 * https://github.com/jOOQ/jOOQ/issues/11700
 * https://github.com/jOOQ/jOOQ/tree/main/jOOQ-examples
 * https://blog.jooq.org/reactive-sql-with-jooq-3-15-and-r2dbc
 * @examples
 * https://github.com/jOOQ/jOOQ/tree/main/jOOQ-examples
 * 
 * @Dou 2020/8/13
 */
@ConditionalOnClass(DSLContext.class)
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(JooqProperties.class)
@Configuration(proxyBeanMethods = false)
public class JooqConfig {
	
	/**
	 * 创建使用默认数据源，和spring默认嵌入式事务管理器的DSLContext
	 */
	@Bean
	DefaultDSLContext dsl(org.jooq.Configuration configuration) {
		return new DefaultDSLContext(configuration);
	}
	
	@Bean
	DefaultConfiguration jooqConfiguration(
			JooqProperties properties,
			DataSource dataSource,
			PlatformTransactionManager txManager){
		DefaultConfiguration configuration = new DefaultConfiguration();
		configuration.set(properties.determineSqlDialect(dataSource));
		configuration.set(new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource)));
		configuration.set(new JooqSpringTransactionProvider(txManager)); //spring提供的默认嵌入式事务PROPAGATION_NESTED
		configuration.set(new DefaultExecuteListenerProvider(new JooqExceptionTranslator()));
		return configuration;
	}
	
    
//    
//    /**
//	 * @spring的事物管理器
//	 * 如果添加spring-boot-starter-jdbc 依赖，框架会默认注入 DataSourceTransactionManager 实例
//	 * 如果你添加的是 spring-boot-starter-data-jpa， 会注入 JpaTransactionManager
//	 * 
//	 * @可以指定为：DataSourceTransactionManager，spring boot的自动配置没有指定，所以这里也不指定
//	 * 直接指定DataSourceTransactionManager可以参考： https://www.jooq.org/doc/3.18/manual/sql-execution/transaction-management/
//	
//	 * @SpringTransactionProvider 已知bug,不适用于 jOOQ 的异步事务: https://github.com/jOOQ/jOOQ/issues/10850 
//	 * 并且r2dbc有同样的问题： https://github.com/spring-projects/spring-framework/issues/28133
//	 * 可以取消SpringTransactionProvider事务管理并使用JOOP自己的事务管理解决：
//	 * this.dslContext = dslContext.configuration().derive((TransactionProvider) null).dsl();
//	 */
//	@Bean
//	JooqSpringTransactionProvider jooqSpringTransactionProvider(
//			PlatformTransactionManager txManager) {
//		return new JooqSpringTransactionProvider(txManager);
//	}
//	
//	
//	/**
//	 * jooq提供对对外的，使用外部的数据库链接，DataSourceConnectionProvider代理类
//	 * 这里使用TransactionAwareDataSourceProxy 可以动态的发现spring 事物上下文transaction context
//	 */
//	@Bean
//	DataSourceConnectionProvider dataSourceConnectionProvider(
//			@Qualifier("dataSource2") DataSource dataSource2) {
//		return new DataSourceConnectionProvider(
//				new TransactionAwareDataSourceProxy(dataSource2));
//	}
//	
//	@Bean
//	SQLDialect sQLDialect(
//			JooqProperties properties, 
//			@Qualifier("dataSource2") DataSource dataSource2) {
//		return properties.determineSqlDialect(dataSource2);
//	}
//	
//	@Bean("dsl2")
//    DSLContext dsl2(
//    		DataSourceConnectionProvider dataSourceConnectionProvider,
//    		SQLDialect sQLDialect,
//    		JooqSpringTransactionProvider jooqSpringTransactionProvider) {
//    	
//        DefaultConfiguration config = new DefaultConfiguration();
//        config.set(sQLDialect);
//        config.set(dataSourceConnectionProvider);
//        config.set(jooqSpringTransactionProvider); //spring提供的默认嵌入式事务PROPAGATION_NESTED
//        config.set(new DefaultExecuteListenerProvider(new JooqExceptionTranslator()));
//        return new DefaultDSLContext(config);
//    }

	
}