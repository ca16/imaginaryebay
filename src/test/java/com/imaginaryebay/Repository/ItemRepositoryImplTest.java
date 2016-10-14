package com.imaginaryebay.Repository;

import com.imaginaryebay.Configuration.DatabaseConfiguration;
import com.imaginaryebay.Configuration.MailConfiguration;
import com.imaginaryebay.Configuration.ModelConfiguration;
import com.imaginaryebay.Configuration.SwaggerConfig;
import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.SendEmail;

import org.apache.http.protocol.HTTP;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 6/30/16.
 */

@PrepareForTest({Category.class})
public class ItemRepositoryImplTest {

    private static final String NOT_AVAILABLE = "Not available.";
    private static final String VALID_CATEGORIES = "Valid category names are: Clothes, Electronics, Books, Wine, Medicine, Kitchen, Food.";

    @Mock
    private ItemDAO itemDao;

    @Mock
    private UserrDao userrDao;

    @Mock
    private ItemDAO itemDaoEmpties;

    private ItemRepositoryImpl impl;
    private ItemRepositoryImpl implEmpties;

    private Item item1;
    private Item item2;
    private Item item3;
    private Item itemInvalidPrice;
    private Item itemInvalidCategory;
    private Item itemInvalidEndtime;
    private Item noFields;
    private Item notInDB;
    private Item toUpdate;

    private List<Item> all;
    private List<Item> clothes;
    private List<Item> electronics;
    private List<Item> empties;

    private Authentication auth1;

    private Userr userr1;
    private Userr userr2;
    private UserDetails ud1;

    private List<GrantedAuthority> authListAdmin;
    private List<GrantedAuthority> authListNonAdmin;
    
    @Mock
    private ApplicationContext ctx;
    @Mock
    private SendEmail smail;
    @Mock
    private Timer timer;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        item1 = new Item();
        item1.setCategory("Clothes");
        item1.setPrice(20.0);
        item1.setName("Scarf");
        item1.setDescription("Wintery");
        item1.setEndtime(valueOf("2016-10-10 00:00:00"));

        item2 = new Item();
        item2.setCategory("Clothes");
        item2.setPrice(200.0);
        item2.setName("Expensive Scarf");
        item2.setDescription("Summery");
        item2.setEndtime(valueOf("2016-11-5 06:00:00"));

        item3 = new Item();
        item3.setCategory("Electronics");
        item3.setPrice(30.0);
        item3.setName("Watch");
        item3.setDescription("Real Expensive");
        item3.setEndtime(valueOf("2016-9-2 11:10:10"));

        itemInvalidPrice = new Item();
        itemInvalidPrice.setCategory("Clothes");
        itemInvalidPrice.setPrice(-20.0);
        itemInvalidPrice.setName("Scarf");
        itemInvalidPrice.setDescription("Summery");
        itemInvalidPrice.setEndtime(valueOf("2016-10-10 00:00:00"));

        itemInvalidCategory = new Item();
        itemInvalidCategory.setCategory("Magic");
        itemInvalidCategory.setPrice(20.0);
        itemInvalidCategory.setName("Scarf");
        itemInvalidCategory.setDescription("Scarf");
        itemInvalidCategory.setEndtime(valueOf("2019-10-10 00:00:00"));

        itemInvalidEndtime = new Item();
        itemInvalidEndtime.setCategory("Clothes");
        itemInvalidEndtime.setPrice(20.0);
        itemInvalidEndtime.setName("Scarf");
        itemInvalidEndtime.setDescription("Scarf");
        itemInvalidEndtime.setEndtime(valueOf("2015-10-10 00:00:00"));

        authListAdmin = new ArrayList<>();
        authListAdmin.add(new SimpleGrantedAuthority("USER"));
        authListAdmin.add(new SimpleGrantedAuthority("ADMIN"));

        authListNonAdmin = new ArrayList<>();
        authListNonAdmin.add(new SimpleGrantedAuthority("USER"));

        userr1 = new Userr("jackie@gmail.com", "Jackie Fire", "dragon", true);
        userr2 = new Userr("tom@hotmail.com", "Tom Doe", "haha", true);
        ud1 = new User("jackie@gmail.com", "dragon", authListAdmin);

        item1.setUserr(userr2);
        item2.setUserr(userr1);

        auth1 = new UsernamePasswordAuthenticationToken(ud1, "dragon", authListAdmin);

        when(userrDao.getUserrByEmail("jackie@gmail.com")).thenReturn(userr1);

        noFields = new Item();
        notInDB = new Item();
        toUpdate = new Item();

        all = new ArrayList<>();
        clothes = new ArrayList<>();
        electronics = new ArrayList<>();
        empties = new ArrayList<>();

        all.add(item1);
        all.add(item2);
        all.add(item3);
        clothes.add(item1);
        clothes.add(item2);
        electronics.add(item3);

        when(itemDao.findByID(1L)).thenReturn(item1);
        when(itemDao.findPriceByID(1L)).thenReturn(20.0);
        when(itemDao.findCategoryByID(1L)).thenReturn(Category.Clothes);
        when(itemDao.findNameByID(1L)).thenReturn("Scarf");
        when(itemDao.findDescriptionByID(1L)).thenReturn("Wintery");
        when(itemDao.findEndtimeByID(1L)).thenReturn(valueOf("2016-10-10 00:00:00"));

        when(itemDao.findByID(2L)).thenReturn(item2);
        when(itemDao.findPriceByID(2L)).thenReturn(200.0);
        when(itemDao.findCategoryByID(2L)).thenReturn(Category.Clothes);
        when(itemDao.findNameByID(2L)).thenReturn("Expensive Scarf");
        when(itemDao.findDescriptionByID(2L)).thenReturn("Summery");
        when(itemDao.findEndtimeByID(2L)).thenReturn(valueOf("2016-11-5 06:00:00"));

        when(itemDao.findByID(10L)).thenReturn(item3);
        when(itemDao.findPriceByID(10L)).thenReturn(30.0);
        when(itemDao.findCategoryByID(10L)).thenReturn(Category.Electronics);
        when(itemDao.findNameByID(10L)).thenReturn("Watch");
        when(itemDao.findDescriptionByID(10L)).thenReturn("Real Expensive");
        when(itemDao.findEndtimeByID(10L)).thenReturn(valueOf("2016-9-2 11:10:10"));

        when(itemDao.findByID(24L)).thenReturn(noFields);
        when(itemDao.findByID(25L)).thenReturn(null);
        when(itemDao.findPriceByID(24L)).thenReturn(null);
        when(itemDao.findDescriptionByID(24L)).thenReturn(null);
        when(itemDao.findNameByID(24L)).thenReturn(null);
        when(itemDao.findCategoryByID(24L)).thenReturn(null);
        when(itemDao.findEndtimeByID(24L)).thenReturn(null);


        when(itemDao.find(notInDB)).thenReturn(null);
        when(itemDao.find(toUpdate)).thenReturn(item2);

        when(itemDao.findAllItems()).thenReturn(all);
        when(itemDao.findAllItemsByCategory(Category.Clothes)).thenReturn(clothes);
        when(itemDao.findAllItemsByCategory(Category.Electronics)).thenReturn(electronics);

        impl = new ItemRepositoryImpl();
        impl.setItemDAO(itemDao);
        impl.setUserrDAO(userrDao);

        when(itemDaoEmpties.findAllItems()).thenReturn(empties);
        when(itemDaoEmpties.findAllItemsByCategory(Category.Clothes)).thenReturn(empties);
        when(itemDaoEmpties.findAllItemsByCategory(Category.Electronics)).thenReturn(empties);

        implEmpties = new ItemRepositoryImpl();
        implEmpties.setItemDAO(itemDaoEmpties);
        
        when(ctx.getBean("sendEmail", SendEmail.class)).thenReturn(smail);


    }

    @Test
    public void save() throws Exception {
        Item toSave = new Item();
        toSave.setPrice(25.0);
        toSave.setName("This Perfect Day.");
        toSave.setEndtime(valueOf("2016-11-5 06:00:00"));

        SecurityContextHolder.getContext().setAuthentication(null);

        try {
            impl.save(toSave);
            fail();
        } catch (RestException exc){
            Assert.assertEquals("You must be logged in to create an item.", exc.getDetailedMessage());
        }

        
        SecurityContextHolder.getContext().setAuthentication(auth1);
        
        impl.setApplicationContext(ctx);
        impl.setTimer(timer);

        impl.save(toSave);
        verify(itemDao).persist(toSave);
        
        try {
            impl.save(itemInvalidPrice);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals("Invalid price", exc.getMessage());
            Assert.assertEquals("Price must be greater than 0.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

        try {
            impl.save(itemInvalidCategory);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals("Invalid category", exc.getMessage());
            Assert.assertEquals(VALID_CATEGORIES, exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

        try {
            impl.save(itemInvalidEndtime);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals("Invalid endtime", exc.getMessage());
            Assert.assertEquals("Auction must end in the future", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

    }

    @Test
    public void findByID() throws Exception {
        assertEquals(impl.findByID(1L), item1);
        assertEquals(impl.findByID(2L), item2);
        assertEquals(impl.findByID(10L), item3);

        try {
            impl.findByID(25L);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Item with id 25 was not found", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.OK);

        }
    }

    @Test
    public void update() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(auth1);
        impl.update(toUpdate);
        verify(itemDao).find(toUpdate);
        verify(itemDao).merge(toUpdate);

        try {
            impl.update(notInDB);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Item to be updated does not exist.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);

        }

    }

    @Test
    public void updateItemByID() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);

        try {
            impl.updateItemByID(1L, toUpdate);
            fail();
        } catch (RestException exc){
            Assert.assertEquals("You must be logged in to edit an item.", exc.getDetailedMessage());
        }

        SecurityContextHolder.getContext().setAuthentication(auth1);

        try {
            impl.updateItemByID(1L, toUpdate);
            fail();
        } catch (RestException exc){
            Assert.assertEquals("You can only update items you own.", exc.getDetailedMessage());
        }


        try {
            impl.updateItemByID(25L, toUpdate);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Item to be updated does not exist.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);

        }

        impl.updateItemByID(2L, toUpdate);
        verify(itemDao).findByID(2L);
        verify(itemDao).updateItemByID(2L, toUpdate);
    }

    @Test
    public void findAllItemsByCategory() throws Exception {
        assertEquals(impl.findAllItemsByCategory("Clothes"), clothes);
        assertEquals(impl.findAllItemsByCategory("Electronics"), electronics);

        try {
            implEmpties.findAllItemsByCategory("Clothes");
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Items of category Clothes were not found", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.OK);

        }
        try {
            implEmpties.findAllItemsByCategory("Electronics");
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Items of category Electronics were not found", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.OK);

        }
        try {
            impl.findAllItemsByCategory("Magic");
            fail();
        } catch(RestException exc){
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
            Assert.assertEquals("Invalid request parameter.", exc.getMessage());
            Assert.assertEquals("Magic is not a valid Category name. " + VALID_CATEGORIES, exc.getDetailedMessage());
        }
    }

    @Test
    public void findAllItems() throws Exception {
        assertEquals(impl.findAllItems(), all);

        try {
            implEmpties.findAllItems();
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("There are no items available.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.OK);

        }
    }

}