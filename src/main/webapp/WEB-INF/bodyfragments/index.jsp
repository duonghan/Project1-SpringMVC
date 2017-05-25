<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
	
<div class="CR1">
	<div class="title">
		<p>
			<strong> MỚI CẬP NHẬT</strong>
		</p>
	</div>

	<br />
	<!-- New Product -->
	
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
				<a style="color: red;" href="${pageContext.request.contextPath}/product/edit?id=${prodInfo.id}">
						Chỉnh sửa</a>
			</div>
			
			<div class="button">
				<a href="${pageContext.request.contextPath}/product/delete?id=${prodInfo.id}">Xóa</a>
			</div>
			
			</security:authorize>
		</div>
	</c:forEach>
	
</div>


<!--NỔI BẬT-->
<hr style="height: 1px; background: #ccc">
<div class="CR1">
	<div class="title">
		<p>
			<strong> SẢN PHẨM BÁN CHẠY</strong>
		</p>
	</div>

	<br />
	
	<c:forEach items="${listPopuler.list}" var="prodInfo">
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
				<a style="color: red;" href="${pageContext.request.contextPath}/product/edit?id=${prodInfo.id}">
						Chỉnh sửa</a>
			</div>
			
			<div class="button">
				<a href="${pageContext.request.contextPath}/product/delete?id=${prodInfo.id}">Xóa</a>
			</div>
			
			</security:authorize>
		</div>
	</c:forEach>
</div>

<!-- Sale Product -->
<hr style="height: 1px; background: #ccc">
<div class="CR1">
	<div class="title">
		<p>
			<strong> SALE</strong>
		</p>
	</div>
	<br />
	
	<c:forEach items="${listSales.list}" var="prodInfo">
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
				<a style="color: red;" href="${pageContext.request.contextPath}/product/edit?id=${prodInfo.id}">
						Chỉnh sửa</a>
			</div>
			
			<div class="button">
				<a href="${pageContext.request.contextPath}/product/delete?id=${prodInfo.id}">Xóa</a>
			</div>
			
			</security:authorize>
		</div>
	</c:forEach>
	
</div>

<!-- Link -->
<div class="CR2">
	<div class="logo">
		<a style="left: 5px;" href="http://www.nike.com/vn/en_gb/"><img src="images/logo3.jpg"
			alt=""></a>
	</div>
	<div class="logo">
		<a href="http://www.adidas.com.vn/"><img src="images/logo4.jpg" alt=""></a>
	</div>
	<div class="logo">
		<a href="http://www.converse.com/"><img src="images/logo5.png" alt=""></a>
	</div>
	<div class="logo">
		<a href="https://www.vans.com/"><img src="images/logo6.jpg" alt=""></a>
	</div>
</div>
</div>