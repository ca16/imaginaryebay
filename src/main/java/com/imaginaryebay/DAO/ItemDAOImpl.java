package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Models.Userr;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public Userr findOwnerByID(Long id){
        return entityManager.find(Item.class, id).getUserr();
    }

    @Override
    public String findNameByID(Long id){
        return entityManager.find(Item.class, id).getName();
    }

    @Override
    public Double findHighestBidByID(Long id){
        return entityManager.find(Item.class, id).getHighestBid();
    }

    @Override
    public Item updateItemByID(Long id, Item item){
        Item toChange = entityManager.find(Item.class, id);
        toChange.setHighestBid(item.getHighestBid());
        toChange.setName(item.getName());
        toChange.setPrice(item.getPrice());
        toChange.setCategory(item.getCategory());
        toChange.setEndtime(item.getEndtime());
        toChange.setDescription(item.getDescription());
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
    public List<ItemPicture> findAllItemPicturesForItemID(Long id){
        String hql = "Select ip from ItemPicture ip join fetch ip.auction_item where ip.auction_item.id = :id";
        Query query = entityManager.createQuery(hql);
        query =query.setParameter("id", id);
        List<ItemPicture> itemPictures = query.getResultList();
        return itemPictures;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ItemPicture> findAllItemPictureURLsForItemID(Long id){
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


    @Override
    public List<Item> findItemsBasedOnPage(int pageNum, int pageSize){
        String queryString="From Item";
        Query query=entityManager.createQuery(queryString);
        query.setFirstResult((pageNum-1)*pageSize);
        query.setMaxResults(pageSize);
        List<Item> itemList=query.getResultList();
        return itemList;
    }

    @Override
    public Long findTotalNumOfItems(){
        String queryString="select count(i) from Item i";
        Query query=entityManager.createQuery(queryString);
        List<Long> result=query.getResultList();
        return result.get(0);
    }

    public List<Item> findItemsByName(String name){
        String match = "%" + name.toLowerCase() + "%";
        Query query = entityManager.createQuery(
                "select i from Item i where lower(i.name) like ?1 or i.category like ?1 and i.endtime > ?2 order by i.endtime desc");
        Query query2 = query.setParameter(1, match);
        Timestamp currentTime = new Timestamp((new java.util.Date()).getTime());
        query2.setParameter(2, currentTime);
        return query2.getResultList();

    }


    public List<Item> findItemsByCategoryAndName(Category cat, String name){
        List<Item> byName = findItemsByName(name);
        List<Item> toRet = new ArrayList<>();
        for (Item item : byName){
            if ((item.getCategory() != null) && item.getCategory().equals(cat)){
                toRet.add(item);
            }
        }
        return toRet;
    }

    @Override
    public List<Item> findItemsBySeller(Long id){
        Query query = entityManager.createQuery(
                "select i from Item i where i.userr.id = ?1 and i.endtime > ?2 order by i.endtime desc");
        Query query2 = query.setParameter(1, id);
        Timestamp currentTime = new Timestamp((new java.util.Date()).getTime());
        query2.setParameter(2, currentTime);
        return query2.getResultList();
    }

    @Override
    public List<Item> findItemsByCategoryAndSeller(Category cat, Long ownerId){
        List<Item> byName = findItemsBySeller(ownerId);
        List<Item> toRet = new ArrayList<>();
        for (Item item : byName){
            if ((item.getCategory() != null) && item.getCategory().equals(cat)){
                toRet.add(item);
            }
        }
        return toRet;

    }

    @Override
    public List<Category> findSellerCategories(Long ownerId){
        List<Category> toRet = new ArrayList<>();
        for (Item item : findItemsBySeller(ownerId)){
            if (!toRet.contains(item.getCategory()) && (null != item.getCategory())){
                toRet.add(item.getCategory());
            }
        }
        return toRet;
    }



}