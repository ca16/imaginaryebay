package com.imaginaryebay.Controller;

import com.imaginaryebay.SendEmail;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    public ResponseEntity<Item> save(Item item) {
        Userr to = item.getUserr();
        String subject = item.getDescription() + " has sold!";
        String text = "Dear " + item.getUserr().getName() + ", your auction is now over. Your item has sold for " + item.getPrice() + ". Thank you for using our site.";
        Timestamp sendDate = item.getEndtime();
        TimerTask task=new SendEmail(to, subject, text, sendDate);
        Timer timer=new Timer();
        timer.schedule(task,item.getEndtime());
        System.out.println("Timer set!");
        return new ResponseEntity<>(this.itemRepository.save(item), HttpStatus.OK);
	}

    @Override
    public ResponseEntity<Item> getItemByID(Long id){
        return new ResponseEntity(this.itemRepository.findByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Double> getPriceByID(Long id){
        return new ResponseEntity(this.itemRepository.findPriceByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Userr> getOwnerByID(Long id){
        return new ResponseEntity(this.itemRepository.findOwnerByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getNameByID(Long id){
        return new ResponseEntity(this.itemRepository.findNameByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Double> getHighestBidByID(Long id){
        return new ResponseEntity(this.itemRepository.findHighestBidByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Category> getCategoryByID(Long id){
        return new ResponseEntity(this.itemRepository.findCategoryByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getDescriptionByID(Long id){
        return new ResponseEntity(this.itemRepository.findDescriptionByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Timestamp> getEndtimeByID(Long id){
        return new ResponseEntity(this.itemRepository.findEndtimeByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Item> updateItemByID(Long id, Item item){
        return new ResponseEntity(this.itemRepository.updateItemByID(id, item), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Item>> getAllItems(String cat){
        if (null != cat){
            return new ResponseEntity(this.itemRepository.findAllItemsByCategory(cat), HttpStatus.OK);
        }
        return new ResponseEntity(this.itemRepository.findAllItems(), HttpStatus.OK);
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

    public ResponseEntity<List<Item>> findItemsBasedOnPage(int pageNum,  int pageSize){
        return new ResponseEntity<List<Item>>(this.itemRepository.findItemsBasedOnPage(pageNum,pageSize),HttpStatus.OK);
    }


}