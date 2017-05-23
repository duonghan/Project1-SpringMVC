<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="login-container" valign="top">

	<h3>Đăng ký tài khoản</h3>
	<br>

<!-- Thua cai form (Bo di) -->


<!--  Day chinh la the form Spring se tao ra tren HTML -->
		<form:form method="POST" modelAttribute="accountForm"
			action="${pageContext.request.contextPath}/signup">


			<!-- Form Name -->
			<h1>Đăng ký</h1>
            <!-- Nen dat theo cach cua Spring -->
			<table>
				<tr>
					<td>Họ tên *</td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name" /></td>
				</tr>
				<tr>
					<td>Email *</td>
					<td><form:input path="email" /></td> 
					<td><form:errors path="email" /></td>
				</tr>

				<tr>
					<td>Tài khoản *</td>
					<td><form:input name="username" /></td>
					<td><form:errors path="username" /></td>
				</tr>

				<tr>
					<td>Mật khẩu *</td>
					<td><form:input type="password" name="password" /></td>
					<td><form:errors path="password" /></td>
				</tr>
				<tr>
					<td>Số điện thoại</td>
					<td><form:input name="phone" /></td>
					<td><form:errors path="phone" /></td>
				</tr>
				<tr>
					<td>Địa chỉ</td>
					<td><form:input name="address" /></td>
					<td><form:errors path="address" /></td>
				</tr>
				<tr>
					<td>Giới tính</td>
					<td><form:input name="gender" /></td>
					<td><form:errors path="phone" /></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td><form:input type="submit" value="Đăng ký" /></td>
				</tr>
			</table>

		</form:form>


	<span class="error-message">${errorMessage }</span>

</div>
