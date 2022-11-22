package com.fix.mobile.rest.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.fix.mobile.dto.Account.AccountRequestDTO;
import com.fix.mobile.dto.Account.AccountResponDTO;
import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.utils.UserName;
import org.modelmapper.ModelMapper;
import com.fix.mobile.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fix.mobile.entity.Account;
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

    @GetMapping("/getAll")
    public List<AccountResponDTO> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/page")
    public Page<AccountResponDTO> page(
            @RequestParam(name = "page" , defaultValue = "1") int page,
            @RequestParam(name = "size" , defaultValue = "10") int size,
            @RequestParam(name = "status", defaultValue = "1") Integer status
    ){
        Pageable pageable = PageRequest.of(page - 1 , size);
        return accountService.findAll(status,pageable);
    }
  
    @GetMapping("/roles")
    public List<Role> findRoles(){
        return roleService.findAll();
    }
    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public AccountResponDTO create(@ModelAttribute AccountRequestDTO accountRequestDTO) {

    	return accountService.save(accountRequestDTO);
    }
    
    @PostMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public AccountResponDTO update(@RequestParam("username") String username, @ModelAttribute AccountRequestDTO accountRequestDTO){
        return accountService.update(accountRequestDTO, username);
    }
    
    @DeleteMapping("/{username}")
    public void delete(@PathVariable("username") String username){
        accountService.deleteById(username);
    }

    
    @GetMapping("/getAccountActive")
    public AccountDTO getAccountActive() {
        Account account = accountService.findByUsername(UserName.getUserName());
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        return accountDTO;
    }

    @RequestMapping("/getdatasale/{page}")
    public Page<Account> getDataShowSale(
            @PathVariable ("page") Integer page,
            @RequestParam ("share") String share
    ){
        if(null == share||"undefined".equals(share)){
            share="";
        }
        return accountService.getByPage(page,5,share);
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

    @PostMapping(value = "/updateImage", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public AccountResponDTO updateImage(@ModelAttribute AccountRequestDTO accountRequestDTO){
        return accountService.updateImage(accountRequestDTO);
    }
}
