package jj.tech.paolu.config.jpa;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
/**
 * JPA多数据源配置,单一数据源由spring boot默认配置
 * https://github.com/spring-projects/spring-data-examples/blob/main/jpa/multiple-datasources/src/main/java/example/springdata/jpa/multipleds/customer/CustomerConfig.java
 * @author Dou
 *
 */
//@Configuration
//@EnableJpaRepositories(entityManagerFactoryRef = "customerEntityManagerFactory",
//		transactionManagerRef = "customerTransactionManager")
public class JpaConfig {
//	@Bean
//	PlatformTransactionManager customerTransactionManager() {
//		return new JpaTransactionManager(customerEntityManagerFactory().getObject());
//	}
//
//	@Bean
//	LocalContainerEntityManagerFactoryBean customerEntityManagerFactory() {
//
//		var jpaVendorAdapter = new HibernateJpaVendorAdapter();
//		jpaVendorAdapter.setGenerateDdl(true);
//
//		var factoryBean = new LocalContainerEntityManagerFactoryBean();
//
//		factoryBean.setDataSource(customerDataSource());
//		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
//		factoryBean.setPackagesToScan(JpaConfig.class.getPackage().getName());
//
//		return factoryBean;
//	}
//	
//	@Bean
//	DataSource customerDataSource() {
//		return new EmbeddedDatabaseBuilder().//
//				setType(EmbeddedDatabaseType.HSQL).//
//				setName("customers").//
//				build();
//	}
}
