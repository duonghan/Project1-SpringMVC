<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="vi_VI" scope="session" />

<div class="page-title">Xác nhận thông tin đơn hàng</div>



<div class="customer-info-container">
	<h3>Thông tin khách hàng:</h3>
	<ul>
		<li>Tên: ${myCart.customerInfo.name}</li>
		<li>Email: ${myCart.customerInfo.email}</li>
		<li>Số điện thoại: ${myCart.customerInfo.phone}</li>
		<li>Địa chỉ: ${myCart.customerInfo.address}</li>
	</ul>
	<h3>Chi tiết giỏ hàng:</h3>
	<ul>
		<li>Số lượng : ${myCart.quantityTotal}</li>
		<li>Tổng : <span> <fmt:formatNumber
					value="${myCart.amountTotal}" type="number" pattern="###,###,### VNĐ" maxFractionDigits="0"/>
		</span></li>
	</ul>
</div>

<form method="POST"
	action="${pageContext.request.contextPath}/shoppingCart/confim">

	<!-- Edit Cart -->
	<a class="navi-item"
		href="${pageContext.request.contextPath}/shoppingCart">Thay đổi</a>

	<input type="submit" value="Gửi" class="button-send-sc" />
</form>

<div class="container">

	<c:forEach items="${myCart.cartLines}" var="cartLineInfo">
		<div class="product-preview-container">
			<ul>
				<li><img class="sp"
					src="${pageContext.request.contextPath}/productImage?id=${cartLineInfo.productInfo.id}" /></li>
				<li>Mã sản phẩm: ${cartLineInfo.productInfo.id} <input type="hidden"
					name="id" value="${cartLineInfo.productInfo.id}" />
				</li>
				<li>Tên sản phẩm: ${cartLineInfo.productInfo.name}</li>
				<li>Đơn giá: <span class="price"> <fmt:formatNumber
							value="${cartLineInfo.productInfo.price}" type="currency" />
				</span>
				</li>
				<c:if test="${cartLineInfo.productInfo.discount > 0.0}">
					<li>Giảm giá: <span><fmt:formatNumber
							value="${cartLineInfo.productInfo.discount *100}" type="percent" /></span>
				</li>
				</c:if>
				<li>Số lượng: ${cartLineInfo.quantity}</li>
				<li>Tổng : <span class="subtotal"> <fmt:formatNumber
							value="${cartLineInfo.amount}" type="currency" />
				</span>
				</li>
			</ul>
		</div>
	</c:forEach>

</div>
