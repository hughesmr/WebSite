package com.admin.scheduler;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * EmailTokenRemove is used as a task to remove email tokens
 */
public class EmailTokenRemoval {

	private static String REMOVE_TOKEN = "DELETE FROM unsubscribe WHERE date < ?";
	
	/**
	 * Constructor
	 */
	public EmailTokenRemoval() {
	}
	
	/**
	 * run is called when a task should be ran
	 */
	public void run() {
		
		removeTokens();
	}
	
	/**
	 * removeTokens removes unsubscribe tokens
	 */
	private void removeTokens(){
		
		long date = System.currentTimeMillis();
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		DataSource dataSource = (DataSource)context.getBean("dataSource");
		JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
		
		
		jdbcTemplateObject.update(REMOVE_TOKEN, new Object[] {date});
	}
}
