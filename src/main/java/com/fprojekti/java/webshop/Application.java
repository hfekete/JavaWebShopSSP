package com.fprojekti.java.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fprojekti.java.webshop.repository.Repository;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		Repository.init();
	}

}
