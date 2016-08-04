package com.imaginaryebay.DAO;

/*
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
*/

import com.imaginaryebay.Models.Feedback;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 *
 * Created by Brian on 8/2/2016.
 */
public class FeedbackDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query1;
    @Mock
    private Query query2;
    @Mock
    private Item item1;
    @Mock
    private Item item2;
    @Mock
    private Item item3;
    @Mock
    private Userr userr1;

    private List<Feedback> findAllMock;
    private List<Feedback> findAllByUserr1Mock;

    private FeedbackDAOImpl impl;
    private ItemDAO impl2;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        findAllMock = new ArrayList<>();
        findAllByUserr1Mock = new ArrayList<>();

        Feedback f1 = new Feedback();
        f1.setId(1L);
        f1.setItem(item1);
        f1.setContent("The feedback for item1");
        f1.setTimestamp(valueOf("2016-9-2 11:10:10"));

        Feedback f2 = new Feedback();
        f2.setId(2L);
        f2.setItem(item2);
        f2.setContent("The feedback for item2");
        f2.setTimestamp(valueOf("2016-12-12 11:10:10"));

        Feedback f3 = new Feedback();
        f3.setId(3L);
        f3.setItem(item3);
        f3.setContent("The feedback for item3");
        f3.setTimestamp(valueOf("2016-3-3 11:10:10"));

        when(item1.getUserr()).thenReturn(userr1);
        when(userr1.getId()).thenReturn(1L);

        findAllMock.add(f1);
        findAllMock.add(f2);
        findAllMock.add(f3);

        when(entityManager.createQuery("SELECT f FROM Feedback f")).thenReturn(query1);
        when(query1.getResultList()).thenReturn(findAllMock);

        when(entityManager.createQuery("SELECT f FROM Feedback f JOIN f.item i WHERE i.userr.id = ?1")).thenReturn(query2);
        when(query2.getResultList()).thenReturn(findAllByUserr1Mock);

        impl = new FeedbackDAOImpl();
        impl.setEntityManager(entityManager);

    }

    @Test
    public void findAll(){
        assertEquals(impl.findAll(), findAllMock);
    }

    @Test
    public void findAllByUserrId(){
        assertEquals(impl.findAllByUserrId(userr1.getId()), findAllByUserr1Mock);
    }

}
