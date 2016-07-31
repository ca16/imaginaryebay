package com.imaginaryebay.Models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Ben_Big on 7/24/16.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id","price"}))
public class Bidding {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="item_id")
    Item item;

    @OneToOne
    @JoinColumn(name="userr_id")
    Userr userr;

    Double price;

    Timestamp biddingTime;

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Userr getUserr() {
        return userr;
    }

    public void setUserr(Userr userr) {
        this.userr = userr;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(Timestamp biddingTime) {
        this.biddingTime = biddingTime;
    }



}
