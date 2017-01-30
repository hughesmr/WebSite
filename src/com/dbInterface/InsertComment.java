package com.dbInterface;

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

import com.email.MailData;

import org.owasp.html.Sanitizers;
import org.owasp.html.PolicyFactory;

/**
 * InsertComment inserts comments into the DB
 */
public class InsertComment {
	
	private static final String INSERT_COMMENT = "INSERT INTO comment " + 
			" (id, body, date, blogId, approved, username) " 
			+ "VALUES (DEFAULT, ?, ?, ?, ?, ?)";

	/**
	 * createComment inserts the comment in the DB
	 * 
	 * @param data is the comment data
	 * @param blogid is the blog id
	 * @return the number of rows inserted
	 */
	public static int createComment(final MailData data, final String blogid){
		
		int status = 0;														
		KeyHolder key = new GeneratedKeyHolder();						
		@SuppressWarnings("resource")
		ApplicationContext context = 										
	    		new ClassPathXmlApplicationContext("db-beans.xml"); 
		DataSource dataSource = (DataSource)context.getBean("dataSource"); 
		JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);     
		final PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
		
		status = jdbcTemplateObject.update(new PreparedStatementCreator() {        
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) 
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_COMMENT, 
                			Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, sanitizer.sanitize(cleanData(data.getBody())));
                ps.setLong(2, System.currentTimeMillis());
                ps.setInt(3, Integer.parseInt(blogid));
                ps.setBoolean(4, false);
                ps.setString(5, sanitizer.sanitize(cleanData(data.getSubject())));
                return ps;
            } 
		}, key); 

	    return status;

	}
	
	/**
	 * cleanData prepares data for insert into db
	 * 
	 * @param input is the input data
	 * @return the cleaned data
	 */
	private static String cleanData(String input){
		
		input = input.replaceAll("'", "''");
		
		return input;
	} 	
} 
