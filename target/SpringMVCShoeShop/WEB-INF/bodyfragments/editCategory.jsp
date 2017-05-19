<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form modelAttribute="categoryForm" method="POST"
	enctype="multipart/form-data">
	<table style="text-align: left;">
		<tr>
			<td>Mã danh mục *</td>
			<td style="color: red;"><c:if test="${not empty categoryForm.id}">
					<form:hidden path="id" /> ${categoryForm.id}
                </c:if> 
                <c:if test="${empty categoryForm.id}">
					<form:input path="id" />
					<form:hidden path="newCategory" />
				</c:if></td>
			<td><form:errors path="id" class="error-message" /></td>
		</tr>

		<tr>
			<td>Tên danh mục *</td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" class="error-message" /></td>
		</tr>

		<tr>
			<td>&nbsp;</td>
			<td><input type="submit" value="Gửi" /> <input type="reset"
				value="Reset" /></td>
		</tr>
	</table>
</form:form>
