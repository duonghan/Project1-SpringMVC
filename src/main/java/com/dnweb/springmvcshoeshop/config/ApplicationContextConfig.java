package com.dnweb.springmvcshoeshop.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.dnweb.springmvcshoeshop.entities.Account;

@Configuration
@ComponentScan("com.dnweb.springmvcshoeshop.*")

@EnableTransactionManagement
@PropertySource("classpath:ds-hibernate-cfg.properties")
public class ApplicationContextConfig {

	// Lưu trữ các giá thuộc tính load bởi @PropertySource.
	@Autowired
	private Environment env;

	@Bean
	public ResourceBundleMessageSource messageSource() {
		// Tai noi dung tu message/validator.properties
		ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
		rb.setBasenames(new String[] { "messages/validator" });
		return rb;
	}

	//Cau hinh chi tile view
	@Bean(name = "viewResolver")
	public ViewResolver getViewResolver() {

		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(TilesView.class);
		return viewResolver;
	}

	@Bean(name = "tilesConfigurer")
	public TilesConfigurer getTilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
		return tilesConfigurer;
	}

	// Cấu hình để Upload.
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();

		// Set Max Size = 25MB
		commonsMultipartResolver.setMaxUploadSize(25 * 1024 * 1024);

		return commonsMultipartResolver;
	}

	//Tai noi dung tu ds-hibernate-cfg.properties
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
		dataSource.setUrl(env.getProperty("ds.url"));
		dataSource.setUsername(env.getProperty("ds.username"));
		dataSource.setPassword(env.getProperty("ds.password"));

		System.out.println("## getDataSource: " + dataSource);

		return dataSource;
	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {

		try {
			Properties properties = new Properties();

			// Xem: ds-hibernate-cfg.properties
			properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
			properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
			properties.put("current_session_context_class", env.getProperty("current_session_context_class"));

			LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
			Package pkg = Account.class.getPackage();
			
			String pkgName = pkg.getName();

			// Package chứa các entity class.
			//  "com.dnweb.springmvcshoeshop.entities" 
			//   com.dnweb.springmvcshoeshop.entities
			factoryBean.setPackagesToScan(new String[] {pkgName});
			factoryBean.setDataSource(dataSource);
			factoryBean.setHibernateProperties(properties);
			factoryBean.afterPropertiesSet();
			//
			SessionFactory sf = factoryBean.getObject();
			System.out.println("## getSessionFactory: " + sf);
			return sf;

		} catch (Exception e) {
			System.out.println("Error getSessionFactory: " + e);
			e.printStackTrace();
			throw e;
		}
	}

	// Hibernate Transaction Manager
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}

}
