package com.fprojekti.java.webshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fprojekti.java.webshop.manager.OrderManager;

@Controller
public class AdminController {

	@RequestMapping("/admin")
	public String doCart(HttpServletRequest request, HttpSession session, Model model) {
		// request.get
		if (session.getAttribute("cart") != null) {
			model.addAttribute("cart", session.getAttribute("cart"));
			return "cart";
		} else {
			return "list";
		}
	}

	@RequestMapping("/admin/logins")
	public String listLogins(HttpServletRequest request, HttpSession session, Model model) {

		// TODO: add login list

		return "admin/loginlist";
	}

	@RequestMapping("/admin/orders")
	public String listAllUsersPurchases(HttpServletRequest request, HttpSession session, Model model) {
		// TODO: add order list
		model.addAttribute("orders", OrderManager.getAllOrders());
		return "admin/orderlist";
	}
}