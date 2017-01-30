package com.dbInterface;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dbInterface.BlogData;
import com.tags.GetTags;

/**
 * IndividualBlogLoader is used to get blod data for one blog plus
 * the max id of all blogs. The max id is used to determine
 * if the next and previous button should be greyed out
 */
public class IndividualBlogLoader {
	
	private String blogId = null;                    
	private String direc = null;
	private DataSource dataSource = null; 			
    private JdbcTemplate jdbcTemplateObject = null;  
    
    private static final String MAX_ID = "SELECT max(id) FROM blog";  // String to hold query 

    /**
     * Constructor 
     * 
     * @param blogId is the id of the blog ot load
     * @param direc is the direction button text
     */
	public IndividualBlogLoader(String blogId, String direc){
		
		this.direc = direc;
		this.blogId = blogId;
		@SuppressWarnings("resource")
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");
		jdbcTemplateObject = new JdbcTemplate(dataSource);
		
	} 	
	
	/**
	 * getABlog gets a blog
	 * 
	 * @return the blog data
	 */
	public BlogData getABlog(){
		
		return loadBlog();
		
	} 

	/**
	 * loadBlog loads the blog data
	 * 
	 * @return the blog data
	 */
	private BlogData loadBlog(){
		
		int blogTagId = Integer.parseInt(blogId);
		String query;
		
		if(direc.equals("none")){ // If no button selected
			
			query = "SELECT * FROM blog WHERE id = ?";
		}
		else if(direc.equals("newer")){ // Else if newer button selected 
			
			blogTagId++;
			query = "SELECT * FROM blog WHERE id > ? ORDER BY id ASC LIMIT 1";
		}
		else{ // Else older button selected
			
			blogTagId--;
			query = "SELECT * FROM blog WHERE id < ? ORDER BY id DESC LIMIT 1";
		} 
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");// Create instance of simple date format
	
		Map<String, Object> row = jdbcTemplateObject.queryForMap(query, 
				new Object[] {Integer.parseInt(blogId)}); 		

		return new BlogData((String)row.get("body"), (String)row.get("title"), 
				sdf.format(new Date((Long)row.get("date"))), maxId(), Integer.toString((Integer)row.get("id")),
				GetTags.getListofTags(Integer.toString(blogTagId), jdbcTemplateObject));

	} 
	
	/**
	 * maxId gets the max blog id
	 * 
	 * @return the max blog id
	 */
	private String maxId(){
		
		return jdbcTemplateObject.queryForObject(MAX_ID, String.class); // Run query 

	} 		
} 