package com.dnweb.springmvcshoeshop.model;

import java.util.Iterator;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dnweb.springmvcshoeshop.entities.OrderDetail;
import com.dnweb.springmvcshoeshop.entities.Product;

public class ProductInfo {

	private String id;
	private String name;
	private double price;
	private String description;
	private Float discount;
	private String categoryId;
	private int  totalQuantity; 
	
	private boolean newProduct = false;

	// Upload file.
	private CommonsMultipartFile fileData;

	
	public ProductInfo() {
		
	}

	public ProductInfo(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.description = product.getDescription();
		this.discount = product.getDiscount();
		this.categoryId = product.getCategory().getId();
	}

	//Dung de truy van trong Hibernate
	public ProductInfo(String id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public ProductInfo(String id, String name, double price, String description, Float discount, String categoryId) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.discount = discount;
		this.categoryId = categoryId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	
	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public boolean isNewProduct() {
		return newProduct;
	}

	public void setNewProduct(boolean newProduct) {
		this.newProduct = newProduct;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Product product) {
		
		int quantity = 0;
		Iterator<OrderDetail> iterator = product.getOrderdetails().iterator();
		
		while (iterator.hasNext()) {	
			quantity += iterator.next().getQuantity();
		}
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>Total quantity:" + quantity);
		this.totalQuantity = quantity;
	}
	
}
