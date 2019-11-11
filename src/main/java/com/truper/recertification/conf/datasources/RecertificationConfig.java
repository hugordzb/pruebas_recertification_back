package com.truper.recertification.conf.datasources;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.truper.recertification.conf.Profiles;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        basePackages = {"com.truper.recertification.dao"})
public class RecertificationConfig {
    
    @Autowired
    private Environment env;
    
    private String prefix = "recertification.datasource";
    
    @Autowired
    @Qualifier("dataSourceRecertification")
    private DataSource dataSourceRecertification;
    
    @Bean
    @ConfigurationProperties(prefix = "recertification.datasource")
    public JndiPropertyHolder primary() {
        return new JndiPropertyHolder();
    }
    
    @Primary
    @Profile({Profiles.PROD, Profiles.QAS})
    @Bean(name = "dataSourceRecertification")
    public DataSource dataSource() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource(primary().getJndiName());
        return dataSource;
    }
    
    @Bean(name = "jdbcrecertificationDS")
    @Primary
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSourceRecertification") DataSource datasource) {
    	return new JdbcTemplate(datasource);
    }
    
    @Primary
    @Bean(name = "dataSourceRecertification")
    @Profile({Profiles.DEV})
    public DataSource recertificationDataSourceDev() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(prefix + ".driver-class-name"));
        dataSource.setUrl(env.getProperty(prefix + ".url"));
        dataSource.setUsername(env.getProperty(prefix + ".username"));
        dataSource.setPassword(env.getProperty( prefix + ".password"));
        return dataSource;
    }
    
    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(this.dataSourceRecertification);
        em.setPackagesToScan(new String[] { "com.truper.recertification.model"});
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put(org.hibernate.cfg.Environment.IMPLICIT_NAMING_STRATEGY, "org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl");
        properties.put(org.hibernate.cfg.Environment.PHYSICAL_NAMING_STRATEGY, "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", 
                this.env.getProperty(this.prefix + ".hibernate.dialect"));
        properties.put("hibernate.enable_lazy_load_no_trans", 
                Boolean.parseBoolean(this.env.getProperty(this.prefix + ".hibernate.enable_lazy_load_no_trans")));
        properties.put("hibernate.show_sql", 
                Boolean.parseBoolean(this.env.getProperty(this.prefix + ".hibernate.show_sql")));
        properties.put("hibernate.format_sql", 
                Boolean.parseBoolean(this.env.getProperty(this.prefix + ".hibernate.format_sql")));
        em.setJpaPropertyMap(properties);
        return em;
    }
    
    @Primary
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }
    
    @Setter
    @Getter
    private static class JndiPropertyHolder {
        private String jndiName;
    }
}