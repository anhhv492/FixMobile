package com.fix.mobile.controller;

import com.fix.mobile.entity.Account;
import com.fix.mobile.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomeController {
    @RequestMapping(value = {"","/home/index"}, method = RequestMethod.GET)
    public String index() {
        return "redirect:/views/index.html#!/home/index";
    }
}
