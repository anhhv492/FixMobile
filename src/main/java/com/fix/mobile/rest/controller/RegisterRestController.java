package com.fix.mobile.rest.controller;

import java.util.List;

import javax.servlet.ServletContext;

import com.fix.mobile.dto.account.AccountResponDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Role;
import com.fix.mobile.help.HashUtil;
import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.service.RoleService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/rest/guest/registers")
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
    public List<AccountResponDTO> findAll() {
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
