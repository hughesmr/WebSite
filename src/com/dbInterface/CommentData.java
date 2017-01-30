package com.dbInterface;

import java.util.List;

/**
 * This class is an abstract data type to pass
 * comment data between classes.
 */
public class CommentData {
	
	
	private List<String[]> comments;      
	private List<Integer> page;      
	
	/**
	 * Constructor
	 * 
	 * @param comments are the list of comments
	 * @param page are the list of pages
	 */
	public CommentData(List<String[]> comments, List<Integer> page){
		
		this.comments = comments;
		this.page = page;
		
	}
	
	/**
	 * getComments returns the comments on the page
	 * 
	 * @return the comments on the page
	 */
	public List<String[]> getComments(){
		
		return this.comments;
	}
	
	/**
	 * getPages returns a list of comment pages
	 * 
	 * @return the list of pages
	 */
	public List<Integer> getPages(){
		
		return this.page;
	}
}
