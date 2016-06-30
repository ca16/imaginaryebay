package com.imaginaryebay;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Chloe on 6/30/16.
 */
//See comments on ItemEnpointsTest for this actually covers
public class MessageEnpointsTest {

    @Test
    public void getsWorkOK() {

        // assertNotNulls should work if there is at least on thing of each category in the database

        RestTemplate template = new RestTemplate();
        template.getForEntity("http://localhost:8080/message/1", List.class);
    }


    @Test
    public void addItemWorksOK() {
        RestTemplate template = new RestTemplate();
        Item item = new Item();
        item.setPrice(20.0);
        item.setCategory(Category.Clothes);
        item.setDescription("Scarf");
        ResponseEntity<Void> resultSave = template.postForEntity("http://localhost:8080/item", item, Void.class);
        assertNotNull(resultSave);

        //If the path was wrong it would be NOT_FOUND so the path works (I think)
        try {
            template.postForEntity("http://localhost:8080/item/1/picture", null, List.class);
        } catch (HttpServerErrorException exc) {
            assertEquals(exc.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Test
    public void updateItemWorksOK() {
        RestTemplate template = new RestTemplate();
        Item item = new Item();
        item.setPrice(40.0);
        item.setCategory(Category.Clothes);
        item.setDescription("Scarf");
        template.put("http://localhost:8080/item/1", item);
    }
}
