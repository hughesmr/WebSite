package com.dbInterface;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pageProcessing.PaginationProcessor;
import com.tags.GetTags;

/**
 * GetBlogTag is used to return a list of blogs
 * based on a tag. This class returns an object
 * of type BlogAndPages
 */
public class GetBlogTag {

	private int maxPage = 0; 						 
	private int pageNum = 0;						
	private String tag = null;						  
	private DataSource dataSource = null; 			
    private JdbcTemplate jdbcTemplateObject = null;  
    
	private static final String GET_BLOGS = "SELECT * from blog WHERE id <= ? AND id IN "				
			+ "(SELECT blogid FROM tags WHERE tag = ?) ORDER BY id DESC LIMIT 10"; 
	private static final String MAX_BLOG_ID = "SELECT MAX(id) FROM blog WHERE id IN " 
			+ "(SELECT blogid FROM tags WHERE tag = ?)";
	private static final String BLOG_COUNT = "SELECT COUNT(*) FROM blog WHERE id IN"
			+ " (SELECT blogid FROM tags WHERE tag = ?)"; 
	private static final String START_ID = "SELECT id FROM blog WHERE id IN " + 
			"(SELECT blogid FROM tags WHERE tag = ?)"
			+ "ORDER BY id DESC limit 1 OFFSET ?"; 
	
    /**
     * Constructor
     * 
     * @param pageNum is the current page number
     * @param tag is the tag to look up blogs by
     */
	public GetBlogTag(int pageNum, String tag){
		
		this.tag = tag;
		this.pageNum = pageNum;
		@SuppressWarnings("resource")
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	} 
	
	/**
	 * getData gets the blog data
	 * 
	 * @return blog data
	 */
	public BlogDataAndPages getData(){

		maxPage = countPages();
		return getBlogDB(maxId());
	} 

	/**
	 * getImage gets the first image in a blog
	 * 
	 * @param body is the body of the blog
	 * @return the first image in the blog
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
	 * removeImage removes images from the body
	 * 
	 * @param body of the blog
	 * @return images removed from blog
	 */
	private String removeImage(String body){
		
		int start = 0;   
		int end = 0;	    
		String temp = null; 
		Boolean cont = true;
		
		while(cont == true){ // While continue to find images
			
			temp = null;// Reset temp string
			
			if((start = body.indexOf("<img")) != -1){ // If image tag
				
				temp = body.substring(start);	
				end = temp.indexOf(">");
				temp = temp.substring(0, (end+1));
				body = body.replace(temp,"");
			}
			else{ // Else no images
				
				cont = false;
			} 
		} 
	
		return body;	
	}

	/**
	 * Method getBlogDb gets 10 blogs from the db, it takes the current page number and the max blogid
     * and returns 10 blogs in descending order as a BlogDataAndPages object
	 * 
	 * @param maxId is the max id of the blog
	 * @return blog data
	 */
	private BlogDataAndPages getBlogDB(int maxId){
	
		int page = 0;										
		long date = 0;
		long currentDate = System.currentTimeMillis();
		BlogDataAndPages data = null;								
		Object[] blogData = new Object[6];							
		List<Object[]> blogs = new ArrayList<Object[]>();           
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
	
		page = pageNum*10;
		maxId = maxId - page + 10;
	
		List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(GET_BLOGS, 
					new Object[] {startId(), tag});
		
		for (Map<String, Object> row : rows) { // For loop through results
		
			blogData = new Object[6];
			
			if(((String)row.get("body")).length() < 500){ // If body length less than 500
				blogData[0] = removeImage(((String)row.get("body")));
			} 
			else{ // Else body length greater than 500
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
		} 

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
	 * maxId gets the max blog id
	 *  
	 * @return the max blog id
	 */
	private int maxId(){
	
		int maxId = 0; 										  
		
		maxId = jdbcTemplateObject.queryForObject(MAX_BLOG_ID, new Object[] {tag}, Integer.class);

		return maxId;
	} 

	/**
	 * countPages is used to determine the number of
     * pages of comments. It returns the integer number of pages
     * 
	 * @return the number or pages of comments
	 */
	private int countPages(){
		
		int mod = 0;  			
		int count = 0;			
					
		count = jdbcTemplateObject.queryForObject(BLOG_COUNT, 
				new Object[] {tag}, Integer.class); 
	
		mod = (count % 10); // Get remainder of division by 10
		count = count / 10; // Get the number of pages
	
	//  We need to make sure we account for adding an extra page
	//	if there is a remainder 		
		if(mod != 0){ // If there is a remainder
			count++;
		} 
	
		return count;	
	} 

	/**
	 * startId finds the id of the first blog that should be queried for. This is bassed 
     * on page number. If we multiply the page number by 10 we know the first blog number using OFFSET
	 * 
	 * @return the start id
	 */
	private int startId(){
	
		int id = 0; 
	
		if(pageNum == 0){ // If page num = 0
			return 0;
		} 
	
		id = jdbcTemplateObject.queryForObject(START_ID, new Object[] 
				{tag, (10*(Integer.valueOf(pageNum)-1))}, Integer.class);

		return id;
	
	}
} 
