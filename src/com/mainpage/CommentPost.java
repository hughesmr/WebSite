package com.mainpage;

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

import com.dbInterface.UserCreation;
import com.dbInterface.InsertComment;
import com.email.MailData;
import com.email.NewCommentMessage;
import com.validate.CaptchaValidation;

/**
 * This class is used as the interface for comments that are getting posted
 */
public class CommentPost extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private String captcha; // String used to hold captcha

	/**
	 * doPost is called during comment post
	 * 
	 * @param request is the request
	 * @param response is the response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int status = 0;				// Variable used to hold insert status
        String rec = "";			// String used to read in data from request stream
        String data = "";			// String used to hold json data
        MailData parsedData = null; // MailData object for holding values
        UserCreation user = new UserCreation();
       
        BufferedReader st = new BufferedReader(new InputStreamReader(request.getInputStream())); // Buffered reader to read in request stream
                
        while((rec = st.readLine()) != null){ // While lines to read
        		data = data + rec;      	
        } 
        
        parsedData = parseJson(data);
        user.createUser(parsedData.getSubject(), parsedData.getFrom(), parsedData.getRecMail());
       
        if(captcha != null && CaptchaValidation.validate(captcha)){ // If captcha is not null
        	
        	status = InsertComment.createComment(parsedData, request.getParameter("blogId"));
        	sendCommentMessage(parsedData, request.getParameter("blogId"));
    
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
	 * sendCommentMessage sends messages to the admin when a comment is posted
	 * 
	 * @param parsedData is the parsed mail data
	 * @param blogId is the id of the blog the comment was posted to
	 */
	private void sendCommentMessage(MailData parsedData, String blogId){
		
		Thread thread = new Thread(new NewCommentMessage(parsedData.getSubject(), 
					parsedData.getBody(), parsedData.getFrom(), Integer.parseInt(blogId)));
		thread.start();
	}
	
	/**
	 * parseJson used to parse json
	 * 
	 * @param json is the json to parse
	 * 
	 * @return MailData which is the parsed json
	 */
	private MailData parseJson(String json){
		
		String email;					
		String name;					
		String body;					
		String recMailTemp = null;		
		boolean recMail = false;		
		Object obj = null;				
		captcha = null;         		
	
		JSONParser parser = new JSONParser();
		
		try { // Try parsing json
			obj = parser.parse(json);
			JSONObject jobj = (JSONObject)obj;
			email = (String) jobj.get("email");
			recMailTemp = (String) jobj.get("recEmail");
			name = (String) jobj.get("name");
	        body = ((String) jobj.get("body")).replace("&#8220", "\"");
	        captcha = (String) jobj.get("captcha");
	        
	        if(recMailTemp.equals("True") || recMailTemp.equals("true")){
	        	recMail = true;
	        }
	        
		} catch (ParseException e) { // Catch json parse exception
		
			e.printStackTrace();
			email = null;
			name = null;
		    body = null;
		    captcha = null;
		} 
        
		return new MailData(email, name, body, recMail, null); // Return an instance of MailData
	}
} 

