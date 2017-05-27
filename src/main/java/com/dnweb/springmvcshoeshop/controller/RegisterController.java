package com.dnweb.springmvcshoeshop.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dnweb.springmvcshoeshop.dao.AccountDAO;
import com.dnweb.springmvcshoeshop.model.AccountInfo;
import com.dnweb.springmvcshoeshop.model.CustomerInfo;
import com.dnweb.springmvcshoeshop.util.UserUtils;
import com.dnweb.springmvcshoeshop.validator.UserAccountValidator;

@Controller
// Su dung Hibernate Transaction.
@Transactional
// Su dung RedirectAttributes
@EnableWebMvc
public class RegisterController {

	@Autowired
	AccountDAO accountDAO;

	@Autowired
	UserAccountValidator userAccountValidator;

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		if (target.getClass() == AccountInfo.class) {
			// No se set validate vao cho dataBinder!!
			dataBinder.setValidator(userAccountValidator);
			// For upload Image.
		}
	}

	//Gioi tinh
	 private Map<String, String> dataGender() {
	       Map<String, String> genderMap = new LinkedHashMap<String, String>();
	       genderMap.put("Nam", "Nam");
	       genderMap.put("Nữ", "Nữ");
	       genderMap.put("Khác", "Khác");
	       return genderMap;
	  }
	 
	 // Day la trang hien thi signup
	// Hien thi trang signup
	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public String signup(Model model) {
		AccountInfo accountForm = new AccountInfo();
		
		Map<String, String> genderMap = this.dataGender();
		
		accountForm.setName("Nguyễn Văn A");
		
		accountForm.setAddress("Hà Nội");
		accountForm.setEmail("example@gmail.com");
		
		model.addAttribute("accountForm", accountForm);
		model.addAttribute("genderMap", genderMap);

		return "signup";
	}

	@RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model,
			@ModelAttribute("accountForm") @Validated AccountInfo accountForm //

			, BindingResult result, 
			final RedirectAttributes redirectAttributes

	) {

		// Neu validate co loi
		if (result.hasErrors()) {
			System.out.println("Lỗi:" + result.toString());
			accountForm.setValid(false);
			Map<String, String> genderMap = this.dataGender();
			
			model.addAttribute("genderMap", genderMap);
			model.addAttribute("accountForm", accountForm);
			
			return "signup";
		}

		accountForm.setValid(true);
		System.out.println("Save ..... ");
		this.accountDAO.saveAccount(accountForm);

		System.out.println("Saved ..... ");

		redirectAttributes.addFlashAttribute("message", "Đăng kí tài khoản không thành công!");
		return "redirect:/login";
	}

	// Hien thi trang chinh sua thong tin tai hoan nguoi dung
	@RequestMapping(value = { "/profile/edit" }, method = RequestMethod.GET)
	public String updateAccount(HttpServletRequest request, Model model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerInfo customerInfo = UserUtils.getLoginedUserFromSession(request);
		if (customerInfo == null) {
			
			return "redirect:/login";
		}
		
		AccountInfo accountInfo = accountDAO.findAccountInfo(userDetails.getUsername());

		if (accountInfo == null) {
			accountInfo = new AccountInfo();
		}

		Map<String, String> genderMap = this.dataGender();
		model.addAttribute("userAccountForm", accountInfo);
		model.addAttribute("genderMap", genderMap);
		
		return "editProfile";
		
	}

	// Luu thay doi thong tin nguoi dung
	@RequestMapping(value = { "/profile/edit" }, method = RequestMethod.POST)
	public String updateAccount(HttpServletRequest request,Model model, 
			@ModelAttribute("userAccountForm") @Validated AccountInfo userAccountForm, //
			BindingResult result) {

		// Nếu validate có lỗi.
		if (result.hasErrors()) { 
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Error:" + result.toString());
			model.addAttribute("errors", "Đã xảy ra lỗi, vui lòng thử lại!");
			return "editProfile";
		}
		
		try {
			accountDAO.saveAccount(userAccountForm);

		} catch (Exception e) {
			String message = e.getMessage();
			model.addAttribute("message", message);

			// Show edit account Info page.
			return "editProfile"; 

		}
		
		//Luu thong tin sau khi cap nhat vao trong sesion
		CustomerInfo customerInfo = new CustomerInfo(userAccountForm);
		UserUtils.saveLoginedUser(request, customerInfo);
		return "redirect:/profile";
	}

}
