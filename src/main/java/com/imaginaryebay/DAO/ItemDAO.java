package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
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

    public Item find(Item item);

    public Item findByID(Long id);

    public Double findPriceByID(Long id);

    public Category findCategoryByID(Long id);

    public Timestamp findEndtimeByID(Long id);

    public String findDescriptionByID(Long id);

    public Userr findOwnerByID(Long id);

    public String findNameByID(Long id);

    public Double findHighestBidByID(Long id);

    public Item updateItemByID(Long id, Item item);

    public List<Item> findAllItemsByCategory(Category category);

    public List<Item> findAllItems();

    List<ItemPicture> findAllItemPicturesForItemID(Long id);

    List<ItemPicture> findAllItemPictureURLsForItemID(Long id);

    public List<Item> findItemsBasedOnPage(int pageNum, int pageSize);

    public List<Item> findItemsByName(String name);

    public List<Item> findItemsByCategoryAndName(Category cat, String name);

    public List<Item> findItemsBySeller(Long id);

    public List<Item> findItemsByCategoryAndSeller(Category cat, Long ownerId);

    public List<Category> findSellerCategories(Long ownerId);
}