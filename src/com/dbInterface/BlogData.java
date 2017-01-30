package com.dbInterface;

import java.util.List;

/**
 * This class is used to storing blog data
 * to send to ftl
 */
public class BlogData {
	
	private String body = null;
	private String title = null;
	private String date = null;
	private String maxId = null;
	private String blogId = null;
	private List<String> tags = null;     
	
	/**
	 * Constructor 
	 * 
	 * @param body is the blog body
	 * @param title is the title of the blog
	 * @param date is the blog posting date
	 * @param maxId is the max blog id
	 * @param blogId is the blog id
	 * @param tags is the tags associated with the blog
	 */
	BlogData(String body, String title, String date, String maxId, String blogId, List<String> tags){
		
		this.body = body;
		this.title = title;
		this.date = date;
		this.maxId = maxId;
		this.blogId = blogId;
		this.tags = tags;
		
	}
	
	/**
	 * getTags returns a list of tags
	 * 
	 * @return the tags
	 */
	public List<String> getTags(){
		
		if(tags.isEmpty()){
			return null;
		}
		else{
			return tags;
		}
	}
	
	/**
	 * getTitle returns the blog title
	 * 
	 * @return the title 
	 */
	public String getTitle(){
		
		return title;
	}
	
	/**
	 * getBody returns the blog body
	 * 
	 * @return the body
	 */
	public String getBody(){

		return body;
	}
	
	/**
	 * getDate returns the blog date
	 * 
	 * @return the date
	 */
	public String getDate(){

		return date;
	}
	
	/**
	 * getMaxId returns the max blog id
	 * 
	 * @return the max blog id
	 */
	public String getMaxId(){

		return maxId;
	}
	
	/**
	 * getBlodId gets the blog id
	 * 
	 * @return the blog id
	 */
	public int getBlogId(){

		return Integer.parseInt(blogId);
	}
	
	/**
	 * getMaxPage gets the max page Id
	 * 
	 * @return the max page id
	 */
	public int getMaxPage(){
		

		return Integer.parseInt(maxId);	
	}
}
