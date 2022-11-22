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
public class SercurityConfig{
	//HttpSecurity: Phân quyền sử dụng
	//Cau hinh, chi thi phuong thuc login
	@Autowired
	private HttpServletRequest request;

	@Autowired
	JwtEntrypoint jwtEntrypoint;

	@Autowired
	JwtFilter jwtFilter;
	

	
//	@Bean 
//	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter()throws Exception{
//		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
//		jwtAuthenticationTokenFilter;
//	}
//	@Bean
//	public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
//	return new RestAuthenticationEntryPoint();
//	}
//	@Bean
//	public CustomAccessDeniedHandler customAccessDeniedHandler() {
//		return new CustomAccessDeniedHandler();
//	}
//	@Bean
//	@Override
//	protected AuthenticationManager authenticationManager()throws Exception{
//		return super.authenticationManager();
//	}
	@Autowired
	UserService userService;


//	@Bean
//	public BCryptPasswordEncoder pe() {//Mã hóa password
//		return new BCryptPasswordEncoder();
//	}
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////	Viet phuong thuc userDetailService cos du lieu truyen vao: username
////		HttpSession session = request.getSession();
//		auth.userDetailsService((username) ->{
//			try {
//				Account user = accountService.findByName(username);
//				if (user == null) {
////					session.setAttribute("error", "Sai email hoặc mật khẩu");
//					System.out.println("Sai thông tin login");
//				}else
//					System.out.println("Password chưa mã hóa: "+ user.getPassword());
////					System.out.println("Mã hóa pass word: " + pe().encode(user.getPassword()));
//					String password = user.getPassword();
//					String roles =  user.getRole().getName();
//					return User.withUsername(username).password(password).roles(roles).build();
//
//
//			} catch (NoSuchElementException e) {
//				throw new UsernameNotFoundException(username + "not found!");
//			}
//		});
//
//=======
	PasswordEncoder passwordEncoder() {//Mã hóa password
		return new BCryptPasswordEncoder();
	}
	@Bean
	protected AuthenticationManager authenticationManagerBean(HttpSecurity httpSecurity) throws Exception {
	//Viet phuong thuc userDetailService cos du lieu truyen vao: username
//		auth.userDetailsService(username ->{
//			try {
//				Account user = accountService.findByName(username);
//				String password = pe().encode(user.getPassword());
//				String roles =  user.getRole().getName();
//				return User.withUsername(username).password(password).roles(roles).build();
//			} catch (NoSuchElementException e) {
//				throw new UsernameNotFoundException(username + "not found!");
//			}
//		});
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userService)
				.passwordEncoder(passwordEncoder())
				.and().build();

	}
	
    @Bean
	protected SecurityFilterChain configure(HttpSecurity http)throws Exception {
		//Disable crsf cho đường dẫn /rest/**
    	http.csrf().disable();
    	
		http.authorizeHttpRequests().antMatchers("/admin/*",
//						"/rest/admin/accounts/getAccountActive",
//						"/rest/admin/accounts/setaddressdefault",
//						"/rest/admin/accounts/getAddress",
//						"/rest/admin/accounts/updateAccountActive",
						"/rest/admin/registers").permitAll()//Phân quyền sử dụng
//		.antMatchers("/order/**").authenticated()
//		.antMatchers("/rest/admin/**","/rest/staff/**").hasAnyRole("STAFF","ADMIN")
//		.antMatchers("/rest/authorities").hasRole("DIRE")
		 .and().exceptionHandling().authenticationEntryPoint(jwtEntrypoint)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


		http.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/security/login/success")
		.defaultSuccessUrl("/security/login/success",false)
		
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
  //cơ chế mã hóa mật khẩu
//    public BCryptPasswordEncoder getPasswordEnconder() {
//    	return new BCryptPasswordEncoder();
//    }
  //cho phép truy xuất REST API từ bên ngoài (domain khác)
//    @Override
//    	public void configure(WebSecurity web) throws Exception {
//    		// TODO Auto-generated method stub
//    		web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
//    	}
}
