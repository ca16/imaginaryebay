package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;

import java.util.List;

/**
 * Created by Ben_Big on 7/24/16.
 */
public interface BiddingDAO {

    public void persist (Bidding bidding);

    public Bidding getBiddingByID(Long id);

    public List<Bidding> getBiddingByUserrID (Long id);

    public List<Bidding> getBiddingByItem (Item item);

    public List<Bidding> getBiddingByItemID (Long id);

    public Bidding getHighestBiddingForItem (Long id);
}
