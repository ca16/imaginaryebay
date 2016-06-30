package com.imaginaryebay.Repository;
import com.imaginaryebay.Models.Userr;

/**
 * Created by Ben_Big on 6/27/16.
 */
public interface UserrRepository {

    public void createNewUserr(Userr userr);

    public Userr getUserrByID(Long id);

    public Userr getUserrByEmail(String email);

}
