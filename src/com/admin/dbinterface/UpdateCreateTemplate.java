package com.admin.dbinterface;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class UpdateCreateTemplate {
	
	private String type = "";					    
	private String templateBody = "";				
	private ApplicationContext context = null;		
	private DataSource dataSource = null;			
	private JdbcTemplate jdbcTemplateObject = null;
	
	private static final String TEMPLATE_COUNT = "SELECT COUNT(*) FROM templates WHERE name = ?";
	private static final String UPDATE = "UPDATE templates SET body = ? WHERE name = ?";	
	private static final String INSERT = "INSERT INTO templates (id, name, body) " 
			+ "VALUES (DEFAULT, ?, ?)";
	
	/**
	 * Constructor 
	 * 
	 * @param templateBody is the body of the template
	 * @param type is the type of template
	 */
	public UpdateCreateTemplate(String templateBody, String type){
		
		this.type = type;				
		this.templateBody = templateBody;  
		context = new ClassPathXmlApplicationContext("db-beans.xml");
		dataSource = (DataSource)context.getBean("dataSource");       
		jdbcTemplateObject = new JdbcTemplate(dataSource);            
	}
	
	/**
	 * startUpdateOrInsert determines if an update or insert is needed.
	 * 
	 * @return 0 if update/insert is successful 1 if not
	 */
	public int startUpdateOrInsert(){
		
		int rows = 0; // Int to hold number of rows updated
		
		if(checkTemplate()){// If template exists already
			rows = updateTemplate();
		}
		else{// Else template create
			rows = insertTemplate();
		}
		
		if(rows > 0){// If tempalte updated/inserted
			return 0;
		}
		else{// Else template not updated/inserted
			return 1;
		}
	}

	/**
	 * updateTemplate updates the template
	 * 
	 * @return the number of rows updated
	 */
	private int updateTemplate(){
		
		return jdbcTemplateObject.update(UPDATE, 
				new Object[] {this.templateBody, this.type});	
	}
	
	/**
	 * insertTemplate inserts the template
	 * 
	 * @return the number of rows inserted
	 */
	private int insertTemplate(){
		
		return jdbcTemplateObject.update(INSERT, 
				new Object[] {this.type, this.templateBody});	
	}	
	
	/**
	 * checkTemplate checks if the template already exists
	 * 
	 * @return true if the template exists
	 */
	private boolean checkTemplate(){
		
		if(jdbcTemplateObject.queryForObject(TEMPLATE_COUNT, 
				new Object[] {this.type}, Integer.class) > 0){ // If exists
			return true;
		}
		else{// Else does not exist
			return false;
		}
	}
}
