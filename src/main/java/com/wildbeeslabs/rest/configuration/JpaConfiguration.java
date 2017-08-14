package com.wildbeeslabs.rest.configuration;

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

@Configuration
@EnableJpaRepositories(basePackages = "com.wildbeeslabs.rest.repositories",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
//@ImportResource("classpath:hibernate-config.xml")
//@PropertySource({"classpath:application.yml"})
@EnableTransactionManagement
public class JpaConfiguration {

    @Autowired
    private Environment environment;

    @Value("${datasource.subscriptionapp.maxPoolSize:10}")
    private int maxPoolSize;

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
        return factoryBean;
    }

    /*
     * Hibernate specific adapter
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        return hibernateJpaVendorAdapter;
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
        return properties;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
}
