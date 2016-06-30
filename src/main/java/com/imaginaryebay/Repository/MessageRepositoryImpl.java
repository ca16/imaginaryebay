package com.imaginaryebay.Repository;
import java.util.List;

import com.imaginaryebay.DAO.MessageDao;
import com.imaginaryebay.Models.Message;

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
        return this.messageDao.findAllMessagesByReceiverID(id);
    }


}