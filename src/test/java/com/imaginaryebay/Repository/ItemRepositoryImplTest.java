package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import org.junit.Assert;
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
 * Created by Chloe on 6/30/16.
 */
public class ItemRepositoryImplTest {

    @Mock
    private ItemDAO itemDao;

    @Mock
    private ItemDAO itemDaoEmpties;

    private ItemRepositoryImpl impl;
    private ItemRepositoryImpl implEmpties;

    private Item item1;
    private Item item2;
    private Item item3;
    private Item noFields;
    private Item notInDB;
    private Item toUpdate;

    private List<Item> all;
    private List<Item> clothes;
    private List<Item> electronics;
    private List<Item> empties;


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

        impl = new ItemRepositoryImpl();
        impl.setItemDAO(itemDao);

        when(itemDaoEmpties.findAllItems()).thenReturn(empties);
        when(itemDaoEmpties.findAllItemsByCategory(Category.Clothes)).thenReturn(empties);
        when(itemDaoEmpties.findAllItemsByCategory(Category.Electronics)).thenReturn(empties);

        implEmpties = new ItemRepositoryImpl();
        implEmpties.setItemDAO(itemDaoEmpties);


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

        try {
            impl.findByID(25L);
        } catch (RestException exc) {
            Assert.assertEquals("Item not found.", exc.getMessage());
        }
    }


    @Test
    public void findPriceByID() throws Exception {
        assertEquals(impl.findPriceByID(1L), new Double(20.0));
        assertEquals(impl.findPriceByID(2L), new Double(200.0));
        assertEquals(impl.findPriceByID(10L), new Double(30.0));

        try {
            impl.findPriceByID(24L);
        } catch (RestException exc) {
            Assert.assertEquals("Item does not have a price.", exc.getMessage());
        }

        try {
            impl.findPriceByID(25L);
        } catch (RestException exc) {
            Assert.assertEquals("Item not found.", exc.getMessage());
        }
    }

    @Test
    public void findCategoryByID() throws Exception {
        assertEquals(impl.findCategoryByID(1L), Category.Clothes);
        assertEquals(impl.findCategoryByID(2L), Category.Clothes);
        assertEquals(impl.findCategoryByID(10L), Category.Electronics);

        try {
            impl.findCategoryByID(24L);
        } catch (RestException exc) {
            Assert.assertEquals("Item does not have a category.", exc.getMessage());
        }

        try {
            impl.findCategoryByID(25L);
        } catch (RestException exc) {
            Assert.assertEquals("Item not found.", exc.getMessage());
        }
    }

    @Test
    public void findEndtimeByID() throws Exception {
        assertEquals(impl.findEndtimeByID(1L), valueOf("2016-10-10 00:00:00"));
        assertEquals(impl.findEndtimeByID(2L), valueOf("2016-11-5 06:00:00"));
        assertEquals(impl.findEndtimeByID(10L), valueOf("2016-9-2 11:10:10"));

        try {
            impl.findEndtimeByID(24L);
        } catch (RestException exc) {
            Assert.assertEquals("Item does not have an endtime.", exc.getMessage());
        }

        try {
            impl.findEndtimeByID(25L);
        } catch (RestException exc) {
            Assert.assertEquals("Item not found.", exc.getMessage());
        }
    }

    @Test
    public void findDescriptionByID() throws Exception {
        assertEquals(impl.findDescriptionByID(1L), "Scarf");
        assertEquals(impl.findDescriptionByID(2L), "Expensive Scarf");
        assertEquals(impl.findDescriptionByID(10L), "Watch");

        try {
            impl.findDescriptionByID(24L);
        } catch (RestException exc) {
            Assert.assertEquals("Item does not have a description.", exc.getMessage());
        }

        try {
            impl.findDescriptionByID(25L);
        } catch (RestException exc) {
            Assert.assertEquals("Item not found.", exc.getMessage());
        }

    }


    @Test
    public void update() throws Exception {
        impl.update(toUpdate);
        verify(itemDao).find(toUpdate);
        verify(itemDao).merge(toUpdate);

        try {
            impl.update(notInDB);
        } catch (RestException exc) {
            Assert.assertEquals("Item to be updated does not exist.", exc.getMessage());
        }

    }

    @Test
    public void updateItemByID() throws Exception {
        impl.updateItemByID(2L, toUpdate);
        verify(itemDao).findByID(2L);
        verify(itemDao).updateItemByID(2L, toUpdate);

        try {
            impl.updateItemByID(25L, toUpdate);
        } catch (RestException exc) {
            Assert.assertEquals("Item not found.", exc.getMessage());
        }
    }

    @Test
    public void findAllItemsByCategory() throws Exception {
        assertEquals(impl.findAllItemsByCategory(Category.Clothes), clothes);
        assertEquals(impl.findAllItemsByCategory(Category.Electronics), electronics);

        try {
            implEmpties.findAllItemsByCategory(Category.Clothes);
        } catch (RestException exc) {
            Assert.assertEquals("No items of that category.", exc.getMessage());
        }
        try {
            implEmpties.findAllItemsByCategory(Category.Electronics);
        } catch (RestException exc) {
            Assert.assertEquals("No items of that category.", exc.getMessage());
        }
    }

    @Test
    public void findAllItems() throws Exception {
        assertEquals(impl.findAllItems(), all);

        try {
            implEmpties.findAllItems();
        } catch (RestException exc) {
            Assert.assertEquals("No items available.", exc.getMessage());
        }
    }
}
/*
    @Test
    public void returnItemPicturesForItem() throws Exception {

    }*/