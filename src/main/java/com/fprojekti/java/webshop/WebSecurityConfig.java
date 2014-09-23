package com.fprojekti.java.webshop;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import com.fprojekti.java.webshop.security.LoginSucessHandler;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// ROLES
	private static final String ROLE_ADMIN = "ADMIN";
	private static final String ROLE_USER = "USER";

	//
	private String[] PERMIT_TO_ALL = { "/", "/list", "/cart", "/addtocart", "/updatecart", "/login", "/error/**",
			"error", "/css/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()//
				.antMatchers(PERMIT_TO_ALL).permitAll()//
				.antMatchers("/admin/**").hasRole(ROLE_ADMIN)//
				.anyRequest().authenticated();

		http.formLogin().loginPage("/login").permitAll().and().logout().permitAll();
		http.formLogin().successHandler(new LoginSucessHandler());
	}

	@Configuration
	protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("user").password("user").roles(ROLE_USER);
			auth.inMemoryAuthentication().withUser("user1").password("user1").roles(ROLE_USER);
			auth.inMemoryAuthentication().withUser("user2").password("user2").roles(ROLE_USER);
			auth.inMemoryAuthentication().withUser("admin").password("admin").roles(ROLE_ADMIN);
		}

	}

}