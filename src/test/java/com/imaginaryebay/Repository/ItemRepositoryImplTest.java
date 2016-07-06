package com.imaginaryebay.Repository;

import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.DAO.ItemDAOImpl;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Created by Chloe on 6/30/16.
 */
public class ItemRepositoryImplTest {

    @Mock
    private ItemDAO itemDao;

    private ItemRepositoryImpl impl;
    private Item item1;
    private Item item2;
    private Item item3;
    private Item noFields;
    private Item notInDB;
    private Item toUpdate;
    
    private ItemPicture itempic1;
    private ItemPicture itempic2;
    private ItemPicture itempic3;
    private ItemPicture itempic4;
    private ItemPicture itempic5;
    private ItemPicture itempic6;

    private List<Item> all;
    private List<Item> clothes;
    private List<Item> electronics;
    
    private List<ItemPicture> item1pics;
    private List<ItemPicture> item2pics;
    private List<ItemPicture> item3pics;
    
    private ResponseEntity<List<ItemPicture>> item1picresponse;
    private ResponseEntity<List<ItemPicture>> item2picresponse;
    private ResponseEntity<List<ItemPicture>> item3picresponse;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        item1 = new Item();
        item1.setCategory(Category.Clothes);
        item1.setPrice(20.0);
        item1.setDescription("Scarf");
        item1.setEndtime(valueOf("2016-10-10 00:00:00"));

        item2 = new Item();
        item2.setCategory(Category.Clothes);
        item2.setPrice(200.0);
        item2.setDescription("Expensive Scarf");
        item2.setEndtime(valueOf("2016-11-5 06:00:00"));

        item3 = new Item();
        item3.setCategory(Category.Electronics);
        item3.setPrice(30.0);
        item3.setDescription("Watch");
        item3.setEndtime(valueOf("2016-9-2 11:10:10"));

        noFields = new Item();
        notInDB = new Item();
        
        itempic1 = new ItemPicture();
        itempic1.setAuction_item(item1);
        itempic1.setUrl("http://scarfheroes.wikia.com/wiki/File:Royal-stewart-tartan-lambswool-scarf.jpg");
        itempic2 =new ItemPicture();
        itempic2.setAuction_item(item1);
        itempic2.setUrl("http://www.shopazil.com/products/solid-silk-linen-scarf");
        itempic3 = new ItemPicture();
        itempic3.setAuction_item(item2);
        itempic3.setUrl("http://99fungames.com/knot-your-scarf-game/");
        itempic4=new ItemPicture();
        itempic4.setAuction_item(item2);
        itempic4.setUrl("http://www.overstock.com/Clothing-Shoes/Burberry-Plaid-Camel-Cashmere-Scarf/5400771/product.html");
        itempic5=new ItemPicture();
        itempic5.setAuction_item(item3);
        itempic5.setUrl("http://www.shinola.com/shop/therunwell47-leather-watch-s0110.html");
        itempic6 = new ItemPicture();
        itempic6.setAuction_item(item3);
        itempic6.setUrl("http://6iee.com/790839.html");

        all = new ArrayList<>();
        clothes = new ArrayList<>();
        electronics = new ArrayList<>();
        
        item1pics = new ArrayList<>();
        item2pics = new ArrayList<>();
        item3pics = new ArrayList<>();

        all.add(item1);
        all.add(item2);
        all.add(item3);
        clothes.add(item1);
        clothes.add(item2);
        electronics.add(item3);
        
        item1pics.add(itempic1);
        item1pics.add(itempic2);
        item2pics.add(itempic3);
        item2pics.add(itempic4);
        item3pics.add(itempic5);
        item3pics.add(itempic6);
        
        item1picresponse=new ResponseEntity<>(item1pics,HttpStatus.OK);
        item2picresponse=new ResponseEntity<>(item2pics,HttpStatus.OK);
        item3picresponse=new ResponseEntity<>(item3pics,HttpStatus.OK);

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
        
        when(itemDao.returnAllItemPicturesForItemID(1L)).thenReturn(item1pics);
        when(itemDao.returnAllItemPicturesForItemID(2L)).thenReturn(item2pics);
        when(itemDao.returnAllItemPicturesForItemID(10L)).thenReturn(new ArrayList<ItemPicture>());

        impl = new ItemRepositoryImpl();
        impl.setItemDAO(itemDao);

    }

    @Test
    public void save() throws Exception {
        Item toSave = new Item();
        impl.save(toSave);
        verify(itemDao).persist(toSave);
    }

    @Test
    public void findByID() throws Exception {
        assertEquals(impl.findByID(1L), item1);
        assertEquals(impl.findByID(2L), item2);
        assertEquals(impl.findByID(10L), item3);

        // Case where item does not exist
        //Eventually will be testing an exception here
        assertEquals(impl.findByID(25L), null);


    }

    @Test
    public void findPriceByID() throws Exception {
        assertEquals(impl.findPriceByID(1L), new Double(20.0));
        assertEquals(impl.findPriceByID(2L), new Double(200.0));
        assertEquals(impl.findPriceByID(10L), new Double(30.0));

        // Case where item exists but has no price
        //Eventually will be testing an exception here
        assertEquals(impl.findPriceByID(24L), null);

        // Case where item does not exist
        //Eventually will be testing an exception here
        assertEquals(impl.findPriceByID(25L), null);

    }

    @Test
    public void findCategoryByID() throws Exception {
        assertEquals(impl.findCategoryByID(1L), Category.Clothes);
        assertEquals(impl.findCategoryByID(2L), Category.Clothes);
        assertEquals(impl.findCategoryByID(10L), Category.Electronics);

        // Case where item exists but has no category
        //Eventually will be testing an exception here
        assertEquals(impl.findCategoryByID(24L), null);

        // Case where item does not exist
        //Eventually will be testing an exception here
        assertEquals(impl.findCategoryByID(25L), null);

        //Should be able to differentiate between the two cases with exception messages
    }

    @Test
    public void findEndtimeByID() throws Exception {
        assertEquals(impl.findEndtimeByID(1L), valueOf("2016-10-10 00:00:00"));
        assertEquals(impl.findEndtimeByID(2L), valueOf("2016-11-5 06:00:00"));
        assertEquals(impl.findEndtimeByID(10L), valueOf("2016-9-2 11:10:10"));

        // Case where item exists but has no endtime
        //Eventually will be testing an exception here
        assertEquals(impl.findEndtimeByID(24L), null);

        // Case where item does not exist
        //Eventually will be testing an exception here
        assertEquals(impl.findEndtimeByID(25L), null);

        //Should be able to differentiate between the two cases with exception messages

    }

    @Test
    public void findDescriptionByID() throws Exception {
        assertEquals(impl.findDescriptionByID(1L), "Scarf");
        assertEquals(impl.findDescriptionByID(2L), "Expensive Scarf");
        assertEquals(impl.findDescriptionByID(10L), "Watch");

        // Case where item exists but has no description
        //Eventually will be testing an exception here
        assertEquals(impl.findDescriptionByID(24L), null);

        // Case where item does not exist
        //Eventually will be testing an exception here
        assertEquals(impl.findDescriptionByID(25L), null);

        //Should be able to differentiate between the two cases with exception messages

    }


    @Test
    public void update() throws Exception {
        impl.update(toUpdate);
        verify(itemDao).find(toUpdate);
        verify(itemDao).merge(toUpdate);

        // Case where item does not exist
        //Eventually will be testing an exception here
        impl.update(notInDB);

    }

    @Test
    public void updateItemByID() throws Exception {
        impl.updateItemByID(2L, toUpdate);
        verify(itemDao).findByID(2L);
        verify(itemDao).updateItemByID(2L, toUpdate);

        // Case where item does not exist
        //Eventually will be testing an exception here
        assertEquals(impl.updateItemByID(25L, toUpdate), null);
    }

    @Test
    public void findAllItemsByCategory() throws Exception {
        assertEquals(impl.findAllItemsByCategory(Category.Clothes), clothes);
        assertEquals(impl.findAllItemsByCategory(Category.Electronics), electronics);
    }

    @Test
    public void findAllItems() throws Exception {
        assertEquals(impl.findAllItems(), all);
    }

    @Test
    public void returnItemPicturesForItem() throws Exception {
    	assertEquals(impl.returnItemPicturesForItem(1L,"false"),item1picresponse);
    	assertEquals(impl.returnItemPicturesForItem(2L,"false"),item2picresponse);
    	assertEquals(impl.returnItemPicturesForItem(10L,"false"),new ResponseEntity<List<ItemPicture>>(HttpStatus.BAD_REQUEST));
    	assertEquals(impl.returnItemPicturesForItem(1L,"dsgsfg"),new ResponseEntity<List<ItemPicture>>(HttpStatus.BAD_REQUEST));
    }

}