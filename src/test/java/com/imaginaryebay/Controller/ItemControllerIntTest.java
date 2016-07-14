package com.imaginaryebay.Controller;

import com.imaginaryebay.Configuration.DatabaseConfiguration;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManagerFactory;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;



/**
 * Created by Brian on 7/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
@WebAppConfiguration
public class ItemControllerIntTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private Item item1;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void readSingleItem() throws Exception {
        mockMvc.perform(get("/item/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.price", is(40.0)))
                .andExpect(jsonPath("$.category", is("Clothes")))
                .andExpect(jsonPath("$.description", is("Scarf")));
    }

//    @Test
//    public void createSingleItem() throws Exception {
//        mockMvc.perform(post("/item").contentType(contentType).content("{\"id\": 1, \"price\": 40, \"endtime\": null, \"description\": \"Scarf\", \"category\": \"Clothes\"}"))
//                .andExpect(status().isCreated());
//    }
}
