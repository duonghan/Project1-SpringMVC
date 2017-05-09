package com.dnweb.springmvcshoeshop.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dnweb.springmvcshoeshop.model.CustomerInfo;

@Component
public class CustomerInfoValidator implements Validator {

	private EmailValidator emailValidator = EmailValidator.getInstance();

	public boolean supports(Class<?> clazz) {
		return clazz == CustomerInfo.class;
	}

	public void validate(Object target, Errors errors) {
		
		CustomerInfo custInfo = (CustomerInfo) target;

		// Kiểm tra các trường (field) của CustomerInfo.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.customerForm.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.customerForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.customerForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.customerForm.address");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.customerForm.phone");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.customerForm.gender");
		
		//Kiểm tra định dạng của email
		if (!emailValidator.isValid(custInfo.getEmail())) {
			errors.rejectValue("email", "Pattern.customerForm.email");
		}

	}

}
