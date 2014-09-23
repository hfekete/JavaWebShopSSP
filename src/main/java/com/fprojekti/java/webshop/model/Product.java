package com.fprojekti.java.webshop.model;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fprojekti.java.webshop.manager.ProductManager;

public class Product {
	private long id;
	private String name;
	private double price;
	private long categoryId;

	public Product(long id, String name, double price, long categoryId) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.categoryId = categoryId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Product) {
			return ((Product) obj).getId() == this.getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(id).toHashCode();
	}

	@Override
	public String toString() {
		return String.format("Product[id=%d, category_id=%d, name='%s', price='%.2f']", id, categoryId, name, price);
	}

	//

	public Category getCategory() {
		return ProductManager.getCategoryById(categoryId);
	}

	//
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

}