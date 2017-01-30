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
 * NewsLetterProcessor processes news letters
 */
public class NewsLetterProcessor implements Runnable{
	
	private int id = 0;			
	private int jobid = 0;		
	private long date = 0;	
	private int userid = 0;		
	private String body = "";	  
	private String subject = "";  
	private static final String path = "com/email/template/newsLetter.ftl"; 
	private ApplicationContext context = null;			      
	private JdbcTemplate jdbcTemplateObject = null;		      
	private DataSource dataSource = null;					

	private static final String NEWSLETTER = "SELECT subject, body FROM newsletter WHERE id = ?";
	/**
	 * Constructor
	 * 
	 * @param id is the task id
	 * @param jobid is the job id
	 * @param date is the send date
	 */
	public NewsLetterProcessor(int id, int jobid, long date){
		
		this.id = id;	  
		this.jobid = jobid;
		this.date = date;  
		this.userid = 0;  
		this.context = new ClassPathXmlApplicationContext("db-beans.xml"); 
		dataSource = (DataSource)context.getBean("dataSource");           
		jdbcTemplateObject = new JdbcTemplate(dataSource);                
		
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		
		newsData(); // Get news Data
		
		if(body.length() > 0){ // If there is a body to process
			
			emailProcess();
		} 
		
		Utilities.removeTask(this.jdbcTemplateObject, this.id); // Remove task from db
		
	}
	
	/**
	 * emailProcess processes emails for each user
	 */
	private void emailProcess(){
		
		long date = TokenGen.getFutureDate();
	    byte[] bDate = TokenGen.longToBytes(date);
		String compiledBody = "";
		String token = "";
		List<String> addresses = Utilities.getEmailAddress(jdbcTemplateObject, userid);
		userid = userid + PropertyLoader.mailBatch; 
		
		while(!addresses.isEmpty()){ // While user emails to send
			
			for(String email : addresses){ // For each user email address
				email = email.trim();
				token = TokenGen.getToken(bDate);
				InsertUnsubscribeToken.insertUnsub(token, email, date, this.jdbcTemplateObject);
				compiledBody = createBody(token, email);

				SendMail.send(email.replaceAll("\\s+",""), this.subject, compiledBody); // Send email
			} 
			
			try { 
				Thread.sleep(PropertyLoader.sleepTime);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			} 
			
			token = "";
			compiledBody = "";
			addresses.clear();
			addresses = Utilities.getEmailAddress(jdbcTemplateObject, userid);
			userid = userid + PropertyLoader.mailBatch; 
			
		} 
	}
	
	/**
	 * createBody creates the body of the email
	 * 
	 * @param token is the unsubscribe token
	 * @param address is the address of the user
	 * 
	 * @return the body of the email
	 */
	private String createBody(String token, String address){
		
		String overlay = Utilities.getMailTemplate(jdbcTemplateObject, "newsLetter");
		Template template = null;									
		StringWriter outputWriter = new StringWriter();   			
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Map<String, Object> data = new HashMap<String, Object>(); 
        Configuration cfg = new Configuration(); 				   

        data.put("newsLetter", body); 
        data.put("date", sdf.format(new Date(this.date))); 
        data.put("token", token); 
        data.put("address", address); 
        data.put("url", PropertyLoader.url); 
        
        try { // Try to compile template
        	if(overlay.length() == 0){ // If no template overlay
        		
        		cfg.setClassForTemplateLoading(this.getClass(), "/"); 
        		template = cfg.getTemplate(this.path); 
        	} 
        	else{// Else template overlay
        		
        		cfg.setObjectWrapper(new DefaultObjectWrapper());
        		template = new Template("newsLetter", new StringReader(overlay), cfg);
        	} 
        	
			template.process(data, outputWriter);  
			
		} catch (IOException e) {
	
			e.printStackTrace();
		} catch (TemplateException e) {
		
			e.printStackTrace();
		} 

		return outputWriter.toString();	
	} 
	
	/**
	 * newsData gets the news letter data
	 */
	private void newsData(){
		
		List<Map<String, Object>> rows = 
				jdbcTemplateObject.queryForList(NEWSLETTER, new Object[] {this.jobid});
		
		for (Map<String, Object> row : rows) { // For each result
			
			this.subject = (String)row.get("subject");
			this.body = (String)row.get("body");
		}
	} 
}

