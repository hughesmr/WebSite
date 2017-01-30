package com.dbInterface;

import java.sql.Date;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;

import com.admin.properties.PropertyLoader;
import com.dbInterface.CommentData;
import com.pageprocessing.PaginationProcessor;

/**
 * LoadComment loads a comment from the DB
 */
public class LoadComment {
	
	private String pageNum; 	  // String to hold requested page number
    private String blogId;		  // String to hold current blogId
    private DataSource dataSource; 			 // DataSource object
    private JdbcTemplate jdbcTemplateObject; // JdbcTemplate object
    
    private static final String PAGE_COUNT = "SELECT count(*) from comment WHERE blogId = ? AND approved = TRUE"; 
	private static final String START_ID = "SELECT id FROM comment where blogID = ?" + 
			" and approved = TRUE ORDER BY id ASC limit 1 OFFSET ?"; 
	private static final String GET_COMMENT = "SELECT body, date, username from comment WHERE "
			+ "blogId = ? AND approved = TRUE AND id >= ? "
			+"ORDER BY id LIMIT 10"; 
 
    /**
     * Constructor
     * 
     * @param blogId is the id of the blog
     * @param pageNum is the page number of the comments
     */
    public LoadComment(String blogId, String pageNum){
    	
    	this.blogId = blogId;
    	this.pageNum = pageNum;
		@SuppressWarnings("resource")
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");
		jdbcTemplateObject = new JdbcTemplate(dataSource);
    	
    } 
 
    /**
     * loadCommentDb is used to load the comment from the db
     * @return the comment data
     */
	public CommentData loadCommentsDb(){
							
		return new CommentData(runCommentQuery(), 
				PaginationProcessor.calculatePages(countPages(), 
						Integer.parseInt(pageNum))); // Create a new instance of BlogDataAndPages to store blog data
					
	} 
	
	/**
	 * formatData below converts the data into human readable form and returns an array with all comment data
	 * 
	 * @param body is the body of the commnet
	 * @param date is the date of posting
	 * @param username of the comment poster
	 * @return the formated data
	 */
	private String[] formatData(String  body, long date, String username){
		
		String[] comment = new String[3];							// String to hold new comment 
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  // Object to format epoch timestamp to human readable 
		    	
    	comment[0] = hideBannedWords(body);
    	comment[1] = username;
    	comment[2] = sdf.format(new Date(date));
		return comment; // Return comment data
		
	} 
	
	/**
	 * hideBannedWords replaces all banned words with stars
	 * 
	 * @param word is the word to determine if it is banned
	 * @return the cleansed word
	 */
	private String hideBannedWords(String word){
		
		for(String rep: PropertyLoader.bannedWords){ // For each word
			
			word = word.replaceAll(rep, getCleansedWord(rep));
		} 
		
		return word;
		
	} 
		
	/**
	 * getCleansedWord gets cleansed banded word
	 * 
	 * @param word is the word to cleanse
	 * @return the cleansed word
	 */
	private String getCleansedWord(String word){
		
		String star = "";
		int length = word.length();

		for(int i = 0; i < length; i++){ // For create star string
			star = star + "*";
		} 
		
		return star;
		
	}
		
	/**
	 * runCommentQuery gets comments
	 * 
	 * @return a list of comments
	 */
	private List<String[]> runCommentQuery(){
		
		List<String[]> comments = new ArrayList<String[]>(); 	   
		
		List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(GET_COMMENT, 
				new Object[] {Integer.parseInt(blogId), startId()}); 			
		
		for (Map<String, Object> row : rows) { // For loop through results 
			
			comments.add(formatData((String)row.get("body"),(Long)row.get("date"), (String)row.get("username")));
		} 
		 
		if(comments.isEmpty()){ // If comments are empty 
			
			comments.add((new String[]{"There are no comments at this time.","",""}));	
		}
		
		return comments; 
					
	} 
		
	/**
	 * startId gets the first comment id
	 * 
	 * @return the first id
	 */
	private int startId(){
		
		int id = 0; 			
		
		if(pageNum.equals("0")){ // If page num = 0
			return 0;
		} 
		
		id = jdbcTemplateObject.queryForObject(START_ID, new Object[] 
				{Integer.parseInt(blogId), (10*Integer.valueOf(pageNum))}, Integer.class); // Run query 
	
		return id; 
		
	} 

	/**
	 * countPages gets the page count
	 * 
	 * @return the page count
	 */
	private int countPages(){
		
		int mod = 0;  			
		int count = 0;			
					
		count = jdbcTemplateObject.queryForObject(PAGE_COUNT, 
				new Object[] {Integer.parseInt(blogId)}, Integer.class); // Run query 
	
		mod = (count % 10); // Get remainder of division by 10
		count = count / 10; // Get the number of pages

//      We need to make sure we account for adding an extra page
//		if there is a remainder 		
		if(mod != 0){ // If there is a remainder
			count++;
		} 

		return count;	
		
	}
} 

