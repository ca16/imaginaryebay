package com.imaginaryebay.Models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chloe on 7/5/16.
 */
public class UserrTest {

    private Userr userr1;
    private Userr userr2;
    private Userr userr3;


    @Before
    public void setUp() throws Exception {

        userr1 = new Userr("a@amail.com", "Ace Aceson", "aaaa");
        userr2 = new Userr("b@bmail.com", "Bb Beeson", "bbbb");
        userr3 = new Userr("a2@amail.com", "A2 A2man", "a2a2");

    }

    //Test getID()?

    @Test
    public void getEmail() throws Exception {

        Assert.assertEquals("a@amail.com", userr1.getEmail());
        Assert.assertEquals("b@bmail.com", userr2.getEmail());
        Assert.assertEquals("a2@amail.com", userr3.getEmail());

    }

    @Test
    public void setEmail() throws Exception {

        userr1.setEmail("anew@amail.com");
        Assert.assertEquals("anew@amail.com", userr1.getEmail());
        userr2.setEmail("bnew@bmail.com");
        Assert.assertEquals("bnew@bmail.com", userr2.getEmail());
        userr3.setEmail("a2new@amail.com");
        Assert.assertEquals("a2new@amail.com", userr3.getEmail());

    }

    @Test
    public void getName() throws Exception {

        Assert.assertEquals("Ace Aceson", userr1.getName());
        Assert.assertEquals("Bb Beeson", userr2.getName());
        Assert.assertEquals("A2 A2man", userr3.getName());

    }

    @Test
    public void setName() throws Exception {

        userr1.setName("Ace Smith");
        Assert.assertEquals("Ace Smith", userr1.getName());
        userr2.setName("Bob Beeson");
        Assert.assertEquals("Bob Beeson", userr2.getName());
        userr3.setName("New A2man");
        Assert.assertEquals("New A2man", userr3.getName());


    }

    @Test
    public void getPassword() throws Exception {

        Assert.assertEquals("aaaa", userr1.getPassword());
        Assert.assertEquals("bbbb", userr2.getPassword());
        Assert.assertEquals("a2a2", userr3.getPassword());

    }

    @Test
    public void setPassword() throws Exception {

        userr1.setPassword("goodpassword");
        Assert.assertEquals("goodpassword", userr1.getPassword());
        userr2.setPassword("badpassword");
        Assert.assertEquals("badpassword", userr2.getPassword());
        userr3.setPassword("dragon");
        Assert.assertEquals("dragon", userr3.getPassword());

    }

}