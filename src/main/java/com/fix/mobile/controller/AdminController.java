package com.fix.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/admin")
@CrossOrigin("*")
public class AdminController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home() {
		return "redirect:/admin/index.html";
	}



}
