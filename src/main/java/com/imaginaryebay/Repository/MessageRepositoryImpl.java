package com.imaginaryebay.Repository;
import java.util.List;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.Controller.UserrController;
import com.imaginaryebay.Controller.UserrControllerImpl;
import com.imaginaryebay.DAO.MessageDao;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.DAO.UserrDaoImpl;
import com.imaginaryebay.Models.Message;
import com.imaginaryebay.Models.Userr;

import org.springframework.transaction.annotation.Transactional;


@Transactional
public class MessageRepositoryImpl implements MessageRepository {

    private MessageDao messageDao;

    public void setMessageDao(MessageDao messageDao){
        this.messageDao=messageDao;
    }
    @Override
    public void createNewMessage(Message message) {
        this.messageDao.persist(message);
    }

    @Override
    public List<Message> returnAllMessagesByReceiverID(Long id) {
    	List<Message> messages = this.messageDao.findAllMessagesByReceiverID(id);
    	if(!messages.isEmpty()){
    		return messages;
    	}
    	throw new RestException("No messages for receiver.","Messages for receiver with id " + id + " were not found.");
    }


}