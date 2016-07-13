package com.imaginaryebay.Controller;

import com.imaginaryebay.DAO.ItemPictureDAO;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Models.S3FileUploader;
import com.imaginaryebay.Repository.ItemRepository;
import com.sun.mail.iap.Response;

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

    private static final String FAIL_STEM           = "Unable to upload.";
    private static final String FAIL_EMPTY_FILES    = "Unable to upload. File is empty.";
    private static final String COLON_SEP           = ": ";
    private static final String HTML_BREAK          = "</br>";

    @Override
    public void save(Item item) {
        this.itemRepository.save(item);
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
    public ResponseEntity<List<Item>> getAllItems(Category cat){
        if (null != cat){
            return new ResponseEntity<List<Item>>(this.itemRepository.findAllItemsByCategory(cat), HttpStatus.OK);
        }
        return new ResponseEntity<List<Item>>(this.itemRepository.findAllItems(), HttpStatus.OK);
    }

    public ResponseEntity<List<ItemPicture>> returnItemPicturesForItem(Long id, String urlOnly) {
        return itemRepository.returnItemPicturesForItem(id, urlOnly);
    }

    // TODO: Doesn't really qualify as a DAO. Should we place this in a class or leave here?
    public ResponseEntity<String> createItemPicturesForItem(Long id, MultipartFile[] files){

        String fileName = null;
        String imageURL = "";
        Item item = itemRepository.findByID(id);

        if (files != null && files.length > 0) {
            for (MultipartFile mpFile : files) {
                if (!mpFile.isEmpty()) {
                    try {
                        fileName = mpFile.getOriginalFilename();

                        S3FileUploader s3FileUploader = new S3FileUploader();
                        imageURL = s3FileUploader.fileUploader(mpFile);

                        if (imageURL == null) {
                            return new ResponseEntity<>(FAIL_STEM, HttpStatus.INTERNAL_SERVER_ERROR);
                        } else {
                            item.addItemPicture(new ItemPicture(imageURL));
                            itemRepository.update(item);
                        }

                    } catch (Exception e) {
                        String message = FAIL_STEM + fileName + COLON_SEP + e.getMessage() + HTML_BREAK;
                        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
            return new ResponseEntity<>(imageURL, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(FAIL_EMPTY_FILES, HttpStatus.BAD_REQUEST);
        }
    }
}