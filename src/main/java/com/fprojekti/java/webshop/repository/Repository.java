package com.fprojekti.java.webshop.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.fprojekti.java.webshop.model.Category;
import com.fprojekti.java.webshop.model.Order;
import com.fprojekti.java.webshop.model.Product;
import com.fprojekti.java.webshop.model.User;

public class Repository {
	public static void init() {

	}

	// names of the tables
	private static final String TABLE_CATEGORY = "category";
	private static final String TABLE_PRODUCT = "product";
	private static final String TABLE_ORDER = "consumer_order";
	private static final String TABLE_ORDER_PRODUCT_AMOUNT = "order_product_amount";
	private static final String TABLE_LOGINS = "logins";

	// COMMON
	private static final String TABLE_ROW_ID = "id";

	// product table column names
	private static final String TABLE_PRODUCT_CATEGORY_ID = "category_id";
	private static final String TABLE_PRODUCT_NAME = "name";
	private static final String TABLE_PRODUCT_PRICE = "price";

	// category table column names
	private static final String TABLE_CATEGORY_NAME = "name";

	// oder table column names
	private static final String TABLE_ORDER_PROD_LIST_ID = "products_in_order_id";
	private static final String TABLE_ORDER_DATETIME = "order_date";
	private static final String TABLE_ORDER_NACIN_PLACANJA = "payment_type";
	
	//logins table column names
	private static final String TABLE_LOGINS_DATE = "datum_prijave";
	private static final String TABLE_LOGINS_USERNAME = "username";
	
	/**************************************************************************************************************/
	// / / create and insert commands
	private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT
			+ "(id serial, category_id long, name varchar(255), price decimal(5,2))";
	private static final String INSERT_INTO_PRODUCTS = "INSERT INTO " + TABLE_PRODUCT
			+ "(category_id, name, price) values(?,?, ?)";
	//
	private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY
			+ "(id serial, name varchar(50))";
	private static final String INSERT_INTO_CATEGORY = "INSERT INTO " + TABLE_CATEGORY + "(name) values(?)";

	private static final String CREATE_TABLE_ORDER = "CREATE TABLE " + TABLE_ORDER
			+ "(id serial, userOwner varchar(50),order_date datetime, payment_type int)";
	private static final String INSERT_INTO_ORDER = "INSERT INTO " + TABLE_ORDER
			+ "( userOwner, order_date, payment_type) values(?,?,?)";

	private static final String CREATE_TABLE_ORDER_PRODUCT_AMOUNT = "CREATE TABLE " + TABLE_ORDER_PRODUCT_AMOUNT
			+ "(id serial, product_id long, order_id long, prod_amount long)";
	private static final String INSERT_INTO_ORDER_PRODUCT_AMOUNT = "INSERT INTO " + TABLE_ORDER_PRODUCT_AMOUNT
			+ "(product_id, order_id, prod_amount) values(?,?,?)";

	static {
		// simple DS for test (not for production!)
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUsername("sa");
		dataSource.setUrl("jdbc:h2:mem");
		dataSource.setPassword("");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		System.out.println("Creating tables");
		// products table
		jdbcTemplate.execute("drop table " + TABLE_PRODUCT + " if exists");
		jdbcTemplate.execute(CREATE_TABLE_PRODUCT);
		// category table
		jdbcTemplate.execute("drop table " + TABLE_CATEGORY + " if exists");
		jdbcTemplate.execute(CREATE_TABLE_CATEGORY);
		// order table
		jdbcTemplate.execute("drop table " + TABLE_ORDER + " if exists");
		jdbcTemplate.execute(CREATE_TABLE_ORDER);
		// order product amount table
		jdbcTemplate.execute("drop table " + TABLE_ORDER_PRODUCT_AMOUNT + " if exists");
		jdbcTemplate.execute(CREATE_TABLE_ORDER_PRODUCT_AMOUNT);

		// insert basic data
		System.out.printf("Inserting product records ");
		jdbcTemplate.update(INSERT_INTO_CATEGORY, "scifi");
		jdbcTemplate.update(INSERT_INTO_CATEGORY, "fantasy");

		jdbcTemplate.update(INSERT_INTO_PRODUCTS, 1, "Hyperion", 8.2);
		jdbcTemplate.update(INSERT_INTO_PRODUCTS, 1, "Fall of hyperion", 5.2);
		jdbcTemplate.update(INSERT_INTO_PRODUCTS, 1, "Endimion", 6.2);
		jdbcTemplate.update(INSERT_INTO_PRODUCTS, 1, "Rise of Endimion", 9.2);

		jdbcTemplate.update(INSERT_INTO_PRODUCTS, 2, "Legende o drizztu 1", 4.2);
		jdbcTemplate.update(INSERT_INTO_PRODUCTS, 2, "Legende o drizztu 2", 5.2);
		jdbcTemplate.update(INSERT_INTO_PRODUCTS, 2, "Legende o drizztu 3", 6.2);
		jdbcTemplate.update(INSERT_INTO_PRODUCTS, 2, "egende o drizztu 4", 12.2);

		jdbcForWebShop = jdbcTemplate;
		System.out.println("Repository ... Init Done !");
	}

	private static final JdbcTemplate jdbcForWebShop;

	/** test method */
	public static void displayAllProductsInConsole() {
		List<Product> results = getAllProducts();
		for (Product product : results) {
			System.out.println(product);
		}
	}

	public static List<Product> getAllProducts() {
		return _products;
	}

	private static List<Product> _products = _getAllProducts();

	private static List<Product> _getAllProducts() {
		List<Product> results = jdbcForWebShop.query("select * from " + TABLE_PRODUCT, new RowMapper<Product>() {
			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Product(rs.getLong(TABLE_ROW_ID), rs.getString(TABLE_PRODUCT_NAME), rs
						.getDouble(TABLE_PRODUCT_PRICE), rs.getLong(TABLE_PRODUCT_CATEGORY_ID));
			}
		});
		return results;
	}

	public static List<Category> getAllCategories() {
		return _categories;
	}

	private static List<Category> _categories = _getAllCategories();

	private static List<Category> _getAllCategories() {
		List<Category> results = jdbcForWebShop.query("select * from " + TABLE_CATEGORY, new RowMapper<Category>() {
			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Category(rs.getLong(TABLE_ROW_ID), rs.getString(TABLE_CATEGORY_NAME));
			}
		});
		return results;
	}

	public static synchronized void createOrder(Order o) {
		// kreiraj order u order tablici
		jdbcForWebShop.update(INSERT_INTO_ORDER, o.getOwner().getUsername(), Calendar.getInstance().getTime(), 1);
		Long order_id = jdbcForWebShop.queryForLong("SELECT TOP 1 id FROM " + TABLE_ORDER + " ORDER BY ID DESC;");
		for (Product p : o.getProductsInOrder()) {
			long amount = o.getProducts().get(p);
			long prod_id = p.getId();
			jdbcForWebShop.update(INSERT_INTO_ORDER_PRODUCT_AMOUNT, prod_id, order_id, amount);
		}
		_orders = _getAllOrders();
	}

	public static Order getOrder(Long id) {
		String select = "select * from " + TABLE_ORDER + " where id=?";
		Order result = jdbcForWebShop.query(select, new Object[] { id }, new ResultSetExtractor<Order>() {
			@Override
			// userOwner, order_date, payment_type
			public Order extractData(ResultSet rs) throws SQLException, DataAccessException {
				Order ox = new Order(new User(rs.getString("userOwner")));
				Long lId = rs.getLong(TABLE_ROW_ID);
				ox.setId(lId);
				ox.setDate(rs.getDate("order_date"));
				ox.setProducts(getProductAmountData(lId, _products));
				return ox;
			}

		});
		return result;
	}

	private static class OrderProductAmount {
		long product_id;
		long order_id;
		long amount;

		public OrderProductAmount(long product_id, long order_id, long amount) {
			super();
			this.product_id = product_id;
			this.order_id = order_id;
			this.amount = amount;
		}
	}

	private static Map<Product, Long> getProductAmountData(long order_id, List<Product> products) {
		List<OrderProductAmount> result = jdbcForWebShop.query("select * from " + TABLE_ORDER_PRODUCT_AMOUNT
				+ " where order_id=? ;"//
		, new Object[] { order_id }//
				, new RowMapper<Repository.OrderProductAmount>() {

					@Override
					public OrderProductAmount mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new OrderProductAmount(rs.getLong("product_id"), rs.getLong("order_id"), rs
								.getLong("prod_amount"));
					}

				});

		Map<Product, Long> returnValue = new HashMap<Product, Long>();
		for (OrderProductAmount opa : result) {
			for (Product p : products) {
				if (p.getId() == opa.product_id) {
					returnValue.put(p, opa.amount);
					break;
				}
			}
		}
		return returnValue;
	}

	private static List<Order> _orders = _getAllOrders();

	public static List<Order> getAllOrders() {
		return _orders;
	}

	private static List<Order> _getAllOrders() {

		List<Product> products = getAllProducts();

		List<Order> results = jdbcForWebShop.query("select * from " + TABLE_ORDER, new RowMapper<Order>() {
			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
				Order o = new Order(rs.getLong(TABLE_ROW_ID), rs.getDate(TABLE_ORDER_DATETIME));
				o.setProducts(getProductAmountData(rs.getLong(TABLE_ROW_ID), products));
				o.setOwner(new User(rs.getString("userOwner")));
				return o;
			}
		});
		return results;
	}
}