package com.imaginaryebay.Models;



import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imaginaryebay.Models.Userr;

@Entity
@Table(name="Message")
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "r_id")
    private Userr receiver_id;

    @Column(name="date_sent")
    private Timestamp date_sent;

    public Message(){

    }

    public Message(Userr receiver_id, Timestamp date_sent) {
        this.receiver_id = receiver_id;
        this.date_sent = date_sent;
    }

    public Userr getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(Userr receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Timestamp getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(Timestamp date_sent) {
        this.date_sent = date_sent;
    }

    public Long getId() {
        return id;
    }

}