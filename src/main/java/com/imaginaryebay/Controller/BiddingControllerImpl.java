package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.BiddingRepository;
import com.imaginaryebay.Repository.ItemRepository;
import com.imaginaryebay.Repository.UserrRepository;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Override
    public void createNewBidding(long userrID, long itemID, double price){
        Userr userr=userrRepository.getUserrByID(userrID);
        //ToDo: what happens when the userr does not exist
        //ToDo: check the userrID is the one that has logged in
        Item item=itemRepository.findByID(itemID);
        //ToDo: what happens when the item does not exist
        biddingRepository.createNewBidding(userr,item,price);
    }

    @Override
    public Bidding getBiddingByID (Long id){
        return biddingRepository.getBiddingByID(id);
    }

    @Override
    public List<Bidding> getBiddingByUserrID (long id){
        return biddingRepository.getBiddingByUserrID(id);
    }

    @Override
    public List<Bidding> getBiddingByItem (long id){
        Item item=itemRepository.findByID(id);
        return biddingRepository.getBiddingByItem(item);
    }

    @Override
    public Bidding getHighestBiddingForItem (Long id){
        return biddingRepository.getHighestBiddingForItem(id);
    }
}
