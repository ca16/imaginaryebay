package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Message;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.UserrRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ben_Big on 6/28/16.
 */


@ComponentScan("com.imaginaryebay.Repository")

public class UserrControllerImpl implements UserrController {

    @Autowired
    private UserrRepository userrRepository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage accountCreationMessage;
    @Autowired
    private MessageController messageController;

    public void setUserrRepository(UserrRepository userrRepository){
        this.userrRepository = userrRepository;
    }

    @Override
    public ResponseEntity<Void> createNewUserr(Userr userr){
        //ToDo: should have a mechanism here to check if the user exists or not
        // Done in repository part
        userrRepository.createNewUserr(userr);
        SimpleMailMessage msg = new SimpleMailMessage(this.accountCreationMessage);
        msg.setTo(userr.getEmail());
        msg.setSentDate(new Date());
        msg.setText(
                "Dear " + userr.getName()
                        + ", thank you for creating an account. Your account username is "
                        + userr.getEmail() + " and your password is " + userr.getPassword() + ".");
        try {
            this.mailSender.send(msg);
            System.out.println("Message sent successfully");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
        messageController.createNewMessage(new Message(userr,new Timestamp(msg.getSentDate().getTime())));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Userr> getUserrByID(Long id){
        return new ResponseEntity<>(userrRepository.getUserrByID(id), HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Userr> getUserrByEmail(String email){
        return new ResponseEntity<>(userrRepository.getUserrByEmail(email), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Userr>> getAllUserrs (){
        return new ResponseEntity<>(userrRepository.getAllUserrs(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Userr>> getUserrByName(String name){
        return new ResponseEntity<>(userrRepository.getUserrByName(name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Userr> updateUserrByID(Long id, Userr u){
        return new ResponseEntity<>(userrRepository.updateUserrByID(id, u), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Item>> getItemsSoldByThisUser(Long id){
        return new ResponseEntity<>(userrRepository.getItemsSoldByThisUser(id), HttpStatus.OK);
    }
}
