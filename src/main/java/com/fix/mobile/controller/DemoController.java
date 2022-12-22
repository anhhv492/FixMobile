package com.fix.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/view")
public class DemoController {
	
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String demo() {
		return "view/Shop/index";
	}
	
	
	@RequestMapping(value = "/cart",method = RequestMethod.GET)
	public String demo1() {
		return "view/Cart/cart";
	}

	@RequestMapping(value = "/products",method = RequestMethod.GET)
	public String demo2() {
		return "view/Product/product";
	}

	
}
