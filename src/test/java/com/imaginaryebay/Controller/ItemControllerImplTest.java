package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    private List<Item> all;
    private List<Item> clothes;
    private List<Item> electronics;

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

        all.add(item1);
        all.add(item2);
        all.add(item3);
        clothes.add(item1);
        clothes.add(item2);
        electronics.add(item3);

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
        assertEquals(impl.getItemByID(1L), item1);
        assertEquals(impl.getItemByID(2L), item2);
        assertEquals(impl.getItemByID(10L), item3);

    }

    @Test
    public void findPriceByID() throws Exception {
        assertEquals(impl.getPriceByID(1L), new Double(20.0));
        assertEquals(impl.getPriceByID(2L), new Double(200.0));
        assertEquals(impl.getPriceByID(10L), new Double(30.0));


    }

    @Test
    public void findCategoryByID() throws Exception {
        assertEquals(impl.getCategoryByID(1L), Category.Clothes);
        assertEquals(impl.getCategoryByID(2L), Category.Clothes);
        assertEquals(impl.getCategoryByID(10L), Category.Electronics);

    }

    @Test
    public void findEndtimeByID() throws Exception {
        assertEquals(impl.getEndtimeByID(1L), valueOf("2016-10-10 00:00:00"));
        assertEquals(impl.getEndtimeByID(2L), valueOf("2016-11-5 06:00:00"));
        assertEquals(impl.getEndtimeByID(10L), valueOf("2016-9-2 11:10:10"));

    }

    @Test
    public void findDescriptionByID() throws Exception {
        assertEquals(impl.getDescriptionByID(1L), "Scarf");
        assertEquals(impl.getDescriptionByID(2L), "Expensive Scarf");
        assertEquals(impl.getDescriptionByID(10L), "Watch");

    }

    @Test
    public void updateItemByID() throws Exception {
        Item toUpdate = new Item();
        impl.updateItemByID(2L, toUpdate);
        verify(itemRepo).updateItemByID(2L, toUpdate);
    }

    @Test
    public void getAllItems() throws Exception {
        assertEquals(impl.getAllItems(null), all);
        assertEquals(impl.getAllItems(Category.Clothes), clothes);
        assertEquals(impl.getAllItems(Category.Electronics), electronics);


    }
/*
    @Test
    public void returnItemPicturesForItem() throws Exception {

    }

    @Test
    public void createItemPicturesForItem() throws Exception {

    }*/

}