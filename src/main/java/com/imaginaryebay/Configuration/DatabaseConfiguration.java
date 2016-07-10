package com.imaginaryebay.Configuration;

//import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by Ben_Big on 6/27/16.
 */


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.imaginaryebay"})
@EnableWebMvc
public class DatabaseConfiguration {
/*
    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws PropertyVetoException{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("org.postgresql.Driver");
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/testdb");
        datSaSource.setUser("postgres");
        dataSource.setPassword("jingo123");
        return dataSource;
    }

    private Map<String,?> jpaProperties() {
        Map<String,String> jpaPropertiesMap = new HashMap<String,String>();
        jpaPropertiesMap.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");

        //ToDo: "update" property should only be used during development
        jpaPropertiesMap.put("hibernate.hbm2ddl.auto", "update");
        return jpaPropertiesMap;
    }*/

    @Bean
    public DataSource dataSource() throws PropertyVetoException{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("org.postgresql.Driver");
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/testdb");
        dataSource.setUser("postgres");
        dataSource.setPassword("jingo123");
        return dataSource;
    }

//    @Bean
//    public DataSource dataSource() throws PropertyVetoException{
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass("org.postgresql.Driver");
//        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/firstdb");
//        dataSource.setUser("Chloe");
//        dataSource.setPassword("");
//        return dataSource;
//    }

    private Map<String,?> jpaProperties() {
        Map<String,String> jpaPropertiesMap = new HashMap<String,String>();
        jpaPropertiesMap.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        jpaPropertiesMap.put("hibernate.show_sql", "true");
//        jpaPropertiesMap.put("hibernate.hbm2ddl.auto", "create");
        return jpaPropertiesMap;
    }


    //ToDo: What to do with PropertyVetoException
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws PropertyVetoException{
        LocalContainerEntityManagerFactoryBean factoryBean=
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.imaginaryebay.");
        factoryBean.setJpaPropertyMap(jpaProperties());
        return factoryBean;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }





}
