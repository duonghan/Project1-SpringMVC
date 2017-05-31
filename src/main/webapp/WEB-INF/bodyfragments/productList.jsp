<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<fmt:setLocale value="vi_VI" scope="session" />

<hr style="height: 1px; background: #ccc">
<div class="CR1">

<div class="title">
		<p>
			<strong> DANH SÁCH SẢN PHẨM</strong>
		</p>
</div>
<c:forEach items="${paginationProducts.list}" var="prodInfo">

	<div class="sp">
		<img
			src="${pageContext.request.contextPath}/productImage?id=${prodInfo.id}"
			alt="${prodInfo.name}">

		<div class="namesp">
			<a
				href="${pageContext.request.contextPath}/productImage?id=${prodInfo.id}">${prodInfo.name}</a>
		</div>

		<c:if test="${prodInfo.discount == 0.0}">
			<a style="margin-left: 50px; color: red;"> <fmt:formatNumber
				value="${prodInfo.price} " type="number" pattern="###,###,### VNĐ"
				maxFractionDigits="0" /></a>
		</c:if>
		
		<c:if test="${prodInfo.discount != 0.0 }">
			<div class="sale">
				<a><fmt:formatNumber
				value="${prodInfo.price}" type="number" pattern="###,###,### VNĐ" /></a> 
				<span style="color: red;">
				<fmt:formatNumber value="${prodInfo.price * (1 - prodInfo.discount)}" 
				type="number" pattern="###,###,### VNĐ"
				maxFractionDigits="0"/></span>
			</div>
		</c:if>
		

		<div class="button">
			<a
				href="${pageContext.request.contextPath}/buyProduct?id=${prodInfo.id}">Mua
				hàng</a>
		</div>

		<!-- For Admin edit Product -->
		<security:authorize access="hasRole('ROLE_ADMIN')">
		<div class="button">
			<a href="${pageContext.request.contextPath}/product/edit?id=${prodInfo.id}">
					Chỉnh sửa</a>
		</div>
		
		<div class="button">
			<a href="${pageContext.request.contextPath}/product/delete?id=${prodInfo.id}">Xóa</a>
		</div>
			
		</security:authorize>
	</div>
</c:forEach>
<br />


<c:if test="${paginationProducts.totalPages > 1}">
	<div class="page-navigator">
		<c:forEach items="${paginationProducts.navigationPages}" var="page">
			<c:if test="${page != -1 }">
			<div>
				<a href="${pageContext.request.contextPath}/product/list?page=${page}" class="nav-item">${page}</a>
			</div>
			</c:if>
			<c:if test="${page == -1 }">
				<span class="nav-item"> ... </span>
			</c:if>
		</c:forEach>

	</div>
</c:if>

</div>
