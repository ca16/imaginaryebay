package com.imaginaryebay.DAO;


import com.imaginaryebay.Models.Userr;


/**
 * Created by Ben_Big on 6/27/16.
 */
public interface UserrDao {


    public void persist (Userr userr);

    public Userr getUserrByID (long id);

    public Userr getUserrByEmail(String email);



}
