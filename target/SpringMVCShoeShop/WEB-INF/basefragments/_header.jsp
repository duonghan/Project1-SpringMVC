<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<div class="header">
	<div class="H0">
		<div class="Hleft">
			<img src="${pageContext.request.contextPath}/images/logo.png"
				height="50" width="60" /> <img
				src="${pageContext.request.contextPath}/images/logo.png" height="50"
				width="60" /> <img
				src="${pageContext.request.contextPath}/images/logo.png" height="50"
				width="60" />
			<div class="logo2">
				<strong>MY SHOE SHOP</strong>
			</div>
		</div>
		<div class="Hright">
			<ul>
				<c:if test="${pageContext.request.userPrincipal.name != null}">
					<li><img style="margin-right: 10px;"
						src="${pageContext.request.contextPath}/images/icon1.png"
						height="15" width="15" /> <a
						href="${pageContext.request.contextPath}/accountInfo">
							${pageContext.request.userPrincipal.name} </a></li>

					<li><a href="${pageContext.request.contextPath}/logout">Đăng
							xuất</a></li>
				</c:if>

				<c:if test="${pageContext.request.userPrincipal.name == null}">
					<li><img style="margin-right: 10px;"
						src="${pageContext.request.contextPath}/images/icon1.png"
						height="15" width="15" /><a class="L1" href="login"
						target="_self">Đăng nhập</a></li>
					<li>
					<li><img style="margin-right: 10px;"
						src="${pageContext.request.contextPath}/images/icon2.png"
						height="15" width="15" /><a class="L2"
						href="${pageContext.request.contextPath}/signup" target="_self">Đăng
							ký</a></li>
				</c:if>


				<li>
				<li><img style="margin-right: 10px;"
					src="${pageContext.request.contextPath}/images/icon3.png"
					height="15" width="15" /><a class="L3"
					href="${pageContext.request.contextPath}/shoppingCart"
					target="_self">Giỏ hàng</a></li>
			</ul>
		</div>
	</div>
	<div class="menu">
		<li><a href="${pageContext.request.contextPath}/"><strong>Trang chủ</strong></a></li>
		<li><a href="${pageContext.request.contextPath}/about"><strong>Giới
					thiệu</strong></a></li>
		<li><a href="${pageContext.request.contextPath}/productList"><strong>Sản phẩm </strong></a></li>
		<li><a href="${pageContext.request.contextPath}/contactus""><strong>Liên
					hệ</strong></a></li>
		<security:authorize
			access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
			<li><a href="${pageContext.request.contextPath}/orderList"><strong>
					Danh sách đơn hàng </strong></a></li>
		</security:authorize>

		<security:authorize access="hasRole('ROLE_ADMIN')">
			<li><a href="${pageContext.request.contextPath}/product"><strong>
					Thay đổi sản phẩm </strong></a></li>
		</security:authorize>
		
		<div class="timkiem">
			<input class="input" type="text" placeholder="Tìm kiếm...">
			<button>
				<img src="${pageContext.request.contextPath}/images/icon6.png"
					height="15" width="15">
			</button>
		</div>
	</div>

	<!--.slider-->
	<div class="H1">
		<img id="img" src="${pageContext.request.contextPath}/images/img1.jpg"
			alt="" />
		<div id="H1L">
			<img class="left" onClick="slide(-1)"
				src="${pageContext.request.contextPath}/images/icon4.png">
		</div>
		<div id="H1R">
			<img class="right" onClick="slide(1)"
				src="${pageContext.request.contextPath}/images/icon5.png">
		</div>
	</div>
</div>
