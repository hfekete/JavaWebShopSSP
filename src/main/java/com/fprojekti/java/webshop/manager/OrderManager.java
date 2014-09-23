package com.fprojekti.java.webshop.manager;

import java.util.ArrayList;
import java.util.List;

import com.fprojekti.java.webshop.model.Cart;
import com.fprojekti.java.webshop.model.Order;
import com.fprojekti.java.webshop.model.User;
import com.fprojekti.java.webshop.repository.Repository;

public class OrderManager {

	public static List<Order> getAllOrders() {
		return Repository.getAllOrders();
	}

	public static List<Order> getAllOrderForUser(User u) {
		List<Order> res = new ArrayList<Order>();
		for (Order o : Repository.getAllOrders()) {
			try {
				if (o.getOwner().equals(u)) {
					res.add(o);
				}
			} catch (Exception e) {

			}
		}
		return res;
	}

	public static void confirmOrder(Cart cart) {
		Order o = new Order(cart.getOwner());
		o.setProducts(cart.getProducts());
		Repository.createOrder(o);
	}

	public static Cart createOrder(User owner) {
		return new Cart(owner);
	}

}
