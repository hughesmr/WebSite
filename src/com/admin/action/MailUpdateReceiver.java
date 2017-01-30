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

import com.admin.dbinterface.CreateNewsLetter;
import com.validate.CaptchaValidation;

/**
 * MailUpdateReciever recieves news letter updates
 */
public class MailUpdateReceiver extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private String captcha; 

	/**
	 * doPost handles the newsletter post
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int status = 0;				
        String rec = "";			
        String data = "";			
        CreateNewsLetter news = new CreateNewsLetter(); 
        String[] parsedData = null;

        BufferedReader st = new BufferedReader(new InputStreamReader(request.getInputStream())); // Buffered reader to read in request stream
                
        while((rec = st.readLine()) != null){ // While lines to read
        		data = data + rec;      	
        } 
        
        parsedData = parseJson(data); 
       
        if(captcha != null && CaptchaValidation.validate(captcha)){ // If captcha is not null
     
        	status = news.createNews(parsedData);

        	if(status == 0){ // If mail sent successfully 	
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
	 * parseJson parses the json 
	 * 
	 * @param json is the json to parse
	 * @return formated json
	 */
	private String[] parseJson(String json){
		
		String[] content = new String[3];
		Object obj = null;		
		captcha = null;         
		
		JSONParser parser = new JSONParser();
		try { // Try parsing json
			obj = parser.parse(json);
			JSONObject jobj = (JSONObject)obj;
			content[0] = (String) jobj.get("subject");
	        content[1] = ((String) jobj.get("body")).replace("&#8220", "\"");
	        content[2] = (String) jobj.get("date");
	        captcha = (String) jobj.get("captcha");

		} catch (ParseException e) { 
			e.printStackTrace();
		    captcha = null;
		} 
        
		return content; 
		
	}	
} 
