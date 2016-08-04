package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.BiddingDAO;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 7/25/16.
 */

@Component
@ComponentScan("com.imaginaryebay.DAO")
@Transactional
public class BiddingRepositoryImpl implements BiddingRepository {

    private static final String NOT_AVAILABLE = "Not available.";


    @Autowired
    private BiddingDAO biddingDAO;

    @Autowired
    private UserrDao userrDAO;

    @Autowired
    private ItemDAO itemDAO;

    public void setBiddingDAO(BiddingDAO biddingDao) {
        this.biddingDAO = biddingDao;
    }

    public void setUserrDAO(UserrDao userrDao) {
        this.userrDAO = userrDao;
    }

    public void setItemDAO(ItemDAO itemDao) { this.itemDAO = itemDao; }

    @Override
    public void createNewBidding(Long itemId, Double price){
        Item toBidOn = this.itemDAO.findByID(itemId);

        // can't bid on non-existent items
        if (itemId == null || toBidOn == null) {
            throw new RestException(NOT_AVAILABLE,
                    "Item with id " + itemId + " does not exist.", HttpStatus.BAD_REQUEST);
        }

        // can't bid if you're not logged in
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RestException(NOT_AVAILABLE, "You must be logged in to create an item.", HttpStatus.UNAUTHORIZED);
        }

        String email = auth.getName();
        Userr owner = userrDAO.getUserrByEmail(email);

        // can't bid on your own item
        if (toBidOn.getUserr().equals(owner)) {
            throw new RestException(NOT_AVAILABLE, "You can only bid on items you do not own.", HttpStatus.FORBIDDEN);
        }

        // can't bid after the auction has ended
        // can we assume earlier bids will be dealt with earlier?
        java.util.Date date = new java.util.Date();
        Timestamp biddingTime = new Timestamp(date.getTime());
        Timestamp deadline = toBidOn.getEndtime();
        if (biddingTime.after(deadline)){
            throw new RestException(NOT_AVAILABLE,
                    "This auction is closed.", HttpStatus.BAD_REQUEST);
        }

        // can't make a bid lower than the current highest bid
        // (if there's a current highest bid)
        if ((toBidOn.getHighestBid() != null) && (price <= toBidOn.getHighestBid())){
            throw new RestException("Invalid bid amount.",
                    "Your bid amount must be greater than the item's current highest bid, which is " + toBidOn.getHighestBid() + ".", HttpStatus.BAD_REQUEST);
        }

        // can't make a bid lower than the item's price
        else if ((toBidOn.getHighestBid() == null) && (price < toBidOn.getPrice())){
            throw new RestException("Invalid bid amount.",
                    "Your bid amount mut be greater than the item's price, which is " + toBidOn.getPrice() + ".", HttpStatus.BAD_REQUEST);
        }

        Bidding bidding = new Bidding();
        bidding.setUserr(owner);
        bidding.setItem(toBidOn);
        bidding.setBiddingTime(biddingTime);
        bidding.setPrice(price);
        biddingDAO.persist(bidding);

        toBidOn.setHighestBid(price);
        itemDAO.updateItemByID(itemId, toBidOn);

//        //ToDo: Can I person bid on his own item?
//        java.util.Date date= new java.util.Date();
//        Timestamp biddingTime=new Timestamp(date.getTime());
//        Timestamp deadline=item.getEndtime();
//        if (biddingTime.after(deadline)){
//            //ToDo: Some sort of exception should be returned here
//            return;
//        }
//        if (price<item.getPrice()){
//            //ToDo: Some sort of exception should be returned here
//        }
//        //ToDo: compare price with highest price in item
//        //ToDo: update the highest price in item.
//
//        Bidding bidding=new Bidding();
//        bidding.setUserr(userr);
//        bidding.setItem(item);
//        bidding.setBiddingTime(biddingTime);
//        bidding.setPrice(price);
//        biddingDAO.persist(bidding);


    }

    @Override
    public Bidding getBiddingByID (Long id){
        Bidding toRet = this.biddingDAO.getBiddingByID(id);
        if (toRet == null) {
            throw new RestException(NOT_AVAILABLE,
                    "Bid with id " + id + " does not exist.", HttpStatus.OK);
        }

        return toRet;
    }

    @Override
    public List<Bidding> getBiddingByUserrID (Long id){
        Userr user = userrDAO.getUserrByID(id);
        if (null == user){
            throw new RestException(NOT_AVAILABLE,
                    "User with id " + id + " does not exist.", HttpStatus.OK);
        }
        List<Bidding> toRet = this.biddingDAO.getBiddingByUserrID(id);
        if (toRet.isEmpty()){
            throw new RestException(NOT_AVAILABLE,
                    "No bids have been made by user with id " + id + ".", HttpStatus.OK);
        }
        return toRet;
    }

    @Override
    public List<Bidding> getBiddingByItem (Item item){
        Item it = itemDAO.find(item);
        if (it == null){
            throw new RestException(NOT_AVAILABLE, "The item does not exist.", HttpStatus.OK);
        }
        List<Bidding> toRet = this.biddingDAO.getBiddingByItem(item);
        if (toRet.isEmpty()){
            throw new RestException(NOT_AVAILABLE,
                    "No bids have been made on this item.", HttpStatus.OK);
        }
        return toRet;
    }

    @Override
    public List<Bidding> getBiddingByItemID (Long id){
        Item it = itemDAO.findByID(id);
        if (it == null){
            throw new RestException(NOT_AVAILABLE, "The item does not exist.", HttpStatus.OK);
        }
        List<Bidding> toRet = this.biddingDAO.getBiddingByItemID(id);
        if (toRet.isEmpty()){
            throw new RestException(NOT_AVAILABLE,
                    "No bids have been made on this item.", HttpStatus.OK);
        }
        return toRet;
    }

    @Override
    public Bidding getHighestBiddingForItem (Long id){
        Bidding toRet = biddingDAO.getHighestBiddingForItem(id);
        if (null == toRet){
            throw new RestException(NOT_AVAILABLE, "There is no highest bid for this item.", HttpStatus.OK);
        }
        else return toRet;
    }

    @Override
    public List<Item> getActiveBidItemsByBidder(Long bidderId){
        bidderValidation(bidderId);
        Userr user = userrDAO.getUserrByID(bidderId);
        if (null == user){
            throw new RestException(NOT_AVAILABLE, "User with id " + bidderId + " does not exist.", HttpStatus.OK);
        }

        return biddingDAO.getActiveBidItemsByBidder(bidderId);


    }

    @Override
    public List<Item> getSuccessfulBidItemsByBidder(Long bidderId){
        bidderValidation(bidderId);
        Userr user = userrDAO.getUserrByID(bidderId);
        if (null == user){
            throw new RestException(NOT_AVAILABLE, "User with id " + bidderId + " does not exist.", HttpStatus.OK);
        }
        return biddingDAO.getSuccessfulBidItemsByBidder(bidderId);

    }

    @Override
    public List<Item> getActiveItemsByBidderByPage (Long bidderID, int pageNum, int pageSize) {
        bidderValidation(bidderID);
        Userr user = userrDAO.getUserrByID(bidderID);
        if (null == user){
            throw new RestException(NOT_AVAILABLE, "User with id " + bidderID + " does not exist.", HttpStatus.OK);
        }

        return biddingDAO.getActiveBidItemsByBidderByPage(bidderID, pageNum, pageSize);
    }

    @Override
    public List<Item> getSuccessfulAuctionItemsByBidderByPage (Long bidderID, int pageNum, int pageSize) {
        bidderValidation(bidderID);
        Userr user = userrDAO.getUserrByID(bidderID);
        if (null == user){
            throw new RestException(NOT_AVAILABLE, "User with id " + bidderID + " does not exist.", HttpStatus.OK);
        }
        return biddingDAO.getSuccessfulBidItemsByBidderByPage(bidderID, pageNum, pageSize);
    }

    public Integer getActiveBidItemsByBidderCount(Long bidderId){
        bidderValidation(bidderId);
        return biddingDAO.getActiveBidItemsByBidderCount(bidderId);
    }

    public Integer getSuccessfulBidItemsByBidderCount(Long bidderId){
        bidderValidation(bidderId);
        return biddingDAO.getSuccessfulBidItemsByBidderCount(bidderId);
    }

    private void bidderValidation(Long bidderID){

        // can't bid if you're not logged in
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RestException(NOT_AVAILABLE, "You must be logged in to find bids by bidder.", HttpStatus.UNAUTHORIZED);
        }

        String email = auth.getName();
        Userr user = userrDAO.getUserrByEmail(email);

        // can't bid on your own item
        if (!bidderID.equals(user.getId())) {
            throw new RestException(NOT_AVAILABLE, "You can only find look at your own bids.", HttpStatus.FORBIDDEN);
        }
    }

}
