package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.UserrRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Chloe on 6/30/16.
 */

@RestController
@RequestMapping("/user")

public interface UserrController {


    @RequestMapping (method = RequestMethod.POST)
    public void createNewUserr(@RequestBody  Userr userr);


    @RequestMapping (value="/{id}", method = RequestMethod.GET)
    public Userr getUserrByID(@PathVariable("id") Long id);

    @RequestMapping (value="/email/{email}/", method = RequestMethod.GET)
    public Userr getUserrByEmail(@PathVariable("email") String email);


    @RequestMapping (method = RequestMethod.GET)
    public List<Userr> getAllUserrs();

    @RequestMapping (value="/name/{name}",method = RequestMethod.GET)
    public List<Userr> getUserrByName(@PathVariable("name") String name);



    @RequestMapping (value="/{id}",method = RequestMethod.PUT)
    public Userr updateUserrByID(@PathVariable("id")Long id, @RequestBody  Userr u);




}
