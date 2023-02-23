package jj.tech.paolu.config.datasource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

/**
 * 定义除spring提供默认数据源，以外的自定义数据源
 * 参考： https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto.data-access.configure-two-datasources
 * 	 	https://github.com/spring-projects/spring-boot/issues/20814
 * 		https://github.com/spring-projects/spring-boot/issues/13325
 * @author Dou
 */
@Configuration(proxyBeanMethods=false)
public class DataSourceConfig {


	
	@Bean
	@Primary
	@ConditionalOnClass(HikariDataSource.class)
	//@ConfigurationProperties("spring.datasource")
	HikariDataSource dataSource(DataSourceProperties properties) {
		
		return (HikariDataSource) properties.initializeDataSourceBuilder()
				 .type(HikariDataSource.class).build();
	}
	
//	/**
//	 * 数据库名称url:需要改成jdbc-url，其他名称值和spring默认的一样
//	 * 就可以自动注入
//	 */
//	@Bean("dataSource2")
//	@ConfigurationProperties("app.datasource")
//	public HikariDataSource dataSource2() {
//		return (HikariDataSource) DataSourceBuilder.create()
//				.type(HikariDataSource.class).build();
//	}
	
	
	
//	/**
//	 * @p DataSourceProperties
//	 * @p 如果没有 显示的提供 url 用户名、密码; 创建一个默认的嵌入式的数据库
//	 * @p 并提供url/jdbcUrl的转化
//	 * @p @EnableConfigurationProperties(DataSourceProperties.class)，这个注解可以不用加，因为
//	 * spring 在初始化 DataSourceAutoConfiguration的时候，会初始化DataSourceProperties这个类到spring上下文
//	 */
//	@Bean
//	@Primary
//	@ConfigurationProperties("spring.datasource")
//	public DataSourceProperties dataSourceProperties() {
//		return new DataSourceProperties();
//	}

	
	


}