package com.fix.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @RequestMapping(value = {"","/home/index"}, method = RequestMethod.GET)
    public String index() {
        return "redirect:/views/index.html#!/home/index";
    }

}
