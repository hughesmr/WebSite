package com.admin.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.Mail.MailData;
import com.admin.dbinterface.CreateBlog;
import com.validate.ValidateCapt;

/**
 * BlogReceiver receives the new blogs
 */
public class BlogReceiver extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private String captcha; 
	private String emailNot = "";

	/**
	 * doPost handles the blog posts
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int status = 0;				
        String rec = "";		
        String data = "";			
        MailData parsedData = null; 
        CreateBlog blog = null;     
        BufferedReader st = new BufferedReader(new InputStreamReader(request.getInputStream())); 
                
        while((rec = st.readLine()) != null){ // While lines to read
        		data = data + rec;      	
        } 
        
        parsedData = parseJson(data); 
   
        if(captcha != null && ValidateCapt.validate(captcha)){ // If captcha is not null
     
        	// NOTE that blog creation should return a 1. The reason is
        	// it returns the number of rows created. 
        	
        	blog = new CreateBlog(parsedData, emailNot);
        	status = blog.blogCreation();

        	if(status == 1){ // If mail sent successfully 	
        		response.getWriter().write("{\"status\":\"0\"}");
        	}	
        	else{ // Else mail failed
        		response.getWriter().write("{\"status\":\"1\"}");
        	} 
        } 
        else{ // Else captcha is null
        	response.getWriter().write("{\"status\":\"2\"}");
        } 
	} 
	
	/**
	 * paseJson parses the blog json
	 * 
	 * @param json is the json to parse
	 * @return the formated json
	 */
	private MailData parseJson(String json){
		
		String title = null;	
		String body = null;		
		String tags = null;		
		String date = null;
		Object obj = null;	
		captcha = null;        
		
		JSONParser parser = new JSONParser();
		try { // Try parsing json
			obj = parser.parse(json);
			JSONObject jobj = (JSONObject)obj;
			title = (String) jobj.get("title");
	        body = ((String) jobj.get("body")).replace("&#8220", "\"");
	        if(json.contains("tags")){
	        	tags = (String) jobj.get("tags");
	        }
	        this.emailNot = (String) jobj.get("emailNot");
	        captcha = (String) jobj.get("captcha");
	        date = (String) jobj.get("date");
	      
	        
		} catch (ParseException e) { // Catch json parse exception
		
			e.printStackTrace();
			title = null;
		    body = null;
		    date = null;
		    captcha = null;
		} 
        
		return new MailData(tags, title, body, false, date); 
	}
} 
