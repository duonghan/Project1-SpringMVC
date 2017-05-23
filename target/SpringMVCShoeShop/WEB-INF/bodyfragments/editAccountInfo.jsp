<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<div class="page-title">Cập nhật thông tin người dùng</div>

<form:form method="POST" modelAttribute="userAccountForm"
	action="${pageContext.request.contextPath}/editAccountInfo">

	<table>
		<tr>
			<td>Họ tên*</td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" class="error-message" /></td>
		</tr>

		<tr>
			<td>Email *</td>
			<td><form:input path="email" /></td>
			<td><form:errors path="email" class="error-message" /></td>
		</tr>

		<tr>
			<td>Số điện thoại *</td>
			<td><form:input path="phone" /></td>
			<td><form:errors path="phone" class="error-message" /></td>
		</tr>

		<tr>
			<td>Địa chỉ *</td>
			<td><form:input path="address" /></td>
			<td><form:errors path="address" class="error-message" /></td>
		</tr>

		<tr>
			<td>&nbsp;</td>
			<td><input type="submit" value="Submit" /> <input type="reset"
				value="Reset" /></td>
		</tr>
	</table>

</form:form>