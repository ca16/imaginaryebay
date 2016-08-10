package com.imaginaryebay.Configuration;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.imaginaryebay.SendEmail;

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
        javaMailSender.setUsername("imaginaryebay@gmail.com");
        javaMailSender.setPassword("poopsicle");
        javaMailSender.setJavaMailProperties(mailProperties);
        return javaMailSender;
    }
    @Bean
    public SimpleMailMessage accountCreationMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("imaginaryebay@gmail.com");
        message.setSubject("Your ImaginaryEbay Account Creation");
        return message;
    }
    
    @Bean
    public SimpleMailMessage contactMessageToUser(){
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setFrom("imaginaryebay@gmail.com");
    	message.setSubject("Contact Form Submission Received");
    	message.setText("Thank you for contacting us. We will respond within 24-48 hours. From the team at ImaginaryEbay.");
    	return message;
    }
    
    @Bean
    public SimpleMailMessage contactMessageToAdmin(){
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setSubject("Contact Form Submission Received");
    	message.setTo("imaginaryebay@gmail.com");
    	return message;
    }
    
    @Bean
    public SimpleMailMessage itemSoldMessage(){
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setFrom("imaginaryebay@gmail.com");
        message.setSubject("Your item has sold off ImaginaryEbay!");
        return message;
    }
    
    @Bean
    public SimpleMailMessage itemWonMessage(){
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setFrom("imaginaryebay@gmail.com");
        message.setSubject("You have won an item off ImaginaryEbay!");
        return message;
    }
    
    @Bean
    public SimpleMailMessage itemLostMessage(){
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setFrom("imaginaryebay@gmail.com");
        message.setSubject("Better luck next time!");
        return message;
    }
    
    @Bean
    @Scope("prototype")
    public SendEmail sendEmail(){
    	SendEmail task = new SendEmail();
    	return task;
    }
    
    @Bean
    public Timer timer(){
    	Timer timer = new Timer();
    	return timer;
    }
}
