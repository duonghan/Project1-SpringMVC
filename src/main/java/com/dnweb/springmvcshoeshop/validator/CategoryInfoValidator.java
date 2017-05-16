package com.dnweb.springmvcshoeshop.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dnweb.springmvcshoeshop.dao.CategoryDAO;
import com.dnweb.springmvcshoeshop.entities.Category;
import com.dnweb.springmvcshoeshop.model.CategoryInfo;

@Component
public class CategoryInfoValidator implements Validator{
	
	@Autowired
	private CategoryDAO categoryDAO;

	public boolean supports(Class<?> clazz) {
		return clazz == CategoryInfo.class;
	}

	public void validate(Object target, Errors errors) {
		CategoryInfo categoryInfo = (CategoryInfo) target;
		
		//Kiem tra cac truong cua ProductInfo.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.categoryForm.id");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.categoryForm.name");
		
		//Kiem tra cac truong da ton tai
		String id = categoryInfo.getId();
		
		Category category = categoryDAO.findCategory(id);
		if (category != null) {
			errors.rejectValue("id", "Duplicate.categoryForm.id");
		}
	}
	
}
