package com.dnweb.springmvcshoeshop.model;

import java.util.Date;

public class AccountInfo {

	private String username;// Ko phai la userName
	private String password;
	private String role;
	private boolean active;
	private String name;
	private String email;
	private String phone;
	private String address;
	private String gender;
	private Date created;
	
	private boolean valid;
	
	public AccountInfo() {
		
	}


	public AccountInfo(String username, String password, String role, boolean active, String name, String email,
			String phone, String address, String gender, Date created) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.active = active;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.created = created;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
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


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public boolean isValid() {
		return valid;
	}


	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	
}
