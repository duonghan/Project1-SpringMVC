package com.dnweb.springmvcshoeshop.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dnweb.springmvcshoeshop.dao.CategoryDAO;
import com.dnweb.springmvcshoeshop.dao.OrderDAO;
import com.dnweb.springmvcshoeshop.dao.ProductDAO;
import com.dnweb.springmvcshoeshop.entities.Product;
import com.dnweb.springmvcshoeshop.model.CartInfo;
import com.dnweb.springmvcshoeshop.model.CustomerInfo;
import com.dnweb.springmvcshoeshop.model.PaginationResult;
import com.dnweb.springmvcshoeshop.model.ProductInfo;
import com.dnweb.springmvcshoeshop.util.Utils;
import com.dnweb.springmvcshoeshop.validator.CustomerInfoValidator;

@Controller

// Su dung Hibernate Transaction.
@Transactional

// Su dung RedirectAttributes
@EnableWebMvc
public class MainController {
	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private CustomerInfoValidator customerInfoValidator;

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();

		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		// TrÆ°á»�ng há»£p update SL trĂªn giá»� hĂ ng.
		// (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
		if (target.getClass() == CartInfo.class) {

		}

		// TrÆ°á»�ng há»£p save thĂ´ng tin khĂ¡ch hĂ ng.
		// (@ModelAttribute("customerForm") @Validated CustomerInfo
		// customerForm)
		else if (target.getClass() == CustomerInfo.class) {
			dataBinder.setValidator(customerInfoValidator);
		}

	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "403";
	}
	
	@RequestMapping("/about")
	public String about() {
		return "about";
	}

	@RequestMapping("/contactus")
	public String contactus(Model model) {
		listCategory(model);
		return "contactus";
	}
	
	// Vi du trang hom ...
	@RequestMapping("/")
	public String home(/*Model model*/) {
		//model.addAttribute("listCategory", this.categoryDAO.getAllCategory());
		//listCategory(model);
		return "index";
	}
	

	// Phuong thuc na can duoc goi tai má»�i nÆ¡i, Ä‘á»ƒ nĂ³ táº¡o attribute nay cho _menu.
	//Hiá»ƒn thá»‹ danh má»¥c sáº£n pháº©m
	 // Vi du nhu the, khi do trang attribute listCaegory luon co gia tri tai má»�i trang.
	// Hoáº·c cĂ³ thá»ƒ lĂ m nĂ³ trong Intercepter.
	// Hieu khong?
	//hieu r a ak
	//lan trc e cu tuong controller nĂ³ cáº§n pháº£i nháº­n Ä‘c cĂ¡i request nĂ o Ä‘Ă³ kia
	//nhÆ°ng h e hiá»ƒu r
	//hnay cÅ©ng muá»™n r, hnay e há»�i nhiá»�u quĂ¡
	//
	public String listCategory(Model model){
		model.addAttribute("listCategory", categoryDAO.getAllCategory());
		return "_menu";
		 
	}
	
	
	// Danh sĂ¡ch sáº£n pháº©m.
	@RequestMapping({ "/productList" })
	public String listProductHandler(Model model, //
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		final int maxResult = 5;
		final int maxNavigationPage = 10;

		PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
				maxResult, maxNavigationPage, likeName);

		model.addAttribute("paginationProducts", result);
		return "productList";
	}

	@RequestMapping({ "/buyProduct" })
	public String listProductHandler(HttpServletRequest request, Model model, //
			@RequestParam(value = "id", defaultValue = "") String id) {

		Product product = null;
		if (id != null && id.length() > 0) {
			product = productDAO.findProduct(id);
		}
		if (product != null) {

			// ThĂ´ng tin giá»� hĂ ng cĂ³ thá»ƒ Ä‘Ă£ lÆ°u vĂ o trong Session trÆ°á»›c Ä‘Ă³.
			CartInfo cartInfo = Utils.getCartInSession(request);

			ProductInfo productInfo = new ProductInfo(product);

			cartInfo.addProduct(productInfo, 1);
		}

		// Chuyá»ƒn sang trang danh sĂ¡ch cĂ¡c sáº£n pháº©m Ä‘Ă£ mua.
		return "redirect:/shoppingCart";
	}

	@RequestMapping({ "/shoppingCartRemoveProduct" })
	public String removeProductHandler(HttpServletRequest request, Model model, //
			@RequestParam(value = "id", defaultValue = "") String id) {
		Product product = null;
		if (id != null && id.length() > 0) {
			product = productDAO.findProduct(id);
		}
		if (product != null) {

			// ThĂ´ng tin giá»� hĂ ng cĂ³ thá»ƒ Ä‘Ă£ lÆ°u vĂ o trong Session trÆ°á»›c Ä‘Ă³.
			CartInfo cartInfo = Utils.getCartInSession(request);

			ProductInfo productInfo = new ProductInfo(product);

			cartInfo.removeProduct(productInfo);

		}

		// Chuyá»ƒn sang trang danh sĂ¡ch cĂ¡c sáº£n pháº©m Ä‘Ă£ mua.
		return "redirect:/shoppingCart";
	}

	// POST: Cáº­p nháº­p sá»‘ lÆ°á»£ng cho cĂ¡c sáº£n pháº©m Ä‘Ă£ mua.
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("cartForm") CartInfo cartForm) {

		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.updateQuantity(cartForm);

		// Chuyá»ƒn sang trang danh sĂ¡ch cĂ¡c sáº£n pháº©m Ä‘Ă£ mua.
		return "redirect:/shoppingCart";
	}

	// GET: Hiá»ƒn thá»‹ giá»� hĂ ng.
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {
		CartInfo myCart = Utils.getCartInSession(request);

		model.addAttribute("cartForm", myCart);
		return "shoppingCart";
	}

	// GET: Nháº­p thĂ´ng tin khĂ¡ch hĂ ng.
	@RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
	public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {

		CartInfo cartInfo = Utils.getCartInSession(request);

		// ChÆ°a mua máº·t hĂ ng nĂ o.
		if (cartInfo.isEmpty()) {

			// Chuyá»ƒn tá»›i trang danh giá»� hĂ ng
			return "redirect:/shoppingCart";
		}

		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
		if (customerInfo == null) {
			customerInfo = new CustomerInfo();
		}

		model.addAttribute("customerForm", customerInfo);

		return "shoppingCartCustomer";
	}

	// POST: Save thĂ´ng tin khĂ¡ch hĂ ng.
	@RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
	public String shoppingCartCustomerSave(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("customerForm") @Validated CustomerInfo customerForm, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {

		// Káº¿t quáº£ Validate CustomerInfo.
		if (result.hasErrors()) {
			customerForm.setValid(false);
			// Forward to reenter customer info.
			// Forward tá»›i trang nháº­p láº¡i.
			return "shoppingCartCustomer";
		}

		customerForm.setValid(true);
		CartInfo cartInfo = Utils.getCartInSession(request);

		cartInfo.setCustomerInfo(customerForm);

		// Chuyá»ƒn hÆ°á»›ng sang trang xĂ¡c nháº­n.
		return "redirect:/shoppingCartConfirmation";
	}

	// GET: Xem láº¡i thĂ´ng tin Ä‘á»ƒ xĂ¡c nháº­n.
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
	public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);

		// ChÆ°a mua máº·t hĂ ng nĂ o.
		if (cartInfo.isEmpty()) {

			// Chuyá»ƒn tá»›i trang danh giá»� hĂ ng
			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {

			// Chuyá»ƒn tá»›i trang nháº­p thĂ´ng tin khĂ¡ch hĂ ng.
			return "redirect:/shoppingCartCustomer";
		}

		return "shoppingCartConfirmation";
	}

	// POST: Gá»­i Ä‘Æ¡n hĂ ng (Save).
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)

	// Day xá»­ lĂ½, nguoi dung hoan thanh dÆ¡n hang ==> Luu thong tin don hang
	// VĂ o DB
	// TrĂ¡nh ngoáº¡i lá»‡: UnexpectedRollbackException (Xem giáº£i thĂ­ch thĂªm).
	@Transactional(propagation = Propagation.NEVER)
	public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);

		// ChÆ°a mua máº·t hĂ ng nĂ o.
		if (cartInfo.isEmpty()) {

			// Chuyá»ƒn tá»›i trang danh giá»� hĂ ng
			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {

			// Chuyá»ƒn tá»›i trang nháº­p thĂ´ng tin khĂ¡ch hĂ ng.
			return "redirect:/shoppingCartCustomer";
		}
		try {
			// LÆ°u don hang vao DB
			orderDAO.saveOrder(cartInfo);
		} catch (Exception e) {

			// Cáº§n thiáº¿t: Propagation.NEVER?
			return "shoppingCartConfirmation";
		}

		// XĂ³a rá»� hĂ ng khá»�i session.
		Utils.removeCartInSession(request);

		// LÆ°u thĂ´ng tin Ä‘Æ¡n hĂ ng Ä‘Ă£ xĂ¡c nháº­n mua.
		// LÆ°u thong tin don hang vua mua (La don hang cuoi cung, moi nhĂ¡t).
		Utils.storeLastOrderedCartInSession(request, cartInfo);

		// Chuyáº¿n hÆ°á»›ng tá»›i trang hoĂ n thĂ nh mua hĂ ng.
		return "redirect:/shoppingCartFinalize";
	}

	@RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
	public String shoppingCartFinalize(HttpServletRequest request, Model model) {

		CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

		if (lastOrderedCart == null) {
			return "redirect:/shoppingCart";
		}

		return "shoppingCartFinalize";
	}

	@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
	public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("id") String id) throws IOException {
		Product product = null;
		if (id != null) {
			product = this.productDAO.findProduct(id);
		}
		if (product != null && product.getImage() != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(product.getImage());
		}
		response.getOutputStream().close();
	}
	


}
