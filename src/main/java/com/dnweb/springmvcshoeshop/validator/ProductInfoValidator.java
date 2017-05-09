package com.dnweb.springmvcshoeshop.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dnweb.springmvcshoeshop.dao.ProductDAO;
import com.dnweb.springmvcshoeshop.entities.Product;
import com.dnweb.springmvcshoeshop.model.ProductInfo;

@Component
public class ProductInfoValidator implements Validator {
	@Autowired
	private ProductDAO productDAO;

	// Validator này chỉ dùng để kiểm tra class ProductInfo.
	public boolean supports(Class<?> clazz) {
		return clazz == ProductInfo.class;
	}
	
	
	public void validate(Object target, Errors errors) {
		ProductInfo productInfo = (ProductInfo) target;

		// Kiểm tra các trường (field) của ProductInfo.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.productForm.id");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");

		String code = productInfo.getId();
		if (code != null && code.length() > 0) {
			if (code.matches("\\s+")) {
				errors.rejectValue("code", "Pattern.productForm.code");
			} else if (productInfo.isNewProduct()) {
				Product product = productDAO.findProduct(code);
				if (product != null) {
					errors.rejectValue("code", "Duplicate.productForm.code");
				}
			}
		}
	}

}
