package com.fix.mobile.rest.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.utils.UserName;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping(value = "/rest/admin/accounts")
public class AccountRestController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;

    @Autowired
    ServletContext application;

    @Autowired
    ModelMapper modelMapper;

    public AccountRestController(AccountService accountService, RoleService roleService, ServletContext application, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.application = application;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }
    @GetMapping("/page/{page}")
	public List<Account> findAllPageable(@PathVariable("page") Optional<Integer> page){
		Pageable pageable = PageRequest.of(page.get(), 10);
		List<Account> accounts = accountService.findAll(pageable).getContent();
		return accounts;
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
    
    @PutMapping("/update")
    public AccountDTO update(@RequestParam("username") String username, @RequestBody AccountDTO accountDTO){
        return accountService.update(accountDTO, username);
    }
    
    @DeleteMapping("/{username}")
    public void delete(@PathVariable("username") String username){
        accountService.deleteById(username);
    }
    
    @GetMapping("/getAccountActive")
    public AccountDTO getAccountActive(){
        Account account = accountService.findByUsername(UserName.getUserName());
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        return accountDTO;
    }

    @PostMapping("/setaddressdefault")
    public void setaddressdefault(@RequestBody Integer id){
        accountService.setAddressDefault(id);
    }

    @GetMapping("/getAddress")
    public AddressDTO getAddress(){
        return accountService.getAddress();
    }

    @PostMapping("/updateAccountActive")
    public AccountDTO updateAccountActive(@RequestBody AccountDTO accountDTO){
        return accountService.updateAccountActive(accountDTO);
    }
}
