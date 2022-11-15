package com.fix.mobile.controller;

import com.fix.mobile.service.RamService;
import com.fix.mobile.entity.Ram;
import com.fix.mobile.service.RamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/product")
@CrossOrigin("*")
public class ConfigrutationController {
	@Autowired
	private RamService ramService;

	// tim ram de len table

	public  String findall(Model model){
		List<Ram>  ramList = ramService.findAll();
		model.addAttribute("ramList",ramList);
		return "json";
	}






}
