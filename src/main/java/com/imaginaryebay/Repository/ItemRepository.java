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

    public Double findPriceByID(Long id);

    public Category findCategoryByID(Long id);

    public Timestamp findEndtimeByID(Long id);

    public Userr findOwnerByID(Long id);

    public String findNameByID(Long id);

    public Double findHighestBidByID(Long id);

    public String findDescriptionByID(Long id);

    public Item updateItemByID(Long id, Item item);

    public List<Item> findAllItemsByCategory(String category);

    public List<Item> findAllItems();

    public List<ItemPicture> findAllItemPicturesForItem(Long id, String urlOnly);

    public ItemPicture createItemPictureForItem(Long id, MultipartFile file);

    public String createItemPicturesForItem(Long id, MultipartFile[] files);

    public List<Item> findItemsByName(String name);

    public List<Item> findItemsByCategoryAndName(String category, String name);

    public List<Item> findItemsBySeller(Long ownerId);

    public List<Item> findItemsByCategoryAndSeller(String cat, Long ownerId);

    public List<Category> findSellerCategories(Long ownerId);

    public List<Item> findItemsBasedOnPage(int pageNum, int pageSize);

    public Long findTotalNumOfItems();

    public List<Item> findItemsByCategoryBasedOnPage(String category, int pageNum, int pageSize);

    public List<Item> findItemsByCategoryAndSellerBasedOnPage(String cateogry, Long sellerID, int pageNum, int pageSize);

    public List<Item> findItemsByNameAndCategoryBasedOnPage(String category, String name, int pageNum, int pageSize);

    public List<Item> findItemsByNameBasedOnPage(String name, int pageNum, int pageSize);

    public List<Item> findItemsBySellerBasedOnPage(Long id, int pageNum, int pageSize);

    }