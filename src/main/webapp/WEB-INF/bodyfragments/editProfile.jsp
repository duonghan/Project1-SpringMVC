<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>${erros }</h2>

<div class="page-title">Cập nhật thông tin người dùng</div>

<form:form method="POST" 
	modelAttribute="userAccountForm"
	action="${pageContext.request.contextPath}/profile/edit">

	<table>
		<tr>
		<c:if test="${not empty userAccountForm.username }">
			<td><form:hidden path="username"/></td>
			<td><form:hidden path="password"/></td>
			<td><form:hidden path="role"/></td>
			<td><form:hidden path="active"/></td>
		</c:if>
		</tr>
		
		<tr>
			<td>Họ tên*</td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" /></td>
		</tr>

		<tr>
			<td>Email *</td>
			<td><form:input path="email" /></td>
			<td><form:errors path="email"/></td>
		</tr>

		<tr>
			<td>Giới tính *</td>
			<td>
			<form:select path="gender">
                <form:option value="" label="- Chọn một -" />
                <form:options items="${genderMap}" />
            </form:select>
            </td>
			
			<td><form:errors path="gender"/></td>
		</tr>
		
		<tr>
			<td>Số điện thoại *</td>
			<td><form:input path="phone" /></td>
			<td><form:errors path="phone"/></td>
		</tr>

		<tr>
			<td>Địa chỉ *</td>
			<td><form:input path="address" /></td>
			<td><form:errors path="address"/></td>
		</tr>

		<tr>
			<td><input type="submit" value="Gửi" /> <input type="reset"
				value="Reset" /></td>
		</tr>
	</table>

</form:form>