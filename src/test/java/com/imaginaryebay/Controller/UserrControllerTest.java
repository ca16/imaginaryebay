package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.UserrRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 7/22/16.
 */
public class UserrControllerTest {
    @Mock
    private UserrRepository userrRepo;

    private UserrControllerImpl impl;

    private Userr userr1;
    private Userr userr2;
    private Userr userr3;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        userr1 = new Userr("a@amail.com", "Ace Aceson", "aaaa");
        userr2 = new Userr("b@bmail.com", "Bb Beeson", "bbbb");
        userr3 = new Userr("a2@amail.com", "A2 A2man", "a2a2");

        when(userrRepo.getUserrByID(1L)).thenReturn(userr1);
        when(userrRepo.getUserrByID(2L)).thenReturn(userr2);
        when(userrRepo.getUserrByID(3L)).thenReturn(userr3);

        when(userrRepo.getUserrByEmail("a@amail.com")).thenReturn(userr1);
        when(userrRepo.getUserrByEmail("b@bmail.com")).thenReturn(userr2);
        when(userrRepo.getUserrByEmail("a2@amail.com")).thenReturn(userr3);

        impl = new UserrControllerImpl();
        impl.setUserrRepository(userrRepo);

    }

    @Test
    public void createNewUserr() throws Exception {


    }

    @Test
    public void getUserrByID() throws Exception {

        Assert.assertEquals(userr1, impl.getUserrByID(1L).getBody());
        Assert.assertEquals(userr2, impl.getUserrByID(2L).getBody());
        Assert.assertEquals(userr3, impl.getUserrByID(3L).getBody());

        Assert.assertEquals(HttpStatus.OK, impl.getUserrByID(1L).getStatusCode());
        Assert.assertEquals(HttpStatus.OK, impl.getUserrByID(2L).getStatusCode());
        Assert.assertEquals(HttpStatus.OK, impl.getUserrByID(3L).getStatusCode());

    }

    @Test
    public void getUserrByEmail() throws Exception {

        Assert.assertEquals(userr1, impl.getUserrByEmail("a@amail.com").getBody());
        Assert.assertEquals(userr2, impl.getUserrByEmail("b@bmail.com").getBody());
        Assert.assertEquals(userr3, impl.getUserrByEmail("a2@amail.com").getBody());

        Assert.assertEquals(HttpStatus.OK, impl.getUserrByEmail("a@amail.com").getStatusCode());
        Assert.assertEquals(HttpStatus.OK, impl.getUserrByEmail("b@bmail.com").getStatusCode());
        Assert.assertEquals(HttpStatus.OK, impl.getUserrByEmail("a2@amail.com").getStatusCode());

    }

    @Test
    public void getAllUserrs() throws Exception {

    }

    @Test
    public void getUserrByName() throws Exception {

    }

    @Test
    public void updateUserrByID() throws Exception {

    }

    @Test
    public void getItemsSoldByThisUser() throws Exception {

    }

    @Test
    public void deleteUserrByID() throws Exception {

    }

}