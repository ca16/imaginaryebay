package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Models.Userr;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chloe on 6/28/16.
 */
public interface ItemRepository {

    public Item save(Item item);

    public void update(Item item);

    public Item findByID(Long id);

    public Item updateItemByID(Long id, Item item);

    public List<Category> findSellerCategories(Long ownerId);

    public List<ItemPicture> findAllItemPicturesForItem(Long id, String urlOnly);

    public ItemPicture createItemPictureForItem(Long id, MultipartFile file);

    public String createItemPicturesForItem(Long id, MultipartFile[] files);

    public Double findHighestBidByID(Long id);

    //////////////////////////////////////
    // Item Searches /////////////////////
    //////////////////////////////////////

    public List<Item> findAllItems();

    public List<Item> findItemsByKeyword(String keyword);

    public List<Item> findAllItemsByCategory(String category);

    public List<Item> findItemsBySeller(Long ownerId);

    public List<Item> findItemsByCategoryAndKeyword(String category, String keyword);

    public List<Item> findItemsByCategoryAndSeller(String cat, Long ownerId);

    ////////////////////////////////////
    // Include Pagination //////////////
    ////////////////////////////////////

    public List<Item> findItemsBasedOnPage(int pageNum, int pageSize);

    public List<Item> findItemsByCategoryBasedOnPage(String category, int pageNum, int pageSize);

    public List<Item> findItemsByCategoryAndSellerBasedOnPage(String cateogry, Long sellerID, int pageNum, int pageSize);

    public List<Item> findItemsByKeywordAndCategoryBasedOnPage(String category, String keyword, int pageNum, int pageSize);

    public List<Item> findItemsByKeywordBasedOnPage(String keyword, int pageNum, int pageSize);

    public List<Item> findItemsBySellerBasedOnPage(Long id, int pageNum, int pageSize);

    //////////////////////////////////////
    // Counts ////////////////////////////
    //////////////////////////////////////

    public Integer findItemsCount();

    public Integer findItemsByKeywordCount(String keyword);

    public Integer findItemsByCategoryCount(String category);

    public Integer findItemsBySellerCount(Long id);

    public Integer findItemsByCategoryAndSellerCount(String category, Long sellerID);

    public Integer findItemsByCategoryAndKeywordCount(String category, String keyword);

    }