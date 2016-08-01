package com.imaginaryebay.Repository;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;

import java.util.List;

/**
 * Created by Ben_Big on 6/27/16.
 */
public interface UserrRepository {

    public void createNewUserr(Userr userr);

    public String getUserNameByID(Long id);

    public Userr getUserrByID(Long id);

    public Userr getUserrByEmail(String email);

    public List<Userr> getAllUserrs();

    public List<Userr> getUserrByName(String name);

    public Userr updateUserrByID(Long id, Userr u);

    public List<Item> getItemsSoldByThisUser(Long id);

    public Userr lockout(Long id, Boolean state);

}