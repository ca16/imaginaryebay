package com.imaginaryebay.Controller;

import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.imaginaryebay.Models.ContactFormEmail;

public class ContactControllerImpl implements ContactController {
	
	@Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage contactMessageToUser;
    @Autowired
    private SimpleMailMessage contactMessageToAdmin;

	@Override
	public ResponseEntity<ContactFormEmail> sendEmail(ContactFormEmail contactEmail) {
		SimpleMailMessage msgToUser = new SimpleMailMessage(this.contactMessageToUser);
        msgToUser.setTo(contactEmail.getEmailAddress());
        try {
            this.mailSender.send(msgToUser);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
        SimpleMailMessage msgToAdmin = new SimpleMailMessage(this.contactMessageToAdmin);
        msgToAdmin.setText(contactEmail.getEmailContent() + " Received by: " + contactEmail.getName() + ", " + contactEmail.getEmailAddress());
        try {
            this.mailSender.send(msgToAdmin);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
		return new ResponseEntity<ContactFormEmail>(contactEmail, HttpStatus.OK);
	}

}
