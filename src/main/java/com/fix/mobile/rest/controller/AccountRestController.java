package com.fix.mobile.rest.controller;

import java.util.List;

import javax.servlet.ServletContext;

import com.fix.mobile.dto.account.AccountRequestDTO;
import com.fix.mobile.dto.account.AccountResponDTO;
import com.fix.mobile.dto.account.UpdatePasswordDTO;
import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.service.sendMailService;
import com.fix.mobile.utils.UserName;
import org.apache.naming.factory.SendMailFactory;
import org.hibernate.StaleStateException;
import org.modelmapper.ModelMapper;
import com.fix.mobile.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fix.mobile.entity.Account;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.service.RoleService;

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

    @Autowired
    sendMailService mailService;
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
            @RequestParam(name = "status", defaultValue = "1") Integer status,
            @RequestParam(name = "role", defaultValue = "0") Integer role,
            @RequestParam(name = "search", required = false) String search
    ){
        Pageable pageable = PageRequest.of(page - 1 , size);
        return accountService.findAll(search, role ,status ,pageable);
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

    @PostMapping("/updatePassword")
    public Boolean updatePassword (@RequestBody UpdatePasswordDTO updatePasswordDTO){

         return accountService.updatePassword(updatePasswordDTO);
    }



}
