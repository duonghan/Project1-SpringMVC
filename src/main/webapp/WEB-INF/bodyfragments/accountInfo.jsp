<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="account-container">
	<form:form method="POST" modelAttribute="loginedUser">

		<h1>Thông tin tài khoản ${pageContext.request.userPrincipal.name}</h1>


		<ul>
			<li>Họ Tên: ${loginedUser.name}</li>
			<li>Email: ${loginedUser.email}</li>
			<li>Địa chỉ: ${loginedUser.address}</li>
			<li>Số điện thoại: ${loginedUser.phone}</li>
			<li>Giới tính: ${loginedUser.gender}</li>
			
		</ul>
		<a href="${pageContext.request.contextPath}/profile/edit">Chỉnh sửa thông tin cá nhân</a>
		</form:form>
</div>