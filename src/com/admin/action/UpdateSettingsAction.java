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

import com.admin.dbinterface.UpdateSettings;

/**
 * UpdateSettingsAction updates settings
 */
public class UpdateSettingsAction extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * doPost handles the settings update
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int status = 0;			
        String rec = "";			
        String data = "";			
        String[] parsedData = new String[2]; 
        UpdateSettings update = new UpdateSettings(); 
        BufferedReader st = new BufferedReader(new InputStreamReader(request.getInputStream()));
                
        while((rec = st.readLine()) != null){ // While lines to read
        		data = data + rec;      	
        } 
        
        parsedData = parseJson(data); 
 
        status = update.updateProperty(parsedData);
        	
        if(status == 0){ // If mail sent successfully 	
        	response.getWriter().write("{\"status\":\"0\"}");
        }
        else if(status == 1){ // Else mail failed
        	response.getWriter().write("{\"status\":\"1\"}");
        } 
        else{// Else prop deleted
        	response.getWriter().write("{\"status\":\"2\"}");
        }
	} 
	
	/**
	 * parseJson parses the json
	 * 
	 * @param json the json to parse
	 * @return the parsed json
	 */
	private String[] parseJson(String json){
		
		String[] setting = new String[2];
		Object obj = null;		// Object used to hold json
		
		JSONParser parser = new JSONParser();
		try { // Try parsing json
			obj = parser.parse(json);
			JSONObject jobj = (JSONObject)obj;
			setting[0] = (String) jobj.get("type");
	        setting[1] = ((String) jobj.get("val")).replace("&#8220", "\"");

		} catch (ParseException e) { // Catch json parse exception
			
			e.printStackTrace();
		} 
        
		return setting; 
	}
}
