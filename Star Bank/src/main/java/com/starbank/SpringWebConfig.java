package com.starbank;

import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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

import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.IAdminDAO;
import com.starbank.model.dao.ICardDAO;
import com.starbank.model.dao.ICreditDAO;
import com.starbank.model.dao.ICurrentAccountDAO;
import com.starbank.model.dao.IDepositDAO;
import com.starbank.model.dao.IMessageDAO;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.dao.repo.AccountRepository;
import com.starbank.model.dao.repo.AdminRepository;
import com.starbank.model.dao.repo.CardRepository;
import com.starbank.model.dao.repo.CreditRepository;
import com.starbank.model.dao.repo.CurrentAccountRepository;
import com.starbank.model.dao.repo.DepositRepository;
import com.starbank.model.dao.repo.MessageRepository;
import com.starbank.model.dao.repo.TransactionFinalizerRepository;
import com.starbank.model.dao.repo.UserRepository;

@Configuration
@EnableWebMvc
@ComponentScan("com.starbank")
@PropertySource("classpath:config.properties")
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	@Value("${dbDriver}")
	private String dbDriver;
	@Value("${dbUrlPrefix}")
	private String dbUrlPrefix;
	@Value("${dbSchema}")
	private String dbSchema;
	@Value("${sslDisable}")
	private String sslDisable;
	@Value("${dbPort}")
	private String dbPort;
	@Value("${dbHost}")
	private String dbHost;
	@Value("${dbPassword}")
	private String dbPassword;
	@Value("${dbUsername}")
	private String dbUsername;


	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

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
		dataSource.setDriverClassName(dbDriver);
		System.out.println(dbUrlPrefix + dbHost + dbPort + dbSchema + sslDisable);
		dataSource.setUrl(dbUrlPrefix + dbHost + ":" + dbPort + "/" + dbSchema + sslDisable);
		dataSource.setUsername(dbUsername);
		dataSource.setPassword(dbPassword);

		return dataSource;
	}

	@Bean
	public IUserDAO getUserRepository() {
		return new UserRepository(getDataSource());
	}

	@Bean
	public IAdminDAO getAdminRepository() {
		return new AdminRepository(getDataSource());
	}

	@Bean
	public IAccountDAO getAccountRepository() {
		return new AccountRepository(getDataSource(), transactionTemplate());
	}

	@Bean
	public IMessageDAO getSendMessageRepository() {
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

	@Bean
	public ICurrentAccountDAO getCurrentAccountRepository() {
		return new CurrentAccountRepository(getDataSource());
	}

	@Bean
	public IDepositDAO getDepositRepository() {
		return new DepositRepository(getDataSource());
	}

	@Bean
	public ICreditDAO getCreditRepository() {
		return new CreditRepository(getDataSource());
	}

	@Bean
	public ICardDAO getCardRepository() {
		return new CardRepository(getDataSource());
	}

}
