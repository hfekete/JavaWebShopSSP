package com.fprojekti.java.webshop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		// registry.addViewController("/home").setViewName("list");
		// registry.addViewController("/").setViewName("list");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/user").setViewName("user");

	}

}