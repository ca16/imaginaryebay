package com.imaginaryebay.Models;

public class ContactFormEmail {
	private String name;
	private String emailAddress;
	private String emailContent;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	
	public ContactFormEmail(){
		
	}
	public ContactFormEmail(String name, String emailAddress, String emailContent) {
		this.name = name;
		this.emailAddress = emailAddress;
		this.emailContent = emailContent;
	}
	
	
}
