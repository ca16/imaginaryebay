package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.UserrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ben_Big on 6/28/16.
 */


@ComponentScan("com.imaginaryebay.Repository")

public class UserrControllerImpl implements UserrController {

    @Autowired
    private UserrRepository userrRepository;

    public void setUserrRepository(UserrRepository userrRepository){
        this.userrRepository = userrRepository;
    }

    @Override
    public ResponseEntity<Void> createNewUserr(Userr userr){
        userrRepository.createNewUserr(userr);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<String> getUserNameByID(Long id){
        return new ResponseEntity<String>(userrRepository.getUserNameByID(id),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Userr> getUserrByID(Long id){
        return new ResponseEntity<>(userrRepository.getUserrByID(id), HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Userr> getUserrByEmail(String email){
        return new ResponseEntity<>(userrRepository.getUserrByEmail(email), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Userr>> getAllUserrs (){
        return new ResponseEntity<>(userrRepository.getAllUserrs(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Userr>> getUserrByName(String name){
        return new ResponseEntity<>(userrRepository.getUserrByName(name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Userr> updateUserrByID(Long id, Userr u){
        return new ResponseEntity<>(userrRepository.updateUserrByID(id, u), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Item>> getItemsSoldByThisUser(Long id){
        return new ResponseEntity<>(userrRepository.getItemsSoldByThisUser(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Userr> lockout(Long id, Boolean state){
        return new ResponseEntity<>(userrRepository.lockout(id, state), HttpStatus.OK);
    }

}
