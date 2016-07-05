package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Userr;

import javax.persistence.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Ben_Big on 6/27/16.
 */
public class UserrDaoImpl implements UserrDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void createNewUserr (Userr userr){
        entityManager.persist(userr);
    }


    //ToDo: For method that returns an whole object, use find() or getReference()
    //getReference() is actually problematic, as it is not called immediately.
    public Userr getUserrByID (long id){
        Userr userr=entityManager.find(Userr.class, id);
        return userr;
    }


    public Userr getUserByEmail (String email){
        String queryString = "SELECT u FROM Userr u WHERE u.email = :EMA";
        Query query = entityManager.createQuery(queryString);
        query = query.setParameter("EMA",email);
        List<Userr> listOfUserr=query.getResultList();
        Iterator<Userr> itr=listOfUserr.iterator();
        if (itr.hasNext()){
            return itr.next();
        }
        else{
            return null;
        }

    }


}
