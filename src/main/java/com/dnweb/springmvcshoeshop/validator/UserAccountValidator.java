package com.dnweb.springmvcshoeshop.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dnweb.springmvcshoeshop.dao.AccountDAO;
import com.dnweb.springmvcshoeshop.entities.Account;
import com.dnweb.springmvcshoeshop.model.AccountInfo;

@Component
public class UserAccountValidator implements Validator {

	// common-validator library.
	private EmailValidator emailValidator = EmailValidator.getInstance();

	@Autowired
	private AccountDAO accountDAO;

	public boolean supports(Class<?> clazz) {
		return clazz == AccountInfo.class;
	}

	public void validate(Object target, Errors errors) {

		AccountInfo accountInfo = (AccountInfo) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userAccountForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.userAccountForm.userName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.userAccountForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userAccountForm.pasword");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.userAccountForm.address");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.userAccountForm.gender");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.userAccountForm.phone");

		if (errors.hasErrors()) {
			return;
		}

		// Email is invalid
		if (!emailValidator.isValid(accountInfo.getEmail())) {

			errors.rejectValue("email", "Pattern.userAccountForm.email");
			return;
		}

		// Password is invalid
		if (accountInfo.getPassword().length() < 5 ||accountInfo.getPassword().length() > 20) {
			errors.rejectValue("password", "Pattern.userAccountForm.email");
		}

		// Account already exist
		Account account = accountDAO.findAccountByUsername(accountInfo.getUsername());
		if (account != null) {
			if (accountInfo.getUsername() == null) {
				errors.rejectValue("userName", "Pattern.userAccountForm.userName");
				return;
			} else if (!accountInfo.getUsername().equals(account.getUsername())) {
				errors.rejectValue("userName", "Duplicate.userAccountForm.userName");
				return;
			}
		}

		// Email already exist
		account = accountDAO.findAccoutByEmail(accountInfo.getEmail());
		if (account != null) {
			if (account.getEmail() == null ) {
				errors.rejectValue("email", "Pattern.userAccountForm.email");
				return;
			} else if (!account.getEmail().equals(accountInfo.getEmail())) {
				errors.rejectValue("email", "Duplicate.userAccountForm.email");
				return;
			}
		}
		
		//Gender invalid
		if (accountInfo.getGender() != "Nam" || accountInfo.getGender() != "Nữ") {
			errors.rejectValue("gender", "Invalid.customerForm.gender");
		}
		
	}

	

}
