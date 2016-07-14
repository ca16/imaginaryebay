package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Userr;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ben_Big on 6/27/16.
 */


@Component
public class UserrDaoImpl implements UserrDao, UserDetailsService{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException
    {
        Userr u=null;
        Query query=entityManager.createQuery("select u from Userr u where u.email=:E");
        query.setParameter("E",email);
        List<Userr> userrList=query.getResultList();
        Iterator<Userr> itr=userrList.iterator();
        if(itr.hasNext()){
            u=itr.next();
        }
        else{
            throw new UsernameNotFoundException("No such user with such email address: "+email);
        }

        // boolean fields required for User (the one in security.core.userdetails.User)
        boolean accountNonExpired=true;
        boolean credentialNonExpired=true;
        boolean accountNonLocked=true;
        boolean accountIsEnabled=true;


        List<GrantedAuthority> authList=new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("USER"));
        if (u.isAdmin()){
            authList.add(new SimpleGrantedAuthority("ADMIN"));
        }


        return new User(
                u.getEmail(),
                u.getPassword(),
                accountIsEnabled,
                accountNonExpired,
                credentialNonExpired,
                accountNonLocked,
                authList
        );

    }



    public void createNewUserr (Userr userr){
        entityManager.persist(userr);
    }


    //ToDo: For method that returns an whole object, use find() or getReference()
    //getReference() is actually problematic, as it is not called immediately.
    public Userr getUserrByID (long id){
        Userr userr=entityManager.find(Userr.class, id);
        return userr;
    }


    public Userr getUserByEmail (String email){
        String queryString = "SELECT u FROM Userr u WHERE u.email = :EMA";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("EMA",email);
        List<Userr> listOfUserr=query.getResultList();
        Iterator<Userr> itr=listOfUserr.iterator();
        if (itr.hasNext()){
            return itr.next();
        }
        else{
            return null;
        }

    }


}
