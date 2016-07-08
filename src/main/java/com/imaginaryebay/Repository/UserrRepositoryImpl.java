package com.imaginaryebay.Repository;

import java.util.List;

import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ben_Big on 6/27/16.
 */



@Transactional
public class UserrRepositoryImpl implements UserrRepository {

	private UserrDao userrDao;

    public void setUserrDao(UserrDao userrDao){
        this.userrDao=userrDao;
    }

    //ToDo: First see if this newUserr exists or not, only create if it does not exist. If it does exist I probably should throw an exception here
    public void createNewUserr(Userr newUserr) {
        Userr u=userrDao.getUserByEmail(newUserr.getEmail());
        if (u==null) {
            userrDao.createNewUserr(newUserr);
        }
        else{
            System.out.println("The user has existed");
        }
    }


    public Userr getUserrByID (Long id) {
        return userrDao.getUserrByID(id);
    }


    public Userr getUserrByEmail(String email){
        return userrDao.getUserByEmail(email);
    }
    
    public List<Userr> returnAllUserrs(){
    	return userrDao.findAllUserrs();
    }

	@Override
	public Userr getUserByEmail(String email) {
		return userrDao.getUserByEmail(email);
	}

	@Override
	public Userr getUserByName(String name) {
		return userrDao.getUserByName(name);
	}

	@Override
	public String getAddressByUserID(Long id) {
		return userrDao.getAddressByUserID(id);
	}

	@Override
	public String getAddressByUserName(String name) {
		return userrDao.getAddressByUserName(name);
	}

	@Override
	public String getEmailByUserID(Long id) {
		return userrDao.getEmailByUserID(id);
	}

	@Override
	public String getEmailByUserName(String name) {
		return userrDao.getEmailByUserName(name);
	}

	@Override
	public Boolean isAdminByUserID(Long id) {
		return userrDao.isAdminByUserID(id);
	}

	@Override
	public Userr updateUserByID(Long id, Userr userr) {
		return userrDao.updateUserByID(id,userr);
	}
}
