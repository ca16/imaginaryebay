package com.imaginaryebay.Repository;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

import java.util.List;

/**
 * Created by ben on 7/25/16.
 */
public interface BiddingRepository {

    public void createNewBidding(Userr userr, Item item, double price);

    public Bidding getBiddingByID (long id);

    public List<Bidding> getBiddingByUserrID (long id);

    public List<Bidding> getBiddingByItem (Item item);

    public Bidding getHighestBiddingForItem (Long id);


}
