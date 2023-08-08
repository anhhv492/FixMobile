package com.fix.mobile.config;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fix.mobile.jwt.JwtEntrypoint;
import com.fix.mobile.jwt.JwtFilter;
import com.fix.mobile.jwt.JwtUtil;

import com.fix.mobile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.Role;
import com.fix.mobile.service.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration//File cấu hình
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//Cho phép sử dụng Annotation trong @Controller
public class SercurityConfig {
    //HttpSecurity: Phân quyền sử dụng
    //Cau hinh, chi thi phuong thuc login
    @Autowired
    private HttpServletRequest request;

    @Autowired
    JwtEntrypoint jwtEntrypoint;

    @Autowired
    JwtFilter jwtFilter;



    @Autowired
    UserService userService;



	@Bean
	public BCryptPasswordEncoder pe() {//Mã hóa password
		return new BCryptPasswordEncoder();
	}

	PasswordEncoder passwordEncoder() {//Mã hóa password
		return new BCryptPasswordEncoder();
	}

    @Bean
    protected AuthenticationManager authenticationManagerBean(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder())
                .and().build();

    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        //Disable crsf cho đường dẫn /rest/**
        http.csrf().disable();

        http.authorizeHttpRequests().antMatchers("/admin/**",
                        "/rest/guest/**","/api/auth/**").permitAll()//Phân quyền sử dụng
                .antMatchers("/order/**").authenticated()
                .antMatchers("/rest/admin/**","/admin/**","/admin/rest/**").hasRole("ADMIN")
                .antMatchers("/rest/staff/**").hasAnyRole("STAFF", "ADMIN")
                .antMatchers("/rest/user/**").hasAnyRole("USER", "STAFF", "ADMIN")
                .and().exceptionHandling().authenticationEntryPoint(jwtEntrypoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/security/login/success")
                .defaultSuccessUrl("/security/login/success", false)

                .failureUrl("/security/login/error");

        http.rememberMe()
                .tokenValiditySeconds(86400);

        http.exceptionHandling()
                .accessDeniedPage("/security/unauthoried");

        http.logout()
                .logoutUrl("/security/logoff")// địa chỉ hệ thống xử lý
                .logoutSuccessUrl("/security/logoff/success");


        return http.build();
    }

}
