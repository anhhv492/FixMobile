package com.fix.mobile;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FixMobileApplication {
	public static void main(String[] args) {
		SpringApplication.run(FixMobileApplication.class, args);
		Logger LOGGER = Logger.getLogger(FixMobileApplication.class);
		LOGGER.info("Start FixMobileApplication");
	}

}
