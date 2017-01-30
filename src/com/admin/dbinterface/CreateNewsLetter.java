package com.admin.dbinterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * This class creates news letters
 */
public class CreateNewsLetter {
		
	private int letterId = 0;							
	private static ApplicationContext context = null;
	private DataSource dataSource = null;
	private JdbcTemplate jdbcTemplateObject = null;
	
	private static final String INSERT = "INSERT INTO newsletter (id, body, subject, date) " 
			+ "VALUES (DEFAULT, ?, ?, ?)";

	/**
	 * Constructor
	 */
	public CreateNewsLetter(){
		
		context = new ClassPathXmlApplicationContext("db-beans.xml");  	    
		dataSource = (DataSource)context.getBean("dataSource");  
		jdbcTemplateObject = new JdbcTemplate(dataSource);    
	} 	
	
	/**
	 * createNews creates the news letter
	 * 
	 * @param news is the news letter
	 * @return 0 if successful
	 */
	public int createNews(String[] news){
		
		if(insertNewsLetter(news) == 1){ // If successful
			CreateTask.insertTask(jdbcTemplateObject, 2, letterId, Long.parseLong(news[2]));
			return 0;
		} 
		else{ // Else error
			return 1;
		} 
	} 	
	
	/**
	 * insertNewsLetter inserts the news letter into the db
	 * 
	 * @param news is the news letter to insert
	 * @return the number of rows inserted
	 */
	private int insertNewsLetter(final String[] news){
		
		int status = 0;
		KeyHolder key = new GeneratedKeyHolder();							
		
		status = jdbcTemplateObject.update(new PreparedStatementCreator() {     
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) 
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, news[1]);
                ps.setString(2, news[0]);
                ps.setLong(3, Long.parseLong(news[2]));
                return ps;
            } 
		}, key); 
		letterId = (Integer) key.getKeys().get("id");
		
		return status;
	} 
}
