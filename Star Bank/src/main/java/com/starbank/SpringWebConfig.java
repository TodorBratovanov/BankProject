package com.starbank;

import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.starbank.model.dao.repo.AccountRepository;
import com.starbank.model.dao.repo.AdminRepository;
import com.starbank.model.dao.repo.MessageRepository;
import com.starbank.model.dao.repo.TransactionFinalizerRepository;
import com.starbank.model.dao.repo.UserRepository;

@Configuration
@EnableWebMvc
@ComponentScan("com.starbank")
public class SpringWebConfig extends WebMvcConfigurerAdapter {


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("/static/assets/");
		registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/static/fonts/");
		registry.addResourceHandler("/images/**").addResourceLocations("/static/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("/static/js/");
	}


	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/jsp/");
		resolver.setSuffix(".jsp");

		return resolver;
	}

	// localization configuration
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);
		return resolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
		changeInterceptor.setParamName("language");
		registry.addInterceptor(changeInterceptor);
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/e_banking?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("qwerty1818");

		return dataSource;
	}

	@Bean
	public UserRepository getUserRepository() {
		return new UserRepository(getDataSource());
	}

	@Bean
	public AdminRepository getAdminRepository() {
		return new AdminRepository(getDataSource());
	}

	@Bean
	public AccountRepository getAccountRepository() {
		return new AccountRepository(getDataSource(), transactionTemplate());
	}

	@Bean
	public MessageRepository getSendMessageRepository() {
		return new MessageRepository(getDataSource());
	}

	@Bean
	public TransactionFinalizerRepository getTransactionFinalizerRepository() {
		return new TransactionFinalizerRepository(getDataSource(), transactionTemplate());
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(getDataSource());
	}
	

	@Bean
	public TransactionTemplate transactionTemplate() {
		TransactionTemplate transactionTemplate = new TransactionTemplate();
		transactionTemplate.setTransactionManager(transactionManager());
		return transactionTemplate;
	}


}
