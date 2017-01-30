package com.email;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;

import com.admin.properties.PropertyLoader;
/**
 * SendMail sends emails
 */
public class SendMail {
	
	 private static final String u = "MAIL USERNAME";
     private static final String pw = "MAIL PASSWORD";
	
	/**
	 * send sends the email
	 * 
	 * @param to is the address to send to
	 * @param subject is the subject of the message
	 * @param body is the body of the message
	 * 
	 * @return true if send is successful
	 */
	public static boolean send(String to, String subject, String body){
		
		 Properties props = new Properties(); 
		
		 if(to != null && subject != null && body != null && 
				 PropertyLoader.fromAdress != null){ // If error parsing json
		 
			 props.put("mail.smtp.host", "smtp.gmail.com");
			 props.put("mail.smtp.socketFactory.port", "587");
			 props.put("mail.smtp.socketFactory.class", "javax.net.SocketFactory");
			 props.put("mail.smtp.auth", "true");
			 props.put("mail.smtp.port", "587");
			 props.put("mail.smtp.ssl.enable", "false");
			 props.put("mail.smtp.starttls.enable", "true");
			 props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	   
		     Session session = Session.getInstance(props,
		   		  new javax.mail.Authenticator() {
		   			protected PasswordAuthentication getPasswordAuthentication() {
		   				return new PasswordAuthentication(u, pw);
		   			}
		   	 });
		     
		    session.setDebug(false);
	    
			try{ // Try to send message
				
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(PropertyLoader.fromAdress));
				message.setReplyTo(new javax.mail.Address[]{
						new javax.mail.internet.InternetAddress(to)
				});
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(u));
				message.setSubject(subject);
				message.setText(body);
				message.setContent(body, "text/html");
				Transport.send(message);
				
				return true;
				
			}catch(MessagingException mex){ 
				
				System.out.println("message failed to send: " + mex);
			} 	
		}
		
		return false;
	} // End method send
	
} // End class SendMail
