package com.fprojekti.java.webshop.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fprojekti.java.webshop.manager.OrderManager;
import com.fprojekti.java.webshop.model.User;

@Controller
public class UserController {
	@RequestMapping({"/user", "/user/orderlist"})
	public String userPage(HttpServletRequest request, Model model) {
		model.addAttribute("orders", OrderManager.getAllOrderForUser(new User(request.getRemoteUser())));
		return ("user/orderlist");
	}


}
