package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Item;
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

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 7/22/16.
 */
public class UserrRepositoryTest {

    private static final String NOT_AVAILABLE = "Not available.";

    @Mock
    private UserrDao userrDao;


    private UserrRepositoryImpl impl;

    private Userr admin1;
    private Userr admin2;
    private Userr nonAdmin1;
    private Userr butters2;

    private UserDetails admin1UD;
    private UserDetails admin2UD;
    private UserDetails nonAdmin1UD;
    private UserDetails nonAdminNonUserUD;

    private List<GrantedAuthority> authListAdmin;
    private List<GrantedAuthority> authListNonAdmin;

    private Authentication authAdmin1;
    private Authentication authAdmin2;
    private Authentication authNonAdmin1;

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

        authListAdmin = new ArrayList<>();
        authListAdmin.add(new SimpleGrantedAuthority("USER"));
        authListAdmin.add(new SimpleGrantedAuthority("ADMIN"));

        authListNonAdmin = new ArrayList<>();
        authListNonAdmin.add(new SimpleGrantedAuthority("USER"));

        admin1UD = new User("jackie@gmail.com", "dragon", authListAdmin);
        admin2UD = new User("tom@hotmail.com", "haha", authListAdmin);
        nonAdmin1UD = new User("butters@gmail.com", "Stotch", authListNonAdmin);

        authAdmin1 = new UsernamePasswordAuthenticationToken(admin1UD, "dragon", authListAdmin);
        authAdmin2 = new UsernamePasswordAuthenticationToken(admin2UD, "haha", authListAdmin);
        authNonAdmin1 = new UsernamePasswordAuthenticationToken(nonAdmin1UD, "Stotch", authListNonAdmin);

        when(userrDao.getUserrByID(1L)).thenReturn(admin1);
        when(userrDao.getUserrByID(2L)).thenReturn(admin2);
        when(userrDao.getUserrByID(3L)).thenReturn(nonAdmin1);
        when(userrDao.getUserrByID(4L)).thenReturn(null);

        when(userrDao.getUserrByEmail("jackie@gmail.com")).thenReturn(admin1);
        when(userrDao.getUserrByEmail("tom@hotmail.com")).thenReturn(admin2);
        when(userrDao.getUserrByEmail("butters@gmail.com")).thenReturn(nonAdmin1);
        when(userrDao.getUserrByEmail("other@gmail.com")).thenReturn(null);

        allUsers = new ArrayList<>();
        allUsers.add(admin1);
        allUsers.add(admin2);
        allUsers.add(nonAdmin1);

        when(userrDao.getAllUserrs()).thenReturn(allUsers);

        justAdmin1 = new ArrayList<>();
        justAdmin1.add(admin1);
        justAdmin2 = new ArrayList<>();
        justAdmin2.add(admin2);
        justNonAdmin1 = new ArrayList<>();
        justNonAdmin1.add(nonAdmin1);
        justNonAdmin1.add(butters2);
        just1Butters = new ArrayList<>();
        just1Butters.add(nonAdmin1);


        when(userrDao.getUserrByName("Jackie Fire")).thenReturn(justAdmin1);
        when(userrDao.getUserrByName("Tom Doe")).thenReturn(justAdmin2);
        when(userrDao.getUserrByName("Butters Stotch")).thenReturn(justNonAdmin1);
        when(userrDao.getUserrByName("Other Person")).thenReturn(null);

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

        when(userrDao.getItemsSoldByThisUser(1L)).thenReturn(itemList1);
        when(userrDao.getItemsSoldByThisUser(2L)).thenReturn(itemList2);
        when(userrDao.getItemsSoldByThisUser(3L)).thenReturn(itemList3);

        impl = new UserrRepositoryImpl();
        impl.setUserrDao(userrDao);

    }

    @Test
    public void createNewUserr() throws Exception {

        // pretending Butters hasn't registered yet
        when(userrDao.getUserrByEmail("butters@gmail.com")).thenReturn(null);

        impl.createNewUserr(nonAdmin1);
        verify(userrDao).persist(nonAdmin1);

        try {
            impl.createNewUserr(admin1);
            fail();
        }catch(RestException exc){
            Assert.assertEquals("User cannot be created.", exc.getMessage());
            Assert.assertEquals("User with email jackie@gmail.com already exists.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

    }

    @Test
    public void getUserrByID() throws Exception {

        // Access your own info - admin
        SecurityContextHolder.getContext().setAuthentication(authAdmin1);
        Assert.assertEquals(admin1, impl.getUserrByID(1L));

        // Access someone else's info - admin
        SecurityContextHolder.getContext().setAuthentication(authAdmin2);
        Assert.assertEquals(admin1, impl.getUserrByID(1L));

        // Access your own info - non admin
        SecurityContextHolder.getContext().setAuthentication(authNonAdmin1);
        Assert.assertEquals(nonAdmin1, impl.getUserrByID(3L));

        // Access someone else's info - non admin
        try {
            impl.getUserrByID(1L);
            fail();
        }catch (RestException exc){
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("You do not have the authority to view this user.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.FORBIDDEN);
        }

        // User doesn't exist
        try {
            impl.getUserrByID(4L);
            fail();
        }catch (RestException exc){
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("User with ID 4 does not exist.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.OK);
        }

    }

    @Test
    public void getUserrByEmail() throws Exception {

        // Access your own info - admin
        SecurityContextHolder.getContext().setAuthentication(authAdmin1);
        Assert.assertEquals(admin1, impl.getUserrByEmail("jackie@gmail.com"));

        // Access someone else's info - admin
        SecurityContextHolder.getContext().setAuthentication(authAdmin2);
        Assert.assertEquals(admin1, impl.getUserrByEmail("jackie@gmail.com"));

        // Access your own info - non admin
        SecurityContextHolder.getContext().setAuthentication(authNonAdmin1);
        Assert.assertEquals(nonAdmin1, impl.getUserrByEmail("butters@gmail.com"));

        // Access someone else's info - non admin
        try {
            impl.getUserrByEmail("jackie@gmail.com");
            fail();
        }catch (RestException exc){
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("You do not have the authority to view this user.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.FORBIDDEN);
        }

        // User doesn't exist
        SecurityContextHolder.getContext().setAuthentication(authAdmin2);
        try {
            impl.getUserrByEmail("other@gmail.com");
            fail();
        }catch (RestException exc){
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("User with email other@gmail.com does not exist.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.OK);
        }
    }

    @Test
    public void getAllUserrs() throws Exception {

        // Access all users - admin
        SecurityContextHolder.getContext().setAuthentication(authAdmin1);
        Assert.assertEquals(allUsers, impl.getAllUserrs());

        // Access all users - non admin
        SecurityContextHolder.getContext().setAuthentication(authNonAdmin1);
        try {
            impl.getAllUserrs();
            fail();
        }catch (RestException exc){
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("You do not have the authority to view all users.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.FORBIDDEN);
        }

    }

    @Test
    public void getUserrByName() throws Exception {

        // Access your own info - admin
        SecurityContextHolder.getContext().setAuthentication(authAdmin1);
        Assert.assertEquals(justAdmin1, impl.getUserrByName("Jackie Fire"));

        // Access someone else's info - admin
        SecurityContextHolder.getContext().setAuthentication(authAdmin2);
        Assert.assertEquals(justAdmin1, impl.getUserrByName("Jackie Fire"));

        // Access your own info - non admin
        // Multiple people with the same name, non admin gets 1
        SecurityContextHolder.getContext().setAuthentication(authNonAdmin1);
        Assert.assertEquals(just1Butters, impl.getUserrByName("Butters Stotch"));

        // Multiple people with the same name, admin gets all
        SecurityContextHolder.getContext().setAuthentication(authAdmin2);
        Assert.assertEquals(justNonAdmin1, impl.getUserrByName("Butters Stotch"));

        // Access someone else's info - non admin
        SecurityContextHolder.getContext().setAuthentication(authNonAdmin1);
        Assert.assertEquals(new ArrayList<Userr>(), impl.getUserrByName("Jackie Fire"));

    }

    @Test
    public void updateUserrByID() throws Exception {

        // Admin updates another user
        SecurityContextHolder.getContext().setAuthentication(authAdmin1);
        Userr adminUpdatesOther = new Userr("butters@gmail.com", "Butters B. Stotch", "Stotch");
        impl.updateUserrByID(3L, adminUpdatesOther);
        verify(userrDao).updateUserrByID(3L, adminUpdatesOther);

        // Admin updates self
        Userr adminUpdatesSelf = new Userr("jackie@gmail.com", "Jackie Fires", "dragon", true);
        impl.updateUserrByID(1L, adminUpdatesSelf);
        verify(userrDao).updateUserrByID(1L, adminUpdatesSelf);

        // Non-admin updates self
        SecurityContextHolder.getContext().setAuthentication(authNonAdmin1);
        Userr userUpdatesSelf = new Userr("butters@gmail.com", "Butters B. Stotch", "Stotch");
        impl.updateUserrByID(3L, userUpdatesSelf);
        verify(userrDao).updateUserrByID(3L, userUpdatesSelf);

        try {
            impl.updateUserrByID(1L, adminUpdatesOther);
            fail();
        }catch (RestException exc){
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("You do not have the authority to update this user.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.FORBIDDEN);
        }

        // Attempting to update an non-existent user
        SecurityContextHolder.getContext().setAuthentication(authAdmin2);
        try {
            impl.updateUserrByID(4L, adminUpdatesOther);
            fail();
        }catch (RestException exc){
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("User with ID 4 does not exist.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }


    }

    @Test
    public void getItemsSoldByThisUser() throws Exception {

        Assert.assertEquals(itemList1, impl.getItemsSoldByThisUser(1L));
        Assert.assertEquals(itemList2, impl.getItemsSoldByThisUser(2L));
        Assert.assertEquals(itemList3, impl.getItemsSoldByThisUser(3L));

        try {
            impl.getItemsSoldByThisUser(4L);
            fail();
        }catch (RestException exc){
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("User with ID 4 does not exist.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

    }

}