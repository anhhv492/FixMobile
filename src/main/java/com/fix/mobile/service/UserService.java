package com.fix.mobile.service;

import com.fix.mobile.entity.Account;
import com.fix.mobile.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByName(username);
        if (account != null && account.getUsername().equals(username)){
            return User.withUsername(username).password(account.getPassword()).roles(account.getRole().getName()).build();
        }
        throw new UsernameNotFoundException("User not found with the name " + username);
    }
}
