package com.imaginaryebay;

import com.imaginaryebay.Models.Message;
import com.imaginaryebay.Models.Userr;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Chloe on 6/30/16.
 */
//See comments on ItemEndpointsTest for this actually covers
public class MessageEndpointsTest {

    @Test
    public void getsWorkOK() {

        // assertNotNulls should work if there is at least on thing of each category in the database

        RestTemplate template = new RestTemplate();
        try {
            template.getForEntity("http://localhost:8080/message/1", List.class);
        }catch (Exception exc){
            if (exc.getClass() == HttpClientErrorException.class){
                HttpClientErrorException hexc = (HttpClientErrorException) exc;
                assertNotEquals(hexc.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }
    }


    @Test
    public void addMessageWorksOK() {
        RestTemplate template = new RestTemplate();
        Message mess = new Message();
        mess.setReceiver_id(new Userr("a@gmail.com", "a", "aaa", true));
        mess.setDate_sent(valueOf("2016-05-05 00:04:04"));

        //If the path was wrong it would be NOT_FOUND so the path works (I think)

        try {
            template.postForEntity("http://localhost:8080/message", mess, Void.class);
        } catch (Exception exc) {
            if (exc.getClass() == HttpClientErrorException.class) {
                HttpClientErrorException hexc = (HttpClientErrorException) exc;
                assertNotEquals(hexc.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }

    }

}
