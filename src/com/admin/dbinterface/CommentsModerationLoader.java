package com.admin.dbinterface;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dbInterface.CommentData;
import com.pageprocessing.PaginationProcessor;

/**
 * This class is used for comment moderation
 */
public class CommentsModerationLoader {
		
	private String pageNum; 	 
	private DataSource dataSource; 			
	private JdbcTemplate jdbcTemplateObject; 
	
	private static final String GET_TITLE = "SELECT title FROM blog WHERE id = ?";
	private static final String GET_COMMENT = "SELECT id, blogid, body, date, username " +
			" from comment WHERE approved = FALSE AND id >= ? ORDER BY id LIMIT 10"; 
	private static final String GET_START_ID = "SELECT id FROM comment where " +
			"approved = FALSE ORDER BY id ASC limit 1 OFFSET ?"; 
	private static final String COMMENT_COUNT = "SELECT count(*) FROM comment WHERE approved = FALSE"; 
	    
	/**
	 * Constructor 
	 * 
	 * @param pageNum is the comment page number
	 */
	public CommentsModerationLoader(String pageNum){
		    	
		this.pageNum = pageNum;
		@SuppressWarnings("resource")
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}    
	
	/**
	 * loadComments loads comments from the db
	 * 
	 * @return comments for the current page number
	 */
	public CommentData loadComments(){
								
		return new CommentData(runCommentQuery(), 
				PaginationProcessor.calculatePages(countPages(), 
				Integer.parseInt(pageNum))); 
	} 
		
	/**
	 * formatData formats comment data
	 * 
	 * @param blogid is the blog id for the comment
	 * @param id is the id of the comment
	 * @param body is the body of the comment
	 * @param date is the date of the comment
	 * @param username is the username of the comment
	 * @return formated comment data
	 */
	private String[] formatData(Long blogid, int id, String  body, 
			long date, String username){
			
		String[] comment = new String[6];							
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  
			    	
		comment[0] = body;
		comment[1] = username;
	   	comment[2] = sdf.format(new Date(date));
	   	comment[3] = Integer.toString(id);
	  	comment[4] = Long.toString(blogid);
	    comment[5] = getTitle(blogid);
	    
		return comment; 	
	} 
		
	/**
	 * getTitle gets the title of the blog
	 * 
	 * @param blogid is the id of the blog to get the title for
	 * @return the title of the blog
	 */
	private String getTitle(Long blogid){
			
		return jdbcTemplateObject.queryForObject(GET_TITLE,
				new Object[] {blogid}, String.class); 
	}

	/**
	 * runCommentQuery gets comments
	 * 
	 * @return a list of comments
	 */
	private List<String[]> runCommentQuery(){
			
		List<String[]> comments = new ArrayList<String[]>(); 	    	
		List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(GET_COMMENT, 
				new Object[] {startId()}); 			
			
		for (Map<String, Object> row : rows) { // For loop through results 
			comments.add(formatData((Long)row.get("blogid"), (Integer)row.get("id"),
					(String)row.get("body"),(Long)row.get("date"), (String)row.get("username")));
		} 
			 
		if(comments.isEmpty()){ // If comments are empty 
			comments.add((new String[]{"There are no comments at this time.","",""}));	
		} 
			
		return comments; 
	} 	
		
	/**
	 * startId is the start id of comments
	 * 
	 * @return the first id or 0
	 */
	private int startId(){			
			
		if(pageNum.equals("0")){ // If page num = 0
			return 0;
		} 
			
		return jdbcTemplateObject.queryForObject(GET_START_ID, new Object[] 
				{(10*Integer.valueOf(pageNum))}, Integer.class);
	} 
		
	/**
	 * countPages returns the page count
	 * 
	 * @return the number or comment pages
	 */
	private int countPages(){
			
		int mod = 0;  		
		int count = 0;		
						
		count = jdbcTemplateObject.queryForObject(COMMENT_COUNT, Integer.class); 
		
		mod = (count % 10); // Get remainder of division by 10
		count = count / 10; // Get the number of pages
		
		if(mod != 0){ // If there is a remainder
			count++;
		} 

		return count;	
	} 	
}
