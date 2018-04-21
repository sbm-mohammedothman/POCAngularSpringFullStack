package com.smb.demo.angularspringbe.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.hibernate.cfg.Environment.*;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.sbm.demo.angularspringbe.config")
@ComponentScan("com.sbm.demo.angularspringbe.dao")
@ComponentScan("com.sbm.demo.angularspringbe.service")
@ComponentScan("com.sbm.demo.angularspringbe.model")
@PropertySource ("classpath:db.properties")
public class HibernateConfig {
	
	//Hibernate configuration without datasource
	
	@Autowired
	   private Environment env;

	   @Bean
	   public LocalSessionFactoryBean getSessionFactory() {
	      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

	      Properties props = new Properties();
	      // Setting JDBC properties
	      props.put(DRIVER, env.getProperty("mysql.driver"));
	      props.put(URL, env.getProperty("mysql.url"));
	      props.put(USER, env.getProperty("mysql.user"));
	      props.put(PASS, env.getProperty("mysql.password"));

	      // Setting Hibernate properties
	      props.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
	      props.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
	      props.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
	      props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));

	      // Setting C3P0 properties
	      props.put(C3P0_MIN_SIZE, env.getProperty("hibernate.c3p0.min_size"));
	      props.put(C3P0_MAX_SIZE, env.getProperty("hibernate.c3p0.max_size"));
	      props.put(C3P0_ACQUIRE_INCREMENT, 
	            env.getProperty("hibernate.c3p0.acquire_increment"));
	      props.put(C3P0_TIMEOUT, env.getProperty("hibernate.c3p0.timeout"));
	      props.put(C3P0_MAX_STATEMENTS, env.getProperty("hibernate.c3p0.max_statements"));

	      factoryBean.setHibernateProperties(props);
	      factoryBean.setPackagesToScan("com.smb.demo.angularspringbe.model");

	      return factoryBean;
	   }
	   
	   @Bean
	    public DataSource dataSource() {
		 
		 DriverManagerDataSource dataSource = new DriverManagerDataSource();
		 try
		 {
	        dataSource.setDriverClassName(env.getProperty("mysql.driver"));
	        dataSource.setUrl(env.getProperty("mysql.url"));
	        dataSource.setUsername(env.getProperty("mysql.user"));
	        dataSource.setPassword(env.getProperty("mysql.password"));
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			
		 }     
	        return dataSource;
	    }
	

	   @Bean
	   public HibernateTransactionManager getTransactionManager() {
	      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	      transactionManager.setSessionFactory(getSessionFactory().getObject());
	      return transactionManager;
	   }
}