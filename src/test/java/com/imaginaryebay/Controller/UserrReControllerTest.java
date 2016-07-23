package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.UserrRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 7/5/16.
 */
public class UserrReControllerTest {
    @Mock
    private UserrRepository userrRepo;

    private UserrController impl;

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

        impl = new UserrControllerImpl();
//        impl.setUserRepository(userrRepo);

    }

    @Test
    public void getUserrByID() throws Exception {

        Assert.assertEquals(userr1, impl.getUserrByID(1L));
        Assert.assertEquals(userr2, impl.getUserrByID(2L));
        Assert.assertEquals(userr3, impl.getUserrByID(3L));

    }

}