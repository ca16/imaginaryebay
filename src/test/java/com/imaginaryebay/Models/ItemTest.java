package com.imaginaryebay.Models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Timestamp.valueOf;
public class ItemTest {

    private Item item1;
    private Item item2;
    private Item item3;

    private Userr userr1;
    private Userr userr2;
    private Userr userr3;


    private ItemPicture itempic1;
    private ItemPicture itempic2;
    private ItemPicture itempic3;

    private List<ItemPicture> list1;
    private List<ItemPicture> list2;
    private List<ItemPicture> list3;

    @Before
    public void setUp() throws Exception {

//    	userr1 = new Userr("t_vivio@yahoo.com","Tina","sfda","Seattle",true);
//    	userr2 = new Userr("hello@gmail.com","Kyle","agdf","New York",false);
//    	userr3 = new Userr("potato@gmail.com","Sam","rtdu","Boston",false);

        userr1 = new Userr("t_vivio@yahoo.com","Tina","sfda",true);
        userr2 = new Userr("hello@gmail.com","Kyle","agdf",false);
        userr3 = new Userr("potato@gmail.com","Sam","rtdu",false);

    	item1 = new Item();
        item1.setCategory(Category.Clothes);
        item1.setPrice(20.0);
        item1.setDescription("Scarf");
        item1.setEndtime(valueOf("2016-10-10 00:00:00"));
        item1.setUserr(userr1);

        item2 = new Item();
        item2.setCategory(Category.Clothes);
        item2.setPrice(200.0);
        item2.setDescription("Expensive Scarf");
        item2.setEndtime(valueOf("2016-11-5 06:00:00"));
        item2.setUserr(userr2);

        item3 = new Item();
        item3.setCategory(Category.Electronics);
        item3.setPrice(30.0);
        item3.setDescription("Watch");
        item3.setEndtime(valueOf("2016-9-2 11:10:10"));
        item3.setUserr(userr3);

        itempic1=new ItemPicture();
        itempic1.setAuction_item(item1);
        itempic1.setUrl("scarf.html");

        itempic2=new ItemPicture();
        itempic2.setAuction_item(item2);
        itempic2.setUrl("expensivescarf.html");

        itempic3=new ItemPicture();
        itempic3.setAuction_item(item3);
        itempic3.setUrl("watch.html");

        list1 = new ArrayList<>();
        list1.add(itempic1);
        list2=new ArrayList<>();
        list2.add(itempic2);
        list3=new ArrayList<>();
        list3.add(itempic3);

    }

    //Test getID()?

    @Test
    public void getCategory() throws Exception {

        Assert.assertEquals(Category.Clothes, item1.getCategory());
        Assert.assertEquals(Category.Clothes, item2.getCategory());
        Assert.assertEquals(Category.Electronics, item3.getCategory());

    }

    @Test
    public void setCategory() throws Exception{
    	item1.setCategory(Category.Electronics);
    	Assert.assertEquals(Category.Electronics,item1.getCategory());
    	item2.setCategory(Category.Electronics);
    	Assert.assertEquals(Category.Electronics,item2.getCategory());
    	item3.setCategory(Category.Clothes);
    	Assert.assertEquals(Category.Clothes,item3.getCategory());
    }


    @Test
    public void getDescription() throws Exception {

        Assert.assertEquals("Scarf", item1.getDescription());
        Assert.assertEquals("Expensive Scarf", item2.getDescription());
        Assert.assertEquals("Watch", item3.getDescription());

    }

    @Test
    public void setDescription() throws Exception {

        item1.setDescription("Ipod");
        Assert.assertEquals("Ipod", item1.getDescription());
        item2.setDescription("Laptop");
        Assert.assertEquals("Laptop", item2.getDescription());
        item3.setDescription("Shirt");
        Assert.assertEquals("Shirt", item3.getDescription());


    }

    @Test
    public void getPrice() throws Exception {

        Assert.assertEquals((Double) 20.0, item1.getPrice());
        Assert.assertEquals((Double)200.0, item2.getPrice());
        Assert.assertEquals((Double)30.0, item3.getPrice());

    }

    @Test
    public void setPrice() throws Exception {

    	item1.setPrice(100.0);
        Assert.assertEquals((Double) 100.0, item1.getPrice());
        item2.setPrice(500.0);
        Assert.assertEquals((Double) 500.0, item2.getPrice());
        item3.setPrice(10.0);
        Assert.assertEquals((Double) 10.0, item3.getPrice());

    }
    @Test
    public void getEndtime() throws Exception{
    	Assert.assertEquals(valueOf("2016-10-10 00:00:00"),item1.getEndtime());
    	Assert.assertEquals(valueOf("2016-11-5 06:00:00"),item2.getEndtime());
    	Assert.assertEquals(valueOf("2016-9-2 11:10:10"),item3.getEndtime());
    }

    @Test
    public void setEndtime() throws Exception{
    	item1.setEndtime(valueOf("2015-8-4 00:00:00"));
    	Assert.assertEquals(valueOf("2015-8-4 00:00:00"),item1.getEndtime());
    	item2.setEndtime(valueOf("2014-4-9 00:00:00"));
    	Assert.assertEquals(valueOf("2014-4-9 00:00:00"),item2.getEndtime());
    	item3.setEndtime(valueOf("2010-7-20 00:00:00"));
    	Assert.assertEquals(valueOf("2010-7-20 00:00:00"),item3.getEndtime());
    }

    @Test
    public void getUserr() throws Exception{
    	Assert.assertEquals(userr1,item1.getUserr());
    	Assert.assertEquals(userr2,item2.getUserr());
    	Assert.assertEquals(userr3,item3.getUserr());
    }
    @Test
    public void setUserr() throws Exception{
    	item1.setUserr(userr3);
    	Assert.assertEquals(userr3,item1.getUserr());
    	item2.setUserr(userr1);
    	Assert.assertEquals(userr1,item2.getUserr());
    	item3.setUserr(userr2);
    	Assert.assertEquals(userr2,item3.getUserr());
    }
    @Test
    public void addItemPicture() throws Exception{
    	item1.addItemPicture(itempic1);
    	Assert.assertEquals(item1,itempic1.getAuction_item());
    	Assert.assertEquals(list1,item1.getItemPictures());
    	item2.addItemPicture(itempic2);
    	Assert.assertEquals(item2,itempic2.getAuction_item());
    	Assert.assertEquals(list2,item2.getItemPictures());
    	item3.addItemPicture(itempic3);
    	Assert.assertEquals(item3,itempic3.getAuction_item());
    	Assert.assertEquals(list3,item3.getItemPictures());
    }

}

