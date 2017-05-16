<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<fmt:setLocale value="en_US" scope="session" />

<div class="page-title">Danh sách sản phẩm</div>

<c:forEach items="${paginationProducts.list}" var="prodInfo">
	<div class="product-preview-container">
		<ul>
			<li><img class="product-image"
				src="${pageContext.request.contextPath}/productImage?id=${prodInfo.id}" /></li>
			<li>Mã sản phẩm: ${prodInfo.id}</li>
			<li>Tên sản phẩm: ${prodInfo.name}</li>
			<li>Đơn giá: <fmt:formatNumber value="${prodInfo.price}"
					 /> VNĐ</li>
			<li><a
				href="${pageContext.request.contextPath}/buyProduct?id=${prodInfo.id}">
					Mua</a></li>
					
			<!-- For Admin edit Product -->
			<security:authorize access="hasRole('ROLE_ADMIN')">
				<li><a style="color: red;"
					href="${pageContext.request.contextPath}/product?id=${prodInfo.id}">
						Chỉnh sửa</a></li>
			</security:authorize>
		</ul>
	</div>

</c:forEach>
<br />


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
