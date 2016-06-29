package com.imaginaryebay.DAO;

import java.util.List;

import com.imaginaryebay.Models.Message;

public interface MessageDao {

	void persist(Message message);
	
	List<Message> findAllMessagesByReceiverID(Long receiver_id);

}