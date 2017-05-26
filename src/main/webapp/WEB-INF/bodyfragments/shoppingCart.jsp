<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<fmt:setLocale value="vi_VI" scope="session" />

<div class="page-title">Giỏ hàng</div>

<c:if test="${empty cartForm or empty cartForm.cartLines}">
	<h2>Không có sản phẩm nào trong giỏ hàng</h2>
	<a href="${pageContext.request.contextPath}/productList">Xem danh sách sản phẩm</a>
</c:if>

<c:if test="${not empty cartForm and not empty cartForm.cartLines}">
	<form:form method="POST" modelAttribute="cartForm"
		action="${pageContext.request.contextPath}/shoppingCart">

		<c:forEach items="${cartForm.cartLines}" var="cartLineInfo"
			varStatus="varStatus">
			<div class="product-preview-container">
				<ul>
					<li><img class="product-image"
						src="${pageContext.request.contextPath}/productImage?id=${cartLineInfo.productInfo.id}" />
					</li>
					<li>Mã sản phẩm: ${cartLineInfo.productInfo.id} <form:hidden
							path="cartLines[${varStatus.index}].productInfo.id" />

					</li>
					<li>Tên sản phẩm: ${cartLineInfo.productInfo.name}</li>
					<li>Đơn giá: <span class="price"> <fmt:formatNumber
								value="${cartLineInfo.productInfo.price}" type="number" pattern="###,###,### VNĐ" maxFractionDigits="0"/>

					</span></li>
					<li>Số lượng: <form:input
							path="cartLines[${varStatus.index}].quantity" /></li>
					<li>Tổng: <span class="subtotal"> <fmt:formatNumber
								value="${cartLineInfo.amount}" type="number" pattern="###,###,### VNĐ" maxFractionDigits="0"/>

					</span>
					</li>
					<li><a
						href="${pageContext.request.contextPath}/shoppingCart/remove/product?id=${cartLineInfo.productInfo.id}">
							Xóa </a></li>
				</ul>
			</div>
		</c:forEach>
		
		<div style="clear: both"></div>
		<input class="button-update-sc" type="submit" value="Cập nhật số lượng" />
		
		<a class="navi-item"
			href="${pageContext.request.contextPath}/shoppingCart/confirm">Đặt hàng</a>
		
		<a class="navi-item"
			href="${pageContext.request.contextPath}/productList">Tiếp tục mua hàng</a>
	</form:form>


</c:if>
