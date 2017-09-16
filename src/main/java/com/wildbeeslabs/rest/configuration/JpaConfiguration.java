package com.wildbeeslabs.rest.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;
import java.beans.PropertyVetoException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
//import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.wildbeeslabs.rest.repositories",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
//@ImportResource("classpath:hibernate.cfg.xml")
//@PropertySource({"classpath:application.yml"})
@EnableTransactionManagement
public class JpaConfiguration {

//    @PersistenceContext(unitName = "ds2", type = PersistenceContextType.TRANSACTION)
//    private EntityManager em;
    @Autowired
    private Environment environment;

    @Value("${datasource.subscriptionapp.maxPoolSize:10}")
    private int maxPoolSize;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.subscriptionapp")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /*
     * Hikari pool datasource configuration
     */
    @Bean
    public DataSource dataSource() {
        DataSourceProperties dataSourceProperties = dataSourceProperties();
        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
                .create(dataSourceProperties.getClassLoader())
                .driverClassName(dataSourceProperties.getDriverClassName())
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(maxPoolSize);
        return dataSource;
        /*final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("datasource.subscriptionapp.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("datasource.subscriptionapp.url"));
        dataSource.setUsername(environment.getRequiredProperty("datasource.subscriptionapp.username"));
        dataSource.setPassword(environment.getRequiredProperty("datasource.subscriptionapp.password"));
        return dataSource;*/
    }

    /*
     * Combo pool datasource configuration
     */
    //@Bean
    public DataSource dataSource2() throws PropertyVetoException {
        ComboPooledDataSource dataSource2 = new ComboPooledDataSource();
        dataSource2.setAcquireIncrement(Integer.valueOf(environment.getRequiredProperty("datasource.subscriptionapp.acquire_increment")));
        dataSource2.setMaxStatementsPerConnection(Integer.valueOf(environment.getRequiredProperty("datasource.subscriptionapp.maxStatementsPerConnection")));
        dataSource2.setMaxStatements(Integer.valueOf(environment.getRequiredProperty("datasource.subscriptionapp.maxStatements")));
        dataSource2.setMaxPoolSize(Integer.valueOf(environment.getRequiredProperty("datasource.subscriptionapp.maxPoolSize")));
        dataSource2.setMinPoolSize(Integer.valueOf(environment.getRequiredProperty("datasource.subscriptionapp.minPoolSize")));
        dataSource2.setJdbcUrl(environment.getRequiredProperty("datasource.subscriptionapp.url"));
        dataSource2.setUser(environment.getRequiredProperty("datasource.subscriptionapp.username"));
        dataSource2.setPassword(environment.getRequiredProperty("datasource.subscriptionapp.password"));
        dataSource2.setDriverClass(environment.getRequiredProperty("datasource.subscriptionapp.driverClassName"));
        return dataSource2;
    }

    /*
     * Entity Manager Factory initialization
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(new String[]{"com.wildbeeslabs.rest.model"});
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        factoryBean.setPersistenceUnitName("local");
//        factoryBean.setPersistenceUnitManager(persistenceUnitManager());
        return factoryBean;
    }

    /*
     * Hibernate specific adapter
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
//        hibernateJpaVendorAdapter.setShowSql(true);
//        hibernateJpaVendorAdapter.setGenerateDdl(true);
//        hibernateJpaVendorAdapter.setDatabasePlatform(environment.getRequiredProperty("datasource.subscriptionapp.hibernate.dialect"));
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /*
     * Set of Hibernate specific properties
     */
    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.subscriptionapp.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("datasource.subscriptionapp.hibernate.hbm2ddl.method"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.subscriptionapp.hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("datasource.subscriptionapp.hibernate.format_sql"));
        if (StringUtils.isNotEmpty(environment.getRequiredProperty("datasource.subscriptionapp.defaultSchema"))) {
            properties.put("hibernate.default_schema", environment.getRequiredProperty("datasource.subscriptionapp.defaultSchema"));
        }
        if (StringUtils.isNotEmpty(environment.getRequiredProperty("datasource.subscriptionapp.hibernate.globally_quoted_identifiers"))) {
            properties.put("hibernate.globally_quoted_identifiers", environment.getRequiredProperty("datasource.subscriptionapp.hibernate.globally_quoted_identifiers"));
        }
        if (StringUtils.isNotEmpty(environment.getRequiredProperty("datasource.subscriptionapp.hibernate.hbm2ddl.import_files"))) {
            properties.put("hibernate.hbm2ddl.import_files", environment.getRequiredProperty("datasource.subscriptionapp.hibernate.hbm2ddl.import_files"));
        }
        return properties;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }

    @Bean
    public FactoryBean<SessionFactory> sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }

    @Bean
    public PersistenceUnitManager persistenceUnitManager() {
        DefaultPersistenceUnitManager manager = new DefaultPersistenceUnitManager();
        manager.setDefaultDataSource(dataSource());
        return manager;
    }

    @Bean
    public BeanPostProcessor postProcessor() {
        PersistenceAnnotationBeanPostProcessor postProcessor = new PersistenceAnnotationBeanPostProcessor();
        return postProcessor;
    }
}
