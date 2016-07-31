package com.imaginaryebay.Repository;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

import java.util.List;

/**
 * Created by ben on 7/25/16.
 */
public interface BiddingRepository {

    public void createNewBidding(Long itemId, Double price);

    public Bidding getBiddingByID (Long id);

    public List<Bidding> getBiddingByUserrID (Long id);

    public List<Bidding> getBiddingByItem (Item item);

    public List<Bidding> getBiddingByItemID (Long id);

    public Bidding getHighestBiddingForItem (Long id);

    public List<Item> getActiveBidItemsByBidder(Long bidderId);

    public List<Item> getSuccessfulBidItemsByBidder(Long bidderId);




}
