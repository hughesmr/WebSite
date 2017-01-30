package com.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * This class is used to get the tags that are displayed with all 
 * of the blogs
 */

public class TagsRetrieval {
	
	private static final String TAGS_BY_BLOG = "SELECT tag FROM tags WHERE blogid = ?"; 
	
	/**
	 * getListofTags returns a list of tags as strings
	 * 
	 * @param blogid is the id to get tags for
	 * @param jdbcTemplateObject is the spring jdbc connection object
	 * @return the list of tags
	 */	
	public static List<String> getListofTags(String blogid, JdbcTemplate jdbcTemplateObject){
		
		List<String> tags = new ArrayList<String>();           
		List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(TAGS_BY_BLOG, 
				new Object[] {Integer.parseInt(blogid)}); 			
	
		for (Map<String, Object> row : rows) { // For each tag in taga
			tags.add(((String)row.get("tag")).trim());
		} 
		
		return tags;
	} 
} 
