package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

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

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 6/30/16.
 */

// Stuff related to invlad category item and power mocking is commented out
    //because it doesn't work with junit version 4.12, but that's required for
    // Login test...
//@RunWith(PowerMockRunner.class)
@PrepareForTest({Category.class})
public class ItemRepositoryImplTest {

    private static final String NOT_AVAILABLE = "Not available.";

    @Mock
    private ItemDAO itemDao;

    @Mock
    private UserrDao userrDao;

    @Mock
    private ItemDAO itemDaoEmpties;

    private Category invalidCategory;


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
    private UserDetails ud1;

    private List<GrantedAuthority> authListAdmin;
    private List<GrantedAuthority> authListNonAdmin;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        //invalidCategory = PowerMockito.mock(Category.class);

        item1 = new Item();
        item1.setCategory("Clothes");
        item1.setPrice(20.0);
        item1.setName("Scarf");
        item1.setDescription("Scarf");
        item1.setEndtime(valueOf("2016-10-10 00:00:00"));

        item2 = new Item();
        item2.setCategory("Clothes");
        item2.setPrice(200.0);
        item2.setName("Expensive Scarf");
        item2.setDescription("Expensive Scarf");
        item2.setEndtime(valueOf("2016-11-5 06:00:00"));

        item3 = new Item();
        item3.setCategory("Electronics");
        item3.setPrice(30.0);
        item3.setName("Watch");
        item3.setDescription("Watch");
        item3.setEndtime(valueOf("2016-9-2 11:10:10"));

        itemInvalidPrice = new Item();
        itemInvalidPrice.setCategory("Clothes");
        itemInvalidPrice.setPrice(-20.0);
        itemInvalidPrice.setName("Scarf");
        itemInvalidPrice.setDescription("Scarf");
        itemInvalidPrice.setEndtime(valueOf("2016-10-10 00:00:00"));

        itemInvalidCategory = new Item();
        itemInvalidCategory.setCategory("Books");
        itemInvalidCategory.setPrice(20.0);
        itemInvalidCategory.setName("Scarf");
        itemInvalidCategory.setDescription("Scarf");
        itemInvalidCategory.setEndtime(valueOf("2016-10-10 00:00:00"));

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
        ud1 = new User("jackie@gmail.com", "dragon", authListAdmin);

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
        when(itemDao.findDescriptionByID(1L)).thenReturn("Scarf");
        when(itemDao.findEndtimeByID(1L)).thenReturn(valueOf("2016-10-10 00:00:00"));

        when(itemDao.findByID(2L)).thenReturn(item2);
        when(itemDao.findPriceByID(2L)).thenReturn(200.0);
        when(itemDao.findCategoryByID(2L)).thenReturn(Category.Clothes);
        when(itemDao.findDescriptionByID(2L)).thenReturn("Expensive Scarf");
        when(itemDao.findEndtimeByID(2L)).thenReturn(valueOf("2016-11-5 06:00:00"));

        when(itemDao.findByID(10L)).thenReturn(item3);
        when(itemDao.findPriceByID(10L)).thenReturn(30.0);
        when(itemDao.findCategoryByID(10L)).thenReturn(Category.Electronics);
        when(itemDao.findDescriptionByID(10L)).thenReturn("Watch");
        when(itemDao.findEndtimeByID(10L)).thenReturn(valueOf("2016-9-2 11:10:10"));

        when(itemDao.findByID(24L)).thenReturn(noFields);
        when(itemDao.findByID(25L)).thenReturn(null);
        when(itemDao.findPriceByID(24L)).thenReturn(null);
        when(itemDao.findDescriptionByID(24L)).thenReturn(null);
        when(itemDao.findCategoryByID(24L)).thenReturn(null);
        when(itemDao.findEndtimeByID(24L)).thenReturn(null);


        when(itemDao.find(notInDB)).thenReturn(null);
        when(itemDao.find(toUpdate)).thenReturn(item2);

        when(itemDao.findAllItems()).thenReturn(all);
        when(itemDao.findAllItemsByCategory(Category.Clothes)).thenReturn(clothes);
        when(itemDao.findAllItemsByCategory(Category.Electronics)).thenReturn(electronics);

//        when(invalidCategory.toString()).thenReturn("Books");

        impl = new ItemRepositoryImpl();
        impl.setItemDAO(itemDao);
        impl.setUserrDAO(userrDao);

        when(itemDaoEmpties.findAllItems()).thenReturn(empties);
        when(itemDaoEmpties.findAllItemsByCategory(Category.Clothes)).thenReturn(empties);
        when(itemDaoEmpties.findAllItemsByCategory(Category.Electronics)).thenReturn(empties);

        implEmpties = new ItemRepositoryImpl();
        implEmpties.setItemDAO(itemDaoEmpties);




    }

    @Test
    public void save() throws Exception {
        Item toSave = new Item();
        toSave.setPrice(25.0);
        toSave.setName("This Perfect Day.");
        toSave.setEndtime(valueOf("2016-11-5 06:00:00"));
        SecurityContextHolder.getContext().setAuthentication(auth1);

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
/*
        try {
            impl.save(itemInvalidCategory);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals("Invalid category", exc.getMessage());
            Assert.assertEquals("Books is not a valid category name", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
*/
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

        //Doing two tests for impl.findByID(25L) to make sure that:
        //1. An exception is thrown
        //2. It has the expected message
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
    public void findPriceByID() throws Exception {
        assertEquals(impl.findPriceByID(1L), new Double(20.0));
        assertEquals(impl.findPriceByID(2L), new Double(200.0));
        assertEquals(impl.findPriceByID(10L), new Double(30.0));

        try {
            impl.findPriceByID(25L);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Item with id 25 was not found", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);

        }
    }

    @Test
    public void findCategoryByID() throws Exception {
        assertEquals(impl.findCategoryByID(1L), Category.Clothes);
        assertEquals(impl.findCategoryByID(2L), Category.Clothes);
        assertEquals(impl.findCategoryByID(10L), Category.Electronics);

        try {
            impl.findCategoryByID(24L);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Item with id 24 does not have a category", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.OK);

        }

        try {
            impl.findCategoryByID(25L);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Item with id 25 was not found", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);

        }
    }

    @Test
    public void findEndtimeByID() throws Exception {
        assertEquals(impl.findEndtimeByID(1L), valueOf("2016-10-10 00:00:00"));
        assertEquals(impl.findEndtimeByID(2L), valueOf("2016-11-5 06:00:00"));
        assertEquals(impl.findEndtimeByID(10L), valueOf("2016-9-2 11:10:10"));

        try {
            impl.findEndtimeByID(25L);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Item with id 25 was not found", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);

        }
    }

    @Test
    public void findDescriptionByID() throws Exception {
        assertEquals(impl.findDescriptionByID(1L), "Scarf");
        assertEquals(impl.findDescriptionByID(2L), "Expensive Scarf");
        assertEquals(impl.findDescriptionByID(10L), "Watch");

        try {
            impl.findDescriptionByID(24L);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Item with id 24 does not have a description", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.OK);

        }

        try {
            impl.findDescriptionByID(25L);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Item with id 25 was not found", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);

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
        SecurityContextHolder.getContext().setAuthentication(auth1);
        impl.updateItemByID(2L, toUpdate);
        verify(itemDao).findByID(2L);
        verify(itemDao).updateItemByID(2L, toUpdate);

        try {
            impl.updateItemByID(25L, toUpdate);
            fail();
        } catch (RestException exc) {
            Assert.assertEquals(NOT_AVAILABLE, exc.getMessage());
            Assert.assertEquals("Item to be updated does not exist.", exc.getDetailedMessage());
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);

        }
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
            impl.findAllItemsByCategory("Books");
            fail();
        } catch(RestException exc){
            Assert.assertEquals(exc.getStatusCode(), HttpStatus.BAD_REQUEST);
            Assert.assertEquals("Invalid request parameter.", exc.getMessage());
            Assert.assertEquals("Books is not a valid Category name", exc.getDetailedMessage());
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
/*
    @Test
    public void findAllItemPicturesForItem() throws Exception {

    }*/
//=======
//package com.imaginaryebay.Repository;
//
//import com.imaginaryebay.Controller.RestException;
//import com.imaginaryebay.DAO.ItemDAO;
//import com.imaginaryebay.DAO.ItemDAOImpl;
//import com.imaginaryebay.Models.Category;
//import com.imaginaryebay.Models.Item;
//import com.imaginaryebay.Models.ItemPicture;
//
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static java.sql.Timestamp.valueOf;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.when;
//
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.mockito.stubbing.Answer;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.*;
//
///**
// * Created by Chloe on 6/30/16.
// */
//public class ItemRepositoryImplTest {
//
//    @Mock
//    private ItemDAO itemDao;
//
//    private ItemRepositoryImpl impl;
//    private Item item1;
//    private Item item2;
//    private Item item3;
//    private Item noFields;
//    private Item notInDB;
//    private Item toUpdate;
//
//    private ItemPicture itempic1;
//    private ItemPicture itempic2;
//    private ItemPicture itempic3;
//    private ItemPicture itempic4;
//    private ItemPicture itempic5;
//    private ItemPicture itempic6;
//    private ItemPicture itempic7;
//    private ItemPicture itempic8;
//    private ItemPicture itempic9;
//    private ItemPicture itempic10;
//    private ItemPicture itempic11;
//    private ItemPicture itempic12;
//
//    private List<Item> all;
//    private List<Item> clothes;
//    private List<Item> electronics;
//
//    private List<ItemPicture> item1pics;
//    private List<ItemPicture> item2pics;
//    private List<ItemPicture> item3pics;
//    private List<ItemPicture> item1picurls;
//    private List<ItemPicture> item2picurls;
//    private List<ItemPicture> item3picurls;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        item1 = new Item();
//        item1.setCategory(Category.Clothes);
//        item1.setPrice(20.0);
//        item1.setDescription("Scarf");
//        item1.setEndtime(valueOf("2016-10-10 00:00:00"));
//
//        item2 = new Item();
//        item2.setCategory(Category.Clothes);
//        item2.setPrice(200.0);
//        item2.setDescription("Expensive Scarf");
//        item2.setEndtime(valueOf("2016-11-5 06:00:00"));
//
//        item3 = new Item();
//        item3.setCategory(Category.Electronics);
//        item3.setPrice(30.0);
//        item3.setDescription("Watch");
//        item3.setEndtime(valueOf("2016-9-2 11:10:10"));
//
//        noFields = new Item();
//        notInDB = new Item();
//
//        itempic1 = new ItemPicture();
//        itempic1.setAuction_item(item1);
//        itempic1.setUrl("http://scarfheroes.wikia.com/wiki/File:Royal-stewart-tartan-lambswool-scarf.jpg");
//        itempic2 =new ItemPicture();
//        itempic2.setAuction_item(item1);
//        itempic2.setUrl("http://www.shopazil.com/products/solid-silk-linen-scarf");
//        itempic3 = new ItemPicture();
//        itempic3.setAuction_item(item2);
//        itempic3.setUrl("http://99fungames.com/knot-your-scarf-game/");
//        itempic4=new ItemPicture();
//        itempic4.setAuction_item(item2);
//        itempic4.setUrl("http://www.overstock.com/Clothing-Shoes/Burberry-Plaid-Camel-Cashmere-Scarf/5400771/product.html");
//        itempic5=new ItemPicture();
//        itempic5.setAuction_item(item3);
//        itempic5.setUrl("http://www.shinola.com/shop/therunwell47-leather-watch-s0110.html");
//        itempic6 = new ItemPicture();
//        itempic6.setAuction_item(item3);
//        itempic6.setUrl("http://6iee.com/790839.html");
//        itempic7 = new ItemPicture();
//        itempic7.setUrl("http://scarfheroes.wikia.com/wiki/File:Royal-stewart-tartan-lambswool-scarf.jpg");
//        itempic8 =new ItemPicture();
//        itempic8.setUrl("http://www.shopazil.com/products/solid-silk-linen-scarf");
//        itempic9 = new ItemPicture();
//        itempic9.setUrl("http://99fungames.com/knot-your-scarf-game/");
//        itempic10=new ItemPicture();
//        itempic10.setUrl("http://www.overstock.com/Clothing-Shoes/Burberry-Plaid-Camel-Cashmere-Scarf/5400771/product.html");
//        itempic11=new ItemPicture();
//        itempic11.setUrl("http://www.shinola.com/shop/therunwell47-leather-watch-s0110.html");
//        itempic12 = new ItemPicture();
//        itempic12.setUrl("http://6iee.com/790839.html");
//
//        all = new ArrayList<>();
//        clothes = new ArrayList<>();
//        electronics = new ArrayList<>();
//
//        item1pics = new ArrayList<>();
//        item2pics = new ArrayList<>();
//        item3pics = new ArrayList<>();
//        item1picurls=new ArrayList<>();
//        item2picurls=new ArrayList<>();
//        item3picurls=new ArrayList<>();
//
//        all.add(item1);
//        all.add(item2);
//        all.add(item3);
//        clothes.add(item1);
//        clothes.add(item2);
//        electronics.add(item3);
//
//        item1pics.add(itempic1);
//        item1pics.add(itempic2);
//        item2pics.add(itempic3);
//        item2pics.add(itempic4);
//        item3pics.add(itempic5);
//        item3pics.add(itempic6);
//        item1picurls.add(itempic7);
//        item1picurls.add(itempic8);
//        item2picurls.add(itempic9);
//        item2picurls.add(itempic10);
//        item3picurls.add(itempic11);
//        item3picurls.add(itempic12);
//
//        when(itemDao.findByID(1L)).thenReturn(item1);
//        when(itemDao.findPriceByID(1L)).thenReturn(20.0);
//        when(itemDao.findCategoryByID(1L)).thenReturn(Category.Clothes);
//        when(itemDao.findDescriptionByID(1L)).thenReturn("Scarf");
//        when(itemDao.findEndtimeByID(1L)).thenReturn(valueOf("2016-10-10 00:00:00"));
//
//        when(itemDao.findByID(2L)).thenReturn(item2);
//        when(itemDao.findPriceByID(2L)).thenReturn(200.0);
//        when(itemDao.findCategoryByID(2L)).thenReturn(Category.Clothes);
//        when(itemDao.findDescriptionByID(2L)).thenReturn("Expensive Scarf");
//        when(itemDao.findEndtimeByID(2L)).thenReturn(valueOf("2016-11-5 06:00:00"));
//
//        when(itemDao.findByID(10L)).thenReturn(item3);
//        when(itemDao.findPriceByID(10L)).thenReturn(30.0);
//        when(itemDao.findCategoryByID(10L)).thenReturn(Category.Electronics);
//        when(itemDao.findDescriptionByID(10L)).thenReturn("Watch");
//        when(itemDao.findEndtimeByID(10L)).thenReturn(valueOf("2016-9-2 11:10:10"));
//
//        when(itemDao.findByID(24L)).thenReturn(noFields);
//        when(itemDao.findByID(25L)).thenReturn(null);
//        when(itemDao.findPriceByID(24L)).thenReturn(null);
//        when(itemDao.findDescriptionByID(24L)).thenReturn(null);
//        when(itemDao.findCategoryByID(24L)).thenReturn(null);
//        when(itemDao.findEndtimeByID(24L)).thenReturn(null);
//
//
//        when(itemDao.find(notInDB)).thenReturn(null);
//        when(itemDao.find(toUpdate)).thenReturn(item2);
//
//        when(itemDao.findAllItems()).thenReturn(all);
//        when(itemDao.findAllItemsByCategory(Category.Clothes)).thenReturn(clothes);
//        when(itemDao.findAllItemsByCategory(Category.Electronics)).thenReturn(electronics);
//
//        when(itemDao.findAllItemPicturesForItemID(1L)).thenReturn(item1pics);
//        when(itemDao.findAllItemPicturesForItemID(2L)).thenReturn(item2pics);
//        when(itemDao.findAllItemPicturesForItemID(10L)).thenReturn(new ArrayList<ItemPicture>());
//        when(itemDao.findAllItemPictureURLsForItemID(1L)).thenReturn(item1picurls);
//        when(itemDao.findAllItemPictureURLsForItemID(2L)).thenReturn(item2picurls);
//        when(itemDao.findAllItemPictureURLsForItemID(10L)).thenReturn(new ArrayList<ItemPicture>());
//
//        impl = new ItemRepositoryImpl();
//        impl.setItemDAO(itemDao);
//
//    }
//
//    @Test
//    public void save() throws Exception {
//        Item toSave = new Item();
//        impl.save(toSave);
//        verify(itemDao).persist(toSave);
//    }
//
//    @Test
//    public void findByID() throws Exception {
//        assertEquals(impl.findByID(1L), item1);
//        assertEquals(impl.findByID(2L), item2);
//        assertEquals(impl.findByID(10L), item3);
//
//        // Case where item does not exist
//        //Eventually will be testing an exception here
//        assertEquals(impl.findByID(25L), null);
//
//
//    }
//
//    @Test
//    public void findPriceByID() throws Exception {
//        assertEquals(impl.findPriceByID(1L), new Double(20.0));
//        assertEquals(impl.findPriceByID(2L), new Double(200.0));
//        assertEquals(impl.findPriceByID(10L), new Double(30.0));
//
//        // Case where item exists but has no price
//        //Eventually will be testing an exception here
//        assertEquals(impl.findPriceByID(24L), null);
//
//        // Case where item does not exist
//        //Eventually will be testing an exception here
//        assertEquals(impl.findPriceByID(25L), null);
//
//    }
//
//    @Test
//    public void findCategoryByID() throws Exception {
//        assertEquals(impl.findCategoryByID(1L), Category.Clothes);
//        assertEquals(impl.findCategoryByID(2L), Category.Clothes);
//        assertEquals(impl.findCategoryByID(10L), Category.Electronics);
//
//        // Case where item exists but has no category
//        //Eventually will be testing an exception here
//        assertEquals(impl.findCategoryByID(24L), null);
//
//        // Case where item does not exist
//        //Eventually will be testing an exception here
//        assertEquals(impl.findCategoryByID(25L), null);
//
//        //Should be able to differentiate between the two cases with exception messages
//    }
//
//    @Test
//    public void findEndtimeByID() throws Exception {
//        assertEquals(impl.findEndtimeByID(1L), valueOf("2016-10-10 00:00:00"));
//        assertEquals(impl.findEndtimeByID(2L), valueOf("2016-11-5 06:00:00"));
//        assertEquals(impl.findEndtimeByID(10L), valueOf("2016-9-2 11:10:10"));
//
//        // Case where item exists but has no endtime
//        //Eventually will be testing an exception here
//        assertEquals(impl.findEndtimeByID(24L), null);
//
//        // Case where item does not exist
//        //Eventually will be testing an exception here
//        assertEquals(impl.findEndtimeByID(25L), null);
//
//        //Should be able to differentiate between the two cases with exception messages
//
//    }
//
//    @Test
//    public void findDescriptionByID() throws Exception {
//        assertEquals(impl.findDescriptionByID(1L), "Scarf");
//        assertEquals(impl.findDescriptionByID(2L), "Expensive Scarf");
//        assertEquals(impl.findDescriptionByID(10L), "Watch");
//
//        // Case where item exists but has no description
//        //Eventually will be testing an exception here
//        assertEquals(impl.findDescriptionByID(24L), null);
//
//        // Case where item does not exist
//        //Eventually will be testing an exception here
//        assertEquals(impl.findDescriptionByID(25L), null);
//
//        //Should be able to differentiate between the two cases with exception messages
//
//    }
//
//
//    @Test
//    public void update() throws Exception {
//        impl.update(toUpdate);
//        verify(itemDao).find(toUpdate);
//        verify(itemDao).merge(toUpdate);
//
//        // Case where item does not exist
//        //Eventually will be testing an exception here
//        impl.update(notInDB);
//
//    }
//
//    @Test
//    public void updateItemByID() throws Exception {
//        impl.updateItemByID(2L, toUpdate);
//        verify(itemDao).findByID(2L);
//        verify(itemDao).updateItemByID(2L, toUpdate);
//
//        // Case where item does not exist
//        //Eventually will be testing an exception here
//        assertEquals(impl.updateItemByID(25L, toUpdate), null);
//    }
//
//    @Test
//    public void findAllItemsByCategory() throws Exception {
//        assertEquals(impl.findAllItemsByCategory(Category.Clothes), clothes);
//        assertEquals(impl.findAllItemsByCategory(Category.Electronics), electronics);
//    }
//
//    @Test
//    public void findAllItems() throws Exception {
//        assertEquals(impl.findAllItems(), all);
//    }
//
//    @Test
//    public void findAllItemPicturesForItem() throws Exception {
//    	assertEquals(impl.findAllItemPicturesForItem(1L,"false"),item1pics);
//    	assertEquals(impl.findAllItemPicturesForItem(1L,"true"),item1picurls);
//    	assertEquals(impl.findAllItemPicturesForItem(2L,"false"),item2pics);
//    	assertEquals(impl.findAllItemPicturesForItem(2L,"true"),item2picurls);
//    }
//    @Test(expected=RestException.class)
//    public void testReturnItemPicsWithException(){
//    	impl.findAllItemPicturesForItem(10L,"false");
//    	impl.findAllItemPicturesForItem(10L,"true");
//    	impl.findAllItemPicturesForItem(1L,"dsgsfg");
//    }
//
//}
//>>>>>>> origin/tv_sprintbranch
