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

import com.admin.dbinterface.TemplateReset;
import com.admin.dbinterface.UpdateCreateTemplate;
import com.admin.dbinterface.UpdateSettings;

/**
 * TemplateUpdateActions handles tempalte updates
 */
public class TemplateUpdateAction extends HttpServlet{

	/**
	 * doPost posts template update actions
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int status = 0;				
        String rec = "";			
        String data = "";			
        String[] parsedData = new String[2]; 
        BufferedReader st = new BufferedReader(new InputStreamReader(request.getInputStream())); 
                
        while((rec = st.readLine()) != null){ // While lines to read
        		data = data + rec;      	
        } // End while lines to read
        
        parsedData = parseJson(data);
        if(parsedData[1].equals("resetTemplate")){
        	TemplateReset reset = new TemplateReset(parsedData[0]);
        	status = reset.reset();
        }
        else{
            UpdateCreateTemplate update = new UpdateCreateTemplate(parsedData[1], parsedData[0]);
            status = update.startUpdateOrInsert();
        }
        	
        if(status == 0){ // If mail sent successfully 	
        	response.getWriter().write("{\"status\":\"0\"}");
        }
        else{ // Else mail failed
        	response.getWriter().write("{\"status\":\"1\"}");
        } 
	} 
	
	/**
	 * parseJson parses out json for template updates
	 * 
	 * @param json is the json to parse
	 * @return the parsed json string array
	 */
	private String[] parseJson(String json){
		
		String[] setting = new String[2];
		Object obj = null;		// Object used to hold json

		JSONParser parser = new JSONParser();
		try { // Try parsing json
			obj = parser.parse(json);
			JSONObject jobj = (JSONObject)obj;
			setting[0] = (String) jobj.get("type");
	        setting[1] = ((String) jobj.get("val")).replace("&#8220", "\"").replace("&#13;", "\n").replace("&#32;", " ");

		} catch (ParseException e) { // Catch json parse exception
			 
			e.printStackTrace();
		}  
        
		return setting;	
	}
}
