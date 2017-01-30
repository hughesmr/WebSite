package com.email;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.admin.properties.PropertyLoader;
import com.token.TokenGen;
import com.utilities.Utilities;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * NewBlogProcessor processes new blogs mail
 */
public class NewBlogProcessor implements Runnable{

	private int id = 0;
	private int jobid = 0;
	private long date = 0;
	private int userid = 0;
	private String blogBody = "";
	private String blogTitle = null;
	private ApplicationContext context = null;
	private JdbcTemplate jdbcTemplateObject = null;
	private DataSource dataSource = null;
	private static final String path = "com/email/template/newBlog.ftl";
	
	private static final String BLOG_DATA = "SELECT title, body FROM blog WHERE id = ?";
	private static final String BLOG_BODY = "SELECT body FROM blogemail WHERE blogid = ?";
	
	/**
	 * Constructor
	 * 
	 * @param id is the id of the blog
	 * @param jobid is the jobid 
	 * @param date is the date the blog was posted
	 */
	public NewBlogProcessor(int id, int jobid, long date){
		
		this.id = id;
		this.jobid = jobid;
		this.date = date;
		this.userid = 0;
		this.context = new ClassPathXmlApplicationContext("db-beans.xml");  	    // Create spring appcontext
		dataSource = (DataSource)context.getBean("dataSource");  // Get dataSource
		jdbcTemplateObject = new JdbcTemplate(dataSource);     // Object for jdbcTemplate
		
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		blogData();
		
		if(blogBody.length() > 0){
			emailProcess();
		}
		
		Utilities.removeTask(this.jdbcTemplateObject, this.id);	
	}
	
	/**
	 * emailProcess processes the email updates
	 */
	private void emailProcess(){
		
		long date = TokenGen.getFutureDate();
	    byte[] bDate = TokenGen.longToBytes(date);
		String compiledBody = "";
		String token = "";
		String subject = createSubject();
		List<String> addresses = Utilities.getEmailAddress(jdbcTemplateObject, userid);
		userid = userid + PropertyLoader.mailBatch;

		while(!addresses.isEmpty()){
			
			for(String email : addresses){
				
				email = email.trim();
				token = TokenGen.getToken(bDate);
				InsertUnsubscribeToken.insertUnsub(token, email, date, this.jdbcTemplateObject);
				compiledBody = createBody(token, email);
			
				SendMail.send(email.replaceAll("\\s+",""), subject, compiledBody);
			}
			try {
				Thread.sleep(PropertyLoader.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			addresses.clear();
			addresses = Utilities.getEmailAddress(jdbcTemplateObject, userid);	
			userid = userid + PropertyLoader.mailBatch;
		}
		
	}
	
	/**
	 * createBody creates the body of the message
	 * 
	 * @param token is the unsubscribe token
	 * @param address the address to send to
	 * @return the body of the email
	 */
	private String createBody(String token, String address){
		
		String overlay = Utilities.getMailTemplate(jdbcTemplateObject, "newBlog");
		Template template = null;									// Template object
		StringWriter outputWriter = new StringWriter();   			// String writer to output ftl to string
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Map<String, Object> data = new HashMap<String, Object>();   // Map to hold ftl data
        Configuration cfg = new Configuration(); 				    // Config object for ftl
        String image = getImage();
        String body = blogEmailBody();
        
        data.put("blogEmail", body);                       
        data.put("blogimage", image);
        data.put("date", sdf.format(new Date(this.date))); 
        data.put("token", token); 
        data.put("address", address); 
        data.put("url", PropertyLoader.url); 
        
        try { // Try to compile template
        	if(overlay.length() == 0){ // If no template overlay
        		
        		cfg.setClassForTemplateLoading(this.getClass(), "/"); // Set classpath for template
        		template = cfg.getTemplate(this.path); // Load template
        	} 
        	else{// Else template overlay
        		
        		cfg.setObjectWrapper(new DefaultObjectWrapper());
        		template = new Template("newBlog", new StringReader(overlay), cfg);
        	}
        	
			template.process(data, outputWriter); // Create template  
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (TemplateException e) {
			
			e.printStackTrace();
		} 

		return outputWriter.toString();
		
	}
	
	/**
	 * getImage gets images for the message
	 * 
	 * @return the image tag
	 */
	private String getImage(){
		
		int start = 0;      // Int to hold the start position of the image
		int end = 0;	    // Int to hold end position of the image
		String temp = ""; // String to hold the image html
		
		if((start = this.blogBody.indexOf("<img")) != -1){ // If image tag
		
			temp = this.blogBody.substring(start);	
			end = temp.indexOf(">");
			temp = temp.substring(0, (end+1));
	
		} 
	
		return temp;
		
	} 
	
	/**
	 * creatSubject creates the email subject
	 * 
	 * @return the subject
	 */
	private String createSubject(){
		
		String subject = "New Blog: " + this.blogTitle;
		
		return subject;
		
	}
	
	/**
	 * blogData gets the blog data
	 */
	private void blogData(){
		
		List<Map<String, Object>> rows = 
				jdbcTemplateObject.queryForList(BLOG_DATA, new Object[] {this.jobid});
		
		for (Map<String, Object> row : rows) {
			this.blogTitle = (String)row.get("title");
			this.blogBody = (String)row.get("body");
		}
	}
	
	/**
	 * blogEmailBody gets the blog email body
	 * 
	 * @return the body of the blog for emails
	 */
	private String blogEmailBody(){
		
		String result = "";
		
		result = jdbcTemplateObject.queryForObject(BLOG_BODY, 
				new Object[] {this.jobid}, String.class);
		return result;
		
	}
}
