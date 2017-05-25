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
import com.dnweb.springmvcshoeshop.util.CartUtils;
import com.dnweb.springmvcshoeshop.util.UserUtils;
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
		
		PaginationResult<ProductInfo> result = productDAO.queryProducts(1, 9, 10, "");
		PaginationResult<ProductInfo> lstPopuler = productDAO.listPopulerProduct(1, 9, 10);
		PaginationResult<ProductInfo> lstSales = productDAO.listSalesProduct(1, 9, 10);
		
		model.addAttribute("paginationProducts", result);
		model.addAttribute("listPopuler", lstPopuler);
		model.addAttribute("listSales", lstSales);
		
		return "index";
	}

	// Danh sach san pham
	@RequestMapping({ "/product/list" })
	public String listProductHandler(Model model, //
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		final int maxResult = 9;
		final int maxNavigationPage = 10;

		PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
				maxResult, maxNavigationPage, likeName);

		model.addAttribute("paginationProducts", result);
		return "productList";
	}

	// Danh sach san pham theo danh muc
	@RequestMapping(value = { "/category" })
	public String category(Model model, 
			@RequestParam(value = "id", defaultValue = "1") String id,
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		final int maxResult = 9;
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

			// Thong tin gio hang luu vao trong Session
			// trÆ°á»›c Ä‘Ă³.
			CartInfo cartInfo = CartUtils.getCartInSession(request);

			ProductInfo productInfo = new ProductInfo(product);

			cartInfo.addProduct(productInfo, 1);
		}

		// Chuyển sang danh sách các sản phẩm đã mua
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
			CartInfo cartInfo = CartUtils.getCartInSession(request);

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

		CartInfo cartInfo = CartUtils.getCartInSession(request);
		
		cartInfo.updateQuantity(cartForm);

		// Chuyá»ƒn sang trang danh sĂ¡ch cĂ¡c sáº£n pháº©m Ä‘Ă£ mua.
		return "redirect:/shoppingCart";
	}

	// GET: Hien thi gio hang
	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {
		CartInfo myCart = CartUtils.getCartInSession(request);

		//CustomerInfo customerInfo = myCart.getCustomerInfo();
		
		// if (customerInfo == null) {
		// customerInfo = new CustomerInfo();
		// }

		//model.addAttribute("customerForm", customerInfo);
		
		
		model.addAttribute("cartForm", myCart);
		
		return "shoppingCart";
	}

	// GET: Xem lại thông tin để xác nhận.
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
	public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
		
		CustomerInfo customerInfo = UserUtils.getLoginedUserFromSession(request);
		
		CartInfo cartInfo = CartUtils.getCartInSession(request);

		cartInfo.setCustomerInfo(customerInfo);
		
		// Chưa mua mặt hàng nào.
		if (cartInfo.isEmpty()) {

			// Chuyển tới trang danh giỏ hàng
			return "redirect:/shoppingCart";
		} 

		return "shoppingCartConfirmation";
	}

	// POST: Gửi đơn hàng (Save).
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)

	// Tránh ngoại lệ: UnexpectedRollbackException (Xem giải thích thêm).
	@Transactional(propagation = Propagation.NEVER)
	public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
		
		CartInfo cartInfo = CartUtils.getCartInSession(request);

		// Chưa mua mặt hàng nào.
		if (cartInfo.isEmpty()) {

			// Chuyển tới trang danh giỏ hàng
			return "redirect:/shoppingCart";
		} 
		
		try {
			orderDAO.saveOrder(cartInfo);
		} catch (Exception e) {

			// Cần thiết: Propagation.NEVER?
			return "shoppingCartConfirmation";
		}

		// Xóa rỏ hàng khỏi session.
		CartUtils.removeCartInSession(request);

		// Lưu thông tin đơn hàng đã xác nhận mua.
		CartUtils.storeLastOrderedCartInSession(request, cartInfo);

		// Chuyến hướng tới trang hoàn thành mua hàng.
		return "redirect:/shoppingCartFinalize";
	}

	@RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
	public String shoppingCartFinalize(HttpServletRequest request, Model model) {

		CartInfo lastOrderedCart = CartUtils.getLastOrderedCartInSession(request);

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
