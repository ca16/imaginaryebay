package com.imaginaryebay.DAO;

import com.imaginaryebay.Controller.ItemControllerImpl;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Repository.ItemRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 7/1/16.
 */
public class ItemDAOImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;
    @Mock
    private Query query2;
    @Mock 
    Query query3;
    @Mock
    private Query queryAll;
    @Mock
    private Query queryClothes;
    @Mock
    private Query queryElectronics;
    @Mock
    private Query itempicquery1;
    @Mock
    private Query itempicquery2;
    @Mock
    private Query itempicquery3;
    @Mock
    private Query itempicquery4;
    @Mock
    private Query itempicquery5;
    @Mock
    private Query itempicquery6;

    @Mock
    private Item item1;
    @Mock
    private Item item2;
    @Mock
    private Item item3;
    
    @Mock
    private ItemPicture itempic1;
    @Mock
    private ItemPicture itempic2;
    @Mock 
    private ItemPicture itempic3;
    @Mock
    private ItemPicture itempic4;
    @Mock
    private ItemPicture itempic5;
    @Mock
    private ItemPicture itempic6;
    
    @Mock
    private ItemPicture itempic7;
    @Mock
    private ItemPicture itempic8;
    @Mock 
    private ItemPicture itempic9;
    @Mock
    private ItemPicture itempic10;
    @Mock
    private ItemPicture itempic11;
    @Mock
    private ItemPicture itempic12;

    private ItemDAOImpl impl;

    private List<Item> all;
    private List<Item> clothes;
    private List<Item> electronics;
    private List<ItemPicture> item1pics;
    private List<ItemPicture> item2pics;
    private List<ItemPicture> item3pics;
    private List<ItemPicture> item1picurls;
    private List<ItemPicture> item2picurls;
    private List<ItemPicture> item3picurls;

    @Before
    public void setUp() throws Exception {


        MockitoAnnotations.initMocks(this);

        when(item1.getCategory()).thenReturn(Category.Clothes);
        when(item1.getPrice()).thenReturn(20.0);
        when(item1.getDescription()).thenReturn("Scarf");
        when(item1.getEndtime()).thenReturn(valueOf("2016-10-10 00:00:00"));

        when(item2.getCategory()).thenReturn(Category.Clothes);
        when(item2.getPrice()).thenReturn(200.0);
        when(item2.getDescription()).thenReturn("Expensive Scarf");
        when(item2.getEndtime()).thenReturn(valueOf("2016-11-5 06:00:00"));

        when(item3.getCategory()).thenReturn(Category.Electronics);
        when(item3.getPrice()).thenReturn(30.0);
        when(item3.getDescription()).thenReturn("Watch");
        when(item3.getEndtime()).thenReturn(valueOf("2016-9-2 11:10:10"));

        when(item1.getId()).thenReturn(1L);
        when(item2.getId()).thenReturn(2L);
        when(item3.getId()).thenReturn(10L);
        
        when(itempic1.getAuction_item()).thenReturn(item1);
        when(itempic1.getUrl()).thenReturn("http://scarfheroes.wikia.com/wiki/File:Royal-stewart-tartan-lambswool-scarf.jpg");
        
        when(itempic2.getAuction_item()).thenReturn(item1);
        when(itempic2.getUrl()).thenReturn("http://www.shopazil.com/products/solid-silk-linen-scarf");
        when(itempic3.getAuction_item()).thenReturn(item2);
        when(itempic3.getUrl()).thenReturn("http://99fungames.com/knot-your-scarf-game/");
        when(itempic4.getAuction_item()).thenReturn(item2);
        when(itempic4.getUrl()).thenReturn("http://www.overstock.com/Clothing-Shoes/Burberry-Plaid-Camel-Cashmere-Scarf/5400771/product.html");
        when(itempic5.getAuction_item()).thenReturn(item3);
        when(itempic5.getUrl()).thenReturn("http://www.shinola.com/shop/therunwell47-leather-watch-s0110.html");
        when(itempic5.getAuction_item()).thenReturn(item3);
        when(itempic6.getUrl()).thenReturn("http://6iee.com/790839.html");
        when(itempic6.getAuction_item()).thenReturn(item3);
        when(itempic7.getUrl()).thenReturn("http://scarfheroes.wikia.com/wiki/File:Royal-stewart-tartan-lambswool-scarf.jpg");
        when(itempic8.getUrl()).thenReturn("http://www.shopazil.com/products/solid-silk-linen-scarf");
        when(itempic9.getUrl()).thenReturn("http://99fungames.com/knot-your-scarf-game/");
        when(itempic10.getUrl()).thenReturn("http://www.overstock.com/Clothing-Shoes/Burberry-Plaid-Camel-Cashmere-Scarf/5400771/product.html");
        when(itempic11.getUrl()).thenReturn("http://www.shinola.com/shop/therunwell47-leather-watch-s0110.html");
        when(itempic12.getUrl()).thenReturn("http://6iee.com/790839.html");
        
        when(itempic1.getId()).thenReturn(1L);
        when(itempic2.getId()).thenReturn(2L);
        when(itempic3.getId()).thenReturn(3L);
        when(itempic4.getId()).thenReturn(4L);
        when(itempic5.getId()).thenReturn(5L);
        when(itempic6.getId()).thenReturn(6L);
        when(itempic7.getId()).thenReturn(1L);
        when(itempic8.getId()).thenReturn(2L);
        when(itempic9.getId()).thenReturn(3L);
        when(itempic10.getId()).thenReturn(4L);
        when(itempic11.getId()).thenReturn(5L);
        when(itempic12.getId()).thenReturn(6L);


        all = new ArrayList<>();
        clothes = new ArrayList<>();
        electronics = new ArrayList<>();
        item1pics=new ArrayList<>();
        item2pics=new ArrayList<>();
        item3pics=new ArrayList<>();
        item1picurls=new ArrayList<>();
        item2picurls=new ArrayList<>();
        item3picurls=new ArrayList<>();

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
        item1picurls.add(itempic7);
        item1picurls.add(itempic8);
        item2picurls.add(itempic9);
        item2picurls.add(itempic10);
        item3picurls.add(itempic11);
        item3picurls.add(itempic12);

        when(entityManager.find(Item.class, 1L)).thenReturn(item1);
        when(entityManager.find(Item.class, 2L)).thenReturn(item2);
        when(entityManager.find(Item.class, 10L)).thenReturn(item3);

        when(entityManager.createQuery("select i from Item i where i.category = ?1 order by i.price")).thenReturn(query);
        when(query.setParameter(1, Category.Clothes)).thenReturn(queryClothes);
        when(query.setParameter(1, Category.Electronics)).thenReturn(queryElectronics);
        when(queryClothes.getResultList()).thenReturn(clothes);
        when(queryElectronics.getResultList()).thenReturn(electronics);

        when(entityManager.createQuery("select i from Item i order by i.price")).thenReturn(queryAll);
        when(queryAll.getResultList()).thenReturn(all);
        
        when(entityManager.createQuery("Select ip from ItemPicture ip join fetch ip.auction_item where ip.auction_item.id = :id")).thenReturn(query2);
        when(query2.setParameter("id",1L)).thenReturn(itempicquery1);
        when(query2.setParameter("id",2L)).thenReturn(itempicquery2);
        when(query2.setParameter("id",10L)).thenReturn(itempicquery3);
        when(itempicquery1.getResultList()).thenReturn(item1pics);
        when(itempicquery2.getResultList()).thenReturn(item2pics);
        when(itempicquery3.getResultList()).thenReturn(item3pics);
        
        when(entityManager.createQuery("Select ip.id, ip.url from ItemPicture ip join ip.auction_item where ip.auction_item.id = :id")).thenReturn(query3);
        when(query3.setParameter("id",1L)).thenReturn(itempicquery4);
        when(query3.setParameter("id",2L)).thenReturn(itempicquery5);
        when(query3.setParameter("id",10L)).thenReturn(itempicquery6);
        when(itempicquery4.getResultList()).thenReturn(item1picurls);
        when(itempicquery5.getResultList()).thenReturn(item2picurls);
        when(itempicquery6.getResultList()).thenReturn(item3picurls);

        impl = new ItemDAOImpl();
        impl.setEntityManager(entityManager);

    }

    @Test
    public void persist() throws Exception {
        Item toSave = new Item();
        impl.persist(toSave);
        verify(entityManager).persist(toSave);
    }

    @Test
    public void merge() throws Exception {
        Item toMerge = new Item();
        impl.merge(toMerge);
        verify(entityManager).merge(toMerge);
    }

    @Test
    public void refresh() throws Exception {
        Item toRefresh = new Item();
        impl.refresh(toRefresh);
        verify(entityManager).refresh(toRefresh);
    }

    @Test
    public void find() throws Exception {
        assertEquals(impl.find(item1), item1);
        assertEquals(impl.find(item2), item2);
        assertEquals(impl.find(item3), item3);

    }

    @Test
    public void findByID() throws Exception {
        assertEquals(impl.findByID(1L), item1);
        assertEquals(impl.findByID(2L), item2);
        assertEquals(impl.findByID(10L), item3);
    }

    @Test
    public void findPriceByID() throws Exception {
        assertEquals(impl.findPriceByID(1L), new Double (20.0));
        assertEquals(impl.findPriceByID(2L), new Double (200.0));
        assertEquals(impl.findPriceByID(10L), new Double (30.0));
    }

    @Test
    public void findCategoryByID() throws Exception {
        assertEquals(impl.findCategoryByID(1L), Category.Clothes);
        assertEquals(impl.findCategoryByID(2L), Category.Clothes);
        assertEquals(impl.findCategoryByID(10L), Category.Electronics);
    }

    @Test
    public void findEndtimeByID() throws Exception {
        assertEquals(impl.findEndtimeByID(1L), valueOf("2016-10-10 00:00:00"));
        assertEquals(impl.findEndtimeByID(2L), valueOf("2016-11-5 06:00:00"));
        assertEquals(impl.findEndtimeByID(10L), valueOf("2016-9-2 11:10:10"));
    }

    @Test
    public void findDescriptionByID() throws Exception {
        assertEquals(impl.findDescriptionByID(1L), "Scarf");
        assertEquals(impl.findDescriptionByID(2L), "Expensive Scarf");
        assertEquals(impl.findDescriptionByID(10L), "Watch");
    }
/*
    @Test
    public void updateItemByID() throws Exception {

    }*/

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
    public void returnAllItemPicturesForItemID() throws Exception {
    	assertEquals(impl.returnAllItemPicturesForItemID(1L),item1pics);
    	assertEquals(impl.returnAllItemPicturesForItemID(2L),item2pics);
    	assertEquals(impl.returnAllItemPicturesForItemID(10L),item3pics);
    }

//    @Test
//    public void returnAllItemPictureURLsForItemID() throws Exception {
//    	assertEquals(impl.returnAllItemPictureURLsForItemID(1L),item1picurls);
//    	assertEquals(impl.returnAllItemPictureURLsForItemID(2L),item2picurls);
//    	assertEquals(impl.returnAllItemPictureURLsForItemID(10L),item3picurls);
//    }

}