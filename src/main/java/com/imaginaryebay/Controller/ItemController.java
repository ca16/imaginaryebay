package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.ItemRepository;

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
     *
     * @param id the item's ID
     * @return the owner of the item with the given ID
     *
     * Example:
     * curl -X GET localhost:8080/item/owner/1
     *
     */
    @RequestMapping(value="/owner/{id}", method= RequestMethod.GET)
    public ResponseEntity<Userr> getOwnerByID(@PathVariable("id") Long id);

    /**
     *
     * @param id the item's ID
     * @return the name of the item with the given ID
     *
     * Example:
     * curl -X GET localhost:8080/item/name/1
     */
    @RequestMapping(value="/name/{id}", method= RequestMethod.GET)
    public ResponseEntity<String> getNameByID(@PathVariable("id") Long id);

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


    /**
     * @param id the item's ID
     * @return the price of the item with the given ID
     *
     * Example:
     * curl -X GET localhost:8080/item/price/1
     */
    @RequestMapping(value="/price/{id}", method= RequestMethod.GET)
    public ResponseEntity<Double> getPriceByID(@PathVariable("id") Long id);

    /**
     * @param id the item's ID
     * @return category of the item with the given ID
     *
     * Example:
     * curl -X GET localhost:8080/item/category/1
     */
    @RequestMapping(value="/category/{id}", method= RequestMethod.GET)
    public ResponseEntity<Category> getCategoryByID(@PathVariable("id") Long id);

    /**
     * @param id the item's ID
     * @return the description of the item with the given ID
     *
     * Example:
     * curl -X GET localhost:8080/item/description/1
     */
    @RequestMapping(value="/description/{id}", method= RequestMethod.GET)
    public ResponseEntity<String> getDescriptionByID(@PathVariable("id") Long id);

    /**
     * @param id the item's ID
     * @return the endtime of the auction for the item with the given ID
     *
     * Example:
     * curl -X GET localhost:8080/item/endtime/1
     */
    @RequestMapping(value="/endtime/{id}", method= RequestMethod.GET)
    public ResponseEntity<Timestamp> getEndtimeByID(@PathVariable("id") Long id);

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
     * Returns a list of all items if no category is specified. If category is specified
     * returns a list of all items of that category.
     * @param cat the category of items we want a list for
     * @return the list of items that fits the input
     *
     * Example:
     * curl -X GET localhost:8080/item
     * curl -X GET localhost:8080/item?cat=Clothes
     */
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Item>> getAllItems(@RequestParam(value = "cat", required = false) String cat);

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



    @RequestMapping(value="/page/{page}/size/{size}",method= RequestMethod.GET)
    public ResponseEntity<List<Item>> findItemsBasedOnPage(@PathVariable("page") int pageNum, @PathVariable("size") int pageSize);


    @RequestMapping(value="/totalCount",method = RequestMethod.GET)
    public ResponseEntity<Long> findTotalNumOfItems();


}


