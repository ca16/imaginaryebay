package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Userr;
import com.sun.tools.classfile.Code_attribute;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 7/22/16.
 */
public class UserrRepositoryTest {

    @Mock
    private UserrDao userrDao;


    private UserrRepositoryImpl impl;

    private Userr userr1;
    private Userr userr2;
    private Userr newUser;

    private UserDetails user1;
    private List<GrantedAuthority> authListAce;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        userr1 = new Userr("a@amail.com", "Ace Aceson", "aaaa");
        userr2 = new Userr("b@bmail.com", "Bb Beeson", "bbbb");
        newUser = new Userr("new@email.com", "New User", "newnew");

        authListAce = new ArrayList<>();
        authListAce.add(new SimpleGrantedAuthority("USER"));
        authListAce.add(new SimpleGrantedAuthority("ADMIN"));

        UserDetails user1 = new User("a@amail.com", "aaaa", true, true, true, true, authListAce);

        when(userrDao.getUserrByID(1L)).thenReturn(userr1);
        when(userrDao.getUserrByID(2L)).thenReturn(userr2);

        when(userrDao.getUserrByEmail("a@amail.com")).thenReturn(userr1);
        when(userrDao.getUserrByEmail("b@bmail.com")).thenReturn(userr2);
        when(userrDao.getUserrByEmail("newemail@emails.com")).thenReturn(null);

        impl = new UserrRepositoryImpl();
        impl.setUserrDao(userrDao);

    }

    @Test
    public void createNewUserr() throws Exception {

        impl.createNewUserr(newUser);
        verify(userrDao).persist(newUser);

        try {
            impl.createNewUserr(userr1);
        }catch(RestException exc){
            Assert.assertEquals("User cannot be created.", exc.getMessage());
            Assert.assertEquals("User with email a@amail.com already exists.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

    }

    @Test(expected = RestException.class)
    public void testCreateExistingUser() throws Exception{
        impl.createNewUserr(userr1);

    }

    @Test
    public void getUserrByID() throws Exception {

        /*
        Assert.assertEquals(userr1, impl.getUserrByID(1L));
        Assert.assertEquals(userr2, impl.getUserrByID(2L));
        */

        Authentication auth = new UsernamePasswordAuthenticationToken(user1, "aaaa", authListAce);
        SecurityContextHolder.getContext().setAuthentication(auth);
        Assert.assertEquals(userr1, impl.getUserrByID(1L));


    }

    @Test
    public void getUserrByEmail() throws Exception {

        /*
        Assert.assertEquals(userr1, impl.getUserrByEmail("a@amail.com"));
        Assert.assertEquals(userr2, impl.getUserrByEmail("b@bmail.com"));
        */
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