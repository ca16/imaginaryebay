package com.imaginaryebay.Models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static java.sql.Timestamp.valueOf;

public class ItemPictureTest {

	 private Item item1;
	    private Item item2;
	    private Item item3;

	    private ItemPicture itempic1;
	    private ItemPicture itempic2;
	    private ItemPicture itempic3;




	    @Before
	    public void setUp() throws Exception {

	    	item1 = new Item();
	        item1.setCategory(Category.Clothes);
	        item1.setPrice(20.0);
	        item1.setDescription("Scarf");
	        item1.setEndtime(valueOf("2016-10-10 00:00:00"));
	        item1.setUserr(new Userr("t_vivio@yahoo.com","Tina","sfda",true));


            item2 = new Item();
	        item2.setCategory(Category.Clothes);
	        item2.setPrice(200.0);
	        item2.setDescription("Expensive Scarf");
	        item2.setEndtime(valueOf("2016-11-5 06:00:00"));
            item2.setUserr(new Userr("hello@gmail.com","Kyle","agdf", false));

	        item3 = new Item();
	        item3.setCategory(Category.Electronics);
	        item3.setPrice(30.0);
	        item3.setDescription("Watch");
	        item3.setEndtime(valueOf("2016-9-2 11:10:10"));
            item3.setUserr(new Userr("potato@gmail.com","Sam","rtdu", false));

	        itempic1=new ItemPicture("scarf.html");
	        itempic1.setAuction_item(item1);
	        itempic2=new ItemPicture("expensivescarf.html");
	        itempic2.setAuction_item(item2);
	        itempic3=new ItemPicture("watch.html");
	        itempic3.setAuction_item(item3);
	    }

	    //Test getID()?

	    @Test
	    public void getAuction_item() throws Exception{
	    	Assert.assertEquals(item1,itempic1.getAuction_item());
	    	Assert.assertEquals(item2,itempic2.getAuction_item());
	    	Assert.assertEquals(item3,itempic3.getAuction_item());
	    }
	    @Test
	    public void setAuction_item() throws Exception{
	    	itempic1.setAuction_item(item3);
	    	Assert.assertEquals(item3,itempic1.getAuction_item());
	    	itempic2.setAuction_item(item1);
	    	Assert.assertEquals(item1,itempic2.getAuction_item());
	    	itempic3.setAuction_item(item2);
	    	Assert.assertEquals(item2,itempic3.getAuction_item());
	    }

	    @Test
	    public void getUrl() throws Exception{
	    	Assert.assertEquals("scarf.html",itempic1.getUrl());
	    	Assert.assertEquals("expensivescarf.html",itempic2.getUrl());
	    	Assert.assertEquals("watch.html",itempic3.getUrl());
	    }

	    @Test
	    public void setUrl() throws Exception{
	    	itempic1.setUrl("coolwatch.html");
	    	Assert.assertEquals("coolwatch.html",itempic1.getUrl());
	    	itempic2.setUrl("uglyscarf.html");
	    	Assert.assertEquals("uglyscarf.html",itempic2.getUrl());
	    	itempic3.setUrl("beautifulscarf.html");
	    	Assert.assertEquals("beautifulscarf.html",itempic3.getUrl());
	    }

}


