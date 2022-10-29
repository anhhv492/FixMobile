package com.fix.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	@RequestMapping("/login")
	public String loginForm(Model model) {
		return "views/login/form";
	}
	@RequestMapping("/security/login/success")
	public String loginSucess(Model model) {
		model.addAttribute("message", "Đăng nhập thành công!");
		return "redirect:/views/index.html#!/home/index";
	}
	@RequestMapping("/security/login/error")
	public String loginError(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập");
		return "views/login/form";
	}
	
	@RequestMapping("/security/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất");
		return "views/login/form";
	}
	@RequestMapping("/security/logoff/success")
	public String logoffSucess(Model model) {
		model.addAttribute("message", "Bạn đã đăng xuất!");
		return "views/login/form";
	}
	@RequestMapping("/security/login")
	public String login() {
		System.out.println("Bạn đang thực hiện login");
	return null;
	}

}
