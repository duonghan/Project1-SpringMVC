package com.dnweb.springmvcshoeshop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dnweb.springmvcshoeshop.dao.CategoryDAO;

// Truoc khi request den Controller no se di qua Interceptor
// Vi vay set dat gi do trong Intercepor.
// Dang ky Interceptor voi he thong....
// Chu ý them: MenuInterceptor cần phải là 1 BEAN (THì nó mới tiêm được)
// Vay gio cai nay la 1 BEAN
@Component
public class MenuInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private CategoryDAO categoryDAO;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//long startTime = System.currentTimeMillis();
		//System.out.println("\n-------- LogInterception.preHandle --- ");
		//System.out.println("Request URL: " + request.getRequestURL());
		//System.out.println("Start Time: " + System.currentTimeMillis());

		//request.setAttribute("startTime", startTime);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//System.out.println("\n-------- LogInterception.postHandle --- ");
		//System.out.println("Request URL: " + request.getRequestURL());

		// Ở đây, bạn có thể add các attribute vào modelAndView
		// Và sử dụng nó trong các View (jsp,..)
		
		
		 // Tam rào lại, dang test gọi hàm??
		//e dang test cái interceptor trc a à
		// Day se la du lieu cho _menu.jsp
		System.out.println("Interceotpr >>>>>>>>>>>>>>>>>>>>>>>> :"+ categoryDAO );
		 modelAndView.addObject("listCategory", categoryDAO.getAllCategory());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//System.out.println("\n-------- LogInterception.afterCompletion --- ");

		//long startTime = (Long) request.getAttribute("startTime");
		//long endTime = System.currentTimeMillis();
		//System.out.println("Request URL: " + request.get RequestURL());
		//System.out.println("End Time: " + endTime);

		//System.out.println("Time Taken: " + (endTime - startTime));
	}

}