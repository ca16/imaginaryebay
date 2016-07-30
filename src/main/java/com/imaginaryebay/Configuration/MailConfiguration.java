package com.imaginaryebay.Configuration;

import com.imaginaryebay.SendEmail;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//import com.imaginaryebay.SendEmail;

@Configuration
public class MailConfiguration {

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true);
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("tinavivio@gmail.com");
        javaMailSender.setPassword("jingo123");
        javaMailSender.setJavaMailProperties(mailProperties);
        return javaMailSender;
    }
    @Bean
    public SimpleMailMessage accountCreationMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tinavivio@gmail.com");
        message.setSubject("Your Account Creation");
        return message;
    }
    
    @Bean
    public SimpleMailMessage contactMessageToUser(){
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setFrom("tinavivio@gmail.com");
    	message.setSubject("Contact Form Submission Received");
    	message.setText("Thank you for contacting us. We will respond within 24-48 hours.");
    	return message;
    }
    
    @Bean
    public SimpleMailMessage contactMessageToAdmin(){
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setSubject("Contact Form Submission Received");
    	message.setTo("tinavivio@gmail.com");
    	return message;
    }

}
