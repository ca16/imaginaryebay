package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chloe on 6/28/16.
 */
@Transactional
public class ItemRepositoryImpl implements ItemRepository {

    private ItemDAO itemDAO;

    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void save(Item item) {
        this.itemDAO.persist(item);
    }

    public void update(Item item) {
        if (this.itemDAO.find(item) == null) {
            throw new RestException("Item to be updated does not exist.",
                    "Item with id " + item.getId() + " was not found");
        } else {
            this.itemDAO.merge(item);
        }
    }


    public Item findByID(Long id) {
        Item toRet = this.itemDAO.findByID(id);
        if (toRet == null) {
            // what should the number be here?
            throw new RestException("Item not found.",
                    "Item with id " + id + " was not found");
        } else {
            return toRet;
        }
    }

    // What if item doesn't have a price? Is price required?
    public Double findPriceByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            Double price = itemDAO.findPriceByID(id);
            if (price != null) {
                return price;
            }
            //figure out code
            throw new RestException("Item does not have a price.",
                    "Item with id " + id + " does not have a price");
        }
        //figure out code
        throw new RestException("Item not found.",
                "Item with id " + id + " was not found");
    }

    // Same comment as for price, is category required?
    public Category findCategoryByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            Category cat = itemDAO.findCategoryByID(id);
            if (cat != null) {
                return cat;
            }
            throw new RestException("Item does not have a category.",
                    "Item with id " + id + " does not have a category");

        }
        throw new RestException("Item not found.",
                "Item with id " + id + " was not found");
    }

    public Timestamp findEndtimeByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            Timestamp time = itemDAO.findEndtimeByID(id);
            if (time != null) {
                return time;
            }
            throw new RestException("Item does not have an endtime.",
                    "Item with id " + id + " does not have a endtime");

        }
        throw new RestException("Item not found.",
                "Item with id " + id + " was not found");
    }

    public String findDescriptionByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            String description = itemDAO.findDescriptionByID(id);
            if (description != null) {
                return description;
            }
            throw new RestException("Item does not have a description.",
                    "Item with id " + id + " does not have a description");

        }
        throw new RestException("Item not found.",
                "Item with id " + id + " was not found");
    }

    public Item updateItemByID(Long id, Item item) {
        Item current = this.itemDAO.findByID(id);
        if (current != null) {
            //See price comment
            return itemDAO.updateItemByID(id, item);
        }
        throw new RestException("Item not found.",
                "Item with id " + id + " was not found");
    }

    public List<Item> findAllItemsByCategory(Category category) {
        List<Item> toRet = this.itemDAO.findAllItemsByCategory(category);
        if (!toRet.isEmpty()) {
            return toRet;
        }
        throw new RestException("No items of that category.",
                "Items of category " + category + " were not found");
    }

    public List<Item> findAllItems() {
        List<Item> toRet = this.itemDAO.findAllItems();
        if (!toRet.isEmpty()) {
            return toRet;
        }
        throw new RestException("No items available.",
                "There are not items available.");

    }

    /**
     * TODO: @Brian: I'm returning the ResponseEntities through the repository interface. TODO:
     * Do we want to manage this here or move to Controller?
     **/
    public List<ItemPicture> returnItemPicturesForItem(Long id, String urlOnly) {

        List<ItemPicture> itemPictures;

        if (urlOnly.equalsIgnoreCase("true")) {
            itemPictures = itemDAO.returnAllItemPictureURLsForItemID(id);
        } else if (urlOnly.equalsIgnoreCase("false")) {
            itemPictures = itemDAO.returnAllItemPicturesForItemID(id);
        } else {
            throw new RestException("Invalid request parameter.",
                    "The supplied request parameter is invalid for this URL.",
                    HttpStatus.BAD_REQUEST);
        }
        if (itemPictures.isEmpty()) {
            throw new RestException("Not available.",
                    "There are no entries for the requested resource.",
                    HttpStatus.OK);
        }
        return itemPictures;
    }

//    /**
//     * TODO: @Brian: I'm returning the ResponseEntities through the repository interface. TODO:
//     * Do we want to manage this here or move to Controller?
//     **/
//    public ResponseEntity<List<ItemPicture> returnItemPicturesForItem(Long id, String urlOnly) {
//
//        List<ItemPicture> itemPictures;
//
//        if (urlOnly.equalsIgnoreCase("true")) {
//            itemPictures = itemDAO.returnAllItemPictureURLsForItemID(id);
//        } else if (urlOnly.equalsIgnoreCase("false")) {
//            itemPictures = itemDAO.returnAllItemPicturesForItemID(id);
//        } else {
//            return new ResponseEntity<List<ItemPicture>>(HttpStatus.BAD_REQUEST);
//        }
//
//        if (itemPictures.isEmpty()) {
//            return new ResponseEntity<List<ItemPicture>>(HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<List<ItemPicture>>(itemPictures, HttpStatus.OK);
//    }


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
