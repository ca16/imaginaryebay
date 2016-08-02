package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.BiddingControllerImpl;
import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.BiddingDAO;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Timestamp.valueOf;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static org.junit.Assert.*;

/**
 * Created by Chloe on 7/30/16.
 */
public class BiddingRepositoryTest {

    @Mock
    private ItemDAO itemDao;

    @Mock
    private UserrDao userrDao;

    @Mock
    private BiddingDAO biddingDao;

    private BiddingRepositoryImpl impl;

    private Userr admin1;
    private Userr admin2;
    private Userr nonAdmin1;
    private Userr butters2;

    private UserDetails admin1UD;
    private UserDetails admin2UD;
    private UserDetails nonAdmin1UD;
    private UserDetails nonAdminNonUserUD;

    private List<GrantedAuthority> authListAdmin;
    private List<GrantedAuthority> authListNonAdmin;

    private Authentication authAdmin1;
    private Authentication authAdmin2;
    private Authentication authNonAdmin1;

    private Item item1;
    private Item item2;
    private Item item3;
    private Item item4;
    private Item item100;

    private Bidding bid1;

    private List<Bidding> bidsForUser2;
    private List<Bidding> bidsForItem1;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        impl = new BiddingRepositoryImpl();
        impl.setBiddingDAO(biddingDao);
        impl.setUserrDAO(userrDao);
        impl.setItemDAO(itemDao);

        admin1 = new Userr("jackie@gmail.com", "Jackie Fire", "dragon", true);
        admin2 = new Userr("tom@hotmail.com", "Tom Doe", "haha", true);
        nonAdmin1 = new Userr("butters@gmail.com", "Butters Stotch", "Stotch");
        butters2 = new Userr("butters2@gmail.com", "Butters Stotch", "1234");

        authListAdmin = new ArrayList<>();
        authListAdmin.add(new SimpleGrantedAuthority("USER"));
        authListAdmin.add(new SimpleGrantedAuthority("ADMIN"));

        authListNonAdmin = new ArrayList<>();
        authListNonAdmin.add(new SimpleGrantedAuthority("USER"));

        admin1UD = new User("jackie@gmail.com", "dragon", authListAdmin);
        admin2UD = new User("tom@hotmail.com", "haha", authListAdmin);
        nonAdmin1UD = new User("butters@gmail.com", "Stotch", authListNonAdmin);

        authAdmin1 = new UsernamePasswordAuthenticationToken(admin1UD, "dragon", authListAdmin);
        authAdmin2 = new UsernamePasswordAuthenticationToken(admin2UD, "haha", authListAdmin);
        authNonAdmin1 = new UsernamePasswordAuthenticationToken(nonAdmin1UD, "Stotch", authListNonAdmin);

        item1 = new Item();
        item1.setUserr(admin1);
        item1.setName("Clock");
        item1.setPrice(10.0);
        item1.setEndtime(valueOf("2017-9-2 11:10:10"));
        item1.setHighestBid(12.0);
        item1.setId(1L);

        item2 = new Item();
        item2.setUserr(admin2);
        item2.setName("Pillow");
        item2.setPrice(6.0);
        item2.setEndtime(valueOf("2016-6-2 11:10:10"));
        item2.setId(2L);

        item3 = new Item();
        item3.setUserr(admin2);
        item3.setName("Blanket");
        item3.setPrice(10.0);
        item3.setEndtime(valueOf("2017-6-2 11:10:10"));
        item3.setHighestBid(18.0);
        item3.setId(3L);

        item4 = new Item();
        item4.setUserr(admin2);
        item4.setName("Watch");
        item4.setPrice(30.0);
        item4.setEndtime(valueOf("2017-10-2 11:10:10"));
        item4.setId(4L);

        item100 = new Item();

        bid1 = new Bidding();
        bid1.setUserr(admin2);
        bid1.setPrice(12.0);
        bid1.setItem(item1);
        bid1.setBiddingTime(valueOf("2016-9-2 11:10:10"));

        bidsForUser2 = new ArrayList<>();
        bidsForUser2.add(bid1);
        bidsForItem1 = bidsForUser2;

        when(itemDao.findByID(100L)).thenReturn(null);
        when(itemDao.findByID(1L)).thenReturn(item1);
        when(userrDao.getUserrByEmail("jackie@gmail.com")).thenReturn(admin1);
        when(itemDao.findByID(2L)).thenReturn(item2);
        when(itemDao.findByID(3L)).thenReturn(item3);
        when(itemDao.findByID(4L)).thenReturn(item4);
        when(itemDao.find(item100)).thenReturn(null);
        when(itemDao.find(item2)).thenReturn(item2);
        when(itemDao.find(item1)).thenReturn(item1);

        when(biddingDao.getBiddingByID(100L)).thenReturn(null);
        when(biddingDao.getBiddingByID(1L)).thenReturn(bid1);
        when(biddingDao.getBiddingByUserrID(3L)).thenReturn(new ArrayList<>());
        when(biddingDao.getBiddingByUserrID(2L)).thenReturn(bidsForUser2);
        when(biddingDao.getBiddingByItem(item2)).thenReturn(new ArrayList<>());
        when(biddingDao.getBiddingByItem(item1)).thenReturn(bidsForItem1);
        when(biddingDao.getBiddingByItemID(1L)).thenReturn(bidsForItem1);
        when(biddingDao.getHighestBiddingForItem(2L)).thenReturn(null);
        when(biddingDao.getHighestBiddingForItem(1L)).thenReturn(bid1);

        when(userrDao.getUserrByID(10L)).thenReturn(null);
        when(userrDao.getUserrByID(2L)).thenReturn(admin2);
        when(userrDao.getUserrByID(3L)).thenReturn(nonAdmin1);

    }

    @Test
    public void createNewBidding() throws Exception {

        // item to be bid on does not exist
        try{
            impl.createNewBidding(100L, 100.0);
            fail();
        } catch (RestException exc){
            Assert.assertEquals("Item with id 100 does not exist.", exc.getDetailedMessage());
        }

        // no one's logged in
        try{
            impl.createNewBidding(1L, 100.0);
            fail();
        } catch (RestException exc){
            Assert.assertEquals("You must be logged in to create an item.", exc.getDetailedMessage());
        }

        // user is trying to bid on their own item
        SecurityContextHolder.getContext().setAuthentication(authAdmin1);
        try {
            impl.createNewBidding(1L, 100.0);
            fail();
        } catch (RestException exc){
            Assert.assertEquals("You can only bid on items you do not own.", exc.getDetailedMessage());
        }

        // user is trying to bid after the auction is over
        try {
            impl.createNewBidding(2L, 10.0);
            fail();
            } catch (RestException exc){
            Assert.assertEquals("This auction is closed.", exc.getDetailedMessage());
        }

        // user is making a bid lower than the current highest bid
        try {
            impl.createNewBidding(3L, 12.0);
            fail();
        } catch (RestException exc){
            Assert.assertEquals("Your bid amount must be greater than the item's current highest bid, which is 18.0.", exc.getDetailedMessage());
        }

        // the item has no current highest bid and the user is making a bid lower than the
        // items original price
        try {
            impl.createNewBidding(4L, 20.0);
            fail();
        } catch (RestException exc){
            Assert.assertEquals("Your bid amount mut be greater than the item's price, which is 30.0.", exc.getDetailedMessage());
        }

        impl.createNewBidding(4L, 35.0);
        Assert.assertTrue(35.0 == item4.getHighestBid());

        verify(biddingDao).persist(any(Bidding.class));

    }

    @Test
    public void getBiddingByID() throws Exception {

        // Looking for an a bid that doesn't exist
        try {
            impl.getBiddingByID(100L);
            fail();
        }catch (RestException exc){
            Assert.assertEquals("Bid with id 100 does not exist.", exc.getDetailedMessage());
        }

        Assert.assertEquals(bid1, impl.getBiddingByID(1L));
    }

    @Test
    public void getBiddingByUserrID() throws Exception {
        try {
            impl.getBiddingByUserrID(10L);
            fail();
        }catch(RestException exc){
            Assert.assertEquals("User with id 10 does not exist.", exc.getDetailedMessage());
        }

        try {
            impl.getBiddingByUserrID(3L);
            fail();
        }catch (RestException exc){
            Assert.assertEquals("No bids have been made by user with id 3.", exc.getDetailedMessage());
        }

        Assert.assertEquals(bidsForUser2, impl.getBiddingByUserrID(2L));


    }

    @Test
    public void getBiddingByItem() throws Exception {
        try {
            impl.getBiddingByItem(item100);
            fail();
        }catch (RestException exc){
            Assert.assertEquals("The item does not exist.", exc.getDetailedMessage());
        }

        try {
            impl.getBiddingByItem(item2);
            fail();
        }catch (RestException exc){
            Assert.assertEquals("No bids have been made on this item.", exc.getDetailedMessage());
        }

        Assert.assertEquals(bidsForItem1, impl.getBiddingByItem(item1));

    }

    @Test
    public void getBiddingByItemID() throws Exception {
        try {
            impl.getBiddingByItemID(100L);
            fail();
        }catch (RestException exc){
            Assert.assertEquals("The item does not exist.", exc.getDetailedMessage());
        }

        try {
            impl.getBiddingByItemID(2L);
            fail();
        }catch (RestException exc){
            Assert.assertEquals("No bids have been made on this item.", exc.getDetailedMessage());
        }

        Assert.assertEquals(bidsForItem1, impl.getBiddingByItemID(1L));

    }

    @Test
    public void getHighestBiddingForItem() throws Exception {
        try {
            impl.getHighestBiddingForItem(2L);
            fail();
        }catch (RestException exc){
            Assert.assertEquals("There is no highest bid for this item.", exc.getDetailedMessage());
        }

        Assert.assertEquals(bid1, impl.getHighestBiddingForItem(1L));

    }

}