package com.imaginaryebay.Repository;

import java.util.List;

import com.imaginaryebay.Models.Message;

public interface MessageRepository {
	public void createNewMessage(Message message);

    public List<Message> returnAllMessagesByReceiverID(Long id);
}
