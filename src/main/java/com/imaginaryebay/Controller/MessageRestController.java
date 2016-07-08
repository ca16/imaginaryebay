package com.imaginaryebay.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imaginaryebay.Models.Message;
import com.imaginaryebay.Repository.MessageRepository;

//@RestController
//@RequestMapping("/message")
public class MessageRestController implements MessageController{

    private MessageRepository messageRepository;
    public void setMessageRepository(MessageRepository messageRepository){
        this.messageRepository=messageRepository;
    }
    //@RequestMapping(method= RequestMethod.POST)
    //public void createNewMessage(@RequestBody Message message){
    public ResponseEntity<Void> createNewMessage(Message message){
        this.messageRepository.createNewMessage(message);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    //@RequestMapping(value="/{ID}", method=RequestMethod.GET)
    //public List<Message> returnMessagesByReceiverID(@PathVariable("ID") Long id){
    public ResponseEntity<List<Message>> returnMessagesByReceiverID(Long id){
        return new ResponseEntity<List<Message>>(this.messageRepository.returnAllMessagesByReceiverID(id),HttpStatus.OK);
    }
}
