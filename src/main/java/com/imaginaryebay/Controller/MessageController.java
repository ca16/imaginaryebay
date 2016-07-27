package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Message;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * Supports HTTP operations pertaining to email messages.
 *
 */
@RestController
@RequestMapping("/message")
public interface MessageController {
	
	/**
    * Creates a new email message.
    * @param message a new email message to be created
    * @return an HTTP response containing the status
    *
    * EXAMPLE: curl -X POST -H "Content-Type: application/json" -d '{"receiver_id": {"id": 1, "address": "Seattle", "adminFlag": true, "email": "hello@gmail.com", "name": "Bobby", "password": "sdfhsfdh"},
    *  																 "date_sent": "2016-07-25T04:30:00"}' http://localhost:8080/message
    */
    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Void> createNewMessage(@RequestBody Message message);
	/*@ApiOperation(value="get email messages for a specific user")*/
    /**
    * Given the id of a user, responds with a list of the email messages associated with the user.
    * @param id the user id representing the user for which email messages are being requested
    * @return an HTTP response with a list of the email messages associated with the user
    *
    * EXAMPLE: curl -X GET localhost:8080/message/1
    */
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<List<Message>> returnMessagesByReceiverID(@PathVariable("id") Long id);

}
