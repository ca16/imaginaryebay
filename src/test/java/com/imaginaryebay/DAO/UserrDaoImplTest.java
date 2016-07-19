//package com.imaginaryebay.DAO;
//
//import com.imaginaryebay.Models.Userr;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Chloe on 7/5/16.
// */
//public class UserrDaoImplTest {
//
//    @Mock
//    private EntityManager entityManager;
//
//    @Mock
//    private Query query;
//
//    @Mock
//    private Query query1;
//
//    @Mock
//    private Query query2;
//
//    @Mock
//    private Query queryNotThere;
//
//    private UserrDaoImpl impl;
//
//    private Userr userr1;
//    private Userr userr2;
//    private Userr userr3;
//
//    private List<Userr> list1;
//    private List<Userr> list2;
//    private List<Userr> listEmpty;
//
//    @Before
//    public void setUp() throws Exception {
//
//        MockitoAnnotations.initMocks(this);
//
//        userr1 = new Userr("a@amail.com", "Ace Aceson", "aaaa");
//        userr2 = new Userr("b@bmail.com", "Bb Beeson", "bbbb");
//        userr3 = new Userr("a2@amail.com", "A2 A2man", "a2a2");
//
//        list1 = new ArrayList<>();
//        list2 = new ArrayList<>();
//        listEmpty = new ArrayList<>();
//
//        list1.add(userr1);
//        list2.add(userr2);
//
//
//        when(entityManager.find(Userr.class, 1L)).thenReturn(userr1);
//        when(entityManager.find(Userr.class, 2L)).thenReturn(userr2);
//
//        when(entityManager.find(Userr.class, 1L)).thenReturn(userr1);
//        when(entityManager.find(Userr.class, 2L)).thenReturn(userr2);
//
//        when(entityManager.createQuery("SELECT u FROM Userr u WHERE u.email = :EMA")).thenReturn(query);
//        when(query.setParameter("EMA","a@amail.com")).thenReturn(query1);
//        when(query.setParameter("EMA","b@bmail.com")).thenReturn(query2);
//        when(query.setParameter("EMA","c@cmail.com")).thenReturn(queryNotThere);
//
//        when(query1.getResultList()).thenReturn(list1);
//        when(query2.getResultList()).thenReturn(list2);
//        when(queryNotThere.getResultList()).thenReturn(listEmpty);
//
//        impl = new UserrDaoImpl();
////        impl.setEntityManager(entityManager);
//
//    }
//
//    @Test
//    public void createNewUserr() throws Exception {
//
//        impl.createNewUserr(userr1);
//        verify(entityManager).persist(userr1);
//
//        impl.createNewUserr(userr2);
//        verify(entityManager).persist(userr2);
//
//    }
//
//    @Test
//    public void getUserrByID() throws Exception {
//
//        Assert.assertEquals(userr1, impl.getUserrByID(1L));
//        Assert.assertEquals(userr2, impl.getUserrByID(2L));
//
//
//    }
//
//    @Test
//    public void getUserByEmail() throws Exception {
//
//        Assert.assertEquals(userr1, impl.getUserByEmail("a@amail.com"));
//        Assert.assertEquals(userr2, impl.getUserByEmail("b@bmail.com"));
//        Assert.assertEquals(null, impl.getUserByEmail("c@cmail.com"));
//
//    }
//
//}