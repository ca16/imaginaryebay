package com.imaginaryebay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.imaginaryebay.DAO.BiddingDAO;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.DAO.MessageDao;
import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Message;
import com.imaginaryebay.Models.Userr;

public class SendEmail extends TimerTask{
	
	@Autowired
	BiddingDAO biddingDAO;
	@Autowired
	MessageDao messageDao;
	@Autowired
	ItemDAO itemDAO;
	@Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage itemSoldMessage;
    @Autowired
    private SimpleMailMessage itemWonMessage;
    @Autowired 
    SimpleMailMessage itemLostMessage;
	
	private Long itemId;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public SendEmail() {
	
	}

	//@Override
	public void run() {
		SimpleMailMessage msgToItemOwner = new SimpleMailMessage(this.itemSoldMessage);
        msgToItemOwner.setTo(this.itemDAO.findOwnerByID(this.itemId).getEmail());
        msgToItemOwner.setText("Dear " + this.itemDAO.findOwnerByID(this.itemId).getName() + ", your auction is now over. " + this.itemDAO.findNameByID(this.itemId) + " has sold for $" + this.itemDAO.findHighestBidByID(this.itemId) + ". Thank you for using our site.");
        try {
            this.mailSender.send(msgToItemOwner);
            System.out.println("Message sent successfully");
            this.messageDao.persist(new Message(this.itemDAO.findOwnerByID(this.itemId),this.itemDAO.findEndtimeByID(this.itemId)));
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
        List<Bidding> itemBids = this.biddingDAO.getBiddingByItem(this.itemDAO.findByID(this.itemId));
        List<Userr> bidders = new ArrayList<>();
        for(Bidding b : itemBids){
        	if(!bidders.contains(b.getUserr())){
        		bidders.add(b.getUserr());
        	if(b.getPrice()==this.itemDAO.findHighestBidByID(this.itemId)){
        		SimpleMailMessage msgToItemWinner = new SimpleMailMessage(this.itemWonMessage);
                msgToItemWinner.setTo(b.getUserr().getEmail());
                msgToItemWinner.setText("Dear " + b.getUserr().getName() + ", you placed the highest bid on " + this.itemDAO.findNameByID(this.itemId) + ". The auction is now over and you will receive the item in 7-10 business days. Congratulations and thank you for using our site.");
                try {
                    this.mailSender.send(msgToItemWinner);
                    System.out.println("Message sent successfully");
                    this.messageDao.persist(new Message(b.getUserr(),this.itemDAO.findEndtimeByID(this.itemId)));
                } catch (MailException ex) {
                    System.err.println(ex.getMessage());
                }
        	}else{
        		SimpleMailMessage msgToItemLoser = new SimpleMailMessage(this.itemLostMessage);
                msgToItemLoser.setTo(b.getUserr().getEmail());
                msgToItemLoser.setText("Dear " + b.getUserr().getName() + ", you did not place the highest bid on " + this.itemDAO.findNameByID(this.itemId) + ". The auction is now over. Thank you for using our site. Please come back and try again! New items are added to ImaginaryEbay every day!");
                try {
                    this.mailSender.send(msgToItemLoser);
                    System.out.println("Message sent successfully");
                    this.messageDao.persist(new Message(b.getUserr(),this.itemDAO.findEndtimeByID(this.itemId)));
                } catch (MailException ex) {
                    System.err.println(ex.getMessage());
                }
        	}
        }
        }
	}
}
