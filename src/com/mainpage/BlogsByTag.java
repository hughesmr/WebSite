package com.mainpage;

import com.dbInterface.*;
import com.opensymphony.xwork2.ActionSupport;

/**
 * BlogsByTag returns blogs by selected tag
 */
public class BlogsByTag extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private String pageNum = "1";       // String to hold page number
	private String tag = null;
	private String buttonName = null;          // String to hold pressed button name
	private BlogDataAndPages blogData;
	
	/**
	 * execute is called during execution to render the page
	 * 
	 * @return success
	 */
	public String execute() throws Exception{
		
		GetBlogTag sd = new GetBlogTag(Integer.parseInt(pageNum), tag); // New instance of getBlog
	    
	     if(pageNum == null){ // If pageNum null
	    	 pageNum = "1";
	     } 
	     
	     blogData = sd.getData();
	    	
	     return  SUCCESS;
    }

	/**
	 * setTag sets the tag
	 * 
	 * @param tag is the current tag
	 */
	public void setTag(String tag){
		
	      this.tag = tag;
    }

	/**
	 * getTag gets the current tag
	 * 
	 * @return the selected tag
	 */
	public String getTag(){
		
	      return tag;
    }

	/**
	 * setButtonName sets the button name
	 * 
	 * @param name is the name of the button
	 */
	public void setButtonName(String name){
		
	      this.buttonName = name;
    }

	/**
	 * setPageNum sets the current page num and button selection
	 * 
	 * @param pageNum is the current page num
	 */
	public void setPageNum(String pageNum){
	    
	    int temp; // Variable to hold temp page num
	    
	    if ("Previous".equals(buttonName)) { // If next
	    	if(pageNum.equals("") == true){// If page num not initialized 
	    		
		    	this.pageNum = "1";
	    	}
		    else{ // Else increment page num
		    	
		    	temp = Integer.parseInt(pageNum) + 1;
		    	this.pageNum = Integer.toString(temp);
		     } 
	        
	     }
	     if ("Next".equals(buttonName)) { // If previous
	    	 
	    	 temp = Integer.parseInt(pageNum) - 1;
		     this.pageNum = Integer.toString(temp);
	     }
	  
	     if(buttonName == null){
	    	 
	    	 temp = Integer.parseInt(pageNum) + 1;
	    	 this.pageNum = Integer.toString(temp);
	     }
	     
	}

	/**
	 * getBlogData returns blog data
	 * 	
	 * @return blog and page data
	 */
	public BlogDataAndPages getBlogData(){
		
		return blogData;
	}
}
