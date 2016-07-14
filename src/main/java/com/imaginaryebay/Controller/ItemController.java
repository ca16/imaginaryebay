package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
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
     */
    @RequestMapping(method= RequestMethod.POST)
    public void save(@RequestBody Item item);

    /**
     * @param id the item's ID
     * @return the Item with the given ID
     */
    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<Item> getItemByID(@PathVariable("id") Long id);
/*
    @RequestMapping(value="/Owner/{ID}", method= RequestMethod.GET)
    public Userr getOwnerByID(@PathVariable("ID") Long id);
*/

    /**
     * @param id the item's ID
     * @return the price of the item with the given ID
     */
    @RequestMapping(value="/price/{id}", method= RequestMethod.GET)
    public ResponseEntity<Double> getPriceByID(@PathVariable("id") Long id);

    /**
     * @param id the item's ID
     * @return category of the item with the given ID
     */
    @RequestMapping(value="/category/{id}", method= RequestMethod.GET)
    public ResponseEntity<Category> getCategoryByID(@PathVariable("id") Long id);

    /**
     * @param id the item's ID
     * @return the description of the item with the given ID
     */
    @RequestMapping(value="/description/{id}", method= RequestMethod.GET)
    public ResponseEntity<String> getDescriptionByID(@PathVariable("id") Long id);

    /**
     * @param id the item's ID
     * @return the endtime of the auction for the item with the given ID
     */
    @RequestMapping(value="/endtime/{id}", method= RequestMethod.GET)
    public ResponseEntity<Timestamp> getEndtimeByID(@PathVariable("id") Long id);

    /**
     * @param id the ID of the Item to be updated
     * @param item the Item it is being updated to
     * @return the updated item
     */
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<Item> updateItemByID(@PathVariable("id") Long id, @RequestBody Item item);

    /**
     * Returns a list of all items if no category is specified. If category is specified
     * returns a list of all items of that category.
     * @param cat the category of items we want a list for
     * @return the list of items that fits the input
     */
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Item>> getAllItems(@RequestParam(value = "cat", required = false) Category cat);

    /**
     *
     * @param id - The ItemID representing the auction for which images are being requested.
     * @param urlOnly - [True|true] | [False|false] Controls whether the associated Item is sent with the response
     * @return - Returns an HTTP response with the ItemPicture id, URL, and optionally the associated Item
     *
     * EXAMPLE - curl -X GET localhose:8080/item/1/picture
     */
    @RequestMapping(value = "/{id}/picture", method = RequestMethod.GET)
    public ResponseEntity<List<ItemPicture>>
    returnItemPicturesForItem(@PathVariable("id")
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
}


