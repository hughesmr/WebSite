package com.admin.scheduler;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.email.NewBlogProcessor;
import com.email.NewsLetterProcessor;

/**
 * CheckScheduledTasks is called to check for scheduled tasks
 */
public class CheckScheduledTasks {

	private int day = 0;
	private int hour = 0;
	private int month = 0;
	private int year = 0;
	
	private static final String GET_TASK = "SELECT * FROM task WHERE running = FALSE";
	private static final String UPDATE_TASK_STATUS = "UPDATE task SET running = " +
			"true WHERE id = ?";
	
	/**
	 * Constructor
	 */
	public CheckScheduledTasks() {
	}
	
	/**
	 * run is called when a task is ran
	 */
	public void run() {
		
		getTime();
		getTasks();
	}
	  
	/**
	 * getTasks gets the current tasks to be ran
	 */
	private void getTasks(){
		  
		ApplicationContext context = new ClassPathXmlApplicationContext("db-beans.xml");  	    
		DataSource dataSource = (DataSource)context.getBean("dataSource");  
		JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);     
		  
		List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(GET_TASK);
		
		for (Map<String, Object> row : rows) {
			
			int id = (Integer) row.get("id");
			int jobType = (Integer) row.get("type");
			long date = (Long)row.get("date");
			
			Calendar d = Calendar.getInstance();
			d.setTimeInMillis(date);
			
			if(d.get(Calendar.DAY_OF_MONTH) == day && d.get(Calendar.HOUR_OF_DAY) <= hour &&
					d.get(Calendar.YEAR) == year && d.get(Calendar.MONTH) == month){
			
				if(jobType == 1){ // If new blog email
				
					Thread thread = new Thread(new NewBlogProcessor(id, (Integer)row.get("jobid"), date));
					thread.start();
				
				}
				else if(jobType == 2){ // If newsLetter to send
					Thread thread = new Thread(new NewsLetterProcessor(id, (Integer)row.get("jobid"), date));
					thread.start();
				
				} 
			
				jdbcTemplateObject.update(UPDATE_TASK_STATUS, id);
			}
		}    
	}
	 
	/**
	 * getTime gets the current time
	 */
	private void getTime(){
		  
		Calendar date = Calendar.getInstance(); 
		  
		this.hour = date.get(Calendar.HOUR_OF_DAY);
		this.day  = date.get(Calendar.DAY_OF_MONTH);
		this.month = date.get(Calendar.MONTH);
		this.year = date.get(Calendar.YEAR);
		  
	}
}
