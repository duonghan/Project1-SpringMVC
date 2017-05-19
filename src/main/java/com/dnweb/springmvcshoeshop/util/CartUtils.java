package com.dnweb.springmvcshoeshop.util;

import javax.servlet.http.HttpServletRequest;

import com.dnweb.springmvcshoeshop.model.CartInfo;

public class CartUtils {
	// Thông tin các mặt hàng đã mua, được lưu trữ trong Session.
	public static CartInfo getCartInSession(HttpServletRequest request) {

		// Thông tin giỏ hàng có thể đã lưu vào trong Session trước đó.
		CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");

		// Nếu chưa tạo giỏ hàng, tạo nó.
		if (cartInfo == null) {
			cartInfo = new CartInfo();

			// Và lưu vào trong session.
			request.getSession().setAttribute("myCart", cartInfo);
		}

		return cartInfo;
	}

	public static void removeCartInSession(HttpServletRequest request) {
		request.getSession().removeAttribute("myCart");
	}

	public static void storeLastOrderedCartInSession(HttpServletRequest request, CartInfo cartInfo) {
		request.getSession().setAttribute("lastOrderedCart", cartInfo);
	}

	// Sau khi người dùng mua xong 1 đơn hàng ==> Nó lưu cái đơn hàng cuối cùng
	// Vào session, 
	// Giúp người dùng có thể xem lại đơn hàng cuối cùng đã mua. Vậy thôi.
	// Neu người dùng chưa mua và ththanh toán đơn hàng nào trong session đó
	///==> Cai nay null.
	public static CartInfo getLastOrderedCartInSession(HttpServletRequest request) {
		return (CartInfo) request.getSession().getAttribute("lastOrderedCart");
	}
}
