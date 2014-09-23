package com.fprojekti.java.webshop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {
	private Map<Product, Long> products = new HashMap<Product, Long>();
	private User owner;
	private final String paymentType = "Pouzecem";

	public Cart(Map<Product, Long> products, User owner) {
		super();
		this.products = products;
		this.owner = owner;
	}

	public Cart(User owner) {
		super();
		this.owner = owner;
	}

	public void addProductToCart(Product prod, Long amount) {
		products.put(prod, amount);
	}

	public Set<Product> getProductSet() {
		return products.keySet();
	}

	public Long getAmountOfProd(Product prod) {
		return products.get(prod);
	}

	public Double getTotalPrice() {
		Double d = 0.0;
		for (Product prod : getProductSet()) {
			d += (prod.getPrice() * products.get(prod));
		}
		return d;
	}

	//
	//
	//
	//
	/**
	 * Gets the products where the key is the product, and the value is the long
	 * (amount of the items for product)
	 * 
	 * @return map
	 */
	public Map<Product, Long> getProducts() {
		return products;
	}

	public void setProducts(Map<Product, Long> products) {
		this.products = products;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getPaymentType() {
		return paymentType;
	}

}
