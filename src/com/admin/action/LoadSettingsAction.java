package com.admin.action;

import java.util.List;

import com.admin.dbinterface.LoadSettings;
import com.admin.properties.PropertyLoader;
import com.opensymphony.xwork2.ActionSupport;

/**
 * LoadSettingsActions loads the settings
 */
public class LoadSettingsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	/** 
	 * execute is called on page load
	 */
	public String execute() throws Exception {
		
		return  SUCCESS;
	}
	
	/**
	 * getBanWords gets the banned words
	 * 
	 * @return a list of banned words
	 */
	public List<String> getBanWords(){
		
		return PropertyLoader.bannedWords;
	} 
	
	/**
	 * getEmailSleep gets the email batch sleep time
	 * 
	 * @return the email sleep time
	 */
	public int getEmailsleep(){
		
		return PropertyLoader.sleepTime;
	}
	
	/**
	 * getFromAdd returns the from address
	 * 
	 * @return the from address
	 */
	public String getFromadd(){
		
		return PropertyLoader.fromAdress;
	}
	
	/**
	 * getAdminEmail returns the admin email
	 * 
	 * @return admin email
	 */
	public String getAdminemail(){
		
		return PropertyLoader.adminEmail;
	}
	
	/**
	 * getMailBatch returns the mail batch size
	 * 
	 * @return the batch size
	 */
	public int getMailbatch(){
		
		return PropertyLoader.mailBatch;
	}
	
	/**
	 * getUrl gets the url property
	 * 
	 * @return the url
	 */
	public String getUrl(){
		
		return PropertyLoader.url;
	}
	
	/**
	 * getUnsubscribeEmail gets the unsubscribe email property
	 * 
	 * @return the unsubscribe email property
	 */
	public long getUnsubscribeEmail(){
		
		return PropertyLoader.mailUnsubscribeWeeks;
	}
}