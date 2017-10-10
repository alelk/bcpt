package com.alelk.bcpt.database;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Bcpt Database Config
 *
 * Created by Alex Elkin on 07.09.2017.
 */
@Configuration
@Import(BcptServiceAdaptersConfig.class)
@EnableTransactionManagement
@ComponentScan({"com.alelk.bcpt.database"})
@PropertySource({"bcpt-database.properties"})
class BcptDatabaseConfig {

    private Environment environment;

    @Autowired
    BcptDatabaseConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, Properties hibernateProperties) {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.alelk.bcpt.database");
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource(
                environment.getProperty("jdbc.url"),
                environment.getProperty("jdbc.user"),
                environment.getProperty("jdbc.pass")
        );
        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        return dataSource;
    }

    @Bean
    @Autowired
    public JpaTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new JpaTransactionManager(sessionFactory);
    }

    @Bean
    PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
                setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
                setProperty("hibernate.enable_lazy_load_no_trans", environment.getProperty("hibernate.enable_lazy_load_no_trans"));
                setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
            }
        };
    }
}
