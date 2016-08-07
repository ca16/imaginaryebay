package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.ItemRepository;
import com.sun.mail.iap.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chloe on 6/23/16.
 */
@RestController
@RequestMapping("/item")
public interface ItemController {

    /**
     * Saves the given Item
     * @param item the item to be saved
     *
     * Example:
     * curl -X POST -H "Content-Type: application/json" -d '{ "description" : "watch", "endtime":"2017-04-04T00:00:00", "category": "Electronics", "price": "20.0"}' "http://localhost:8080/item"
     *
     */
    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Item> save(@RequestBody Item item);

    /**
     * @param id the item's ID
     * @return the Item with the given ID
     *
     * Example:
     * curl -X GET localhost:8080/item/1
     */
    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<Item> getItemByID(@PathVariable("id") Long id);

    /**
     * returns the categories of items the seller with a given id sells
     * @param id the seller's id
     * @return the categories of items the given seller sells
     *
     * Example:
     * curl -X GET localhost:8080/item/sellercategories/1
     */
    @RequestMapping(value="/sellercategories/{id}", method= RequestMethod.GET)
    public ResponseEntity<List<Category>> getSellerCategoriesByID(@PathVariable("id") Long id);

    /**
     * @param id the ID of the Item to be updated
     * @param item the Item it is being updated to
     * @return the updated item
     *
     * Example:
     * curl -X PUT -H "Content-Type: application/json" -d '{ "description" : "notwatch", "endtime":"2017-04-04T00:00:00", "category": "Electronics", "price": "20.0"}' "http://localhost:8080/item/1"
     */
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<Item> updateItemByID(@PathVariable("id") Long id, @RequestBody Item item);

    /**
     *
     * @param id - The ItemID representing the auction for which images are being requested.
     * @param urlOnly - [True|true] | [False|false] Controls whether the associated Item is sent with the response
     * @return - Returns an HTTP response with the ItemPicture id, URL, and optionally the associated Item
     *
     * EXAMPLE - curl -X GET localhost:8080/item/1/picture
     */
    @RequestMapping(value = "/{id}/picture", method = RequestMethod.GET)
    public ResponseEntity<List<ItemPicture>>
    getAllItemPicturesForItem(@PathVariable("id")
                                      Long id,
                              @RequestParam(value = "urlOnly", required = false, defaultValue = "false")
                                      String urlOnly
    );


    /**
     *
     * @param id - The ItemID representing the auction this picture is uploaded for.
     * @param file - An image file to be uploaded.
     * @return - Returns an HTTP Response containing the status and string representing the
     *           uploaded image's URL.
     *
     * EXAMPLE : curl -X POST -F "file=@/path/to/image.jpg" localhost:8080/item/1/picture
     */
    @RequestMapping(value = "/{id}/picture", method = RequestMethod.POST)
    public ResponseEntity<ItemPicture> createItemPictureForItem(@PathVariable("id") Long id,
                                                           @RequestParam("file") MultipartFile file);


    /**
     * Returns a list of all items if no category or name is specified.
     * If only category is specified returns a list of all items of that category.
     * If only keyword is specified returns a list of all items whose name matches or partially matches the given name.
     * @param cat the category of items we want a list for
     * @param keyword keyword to search by
     * @return the list of items that fits the input
     *
     * Example:
     * curl -X GET localhost:8080/item
     * curl -X GET localhost:8080/item?cat=Clothes
     * curl -X GET localhost:8080/item?keyword=card
     * curl -X GET "localhost:8080/item?cat=Clothes&keyword=card"
     * curl -X GET localhost:8080/item?sellerID=2
     * curl -X GET "localhost:8080/item?cat=Clothes&sellerID=2"
     * (with two parameters, make sure the  " " are there if you're using curl)
     */
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Item>> getAllItems(@RequestParam(value = "cat", required = false) String cat, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "sellerID", required = false) Long sellerID);


    /*
     * Example:
     * curl -X GET localhost:8080/item/page/1/size/2
     * curl -X GET localhost:8080/item/page/1/size/3?cat=Clothes
     * curl -X GET localhost:8080/item/page/2/size/10?keyword=card
     * curl -X GET "localhost:8080/item/page/2/size/2?cat=Clothes&keyword=card"
     * curl -X GET localhost:8080/item/page/1/size/4?sellerID=2
     * curl -X GET "localhost:8080/item/page/2/size/1?cat=Clothes&sellerID=2"
     */
    @RequestMapping(value="/page/{page}/size/{size}",method= RequestMethod.GET)
    public ResponseEntity<List<Item>> findItemsBasedOnPage(@PathVariable("page") int pageNum, @PathVariable("size") int pageSize, @RequestParam(value = "cat", required = false) String cat, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "sellerID", required = false) Long sellerID);

    /*
     * curl -X GET localhost:8080/item/count
     * curl -X GET localhost:8080/item/count?cat=Clothes
     * curl -X GET localhost:8080/item/count?keyword=card
     * curl -X GET "localhost:8080/item/count?cat=Clothes&keyword=card"
     * curl -X GET localhost:8080/item/count?sellerID=2
     * curl -X GET "localhost:8080/item/count?cat=Clothes&sellerID=2"
     */
    @RequestMapping(value="/count",method= RequestMethod.GET)
    public ResponseEntity<Integer> findItemsWithCount(@RequestParam(value = "cat", required = false) String cat, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "sellerID", required = false) Long sellerID);

    /**
     *
     * @param id the item's ID
     * @return the highest bid for the item with the given ID
     *
     * Example:
     * curl -X GET localhost:8080/item/highestbid/1
     */
    @RequestMapping(value="/highestbid/{id}", method= RequestMethod.GET)
    public ResponseEntity<Double> getHighestBidByID(@PathVariable("id") Long id);


}


