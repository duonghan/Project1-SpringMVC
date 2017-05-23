<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:forEach items="${paginationProductsByCategory.list}" var="prodByCate">
	<ul>
		<li><img class="product-image"
			src="${pageContext.request.contextPath}/productImage?id=${prodByCate.id}" /></li>
		<li>Mã sản phẩm: ${prodByCate.id}</li>
		<li>Tên sản phẩm: ${prodByCate.name}</li>
		<li>Đơn giá: <fmt:formatNumber value="${prodByCate.price}" />
			VNĐ
		</li>
		<li><a
			href="${pageContext.request.contextPath}/buyProduct?id=${prodByCate.id}">
				Mua</a></li>

		<!-- For Admin edit Product -->
		<security:authorize access="hasRole('ROLE_ADMIN')">
			<li><a style="color: red;"
				href="${pageContext.request.contextPath}/product?id=${prodByCate.id}">
					Chỉnh sửa</a></li>
		</security:authorize>
	</ul>

</c:forEach>

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