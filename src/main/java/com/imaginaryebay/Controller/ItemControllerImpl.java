package com.imaginaryebay.Controller;

import com.imaginaryebay.DAO.ItemPictureDAO;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.ItemRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chloe on 6/23/16.
 */
@Transactional
public class ItemControllerImpl implements ItemController {

    private ItemRepository itemRepository;

    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    private ItemPictureDAO itemPictureDAO;

    public void setItemPictureDAO(ItemPictureDAO itemPictureDAO) {
        this.itemPictureDAO = itemPictureDAO;
    }

    @Override
    public ResponseEntity<Item> save(Item item) {
        return new ResponseEntity<>(this.itemRepository.save(item), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Item> getItemByID(Long id) {
        return new ResponseEntity(this.itemRepository.findByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Double> getHighestBidByID(Long id) {
        return new ResponseEntity(this.itemRepository.findHighestBidByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Item> updateItemByID(Long id, Item item) {
        return new ResponseEntity(this.itemRepository.updateItemByID(id, item), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Item>> getAllItems(String cat, String keyword, Long sellerID) {
        if ((null != cat) && (null != sellerID)) {
            return new ResponseEntity<>(this.itemRepository.findItemsByCategoryAndSeller(cat, sellerID), HttpStatus.OK);
        }
        if ((null != cat) && (null != keyword)) {
            return new ResponseEntity(this.itemRepository.findItemsByCategoryAndKeyword(cat, keyword), HttpStatus.OK);
        }
        if (null != sellerID) {
            return new ResponseEntity(this.itemRepository.findItemsBySeller(sellerID), HttpStatus.OK);
        }
        if (null != cat) {
            return new ResponseEntity(this.itemRepository.findAllItemsByCategory(cat), HttpStatus.OK);
        }
        if (null != keyword) {
            return new ResponseEntity(this.itemRepository.findItemsByKeyword(keyword), HttpStatus.OK);
        }

        if ((null == cat) && (null == keyword) && (null == sellerID)){
            return new ResponseEntity(this.itemRepository.findAllItems(), HttpStatus.OK);
        }
        else {
            throw new RestException("Argument combination unavailable.", "Invalid argument combinations include: cat, sellerID and keyword, and sellerID and keyword.", HttpStatus.OK);
        }
    }

    public ResponseEntity<List<ItemPicture>> getAllItemPicturesForItem(Long id, String urlOnly) {
        return new ResponseEntity<>(itemRepository.findAllItemPicturesForItem(id, urlOnly),
                HttpStatus.OK);
    }

//    public ResponseEntity<String> createItemPicturesForItem(Long id, MultipartFile[] files){
//        return new ResponseEntity<>(itemRepository.createItemPicturesForItem(id, files),
//                                    HttpStatus.OK);
//    }

    public ResponseEntity<ItemPicture> createItemPictureForItem(Long id, MultipartFile file) {
        return new ResponseEntity<ItemPicture>(itemRepository.createItemPictureForItem(id, file), HttpStatus.OK);
    }

    public ResponseEntity<List<Item>> findItemsBasedOnPage(int pageNum, int pageSize, String cat, String keyword, Long sellerID) {
        if ((null != cat) && (null != sellerID)) {
            return new ResponseEntity<>(this.itemRepository.findItemsByCategoryAndSellerBasedOnPage(cat, sellerID, pageNum, pageSize), HttpStatus.OK);
        }
        if ((null != cat) && (null != keyword)) {
            return new ResponseEntity(this.itemRepository.findItemsByKeywordAndCategoryBasedOnPage(cat, keyword, pageNum, pageSize), HttpStatus.OK);
        }
        if (null != sellerID) {
            return new ResponseEntity(this.itemRepository.findItemsBySellerBasedOnPage(sellerID, pageNum, pageSize), HttpStatus.OK);
        }
        if (null != cat) {
            return new ResponseEntity(this.itemRepository.findItemsByCategoryBasedOnPage(cat, pageNum, pageSize), HttpStatus.OK);
        }
        if (null != keyword) {
            return new ResponseEntity(this.itemRepository.findItemsByKeywordBasedOnPage(keyword, pageNum, pageSize), HttpStatus.OK);
        }
        if ((null == cat) && (null == keyword) && (null == sellerID)){
            return new ResponseEntity<List<Item>>(this.itemRepository.findItemsBasedOnPage(pageNum, pageSize), HttpStatus.OK);
        }
        else {
            throw new RestException("Argument combination unavailable.", "Invalid argument combinations include: cat, sellerID and keyword,and sellerID and keyword.", HttpStatus.OK);
        }

    }

    public ResponseEntity<List<Category>> getSellerCategoriesByID(@PathVariable("id") Long id) {
        return new ResponseEntity<List<Category>>(this.itemRepository.findSellerCategories(id), HttpStatus.OK);
    }

    public ResponseEntity<Integer> findItemsWithCount(String cat, String keyword, Long sellerID) {
        if ((null != cat) && (null != sellerID)) {
            return new ResponseEntity<>(itemRepository.findItemsByCategoryAndSellerCount(cat, sellerID), HttpStatus.OK);
        }
        if ((null != cat) && (null != keyword)) {
            return new ResponseEntity<>(itemRepository.findItemsByCategoryAndKeywordCount(cat, keyword), HttpStatus.OK);
        }
        if (null != sellerID) {
            return new ResponseEntity<>(itemRepository.findItemsBySellerCount(sellerID), HttpStatus.OK);
        }
        if (null != cat) {
            return new ResponseEntity<>(itemRepository.findItemsByCategoryCount(cat), HttpStatus.OK);
        }
        if (null != keyword) {
            return new ResponseEntity<>(itemRepository.findItemsByKeywordCount(keyword), HttpStatus.OK);
        }

        if ((null == cat) && (null == keyword) && (null == sellerID)){
            return new ResponseEntity(itemRepository.findItemsCount(), HttpStatus.OK);
        }
        else {
            throw new RestException("Argument combination unavailable.", "Invalid argument combinations include: cat, sellerID and keyword,and sellerID and keyword.", HttpStatus.OK);
        }
    }

//    public ResponseEntity<Long> findTotalNumOfItems(){
//        return new ResponseEntity<Long>(this.itemRepository.findTotalNumOfItems(),HttpStatus.OK);
//    }

}