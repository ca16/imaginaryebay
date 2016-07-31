package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ben on 7/25/16.
 */

@RestController
@RequestMapping("/bidding")

public interface BiddingController {

    //@RequestMapping (value="/userID/{userID}/itemID/{itemID}/price/{price}", method = RequestMethod.POST)
    @RequestMapping (value="/itemID/{itemID}/price/{price}", method = RequestMethod.POST)
    //public void createNewBidding(@PathVariable("userID") Long userrID, @PathVariable("itemID") Long itemID, @PathVariable("price") Double price);
    public ResponseEntity<Void> createNewBidding(@PathVariable("itemID") Long itemID, @PathVariable("price") Double price);

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ResponseEntity<Bidding> getBiddingByID (@PathVariable("id") Long id);

    @RequestMapping(value="/userID/{userID}",method = RequestMethod.GET)
    public ResponseEntity<List<Bidding>> getBiddingByUserrID (@PathVariable("userID")  Long id);

    @RequestMapping(value="/itemID/{itemID}",method = RequestMethod.GET)
    public ResponseEntity<List<Bidding>> getBiddingByItem (@PathVariable("itemID") Long id);

    @RequestMapping(value="/highest/itemID/{itemID}",method = RequestMethod.GET)
    public ResponseEntity<Bidding> getHighestBiddingForItem (@PathVariable("itemID") Long id);

    @RequestMapping(value="/active/{bidderID}",method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getActiveItemsByBidder (@PathVariable("bidderID") Long bidderID);

    @RequestMapping(value="/successful/{bidderID}",method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getSuccessfulAuctionItemsByBidder (@PathVariable("bidderID") Long bidderID);

    @RequestMapping(value="/active/{bidderID}/count",method = RequestMethod.GET)
    public ResponseEntity<Integer> getCountOfActiveItemsByBidder (@PathVariable("bidderID") Long bidderID);

    @RequestMapping(value="/successful/{bidderID}/count",method = RequestMethod.GET)
    public ResponseEntity<Integer> getCountOfSuccessfulAuctionItemsByBidder (@PathVariable("bidderID") Long bidderID);

    @RequestMapping(value="/active/{bidderID}/page/{page}/size/{size}",method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getActiveItemsByBidderByPage (@PathVariable("bidderID") Long bidderID, @PathVariable("page") int pageNum, @PathVariable("size") int pageSize);

    @RequestMapping(value="/successful/{bidderID}/page/{page}/size/{size}",method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getSuccessfulAuctionItemsByBidderByPage (@PathVariable("bidderID") Long bidderID, @PathVariable("page") int pageNum, @PathVariable("size") int pageSize);
}
