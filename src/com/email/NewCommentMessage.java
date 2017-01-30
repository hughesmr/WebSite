package com.email;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.admin.properties.PropertyLoader;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * NewCommentMessage creates an alert email that a comment was posted
 */
public class NewCommentMessage implements Runnable{
	
	private int blogid = 0;
	private String name = "";
	private String email = "";
	private String comment = "";
	private static final String path = "com/email/template/newComment.ftl"; // String to hold path of template

	/**
	 * Constructor
	 * 
	 * @param name of poster
	 * @param comment is the comment that was posted
	 * @param email is the email address of poster
	 * @param blogid is the id of the blog the comment was posted to
	 */
	public NewCommentMessage(String name, String comment, String email, int blogid){
		
		this.blogid = blogid;
		this.name = name;
		this.comment = comment;
		this.email = email;
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		String message = compileTemplate();
		MailSend.send(PropertyLoader.adminEmail, "New Comment", message);
	}
	
	/**
	 * compileTemplate compiles the template for the comment message
	 * 
	 * @return the template
	 */
	private String compileTemplate(){
		
		Template template = null;									// Template object
		StringWriter outputWriter = new StringWriter();   			// String writer to output ftl to string
        Map<String, Object> data = new HashMap<String, Object>();   // Map to hold ftl data
        Configuration cfg = new Configuration(); 				    // Config object for ftl

        data.put("comment", this.comment); 
        data.put("name", this.name); 
        data.put("email", this.email); 
        data.put("blogid", this.blogid); 
        data.put("url", PropertyLoader.url);
        
        try { // Try to compile template
      
        	cfg.setClassForTemplateLoading(this.getClass(), "/"); // Set classpath for template
        	template = cfg.getTemplate(this.path); // Load template
			template.process(data, outputWriter); // Create template  
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (TemplateException e) {
			
			e.printStackTrace();
		} 

        return outputWriter.toString();
		
	}
}
