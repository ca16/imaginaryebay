package com.imaginaryebay.Models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Chloe on 6/23/16.
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private Double price;

    private Timestamp endtime;

    private String description;

    // TODO: @Brian: I had to add this to get the endpoints to work properly.
    @Enumerated(EnumType.STRING)
    private Category category;
/*
    @ManyToOne
    @JoinColumn(name = "userr_id")
    private Userr userr;*/

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
/*
    public Userr getUserr() {
        return userr;
    }

    public void setUserr(Userr userr) {
        this.userr = userr;
    }
    */
}
