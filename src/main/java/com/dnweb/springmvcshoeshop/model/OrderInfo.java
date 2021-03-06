package com.dnweb.springmvcshoeshop.model;

import java.util.Date;
import java.util.List;

public class OrderInfo {
	
	private String id;
	private double amount;
	private Date created;
	
	private int orderNum;
	
	private String customerName;
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;
	
	private List<OrderDetailInfo> details;

	public OrderInfo() {
		
	}
	
	
	//Truy van trong hibernate
	public OrderInfo(String id, Date created, int orderNum, double amount, String customerName,
			String customerAddress, String customerEmail, String customerPhone) {

		this.id = id;
		this.amount = amount;
		this.created = created;
		this.orderNum = orderNum;
		
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getCustomerAddress() {
		return customerAddress;
	}



	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}



	public String getCustomerEmail() {
		return customerEmail;
	}



	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}



	public String getCustomerPhone() {
		return customerPhone;
	}



	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}



	public List<OrderDetailInfo> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetailInfo> details) {
		this.details = details;
	}
	
}