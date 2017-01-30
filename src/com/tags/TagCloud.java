package com.tags;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * This class is used to generate a tag cloud for 
 * for the front end. There are no args passed and 
 * json with all of the tag cloud info for weight,
 * name and the link are returned. <3 Mike
 */
public class TagCloud {
	
	
    private DataSource dataSource = null; 			
    private JdbcTemplate jdbcTemplateObject = null; 
    private Cloud cloud = null;			
    
	private static final String GET_TAGS = "SELECT tag FROM tags"; 
    
    /**
     * Constructor
     */
	public TagCloud(){
		
		@SuppressWarnings("resource")
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");
		jdbcTemplateObject = new JdbcTemplate(dataSource);
		cloud = new Cloud();
		cloud.setMaxWeight(38.0);
		cloud.setMinWeight(10.0);
		cloud.setDefaultLink("/tags?tag=%s");
		cloud.setMaxTagsToDisplay(20);
	} 
	
	/**
	 * getCloud is a public accessor method which returns the tag cloud data in json format
	 * 
	 * @return string formated json with tag cloud data
	 */	
	public String getCloud(){
		
		queryForTags();
		
		return getJson(cloud.tags());
	}
		
	/**
	 * queryForTags queries the db for all tags
	 */
	private void queryForTags(){
		
		List<Map<String, Object>> rows = 
				jdbcTemplateObject.queryForList(GET_TAGS);
		
		for (Map<String, Object> row : rows) { // For loop through results 
			cloud.addTag(new Tag((String)row.get("tag")));
		} 
	}
	
	/**
	 * getJson gets the tag cloud json
	 * 
	 * @param tList is the list of tags
	 * @return tag cloud json in string format 
	 */
	private String getJson(List<Tag> tList){
		
		String json = "{\"tags\":["; // String to hold json
		
		for(Tag t : tList){ // For each tag in list
			json = json + "{\"name\":\"" + t.getName().trim() + "\",";  
			json = json + "\"weight\":\"" + Double.toString(t.getWeight()) + "\",";  
			json = json + "\"link\":\"" + t.getLink().trim() + "\"},";  

		} 
		
		json = json.substring(0,json.length()-1) + "]}";
		
		return json; 
	} 
} 