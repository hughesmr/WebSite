package com.admin.dbinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * This class is used to load system
 * properties from the DB
 * 
 */
public class LoadProperties {
	
	private ApplicationContext context = null;
	private DataSource dataSource = null; 			
    private JdbcTemplate jdbcTemplateObject = null; 
    
	private static final String BANNED_WORDS = "SELECT propval FROM property" +
			" WHERE name = 'bannedWord'";
	private static final String FROM_EMAIL = "SELECT propval FROM property " +
			"WHERE name = 'from.email'";
	private static final String SLEEP_TIME = "SELECT propval FROM property " + 
			"WHERE name = 'sleep.email'";
	private static final String BATCH_EMAIL = "SELECT propval FROM property " + 
			"WHERE name = 'batch.email'";
	private static final String URL = "SELECT propval FROM property " +
			"WHERE name = 'instance.url'";
	private static final String ADMIN_EMAIL = "SELECT propval FROM property " +
			"WHERE name = 'instance.admin.email'";
	private static final String TOKEN_LIFE = "SELECT propval FROM property " +
			"WHERE name = 'unsubscribe.time.email'";
	
    /**
     * Constructor
     */
	public LoadProperties(){
		
		context = new ClassPathXmlApplicationContext("db-beans.xml");  	   
		dataSource = (DataSource)context.getBean("dataSource");  
		jdbcTemplateObject = new JdbcTemplate(dataSource);     
	} 
	
	/**
	 * getBannedWords gets the banned words
	 * 
	 * @return the banned words list
	 */
	public List<String> getBannedWords(){
		
		List<String> bannedWords = new ArrayList<String>();
		
		List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(BANNED_WORDS); 
		
		for (Map<String, Object> row : rows) { // For loop through results 
			bannedWords.add(((String)row.get("propVal")).replaceAll("\\s+",""));
		} 
		
		if(bannedWords.isEmpty()){ // If there aren't any banned words
			bannedWords = null;
		} 
		
		return bannedWords;
	}
	
	/**
	 * getFromAddress gets the systems from email address
	 * 
	 * @return the from email address or an empty string
	 */
	public String getFromAdress(){
		
		String from = null;

		try{
			from = jdbcTemplateObject.queryForObject(FROM_EMAIL, String.class);
			return from.replaceAll("\\s+","");
		} catch (EmptyResultDataAccessException e) {
			return "";
		}
	}
	
	/**
	 * getSleepTime gets the sleep time
	 * 
	 * @return return sleep time for batch task 
	 */
	public int getSleepTime(){
		
		int sleepTime = 0;
		
		try{
			sleepTime = jdbcTemplateObject.queryForObject(SLEEP_TIME, Integer.class);
			return sleepTime;
		} catch (EmptyResultDataAccessException e) {
			return 5000;
		}
	}
	
	/**
	 * getMailBatch gets the email batch size property
	 * 
	 * @return the batch email property
	 */
	public int getMailBatch(){
		
		int batch = 0;
	
		try{
			batch = jdbcTemplateObject.queryForObject(BATCH_EMAIL, Integer.class);
			return batch;
		} catch (EmptyResultDataAccessException e) {
			return 500;
		}
	}
	
	/**
	 * getUrl gets the system URL
	 * 
	 * @return system url
	 */
	public String getUrl(){
		
		String url = "";
		
		try {	
			url = jdbcTemplateObject.queryForObject(URL, String.class);
			return url.replaceAll("\\s+","");
			
		} catch (EmptyResultDataAccessException e) {
			return "";
		}
	}
	
	/**
	 * getAdminEmail gets the admin email address
	 * 
	 * @return the admin email
	 */
	public String getAdminEmail(){
		
		String email = "";
		
		try{
			email = jdbcTemplateObject.queryForObject(ADMIN_EMAIL, String.class);
			return email.replaceAll("\\s+","");
		} catch (EmptyResultDataAccessException e) {
			return "";
		}
	}
	
	/**
	 * getMailUnsubscribe get the unsubscribe email token validity time
	 * 
	 * @return token validity time
	 */
	public long getMailUnsubscribe(){
		
		long val = 1;
		
		try{
			val = jdbcTemplateObject.queryForObject(TOKEN_LIFE, Long.class);
			return val;
		} catch (EmptyResultDataAccessException e) {
			return 1;
		}
	}
} 
