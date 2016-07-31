package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.BiddingRepository;
import com.imaginaryebay.Repository.ItemRepository;
import com.imaginaryebay.Repository.UserrRepository;
import com.sun.mail.iap.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by ben on 7/25/16.
 */
public class BiddingControllerImpl implements BiddingController {


    @Autowired
    BiddingRepository biddingRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserrRepository userrRepository;

    public void setBiddingRepository(BiddingRepository brepo) { this.biddingRepository = brepo; }

    public void setItemRepository(ItemRepository irepo) { this.itemRepository = irepo; }

    public void setUserrRepository(UserrRepository urepo) { this.userrRepository = urepo; }



    @Override
    //public void createNewBidding(Long userrID, Long itemID, Double price){
    public ResponseEntity<Void> createNewBidding(Long itemID, Double price){
        //Userr userr=userrRepository.getUserrByID(userrID);
        //ToDo: what happens when the userr does not exist
        //ToDo: check the userrID is the one that has logged in
        //Item item=itemRepository.findByID(itemID);
        //ToDo: what happens when the item does not exist
        //biddingRepository.createNewBidding(userr,item,price);
        //biddingRepository.createNewBidding(item, price);
        biddingRepository.createNewBidding(itemID, price);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Bidding> getBiddingByID (Long id){
        return new ResponseEntity(biddingRepository.getBiddingByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Bidding>> getBiddingByUserrID (Long id){
        return new ResponseEntity<>(biddingRepository.getBiddingByUserrID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Bidding>> getBiddingByItem (Long id){
//        Item item=itemRepository.findByID(id);
//        return new ResponseEntity<>(biddingRepository.getBiddingByItem(item), HttpStatus.OK);
        return new ResponseEntity<List<Bidding>>(biddingRepository.getBiddingByItemID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Bidding> getHighestBiddingForItem (Long id){
        return new ResponseEntity<>(biddingRepository.getHighestBiddingForItem(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Item>> getActiveItemsByBidder (Long bidderID){
        return new ResponseEntity<>(biddingRepository.getActiveBidItemsByBidder(bidderID), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Item>> getSuccessfulAuctionItemsByBidder (Long bidderID){
        return new ResponseEntity<>(biddingRepository.getSuccessfulBidItemsByBidder(bidderID), HttpStatus.OK);
    }

}
