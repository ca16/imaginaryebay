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

    private static final Logger logger = Logger.getLogger(ItemControllerImpl.class);
    private static final String FAIL_STEM = "Unable to upload.";
    private static final String FAIL_EMPTY_FILES = "Unable to upload. File is empty.";
    private static final String COLON_SEP = ": ";
    private static final String UNCAUGHT_EXCEPTION = "An uncaught exception was raised during upload.";
    private static final String NOT_AVAILABLE = "Not available.";
    private static final String NO_ENTRIES = "There are no entries for the requested resource.";
    private static final String INVALID_PARAMETER = "Invalid request parameter.";
    private static final String REQUIRED = "is required.";

    private ItemDAO itemDAO;

    @Autowired
    private UserrDao userrDao;

    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void setUserrDAO(UserrDao userr) {
        this.userrDao = userr;
    }

    public Item save(Item item) {
        // user must be logged in to create an item
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RestException(NOT_AVAILABLE, "You must be logged in to create an item.", HttpStatus.UNAUTHORIZED);
        }

        // price is required
        if (null == item.getPrice()) {
            throw new RestException("No price.", "Price " + REQUIRED, HttpStatus.BAD_REQUEST);
        }

        // price must be positive
        if (!(item.getPrice() > 0)) {
            throw new RestException("Invalid price", "Price must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        // keyword is required
        if (null == item.getName()) {
            throw new RestException("No keyword.", "Name " + REQUIRED, HttpStatus.BAD_REQUEST);
        }

        // auction end time is required
        if (null == item.getEndtime()) {
            throw new RestException("No auction end time.", "Auction end time " + REQUIRED, HttpStatus.BAD_REQUEST);
        }

        // auction end time must be after the current time
        if (item.getEndtime().before(new Timestamp(System.currentTimeMillis()))) {
            throw new RestException("Invalid endtime", "Auction must end in the future", HttpStatus.BAD_REQUEST);
        }

        // if category is specified, it must be a valid category
        if ((null != item.getCategory()) && item.getCategory().equals(Category.Invalid)) {
            throw new RestException("Invalid category", validCategories(), HttpStatus.BAD_REQUEST);
        }

        // the item belongs to the user who is logged in at the time of creation
        String email = auth.getName();
        Userr owner = userrDao.getUserrByEmail(email);
        item.setUserr(owner);

        this.itemDAO.persist(item);
        return item;
    }

    /**
     * TODO: RestExceptions should also provide HttpStatus Codes as arguments to the constructor.
     */

    public void update(Item item) {
        // can't update an item that doesn't exist
        if (this.itemDAO.find(item) == null) {
            throw new RestException(NOT_AVAILABLE,
                    "Item to be updated does not exist.", HttpStatus.BAD_REQUEST);
        }

        // can't change the price to a non-positive price
        if ((null != item.getPrice()) && !(item.getPrice() > 0)) {
            throw new RestException("Invalid price", "Price must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        // can't update the category to an invalid category
        if ((null != item.getCategory()) && item.getCategory().equals(Category.Invalid)) {
            throw new RestException("Invalid category", validCategories(), HttpStatus.BAD_REQUEST);
        }

        // can't change the auction end time to before the current time
        if ((null != item.getEndtime()) && ((item.getEndtime().before(new Timestamp(System.currentTimeMillis()))))) {
            throw new RestException("Invalid endtime", "Auction must end in the future", HttpStatus.BAD_REQUEST);
        }

        this.itemDAO.merge(item);
    }


    public Item findByID(Long id) {
        Item toRet = this.itemDAO.findByID(id);
        if (toRet == null) {
            // what should the number be here?
            logger.error("Item not found!", new RestException("Item not found.", "Item with id " + id + " was not found"));

            // item with the given id doesn't exist
            throw new RestException(NOT_AVAILABLE,
                    detailedMessageConstructor(id), HttpStatus.OK);
        } else {
            return toRet;
        }
    }

    public Userr findOwnerByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            // No check for whether owner is null. All items should have an owner.
            return itemDAO.findOwnerByID(id);
        }

        // non-existent items don't have owners
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public String findNameByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            // No check for whether keyword is null. All items should have an keyword.
            return itemDAO.findNameByID(id);
        }

        // non-existent items don't have names
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public Double findPriceByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            // No check for whether price is null. All items should have an price.
            return itemDAO.findPriceByID(id);
        }

        // non-existent items don't have prices
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public Timestamp findEndtimeByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            // No check for whether end time is null. All items should have an end time.
            return itemDAO.findEndtimeByID(id);
        }

        // non-existent items don't have end times
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public Category findCategoryByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            Category cat = itemDAO.findCategoryByID(id);
            if (cat != null) {
                return cat;
            }
            // some items don't have categories
            throw new RestException(NOT_AVAILABLE,
                    detailedMessageConstructor(id, " does not have a category"), HttpStatus.OK);

        }

        // non-existent items don't have categories
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public Double findHighestBidByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            Double highestBid = itemDAO.findHighestBidByID(id);
            if (highestBid != null) {
                return highestBid;
            }
            // some items don't have highest bids
            throw new RestException(NOT_AVAILABLE,
                    detailedMessageConstructor(id, " does not have a highest bid."), HttpStatus.OK);
        }
        // non-existent items don't have highest bids
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
            // some items don't have descriptions
            throw new RestException(NOT_AVAILABLE,
                    detailedMessageConstructor(id, " does not have a description"), HttpStatus.OK);

        }
        // non-existent items don't have descriptions
        throw new RestException(NOT_AVAILABLE,
                detailedMessageConstructor(id), HttpStatus.BAD_REQUEST);
    }

    public Item updateItemByID(Long id, Item item) {
        Item toUpdate = this.itemDAO.findByID(id);

        // can't update non-existent items
        if (id == null || toUpdate == null) {
            throw new RestException(NOT_AVAILABLE,
                    "Item to be updated does not exist.", HttpStatus.BAD_REQUEST);
        }

        // only a logged in user can edit an item
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RestException(NOT_AVAILABLE, "You must be logged in to edit an item.", HttpStatus.UNAUTHORIZED);
        }

        // only the owner of an item can update the item
        String email = auth.getName();
        if (!toUpdate.getUserr().getEmail().equals(email)) {
            throw new RestException(NOT_AVAILABLE, "You can only update items you own.", HttpStatus.FORBIDDEN);
        }

        // keyword isn't updated if no new keyword is given
        if (null == item.getName()) {
            item.setName(toUpdate.getName());
        }

        // price isn't updated if no new price is given
        if (null == item.getPrice()) {
            item.setPrice((toUpdate.getPrice()));
        }

        // price can't be updated to an invalid price
        else if (!(item.getPrice() > 0)) {
            throw new RestException("Invalid price", "Price must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        // category cannot be updated to an invalid Category
        if ((null != item.getCategory()) && (item.getCategory().equals(Category.Invalid))) {
            throw new RestException("Invalid category", validCategories(), HttpStatus.BAD_REQUEST);
        }

        // auction end time isn't updated if no new end time is given
        if (null == item.getEndtime()) {
            item.setEndtime(toUpdate.getEndtime());
        }

        // auction end time cannot be updated to an invalid end time
        else if (item.getEndtime().before(new Timestamp(System.currentTimeMillis()))) {
            throw new RestException("Invalid endtime", "Auction must end in the future", HttpStatus.BAD_REQUEST);
        }

        // description isn't updated if no new description is given
        if (null == item.getDescription()) {
            item.setDescription(toUpdate.getDescription());
        }

        return itemDAO.updateItemByID(id, item);
    }

    public List<Category> findSellerCategories(Long ownerId){
        return itemDAO.findSellerCategories(ownerId);
    }

    public List<ItemPicture> findAllItemPicturesForItem(Long id, String urlOnly) {

        List<ItemPicture> itemPictures;

        if (urlOnly.equalsIgnoreCase("true")) {
            itemPictures = itemDAO.findAllItemPictureURLsForItemID(id);
        } else if (urlOnly.equalsIgnoreCase("false")) {
            itemPictures = itemDAO.findAllItemPicturesForItemID(id);
        } else {
            throw new RestException(INVALID_PARAMETER,
                    "The supplied request parameter \"" + urlOnly + "\" is invalid for this URL.",
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
        } else {
            throw new RestException(FAIL_STEM, FAIL_EMPTY_FILES, HttpStatus.BAD_REQUEST);
        }
    }

    public String createItemPicturesForItem(Long id, MultipartFile[] files) {

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

    private ItemPicture uploadAndReturnItemPictureForItem(Item item, MultipartFile file) {
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

    //////////////////////////////////////
    // Item Searches /////////////////////
    //////////////////////////////////////

    public List<Item> findAllItems() {
        List<Item> toRet = this.itemDAO.findAllItems();
        if (!toRet.isEmpty()) {
            return toRet;
        }

        // if list is empty, there are no items
        throw new RestException(NOT_AVAILABLE,
                "There are no items available.", HttpStatus.OK);

    }

    public List<Item> findItemsByKeyword(String keyword){
        List<Item> toRet = itemDAO.findItemsByKeyword(keyword);
        if (!toRet.isEmpty()){
            return toRet;
        }

        // if the list is empty, no items that meet the criteria exist
        // criteria: item keyword or category keyword must match or partially match the given keyword
        throw new RestException(NOT_AVAILABLE, "Items with keyword or category similar to " + keyword + " were not found", HttpStatus.OK);
    }


    public List<Item> findAllItemsByCategory(String category) {
        Category cat = categoryValidation(category);

        List<Item> toRet = this.itemDAO.findAllItemsByCategory(cat);
        if (!toRet.isEmpty()) {
            return toRet;
        }

        // if list is empty, there are no items of the category
        throw new RestException(NOT_AVAILABLE,
                "Items of category " + category + " were not found", HttpStatus.OK);
    }

    public List<Item> findItemsBySeller(Long id){
        sellerValidation(id);
        List<Item> toRet = itemDAO.findItemsBySeller(id);
        if (toRet == null){
            throw new RestException(NOT_AVAILABLE,
                    "User with id " + id + " has no items to sell.", HttpStatus.OK);
        }
        return toRet;
    }


    public List<Item> findItemsByCategoryAndKeyword(String category, String keyword){
        Category cat = categoryValidation(category);
        List<Item> toRet = this.itemDAO.findItemsByCategoryAndKeyword(cat, keyword);
        if (!toRet.isEmpty()) {
            return toRet;
        }

        // if the list is empty, no items that meet the criteria exist
        // criteria: item keyword or category keyword must match or partially match the given keyword
        // and the item must be of the given category
        throw new RestException(NOT_AVAILABLE,
                "Items of category " + category + " and keyword or category similar to " + keyword + " were not found", HttpStatus.OK);

    }

    public List<Item> findItemsByCategoryAndSeller(String category, Long sellerID){
        Category cat = categoryValidation(category);
        sellerValidation(sellerID);
        List<Item> toRet = this.itemDAO.findItemsByCategoryAndSeller(cat, sellerID);
        if (!toRet.isEmpty()) {
            return toRet;
        }

        // if the list is empty, no items that meet the criteria exist
        throw new RestException(NOT_AVAILABLE,
                "Items of category " + category + " and sold by user with id " + sellerID + " were not found", HttpStatus.OK);

    }

    //////////////////////////////////////
    // Searches w Pagination /////////////
    //////////////////////////////////////

    public List<Item> findItemsBasedOnPage(int pageNum, int pageSize) {
        return itemDAO.findItemsBasedOnPage(pageNum, pageSize);
    }

    public List<Item> findItemsByKeywordBasedOnPage(String keyword, int pageNum, int pageSize){
        List<Item> toRet = itemDAO.findItemsByKeywordBasedOnPage(keyword, pageNum, pageSize);
        return toRet;

    }

    public List<Item> findItemsByCategoryBasedOnPage(String category, int pageNum, int pageSize){
        Category cat = categoryValidation(category);
        List<Item> toRet = this.itemDAO.findItemsByCategoryBasedOnPage(cat, pageNum, pageSize);
        return toRet;
    }

    public List<Item> findItemsBySellerBasedOnPage(Long sellerID, int pageNum, int pageSize){
        sellerValidation(sellerID);
        List<Item> toRet = this.itemDAO.findItemsBySellerBasedOnPage(sellerID, pageNum, pageSize);
        return toRet;
    }

    public List<Item> findItemsByKeywordAndCategoryBasedOnPage(String category, String keyword, int pageNum, int pageSize){
        Category cat = categoryValidation(category);
        List<Item> toRet = itemDAO.findItemsByCategoryAndKeywordBasedOnPage(cat, keyword, pageNum, pageSize);
        return toRet;
    }

    public List<Item> findItemsByCategoryAndSellerBasedOnPage(String category, Long sellerID, int pageNum, int pageSize){
        Category cat = categoryValidation(category);
        sellerValidation(sellerID);
        List<Item> toRet = this.itemDAO.findItemsByCategoryAndSellerBasedOnPage(cat, sellerID, pageNum, pageSize);
        return toRet;
    }

    //////////////////////////////////////
    // Helpers ///////////////////////////
    //////////////////////////////////////

    /**
     * returns a message telling the user that the item with the given ID wasn't found
     * @param id the item's id
     * @return an informative message
     */
    private String detailedMessageConstructor(Long id) {
        return "Item with id " + id + " was not found";
    }

    /**
     * returns a message telling the user what the problem was with finding the information requested
     * about the item with the given id
     * @param id the item's id
     * @param extras the problem with finding the information
     * @return an informative message
     */
    private String detailedMessageConstructor(Long id, String extras) {
        return "Item with id " + id + extras;
    }

    /**
     * tells the user what the valid categories are
     * @return valid category names
     */
    private String validCategories(){
        String toRet = "Valid category names are: ";
        for (Category cat: Category.values()){
            if (cat != Category.Invalid){
                toRet = toRet + cat.toString() + ", ";
            }
        }
        toRet = toRet.substring(0, toRet.length()-2);
        return toRet + ".";
    }

    private Category categoryValidation(String category){
        Category cat;
        try {
            cat = Category.valueOf(category);
        } catch (IllegalArgumentException exc) {
            // Should we also give them a list of options?
            throw new RestException(INVALID_PARAMETER, validCategories(), HttpStatus.BAD_REQUEST);
        }
        return cat;
    }

    private void sellerValidation(Long sellerID){
        if (userrDao.getUserrByID(sellerID) == null){
            throw new RestException(NOT_AVAILABLE,
                    "User with id " + sellerID + " does not exist.", HttpStatus.BAD_REQUEST);
        }
    }

//    private List<Item> trim(List<Item> toTrim, int pageNum, int pageSize){
//        if (toTrim.size() >= (pageNum * pageSize)){
//            return toTrim.subList((pageNum-1)*pageSize, ((pageNum)*pageSize));
//        }
//        else if ((toTrim.size() < (pageNum * pageSize)) && (toTrim.size() > ((pageNum-1) * pageSize))){
//            return toTrim.subList((pageNum-1)*pageSize, toTrim.size());
//        }
//
//        else {
//            return new ArrayList<>();
//        }
//    }


}
