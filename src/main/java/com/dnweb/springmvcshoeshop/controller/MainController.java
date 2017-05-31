package com.dnweb.springmvcshoeshop.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.dnweb.springmvcshoeshop.dao.OrderDAO;
import com.dnweb.springmvcshoeshop.dao.ProductDAO;
import com.dnweb.springmvcshoeshop.entities.Product;
import com.dnweb.springmvcshoeshop.model.CartInfo;
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

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();

		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		// Update tren gio hang
		// (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
		if (target.getClass() == CartInfo.class) {

		}
	}
	
	//Loi quyen truy cap
	@RequestMapping("/403")
	public String accessDenied() {
		return "403";
	}
	
	//Khong tim thay trang
	@RequestMapping("/404")
	public String pageNotFound(Model model) {
		return "404";
	}
		
	@RequestMapping("/about")
	public String about() {
		return "about";
	}

	@RequestMapping("/contactus")
	public String contactus(Model model) {
		return "contactusPage";
	}
	
	
	//Hien thi trang chu (ds san pham moi, ban chay...)
	@RequestMapping("/")
	public String home( Model model ) {
		
		//Mac dinh trang hien tai
		final int page = 1;
		//So ban ghi toi da trong 1 trang
		final int maxResult = 9;
		//So link trang toi da
		final int maxNavigationPage = 1;
		
		PaginationResult<ProductInfo> result = productDAO.queryProducts(page, maxResult, maxNavigationPage, "");
		PaginationResult<ProductInfo> lstPopuler = productDAO.listPopulerProduct(page, maxResult, maxNavigationPage);
		PaginationResult<ProductInfo> lstSales = productDAO.listSalesProduct(page, maxResult, maxNavigationPage);
		
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
	
	
	//Chi tiet tung san pham
	@RequestMapping(value = { "/product/info" })
	public String product(Model model,//
			@RequestParam(value = "id", defaultValue = "") String id) {
		
		ProductInfo productInfo = null;
		
		if (id != null && id.length() > 0) {
			productInfo = productDAO.findProductInfo(id);
		}else{
			return "redirect:/404";
		}
		
		model.addAttribute("productForm", productInfo);
		
		return "productInfo";
	}
	
	@RequestMapping({ "/buyProduct" })
	public String listProductHandler(HttpServletRequest request, Model model, //
			@RequestParam(value = "id", defaultValue = "") String id) {

		Product product = null;
		if (id != null && id.length() > 0) {
			product = productDAO.findProduct(id);
		}
		if (product != null) {

			CartInfo cartInfo = CartUtils.getCartInSession(request);

			ProductInfo productInfo = new ProductInfo(product);

			cartInfo.addProduct(productInfo, 1);
		}

		// Chuyển sang danh sách các sản phẩm đã mua
		return "redirect:/shopping-cart";
	}

	@RequestMapping({ "/shopping-cart/remove/product" })
	public String removeProductHandler(HttpServletRequest request, Model model, //
			@RequestParam(value = "id", defaultValue = "") String id) {
		Product product = null;
		if (id != null && id.length() > 0) {
			product = productDAO.findProduct(id);
		}
		if (product != null) {

			CartInfo cartInfo = CartUtils.getCartInSession(request);

			ProductInfo productInfo = new ProductInfo(product);

			cartInfo.removeProduct(productInfo);

		}

		return "redirect:/shopping-cart";
	}

	// GET: Hien thi gio hang
	@RequestMapping(value = { "/shopping-cart" }, method = RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {
		CartInfo myCart = CartUtils.getCartInSession(request);

		model.addAttribute("cartForm", myCart);
		
		return "shoppingCart";
	}
	
	// POST: Cap nhat so luong cho cac san pham da mua
	@RequestMapping(value = { "/shopping-cart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("cartForm") CartInfo cartForm) {

		CartInfo cartInfo = CartUtils.getCartInSession(request);
		
		cartInfo.updateQuantity(cartForm);
		//cartInfo.setDescription(cartForm);
		return "redirect:/shopping-cart";
	}
	
	//GET:Hien thi trang nhap thong tin bo sung
	@RequestMapping( value ={"/shopping-cart/other-info"}, method = RequestMethod.GET)
	public String shoppingCartOtherInformation(HttpServletRequest request,
			Model model){
		
		CartInfo cartInfo = CartUtils.getCartInSession(request);
		
		if (cartInfo.isEmpty()) {
			return "redirect:/shopping-cart";
		}
		
		model.addAttribute("cartForm", cartInfo);
		
		return "shoppingCartOtherInfo";
	}
	
	//POST: Luu thong tin bo sung don hang
	@RequestMapping( value = {"/shopping-cart/other-info"}, method = RequestMethod.POST)
	public String shoppingCartOtherInformation(HttpServletRequest request,
			Model model,
			@ModelAttribute("cartForm") CartInfo cartForm){
		
		if (!cartForm.getDescription().isEmpty()) {
			CartInfo cartInfo = CartUtils.getCartInSession(request);
			cartInfo.setDescription(cartForm.getDescription());
		}
		
		
		return "redirect:/shopping-cart/confirm";
	}
	// GET: Xem lại thông tin để xác nhận.
	@RequestMapping(value = { "/shopping-cart/confirm" }, method = RequestMethod.GET)
	public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
		
		CustomerInfo customerInfo = UserUtils.getLoginedUserFromSession(request);
		
		CartInfo cartInfo = CartUtils.getCartInSession(request);

		cartInfo.setCustomerInfo(customerInfo);
		
		model.addAttribute("cartForm", cartInfo);
		
		// Chưa mua mặt hàng nào.
		if (cartInfo.isEmpty()) {

			// Chuyển tới trang danh giỏ hàng
			return "redirect:/shopping-cart";
		} 

		return "shoppingCartConfirmation";
	}

	// POST: Gửi đơn hàng (Save).
	@RequestMapping(value = { "/shopping-cart/confirm" }, method = RequestMethod.POST)

	// Tránh ngoại lệ: UnexpectedRollbackException.
	@Transactional(propagation = Propagation.NEVER)
	public String shoppingCartConfirmationSave(HttpServletRequest request, Model model,
			@ModelAttribute("cartForm") CartInfo cartForm) {
		
		CartInfo cartInfo = CartUtils.getCartInSession(request);

		// Chưa mua mặt hàng nào.
		if (cartInfo.isEmpty()) {
			return "redirect:/shopping-cart";
		} 
		
		try {
			orderDAO.saveOrder(cartInfo);
		} catch (Exception e) {
            e.printStackTrace();
			// Neu co gi loi ==> Chuyen lai sang 
			return "shoppingCartConfirmation";
		}

		// Xoa gio hang khoi Session.
		CartUtils.removeCartInSession(request);

		// Luu thong tin odn hang da xac nhan mua.
		CartUtils.storeLastOrderedCartInSession(request, cartInfo);

		// Chuyen huong den trang hoan thanh mua hang.
		return "redirect:/shopping-cart/finalize";
	}

	//Trang hoan thanh mua hang
	@RequestMapping(value = { "/shopping-cart/finalize" }, method = RequestMethod.GET)
	public String shoppingCartFinalize(HttpServletRequest request, Model model) {

		CartInfo lastOrderedCart = CartUtils.getLastOrderedCartInSession(request);

		if (lastOrderedCart == null) {
			return "redirect:/shopping-cart";
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
	
	
	// O tim kiem san pham
	@RequestMapping(value = {"/product/search"})
	public String searchProduct(HttpServletRequest request, Model model,
			@RequestParam(value= "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page){
		
		final int maxResult = 9;
		final int maxNavigationPage = 10;

		PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
				maxResult, maxNavigationPage, likeName);

		model.addAttribute("paginationProducts", result);
		return "productList";
	}

}
