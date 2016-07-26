package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ben_Big on 7/24/16.
 */

public class BiddingDAOImpl implements  BiddingDAO{

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public void persist (Bidding bidding){
        entityManager.persist(bidding);
    }

    @Override
    public Bidding getBiddingByID(long id){
        Bidding bidding=entityManager.find(Bidding.class,id);
        return bidding;
    }

    @Override
    public List<Bidding> getBiddingByUserrID (long id){
        //Return all the Biddings made by one user
        String queryString="SELECT b from Bidding b join b.userr u where u.id= :I";
        Query query=entityManager.createQuery(queryString);
        query.setParameter("I",id);
        return query.getResultList();
    }

    @Override
    public List<Bidding> getBiddingByItem (Item item){
        //Return all the Biddings on this item
        String queryString="SELECT b from Bidding b join b.item it where it= :IT";
        Query query=entityManager.createQuery(queryString);
        query.setParameter("IT",item);
        return query.getResultList();
    }


    @Override
    public Bidding getHighestBiddingForItem (Long id){
        //
        String queryString=
                "SELECT b from Bidding b join b.item it where it.id= :IT and b.price=(SELECT Max(b2.price) from Bidding b2 join b2.item it2 where it2.id= :IT2)";
        Query query=entityManager.createQuery(queryString);
        query.setParameter("IT",id);
        query.setParameter("IT2",id);
        List<Bidding> itemList= query.getResultList();

        Iterator<Bidding> itr=itemList.iterator();
        while(itr.hasNext()){
            return itr.next();
        }
        return null;
    }

}