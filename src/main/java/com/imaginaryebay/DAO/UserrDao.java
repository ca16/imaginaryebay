package com.imaginaryebay.DAO;


import java.util.List;

import com.imaginaryebay.Models.Userr;


/**
 * Created by Ben_Big on 6/27/16.
 */
public interface UserrDao {


	public void createNewUserr (Userr userr);

    public Userr getUserrByID (Long id);

    public Userr getUserByEmail(String email);
    
    public Userr getUserByName(String name);

	public List<Userr> findAllUserrs();
	
	public String getEmailByUserID(Long id);
    
    public String getEmailByUserName(String name);

    public String getAddressByUserID(Long id);
    
    public String getAddressByUserName(String name);
    
    public Boolean isAdminByUserID(Long id);

    public Userr updateUserByID(Long id, Userr userr);


}
