package com.dnweb.springmvcshoeshop.controller;

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

	// Spring se tim kiem template Tile cĂ³ ten signup nhung ko cĂ³ ==> Loi.
	// Hien thi trang signup
	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public String signup(Model model) {
		AccountInfo accountForm = new AccountInfo();
		
		accountForm.setName("Nguyễn Văn A");
		
		accountForm.setAddress("Hà Nội");
		accountForm.setEmail("abc@gmaiil.com");
		model.addAttribute("accountForm", accountForm);

		return "signup";
	}

	// accountForm
	@RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model,
			@ModelAttribute("accountForm") @Validated AccountInfo accountForm //

			, BindingResult result, // Do co cai nay ==> bat buoc phai cung cap
									// validate
			final RedirectAttributes redirectAttributes

	) {

		// Neu validate co loi
		if (result.hasErrors()) {
			System.out.println("Lỗi:" + result.toString());
			accountForm.setValid(false);

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
	@RequestMapping(value = { "/editprofile" }, method = RequestMethod.GET)
	public String editAccount(HttpServletRequest request, Model model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (userDetails == null) {
			return "redirect:/login";
		}

		AccountInfo accountInfo = accountDAO.findAccountInfo(userDetails.getUsername());

		if (accountInfo == null) {
			accountInfo = new AccountInfo();
		}

		model.addAttribute("userAccountForm", accountInfo);

		return "editProfile";
	}

	// Luu thay doi thong tin nguoi dung
	@RequestMapping(value = { "/editprofile" }, method = RequestMethod.POST)
	public String editAccount(Model model, 
			@ModelAttribute("userAccountForm") @Validated AccountInfo userAccountForm, //
			BindingResult result) {

		if (result.hasErrors()) { 
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
		return "redirect:/accountInfo";
	}

}
