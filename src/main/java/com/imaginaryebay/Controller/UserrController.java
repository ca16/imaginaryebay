package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import com.sun.mail.iap.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Chloe on 6/30/16.
 */

@RestController
@RequestMapping("/user")

public interface UserrController {


    @RequestMapping (value="/new", method = RequestMethod.POST)
    public ResponseEntity<Void> createNewUserr(@RequestBody  Userr userr);


    @RequestMapping (value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Userr> getUserrByID(@PathVariable("id") Long id);

    @RequestMapping (value="/email/{email}/", method = RequestMethod.GET)
    public ResponseEntity<Userr> getUserrByEmail(@PathVariable("email") String email);

    @RequestMapping (method = RequestMethod.GET)
    public ResponseEntity<List<Userr>> getAllUserrs();

    @RequestMapping (value="/name/{name}",method = RequestMethod.GET)
    public ResponseEntity<List<Userr>> getUserrByName(@PathVariable("name") String name);

    @RequestMapping (value="/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Userr> updateUserrByID(@PathVariable("id")Long id, @RequestBody  Userr u);


    @RequestMapping (value="/item/{id}",method=RequestMethod.GET)
    public ResponseEntity<List<Item>> getItemsSoldByThisUser(@PathVariable("id")Long id);

    @RequestMapping (value="/{id}/lockout",method=RequestMethod.PUT)
    public ResponseEntity<Userr> lockout(@PathVariable("id")Long id, @RequestParam(value = "state") Boolean state);

}
