<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="login-container">

	<h3>Đăng ký tài khoản</h3>
	<br>

	<form method="POST"
		action="${pageContext.request.contextPath}/j_spring_security_check">

		<form:form method="POST" modelAttribute="accountForm"
			action="${pageContext.request.contextPath}/signup">


			<!-- Form Name -->
			<h1>Đăng ký</h1>

			<table>
				<tr>
					<td>Họ tên *</td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name" /></td>
				</tr>
				<tr>
					<td>Email *</td>
					<td><input name="email" /></td>
					<td><form:errors path="email" /></td>
				</tr>

				<tr>
					<td>Tài khoản *</td>
					<td><input name="userName" /></td>
					<td><form:errors path="userName" /></td>
				</tr>

				<tr>
					<td>Mật khẩu *</td>
					<td><input type="password" name="password" /></td>
					<td><form:errors path="password" /></td>
				</tr>
				<tr>
					<td>Số điện thoại</td>
					<td><input name="phone" /></td>
					<td><form:errors path="phone" /></td>
				</tr>
				<tr>
					<td>Địa chỉ</td>
					<td><input name="address" /></td>
					<td><form:errors path="address" /></td>
				</tr>
				<tr>
					<td>Giới tính</td>
					<td><input name="gender" /></td>
					<td><form:errors path="phone" /></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="Đăng ký" /></td>
				</tr>
			</table>

		</form:form>
	</form>

	<span class="error-message">${errorMessage }</span>

</div>
