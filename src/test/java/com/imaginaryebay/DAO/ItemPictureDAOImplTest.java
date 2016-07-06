package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.ItemPicture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import static org.mockito.Mockito.verify;

/**
 * Created by Chloe on 7/1/16.
 */
public class ItemPictureDAOImplTest {

    @Mock
    private EntityManager entityManager;


    
    private ItemPictureDAOImpl impl;

    @Before
    public void setUp() throws Exception {


    	MockitoAnnotations.initMocks(this);

        impl = new ItemPictureDAOImpl();
        impl.setEntityManager(entityManager);

    }

    @Test
    public void persist() throws Exception {
        ItemPicture toSave = new ItemPicture();
        impl.persist(toSave);
        verify(entityManager).persist(toSave);
    }

    @Test
    public void merge() throws Exception {
        ItemPicture toMerge = new ItemPicture();
        impl.merge(toMerge);
        verify(entityManager).merge(toMerge);
    }

    @Test
    public void refresh() throws Exception {
        ItemPicture toRefresh = new ItemPicture();
        impl.refresh(toRefresh);
        verify(entityManager).refresh(toRefresh);
    }

    
}