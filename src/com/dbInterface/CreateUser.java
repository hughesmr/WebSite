package com.dbInterface;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * This class is used to enter user info into the DB. It will not enter anything 
 * if the user already exists. 
 */
public class CreateUser {
	
	private DataSource dataSource = null; 			 
    private JdbcTemplate jdbcTemplateObject = null;  
    
    private static final String USER_EXISTS = "SELECT count(*) FROM users WHERE email = ?"; 
    private static final String MAIL_PREFS = "SELECT recieveemail FROM users WHERE email = ?";
    private static final String UPDATE_MAIL_PREFS = "UPDATE users SET recieveemail = ? WHERE email = ?";
    private static final String CREATE_USER = "INSERT INTO users (id, username, email, recieveemail, date) "
			+ "VALUES (DEFAULT, ?, ?, ?, ?)";
        
    /**
     * Constructor
     */
    public CreateUser(){
    	
		@SuppressWarnings("resource")
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");
		jdbcTemplateObject = new JdbcTemplate(dataSource);
    	
    }  
	
    /**
     * createUser is used as the interface to create new users. It handles all calls related to user creation
     * 
     * @param name is the name of the user
     * @param email the email of the user
     * @param recMail if they would like to receive email updates
     */
	public void createUser(String name, String email, boolean recMail){
		
		email = email.toLowerCase();
		
		if(userExists(email)){ // If user exists
			
			if(!checkEmailPrefs(recMail, email)){ // If prefs updated
				
				updateEmailPrefs(recMail, email);
			} 
		} 
		else{ // Else user does not exist
			
			createNewUser(name, email, recMail);	
		} 
	} 	
	
	/**
	 * userExists checks if the user exists
	 * 
	 * @param email is the email of the user
	 * @return true if the user exists
	 */
	private boolean userExists(String email){
		
		int count = 0;												
		
		count = jdbcTemplateObject.queryForObject(USER_EXISTS, 
				new Object[] {email.toLowerCase()}, Integer.class); // Run query 
		
		if(count > 0){ // If user exists
			
			return true;
		} 
		else{ // Else user does not exist
			
			return false;
		} 
	} 
	
	/**
	 * checkEmailPrefs checks the email preferences
	 * 
	 * @param recMail is the current mail settings
	 * @param email is the users email
	 * @return true if the users preferences have changed
	 */
	private boolean checkEmailPrefs(boolean recMail, String email){

		if(jdbcTemplateObject.queryForObject(MAIL_PREFS, 
				new Object[] {email}, Boolean.class) != recMail){ // If prefs haven't changed
			
			return false;
		} 
		else{ // Else prefs have changed
			
			return true;
		} 
	} 
	
	/**
	 * updateEmailPrefs updates email preferences
	 * 
	 * @param recMail is the new preference
	 * @param email is the users email address
	 */
	private void updateEmailPrefs(boolean recMail, String email){
		
		jdbcTemplateObject.update(UPDATE_MAIL_PREFS, new Object[] {recMail, email});
		
	} 	
	
	/**
	 * createNewUser creates new user
	 * 
	 * @param name the name of the user
	 * @param email is the users email
	 * @param recMail is the users mail prefs
	 */
	private void createNewUser(String name, String email, boolean recMail){
		
		Long date = System.currentTimeMillis();										 // Variable to hold user creation date
		
		jdbcTemplateObject.update(CREATE_USER, new Object[] {name, email, recMail, date});
		
	} 	
}
