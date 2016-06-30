package com.imaginaryebay;


import com.imaginaryebay.Configuration.*;
import com.imaginaryebay.Repository.*;
import com.imaginaryebay.Models.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Ben_Big on 6/27/16.
 */


//@SpringBootApplication
public class application {
    public static void main(String[] args) {
    	
        	ApplicationContext applicationContext = 
               new AnnotationConfigApplicationContext(MailConfiguration.class);
        	 
        	SendEmail sm = (SendEmail) applicationContext.getBean(SendEmail.class);
            sm.sendEmailAccountCreation(new Userr("rahmtho90@gmail.com","Rahul","raughOZ:SRHGia;ro"));
            
        }
        //SpringApplication.run(application.class,args);

        //ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DatabaseConfiguration.class, ModelConfiguration.class);

        //UserrRepository userrRepository = applicationContext.getBean(UserrRepository.class);


        //Userr usr = new Userr();
        //usr.setEmail("secondUser@gmail.com");
        //userrRepository.createNewUserr(usr);


        //System.out.println(userrRepository.getUserrByID(250).getEmail());

        //System.out.println(userrRepository.getUserrByEmail("firstUser@gmail.com").getEmail());
    }


