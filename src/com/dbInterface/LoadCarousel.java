package com.dbInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * LoadCarousel loads slidshow carousel data
 */
public class LoadCarousel {
	
    private DataSource dataSource; 			 // DataSource object
    private JdbcTemplate jdbcTemplateObject; // JdbcTemplate object
    
	private static final String GET_CAROUSEL = "SELECT * FROM carousel"; 

    /**
     * LoadCarousel
     */
	public LoadCarousel() {
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	/**
	 * getImages gets the images from the db 
	 * 
	 * @return a list of image addresses
	 */
	public List<String[]> getImages(){
		
		return loadImages();
	}
	
	/**
	 * loadImages loads the image addresses
	 * 
	 * @return a list of image addresses
	 */
	private List<String[]> loadImages(){
		
		List<String[]> carouselData = new ArrayList<String[]>();// List of strings to hold comments
		List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(GET_CAROUSEL); 		
		
		for (Map<String, Object> row : rows) { // For loop through results 
			String[] temp = new String[4];
			temp[0] = (String)row.get("image");
			temp[1] = (String)row.get("description");
			temp[2] = Integer.toString((Integer)row.get("id"));
			temp[3] = (String)row.get("address");
			carouselData.add(temp);
		
		} 
		
		return carouselData;
	}
}
