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
import com.dnweb.springmvcshoeshop.model.CategoryInfo;
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

		// Rruong hop update tren gio hang
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
		// listCategory(model);
		return "contactusPage";
	}

	// Vi du trang hom ...
	@RequestMapping("/")
	public String home( Model model ) {
//		 model.addAttribute("listCategory",
//		this.categoryDAO.getAllCategory());
		 //listCategory(model);
		return "index";
	}

	// Phuong thuc na can duoc goi tai má»�i nÆ¡i, Ä‘á»ƒ nĂ³ táº¡o attribute nay
	// cho _menu.
	// Hiá»ƒn thá»‹ danh má»¥c sáº£n pháº©m
	// Vi du nhu the, khi do trang attribute listCaegory luon co gia tri tai
	// má»�i trang.
	// Hoáº·c cĂ³ thá»ƒ lĂ m nĂ³ trong Intercepter.
	// Hieu khong?
	// hieu r a ak
	// lan trc e cu tuong controller nĂ³ cáº§n pháº£i nháº­n Ä‘c cĂ¡i request
	// nĂ o Ä‘Ă³ kia
	// nhÆ°ng h e hiá»ƒu r
	// hnay cÅ©ng muá»™n r, hnay e há»�i nhiá»�u quĂ¡
	// Goi ham nay, listCateogry co the su dung moi noi, ke ca _menu.jsp, _home.jsp, ...
	// No su dung duoc trong moi View!!
	//nghia la e chi can minh ham nay thoi
	//co the k can trong ham nay, ma van có the dung dc a a
	
	
//	public void listCategory(Model model) {
//		model.addAttribute("listCategory", categoryDAO.getAllCategory());
//	}

	// Danh sach san pham
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

	// Danh sach san pham theo danh muc
	@RequestMapping(value = { "/category" }, method = RequestMethod.GET)
	public String category(Model model, 
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		final int maxResult = 5;
		final int maxNavigationPage = 10;

		PaginationResult<ProductInfo> result = productDAO.queryProductsCategory(page, //
				maxResult, maxNavigationPage, id, likeName);
		
		model.addAttribute("paginationProductsByCategory", result);
		
		return "category";
	}

	@RequestMapping({ "/buyProduct" })
	public String listProductHandler(HttpServletRequest request, Model model, //
			@RequestParam(value = "id", defaultValue = "") String id) {

		Product product = null;
		if (id != null && id.length() > 0) {
			product = productDAO.findProduct(id);
		}
		if (product != null) {

			// ThĂ´ng tin giá»� hĂ ng cĂ³ thá»ƒ Ä‘Ă£ lÆ°u vĂ o trong Session
			// trÆ°á»›c Ä‘Ă³.
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

			// ThĂ´ng tin giá»� hĂ ng cĂ³ thá»ƒ Ä‘Ă£ lÆ°u vĂ o trong Session
			// trÆ°á»›c Ä‘Ă³.
			CartInfo cartInfo = Utils.getCartInSession(request);

			ProductInfo productInfo = new ProductInfo(product);

			cartInfo.removeProduct(productInfo);

		}

		// Chuyá»ƒn sang trang danh sĂ¡ch cĂ¡c sáº£n pháº©m Ä‘Ă£ mua.
		return "redirect:/shoppingCart";
	}

	// POST: Cap nhat so luong cho cac san pham da mua
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("cartForm") CartInfo cartForm) {

		CartInfo cartInfo = Utils.getCartInSession(request);
		
		cartInfo.updateQuantity(cartForm);

		// Chuyá»ƒn sang trang danh sĂ¡ch cĂ¡c sáº£n pháº©m Ä‘Ă£ mua.
		return "redirect:/shoppingCart";
	}

	// GET: Hien thi gio hang
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {
		CartInfo myCart = Utils.getCartInSession(request);

		CustomerInfo customerInfo = myCart.getCustomerInfo();
		
		if (customerInfo == null) {
			customerInfo = new CustomerInfo();
		}

		model.addAttribute("customerForm", customerInfo);
		
		
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

	// POST: Save thông tin khách hàng.
	@RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
	public String shoppingCartCustomerSave(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("customerForm") @Validated CustomerInfo customerForm, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {

		// Kết quả Validate CustomerInfo.
		if (result.hasErrors()) {
			customerForm.setValid(false);
			// Forward to reenter customer info.
			// Forward tới trang nhập lại.
			return "shoppingCartCustomer";
		}

		customerForm.setValid(true);
		CartInfo cartInfo = Utils.getCartInSession(request);

		cartInfo.setCustomerInfo(customerForm);

		// Chuyển hướng sang trang xác nhận.
		return "redirect:/shoppingCartConfirmation";
	}

	// GET: Xem lại thông tin để xác nhận.
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
	public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);

		// Chưa mua mặt hàng nào.
		if (cartInfo.isEmpty()) {

			// Chuyển tới trang danh giỏ hàng
			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {

			// Chuyển tới trang nhập thông tin khách hàng.
			return "redirect:/shoppingCartCustomer";
		}

		return "shoppingCartConfirmation";
	}

	// POST: Gửi đơn hàng (Save).
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)

	// Tránh ngoại lệ: UnexpectedRollbackException (Xem giải thích thêm).
	@Transactional(propagation = Propagation.NEVER)
	public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);

		// Chưa mua mặt hàng nào.
		if (cartInfo.isEmpty()) {

			// Chuyển tới trang danh giỏ hàng
			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {

			// Chuyển tới trang nhập thông tin khách hàng.
			return "redirect:/shoppingCartCustomer";
		}
		try {
			orderDAO.saveOrder(cartInfo);
		} catch (Exception e) {

			// Cần thiết: Propagation.NEVER?
			return "shoppingCartConfirmation";
		}

		// Xóa rỏ hàng khỏi session.
		Utils.removeCartInSession(request);

		// Lưu thông tin đơn hàng đã xác nhận mua.
		Utils.storeLastOrderedCartInSession(request, cartInfo);

		// Chuyến hướng tới trang hoàn thành mua hàng.
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
