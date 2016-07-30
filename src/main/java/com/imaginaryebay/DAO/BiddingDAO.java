package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;

import java.util.List;

/**
 * Created by Ben_Big on 7/24/16.
 */
public interface BiddingDAO {

    public void persist (Bidding bidding);

    public Bidding getBiddingByID(long id);

    public List<Bidding> getBiddingByUserrID (long id);

    public List<Bidding> getBiddingByItem (Item item);

    public Bidding getHighestBiddingForItem (Long id);


}
