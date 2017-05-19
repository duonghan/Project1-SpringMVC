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
// Cáº§n thiáº¿t cho Hibernate Transaction.
@Transactional
// Need to use RedirectAttributes
// Cáº§n thiáº¿t Ä‘á»ƒ sá»­ dá»¥ng RedirectAttributes
@EnableWebMvc
public class AdminController {
	
	// Day la cĂ¡c nhiem vu cua Admin
	// Admin dang nhap de them SP, nháº­p giĂ¡ sáº£n pháº©m,....
	// CĂ²n mainController liĂªn quan tá»›i hiá»ƒn thá»‹ vĂ  bĂ¡n hĂ ng.
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
			// Sá»­ dá»¥ng cho upload Image.
			dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		}
	}

	// Day la trang Hien thi de login.
	// GET: Show Login Page
	// GET: Hiá»ƒn thá»‹ trang login
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(Model model) {

		
		return "login";
	}
	
	// Ham nay dau da duoc goi o dau??
	// Cai nay cho nao goi no???????
	
	//chua a 
	
	public void saveAcccountSession(HttpServletRequest request){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		CustomerInfo customerInfo = this.accountDAO.findCustomerInfo(userDetails.getUsername());
		UserUtils.saveLoginedUser(request, customerInfo);
	}
	
	@RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
	public String accountInfo(HttpServletRequest request,Model model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Lay ra tu Sesssion.
		CustomerInfo customerInfo = UserUtils.getLoginedUserFromSession(request);
		 
		System.out.println("customerInfo:" + customerInfo);
		
		model.addAttribute("loginedUser", customerInfo);
		
		return "accountInfo";
	}
	
	
	//Hien thi trang chinh sua thong tin tai hoan nguoi dung
	@RequestMapping(value = {"/editAccountInfo"}, method = RequestMethod.GET)
	public String editAccount(HttpServletRequest request, Model model){
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (userDetails == null) {
			return "redirect:/login";
		}
		
		AccountInfo accountInfo = accountDAO.findAccountInfo(userDetails.getUsername());
		
		if (accountInfo == null) {
			accountInfo = new AccountInfo();
		}
		
		model.addAttribute("userAccountForm", accountInfo);
		return "editAccountInfo";
	}
	
	//Luu thay doi thong tin nguoi dung
	@RequestMapping(value = {"/editAccountInfo"}, method = RequestMethod.POST)
	public String editAccountInfo(Model model,
			@ModelAttribute("userAccountForm") @Validated AccountInfo accountInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes){
		
		if (result.hasErrors()) {
			return "editAccountInfo";
		}
		try {
			accountDAO.saveAccount(accountInfo);
			
		} catch (Exception e) {
			String message = e.getMessage();
			model.addAttribute("message", message);
			
			// Show edit account Info page.
			return "editAccountInfo";

		}
		return "redirect:/accountInfo";
		
		
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
