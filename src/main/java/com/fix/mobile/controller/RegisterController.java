package com.fix.mobile.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Role;
import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.repository.RoleRepository;
@Controller
@RequestMapping("/register")
public class RegisterController {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private RoleRepository roleRepository;
	
@GetMapping
public String loginForm(Model model) {
	model.addAttribute("createDate", new Date());
	model.addAttribute("roles",roleRepository.findAll());
		return "redirect:/views/index.html#!/register";
}
@PostMapping(value = "/save")
public String postCreate(  @ModelAttribute(name = "account") Account accounts,
		BindingResult result,Model model) {
	//VALIDATION CONTROLLER
//	 @Valid hoặc @Validated: yêu cầu kiểm lỗi bean staff
//	 BindingResult chứa kết quả kiểm lỗi (khai báo ngay sau bean cần kiểm lỗi)
//	 result.hasErrors() có lỗi hay không
	
	System.out.println("Username"+ accountRepository.findByName(accounts.getUsername()));
	Account acc= accountRepository.findByName(accounts.getUsername());
	System.out.println("Role" + accountRepository.findByName(accounts.getUsername()).getRole());
	if (acc == null) {
		String hashedPassword = com.fix.mobile.help.HashUtil.hash(accounts.getPassword());
		accounts.setPassword(hashedPassword);
		accounts.setCreateDate( new Date());
		this.accountRepository.save(accounts);
		return "redirect:/views/index.html#!/home/index";
	}
	else {
		return "views/register/form";	
	}

}
}
