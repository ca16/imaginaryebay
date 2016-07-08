package com.imaginaryebay.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by Brian on 6/27/2016.
 *
 * ItemPicture - Entity object for managing images Auction Item images
 */

@Entity
@Table(name = "pic")
public class ItemPicture {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "item_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private Item auction_item;

    @Column(name = "url")
    private String url;

    public ItemPicture(){

    }

    public ItemPicture(Long id, String url){
        this.id = id;
        this.url = url;
    }

    public ItemPicture(String url){
        this.url = url;
    }

    public Item getAuction_item() {
        return auction_item;
    }

    public void setAuction_item(Item auction_item) {
        this.auction_item = auction_item;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ItemPicture{" +
                "url='" + url + '\'' +
                ", id=" + id +
                '}';
    }
    
}
