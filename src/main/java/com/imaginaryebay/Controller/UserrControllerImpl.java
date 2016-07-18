package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.UserrRepository;
import com.imaginaryebay.Repository.UserrRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Ben_Big on 6/28/16.
 */


@ComponentScan("com.imaginaryebay.Repository")

public class UserrControllerImpl implements UserrController {

    @Autowired
    private UserrRepository userrRepository;

    @Override
    public void createNewUserr(Userr userr){
        userrRepository.createNewUserr(userr);
    }

    @Override
    public Userr getUserrByID(Long id){
        return userrRepository.getUserrByID(id);
    }

    @Override
    public Userr getUserrByEmail(String email){
        return userrRepository.getUserrByEmail(email);
    }

    @Override
    public List<Userr> getAllUserrs (){ return userrRepository.getAllUserrs();}

    @Override
    public List<Userr> getUserrByName(String name){
        return userrRepository.getUserrByName(name);
    }

    @Override
    public Userr updateUserrByID(Long id, Userr u){
        return userrRepository.updateUserrByID(id,u);
    }

}
