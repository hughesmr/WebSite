package com.email;

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

import com.admin.properties.PropertyLoader;
import com.email.MailData;
import com.email.SendMail;
import com.validate.ValidateCapt;

/**
 * GetMailData gets messages from the contact page
 */
public class GetMailData extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private String captcha; 

	/**
	 * doPost handles the post request for contact messages
	 * 
	 * @param request is the request object
	 * @param response is the response object
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		BufferedReader st = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String rec = "";
        String data = "";
        MailData parsedData = null; // MailData object for holding values
        
        while((rec = st.readLine()) != null){ // While lines to read
        	
        		data = data + rec;      	
        }
      
        parsedData = parseJson(data);
        
        if(captcha != null && ValidateCapt.validate(captcha)){ // If captcha is not null
    
        	if(SendMail.send(PropertyLoader.adminEmail, parsedData.getSubject(), (parsedData.getBody() + " " + parsedData.getFrom()))){ // If mail sent successfully 	
        		
        		response.getWriter().write("{\"status\":\"0\"}");
        	}	
        	else{ // Else mail failed
        		
        		response.getWriter().write("{\"status\":\"1\"}");
        	}
        }
        else{ // Else captcha bad
        	
        	response.getWriter().write("{\"status\":\"2\"}");
        }
	} 
	
	/**
	 * parseJson parses the json and returns MailData
	 * 
	 * @param json is the json to parse
	 * @return the parsed data
	 */
	private MailData parseJson(String json){
		
		String from;
		String subject;
		String body;
		Object obj = null;
		captcha = null;         		
		
		JSONParser parser = new JSONParser();
		
		try { // Try parsing json
			obj = parser.parse(json);
			JSONObject jobj = (JSONObject)obj;
	        from = (String) jobj.get("from");
	        subject = (String) jobj.get("subject");
	        body = (String) jobj.get("body");
	        captcha = (String) jobj.get("captcha");
	        
		} catch (ParseException e) { // Catch json parse exception
			
			e.printStackTrace();
			from = null;
		    subject = null;
		    body = null;
		    captcha = null;
		} 
        
		return new MailData(from, subject, body, false, null); 
	}
} 