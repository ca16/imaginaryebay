package com.imaginaryebay.Controller;

import com.imaginaryebay.DAO.ItemPictureDAO;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Repository.ItemRepository;
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
    public void save(Item item) {
        this.itemRepository.save(item);
    }

    @Override
    public Item getItemByID(Long id){
        return this.itemRepository.findByID(id);
    }

    @Override
    public Double getPriceByID(Long id){
        return this.itemRepository.findPriceByID(id);
    }
/*
    @Override
    public Userr getOwnerByID(Long id){
        return this.itemRepository.findOwnerByID(id);
    }
*/
    @Override
    public Category getCategoryByID(Long id){
        return this.itemRepository.findCategoryByID(id);
    }

    @Override
    public String getDescriptionByID(Long id){
        return this.itemRepository.findDescriptionByID(id);
    }

    @Override
    public Timestamp getEndtimeByID(Long id){
        return this.itemRepository.findEndtimeByID(id);
    }

    @Override
    public Item updateItemByID(Long id, Item item){
        return this.itemRepository.updateItemByID(id, item);
    }

    @Override
    public List<Item> getAllItems(Category cat){
        if (null != cat){
            return this.itemRepository.findAllItemsByCategory(cat);
        }
        return this.itemRepository.findAllItems();
    }

//    public ResponseEntity<List<ItemPicture>> returnItemPicturesForItem(Long id, String urlOnly) {
//        return new ResponseEntity<>(itemRepository.returnItemPicturesForItem(id, urlOnly),
//                                    HttpStatus.OK);
//    }

    public List<ItemPicture> returnItemPicturesForItem(Long id, String urlOnly) {
        return itemRepository.returnItemPicturesForItem(id, urlOnly);
    }

//    public ResponseEntity<String> createItemPicturesForItem(Long id, MultipartFile[] files){
//        return new ResponseEntity<>(itemRepository.createItemPicturesForItem(id, files),
//                                    HttpStatus.OK);
//    }

    public ItemPicture createItemPictureForItem(Long id, MultipartFile file){
        return itemRepository.createItemPictureForItem(id, file);
    }
}