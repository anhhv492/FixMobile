package com.fix.mobile.service;


public interface sendMailService{
	void SendEmail(String user, String pass,Integer idchange);


	void SendEmailStatus(String user, String pass,Integer status ,Integer idOrder);
}
