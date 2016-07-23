package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.UserrRepository;
import com.sun.mail.iap.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 7/22/16.
 */
public class UserrControllerTest {
    @Mock
    private UserrRepository userrRepo;

    private UserrControllerImpl impl;

    private Userr admin1;
    private Userr admin2;
    private Userr nonAdmin1;
    private Userr butters2;

    private List<Userr> allUsers;

    private List<Userr> justAdmin1;
    private List<Userr> justAdmin2;
    private List<Userr> justNonAdmin1;
    private List<Userr> just1Butters;

    private Item item1;
    private Item item2;
    private Item item3;
    private Item item4;
    private Item item5;
    private Item item6;

    private List<Item> itemList1;
    private List<Item> itemList2;
    private List<Item> itemList3;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        admin1 = new Userr("jackie@gmail.com", "Jackie Fire", "dragon", true);
        admin2 = new Userr("tom@hotmail.com", "Tom Doe", "haha", true);
        nonAdmin1 = new Userr("butters@gmail.com", "Butters Stotch", "Stotch");
        butters2 = new Userr("butters2@gmail.com", "Butters Stotch", "1234");

        when(userrRepo.getUserrByID(1L)).thenReturn(admin1);
        when(userrRepo.getUserrByID(2L)).thenReturn(admin2);
        when(userrRepo.getUserrByID(3L)).thenReturn(nonAdmin1);
        when(userrRepo.getUserrByID(4L)).thenReturn(null);

        when(userrRepo.getUserrByEmail("jackie@gmail.com")).thenReturn(admin1);
        when(userrRepo.getUserrByEmail("tom@hotmail.com")).thenReturn(admin2);
        when(userrRepo.getUserrByEmail("butters@gmail.com")).thenReturn(nonAdmin1);
        when(userrRepo.getUserrByEmail("other@gmail.com")).thenReturn(null);

        allUsers = new ArrayList<>();
        allUsers.add(admin1);
        allUsers.add(admin2);
        allUsers.add(nonAdmin1);

        when(userrRepo.getAllUserrs()).thenReturn(allUsers);

        justAdmin1 = new ArrayList<>();
        justAdmin1.add(admin1);
        justAdmin2 = new ArrayList<>();
        justAdmin2.add(admin2);
        justNonAdmin1 = new ArrayList<>();
        justNonAdmin1.add(nonAdmin1);
        justNonAdmin1.add(butters2);
        just1Butters = new ArrayList<>();
        just1Butters.add(nonAdmin1);


        when(userrRepo.getUserrByName("Jackie Fire")).thenReturn(justAdmin1);
        when(userrRepo.getUserrByName("Tom Doe")).thenReturn(justAdmin2);
        when(userrRepo.getUserrByName("Butters Stotch")).thenReturn(justNonAdmin1);
        when(userrRepo.getUserrByName("Other Person")).thenReturn(null);

        item1 = new Item();
        item2 = new Item();
        item3 = new Item();
        item4 = new Item();
        item5 = new Item();
        item6 = new Item();

        item1.setDescription("Scarf");
        item2.setDescription("Watch");
        item3.setDescription("TV");
        item4.setDescription("Jeans");
        item5.setDescription("Long Scarf");
        item6.setDescription("Speakers");

        itemList1 = new ArrayList<>();
        itemList2 = new ArrayList<>();
        itemList3 = new ArrayList<>();

        itemList1.add(item1);
        itemList1.add(item2);
        itemList1.add(item3);
        itemList2.add(item4);
        itemList2.add(item5);
        itemList3.add(item6);

        when(userrRepo.getItemsSoldByThisUser(1L)).thenReturn(itemList1);
        when(userrRepo.getItemsSoldByThisUser(2L)).thenReturn(itemList2);
        when(userrRepo.getItemsSoldByThisUser(3L)).thenReturn(itemList3);

        impl = new UserrControllerImpl();
        impl.setUserrRepository(userrRepo);

    }

    @Test
    public void createNewUserr() throws Exception {


    }

    @Test
    public void getUserrByID() throws Exception {

        ResponseEntity<Userr> response1 = impl.getUserrByID(1L);
        ResponseEntity<Userr> response2 = impl.getUserrByID(2L);
        ResponseEntity<Userr> response3 = impl.getUserrByID(3L);


        Assert.assertEquals(admin1, response1.getBody());
        Assert.assertEquals(admin2, response2.getBody());
        Assert.assertEquals(nonAdmin1, response3.getBody());

        Assert.assertEquals(HttpStatus.OK, response1.getStatusCode());
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
        Assert.assertEquals(HttpStatus.OK, response3.getStatusCode());

    }

    @Test
    public void getUserrByEmail() throws Exception {

        ResponseEntity<Userr> response1 = impl.getUserrByEmail("jackie@gmail.com");
        ResponseEntity<Userr> response2 = impl.getUserrByEmail("tom@hotmail.com");
        ResponseEntity<Userr> response3 = impl.getUserrByEmail("butters@gmail.com");

        Assert.assertEquals(admin1, response1.getBody());
        Assert.assertEquals(admin2, response2.getBody());
        Assert.assertEquals(nonAdmin1, response3.getBody());

        Assert.assertEquals(HttpStatus.OK, response1.getStatusCode());
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
        Assert.assertEquals(HttpStatus.OK, response3.getStatusCode());

    }

    @Test
    public void getAllUserrs() throws Exception {

        ResponseEntity<List<Userr>> response = impl.getAllUserrs();
        Assert.assertEquals(allUsers, response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void getUserrByName() throws Exception {

        ResponseEntity<List<Userr>> response1 = impl.getUserrByName("Jackie Fire");
        ResponseEntity<List<Userr>> response2 = impl.getUserrByName("Tom Doe");
        ResponseEntity<List<Userr>> response3 = impl.getUserrByName("Butters Stotch");

        Assert.assertEquals(justAdmin1, response1.getBody());
        Assert.assertEquals(justAdmin2, response2.getBody());
        Assert.assertEquals(justNonAdmin1, response3.getBody());

        Assert.assertEquals(HttpStatus.OK, response1.getStatusCode());
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
        Assert.assertEquals(HttpStatus.OK, response3.getStatusCode());

    }

    @Test
    public void updateUserrByID() throws Exception {

        Userr toUpdate = new Userr("tom@hotmail.com", "Tom Danny Doe", "haha", true);
        ResponseEntity<Userr> response = impl.updateUserrByID(2L, toUpdate);
        verify(userrRepo).updateUserrByID(2L, toUpdate);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void getItemsSoldByThisUser() throws Exception {

        ResponseEntity<List<Item>> response1 = impl.getItemsSoldByThisUser(1L);
        ResponseEntity<List<Item>> response2 = impl.getItemsSoldByThisUser(2L);
        ResponseEntity<List<Item>> response3 = impl.getItemsSoldByThisUser(3L);

        Assert.assertEquals(itemList1, response1.getBody());
        Assert.assertEquals(itemList2, response2.getBody());
        Assert.assertEquals(itemList3, response3.getBody());

        Assert.assertEquals(HttpStatus.OK, response1.getStatusCode());
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
        Assert.assertEquals(HttpStatus.OK, response3.getStatusCode());

    }

    @Test
    public void deleteUserrByID() throws Exception {

        ResponseEntity<Void> response = impl.deleteUserrByID(2L);
        verify(userrRepo).deleteUserrByID(2L);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}