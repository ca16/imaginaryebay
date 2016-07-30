package com.imaginaryebay.Repository;

import com.amazonaws.services.lambda.model.UnsupportedMediaTypeException;
import com.imaginaryebay.Controller.ItemControllerImpl;
import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chloe on 6/28/16.
 */
@Transactional
public class ItemRepositoryImpl implements ItemRepository {

    private static final Logger logger              = Logger.getLogger(ItemControllerImpl.class);
    private static final String FAIL_STEM           = "Unable to upload.";
    private static final String FAIL_EMPTY_FILES    = "Unable to upload. File is empty.";
    private static final String COLON_SEP           = ": ";
    private static final String UNCAUGHT_EXCEPTION  = "An uncaught exception was raised during upload.";
    private static final String NOT_AVAILABLE       = "Not available.";
    private static final String NO_ENTRIES          = "There are no entries for the requested resource.";
    private static final String INVALID_PARAMETER   = "Invalid request parameter.";
    private static final String REQUIRED            = "is required.";

    private ItemDAO itemDAO;

    @Autowired
    private UserrDao userrDao;

    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void setUserrDAO(UserrDao userr) {this.userrDao = userr;}

    public Item save(Item item) {

        if (null == item.getPrice()){
            throw new RestException("No price.", "Price " + REQUIRED, HttpStatus.BAD_REQUEST);
        }

        if (!(item.getPrice() > 0)){
            throw new RestException("Invalid price", "Price must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        if (null == item.getName()){
            throw new RestException("No name.", "Name " + REQUIRED, HttpStatus.BAD_REQUEST);
        }

        if (null == item.getEndtime()){
            throw new RestException("No auction end time.", "Auction end time " + REQUIRED, HttpStatus.BAD_REQUEST);
        }

        if (item.getEndtime().before(new Timestamp(System.currentTimeMillis()))){
            throw new RestException("Invalid endtime", "Auction must end in the future", HttpStatus.BAD_REQUEST);
        }

        if ((null != item.getCategory()) && item.getCategory().equals(Category.Invalid)){
            throw new RestException("Invalid category", "Valid Categories are: Clothes & Electronics.", HttpStatus.BAD_REQUEST);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Userr owner = userrDao.getUserrByEmail(email);
        item.setUserr(owner);

        this.itemDAO.persist(item);
        return item;
    }

    /** TODO: RestExceptions should also provide HttpStatus Codes as arguments to the constructor. */

    public void update(Item item) {
        if (this.itemDAO.find(item) == null) {
            throw new RestException(NOT_AVAILABLE,
                    "Item to be updated does not exist.", HttpStatus.BAD_REQUEST);
        }
        if ((null != item.getPrice()) && !(item.getPrice() > 0)){
            throw new RestException("Invalid price", "Price must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        if ((null != item.getCategory()) && item.getCategory().equals(Category.Invalid)){
            throw new RestException("Invalid category", "Valid Categories are: Clothes & Electronics.", HttpStatus.BAD_REQUEST);
        }

        if ((null != item.getEndtime()) && ((item.getEndtime().before(new Timestamp(System.currentTimeMillis()))))){
            throw new RestException("Invalid endtime", "Auction must end in the future", HttpStatus.BAD_REQUEST);
        }

        this.itemDAO.merge(item);
    }


    public Item findByID(Long id) {
        Item toRet = this.itemDAO.findByID(id);
        if (toRet == null) {
            // what should the number be here?
            logger.error("Item not found!", new RestException("Item not found.", "Item with id " + id + " was not found"));
            throw new RestException(NOT_AVAILABLE,
                    detailedMessageConstructor(id), HttpStatus.OK);
        } else {
            return toRet;
        }
    }

    public Userr findOwnerByID(Long id){
        Item item = this.itemDAO.findByID(id);
        if (item != null){
            // No check for whether owner is null. All items should have an owner.
            return itemDAO.findOwnerByID(id);
        }
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public String findNameByID(Long id){
        Item item = this.itemDAO.findByID(id);
        if (item != null){
            // No check for whether name is null. All items should have an name.
            return itemDAO.findNameByID(id);
        }
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public Double findPriceByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            // No check for whether name is null. All items should have an name.
            return itemDAO.findPriceByID(id);
        }
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public Timestamp findEndtimeByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            // No check for whether name is null. All items should have an name.
            return itemDAO.findEndtimeByID(id);
        }
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    // Same comment as for price, is category required?
    public Category findCategoryByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            Category cat = itemDAO.findCategoryByID(id);
            if (cat != null) {
                return cat;
            }
            throw new RestException(NOT_AVAILABLE,
                    detailedMessageConstructor(id, " does not have a category"), HttpStatus.OK);

        }
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public Double findHighestBidByID(Long id){
        Item item = this.itemDAO.findByID(id);
        if (item != null){
            Double highestBid = itemDAO.findHighestBidByID(id);
            if (highestBid != null){
                return highestBid;
            }
            throw new RestException(NOT_AVAILABLE,
                    detailedMessageConstructor(id, " does not have a highest bid."), HttpStatus.OK);
        }
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public String findDescriptionByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            String description = itemDAO.findDescriptionByID(id);
            if (description != null) {
                return description;
            }
            throw new RestException(NOT_AVAILABLE,
                    detailedMessageConstructor(id, " does not have a description"), HttpStatus.OK);

        }
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public Item updateItemByID(Long id, Item item) {
        Item toUpdate = this.itemDAO.findByID(id);
        if (id == null || toUpdate == null) {
            throw new RestException(NOT_AVAILABLE,
                "Item to be updated does not exist.", HttpStatus.BAD_REQUEST);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        if (!toUpdate.getUserr().getEmail().equals(email)){
            System.out.println(toUpdate.getUserr().getEmail());
            System.out.println(email);
            throw new RestException(NOT_AVAILABLE, "You can only update items you own.", HttpStatus.FORBIDDEN);
        }

        if ((null != item.getPrice()) && !(item.getPrice() > 0)){
            throw new RestException("Invalid price", "Price must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        if ((null != item.getCategory()) && item.getCategory().equals(Category.Invalid)){
            throw new RestException("Invalid category", "Valid Categories are: Clothes & Electronics.", HttpStatus.BAD_REQUEST);
        }

        if ((null != item.getEndtime()) && ((item.getEndtime().before(new Timestamp(System.currentTimeMillis()))))){
            throw new RestException("Invalid endtime", "Auction must end in the future", HttpStatus.BAD_REQUEST);
        }

        return itemDAO.updateItemByID(id, item);
    }

    public List<Item> findAllItemsByCategory(String category) {
        Category cat;
        try {
            cat = Category.valueOf(category);
        }catch (IllegalArgumentException exc){
            // Should we also give them a list of options?
            throw new RestException(INVALID_PARAMETER, category + " is not a valid Category name", HttpStatus.BAD_REQUEST);
        }
        List<Item> toRet = this.itemDAO.findAllItemsByCategory(cat);
        if (!toRet.isEmpty()) {
            return toRet;
        }
        throw new RestException(NOT_AVAILABLE,
                "Items of category " + category + " were not found", HttpStatus.OK);
    }

    public List<Item> findAllItems() {
        List<Item> toRet = this.itemDAO.findAllItems();
        if (!toRet.isEmpty()) {
            return toRet;
        }
        throw new RestException(NOT_AVAILABLE,
                "There are no items available.", HttpStatus.OK);

    }

    public List<ItemPicture> findAllItemPicturesForItem(Long id, String urlOnly){

        List<ItemPicture> itemPictures;

        if (urlOnly.equalsIgnoreCase("true")) {
            itemPictures = itemDAO.findAllItemPictureURLsForItemID(id);
        } else if (urlOnly.equalsIgnoreCase("false")) {
            itemPictures = itemDAO.findAllItemPicturesForItemID(id);
        } else {
            throw new RestException(INVALID_PARAMETER,
                    "The supplied request parameter \"" + urlOnly +  "\" is invalid for this URL.",
                    HttpStatus.BAD_REQUEST);
        }
        if (itemPictures.isEmpty()) {
            throw new RestException(NOT_AVAILABLE, NO_ENTRIES, HttpStatus.OK);
        }
        return itemPictures;
    }

    public ItemPicture createItemPictureForItem(Long id, MultipartFile file) {

        Item item = this.findByID(id);

        if (file != null && !file.isEmpty()) {
            return uploadAndReturnItemPictureForItem(item, file);
        }
        else {
            throw new RestException(FAIL_STEM, FAIL_EMPTY_FILES, HttpStatus.BAD_REQUEST);
        }
    }

    public String createItemPicturesForItem(Long id, MultipartFile[] files){

        String uploadResponse = "";
        Item item = this.findByID(id);

        if (files != null && files.length > 0) {
            for (MultipartFile mpFile : files) {
                if (!mpFile.isEmpty()) {
                    uploadResponse += uploadAndReturnItemPictureForItem(item, mpFile) + " ";
                }
            }
            return uploadResponse;
        } else {
            throw new RestException(FAIL_STEM, FAIL_EMPTY_FILES, HttpStatus.BAD_REQUEST);
        }
    }

    private ItemPicture uploadAndReturnItemPictureForItem(Item item, MultipartFile file){
        String uploadResponse;
        ItemPicture newPicture;
        try {
            S3FileUploader s3FileUploader = new S3FileUploader();
            uploadResponse = s3FileUploader.fileUploader(file);

            if (uploadResponse == null) {
                throw new RestException(FAIL_STEM, "", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                newPicture = new ItemPicture(uploadResponse);
                item.addItemPicture(newPicture);
                this.update(item);
            }
        } catch (UnsupportedMediaTypeException umtex) {
            umtex.printStackTrace();
            throw new RestException("Error with file upload to S3!",
                    umtex.getMessage(),
                    HttpStatus.BAD_REQUEST);
        } catch (IOException ioex) {
            ioex.printStackTrace();
            throw new RestException("Error with file upload to S3!",
                    ioex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RestException("An unexpected error occurred during file upload to S3!",
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return newPicture;
    }

    private String detailedMessageConstructor(Long id){
        return "Item with id " + id + " was not found";
    }

    private String detailedMessageConstructor(Long id, String extras){
        return "Item with id " + id + extras;
    }


    public List<Item> findItemsBasedOnPage(int pageNum, int pageSize){
        return itemDAO.findItemsBasedOnPage(pageNum,pageSize);
    }


}
