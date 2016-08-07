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

    public Item updateItemByID(Long id, Item item);

//    public Long findTotalNumOfItems();

    public List<Category> findSellerCategories(Long ownerId);

    List<ItemPicture> findAllItemPicturesForItemID(Long id);

    List<ItemPicture> findAllItemPictureURLsForItemID(Long id);


    //////////////////////////////////////
    // Item Properties ///////////////////
    //////////////////////////////////////
    // do we need all these?

    public Double findPriceByID(Long id);

    public Category findCategoryByID(Long id);

    public Timestamp findEndtimeByID(Long id);

    public String findDescriptionByID(Long id);

    public Userr findOwnerByID(Long id);

    public String findNameByID(Long id);

    public Double findHighestBidByID(Long id);

    //////////////////////////////////////
    // Item Searches /////////////////////
    //////////////////////////////////////

    public List<Item> findAllItems();

    public List<Item> findItemsByKeyword(String keyword);

    public List<Item> findAllItemsByCategory(Category category);

    public List<Item> findItemsBySeller(Long id);

    public List<Item> findItemsByCategoryAndKeyword(Category cat, String keyword);

    public List<Item> findItemsByCategoryAndSeller(Category cat, Long ownerId);

    //////////////////////////////////////
    // Searches w Pagination /////////////
    //////////////////////////////////////

    public List<Item> findItemsBasedOnPage(int pageNum, int pageSize);

    public List<Item> findItemsByKeywordBasedOnPage(String keyword, int pageNum, int pageSize);

    public List<Item> findItemsByCategoryBasedOnPage(Category category, int pageNum, int pageSize);

    public List<Item> findItemsBySellerBasedOnPage(Long id, int pageNum, int pageSize);

    public List<Item> findItemsByCategoryAndSellerBasedOnPage(Category cateogry, Long sellerID, int pageNum, int pageSize);

    public List<Item> findItemsByCategoryAndKeywordBasedOnPage(Category category, String keyword, int pageNum, int pageSize);


    //////////////////////////////////////
    // Counts ////////////////////////////
    //////////////////////////////////////

    public Integer findItemsCount();

    public Integer findItemsByKeywordCount(String keyword);

    public Integer findItemsByCategoryCount(Category category);

    public Integer findItemsBySellerCount(Long id);

    public Integer findItemsByCategoryAndSellerCount(Category category, Long sellerID);

    public Integer findItemsByCategoryAndKeywordCount(Category category, String keyword);
}