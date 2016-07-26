package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
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

    @RequestMapping (value="/userID/{userID}/itemID/{itemID}/price/{price}", method = RequestMethod.POST)
    public void createNewBidding(@PathVariable("userID") long userrID, @PathVariable("itemID") long itemID, @PathVariable("price") double price);

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Bidding getBiddingByID (@PathVariable("id")  Long id);

    @RequestMapping(value="/userID/{userID}",method = RequestMethod.GET)
    public List<Bidding> getBiddingByUserrID (@PathVariable("userID")  long id);

    @RequestMapping(value="/itemID/{itemID}",method = RequestMethod.GET)
    public List<Bidding> getBiddingByItem (@PathVariable("itemID") long id);

    @RequestMapping(value="/highest/itemID/{itemID}",method = RequestMethod.GET)
    public Bidding getHighestBiddingForItem (@PathVariable("itemID") Long id);

}
