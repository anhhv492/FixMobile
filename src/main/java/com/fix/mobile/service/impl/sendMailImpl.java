package com.fix.mobile.service.impl;

import com.fix.mobile.entity.ProductChange;
import com.fix.mobile.service.ProductChangeService;
import com.fix.mobile.service.sendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class sendMailImpl implements sendMailService {

	@Autowired
	private ProductChangeService productChangeService;

	private static final String EMAIL_WELCOME_SUBJECT = "" +
			"Dear xin chào anh chị yêu câu đổi trả hàng của anh chị đã được xác nhận vui lòng mang đến cửa hàng";
	private static final String EMAIL_FORGOT_PASSWORD = "Online Entertainment - New password";

	@Override
	public void SendEmail( String user, String pass) {
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user,pass);
				}
			});
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(user, false));


			List<ProductChange> listProductSendMail = productChangeService.findByStatusSendEmail();
			if(listProductSendMail.isEmpty()){
				System.out.println("null");
			}else {
				for ( ProductChange  s :  listProductSendMail) {
//					SimpleMailMessage mailMessage = new SimpleMailMessage();
//					mailMessage.setFrom(String.valueOf(new InternetAddress(user)));
//					mailMessage.setTo(s.getEmail());
//					mailMessage.setText(EMAIL_WELCOME_SUBJECT);
//					mailMessage.setSubject("xin chào");
//					mailsend.send(mailMessage);
//					System.out.println("đã gửi email " + mailMessage);

					msg.setFrom(new InternetAddress(user));
					InternetAddress[] toAddresses = {new InternetAddress(s.getEmail())};
					msg.setRecipients(Message.RecipientType.TO, toAddresses);
					msg.setSubject("xin chào");
					msg.setSentDate(new Date());
					msg.setText(EMAIL_WELCOME_SUBJECT);
					// sends the e-mail
					Transport.send(msg);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
}
