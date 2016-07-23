package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Userr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 7/22/16.
 */
public class UserrRepositoryTest {

    @Mock
    private UserrDao userrDao;


    private UserrRepositoryImpl impl;

    private Userr admin1;
    private Userr admin2;
    private Userr nonAdmin1;

    private UserDetails admin1UD;
    private UserDetails admin2UD;
    private UserDetails nonAdmin1UD;

    private List<GrantedAuthority> authListAdmin;
    private List<GrantedAuthority> authListNonAdmin;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        admin1 = new Userr("jackie@gmail.com", "Jackie Fire", "dragon", true);
        admin2 = new Userr("tom@hotmail.com", "Tom Doe", "haha", true);
        nonAdmin1 = new Userr("butters@gmail.com", "Butters Stotch", "Stotch");

        authListAdmin = new ArrayList<>();
        authListAdmin.add(new SimpleGrantedAuthority("USER"));
        authListAdmin.add(new SimpleGrantedAuthority("ADMIN"));

        authListNonAdmin = new ArrayList<>();
        authListNonAdmin.add(new SimpleGrantedAuthority("USER"));

        admin1UD = new User("jackie@gmail.com", "dragon", authListAdmin);
        admin2UD = new User("tom@hotmail.com", "haha", authListAdmin);
        nonAdmin1UD = new User("butters@gmail.com", "Stotch", authListNonAdmin);

        when(userrDao.getUserrByID(1L)).thenReturn(admin1);
        when(userrDao.getUserrByID(2L)).thenReturn(admin2);
        when(userrDao.getUserrByID(3L)).thenReturn(nonAdmin1);

        when(userrDao.getUserrByEmail("jackie@gmail.com")).thenReturn(admin1);
        when(userrDao.getUserrByEmail("tom@hotmail.com")).thenReturn(admin2);
        when(userrDao.getUserrByEmail("butters@gmail.com")).thenReturn(null);

        impl = new UserrRepositoryImpl();
        impl.setUserrDao(userrDao);

    }

    @Test
    public void createNewUserr() throws Exception {

        impl.createNewUserr(nonAdmin1);
        verify(userrDao).persist(nonAdmin1);

        try {
            impl.createNewUserr(admin1);
        }catch(RestException exc){
            Assert.assertEquals("User cannot be created.", exc.getMessage());
            Assert.assertEquals("User with email jackie@gmail.com already exists.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

    }

    @Test(expected = RestException.class)
    public void testCreateExistingUser() throws Exception{
        impl.createNewUserr(admin1);

    }

    @Test
    public void getUserrByID() throws Exception {

        Authentication authAdmin1 = new UsernamePasswordAuthenticationToken(admin1UD, "dragon", authListAdmin);
        Authentication authAdmin2 = new UsernamePasswordAuthenticationToken(admin2UD, "haha", authListAdmin);
        Authentication authNonAdmin1 = new UsernamePasswordAuthenticationToken(nonAdmin1UD, "Stotch", authListNonAdmin);

        SecurityContextHolder.getContext().setAuthentication(authAdmin1);
        Assert.assertEquals(admin1, impl.getUserrByID(1L));

        SecurityContextHolder.getContext().setAuthentication(authAdmin2);
        Assert.assertEquals(admin1, impl.getUserrByID(1L));

        SecurityContextHolder.getContext().setAuthentication(authNonAdmin1);
        try {
            Assert.assertEquals(admin1, impl.getUserrByID(1L));
        }catch (RestException exc){
            Assert.assertEquals("Not available.", exc.getMessage());
            Assert.assertEquals("You do not have the authority to view this user.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.FORBIDDEN);
        }

    }

    @Test(expected = RestException.class)
    public void testNonAdminAccessingOtherUser() throws Exception{
        Authentication authNonAdmin1 = new UsernamePasswordAuthenticationToken(nonAdmin1UD, "Stotch", authListNonAdmin);
        SecurityContextHolder.getContext().setAuthentication(authNonAdmin1);
        Assert.assertEquals(admin1, impl.getUserrByID(1L));
    }

    @Test
    public void getUserrByEmail() throws Exception {

        /*
        Assert.assertEquals(admin1, impl.getUserrByEmail("a@amail.com"));
        Assert.assertEquals(admin2, impl.getUserrByEmail("b@bmail.com"));
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