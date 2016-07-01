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
//See ItemEnpointsTest comments for what this actually covers
public class UserrEndpointsTest {

    @Test
    public void getsWorkOK() {

        RestTemplate template = new RestTemplate();
        template.getForEntity("http://localhost:8080/user/1", List.class);
    }

}