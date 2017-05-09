package com.dnweb.springmvcshoeshop.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dnweb.springmvcshoeshop.dao.AccountDAO;
import com.dnweb.springmvcshoeshop.model.AccountInfo;
import com.dnweb.springmvcshoeshop.model.ProductInfo;
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
			// Sử dụng cho upload Image.
			// dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		}
	}
	
	
	// Set a form validator
	// @InitBinder
	// protected void initBinder(WebDataBinder dataBinder) {
	//
	// // Form mục tiêu
	// Object target = dataBinder.getTarget();
	// if (target == null) {
	// return;
	// }
	// System.out.println("Target=" + target);
	//
	// if (target.getClass() == AccountInfo.class) {
	// dataBinder.setValidator(userAccountValidator);
	// }
	// }

	

	// Spring se tim kiem template Tile có ten signup nhung ko có ==> Loi.
	// Hien thi trang signup
	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public String signup(Model model) {
       AccountInfo accountForm = new AccountInfo();
       
       accountForm.setAddress("Nothing");
       accountForm.setEmail("abc@gmaiil.com");// Cac gia tri mac dinh 
       // tren trang jsp: sign.jsp co su dung 
       // modelAttribute="accountForm"
       // Vi vay phai cung cap thuoc tinh nay. Mac dinh la rong.
       // Tuy nhien co the set các gia tri mac dinh neu muon
       model.addAttribute( "accountForm", accountForm);
		
		return "signup";
	}
	
	
	// accountForm
	@RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model,
			// Sai o cho nay!! accountForm đăng ký rằng nó được Validated bởi 1
			// bộ Validate (Validated == Đã được validate truoc do
			// Truoc khi truyen vao day.
			// Tuy nhien lai khong cung cap cho no mot bo validate!!
			// Có 2 cách - 1: Bỏ @validaed đi 
			// 2- Cung cap 1 validate cho no.
			@ModelAttribute("accountForm") @Validated AccountInfo accountForm //

			, BindingResult result, // Do co cai nay ==> bat buoc phai cung cap validate
			final RedirectAttributes redirectAttributes

	) {

		// Nếu validate có lỗi.
		if (result.hasErrors()) {
			System.out.println("Ẻo:"+ result.toString());
			accountForm.setValid(false);

			return "signup";
		}

		accountForm.setValid(true);
		System.out.println("Save ..... ");
		this.accountDAO.saveAccount(accountForm);
		
		System.out.println("Saved ..... ");

		redirectAttributes.addFlashAttribute("message", "Đăng ký tài khoản thành công!");
		return "redirect:/login";
	}
}
