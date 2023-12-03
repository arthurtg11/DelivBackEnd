package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UrnaApplication {

	private static ApplicationContext appContext;

	public static void main(String[] args) {
		appContext = SpringApplication.run(UrnaApplication.class, args);
	}

	public static ApplicationContext getAppContext() {
		return appContext;
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	};

}
