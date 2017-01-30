package com.admin.dbinterface;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * TemplateReset resets templates
 */
public class TemplateReset {
	
	private String type = "";						
	private ApplicationContext context = null;	
	private DataSource dataSource = null;			
	private JdbcTemplate jdbcTemplateObject = null; 
	
	private static final String DELETE = "DELETE FROM templates WHERE name = ?"; 

	/**
	 * Constructor 
	 * 
	 * @param type is the template type
	 */
	public TemplateReset(String type) {	
		
		this.type = type;
		context = new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");       
		jdbcTemplateObject = new JdbcTemplate(dataSource);            	
	}
	
	/**
	 * reset resets the templates
	 * 
	 * @return 0 if the template is reset
	 */
	public int reset(){
		
		int numRows = deleteTemplate();
		
		if(numRows > 0){ // If delete
			
			return 0;
		} 
		else{ // Else delete
			
			return 1;
		}
	}	
	
	/**
	 * deleteTemplate deletes overriden templates
	 * 
	 * @return the number of rows deleted
	 */
	private int deleteTemplate(){
		
		return jdbcTemplateObject.update(DELETE, new Object[] {this.type});
	}
}
