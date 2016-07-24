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
/**
 * Represents an email message with its receiver and date sent.
 *
 */
@Entity
@Table(name="Message")
public class Message {
	/**
	 * The unique id of the email message.
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    /**
     * The user to whom the email message was sent.
     */
    @ManyToOne
    @JoinColumn(name = "r_id")
    @JsonIgnore
    private Userr receiver_id;
    /**
     * The time and date the email message was sent.
     */
    @Column(name="date_sent")
    private Timestamp date_sent;

    public Message(){

    }
    /**
     * Creates a new email message given its receiver and date sent.
     * @param receiver_id the user to whom the email message was sent
     * @param date_sent the time and date the email message was sent
     */
    public Message(Userr receiver_id, Timestamp date_sent) {
        this.receiver_id = receiver_id;
        this.date_sent = date_sent;
    }
    /**
     * 
     * @return the user to whom the email message was sent
     */
    public Userr getReceiver_id() {
        return receiver_id;
    }
    /**
     * Set the given user as the email message's receiver.
     * @param receiver_id the user to whom the email message was sent
     */
    public void setReceiver_id(Userr receiver_id) {
        this.receiver_id = receiver_id;
    }
    /**
     * 
     * @return the time and date the email message was sent
     */
    public Timestamp getDate_sent() {
        return date_sent;
    }
    /**
     * Set the given timestamp as the email message's date sent.
     * @param date_sent the time and date the email message was sent.
     */
    public void setDate_sent(Timestamp date_sent) {
        this.date_sent = date_sent;
    }
    /**
     * 
     * @return the email message's unique id
     */
    public Long getId() {
        return id;
    }
    /**
     * Set the given id number as the email message's unique id.
     * @param id the email message's unique id number
     */
    public void setId(Long id){
    	this.id=id;
    }

	@Override
	public String toString() {
		return "Message [receiver_id=" + receiver_id + ", date_sent="
				+ date_sent + "]";
	}
    
    

}