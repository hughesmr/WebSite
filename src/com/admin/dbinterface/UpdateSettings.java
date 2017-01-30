package com.admin.dbinterface;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.admin.properties.PropertyLoader;

public class UpdateSettings {
	
	private DataSource dataSource = null; 			 
    private JdbcTemplate jdbcTemplateObject = null;  
    
	private static final String COUNT = "SELECT count(*) FROM property WHERE name = ?"; 
	private static final String DUPS = "SELECT count(*) FROM property WHERE name" +
			" = ? AND propval = ?"; 
	private static final String INSERT = "INSERT INTO property (id, name, propval) " 
			+ "VALUES (DEFAULT, ?, ?)";
	private static final String UPDATE = "UPDATE property SET propval = ? WHERE name = ?"; 
	private static final String DELETE = "DELETE FROM property WHERE propval = ? AND name = ?";
	
    /**
     * Constructor
     */
    public UpdateSettings(){
    	
    	@SuppressWarnings("resource")
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");
		jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
     
    /**
     * updateProperty inserts or updates properties
     * 
     * @param prop is an array of properties
     * @return 0 if inserted, 1 if updated and exists or 2 if it is just a banned word update
     */
	public int updateProperty(String[] prop){
		
		if(checkDuplicate(prop)){ // If property does not exist
		
			if(checkExist(prop) || prop[0].equals("bannedWord")){
				insertProp(prop);
			}
			else{
				updateProp(prop);
			}
			updateSystemProps(prop[0]);
			return 0;
		} 
		else{ // Else property exists
			if(!prop[0].equals("bannedWord") && checkExist(prop)){ // If property exists but is updated
			
				updateProp(prop); 
				updateSystemProps(prop[0]);
				return 1;
			} 
			else if(prop[0].equals("bannedWord")){
				
				removeProp(prop);
				updateSystemProps(prop[0]);
				return 2;
			}
			else{
				return 1;
			}
		} 
	} 
	
	/**
	 * updateSystemProps updates system properties
	 * 
	 * @param type is the type of update
	 */
	private void updateSystemProps(String type){
		
		LoadProperties props = new LoadProperties();
		
		if(type.equals("bannedWord")){
			PropertyLoader.bannedWords = props.getBannedWords();
		}
		else if(type.equals("instance.url")){
			PropertyLoader.url = props.getUrl();
		}
		else if(type.equals("batch.email")){
			PropertyLoader.mailBatch = props.getMailBatch();
		}
		else if(type.equals("instance.admin.email")){
			PropertyLoader.adminEmail = props.getAdminEmail();
		}
		else if(type.equals("from.email")){
			PropertyLoader.fromAdress = props.getFromAdress();
		}
		else if(type.equals("sleep.email")){
			PropertyLoader.sleepTime = props.getSleepTime();
		}
		else if(type.equals("unsubscribe.time.email")){
			PropertyLoader.mailUnsubscribeWeeks = props.getMailUnsubscribe();
			PropertyLoader.mailUnsubscribe = PropertyLoader.mailUnsubscribeWeeks * 604800000;
		}
	}
		
	/**
	 * checkExist checks if the property exists 
	 * 
	 * @param prop are the props to check
	 * 
	 * @return true if the property exists 
	 */
	private boolean checkExist(String[] prop){
		
		int count = 0;												   
		
		count = jdbcTemplateObject.queryForObject(COUNT, 
				new Object[] {prop[0]}, Integer.class); 

		if(count > 0){ // If prop does not exist
			return false;
		} 
		else{ // Else prop does exist
			return true;
		}
	} 
	
	/**
	 * checkDuplicate checks for duplicate properties
	 * 
	 * @param prop are the props to check 
	 * @return false if the properties exist 
	 */
	private boolean checkDuplicate(String[] prop){
		
		int count = 0;												                  
		
		count = jdbcTemplateObject.queryForObject(DUPS, 
				new Object[] {prop[0], prop[1]}, Integer.class);

		if(count > 0){ 
			return false;
		} 
		else{ 
			return true;
		} 
	} 
	
	/**
	 * insertProp inserts properties 
	 * 
	 * @param prop are the properties to insert
	 */
	private void insertProp(String[] prop){
		
		jdbcTemplateObject.update(INSERT, new Object[] {prop[0], prop[1]});
		
		if(prop[0].equals("bannedWord")){ // If bannedWords update
			LoadProperties props = new LoadProperties();
			PropertyLoader.bannedWords = props.getBannedWords(); // Update list of bannedWords
		} 
	} 
	
	/**
	 * updateProp updates properties 
	 * 
	 * @param prop are the properties to update
	 */
	private void updateProp(String prop[]){
				
		jdbcTemplateObject.update(UPDATE, new Object[] {prop[1], prop[0]});
	} 
	
	/**
	 * removeProp removes properties from the db 
	 * 
	 * @param prop are the properties to update
	 */
	private void removeProp(String prop[]){
		
		jdbcTemplateObject.update(DELETE, new Object[] {prop[1], prop[0]});	
	} 
} 
