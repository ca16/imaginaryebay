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
    public Userr getUserrByID (Long id){
        Userr userr=entityManager.find(Userr.class, id);
        //System.out.println(userr);
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
    @SuppressWarnings("unchecked")
	public List<Userr> findAllUserrs(){
    	Query query =entityManager.createQuery("select u from Userr u order by u.name");
    	return query.getResultList();
    }


	@Override
	public Userr getUserByName(String name) {
		String queryString = "SELECT u FROM Userr u WHERE u.name = :NAME";
        Query query = entityManager.createQuery(queryString);
        query=query.setParameter("NAME",name);
        List<Userr> listOfUserr=query.getResultList();
        Iterator<Userr> itr=listOfUserr.iterator();
        if (itr.hasNext()){
            return itr.next();
        }
        else{
            return null;
        }
	}


	@Override
	public String getEmailByUserID(Long id) {
		return entityManager.find(Userr.class,id).getEmail();
	}


	@Override
	public String getEmailByUserName(String name) {
		return getUserByName(name).getEmail();
	}


	@Override
	public String getAddressByUserID(Long id) {
		return entityManager.find(Userr.class,id).getAddress();
	}


	@Override
	public String getAddressByUserName(String name) {
		return getUserByName(name).getAddress();
	}


	@Override
	public Boolean isAdminByUserID(Long id) {
		return entityManager.find(Userr.class,id).getAdminFlag();
	}


	@Override
	public Userr updateUserByID(Long id, Userr userr) {
		Userr toChange = entityManager.find(Userr.class,id);
		toChange.setName(userr.getName());
		toChange.setEmail(userr.getEmail());
		toChange.setAddress(userr.getAddress());
		toChange.setPassword(userr.getPassword());
		toChange.setAdminFlag(userr.getAdminFlag());
		return entityManager.find(Userr.class,id);
	}


}
