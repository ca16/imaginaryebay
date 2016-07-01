package com.imaginaryebay;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Message;
import com.imaginaryebay.Models.Userr;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Chloe on 6/30/16.
 */
//See comments on ItemEnpointsTest for this actually covers
public class MessageEndpointsTest {

    @Test
    public void getsWorkOK() {

        // assertNotNulls should work if there is at least on thing of each category in the database

        RestTemplate template = new RestTemplate();
        template.getForEntity("http://localhost:8080/message/1", List.class);
    }


    @Test
    public void addMessageWorksOK() {
        RestTemplate template = new RestTemplate();
        Message mess = new Message();
        mess.setReceiver_id(new Userr("a@gmail.com", "a", "aaa"));
        mess.setDate_sent(valueOf("2016-05-05 00:04:04"));

        //If the path was wrong it would be NOT_FOUND so the path works (I think)

        try {
            template.postForEntity("http://localhost:8080/message", mess, Void.class);
        } catch (HttpClientErrorException exc) {
            assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

    }

}