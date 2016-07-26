package com.imaginaryebay.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imaginaryebay.Controller.ItemController;
import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.Repository.ItemRepository;

import org.springframework.http.HttpStatus;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chloe on 6/23/16.
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Double highestBid;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Timestamp endtime;

    private String description;

    // TODO: @Brian: I had to add this to get the endpoints to work properly.
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "auction_item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemPicture> itemPictures = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Userr userr;

    public Long getId() {
        return id;
    }
    public void setId(Long id){
    	this.id=id;
    }
    public List<ItemPicture> getItemPictures(){
    	return itemPictures;
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

    public void setCategory(String category) {
        if (null == category){
            return;
        }
        //System.out.println(Category.Clothes.toString());
        //System.out.println(Category.valueOf("Clothes"));
        for (Category cat : Category.values()){
            if (cat.toString().equals(category)){
                this.category = Category.valueOf(category);
                return;
            }
        }
        this.category = Category.Invalid;

        //System.out.println("here");
        //throw new RestException("Invalid category name.", "Category " + category + " is not a valid category name", HttpStatus.BAD_REQUEST);
        //ItemController.printing(category);
        //ItemRepository.excHelper(category);
        //throw new RuntimeException("Category " + category + " is not a valid category name");

    }

    public void setCategory(Category category){
        this.category = category;
    }


    public void addItemPicture(ItemPicture itemPicture){
        itemPicture.setAuction_item(this);
        itemPictures.add(itemPicture);
    }

    public Userr getUserr() {
        return userr;
    }

    public void setUserr(Userr userr) {
        this.userr = userr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Double highestBid) {
        this.highestBid = highestBid;
    }

        @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", price=" + price +
                ", endtime=" + endtime +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", itemPictures=" + itemPictures +
                '}';
    }
}
