package com.fix.mobile.rest.controller;

import com.fix.mobile.dto.LoginDTO;
import com.fix.mobile.dto.ResponseTokenDTO;
import com.fix.mobile.dto.SingUpDTO;
import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Role;
import com.fix.mobile.jwt.JwtUtil;
import com.fix.mobile.service.AccountService;
import com.fix.mobile.service.RoleService;
import com.fix.mobile.service.UserService;
import com.fix.mobile.utils.UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountService accountService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(loginDto.getUsername());

        System.out.println(userDetails.getAuthorities());


        final String token = jwtUtil.GenerateToken(userDetails);

        return ResponseEntity.ok(new ResponseTokenDTO(token, userDetails.getUsername()));
    }
}
