package com.imaginaryebay.Controller;

import com.imaginaryebay.DAO.ItemPictureDAO;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chloe on 6/23/16.
 */
@Transactional
public class ItemControllerImpl implements ItemController {

    private ItemRepository itemRepository;
    public void setItemRepository(ItemRepository itemRepository){
        this.itemRepository=itemRepository;
    }

    private ItemPictureDAO itemPictureDAO;
    public void setItemPictureDAO(ItemPictureDAO itemPictureDAO){
        this.itemPictureDAO=itemPictureDAO;
    }

    @Override
    public ResponseEntity<Void> save(Item item) {
        this.itemRepository.save(item);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Item> getItemByID(Long id){
        return new ResponseEntity<Item>(this.itemRepository.findByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Double> getPriceByID(Long id){
        return new ResponseEntity<Double>(this.itemRepository.findPriceByID(id), HttpStatus.OK);
    }
/*
    @Override
    public Userr getOwnerByID(Long id){
        return this.itemRepository.findOwnerByID(id);
    }
*/
    @Override
    public ResponseEntity<Category> getCategoryByID(Long id){
        return new ResponseEntity<Category>(this.itemRepository.findCategoryByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getDescriptionByID(Long id){
        return new ResponseEntity<String>(this.itemRepository.findDescriptionByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Timestamp> getEndtimeByID(Long id){
        return new ResponseEntity<Timestamp>(this.itemRepository.findEndtimeByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Item> updateItemByID(Long id, Item item){
        return new ResponseEntity<Item>(this.itemRepository.updateItemByID(id, item), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Item>> getAllItems(String cat){
        if (null != cat){
            return new ResponseEntity<List<Item>>(this.itemRepository.findAllItemsByCategory(cat), HttpStatus.OK);
        }
        return new ResponseEntity<List<Item>>(this.itemRepository.findAllItems(), HttpStatus.OK);
    }

    public ResponseEntity<List<ItemPicture>> getAllItemPicturesForItem(Long id, String urlOnly) {
        return new ResponseEntity<>(itemRepository.findAllItemPicturesForItem(id, urlOnly),
                                    HttpStatus.OK);
    }

//    public ResponseEntity<String> createItemPicturesForItem(Long id, MultipartFile[] files){
//        return new ResponseEntity<>(itemRepository.createItemPicturesForItem(id, files),
//                                    HttpStatus.OK);
//    }

    public ResponseEntity<ItemPicture> createItemPictureForItem(Long id, MultipartFile file){
        return new ResponseEntity<ItemPicture>(itemRepository.createItemPictureForItem(id, file), HttpStatus.OK);
    }
}