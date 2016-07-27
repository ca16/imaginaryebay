package com.imaginaryebay.Controller;

import java.util.Properties;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.imaginaryebay.Models.ContactFormEmail;

public class ContactControllerImpl implements ContactController {

	@Override
	public ResponseEntity<Void> sendEmail(ContactFormEmail contactEmail) {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true);
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("tinavivio@gmail.com");
        javaMailSender.setPassword("jingo123");
        javaMailSender.setJavaMailProperties(mailProperties);
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("tinavivio@gmail.com");
        msg.setSubject("Contact Form Submission from " + contactEmail.getName());
        msg.setText(contactEmail.getEmailContent());
        try {
            javaMailSender.send(msg);
            System.out.println("Message sent successfully");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
        SimpleMailMessage msgToUser = new SimpleMailMessage();
        msgToUser.setTo(contactEmail.getEmailAddress());
        msgToUser.setSubject("Contact Form Submission Received");
        msgToUser.setText("Thank you for contacting us. We will respond within 24-48 hours.");
        try {
            javaMailSender.send(msgToUser);
            System.out.println("Message to user sent successfully");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
