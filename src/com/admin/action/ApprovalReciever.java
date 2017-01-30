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

import com.admin.dbinterface.CommentApproval;
import com.validate.ValidateCapt;

/**
 * ApprovalReciever receives comment approvals
 */
public class ApprovalReciever extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private String captcha; 

	/**
	 * doPost handles posts
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int status = 0;				
        String rec = "";			
        String data = "";			
        String[] app = new String[2];
        CommentApproval approve = null;
        BufferedReader st = new BufferedReader(new InputStreamReader(request.getInputStream())); 
                
        while((rec = st.readLine()) != null){ // While lines to read
        		data = data + rec;      	
        }
        
        app = parseJson(data); 
       
        if(captcha != null && ValidateCapt.validate(captcha)){ // If captcha is not null
    
        	approve = new CommentApproval();
        	approve.approveOrDelete(app);

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
	 * parseJson parses json and returns a string array
	 * 
	 * @param json is the json to parse
	 * @return string array
	 */
	private String[] parseJson(String json){
		
		String[] app = new String[2];
		Object obj = null;		
		captcha = null;         
		
		JSONParser parser = new JSONParser();
		try { // Try parsing json
			obj = parser.parse(json);
			JSONObject jobj = (JSONObject)obj;
			app[0] = (String) jobj.get("app");
			app[1] = (String) jobj.get("del");
			captcha = (String) jobj.get("captcha");
		} catch (ParseException e) { // Catch json parse exception
			
			e.printStackTrace();
		    captcha = null;
		} 
    
		return app; 
	} 	
} 
