package com.admin.dbinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * TemplateOverlay handles overlay templates
 */
public class TemplateOverlay {

	private String type = "";
	private ApplicationContext context = null;					
	private JdbcTemplate jdbcTemplateObject = null;					  
	private DataSource dataSource = null;							 
	private String path = "/com/Mail/template/";
	
	private static final String TEMPLATE_COUNT = "SELECT COUNT(*) FROM templates WHERE name = ?";
	private static final String GET_OVERLAY = "SELECT body FROM templates WHERE name = ?";
	
	/**
	 * Constructor
	 * 
	 * @param type is the template type
	 */
	public TemplateOverlay(String type) {
		this.type = type;
		path = path + type + ".ftl";
		this.context = new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");           
		jdbcTemplateObject = new JdbcTemplate(dataSource);                
	}
	
	/**
	 * returnTemplate returns the template
	 * 
	 * @return the template
	 */
	public String returnTemplate(){
		
		if(overlayCheck()){
			return getOverlay();
		}
		else{
			
			return getFile();
		}
	}
	
	/**
	 * getOverlay gets the overlay
	 * 
	 * @return the overlay
	 */
	private String getOverlay(){
		
		return jdbcTemplateObject.queryForObject(GET_OVERLAY,
				new Object[] {this.type}, String.class);
	}
	
	/**
	 * getFile gets the template file
	 * 
	 * @return the file
	 */
	private String getFile(){
		
		int c = 0;
		StringBuilder sb = new StringBuilder();
		InputStream in = getClass().getResourceAsStream(this.path); 
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		try {
			while ((c = reader.read()) != -1) {
				sb.append((char) c);
			}
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
		
		return sb.toString();
	}
	
	/**
	 * overlayCheck checks for overlayed templates
	 * 
	 * @return true if there are overlays
	 */
	private boolean overlayCheck(){
		
		if(jdbcTemplateObject.queryForObject(TEMPLATE_COUNT,
				 new Object[] {this.type}, Integer.class) > 0){
			return true;
		}
		else{
			return false;
		}	
	}
}
