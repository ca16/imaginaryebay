package com.imaginaryebay;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.sun.mail.iap.Response;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Chloe on 6/30/16.
 */

//Just tests if endpoints are accessible, doesn't really check what they do
public class ItemEndpointsTest {
    @Test
    public void getsWorkOK() {

        // assertNotNulls should work if there is at least on thing of each category in the database

        RestTemplate template = new RestTemplate();
        ResponseEntity<List> result =
                template.getForEntity("http://localhost:8080/item", List.class);
        //assertNotNull(result);
        //assertNotNull(result.getBody());

        ResponseEntity<List> resultClothes =
                template.getForEntity("http://localhost:8080/item?cat=Clothes", List.class);
        //assertNotNull(resultClothes);
        //assertNotNull(resultClothes.getBody());

        ResponseEntity<List> resultElectronics =
                template.getForEntity("http://localhost:8080/item?cat=Electronics", List.class);
        //assertNotNull(resultElectronics);
        //assertNotNull(resultElectronics.getBody());
        //System.out.println(result.getBody());
        //assertThat(result.getBody().size(), is(2));

        ResponseEntity<Item> resultItem = template.getForEntity("http://localhost:8080/item/1", Item.class);
        //assertNotNull(resultItem);
        //assertNotNull(resultItem.getBody());

        template.getForEntity("http://localhost:8080/item/description/1", String.class);

        template.getForEntity("http://localhost:8080/item/price/1", Double.class);

        template.getForEntity("http://localhost:8080/item/endtime/1", Timestamp.class);

        template.getForEntity("http://localhost:8080/item/category/1", Category.class);

        try {
            template.getForEntity("http://localhost:8080/item/1/picture", List.class);
        } catch (HttpClientErrorException exc) {
            assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
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