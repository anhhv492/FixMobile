package com.fix.mobile.rest.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Role;
import com.fix.mobile.help.HashUtil;
import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.repository.CategoryRepository;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.service.RoleService;


import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/rest/admin/registers")
public class RegisterRestController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    ServletContext application;

    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }


    @GetMapping("/roles")
    public List<Role> findRoles(){
        return roleService.findAll();
    }
    @PostMapping
    public Account create(@RequestBody Account account) {
        String hashedPassword = HashUtil.hash(account.getPassword());
        account.setPassword(hashedPassword);
        return accountService.save(account);
    }

//    @PutMapping("/{username}")
//    public Account update(@PathVariable("username") String username, @RequestBody Account account){
//        String hashedPassword = HashUtil.hash(account.getPassword());
//        account.setPassword(hashedPassword);
//        return accountService.update(account, username);
//    }



}
