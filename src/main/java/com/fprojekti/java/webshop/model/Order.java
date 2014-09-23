package com.fprojekti.java.webshop.model;

import java.sql.Date;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Order {

	private Map<Product, Long> products = new HashMap<Product, Long>();
	private long id;
	private Date date;
	private final String paymentType = "Pouzecem";
	private User owner;

	public Order(long id, Date date) {
		super();
		this.id = id;
		this.date = date;
	}

	public Order(User owner) {
		super();
		this.owner = owner;
	}

	public Set<Product> getProductsInOrder() {
		return products.keySet();
	}

	public String getDateAsString() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
		return df.format(getDate());
	}

	// /
	//
	//
	//
	//
	//
	//
	//
	//
	//

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPaymentType() {
		return paymentType;
	}

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

}
