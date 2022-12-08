package com.fix.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/forgetPassword")
public class ForgetPasswordController {
    @GetMapping
    public String ForgetPasswordForm(Model model) {

        return "redirect:/views/index.html#!/forgetPassword";
    }
}
