package com.admin.dbinterface;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * This class is used to insert new tasks.
 * 
 * 
 * 1 == Blog Email
 * 2 == Email News Letter
 */
public class CreateTask {
	
	private static final String INSERT = "INSERT INTO task (id, " +
			"type, jobid, date, running) VALUES (DEFAULT, ?, ?, ?, ?)";
	
	public static int insertTask(JdbcTemplate jdbcTemplateObject, int type, int jobId, long taskDate){
		
		return jdbcTemplateObject.update(INSERT, new Object[] {type, jobId, taskDate, false});
	} 
} 
