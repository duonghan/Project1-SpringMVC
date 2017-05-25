<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<hr style="height: 1px; background: #ccc">
<div class="CR1">

	<div class="title">
		<p>
			<strong> DANH SÁCH SẢN PHẨM</strong>
		</p>
	</div>
	<c:forEach items="${paginationProductsByCategory.list}"
		var="prodByCate">

		<div class="sp">
			<img
				src="${pageContext.request.contextPath}/productImage?id=${prodByCate.id}"
				alt="${prodByCate.name}">

			<div class="namesp">
				<a
					href="${pageContext.request.contextPath}/productImage?id=${prodByCate.id}">${prodByCate.name}</a>
			</div>

			<c:if test="${prodByCate.discount == 0.0}">
			<a style="margin-left: 50px; color: red;"> <fmt:formatNumber
				value="${prodByCate.price} " type="number" pattern="###,###,### VNĐ"
				maxFractionDigits="0" /></a>
			</c:if>
		
			<c:if test="${prodByCate.discount != 0.0 }">
				<div class="sale">
					<a><fmt:formatNumber
					value="${prodByCate.price}" type="number" pattern="###,###,### VNĐ" /></a> 
					<span style="color: red;">
					<fmt:formatNumber value="${prodByCate.price * (1 - prodByCate.discount)}" 
					type="number" pattern="###,###,### VNĐ"
					maxFractionDigits="0"/></span>
				</div>
			</c:if>

			<div class="button">
				<a
					href="${pageContext.request.contextPath}/buyProduct?id=${prodByCate.id}">Mua
					hàng</a>
			</div>

			<!-- For Admin edit Product -->
			<security:authorize access="hasRole('ROLE_ADMIN')">
				<div class="button">
					<a style="color: red;"
						href="${pageContext.request.contextPath}/product/edit?id=${prodByCate.id}">
						Chỉnh sửa</a>
				</div>
			</security:authorize>
		</div>
	</c:forEach>
	<br />
</div>


<c:if test="${paginationProducts.totalPages > 1}">
	<div class="page-navigator">
		<c:forEach items="${paginationProducts.navigationPages}" var="page">
			<c:if test="${page != -1 }">
				<a href="productList?page=${page}" class="nav-item">${page}</a>
			</c:if>
			<c:if test="${page == -1 }">
				<span class="nav-item"> ... </span>
			</c:if>
		</c:forEach>

	</div>
</c:if>