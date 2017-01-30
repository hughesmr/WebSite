package com.admin.dbinterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.Mail.MailData;

/**
 * CreateBlog is used to create blogs
 */
public class CreateBlog {
	
	private ApplicationContext context = null;
	private DataSource dataSource = null;
	private JdbcTemplate jdbcTemplateObject = null;
	private MailData data = null;
	private String emailNot = "";
	private int blogId = 0;
	private long date = 0;
	
	private static final String INSERT = "INSERT INTO blog (id, body, date, title) " 
			+ "VALUES (DEFAULT, ?, ?, ?)";
	private static final String SHOULD_EMAIL = "INSERT INTO blogEmail (id, blogid, body) " 
			+ "VALUES (DEFAULT, ?, ?)";
	private static final String INSERT_TAGS = "INSERT INTO tags (id, tag, blogid, date) " 
			+ "VALUES (DEFAULT, ?, ?, ?)";

	/**
	 * Constructor 
	 * 
	 * @param data is the blog data
	 * @param emailNotification is the email notification data
	 */
	public CreateBlog(MailData data, String emailNotification){
		
		this.emailNot = emailNotification;
		this.data = data;
		date = System.currentTimeMillis();
		context = new ClassPathXmlApplicationContext("db-beans.xml");  	  
		dataSource = (DataSource)context.getBean("dataSource"); 
		jdbcTemplateObject = new JdbcTemplate(dataSource);     
	} 	
	
	/**
	 * blogCreation creates the blog
	 * 
	 * @return are the number of blogs inserted
	 */
	public int blogCreation(){
		
		int status = 0;
		
		status = createBlog();
		if(!data.getFrom().equals("")){
			createTag();
		}
		
		if(status == 1){ // If blog created
			CreateTask.insertTask(jdbcTemplateObject, 1, blogId, Long.parseLong(data.getDate())); // Create Email Task
			createEmailUpdate();
		} 
		
		return status;
	} 	
	
	/**
	 * createBlog inserts the blog
	 * 
	 * @return the number of rows
	 */
	private int createBlog(){
		
		int status = 0;														
		KeyHolder key = new GeneratedKeyHolder();							
				
		status = jdbcTemplateObject.update(new PreparedStatementCreator() {    
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT, 
                		Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, cleanData(data.getBody()));
                ps.setLong(2, Long.parseLong(data.getDate()));
                ps.setString(3, cleanData(data.getSubject()));
                return ps;
            } 
		}, key); 
		blogId = (Integer) key.getKeys().get("id");

	    return status;
	} 
	
	/**
	 * createTag adds the tags to the db
	 */
	private void createTag(){
		
		List<String> tags = Arrays.asList(data.getFrom().split("\\s*,\\s*"));
	
		for(String t : tags){ // For each tag
		
			jdbcTemplateObject.update(INSERT_TAGS, new Object[] {t, blogId, date});
		} 
	} 
	
	/**
	 * createEmailUpdate inserts an email update tasks
	 */
	private void createEmailUpdate(){
		
			jdbcTemplateObject.update(SHOULD_EMAIL, new Object[] {blogId, emailNot});
	} 	
	
	/**
	 * cleanData is used to clean input for sql databases
	 * 
	 * @param input is the input data
	 * @return the cleaned data
	 */
	private static String cleanData(String input){
		
		input = input.replaceAll("'", "''");
		
		return input;
	} 
}
