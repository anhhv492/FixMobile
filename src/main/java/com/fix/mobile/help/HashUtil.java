package com.fix.mobile.help;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {
 public static String hash(String plain) {
	 String salt =BCrypt.gensalt();
	 return BCrypt.hashpw(plain, salt);
	 //hashpw(plain, salt); trộn hai chuỗi dữ liệu
 }
 //plain: chuoi ng dung nhap
 // hashed:  chuoi ky tu ma hoa luu trong db
 public static boolean verify(String plain, String hashed) {
	return BCrypt.checkpw(plain, hashed);
	//kiem tra hai chuỗi này có khớp với nhau k
}
}
