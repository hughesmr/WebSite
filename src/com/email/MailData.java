package com.email;

/**
 * MailData is an ADT to hold mail data
 */
public class MailData {
	
	private String from;
	private String subject;
	private String body;
	private String date;
	private boolean recMail;
	
	/**
	 * Constructor 
	 *  
	 * @param from is who the message is from
	 * @param subject is the subject of the message
	 * @param body is the body of the message
	 * @param recMail is whether or not the individual should receive mail
	 * @param date is the date of posting
	 */
	public MailData(String from, String subject, String body, boolean recMail, String date){
		
		this.from = from;
		this.subject = subject;
		this.body = body;
		this.recMail = recMail;
		this.date = date;
		
	}
	
	/**
	 * getFrom returns the from address
	 * 
	 * @return the from address
	 */
	public String getFrom(){
		
		return from;
	}
	
	/**
	 * getDate returns the date
	 * 
	 * @return the date
	 */
	public String getDate(){
		
		return date;
	}
	
	/**
	 * getSubject returns the subject
	 * 
	 * @return the subject
	 */
	public String getSubject(){
		
		return subject;
	}
	
	/**
	 * getBody returns the body
	 * 
	 * @return the body
	 */
	public String getBody(){
		
		return body;
	}
	
	/**
	 * getRecMail returns if the user should receive mail
	 * 
	 * @return if the user receives mail
	 */
	public boolean getRecMail(){
		
		return recMail;
	}
}
