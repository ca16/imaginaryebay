package com.imaginaryebay.Configuration;

import com.imaginaryebay.Controller.UserrRestController;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.DAO.UserrDaoImpl;
import com.imaginaryebay.Repository.UserrRepository;
import com.imaginaryebay.Repository.UserrRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ben_Big on 6/27/16.
 */

@Configuration
public class ModelConfiguration {

    //All the bean dependencies use setter injection

    @Bean
    public UserrDao userrDao(){
        UserrDaoImpl bean = new UserrDaoImpl();
        return bean;
    }

    @Bean
    public UserrRepository userrRepository(){
        UserrRepositoryImpl bean=new UserrRepositoryImpl();
        bean.setUserrDao(userrDao());
        return bean;
    }

    @Bean
    public UserrRestController userrRestController(){
        UserrRestController bean=new UserrRestController();
        bean.setUserrRepository(userrRepository());
        return bean;
    }




}
