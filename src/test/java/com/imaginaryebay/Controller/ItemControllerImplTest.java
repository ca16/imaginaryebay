package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * Created by Chloe on 7/1/16.
 */
public class ItemControllerImplTest {

    @Mock
    private ItemRepository itemRepo;

    private ItemControllerImpl impl;

    private Item item1;
    private Item item2;
    private Item item3;
    private Item toUpdate;
    private ItemPicture itempic1;
    private ItemPicture itempic2;
    private ItemPicture itempic3;
    private ItemPicture itempic4;
    private ItemPicture itempic5;
    private ItemPicture itempic6;
    private ItemPicture itempic7;
    private ItemPicture itempic8;
    private ItemPicture itempic9;
    private ItemPicture itempic10;
    private ItemPicture itempic11;
    private ItemPicture itempic12;

    private List<Item> all;
    private List<Item> clothes;
    private List<Item> electronics;
    private List<ItemPicture> item1pics;
    private List<ItemPicture> item2pics;
    private List<ItemPicture> item3pics;
    private List<ItemPicture> item1picurls;
    private List<ItemPicture> item2picurls;
    private List<ItemPicture> item3picurls;
    private ResponseEntity<List<ItemPicture>> item1picresponse;
    private ResponseEntity<List<ItemPicture>> item2picresponse;
    private ResponseEntity<List<ItemPicture>> item3picresponse;
    private ResponseEntity<List<ItemPicture>> item1picurlresponse;
    private ResponseEntity<List<ItemPicture>> item2picurlresponse;
    private ResponseEntity<List<ItemPicture>> item3picurlresponse;

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

        all = new ArrayList<>();
        clothes = new ArrayList<>();
        electronics = new ArrayList<>();
        item1pics = new ArrayList<>();
        item2pics = new ArrayList<>();
        item3pics = new ArrayList<>();
        item1picurls=new ArrayList<>();
        item2picurls=new ArrayList<>();
        item3picurls=new ArrayList<>();

        all.add(item1);
        all.add(item2);
        all.add(item3);
        clothes.add(item1);
        clothes.add(item2);
        electronics.add(item3);

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
        itempic7 = new ItemPicture();
        itempic7.setUrl("http://scarfheroes.wikia.com/wiki/File:Royal-stewart-tartan-lambswool-scarf.jpg");
        itempic8 =new ItemPicture();
        itempic8.setUrl("http://www.shopazil.com/products/solid-silk-linen-scarf");
        itempic9 = new ItemPicture();
        itempic9.setUrl("http://99fungames.com/knot-your-scarf-game/");
        itempic10=new ItemPicture();
        itempic10.setUrl("http://www.overstock.com/Clothing-Shoes/Burberry-Plaid-Camel-Cashmere-Scarf/5400771/product.html");
        itempic11=new ItemPicture();
        itempic11.setUrl("http://www.shinola.com/shop/therunwell47-leather-watch-s0110.html");
        itempic12 = new ItemPicture();
        itempic12.setUrl("http://6iee.com/790839.html");

        item1pics.add(itempic2);
        item2pics.add(itempic3);
        item2pics.add(itempic4);
        item3pics.add(itempic5);
        item3pics.add(itempic6);
        item1picurls.add(itempic7);
        item1picurls.add(itempic8);
        item2picurls.add(itempic9);
        item2picurls.add(itempic10);
        item3picurls.add(itempic11);
        item3picurls.add(itempic12);

        item1picresponse=new ResponseEntity<>(item1pics,HttpStatus.OK);
        item2picresponse=new ResponseEntity<>(item2pics,HttpStatus.OK);
        item3picresponse=new ResponseEntity<>(item3pics,HttpStatus.OK);
        item1picurlresponse=new ResponseEntity<>(item1picurls,HttpStatus.OK);
        item2picurlresponse=new ResponseEntity<>(item2picurls,HttpStatus.OK);
        item3picurlresponse=new ResponseEntity<>(item3picurls,HttpStatus.OK);

        when(itemRepo.findByID(1L)).thenReturn(item1);
        when(itemRepo.findPriceByID(1L)).thenReturn(20.0);
        when(itemRepo.findCategoryByID(1L)).thenReturn(Category.Clothes);
        when(itemRepo.findDescriptionByID(1L)).thenReturn("Scarf");
        when(itemRepo.findEndtimeByID(1L)).thenReturn(valueOf("2016-10-10 00:00:00"));

        when(itemRepo.findByID(2L)).thenReturn(item2);
        when(itemRepo.findPriceByID(2L)).thenReturn(200.0);
        when(itemRepo.findCategoryByID(2L)).thenReturn(Category.Clothes);
        when(itemRepo.findDescriptionByID(2L)).thenReturn("Expensive Scarf");
        when(itemRepo.findEndtimeByID(2L)).thenReturn(valueOf("2016-11-5 06:00:00"));

        when(itemRepo.findByID(10L)).thenReturn(item3);
        when(itemRepo.findPriceByID(10L)).thenReturn(30.0);
        when(itemRepo.findCategoryByID(10L)).thenReturn(Category.Electronics);
        when(itemRepo.findDescriptionByID(10L)).thenReturn("Watch");
        when(itemRepo.findEndtimeByID(10L)).thenReturn(valueOf("2016-9-2 11:10:10"));

        when(itemRepo.findAllItems()).thenReturn(all);
        when(itemRepo.findAllItemsByCategory(Category.Clothes)).thenReturn(clothes);
        when(itemRepo.findAllItemsByCategory(Category.Electronics)).thenReturn(electronics);

        when(itemRepo.returnItemPicturesForItem(1L,"false")).thenReturn(item1pics);
        when(itemRepo.returnItemPicturesForItem(1L,"true")).thenReturn(item1picurls);
        when(itemRepo.returnItemPicturesForItem(2L,"false")).thenReturn(item2pics);
        when(itemRepo.returnItemPicturesForItem(2L,"true")).thenReturn(item2picurls);

        when(itemRepo.returnItemPicturesForItem(10L,"false")).thenThrow(new RestException("Not available.",
                "There are no entries for the requested resource.",
                HttpStatus.OK));
        when(itemRepo.returnItemPicturesForItem(10L,"true")).thenThrow(new RestException("Not available.",
                "There are no entries for the requested resource.",
                HttpStatus.OK));
        when(itemRepo.returnItemPicturesForItem(10L,"fdahfh")).thenThrow(new RestException("Invalid request parameter.",
                "The supplied request parameter is invalid for this URL.",
                HttpStatus.BAD_REQUEST));
        

        impl = new ItemControllerImpl();
        impl.setItemRepository(itemRepo);


    }

    @Test
    public void save() throws Exception {
        Item toSave = new Item();
        impl.save(toSave);
        verify(itemRepo).save(toSave);
    }

    @Test
    public void findByID() throws Exception {
        assertEquals(impl.getItemByID(1L).getBody(), item1);
        assertEquals(impl.getItemByID(2L).getBody(), item2);
        assertEquals(impl.getItemByID(10L).getBody(), item3);

        assertEquals(impl.getItemByID(1L).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getItemByID(2L).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getItemByID(10L).getStatusCode(), org.springframework.http.HttpStatus.OK);


    }

    @Test
    public void findPriceByID() throws Exception {
        assertEquals(impl.getPriceByID(1L).getBody(), new Double(20.0));
        assertEquals(impl.getPriceByID(2L).getBody(), new Double(200.0));
        assertEquals(impl.getPriceByID(10L).getBody(), new Double(30.0));

        assertEquals(impl.getPriceByID(1L).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getPriceByID(2L).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getPriceByID(10L).getStatusCode(), org.springframework.http.HttpStatus.OK);


    }

    @Test
    public void findCategoryByID() throws Exception {
        assertEquals(impl.getCategoryByID(1L).getBody(), Category.Clothes);
        assertEquals(impl.getCategoryByID(2L).getBody(), Category.Clothes);
        assertEquals(impl.getCategoryByID(10L).getBody(), Category.Electronics);

        assertEquals(impl.getCategoryByID(1L).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getCategoryByID(2L).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getCategoryByID(10L).getStatusCode(), org.springframework.http.HttpStatus.OK);

    }

    @Test
    public void findEndtimeByID() throws Exception {
        assertEquals(impl.getEndtimeByID(1L).getBody(), valueOf("2016-10-10 00:00:00"));
        assertEquals(impl.getEndtimeByID(2L).getBody(), valueOf("2016-11-5 06:00:00"));
        assertEquals(impl.getEndtimeByID(10L).getBody(), valueOf("2016-9-2 11:10:10"));

        assertEquals(impl.getEndtimeByID(1L).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getEndtimeByID(2L).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getEndtimeByID(10L).getStatusCode(), org.springframework.http.HttpStatus.OK);

    }

    @Test
    public void findDescriptionByID() throws Exception {
        assertEquals(impl.getDescriptionByID(1L).getBody(), "Scarf");
        assertEquals(impl.getDescriptionByID(2L).getBody(), "Expensive Scarf");
        assertEquals(impl.getDescriptionByID(10L).getBody(), "Watch");

        assertEquals(impl.getDescriptionByID(1L).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getDescriptionByID(2L).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getDescriptionByID(10L).getStatusCode(), org.springframework.http.HttpStatus.OK);

    }

    @Test
    public void updateItemByID() throws Exception {
        Item toUpdate = new Item();
        impl.updateItemByID(2L, toUpdate);
        verify(itemRepo).updateItemByID(2L, toUpdate);
    }

    @Test
    public void getAllItems() throws Exception {
        assertEquals(impl.getAllItems(null).getBody(), all);
        assertEquals(impl.getAllItems(Category.Clothes).getBody(), clothes);
        assertEquals(impl.getAllItems(Category.Electronics).getBody(), electronics);

        assertEquals(impl.getAllItems(null).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getAllItems(Category.Clothes).getStatusCode(), org.springframework.http.HttpStatus.OK);
        assertEquals(impl.getAllItems(Category.Electronics).getStatusCode(), org.springframework.http.HttpStatus.OK);


    }

    @Test
    public void returnItemPicturesForItem() throws Exception {
//        assertEquals(impl.returnItemPicturesForItem(1L,"false"),item1pics);
//        assertEquals(impl.returnItemPicturesForItem(1L,"true"),item1picurls);
//        assertEquals(impl.returnItemPicturesForItem(2L,"false"),item2pics);
//        assertEquals(impl.returnItemPicturesForItem(2L,"true"),item2picurls);
    	assertEquals(impl.returnItemPicturesForItem(1L,"false"),item1picresponse);
    	assertEquals(impl.returnItemPicturesForItem(1L,"true"),item1picurlresponse);
    	assertEquals(impl.returnItemPicturesForItem(2L,"false"),item2picresponse);
    	assertEquals(impl.returnItemPicturesForItem(2L,"true"),item2picurlresponse);
    }
    @Test(expected=RestException.class)
    public void returnItemPicsWithException(){
    	impl.returnItemPicturesForItem(10L,"false");
    	impl.returnItemPicturesForItem(10L,"true");
    	impl.returnItemPicturesForItem(10L,"fdahfh");
    }
/*
    @Test
    public void returnItemPicturesForItem() throws Exception {

    }

    @Test
    public void createItemPicturesForItem() throws Exception {

    }*/
}