package com.imaginaryebay.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Ben_Big on 6/27/16.
 */


@Entity
@Table(name="Userr")
public class Userr {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }
    
    private String email;
    private String name;
    private String password;
    private boolean isAdmin;




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }




    public Userr(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}
}
