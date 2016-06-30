package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chloe on 6/23/16.
 */
public interface ItemDAO {

    public void merge(Item item);

    public void persist(Item item);

    public void refresh(Item item);

    public Item findByID(Long id);

    public Double findPriceByID(Long id);

    public Category findCategoryByID(Long id);

    public Timestamp findEndtimeByID(Long id);

    public String findDescriptionByID(Long id);

//    public Userr findOwnerByID(Long id);

    public Item updateItemByID(Long id, Item item);

    public List<Item> findAllItemsByCategory(Category category);

    public List<Item> findAllItems();
}