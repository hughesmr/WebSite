package com.admin.action;


import com.admin.dbinterface.TemplateOverlay;
import com.opensymphony.xwork2.ActionSupport;

/**
 * LoadTemplateAction loads the template page
 */
public class LoadTemplateAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private String type = ""; 
	private String body = ""; 
	
	/**
	 * execute is called on page load
	 */
	public String execute() throws Exception {
		
		TemplateOverlay s = new TemplateOverlay(this.type);
		this.body = s.returnTemplate();
		
		return  SUCCESS;
	}
	
	/**
	 * setType sets the tempalte type
	 * 
	 * @param type is the tempalte type
	 */
	public void setType(String type){
		
		this.type = type;	
	}
	
	/**
	 * getType gets the tempalte type
	 * 
	 * @return the template type
	 */
	public String getType(){
		
		if(type.equals("newBlog")){// If newblog
			return "Blog Mail Template";
		}
		else{// Else newsletter
			return "News Letter Mail Template";
		}
	}
	
	/**
	 * getRawType gets the type string
	 * 
	 * @return the type string
	 */
	public String getRawtype(){
		
		return this.type;
	}
	
	/**
	 * getBody gets the template body
	 * 
	 * @return the template body
	 */
	public String getBody(){
		
		return this.body;
	}	
	
	/**
	 * getTokens returns supported template tokens
	 * 
	 * @return template tokens
	 */
	public String getTokens(){
		
		if(type.equals("newBlog")){ // If new blog email
			return "${blogEmail}, ${date}, ${blogimage}";
		}
		else{ // Else newsletter email
			return "${newsLetter}, ${date}";
		}
	}	
}
