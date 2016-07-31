package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ben_Big on 6/27/16.
 */

@Component
@ComponentScan("com.imaginaryebay.DAO")
@Transactional
public class UserrRepositoryImpl implements UserrRepository {

	// forbidden or anotherized?
	// at the moment forbidden gets returned for users that authenticated but do not have
	// the authority to access a specific resource, and unauthorized gets returned for
	// unathenticated users I think..

	private static final String NOT_AVAILABLE       = "Not available.";
	private static final String NO_AUTHORITY        = "You do not have the authority to ";
	private static final String NO_ENTRIES          = "There are no entries for the requested resource.";


	@Autowired
	private UserrDao userrDao;

	public void setUserrDao(UserrDao userrDao){
		this.userrDao = userrDao;
	}

	public void createNewUserr(Userr newUserr) {
		Userr u = userrDao.getUserrByEmail(newUserr.getEmail());
		if (u == null) {
			userrDao.persist(newUserr);
		}
		else{
			throw new RestException("User cannot be created.", "User with email " + newUserr.getEmail() + " already exists.", HttpStatus.BAD_REQUEST);
		}
	}


	public Userr getUserrByID (Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		Userr userr = userrDao.getUserrByID(id);
		if (userr == null) {
			throw new RestException(NOT_AVAILABLE, "User with ID " + id + " does not exist.", HttpStatus.OK);
		} else if (userr.getEmail().equals(email) | isAdmin) {
			return userr;
		} else {
			throw new RestException(NOT_AVAILABLE, NO_AUTHORITY + "view this user.", HttpStatus.FORBIDDEN);
		}
	}

	public Userr getUserrByEmail(String email) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String logInEmail = auth.getName();
		Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		if (email.equals(logInEmail) | isAdmin) {
			Userr userr = userrDao.getUserrByEmail(email);
			if (userr == null) {
				throw new RestException(NOT_AVAILABLE, "User with email " + email + " does not exist.", HttpStatus.OK);
			}
			return userr;
		} else {
			throw new RestException(NOT_AVAILABLE, NO_AUTHORITY + "view this user.", HttpStatus.FORBIDDEN);
		}
	}

	public List<Userr> getAllUserrs() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		if (isAdmin) {
			// exception or just an empty list?
			return userrDao.getAllUserrs();
		} else {
			throw new RestException(NOT_AVAILABLE, NO_AUTHORITY + "view all users.", HttpStatus.FORBIDDEN);
		}
	}

	public List<Userr> getUserrByName(String name){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		List<Userr> userrList = userrDao.getUserrByName(name);
		if (isAdmin){
			/*if (userrList.isEmpty()){
				// should we throw exceptions here or just return an empty list?
				throw new RestException(NO_ENTRIES, "There are no users with the name " + name, HttpStatus.OK);
			}*/
			return userrList;
		}
		Userr userrConcerned = null;
		Iterator<Userr> itr = userrList.iterator();
		while(itr.hasNext()){
			Userr temp = itr.next();
			if(temp.getEmail().equals(email)){
				userrConcerned = temp;
				break;
			}
		}
		List<Userr> result = new ArrayList<>();
		/*if (userrConcerned == null) {
			// Same here, return an empty list, or not?
			throw new RestException(NO_ENTRIES, "User with name " + name + " does not exist, or you do not have the authority to view them.", HttpStatus.OK);
		}*/
		if (userrConcerned != null) {
			result.add(userrConcerned);
		}
		return result;
	}

	public Userr updateUserrByID (Long id, Userr u){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		Userr temp = userrDao.getUserrByID(id);
		if(temp == null){
			throw new RestException(NOT_AVAILABLE, "User with ID " + id + " does not exist.", HttpStatus.BAD_REQUEST);
		}
		else if (isAdmin) {
			userrDao.updateUserrByID(id, u);
			return getUserrByID(id);
		}
		else if (temp.getEmail().equals(email)){
			userrDao.updateUserrByID(id, u);
			return getUserrByID(id);
		}
		else{
			throw new RestException(NOT_AVAILABLE, NO_AUTHORITY + "update this user.", HttpStatus.FORBIDDEN);
		}
	}

	public List<Item> getItemsSoldByThisUser(Long id) {

		Userr temp = userrDao.getUserrByID(id);
		if(temp == null){
			throw new RestException(NOT_AVAILABLE, "User with ID " + id + " does not exist.", HttpStatus.BAD_REQUEST);
		}
		return userrDao.getItemsSoldByThisUser(id);
	}

	public Userr lockout(Long id, Boolean state){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		Userr temp = userrDao.getUserrByID(id);
		if (!isAdmin) {
			throw new RestException(NOT_AVAILABLE, NO_AUTHORITY + "to lockout users.", HttpStatus.FORBIDDEN);
		}
		if(temp == null){
			throw new RestException(NOT_AVAILABLE, "User with ID " + id + " does not exist.", HttpStatus.BAD_REQUEST);
		}
		return userrDao.lockout(id, state);
	}


}