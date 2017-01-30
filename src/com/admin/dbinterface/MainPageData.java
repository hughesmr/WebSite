package com.admin.dbinterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * MainPageData gets the data for the main admin page
 */
public class MainPageData {
	
	private ApplicationContext context = null;
	private DataSource dataSource = null;
	JdbcTemplate jdbcTemplateObject = null;
	
	private static final String BLOG_TASKS = "SELECT task.date, blog.title FROM" +
			" task INNER JOIN blog ON task.jobid = blog.id";
	private static final String NEWS_TASKS = "SELECT task.date, newsletter.subject" +
			" FROM task INNER JOIN newsletter ON task.jobid = newsletter.id";

	/**
	 * Constructor
	 */
	public MainPageData() {
		context = new ClassPathXmlApplicationContext("db-beans.xml");  	    
		dataSource = (DataSource)context.getBean("dataSource");  
		jdbcTemplateObject = new JdbcTemplate(dataSource);     
	}
	
	/**
	 * loadData loads the data
	 * 
	 * @return
	 */
	public List<String[]> loadData(){
		
		return loadTaskData();
	}
	
	/**
	 * loadTaskData gets task data to show on the main page
	 * 
	 * @return task data
	 */
	private List<String[]> loadTaskData(){
		
		List<String[]> data = new ArrayList<String[]>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a");  
		List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(BLOG_TASKS);
		
		for (Map<String, Object> row : rows) { // For loop through results 
			
			String[] temp = new String[3];
			temp[0] = sdf.format((Long)row.get("date"));
			temp[1] = (String)row.get("title");
			temp[2] = "Blog";
			data.add(temp);
		} 
		
		rows.clear();
		rows = jdbcTemplateObject.queryForList(NEWS_TASKS);
		
		for (Map<String, Object> row : rows) { // For loop through results 

			String[] temp = new String[3];
			temp[0] = sdf.format((Long)row.get("date"));
			temp[1] = (String)row.get("subject");
			temp[2] = "News Letter";
			data.add(temp);
		} 
		
		return data;	
	}
}
