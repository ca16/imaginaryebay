package com.imaginaryebay;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import com.imaginaryebay.Models.Userr;

public class SendEmail extends TimerTask{

	private Userr to;
	private String subject;
	private String text;

	public Userr getTo() {
		return to;
	}

	public void setTo(Userr to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public SendEmail(Userr to, String subject, String text) {
		super();
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

	@Override
	public void run() {
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
        msg.setTo(this.to.getEmail());
        msg.setFrom("tinavivio@gmail.com");
        msg.setSubject(this.subject);
        msg.setText(this.text);
        try {
            javaMailSender.send(msg);
            System.out.println("Message sent successfully");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
	}
}
