package com.fprojekti.java.webshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fprojekti.java.webshop.manager.OrderManager;
import com.fprojekti.java.webshop.model.Cart;
import com.fprojekti.java.webshop.model.Product;
import com.fprojekti.java.webshop.model.User;

@Controller
public class CartController {

	@RequestMapping("/cart")
	public String doCart(HttpServletRequest request, HttpSession session, Model model) {
		// request.get
		if (session.getAttribute("cart") != null) {
			model.addAttribute("cart", session.getAttribute("cart"));
			return "cart";
		} else {
			return "list";
		}
	}

	@RequestMapping("/updatecart")
	public String updateCart(HttpServletRequest request, HttpSession session, Model model) {

		if (session.getAttribute("cart") != null) {
			Cart c = (Cart) session.getAttribute("cart");
			for (Product p : c.getProductSet()) {
				try {
					String sx = "book_id" + p.getId();
					c.getProducts().put(p, Long.parseLong(request.getParameter(sx)));
				} catch (Exception e) {
					// DO NOTHING
				}
			}
			session.setAttribute("cart", c);
			model.addAttribute("cart", c);
			return "cart";
		} else {
			return "list";
		}
	}

	@RequestMapping("/checkout")
	public String checkoutCart(HttpServletRequest request, HttpSession session, Model model) {

		if (session.getAttribute("cart") != null) {
			Cart c = (Cart) session.getAttribute("cart");
			c.setOwner(new User(request.getRemoteUser()));
			session.setAttribute("cart", c);
			model.addAttribute("cart", c);
			return "checkout";
		} else {
			return "list";
		}
	}

	@RequestMapping("/buy")
	public String buyCart(HttpServletRequest request, HttpSession session, Model model) {

		Cart c = (Cart) session.getAttribute("cart");
		c.setOwner(new User(request.getRemoteUser()));
		OrderManager.confirmOrder(c);
		model.addAttribute("cart", c);
		session.setAttribute("cart", null);
		return "buy";
	}
}