package com.imaginaryebay.Repository;

import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ben_Big on 6/27/16.
 */

@Component
@ComponentScan("com.imaginaryebay.DAO")
@Transactional
public class UserrRepositoryImpl implements UserrRepository {

	@Autowired
	private UserrDao userrDao;



	public void createNewUserr(Userr newUserr) {
		Userr u=userrDao.getUserrByEmail(newUserr.getEmail());
		if (u==null) {
			userrDao.persist(newUserr);
		}
		else{
			System.out.println("The user has existed");
		}
	}


	public Userr getUserrByID (Long id) {
		return userrDao.getUserrByID(id);
	}


	public Userr getUserrByEmail(String email){
		return userrDao.getUserrByEmail(email);
	}


	public List<Userr> getAllUserrs(){ return userrDao.getAllUserrs();}

	public List<Userr> getUserrByName(String name){ return userrDao.getUserrByName(name);}

	public Userr updateUserrByID (long id, Userr u){
		try{
			userrDao.updateUserrByID(id, u);
			return getUserrByID(id);
		}catch (UsernameNotFoundException unfe ){
			System.out.println("The user does not exist");
			return null;
		}

	}

}