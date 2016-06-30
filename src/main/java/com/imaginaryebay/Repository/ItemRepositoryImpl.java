package com.imaginaryebay.Repository;

import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chloe on 6/28/16.
 */
public class ItemRepositoryImpl implements ItemRepository{

    private ItemDAO itemDAO;

    public void setItemDAO(ItemDAO itemDAO){
        this.itemDAO=itemDAO;
    }

    public void save(Item item) {
        this.itemDAO.persist(item);
    }

    public Item findByID(Long id){
        Item toRet = this.itemDAO.findByID(id);
        if (toRet != null){
            return toRet;
        }
        System.out.println("Item with that ID does not exist");
        return null;
    }

    // What if item doesn't have a price? Is price required?
    public Double findPriceByID(Long id){
        Item item = this.itemDAO.findByID(id);
        if (item != null){
            Double price = itemDAO.findPriceByID(id);
            if (price != null) {
                return price;
            }
            System.out.println("Item does not have a price.");
            return null;
        }
        System.out.println("Item with that ID does not exist.");
        return null;
    }

    // Same comment as for price, is category required?
    public Category findCategoryByID(Long id){
        Item item = this.itemDAO.findByID(id);
        if (item != null){
            Category cat = itemDAO.findCategoryByID(id);
            if (cat != null) {
                return cat;
            }
            System.out.println("Item does not have a category.");
            return null;

        }
        System.out.println("Item with that ID does not exist.");
        return null;
    }

    public Timestamp findEndtimeByID(Long id){
        Item item = this.itemDAO.findByID(id);
        if (item != null){
            Timestamp time = itemDAO.findEndtimeByID(id);
            if (time != null) {
                return time;
            }
            System.out.println("Item does not have a time.");
            return null;

        }
        System.out.println("Item with that ID does not exist.");
        return null;
    }

    public String findDescriptionByID(Long id){
        Item item = this.itemDAO.findByID(id);
        if (item != null){
            String description = itemDAO.findDescriptionByID(id);
            if (description != null) {
                return description;
            }
            System.out.println("Item does not have a description.");
            return null;

        }
        System.out.println("Item with that ID does not exist.");
        return null;
    }

    public Item updateItemByID(Long id, Item item){
        Item current = this.itemDAO.findByID(id);
        if (current != null){
            //See price comment
            return itemDAO.updateItemByID(id, item);
        }
        System.out.println("Item with that ID does not exist");
        return null;
    }

    public List<Item> findAllItemsByCategory(Category category){
        List<Item> toRet = this.itemDAO.findAllItemsByCategory(category);
        if (!toRet.isEmpty()){
            return toRet;
        }
        System.out.println("No items of that category.");
        return null;
    }

    public List<Item> findAllItems(){
       /* List<Item> toRet = this.itemDAO.findAllItems();
        if (!toRet.isEmpty()){
            return toRet;
        }
        System.out.println("No items available.");
        return null;*/
        //return new ArrayList<>();
        return null;
    }
/*
    public Userr findOwnerByID(Long id){
        Item item = this.itemDAO.findByID(id);
        if (item != null){
            Userr owner = itemDAO.findOwnerByID(id);
            if (owner != null) {
                return owner;
            }
            System.out.println("Item does not have an owner.");
            return null;

        }
        System.out.println("Item with that ID does not exist.");
        return null;
    }*/


}
