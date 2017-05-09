package com.dnweb.springmvcshoeshop.model;

import java.util.ArrayList;
import java.util.List;

public class CartInfo {//Thong tin ve gio hang: nguoi mua, ds cac san pham

	private int orderNum;
	
	private CustomerInfo customerInfo;
	
	private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public List<CartLineInfo> getCartLines() {
		return cartLines;
	}

	//Tim kiem thong tin san pham theo id
	private CartLineInfo findLineById(String id) {
		for (CartLineInfo line : this.cartLines) {
			if (line.getProductInfo().getId().equals(id)) {
				return line;
			}
		}
		return null;
	}

	//CRUD
	public void addProduct(ProductInfo productInfo, int quantity) {
		
		CartLineInfo line = this.findLineById(productInfo.getId());

		if (line == null) {
			line = new CartLineInfo();
			line.setQuantity(0);
			line.setProductInfo(productInfo);
			this.cartLines.add(line);
		}
		int newQuantity = line.getQuantity() + quantity;
		if (newQuantity <= 0) {
			this.cartLines.remove(line);
		} else {
			line.setQuantity(newQuantity);
		}
	}

	public void updateProduct(String code, int quantity) {
		
		CartLineInfo line = this.findLineById(code);

		if (line != null) {
			if (quantity <= 0) {
				this.cartLines.remove(line);
			} else {
				line.setQuantity(quantity);
			}
		}
	}

	public void removeProduct(ProductInfo productInfo) {
		CartLineInfo line = this.findLineById(productInfo.getId());
		if (line != null) {
			this.cartLines.remove(line);
		}
	}

	public boolean isEmpty() {
		return this.cartLines.isEmpty();
	}

	//Kiem tra nguoi dung hop le
	public boolean isValidCustomer() {
		return this.customerInfo != null && this.customerInfo.isValid();
	}

	//Lay tong so luong don hang trong gio hang
	public int getQuantityTotal() {
		int quantity = 0;
		for (CartLineInfo line : this.cartLines) {
			quantity += line.getQuantity();
		}
		return quantity;
	}

	//Tong so tien trong gio hang
	public double getAmountTotal() {
		double total = 0;
		for (CartLineInfo line : this.cartLines) {
			total += line.getAmount();
		}
		return total;
	}

	//Cap nhat lai so luong
	public void updateQuantity(CartInfo cartForm) {
		if (cartForm != null) {
			List<CartLineInfo> lines = cartForm.getCartLines();
			for (CartLineInfo line : lines) {
				this.updateProduct(line.getProductInfo().getId(), line.getQuantity());
			}
		}

	}
}
