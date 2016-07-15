package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.UserrRepository;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Chloe on 6/30/16.
 */

@RestController
@RequestMapping("/user")
public interface UserrController {

    /*
        @RequestMapping (method = RequestMethod.POST)
        public void saveUserr(@RequestBody Userr userr){
            userrRepository.createNewUserr(userr);
        }
    */

    @RequestMapping (method = RequestMethod.GET)
    public List<Userr> getAllUserrs();

    @RequestMapping (value="/{id}", method = RequestMethod.GET)
    public Userr getUserrByID(@PathVariable("id") Long id);
}
