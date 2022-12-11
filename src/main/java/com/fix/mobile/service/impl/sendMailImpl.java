//package com.fix.mobile.service.impl;
//
//import com.fix.mobile.service.ProductChangeService;
//import com.fix.mobile.service.sendMailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//public class sendMailImpl implements sendMailService {
//
//	@Autowired
//	private ProductChangeService productChangeService;
//
//	private static final String EMAIL_WELCOME_SUBJECT = "Welcome to Online Entertainment";
//	private static final String EMAIL_FORGOT_PASSWORD = "Online Entertainment - New password";
//
//	@Override
//	public void SendEmail(String host, String port, String user, String pass, Integer idOrder) {
//		host = "smtp.gmail.com";
//		port = "587";
////		user = "cuongndph14605@fpt.edu.vn";
////		pass = "Duycuong1";
//		user= "top1zukavietnam@gmail.com";
//		pass = "namdz123";
//		try {
//			String content = "Thẻ Bảo Lưu";
//			String subject = "Kính gửi quý khách";
//			List<ProductChange>
//			Boolean  comfirm = true;
//			switch (comfirm) {
//				case "sendCard":
//					List<CardSend> list=new ArrayList<>();
//					List<BillCard> listbill=
//							billcardService.finbyidorder(idOrder);
//					for(int i=0;i<listbill.size();i++){
//						CardSend cs= new CardSend();
//						cs.setSTT(i+1);
//						cs.setMadonhang(idOrder);
//						cs.setTenthe(listbill.get(i).getCarts().getCategories().getName());
//						cs.setSeri(listbill.get(i).getCards().getSeri());
//						cs.setMathe(listbill.get(i).getCards().getNumber());
//						cs.setNgayhethan(listbill.get(i).getCards().getExpiry());
//						list.add(cs);
//					}
//					subject = EMAIL_WELCOME_SUBJECT;
//					content = "Dear " + cart.getEmailSend() + " , the cua ban la! \n"+ list;
//					break;
//				default:
//					subject = "Online Entertainment";
//					content = "Maybe this email is wrong, don't care about it";
//			}
//			sendEmail(host, port, user, pass, cart.getEmailSend(), subject, content);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
