package com.dbInterface;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pageProcessing.PaginationProcessor;
import com.dbInterface.BlogDataAndPages;
import com.tags.GetTags;

/**
 * This class is used to return a list of blogs. 
 * This class returns an object of type 
 * BlogAndPages 
 */
public class GetBlog {
	
	private int maxPage = 0;					  
	private int pageNum = 0;					    
	private DataSource dataSource = null; 			 
    private JdbcTemplate jdbcTemplateObject = null; 
    
    private static final String GET_BLOGS = "SELECT * from blog WHERE id <= ? " +
    		"  ORDER BY id DESC LIMIT 10"; 
    private static final String MAX_ID = "SELECT max(id) from blog"; 
    private static final String BLOG_COUNT = "SELECT count(*) from blog"; 
	private static final String START_ID = "SELECT id FROM blog ORDER BY id" +
			" DESC limit 1 OFFSET ?"; 
	
    /**
     * Constructor 
     * 
     * @param pageNum is the current page num
     */
	public GetBlog(int pageNum){
		
		this.pageNum = pageNum;
		@SuppressWarnings("resource")
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");
		jdbcTemplateObject = new JdbcTemplate(dataSource);
		
	}	
	
	/**
	 * getData returns the blog data
	 * 
	 * @return blog data
	 */
	public BlogDataAndPages getData(){
		
		maxPage = countPages();
		return getBlogFromDB(getMaxId());
	} 
	
	/**
	 * getImage gets the first image in the blog
	 * 
	 * @param body is the body of the blog
	 * @return the image link
	 */
	private String getImage(String body){
		
		int start = 0;      
		int end = 0;	   
		String temp = null; 
		
		if((start = body.indexOf("<img")) != -1){ // If image tag
		
			temp = body.substring(start);	
			end = temp.indexOf(">");
			temp = temp.substring(0, (end+1));
	
		} 
	
		return temp;
	} 
	
	/**
	 * removeImage removes all images in the blog
	 * 
	 * @param body is the blog body
	 * @return the body without images
	 */
	private String removeImage(String body){
		
		int start = 0;     
		int end = 0;	   
		String temp = null; 
		Boolean cont = true;
		
		while(cont == true){ // While continue to find images
			
			temp = null;
			
			if((start = body.indexOf("<img")) != -1){ // If image tag
				temp = body.substring(start);	
				end = temp.indexOf(">");
				temp = temp.substring(0, (end+1));
				body = body.replace(temp,"");
			} // 
			else{ // Else no images
				cont = false;
			} 
		
		} 
	
		return body;
	}	
	
	/**
	 * getBlogFromDb gets the blog from the db
	 * 
	 * @param maxId is the max id to return
	 * @return return the blog data
	 */
	private BlogDataAndPages getBlogFromDB(int maxId){
		
		int page = 0;											
		long date = 0;
		long currentDate = System.currentTimeMillis();
		BlogDataAndPages data;								        
		Object[] blogData = new Object[6];							
		List<Object[]> blogs = new ArrayList<Object[]>();           
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  
		
		page = pageNum*10;
		maxId = maxId - page + 10;

		List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(GET_BLOGS, 
						new Object[] {startId()});
			
		for (Map<String, Object> row : rows) { // For loop through results
			
			blogData = new Object[6];
			
			if(((String)row.get("body")).length() < 500){// If body length less than 500
				
				blogData[0] = removeImage(((String)row.get("body")));
			}
			else{// Else body length greater than 500
				
				blogData[0] = removeImage(((String)row.get("body")).substring(0, 500));
			}
			blogData[1] = (String)row.get("title");
			date = (Long)row.get("date");
			blogData[2] = sdf.format(new Date(date));
			blogData[3] = Integer.toString((Integer)row.get("id"));
			blogData[4] = getImage((String)row.get("body"));
			blogData[5] = GetTags.getListofTags(blogData[3].toString(), jdbcTemplateObject);
			
			if(date <= currentDate){
				
				blogs.add(blogData);
			}
            date = 0;
                
		}// End for loop through results

		if(blogs.isEmpty()){ // If no results
			blogData[0] = "There are no blogs yet";
		    blogData[1] = "";
		    blogData[2] = "";
		    blogData[3] = "";
		    blogData[4] = "";
			blogs.add(blogData);
				
		}
			
		data = new BlogDataAndPages(blogs, PaginationProcessor.calculatePages(maxPage, pageNum), pageNum, maxPage);
	       
		return data;
	}

	/**
	 * getMaxId gets the max blog id
	 * 
	 * @return
	 */
	private int getMaxId(){
	
		int maxId = 0; 							   

		try{ // Try DB query
			
			maxId = jdbcTemplateObject.queryForObject(MAX_ID, Integer.class);
		}
		catch(NullPointerException e){ // Catch exception
			
			maxId = 0;
		}

	    return maxId;	
	} 
	
	/**
	 * coutPages counts the pages
	 * 
	 * @return the number of pages
	 */
	private int countPages(){
		
		int mod = 0;  			
		int count = 0;			
					
		count = jdbcTemplateObject.queryForObject(BLOG_COUNT, 
				new Object[] {}, Integer.class);  
	
		mod = (count % 10); // Get remainder of division by 10
		count = count / 10; // Get the number of pages

//      We need to make sure we account for adding an extra page
//		if there is a remainder 		
		if(mod != 0){ // If there is a remainder
			count++;
		} 

		return count;	
	} 
	
	/**
	 * startId returns the starting id
	 * 
	 * @return the starting blog id
	 */
	private int startId(){
		
		int id = 0; 			
		
		if(pageNum == 0){ // If page num = 0
			return 0;
		} 
		
		try{
		id = jdbcTemplateObject.queryForObject(START_ID, new Object[] 
				{(10*(Integer.valueOf(pageNum)-1))}, Integer.class); 
		}
		catch(Exception e){
			id = 0;
		}
	
		return id; 
	} 
} 