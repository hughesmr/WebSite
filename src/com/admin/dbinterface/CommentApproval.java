package com.admin.dbinterface;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * CommentApproval is used to approve comments
 */
public class CommentApproval {
	
	private ApplicationContext context = null;
	private DataSource dataSource = null;
	private JdbcTemplate jdbcTemplateObject = null;
	
	private static final String APPROVE = "UPDATE comment SET approved = TRUE WHERE id = ?"; 
	private static final String DELETE = "DELETE FROM comment WHERE id = ?"; 

	/**
	 * Constructor
	 */
	public CommentApproval(){
		
		context = new ClassPathXmlApplicationContext("db-beans.xml");  	   
		dataSource = (DataSource)context.getBean("dataSource");  
		jdbcTemplateObject = new JdbcTemplate(dataSource);     
	}	
	
	/**
	 * approveOrDelete either approves or deletes comments
	 * 
	 * @param appDel are the comments to approve
	 * @return 0
	 */
	public int approveOrDelete(String[] appDel){
		
		if(appDel[0].length() > 0){ // If comments to approve
			approveComment(getIds(appDel[0]));
		} 
		
		if(appDel[1].length() > 0){ // If comments to delete
			deleteComment(getIds(appDel[1]));
		} 
		
		return 0;
	} 
	
	/**
	 * getIds gets the ids our of the string
	 * 
	 * @param id is the string of ids
	 * @returnthe list of ids
	 */
	private List<Long> getIds(String id){
		
		String[] split = null;
		List<Long> ids = new ArrayList<Long>();
		
		id = id.substring(0, id.length()-1);
		split = id.split(",");

		for(String n : split){ // For each id
			ids.add(Long.parseLong(n));
		} 
		
		return ids;
	}	
	
	/**
	 * approveComment is used to approve comments
	 * 
	 * @param ids are the ids of comments to approve
	 */
	private void approveComment(List<Long> ids){
		
		for(Long n : ids){ // For each comment
			jdbcTemplateObject.update(APPROVE, n);
		} 
	}
	
	/**
	 * deleteComment deletes comments
	 * 
	 * @param ids are the list of ids to delete
	 */
	private void deleteComment(List<Long> ids){
		
		for(Long n : ids){ // Delete for each id
			jdbcTemplateObject.update(DELETE, n);
		} 
	}
}
