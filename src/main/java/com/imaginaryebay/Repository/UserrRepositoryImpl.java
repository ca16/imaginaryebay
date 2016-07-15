package com.imaginaryebay.Repository;

import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ben_Big on 6/27/16.
 */


@ComponentScan("com.imaginaryebay")
@Transactional
public class UserrRepositoryImpl implements UserrRepository {

    @Autowired
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

}
