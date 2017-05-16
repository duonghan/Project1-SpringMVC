<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:forEach items="paginationProductsByCategory" var="prodByCate">
	<ul>
		<li><img class="product-image"
			src="${pageContext.request.contextPath}/productImage?id=${prodInfo.id}" /></li>
		<li>Mã sản phẩm: ${prodInfo.id}</li>
		<li>Tên sản phẩm: ${prodInfo.name}</li>
		<li>Đơn giá: <fmt:formatNumber value="${prodInfo.price}" />
			VNĐ
		</li>
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

</c:forEach>