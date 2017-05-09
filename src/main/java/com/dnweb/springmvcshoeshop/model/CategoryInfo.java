package com.dnweb.springmvcshoeshop.model;

import com.dnweb.springmvcshoeshop.entities.Category;

public class CategoryInfo {

	private String id;
	private String name;

	private boolean newCategory = false;

	public CategoryInfo() {

	}

	public CategoryInfo(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		
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

	public boolean isNewCategory() {
		return newCategory;
	}

	public void setNewCategory(boolean newCategory) {
		this.newCategory = newCategory;
	}
	
}
