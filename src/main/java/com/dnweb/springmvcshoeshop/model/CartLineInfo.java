package com.dnweb.springmvcshoeshop.model;

public class CartLineInfo {//Danh sach moi loai san pham trong gio hang

	private ProductInfo productInfo;
	private int quantity;
	private int size;
	
	public CartLineInfo() {
		this.quantity = 0;
		this.size = 35;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	//Tong thanh toan cua gio hang
	public double getAmount() {
		return this.productInfo.getCost() * this.quantity;
	}

}
