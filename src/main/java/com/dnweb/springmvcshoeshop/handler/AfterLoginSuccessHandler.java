package com.dnweb.springmvcshoeshop.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dnweb.springmvcshoeshop.dao.AccountDAO;
import com.dnweb.springmvcshoeshop.model.CustomerInfo;
import com.dnweb.springmvcshoeshop.util.UserUtils;

// Ngay sau khi login thanh cong, Handler nay duoc goi.
// Can dang ky Handler nay voi Security.
@Component
public class AfterLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private AccountDAO accountDAO;
	
	// Sau khi login xong, chuyen huong tiep
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		System.out.println("???? Call in SuccesssHander:");
		
		UserDetails userDetails =	(UserDetails) authentication.getPrincipal();
		
		String userName= userDetails.getUsername();
		
		//  Load tu DB
        CustomerInfo customerInfo = accountDAO.findCustomerInfo(userName);
		
		// Va luu vao session
		UserUtils.saveLoginedUser(request, customerInfo);
		
		// Chuyen sang /profile  (Tu chuyen huong sang trang nao do)..
		redirectStrategy.sendRedirect(request, response, "/profile");
	}

}
