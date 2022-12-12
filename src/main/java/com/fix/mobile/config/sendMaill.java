package com.fix.mobile.config;

import org.aspectj.bridge.Message;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Date;
import java.util.Properties;


public class sendMaill {
	@Autowired
	JavaMailSender mailsend;
	public  void sendEmail(String host, String port,
								 final String userName, final String password, String toAddress,
								 String subject, String message) throws AddressException, MessagingException {
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password.toCharArray());
			}
		};

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		// Setting up necessary details
		mailMessage.setFrom("top1zukavietnam@gmail.com");
		mailMessage.setTo(userName);
		mailMessage.setText("hello");
		mailMessage.setSubject("xin chào");
		mailsend.send(mailMessage);
	}
}
