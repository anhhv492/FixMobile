package com.fix.mobile.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserName {

    public UserName() {
    }

    public static String getUserName(){
        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String[] objects = principal.split(",",0);
        String username = objects[0].replace("org.springframework.security.core.userdetails.User","")
                .replace("[","").replace("Username=","").trim();
        return username;
    }
}
