package com.dnweb.springmvcshoeshop.util;

import javax.servlet.http.HttpServletRequest;

import com.dnweb.springmvcshoeshop.model.CustomerInfo;

public class UserUtils {
	
	public static void saveLoginedUser(HttpServletRequest request, CustomerInfo customerInfo){
		// Hay can than trung lap customerInfo
		request.getSession().setAttribute("loginedUser", customerInfo);
	}
	
	public static CustomerInfo getLoginedUserFromSession(HttpServletRequest request){
		CustomerInfo customerInfo = (CustomerInfo) request.getSession().getAttribute("loginedUser");
		
		return customerInfo;
	}
}
