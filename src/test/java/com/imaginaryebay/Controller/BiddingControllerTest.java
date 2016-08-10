package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.BiddingRepository;
import com.imaginaryebay.Repository.ItemRepository;
import com.imaginaryebay.Repository.UserrRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 7/30/16.
 */
public class BiddingControllerTest {

    @Mock
    private ItemRepository itemRepo;

    @Mock
    private UserrRepository userrRepo;

    @Mock
    private BiddingRepository biddingRepo;

    private BiddingControllerImpl impl;

    private Bidding bid1;
    private Bidding bid2;
    private Bidding bid3;
    private Bidding bid4;

    private Userr user1;
    private Userr user2;

    private Item item1;
    private Item item2;

    private List<Bidding> bidsForUser1;
    private List<Bidding> bidsForUser2;
    private List<Bidding> bidsForItem1;
    private List<Bidding> bidsForItem2;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        impl = new BiddingControllerImpl();
        impl.setUserrRepository(userrRepo);
        impl.setBiddingRepository(biddingRepo);
        impl.setItemRepository(itemRepo);

        user1 = new Userr("user1@gmail.com", "User One", "pass", true);
        user2 = new Userr("user2@gmail.com", "User Two", "word", false);

        item1 = new Item();
        item2 = new Item();

        bid1 = new Bidding();
        bid1.setUserr(user1);
        bid1.setPrice(10.0);
        bid1.setItem(item1);
        bid1.setBiddingTime(valueOf("2016-9-2 11:10:10"));

        bid2 = new Bidding();
        bid2.setUserr(user2);
        bid2.setPrice(2000.0);
        bid2.setItem(item2);
        bid2.setBiddingTime(valueOf("2017-9-2 11:10:10"));

        bid3 = new Bidding();
        bid3.setUserr(user2);
        bid3.setPrice(12.0);
        bid3.setItem(item1);
        bid3.setBiddingTime(valueOf("2017-10-2 11:10:10"));

        bid4 = new Bidding();
        bid4.setUserr(user1);
        bid4.setPrice(2200.0);
        bid4.setItem(item2);
        bid4.setBiddingTime(valueOf("2017-11-2 11:10:10"));

        bidsForUser1 = new ArrayList<>();
        bidsForUser1.add(bid1);
        bidsForUser1.add(bid4);

        bidsForUser2 = new ArrayList<>();
        bidsForUser2.add(bid2);
        bidsForUser2.add(bid3);

        bidsForItem1 = new ArrayList<>();
        bidsForItem1.add(bid1);
        bidsForItem1.add(bid3);

        bidsForItem2 = new ArrayList<>();
        bidsForItem2.add(bid2);
        bidsForItem2.add(bid4);

        when(biddingRepo.getBiddingByID(1L)).thenReturn(bid1);
        when(biddingRepo.getBiddingByID(2L)).thenReturn(bid2);
        when(biddingRepo.getBiddingByID(3L)).thenReturn(bid3);
        when(biddingRepo.getBiddingByID(4L)).thenReturn(bid4);
//
//        when(biddingRepo.getBiddingByItem(item1)).thenReturn(bidsForItem1);
//        when(biddingRepo.getBiddingByItem(item2)).thenReturn(bidsForItem2);

        when(biddingRepo.getBiddingByItemID(1L)).thenReturn(bidsForItem1);
        when(biddingRepo.getBiddingByItemID(2L)).thenReturn(bidsForItem2);

        when(biddingRepo.getBiddingByUserrID(1L)).thenReturn(bidsForUser1);
        when(biddingRepo.getBiddingByUserrID(2L)).thenReturn(bidsForUser2);

        when(biddingRepo.getHighestBiddingForItem(1L)).thenReturn(bid3);
        when(biddingRepo.getHighestBiddingForItem(2L)).thenReturn(bid4);


    }

    @Test
    public void createNewBidding() throws Exception {

        impl.createNewBidding(10L, 10.0);
        verify(biddingRepo).createNewBidding(10L, 10.0);

    }

    @Test
    public void getBiddingByID() throws Exception {

        Assert.assertEquals(bid1, impl.getBiddingByID(1L).getBody());
        Assert.assertEquals(bid2, impl.getBiddingByID(2L).getBody());
        Assert.assertEquals(bid3, impl.getBiddingByID(3L).getBody());
        Assert.assertEquals(bid4, impl.getBiddingByID(4L).getBody());

        Assert.assertEquals(HttpStatus.OK, impl.getBiddingByID(1L).getStatusCode());
        Assert.assertEquals(HttpStatus.OK, impl.getBiddingByID(2L).getStatusCode());
        Assert.assertEquals(HttpStatus.OK, impl.getBiddingByID(3L).getStatusCode());
        Assert.assertEquals(HttpStatus.OK, impl.getBiddingByID(4L).getStatusCode());

    }

    @Test
    public void getBiddingByUserrID() throws Exception {

        Assert.assertEquals(bidsForUser1, impl.getBiddingByUserrID(1L).getBody());
        Assert.assertEquals(bidsForUser2, impl.getBiddingByUserrID(2L).getBody());

        Assert.assertEquals(HttpStatus.OK, impl.getBiddingByUserrID(1L).getStatusCode());
        Assert.assertEquals(HttpStatus.OK, impl.getBiddingByUserrID(2L).getStatusCode());
    }

    @Test
    public void getBiddingByItem() throws Exception {

        Assert.assertEquals(bidsForItem1, impl.getBiddingByItem(1L).getBody());
        Assert.assertEquals(bidsForItem2, impl.getBiddingByItem(2L).getBody());

        Assert.assertEquals(HttpStatus.OK, impl.getBiddingByItem(1L).getStatusCode());
        Assert.assertEquals(HttpStatus.OK, impl.getBiddingByItem(2L).getStatusCode());

    }

    @Test
    public void getHighestBiddingForItem() throws Exception {

        Assert.assertEquals(bid3, impl.getHighestBiddingForItem(1L).getBody());
        Assert.assertEquals(bid4, impl.getHighestBiddingForItem(2L).getBody());

        Assert.assertEquals(HttpStatus.OK, impl.getHighestBiddingForItem(1L).getStatusCode());
        Assert.assertEquals(HttpStatus.OK, impl.getHighestBiddingForItem(2L).getStatusCode());

    }

}