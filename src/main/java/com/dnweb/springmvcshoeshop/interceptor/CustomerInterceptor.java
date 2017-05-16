package com.dnweb.springmvcshoeshop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dnweb.springmvcshoeshop.dao.AccountDAO;

@Component
public class CustomerInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	AccountDAO accountDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		System.out.println("Truy cap vao customer Interceptor>>>>>>>>>>" + accountDAO);
		
		modelAndView.addObject("customerForm", accountDAO.findCustomerInfo(request.getUserPrincipal().getName()));
	}
	
	
}
