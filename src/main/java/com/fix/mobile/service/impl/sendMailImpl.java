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
								"Dear anh ch??? y??u c??u ?????i tr??? h??ng c???a anh ch??? ????" +
								"???????c x??c nh???n vui l??ng mang m??y "+
								s.getOrderDetail().getProduct().getName() +
								" ?????n c???a h??ng ?????a ch??? t???i Ng?? 470 ??. L??ng, L??ng H???, ?????ng ??a, H?? N???i";
						msg.setFrom(new InternetAddress(user));
						InternetAddress[] toAddresses = {new InternetAddress(s.getEmail())};
						msg.setRecipients(Message.RecipientType.TO, toAddresses);
						msg.setSubject("X??c nh???n y??u c??u ?????i tr???");
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
				InternetAddress[] toAddresses = {new InternetAddress("email c???n g???i")};
				msg.setRecipients(Message.RecipientType.TO, toAddresses);
				msg.setSubject("xin ch??o");
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
				msg.setSubject("Fix Mobile - ?????i m???t kh???u t??i kho???n");
				msg.setSentDate(new Date());
				msg.setText("M???t kh???u c???a b???n l??: kdi23239dsad");
				Transport.send(msg);
			}
			else{
				System.out.println("b???n log");
			}
		} catch (Exception e) {
		 throw new StaleStateException("Email n??y kh??ng t??m th???y trong database");
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
				msg.setSubject("FixMobile - Thay ?????i th??ng tin ????n h??ng m??: "+order.getIdOrder());
				msg.setSentDate(new Date());
				if(order.getStatus()==0){
					msg.setText("Xin ch??o "+fullName+"\n"+"????n h??ng c???a b???n ???? ???????c ti???p nh???n, ch??ng t??i s??? li??n h??? v???i b???n trong th???i gian s???m nh???t");
					Transport.send(msg);
				}
				else if(order.getStatus()==1){
					msg.setText("Xin ch??o "+fullName+"\n"+"????n h??ng c???a b???n ??ang ???????c x??? l??, vui l??ng ch???");
					Transport.send(msg);
				}
				else if(order.getStatus()==2){
					msg.setText("Xin ch??o "+fullName+"\n"+"????n h??ng c???a b???n ??ang ???????c giao, vui l??ng ki???m tra l???i th??ng tin");
					Transport.send(msg);
				}
				else if(order.getStatus()==3){
					msg.setText("Xin ch??o "+fullName+"\n"+"????n h??ng ???? ho??n t???t giao d???ch, c???m ??n b???n ???? s??? d???ng d???ch v??? c???a ch??ng t??i");
					Transport.send(msg);
				}
			}
		} catch (Exception e) {
			throw new StaleStateException("Email n??y kh??ng t??m th???y trong database");
		}
	}
}
