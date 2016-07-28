package com.imaginaryebay.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben_Big on 6/27/16.
 */


@Entity
@Table(name="Userr")
public class Userr implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    @Column(nullable = false, unique=true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private boolean isAdmin;

    @Column(nullable = true)
    private String address;



    protected Userr(){}


    public Userr(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.isAdmin=false;
    }

    public Userr(String email, String name, String password, boolean isAdmin) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.isAdmin=false;
    }



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


    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


	@Override
	public String toString() {
		return "Userr [id=" + id + ", email=" + email + ", name=" + name
				+ ", password=" + password + ", isAdmin=" + isAdmin
				+ ", address=" + address + "]";
	}
    

}
