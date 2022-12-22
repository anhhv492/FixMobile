package com.fix.mobile.controller;

import com.fix.mobile.entity.Account;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.utils.UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class LoginController {
	@Autowired
	private AccountService accountService;
	@RequestMapping("/login")
	public String loginForm(Model model) {

		return "redirect:/views/index.html#!/login";
	}

	@GetMapping("/rest/account")
	@ResponseBody
	public Account account() {
		Account account = accountService.findByUsername(UserName.getUserName());
		return account;
	}
	@RequestMapping("/security/login/success")
	public String loginSuccess(Model model) {
		model.addAttribute("message", "Đăng nhập thành công!");
		return "redirect:/views/index.html#!/home/index";
	}
	@RequestMapping("/security/login/error")
	public String loginError(Model model) {

		model.addAttribute("error", "Tài khoản không hoạt động hoặc sai thông tin");
		System.out.println("Tài khoản không hoạt động hoặc sai thông tin");

	return "redirect:/views/index.html#!/login";

	}
	
	@RequestMapping("/security/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất");

		model.addAttribute("message","Sai thông tin đăng nhập");
		return "redirect:/views/index.html#!/login";

	}
	@RequestMapping("/security/logoff/success")
	public String logoffSucess(Model model) {
		model.addAttribute("message", "Bạn đã đăng xuất!");

		return "redirect:/views/index.html#!/login";

	}
	@RequestMapping("/security/login")
	public String login() {
		System.out.println("Bạn đang thực hiện login");
	return null;
	}

}
