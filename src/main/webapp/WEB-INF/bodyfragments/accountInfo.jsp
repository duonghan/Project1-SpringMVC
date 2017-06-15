<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="account-container">
	<form:form method="POST" modelAttribute="loginedUser">
        <div class="CRAC0">
        <br>
        <br>
		<div class="nameAC"><strong>THÔNG TIN TÀI KHOẢN: </strong> ${pageContext.request.userPrincipal.name}</div>
		<br>
        <div class="CRAC1">
			<p><strong>Họ Tên:</strong> ${loginedUser.name}</p>
			<p><strong>Email:</strong> ${loginedUser.email}</p>
			<p><strong>Địa chỉ:</strong> ${loginedUser.address}</p>
			<p><strong>Số điện thoại:</strong> ${loginedUser.phone}</p>
			<p><strong>Giới tính:</strong> ${loginedUser.gender}</p>
		<a href="${pageContext.request.contextPath}/profile/edit">Chỉnh sửa thông tin cá nhân</a>
		</div>
		</div>
		</form:form>
</div>