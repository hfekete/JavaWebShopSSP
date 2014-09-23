package com.fprojekti.java.webshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fprojekti.java.webshop.manager.ProductManager;
import com.fprojekti.java.webshop.model.Category;
import com.fprojekti.java.webshop.model.Product;

@Controller
public class MainController implements ErrorController {

	public static final String ERROR_PAGE = "/error";
	public static final String HOME_PAGE = "list";

	@RequestMapping({ "/list", "/" })
	public String greeting(@RequestParam(value = "category", required = false) Long category,
			HttpServletRequest request, HttpServletResponse response, Model model) {

		updateModel(model, category);
		return HOME_PAGE;
	}

	private void updateModel(Model model, Long category_id) {
		List<Category> categories = ProductManager.getAllCategories();
		List<Product> products = ProductManager.getProductsByCategory(category_id);

		model.addAttribute("categories", categories);
		model.addAttribute("products", products);
	}

	@RequestMapping(value = ERROR_PAGE)
	public String errorHandling(HttpServletRequest request, Model model, HttpServletResponse response) {
		int statusCode = response.getStatus();
		model.addAttribute("errorCode", statusCode);
		return getErrorPath();
	}

	@Override
	public String getErrorPath() {
		return ERROR_PAGE;
	}

}
