package com.fprojekti.java.webshop.manager;

import java.util.ArrayList;
import java.util.List;

import com.fprojekti.java.webshop.model.Category;
import com.fprojekti.java.webshop.model.Product;
import com.fprojekti.java.webshop.repository.Repository;

public class ProductManager {
	public static List<Category> getAllCategories() {
		return Repository.getAllCategories();
	}

	public static Category getCategoryById(long id) {
		for (Category cat : Repository.getAllCategories()) {
			if (cat.getId() == id) {
				return cat;
			}
		}
		// TODO: mozda baciti exception
		return null;
	}

	public static List<Product> getAllProducts() {
		return Repository.getAllProducts();
	}

	public static List<Product> getProductsByCategory(Long category) {
		List<Product> resProducts = new ArrayList<Product>();
		if (category == null || category == 0) {
			return Repository.getAllProducts();
		}
		for (Product prod : Repository.getAllProducts()) {
			if (prod.getCategoryId() == category) {
				resProducts.add(prod);
			}
		}
		return resProducts;
	}

	public static Product getProductById(Long prod_id) {

		for (Product prod : getAllProducts()) {
			if (prod.getId() == prod_id) {
				return prod;
			}
		}
		return null;
	}
}
