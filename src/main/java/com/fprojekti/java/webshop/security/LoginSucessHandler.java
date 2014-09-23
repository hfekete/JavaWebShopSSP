package com.fprojekti.java.webshop.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.fprojekti.java.webshop.repository.Repository;

@Service
public class LoginSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		try {
			String username = authentication.getName();
			String ip_addr = request.getRemoteAddr();
			System.out.println("USER LOGGED IN " + username + " from: " + ip_addr);
			
			Repository.userLoggedIn(username, ip_addr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//super.setUseReferer(true);
		super.onAuthenticationSuccess(request, response, authentication);
	}
}