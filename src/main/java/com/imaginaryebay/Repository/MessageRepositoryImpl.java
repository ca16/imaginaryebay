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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class MessageRepositoryImpl implements MessageRepository {
	@Autowired
	private UserrDao userrDao;
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
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		Userr userr = userrDao.getUserrByID(id);
		if (userr == null) {
			throw new RestException("Not available.", "User with ID " + id + " does not exist.", HttpStatus.OK);
		} else if (userr.getEmail().equals(email) | isAdmin) {
			List<Message> messages = this.messageDao.findAllMessagesByReceiverID(id);
	    	if(!messages.isEmpty()){
	    		return messages;
	    	}
	    	throw new RestException("No messages for receiver.","Messages for receiver with id " + id + " were not found.",HttpStatus.OK);
		} else {
			throw new RestException("Not available.", "You do not have authority to view this user's messages.", HttpStatus.FORBIDDEN);
		}
    	
    }


}