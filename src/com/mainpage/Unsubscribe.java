package com.mainpage;

import com.admin.properties.PropertyLoader;
import com.dbInterface.UnsubscribeUser;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Unsubscribe handles unsubscribe requests for newsletters 
 */
public class Unsubscribe extends ActionSupport{
	
	private int retVal = 3;
	private String token = "";
	private String email = "";

	/**
	 * execute is called during execution to render the page
	 * 
	 * @return success
	 */
	public String execute() throws Exception{
		
		 if(token.length() > 0 || email.length() > 0){
			 
			 UnsubscribeUser n = new UnsubscribeUser();
			 retVal = n.updateUserPref(token, email);
		 }

		 return  SUCCESS; 
    }
	/**
	 * setToken sets the unsubscribe token
	 * 
	 * @param token is the token being sent
	 */
	public void setToken(String token){
		
		this.token = token;
	}
	
	/**
	 * setEmail sets the email address
	 * 
	 * @param email is the address of the individual wanting to unsubscribe
	 */
	public void setEmail(String email){
		
		this.email = email;
	}
	
	/**
	 * getReturnVal returns the response to the user
	 * 
	 * @return is the response
	 */
	public String getReturnVal(){
		
		String resp = "";
		
		if(this.retVal == 0){
			
			resp = "The email: " + this.email + " has been unsubscribed from email updates.";
		}
		else if(this.retVal == 2){
			
			resp = "The unsubscribe token has either expired or already been used, please try using a newer email message link. Note that you have "
					+ PropertyLoader.mailUnsubscribeWeeks + " week(s) to use the unsubscribe links in emails.";
		}
		else if(this.retVal == 3){
			
			resp = "Email not provided for unsubscribe, please use link in emails.";
		}
		else{
			
			resp = "There was an error unsubscribing email: " + this.email + ". Please try again later.";
		}
		
		return resp;
	}
}
