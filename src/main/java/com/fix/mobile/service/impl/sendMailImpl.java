package com.fix.mobile.service.impl;

import com.fix.mobile.config.SercurityConfig;
import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Order;
import com.fix.mobile.entity.ProductChange;
import com.fix.mobile.entity.Ram;
import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.service.ProductChangeService;
import com.fix.mobile.service.sendMailService;
import com.fix.mobile.utils.UserName;
import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.Random;

@Service
public class sendMailImpl implements sendMailService {

	@Autowired
	private ProductChangeService productChangeService;
	@Autowired
	private AccountRepository acccountReposetory;

	@Autowired
	SercurityConfig sendCurity;


	private static final String EMAIL_FORGOT_PASSWORD = "Online Entertainment - New password";

	@Override
	public void SendEmail( String user, String pass,Integer idchange) {
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

			List<ProductChange> listProductSendMail = productChangeService.findByStatusSendEmail( idchange);
			if(listProductSendMail.isEmpty()){
				System.out.println("null");
			}else {
					for ( ProductChange s: listProductSendMail) {
						String EMAIL_WELCOME_SUBJECT =
								"Dear anh chị yêu câu đổi trả hàng của anh chị đã" +
								"được xác nhận vui lòng mang máy "+
								s.getOrderDetail().getProduct().getName() +
								" đến cửa hàng địa chỉ tại Ngõ 470 Đ. Láng, Láng Hạ, Đống Đa, Hà Nội";
						msg.setFrom(new InternetAddress(user));
						InternetAddress[] toAddresses = {new InternetAddress(s.getEmail())};
						msg.setRecipients(Message.RecipientType.TO, toAddresses);
						msg.setSubject("Xác nhận yêu câu đổi trả");
						msg.setSentDate(new Date());
						msg.setText(EMAIL_WELCOME_SUBJECT);
						Transport.send(msg);
					}
				}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	@Override
	public void SendEmailStatus(String user, String pass, Integer status,Integer idOrder) {
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

			if(status != null){
				msg.setFrom(new InternetAddress(user));
				InternetAddress[] toAddresses = {new InternetAddress("email cần gửi")};
				msg.setRecipients(Message.RecipientType.TO, toAddresses);
				msg.setSubject("xin chào");
				msg.setSentDate(new Date());
				msg.setText("");
				// sends the e-mail
				Transport.send(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	@Override
	public void SendEmailChangePass(String user, String pass, String mail) {
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
			Account acc= acccountReposetory.findByEmail(mail);

			if(acc.getEmail().equals(mail)){
				acc.setPassword(sendCurity.pe().encode("kdi23239dsad"));
				acccountReposetory.save(acc);
				msg.setFrom(new InternetAddress(user));
				InternetAddress[] toAddresses = {new InternetAddress(acc.getEmail())};
				msg.setRecipients(Message.RecipientType.TO, toAddresses);
				msg.setSubject("Fix Mobile - Đổi mật khẩu tài khoản");
				msg.setSentDate(new Date());
				msg.setText("Mật khẩu của bạn là: kdi23239dsad");
				Transport.send(msg);
			}
			else{
				System.out.println("bắn log");
			}
		} catch (Exception e) {
		 throw new StaleStateException("Email này không tìm thấy trong database");
		}

	}

	@Override
	public void sendEmailOrder(String user,String pass,String mail,String fullName, Order order) {
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
			Account acc= acccountReposetory.findByEmail(mail);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(user, false));
			if(acc.getEmail().equals(mail)){
				msg.setFrom(new InternetAddress(user));
				InternetAddress[] toAddresses = {new InternetAddress(acc.getEmail())};
				msg.setRecipients(Message.RecipientType.TO, toAddresses);
				msg.setSubject("FixMobile - Thay đổi thông tin đơn hàng mã: "+order.getIdOrder());
				msg.setSentDate(new Date());
				if(order.getStatus()==0){
					msg.setText("Xin chào "+fullName+"\n"+"Đơn hàng của bạn đã được tiếp nhận, chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất");
					Transport.send(msg);
				}
				else if(order.getStatus()==1){
					msg.setText("Xin chào "+fullName+"\n"+"Đơn hàng của bạn đang được xử lý, vui lòng chờ");
					Transport.send(msg);
				}
				else if(order.getStatus()==2){
					msg.setText("Xin chào "+fullName+"\n"+"Đơn hàng của bạn đang được giao, vui lòng kiểm tra lại thông tin");
					Transport.send(msg);
				}
				else if(order.getStatus()==3){
					msg.setText("Xin chào "+fullName+"\n"+"Đơn hàng đã hoàn tất giao dịch, cảm ơn bạn đã sử dụng dịch vụ của chúng tôi");
					Transport.send(msg);
				}
			}
		} catch (Exception e) {
			throw new StaleStateException("Email này không tìm thấy trong database");
		}
	}
}
