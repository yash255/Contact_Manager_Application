package com.contact.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	public boolean  sendEmail(String message, String subject, String to) {
		// TODO Auto-generated method stub
    	//variable for gmail 
	 boolean f = false;
	 
	 String from = "yash31khandelwal@gmail.com";
	 
	 
    			String host = "smtp.gmail.com";
    			
    			//get system property
    		Properties properties =	System.getProperties(); 
    		System.out.println(properties);
    		
    		//setting important information
    		
    		//host set
    		properties.put("mail.smtp.host", host);
    		properties.put("mail.smtp.port", "465");
    		properties.put("mail.smtp.ssl.enable", "true");
    		properties.put("mail.smtp.auth", "true");
    		
    		//step 1 to get session method
    	Session session =	Session.getInstance(properties, new Authenticator() {
    			
    			@Override
    			protected PasswordAuthentication getPasswordAuthentication() {
    				
    				return new PasswordAuthentication("yashkhandelwalseb25@gmail.com", "Yashseb@25");
    			}
    		});

    	session.setDebug(true);

    	//step 2 compose the message test,multimedia

    	  MimeMessage m = new MimeMessage(session);
    	  
    	  //from email
    	  try {
    		  m.setFrom(from);
    		  //adding recipient to message
    		  m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    		  
    		  //adding subject to message
    		  m.setSubject(subject);
    		  
    		//  m.setText(message);
    		  
    		  m.setContent(message, "text/html");
    		  
    		  //adding attachment
    		  
    		  
    		  //file path
//    		  String path ="E:\\Downloads\\logo.png";
//    		  
//    		  MimeMultipart mimeMultipart = new MimeMultipart();
//    		  
//    		  MimeBodyPart textPart = new MimeBodyPart();
//    		  
//    		  MimeBodyPart filePart = new MimeBodyPart();
//    		  
//    		  try {
//    			  textPart.setText(message);
//    			  
//    			  File file = new File(path);
//    			  filePart.attachFile(file);
//    			  
//    			  mimeMultipart.addBodyPart(textPart);
//    			  mimeMultipart.addBodyPart(filePart);
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//    		  
//    		  m.setContent(mimeMultipart);
//    		  
    		  
    		  //send
    		  
    		  //send message using Transport class
    		  
    		Transport.send(m);
    		System.out.println("message send");
    		f=true;
    	  }
    	  catch (Exception e) {
			// TODO: handle exception
    		  e.printStackTrace();
		}
    	  return f;
		
	}

}
