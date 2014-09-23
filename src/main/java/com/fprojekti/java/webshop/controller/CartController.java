package com.fprojekti.java.webshop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fprojekti.java.webshop.manager.OrderManager;
import com.fprojekti.java.webshop.manager.ProductManager;
import com.fprojekti.java.webshop.model.Cart;
import com.fprojekti.java.webshop.model.Product;
import com.fprojekti.java.webshop.model.User;

@Controller
public class CartController {

	@RequestMapping("/cart")
	public String doCart(@RequestParam(value = "remove", required = false) Long remove_prod_id,
			HttpServletRequest request, HttpSession session, Model model) {
		// request.get

		try {
			if (session.getAttribute("cart") != null) {
				if (remove_prod_id != null) {
					Cart c = (Cart) session.getAttribute("cart");
					c.getProducts().remove(new Product(remove_prod_id, null, 0, 0));
					if (c.getProducts().isEmpty()) {
						return MainController.HOME_PAGE;
					}
				}

				model.addAttribute("cart", session.getAttribute("cart"));
				return "cart";
			} else {
				return MainController.HOME_PAGE;
			}
		} catch (Exception e) {
			return MainController.HOME_PAGE;
		}
	}

	@RequestMapping("/updatecart")
	public String updateCart(HttpServletRequest request, HttpSession session, Model model) {

		if (session.getAttribute("cart") != null) {
			Cart c = (Cart) session.getAttribute("cart");
			Map<Product, Long> newProductList = new HashMap<Product, Long>();
			for (Product p : c.getProductSet()) {
				try {
					String book_id_param_name = "book_id" + p.getId();
					Long amount = Long.parseLong(request.getParameter(book_id_param_name));
					if (amount > 0) {
						newProductList.put(p, amount);
					}
				} catch (Exception e) {
					// DO NOTHING
				}
			}
			c.setProducts(newProductList);
			if (newProductList.isEmpty()) {
				return MainController.HOME_PAGE;
			}
			session.setAttribute("cart", c);
			model.addAttribute("cart", c);
			return "cart";
		} else {
			return MainController.HOME_PAGE;
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

	@RequestMapping("/addtocart")
	public String addToCart(@RequestParam(value = "id", required = true) Long prod_id,//
			HttpServletRequest request, Model model) {

		CartController.addToCart(prod_id, request);

		return "forward:" + MainController.HOME_PAGE;
	}

	public static void addToCart(Long prod_id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object o = session.getAttribute("cart");
		if (o == null || !(o instanceof Cart)) {
			Cart c = OrderManager.createOrder(new User(request.getRemoteUser()));
			c.addProductToCart(ProductManager.getProductById(prod_id), 1l);
			session.setAttribute("cart", c);
		} else {
			Cart c = (Cart) o;
			c.addProductToCart(ProductManager.getProductById(prod_id), 1l);
			session.setAttribute("cart", c);
		}
	}

}