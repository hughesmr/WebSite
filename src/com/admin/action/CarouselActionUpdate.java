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

import com.admin.dbinterface.CreateCarousel;

/**
 * CarouselActionUpdate handles carousel updates
 */
public class CarouselActionUpdate extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * doPost posts the update action
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int status = 0;				
        String rec = "";			
        String data = "";			
        String[] parsedData = new String[2]; 
        CreateCarousel create = new CreateCarousel();
        BufferedReader st = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String del = request.getParameter("delete");         
        while((rec = st.readLine()) != null){ // While lines to read
        		data = data + rec;      	
        } 
        
        if(del.equals("n")){
          parsedData = parseJson(data, 0);
          status = create.create(parsedData);
        }
        else{
        	parsedData = parseJson(data, 1);
        	status = create.deleteSlide(parsedData[0]);
        }
        
        if(status == 0){ // If mail sent successfully 	
        	response.getWriter().write("{\"status\":\"0\"}");
        }
        else{ // Else mail failed
        	response.getWriter().write("{\"status\":\"1\"}");
        } 
	}	
	
	/**
	 * parseJson parses json and returns a string array 
	 * 
	 * @param json to parse	
	 * @param type the template type
	 * @return the string arrray
	 */
	private String[] parseJson(String json, int type){
		
		String[] upl = new String[3];
		Object obj = null;		

		JSONParser parser = new JSONParser();
		
		if(type == 0){
		
			try { // Try parsing json
				obj = parser.parse(json);
				JSONObject jobj = (JSONObject)obj;
				upl[0] = (String) jobj.get("name");
				upl[1] = ((String) jobj.get("des")).replace("&#8220", "\"").replace("&#13;", "\n").replace("&#32;", " ");
				upl[2] = ((String) jobj.get("address")).replace("&#8220", "\"").replace("&#13;", "\n").replace("&#32;", " ");
				
			} catch (ParseException e) { // Catch json parse exception
				
				e.printStackTrace();
			} 
		}
		else{
			try { // Try parsing json
				obj = parser.parse(json);
				JSONObject jobj = (JSONObject)obj;
				upl[0] = (String) jobj.get("delete");

			} catch (ParseException e) { // Catch json parse exception
				
				e.printStackTrace();
			} 
		}
        
		return upl; 
	}
}
