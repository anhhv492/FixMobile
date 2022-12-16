package com.fix.mobile.rest.controller;

import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.dto.account.AccountRequestDTO;
import com.fix.mobile.dto.account.AccountResponDTO;
import com.fix.mobile.dto.account.UpdatePasswordDTO;
import com.fix.mobile.entity.Account;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.service.RoleService;
import com.fix.mobile.utils.UserName;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;

@RestController
@RequestMapping(value= "/rest/user")
@CrossOrigin("*")
@Component
public class UserController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;

    @Autowired
    ServletContext application;

    @Autowired
    ModelMapper modelMapper;

    public UserController(AccountService accountService, RoleService roleService, ServletContext application, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.application = application;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAccountActive")
    public AccountDTO getAccountActive() {
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

    @PostMapping(value = "/updateImage", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public AccountResponDTO updateImage(@ModelAttribute AccountRequestDTO accountRequestDTO){
        return accountService.updateImage(accountRequestDTO);
    }

    @PostMapping("/updatePassword")
    public Boolean updatePassword (@RequestBody UpdatePasswordDTO updatePasswordDTO){

        return accountService.updatePassword(updatePasswordDTO);
    }
}
