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
@RequestMapping("/Item")
public interface ItemController {

    @RequestMapping(method= RequestMethod.POST)
    public void save(@RequestBody Item item);

    @RequestMapping(value="/{ID}", method= RequestMethod.GET)
    public Item getItemByID(@PathVariable("ID") Long id);
/*
    @RequestMapping(value="/Owner/{ID}", method= RequestMethod.GET)
    public Userr getOwnerByID(@PathVariable("ID") Long id);
*/
    @RequestMapping(value="/Price/{ID}", method= RequestMethod.GET)
    public Double getPriceByID(@PathVariable("ID") Long id);

    @RequestMapping(value="/Category/{ID}", method= RequestMethod.GET)
    public Category getCategoryByID(@PathVariable("ID") Long id);

    @RequestMapping(value="/Description/{ID}", method= RequestMethod.GET)
    public String getDescriptionByID(@PathVariable("ID") Long id);

    @RequestMapping(value="/EndTime/{ID}", method= RequestMethod.GET)
    public Timestamp getEndtimeByID(@PathVariable("ID") Long id);

    @RequestMapping(value="/{ID}", method= RequestMethod.PUT)
    public Item updateItemByID(@PathVariable("ID") Long id, @RequestBody Item item);

    @RequestMapping(method= RequestMethod.GET)
    public List<Item> getAllItems(@RequestParam(value = "cat", required = false) Category cat);

    @RequestMapping(value = "/{id}/picture", method = RequestMethod.GET)
    public ResponseEntity<List<ItemPicture>>
    returnItemPicturesForItem(@PathVariable("id")
                                      Long id,
                              @RequestParam(value = "urlOnly", required = false, defaultValue = "false")
                                      String urlOnly
    );

    @RequestMapping(value = "/{id}/picture", method = RequestMethod.POST)
    public ResponseEntity<String> createItemPicturesForItem(@PathVariable("id") Long id,
                                                            @RequestParam("file") MultipartFile[] files);
}


