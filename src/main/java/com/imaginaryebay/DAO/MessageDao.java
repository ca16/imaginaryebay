package com.imaginaryebay.DAO;

import java.util.List;

import com.imaginaryebay.Models.Message;
/**
 * 
 * Supports database interactions on email messages.
 *
 */
public interface MessageDao {
	/**
	 * Adds the given email message to the database.
	 * @param message the email message to be added to the database
	 */
    void persist(Message message);
    /**
     * Given the id of a user who exists in the database, selects all the email messages in the database that are related to that user.
     * @param receiver_id the id of the user whose email messages are to be selected from the database
     * @return a list of the email messages that exist in the database that are related to the given user
     */
    List<Message> findAllMessagesByReceiverID(Long receiver_id);

}