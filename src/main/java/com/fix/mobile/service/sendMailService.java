package com.fix.mobile.service;


import com.fix.mobile.entity.Order;

public interface sendMailService{
	void SendEmail(String user, String pass,Integer idchange);

	void SendEmailStatus(String user, String pass,Integer status ,Integer idOrder);

	void SendEmailChangePass(String user, String pass ,String mail);

	void sendEmailOrder(String User, String pass,String mail,String fullName, Order order);
}
