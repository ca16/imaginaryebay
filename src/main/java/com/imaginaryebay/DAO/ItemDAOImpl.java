package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chloe on 6/23/16.
 */
public class ItemDAOImpl implements ItemDAO{

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }

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
    public Item find(Item item) { return entityManager.find(Item.class, item.getId()); }

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
        Query query2 = query.setParameter(1, category);
        return query2.getResultList();

    }

    @Override
    public List<Item> findAllItems() {
        Query query = entityManager.createQuery(
                "select i from Item i order by i.price");
        return query.getResultList();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ItemPicture> returnAllItemPicturesForItemID(Long id){
        String hql = "Select ip from ItemPicture ip join fetch ip.auction_item where ip.auction_item.id = :id";
        Query query = entityManager.createQuery(hql);
        query =query.setParameter("id", id);
        List<ItemPicture> itemPictures = query.getResultList();
        return itemPictures;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ItemPicture> returnAllItemPictureURLsForItemID(Long id){
        String hql = "Select ip.id, ip.url from ItemPicture ip join ip.auction_item where ip.auction_item.id = :id";
        Query query = entityManager
                .createQuery(hql);
        
        query=query.setParameter("id", id);
        List<Object[]> selection = query.getResultList();
        List<ItemPicture> itemPictures = selection
                .stream()
                .map( (x) -> new ItemPicture( (Long) x[0], x[1].toString() ) )
                .collect( Collectors.toList() );
        return itemPictures;
    }
}