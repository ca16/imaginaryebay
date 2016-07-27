package com.imaginaryebay.Repository;

import com.imaginaryebay.DAO.BiddingDAO;
import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ben on 7/25/16.
 */

@Component
@ComponentScan("com.imaginaryebay.DAO")
@Transactional
public class BiddingRepositoryImpl implements BiddingRepository {

    @Autowired
    BiddingDAO biddingDAO;



    @Override
    public void createNewBidding(Userr userr, Item item, double price){
        //ToDo: Can I person bid on his own item?
        java.util.Date date= new java.util.Date();
        Timestamp biddingTime=new Timestamp(date.getTime());
        Timestamp deadline=item.getEndtime();
        if (biddingTime.after(deadline)){
            //ToDo: Some sort of exception should be returned here
            return;
        }
        if (price<item.getPrice()){
            //ToDo: Some sort of exception should be returned here
        }
        //ToDo: compare price with highest price in item
        //ToDo: update the highest price in item.

        Bidding bidding=new Bidding();
        bidding.setUserr(userr);
        bidding.setItem(item);
        bidding.setBiddingTime(biddingTime);
        bidding.setPrice(price);
        biddingDAO.persist(bidding);

    }

    @Override
    public Bidding getBiddingByID (long id){
        return biddingDAO.getBiddingByID(id);
    }

    @Override
    public List<Bidding> getBiddingByUserrID (long id){
        return biddingDAO.getBiddingByUserrID(id);
    }

    @Override
    public List<Bidding> getBiddingByItem (Item item){
        return biddingDAO.getBiddingByItem(item);
    }

    @Override
    public Bidding getHighestBiddingForItem (Long id){
        return biddingDAO.getHighestBiddingForItem(id);
    }


}
