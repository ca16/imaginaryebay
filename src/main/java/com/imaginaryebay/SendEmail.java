package com.imaginaryebay;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.imaginaryebay.Models.Userr;

public class SendEmail {

    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    public void sendEmailAccountCreation(Userr userr) {

        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(userr.getEmail());
        msg.setText(
                "Dear " + userr.getName()
                        + ", thank you for creating an account. Your account username is "
                        + userr.getEmail()+" and your password is " +userr.getPassword()+".");
        try{
            this.mailSender.send(msg);
            System.out.println("Message sent successfully");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }



}