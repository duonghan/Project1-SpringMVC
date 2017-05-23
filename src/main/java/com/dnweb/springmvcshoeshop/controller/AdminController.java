package com.dnweb.springmvcshoeshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dnweb.springmvcshoeshop.dao.AccountDAO;
import com.dnweb.springmvcshoeshop.dao.CategoryDAO;
import com.dnweb.springmvcshoeshop.dao.OrderDAO;
import com.dnweb.springmvcshoeshop.dao.ProductDAO;
import com.dnweb.springmvcshoeshop.model.AccountInfo;
import com.dnweb.springmvcshoeshop.model.CategoryInfo;
import com.dnweb.springmvcshoeshop.model.CustomerInfo;
import com.dnweb.springmvcshoeshop.model.OrderDetailInfo;
import com.dnweb.springmvcshoeshop.model.OrderInfo;
import com.dnweb.springmvcshoeshop.model.PaginationResult;
import com.dnweb.springmvcshoeshop.model.ProductInfo;
import com.dnweb.springmvcshoeshop.util.UserUtils;
import com.dnweb.springmvcshoeshop.validator.ProductInfoValidator;

@Controller
// Enable Hibernate Transaction.
@Transactional
// Need to use RedirectAttributes
@EnableWebMvc
public class AdminController {
	
	// Day la cac nhiem vu cua Admin
	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private ProductInfoValidator productInfoValidator;
	
	//Duoc cau hinh ApplicationContextConfig
	@Autowired
	private ResourceBundleMessageSource messageSource;

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		if (target.getClass() == ProductInfo.class) {
			dataBinder.setValidator(productInfoValidator);
			// For upload Image.
			dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		}
	}

	// GET: Show Login Page
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(Model model) {

		
		return "login";
	}
	
	public void saveAcccountSession(HttpServletRequest request){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		CustomerInfo customerInfo = this.accountDAO.findCustomerInfo(userDetails.getUsername());
		UserUtils.saveLoginedUser(request, customerInfo);
	}
	
	@RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
	public String accountInfo(HttpServletRequest request,Model model) {

		// Lay ra tu Sesssion.
		CustomerInfo customerInfo = UserUtils.getLoginedUserFromSession(request);
		 
		System.out.println("customerInfo:" + customerInfo);
		
		model.addAttribute("loginedUser", customerInfo);
		
		return "accountInfo";
	}
	
	@RequestMapping(value = { "/orderList" }, method = RequestMethod.GET)
	public String orderList(Model model, //
			@RequestParam(value = "page", defaultValue = "1") String pageStr) {
		int page = 1;
		try {
			page = Integer.parseInt(pageStr);
		} catch (Exception e) {
		}
		final int MAX_RESULT = 5;
		final int MAX_NAVIGATION_PAGE = 10;

		PaginationResult<OrderInfo> paginationResult //
				= orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

		model.addAttribute("paginationResult", paginationResult);
		return "orderList";
	}
	
	//Thay doi category
	@RequestMapping(value = { "/editCategory" }, method=RequestMethod.GET)
	public String showCategory(Model model,
			@RequestParam(value = "id", defaultValue="") String id){
		
		CategoryInfo categoryInfo = null;
		
		if (id != null && id.length() > 0) {
			categoryInfo = categoryDAO.findCategoryInfo(id);
		}
		if (categoryInfo == null) {
			categoryInfo = new CategoryInfo();
			categoryInfo.setNewCategory(true);
		}
		
		model.addAttribute("categoryForm", categoryInfo);
		return "editCategory";
	}
	
	// No se goi toi phuong thuc nay. Ma phuong thuc nay mình lập trình ko vì mục đích save
	// ==> Vẫn dc, nó đơn giản là hiển thị SP thay vì save ==> Ko đúng mục đích vậy thôi.
	//ok e hiểu r ạ
	// Day la GET ==> Khi người dùng xem một SP.
	// GET: Show product.
	// GET: Hiá»ƒn thá»‹ product
	@RequestMapping(value = { "/product" }, method = RequestMethod.GET)
	public String product(Model model, @RequestParam(value = "id", defaultValue = "") String id) {
		ProductInfo productInfo = null;

		if (id != null && id.length() > 0) {
			productInfo = productDAO.findProductInfo(id);
		}
		if (productInfo == null) {
			productInfo = new ProductInfo();
			productInfo.setNewProduct(true);
		}
		model.addAttribute("productForm", productInfo);
		return "product";
	}
	
	//Luu thong tin sau khi thay doi category
	
	@RequestMapping(value = { "/editCategory" }, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.NEVER)
	public String categorySave(Model model, //
			@ModelAttribute("categoryForm") CategoryInfo categoryInfo,
			BindingResult result,
			final RedirectAttributes redirectAttributes){
		
		if (result.hasErrors()) {
			return "editCategory";
		}
		
		categoryDAO.saveCategory(categoryInfo);
		
		return "redirect:/";
	}

	// Day la POST (Khi người dùng Submit, de save Product).
	// POST: Save product
	@RequestMapping(value = { "/product" }, method = RequestMethod.POST)
	// Trinh ngoai le: UnexpectedRollbackException.
	
	@Transactional(propagation = Propagation.NEVER)
	public String productSave(Model model, //
			@ModelAttribute("productForm") @Validated ProductInfo productInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "product";
		}
		try {
			productDAO.save(productInfo);
		} catch (Exception e) {
			// Need: Propagation.NEVER?
			// Cáº§n thiáº¿t: Propagation.NEVER?
			String message = e.getMessage();
			model.addAttribute("message", message);
			// Show product form.
			return "product";

		}
		return "redirect:/productList";
	}

	@RequestMapping(value = { "/order" }, method = RequestMethod.GET)
	public String orderView(Model model, @RequestParam("orderId") String orderId) {
		OrderInfo orderInfo = null;
		if (orderId != null) {
			orderInfo = this.orderDAO.getOrderInfo(orderId);
		}
		if (orderInfo == null) {
			return "redirect:/orderList";
		}
		List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
		orderInfo.setDetails(details);

		model.addAttribute("orderInfo", orderInfo);

		return "order";
	}

}
