package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.ItemPicture;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Brian on 6/29/2016.
 */

@Transactional
public class ItemPictureDAOImpl implements ItemPictureDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void merge(ItemPicture itemPicture) {
        entityManager.merge(itemPicture);
    }

    @Override
    public void persist(ItemPicture itemPicture) {
        entityManager.persist(itemPicture);
    }

    @Override
    public void refresh(ItemPicture itemPicture) {
        entityManager.refresh(itemPicture);
    }
}
