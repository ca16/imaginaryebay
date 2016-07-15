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

/**
 * Created by Ben_Big on 6/28/16.
 */


//@RestController
//@RequestMapping("/user")
@ComponentScan("com.imaginaryebay.Repository")

public class UserrRestController implements UserrController {

    @Autowired
    private UserrRepository userrRepository;

    /*public void setUserrRepository(UserrRepository userrRepository){
        this.userrRepository=userrRepository;
    }*/

/*
    @RequestMapping (method = RequestMethod.POST)
    public void saveUserr(@RequestBody Userr userr){
        userrRepository.createNewUserr(userr);
    }
*/
    //@RequestMapping (value="/{id}", method = RequestMethod.GET)
    //public Userr getUserrByID(@PathVariable ("id") Long id){
    public Userr getUserrByID(Long id){
        return userrRepository.getUserrByID(id);
    }



}
