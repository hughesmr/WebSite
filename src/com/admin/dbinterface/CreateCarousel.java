package com.admin.dbinterface;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * CreateCarousel inserts carousel data
 */
public class CreateCarousel {
	
	private ApplicationContext context = null;
	private DataSource dataSource = null;
	private JdbcTemplate jdbcTemplateObject = null;
	
	private static final String DELETE = "DELETE FROM carousel WHERE id = ?";
	private static final String INSERT = "INSERT INTO carousel (id, description," +
			" image, address) values (DEFAULT, ?, ?, ?)";

	/**
	 * Constructor
	 */
	public CreateCarousel() {

		context = new ClassPathXmlApplicationContext("db-beans.xml");  	   
		dataSource = (DataSource)context.getBean("dataSource");  
		jdbcTemplateObject = new JdbcTemplate(dataSource);     
	}
	
	/**
	 * create creates a slideshow image
	 * 
	 * @param data is the data to insert
	 * @return 0 if the image is created
	 */
	public int create(String[] data){
		
		if(insertCarousel(data) > 0){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	/**
	 * deleteSlide deletes slides
	 * 
	 * @param data is the data to delete
	 * @return 0 if the value is deleted
	 */
	public int deleteSlide(String data){
		
		if(deleteCarousel(data) > 0){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	/**
	 * insertCarousel inserts carousel images
	 * 
	 * @param data is the image to insert
	 * @return the number of rows inserted
	 */
	private int insertCarousel(String[] data){
		
		return jdbcTemplateObject.update(INSERT, new Object[] {data[1], data[0], data[2]});
	}
	
	/**
	 * deleteCarousel deletes carousel data
	 * 
	 * @param data is the data to delete
	 * @return number of rows deleted
	 */
	private int deleteCarousel(String data){
		
		return jdbcTemplateObject.update(DELETE, new Object[] {Integer.parseInt(data)});	
	}
}
