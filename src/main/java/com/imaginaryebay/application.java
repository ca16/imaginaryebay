package com.imaginaryebay;

import com.apple.eawt.Application;

import com.imaginaryebay.Configuration.*;
import com.imaginaryebay.Repository.*;
import com.imaginaryebay.Models.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import com.imaginaryebay.Configuration.DatabaseConfiguration;
import com.imaginaryebay.Configuration.ModelConfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Ben_Big on 6/27/16.
 */

public class application {
    public static void main(String[] args) {
    	
        	ApplicationContext applicationContext = 
               new AnnotationConfigApplicationContext(MailConfiguration.class);
        	 
        	SendEmail sm = (SendEmail) applicationContext.getBean(SendEmail.class);
            sm.sendEmailAccountCreation(new Userr("t_vivio@yahoo.com","Rahul","raughOZ:SRHGia;ro"));
            
        }
    }