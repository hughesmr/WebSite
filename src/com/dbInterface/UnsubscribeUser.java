package com.dbInterface;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * This class is called to unsubscribe users from newsletters
 */
public class UnsubscribeUser {

    private DataSource dataSource; 			 
    private JdbcTemplate jdbcTemplateObject; 
    
	private static final String CHECK_TOKEN = "SELECT count(*) FROM unsubscribe WHERE token = ? AND address = ?";
	private static final String UPDATE_MAIL_PREFS = "UPDATE users SET recieveemail = FALSE WHERE email = ?";
	private static final String DELETE_TOKEN = "DELETE FROM unsubscribe WHERE token = ? AND address = ?";
	
    /**
     * Constructor
     */
	public UnsubscribeUser() {
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	/**
	 * updateUserPref updates user email preferences 
	 * 
	 * @param token the token to remove
	 * @param email the associated email
	 * @return 2 if the token is not found, 0 if successful and 1 if an error
	 */
	public int updateUserPref(String token, String email){
		
		int up = 0;
		
		if(tokenStatus(token, email) == 0){
			return 2;
		}
		
		up = update(email);
		removeToken(token, email);
		
		if(up > 0){
			return 0;
		}
		else{
			return 1;
		}	
	}
	
	/**
	 * tokenStatus determines if the token is valid 
	 * 
	 * @param token the token to remove
	 * @param email the associated email
	 * @return the number or rows returned
	 */
	private int tokenStatus(String token, String email){
		
		return jdbcTemplateObject.queryForObject(CHECK_TOKEN, new Object[] {token, email}, Integer.class);
	}
	
	/**
	 * update updates newsletter settings
	 * 
	 * @param email the email to update
	 * @return the number of rows updated
	 */
	private int update(String email){
		
		return jdbcTemplateObject.update(UPDATE_MAIL_PREFS, new Object[] {email});
	}
	
	/**
	 * removeToken removes unsubscribe tokens
	 * 
	 * @param token the token to remove
	 * @param email the associated email
	 * @return the number or rows removed
	 */
	private int removeToken(String token, String email){
		
		return jdbcTemplateObject.update(DELETE_TOKEN, new Object[] {token, email});
	}
}
