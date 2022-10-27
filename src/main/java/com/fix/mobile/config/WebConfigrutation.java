package com.fix.mobile.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Service
public class WebConfigrutation implements WebMvcConfigurer {
	@Bean
	public CommonsMultipartResolver mulpartResolver(){
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		return resolver;
	}
	@Bean
	public Cloudinary cloudinary(){
		Cloudinary cloud = new Cloudinary(
				ObjectUtils.asMap(
						"cloud_name","dcll6yp9s",
						"api_key","916219768485447",
						"api_secret","zUlI7pdWryWsQ66Lrc7yCZW0Xxg",
						"secure",true
				)
		);
		return  cloud;
	}
}
