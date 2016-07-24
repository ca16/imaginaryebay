package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Message;
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
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Brian on 7/5/2016.
 */
public class MessageDaoTest {

    @Mock
    EntityManager entityManager;

    @Mock
    private Message msg1;
    @Mock
    private Message msg2;
    @Mock
    private Message msg3;

    @Mock
    private Userr user1;
    @Mock
    private Userr user2;

    @Mock
    private Query query;
    @Mock
    private Query query1;
    @Mock
    private Query query2;

    private List<Message> messages1;
    private List<Message> messages2;
    private List<Message> noMessages;

    private MessageDaoImpl thisImpl;


    @Before
    public void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);

        messages1 = new ArrayList<>();
        messages2 = new ArrayList<>();
        noMessages = new ArrayList<>();

        when(user1.getId()).thenReturn(1L);
        when(user1.getEmail()).thenReturn("test@email.com");
        when(user1.getName()).thenReturn("Name");
        when(user1.getPassword()).thenReturn("password");

        when(user2.getId()).thenReturn(2L);
        when(user2.getEmail()).thenReturn("prueba@email.com");
        when(user2.getName()).thenReturn("Nombre");
        when(user2.getPassword()).thenReturn("pass");

        when(msg1.getId()).thenReturn(5L);
        when(msg1.getReceiver_id()).thenReturn(user1);
        when(msg1.getDate_sent()).thenReturn(valueOf("2016-1-1 00:00:00"));

        when(msg2.getId()).thenReturn(6L);
        when(msg2.getReceiver_id()).thenReturn(user1);
        when(msg2.getDate_sent()).thenReturn(valueOf("2016-12-31 11:59:59"));

        when(msg3.getId()).thenReturn(7L);
        when(msg3.getReceiver_id()).thenReturn(user2);
        when(msg3.getDate_sent()).thenReturn(valueOf("2016-1-10 14:14:14"));

        messages1.add(msg1);
        messages1.add(msg2);

        messages2.add(msg3);

        when(entityManager.createQuery("select m from Message m where m.receiver_id.id = ?1")).thenReturn(query);

        when(query.setParameter(1, 1L)).thenReturn(query1);
        when(query1.getResultList()).thenReturn(messages1);

        when(query.setParameter(1, 2L)).thenReturn(query2);
        when(query2.getResultList()).thenReturn(messages2);

        thisImpl = new MessageDaoImpl();
        thisImpl.setEntityManager(entityManager);
    }

    @Test
    public void persist() throws Exception{

        Message msgToSave1 = new Message();
        Message msgToSave2 = new Message(user1, msg1.getDate_sent());

        thisImpl.persist(msgToSave1);
        thisImpl.persist(msgToSave2);

        verify(entityManager).persist(msgToSave1);
        verify(entityManager).persist(msgToSave2);
    }

    @Test
    public void findAllMessagesByReceiverID(){
        assertEquals(messages1, entityManager.createQuery("select m from Message m where m.receiver_id.id = ?1")
                                             .setParameter(1, 1L)
                                             .getResultList());
        assertEquals(messages2, entityManager.createQuery("select m from Message m where m.receiver_id.id = ?1")
                                             .setParameter(1, 2L)
                                             .getResultList());
        assertEquals(noMessages, thisImpl.findAllMessagesByReceiverID(3L));
    }
}
