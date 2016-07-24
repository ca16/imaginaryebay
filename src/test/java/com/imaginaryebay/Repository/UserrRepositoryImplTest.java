//package com.imaginaryebay.Repository;
//
//import com.imaginaryebay.DAO.ItemDAO;
//import com.imaginaryebay.DAO.UserrDao;
//import com.imaginaryebay.Models.Item;
//import com.imaginaryebay.Models.Userr;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Chloe on 7/5/16.
// */
//public class UserrRepositoryImplTest {
//
//    @Mock
//    private UserrDao userrDao;
//
//    private UserrRepositoryImpl impl;
//
//    private Userr userr1;
//    private Userr userr2;
//    private Userr newUser;
//
//
//
//    @Before
//    public void setUp() throws Exception {
//
//        MockitoAnnotations.initMocks(this);
//
//        userr1 = new Userr("a@amail.com", "Ace Aceson", "aaaa");
//        userr2 = new Userr("b@bmail.com", "Bb Beeson", "bbbb");
//        newUser = new Userr("new@email.com", "New User", "newnew");
//
//        when(userrDao.getUserrByID(1L)).thenReturn(userr1);
//        when(userrDao.getUserrByID(2L)).thenReturn(userr2);
//
//        when(userrDao.getUserByEmail("a@amail.com")).thenReturn(userr1);
//        when(userrDao.getUserByEmail("b@bmail.com")).thenReturn(userr2);
//
//        when(userrDao.getUserByEmail("newemail@emails.com")).thenReturn(null);
//
//        impl = new UserrRepositoryImpl();
//        impl.setUserrDao(userrDao);
//
//    }
//
//    @Test
//    public void createNewUserr() throws Exception {
//
//        impl.createNewUserr(newUser);
//        verify(userrDao).createNewUserr(newUser);
//
//        impl.createNewUserr(userr1);
//        //there will probably be an exception here...
//
//    }
//
//    @Test
//    public void getUserrByID() throws Exception {
//
//        Assert.assertEquals(userr1, impl.getUserrByID(1L));
//        Assert.assertEquals(userr2, impl.getUserrByID(2L));
//
//    }
//
//    @Test
//    public void getUserrByEmail() throws Exception {
//
//        Assert.assertEquals(userr1, impl.getUserrByEmail("a@amail.com"));
//        Assert.assertEquals(userr2, impl.getUserrByEmail("b@bmail.com"));
//
//    }
//
//}