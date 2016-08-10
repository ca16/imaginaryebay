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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public List<Category> findSellerCategories(Long ownerId){
        List<Category> toRet = new ArrayList<>();
        for (Item item : findItemsBySeller(ownerId)){
            if (!toRet.contains(item.getCategory()) && (null != item.getCategory())){
                toRet.add(item.getCategory());
            }
        }
        return toRet;
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
    @SuppressWarnings("unchecked")
    public List<ItemPicture> findAllItemPicturesForItemID(Long id){
        String hql = "Select ip from ItemPicture ip join fetch ip.auction_item where ip.auction_item.id = :id";
        Query query = entityManager.createQuery(hql);
        query =query.setParameter("id", id);
        List<ItemPicture> itemPictures = query.getResultList();
        return itemPictures;
    }


    public List<ItemPicture> findThreeRandomPicsBySeller(Long sellerID){
        List<ItemPicture> pics = new ArrayList<>();
        List<Item> items = findItemsBySeller(sellerID);
        for (Item item : items){
            pics.addAll(findAllItemPicturesForItemID(item.getId()));
        }
        if (pics.size() < 3){
            return pics;
        }
        else {
            Set<ItemPicture> toRet = new HashSet<>();
            while (toRet.size() < 3) {
                Integer select = (int )(Math.random() * pics.size());
                toRet.add(pics.get(select));
            }
            return new ArrayList<>(toRet);
        }
    }

    //////////////////////////////////////
    // Item Searches /////////////////////
    //////////////////////////////////////

    @Override
    public List<Item> findAllItems() {
        Query query = entityManager.createQuery(
                "select i from Item i order by i.price");
        return query.getResultList();

    }

    @Override
    public List<Item> findItemsByKeyword(String keyword){
        return byKeyword(keyword).getResultList();

    }

    @Override
    public List<Item> findAllItemsByCategory(Category category){
        return byCategory(category).getResultList();

    }

    @Override
    public List<Item> findItemsBySeller(Long id){
        return bySeller(id).getResultList();
    }

    public List<Item> findItemsByCategoryAndKeyword(Category cat, String keyword){
        return byCategoryAndKeyword(cat, keyword).getResultList();
    }

    @Override
    public List<Item> findItemsByCategoryAndSeller(Category cat, Long sellerID){
        return byCategoryAndSeller(cat, sellerID).getResultList();

    }

    //////////////////////////////////////
    // Searches w Pagination /////////////
    //////////////////////////////////////

    @Override
    public List<Item> findItemsBasedOnPage(int pageNum, int pageSize){
        String queryString="From Item";
        Query query=entityManager.createQuery(queryString);
        query.setFirstResult((pageNum-1)*pageSize);
        query.setMaxResults(pageSize);
        List<Item> itemList=query.getResultList();
        return itemList;
    }

    public List<Item> findItemsByKeywordBasedOnPage(String keyword, int pageNum, int pageSize) {
        return paginationHelper(byKeyword(keyword), pageNum, pageSize);
    }

    public List<Item> findItemsByCategoryBasedOnPage(Category category, int pageNum, int pageSize){
        return paginationHelper(byCategory(category), pageNum, pageSize);
    }

    public List<Item> findItemsBySellerBasedOnPage(Long id, int pageNum, int pageSize){
        return paginationHelper(bySeller(id), pageNum, pageSize);
    }


    public List<Item> findItemsByCategoryAndSellerBasedOnPage(Category category, Long sellerID, int pageNum, int pageSize){
        return paginationHelper(byCategoryAndSeller(category, sellerID), pageNum, pageSize);
    }

    public List<Item> findItemsByCategoryAndKeywordBasedOnPage(Category category, String keyword, int pageNum, int pageSize){
        return paginationHelper(byCategoryAndKeyword(category, keyword), pageNum, pageSize);
    }


    //////////////////////////////////////
    // Counts ////////////////////////////
    //////////////////////////////////////

    public Integer findItemsCount(){
        return findAllItems().size();
    }

    public Integer findItemsByKeywordCount(String keyword) {
        return findItemsByKeyword(keyword).size();
    }

    public Integer findItemsByCategoryCount(Category category){
        return findAllItemsByCategory(category).size();

    }

    public Integer findItemsBySellerCount(Long id){
        return findItemsBySeller(id).size();

    }

    public Integer findItemsByCategoryAndSellerCount(Category category, Long sellerID){
        return findItemsByCategoryAndSeller(category, sellerID).size();

    }

    public Integer findItemsByCategoryAndKeywordCount(Category category, String keyword){
        return findItemsByCategoryAndKeyword(category, keyword).size();
    }


    //////////////////////////////////////
    // Helpers ///////////////////////////
    //////////////////////////////////////

    private Query byCategory(Category category){
        Query query = entityManager.createQuery(
                "select i from Item i where i.category = ?1 order by i.endtime desc");
        Query query2 = query.setParameter(1, category);
        return query2;
    }

    private Query bySeller(Long sellerID){
        Query query = entityManager.createQuery(
                "select i from Item i where i.userr.id = ?1 order by i.endtime desc");
        Query query2 = query.setParameter(1, sellerID);
        return query2;
    }

    private Query byKeyword(String keyword){
        String match = "%" + keyword.toLowerCase() + "%";
        Query query = entityManager.createQuery(
                "select i from Item i where lower(i.name) like ?1 order by i.endtime desc");
        Query query2 = query.setParameter(1, match);
        return query2;
    }

    private Query byCategoryAndKeyword(Category category, String keyword){
        String match = "%" + keyword.toLowerCase() + "%";
        Query query = entityManager.createQuery(
                "select i from Item i where lower(i.name) like ?1 and i.category = ?2 order by i.endtime desc");
        Query query2 = query.setParameter(1, match);
        query2.setParameter(2, category);
        return query2;
    }

    private Query byCategoryAndSeller(Category category, Long sellerID){
        Query query = entityManager.createQuery(
                "select i from Item i where i.category = ?1 and i.userr.id = ?2 order by i.endtime desc");
        Query query2 = query.setParameter(1, category);
        query2.setParameter(2, sellerID);
        return query2;
    }

    private List<Item> paginationHelper(Query query, int pageNum, int pageSize){
        query.setFirstResult((pageNum-1)*pageSize);
        query.setMaxResults(pageSize);
        List<Item> itemList=query.getResultList();
        return itemList;
    }

}