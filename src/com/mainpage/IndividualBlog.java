package com.mainpage;

import com.opensymphony.xwork2.ActionSupport;
import com.dbInterface.BlogData;
import com.dbInterface.IndividualBlogLoader;

/**
 * This class is the action class used to compile the blogview ftl template
 */
public class IndividualBlog extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private String blogId;
	private String buttonName = null;
	private String direc = null;
	private BlogData bl = null;
		
	/**
	 * execute is called during execution to render the page
	 * 
	 * @return success
	 */
	public String execute() throws Exception {
		 
		getOrSetUrl();
	
		IndividualBlogLoader n = new IndividualBlogLoader(blogId, direc);
		
		bl = n.getABlog();
	
        return  SUCCESS;
	}

	/**
	 * getOrSetUrl either gets the url or sets it depending on request type
	 */
	private void getOrSetUrl(){
		
		if("Older".equals(buttonName)){ // If older button selected
			
			direc = "older";
		} 
		else if("Newer".equals(buttonName)){ // Else newer button selected

			direc = "newer";
		} 
		else{
			direc = "none";
		}	
	} 
	
	/**
	 * setBlogId sets the blog id
	 * 
	 * @param blogId is the id of the blog being requested
	 */
	public void setBlogId(String blogId){
		
		this.blogId = blogId;
	} 

	/**
	 * setButtonName sets the button name
	 * 
	 * @param name is the button name
	 */
	public void setButtonName(String name){

      this.buttonName = name;
	}

	/**
	 * getBlogData gets the blog data
	 * 
	 * @return the blog data
	 */
	public BlogData getBlogData(){
		
		return bl;
	}
} 
