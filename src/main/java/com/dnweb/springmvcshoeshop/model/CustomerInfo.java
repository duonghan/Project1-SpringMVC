package com.dnweb.springmvcshoeshop.model;

import com.dnweb.springmvcshoeshop.entities.Account;

public class CustomerInfo {

	private String username;
	private String name;
	private String email;
	private String phone;
	private String address;
	private String gender;

	private boolean valid;

	
	public CustomerInfo() {

	}

	public CustomerInfo(Account account) {
		this.username = account.getUsername();
		this.name = account.getName();
		this.email = account.getEmail();
		this.phone = account.getPhone();
		this.address = account.getAddress();
		this.gender = account.getGender();
	}

	public CustomerInfo(String username, String name, String email, String phone, String address, String gender) {
		super();
		this.username = username;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
	}
	
	public CustomerInfo(AccountInfo accountInfo) {
		this.username = accountInfo.getUsername();
		this.name = accountInfo.getName();
		this.email = accountInfo.getEmail();
		this.phone = accountInfo.getPhone();
		this.address = accountInfo.getAddress();
		this.gender = accountInfo.getGender();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
