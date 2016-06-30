package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Message;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Chloe on 6/30/16.
 */

@RestController
@RequestMapping("/message")
public interface MessageController {

    @RequestMapping(method= RequestMethod.POST)
    public void createNewMessage(@RequestBody Message message);

    @RequestMapping(value="/{ID}", method=RequestMethod.GET)
    public List<Message> returnMessagesByReceiverID(@PathVariable("ID") Long id);

}
