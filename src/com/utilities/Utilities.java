package com.utilities;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import com.admin.properties.PropertyLoader;

public class Utilities {
	
	private static final String DELETE_TASK = "DELETE FROM task WHERE id = ?";
	private static final String GET_ADDRESSES = "SELECT email FROM users WHERE" + 
			" recieveemail = TRUE AND id >= ? and id < ? ORDER BY id";
	private static final String GET_TEMPLATE = "SELECT body FROM templates WHERE name = ?";
	private static final String TEMPLATE_COUNT = "SELECT COUNT(*) FROM templates WHERE name = ?";
	
	/**
	 * removeTask removes the task from the db
	 * 
	 * @param jdbcTemplateObject is a jdbc connection object
	 * @param id is the id of the task to remove
	 */
	public static void removeTask(JdbcTemplate jdbcTemplateObject, int id){
		
		jdbcTemplateObject.update(DELETE_TASK, new Object[]{id});
	}
	
	/**
	 * getEmailAddress gets a list of email addresses from userid to userid + batch property
	 * 
	 * @param jdbcTemplateObject is a jdbc connection object 
	 * @param userid is the starting user id 
	 * @return a list of email addresses
	 */
	public static List<String> getEmailAddress(JdbcTemplate jdbcTemplateObject, int userid){

		return (List<String>) jdbcTemplateObject.queryForList(GET_ADDRESSES, 
				new Object[] {userid, (userid + PropertyLoader.mailBatch)}, String.class);
	}
	
	/**
	 * getTemplate checks if there is a custom template
	 * 
	 * @param jdbcTemplateObject is a jdbc connection object 
	 * @param type is the type of template
	 * @return the template
	 */
	public static String getMailTemplate(JdbcTemplate jdbcTemplateObject, String type){
		
		String result = "";
		
		if(jdbcTemplateObject.queryForObject(TEMPLATE_COUNT, 
				new Object[]{type}, Integer.class) > 0){ // If template override 
			
			result = jdbcTemplateObject.queryForObject(GET_TEMPLATE, new Object[]{type}, String.class);
		}
		
		return result;
		
	}
}

