package com.mainpage;

import com.dbInterface.*;
import com.opensymphony.xwork2.ActionSupport;
import com.tags.TagCloud;

/**
 * Blog returns blogs on the blog page
 */
public class Blog extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private String pageNum = "1";      
	private String buttonName = null;          
	private BlogDataAndPages blogData;
	
	/**
	 * execute is called during execution to render the page
	 * 
	 * @return success
	 */
	public String execute() throws Exception{
		 
		TagCloud s = new TagCloud();
		
		s.getCloud();
		
	     GetBlog sd = new GetBlog(Integer.parseInt(pageNum)); // New instance of getBlog
	    
	     if(pageNum == null){ // If pageNum null
	    	 pageNum = "1";
	     } 
	     
	     blogData = sd.getData();
	    	
	     return SUCCESS;
       
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
	    
	    int temp; 
	    
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