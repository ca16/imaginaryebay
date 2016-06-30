package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Chloe on 6/23/16.
 */
public class ItemDAOImpl implements ItemDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void persist(Item item) {
        entityManager.persist(item);
    }

    @Override
    public void merge(Item item) {
        entityManager.merge(item);
    }

    @Override
    public void refresh(Item item) {
        entityManager.refresh(item);
    }


    @Override
    public Item findByID(Long id){
        return entityManager.find(Item.class, id);
    }

    @Override
    public Double findPriceByID(Long id){
        return entityManager.find(Item.class, id).getPrice();
    }

    @Override
    public Category findCategoryByID(Long id){
        return entityManager.find(Item.class, id).getCategory();
    }

    @Override
    public Timestamp findEndtimeByID(Long id){
        return entityManager.find(Item.class, id).getEndtime();
    }

    @Override
    public String findDescriptionByID(Long id){
        return entityManager.find(Item.class, id).getDescription();
    }
/*
    @Override
    public Userr findOwnerByID(Long id){
        return entityManager.find(Item.class, id).getUserr();
    }
*/
    @Override
    public Item updateItemByID(Long id, Item item){
        Item toChange = entityManager.find(Item.class, id);
        toChange.setPrice(item.getPrice());
        toChange.setCategory(item.getCategory());
        toChange.setEndtime(item.getEndtime());
        toChange.setDescription(item.getDescription());
        //toChange.setUserr(item.getUserr());
        return entityManager.find(Item.class, id);
    }

    @Override
    public List<Item> findAllItemsByCategory(Category category){
        Query query = entityManager.createQuery(
                "select i from Item i where i.category = ?1 order by i.price");
        query.setParameter(1, category);
        return query.getResultList();

    }

    @Override
    public List<Item> findAllItems() {
        Query query = entityManager.createQuery(
                "select i from Item i order by i.price");
        return query.getResultList();

    }
}