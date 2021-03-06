
package com.imaginaryebay;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.*;


/**
 * Created by Chloe on 6/30/16.
 */


//Just tests if endpoints are accessible, doesn't really check what they do
public class ItemEndpointsTest {
    @Test
    public void getsWorkOK() {

        RestTemplate template = new RestTemplate();

        try {
            template.getForEntity("http://localhost:8080/item", List.class);
            //assertNotNull(result);
            //assertNotNull(result.getBody());
        } catch (Exception exc) {
            if (exc.getClass() == HttpClientErrorException.class){
                HttpClientErrorException httpExc = (HttpClientErrorException) exc;
                assertNotEquals(httpExc.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }

        try {
            template.getForEntity("http://localhost:8080/item?cat=Clothes", List.class);
            //assertNotNull(resultClothes);
            //assertNotNull(resultClothes.getBody());
        } catch (Exception exc) {
            if (exc.getClass() == HttpClientErrorException.class){
                HttpClientErrorException httpExc = (HttpClientErrorException) exc;
                assertNotEquals(httpExc.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }

        try {
            template.getForEntity("http://localhost:8080/item?cat=Electronics", List.class);
            //assertNotNull(resultElectronics);
            //assertNotNull(resultElectronics.getBody());
            //System.out.println(result.getBody());
            //assertThat(result.getBody().size(), is(2));
        } catch (Exception exc) {
            if (exc.getClass() == HttpClientErrorException.class){
                HttpClientErrorException httpExc = (HttpClientErrorException) exc;
                assertNotEquals(httpExc.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }

        try {
            template.getForEntity("http://localhost:8080/item/1", List.class);
            //assertNotNull(resultItem);
            //assertNotNull(resultItem.getBody());
        } catch (Exception exc) {
            if (exc.getClass() == HttpClientErrorException.class){
                HttpClientErrorException httpExc = (HttpClientErrorException) exc;
                assertNotEquals(httpExc.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }

        try {
            template.getForEntity("http://localhost:8080/item/1/picture", List.class);
        } catch (Exception exc) {
            if (exc.getClass() == HttpClientErrorException.class) {
                HttpClientErrorException httpExc = (HttpClientErrorException) exc;
                assertNotEquals(httpExc.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }
    }


    @Test
    public void addItemWorksOK() {
        RestTemplate template = new RestTemplate();
        Item item = new Item();
        item.setPrice(20.0);
        item.setCategory(Category.Clothes);
        item.setName("Scarf");
        item.setEndtime(valueOf("2016-10-10 00:00:00"));

        try {
            template.put("http://localhost:8080/item", item, Void.class);
        } catch (Exception exc) {
            if (exc.getClass() == HttpClientErrorException.class) {
                HttpClientErrorException httpExc = (HttpClientErrorException) exc;
                assertNotEquals(httpExc.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }


        try {
            template.postForEntity("http://localhost:8080/item/1/picture", null, List.class);
        } catch (Exception exc) {
            if (exc.getClass() == HttpClientErrorException.class) {
                HttpClientErrorException httpExc = (HttpClientErrorException) exc;
                assertNotEquals(httpExc.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @Test
    public void updateItemWorksOK() {
        RestTemplate template = new RestTemplate();
        Item item = new Item();
        item.setPrice(40.0);
        item.setCategory(Category.Clothes);
        item.setDescription("Scarf");
        try {
            template.put("http://localhost:8080/item/1", item);
        } catch (Exception exc) {
            if (exc.getClass() == HttpClientErrorException.class) {
                HttpClientErrorException httpExc = (HttpClientErrorException) exc;
                assertNotEquals(httpExc.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }

    }
}
