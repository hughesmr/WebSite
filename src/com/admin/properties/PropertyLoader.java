package com.admin.properties;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.admin.dbinterface.LoadProperties;

/**
 * PropertyLoader is used to load system propteries into
 * memory once instead of querying the db everytime
 * we need them
 */
public class PropertyLoader extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	public static List<String> bannedWords = null; 
	public static String fromAdress = null; 
	public static int sleepTime = 0;
	public static int mailBatch = 0;
	public static String url = "";
	public static String adminEmail = "";	
	public static long mailUnsubscribe = 0;
	public static long mailUnsubscribeWeeks = 0;
	
	/**
	 * init is called during app initialization to grab app properties 
	 */
	public void init() throws ServletException{
		
		LoadProperties props = new LoadProperties();
		
		bannedWords = props.getBannedWords();
		fromAdress  = props.getFromAdress();
		sleepTime   = props.getSleepTime();
		mailBatch   = props.getMailBatch();
		url 		= props.getUrl();
		adminEmail  = props.getAdminEmail();
		mailUnsubscribeWeeks = props.getMailUnsubscribe();
		mailUnsubscribe = mailUnsubscribeWeeks * 604800000;
		
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("spring-tasks.xml");
	} 	
}
