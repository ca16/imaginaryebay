package com.imaginaryebay.DAO;


import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

import java.util.List;


/**
 * Created by Ben_Big on 6/27/16.
 */
public interface UserrDao {


    public void persist (Userr userr);

    public Userr getUserrByID (long id);

    public Userr getUserrByEmail(String email);

    public List<Userr> getAllUserrs();

    public List<Userr> getUserrByName(String name);

    public void updateUserrByID(long id,Userr u);

    public List<Item> getItemsSoldByThisUser(Long id);
}