<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${not empty errorMessage }">
	<div>${errorMessage}</div>
</c:if>
<form:form modelAttribute="productForm" method="POST"
	enctype="multipart/form-data">
	<table style="text-align: left;">
		<tr>
			<td>Mã sản phẩm *</td>
			<td style="color: red;">
				<c:if
					test="${not empty productForm.id}">
					<form:hidden path="id" /> ${productForm.id}
                </c:if>
                
                <c:if test="${empty productForm.id}">
					<form:input path="id" />
					<form:hidden path="newProduct" />
				</c:if></td>
			<td><form:errors path="id" class="error-message" /></td>
		</tr>

		<tr>
			<td>Tên sản phẩm *</td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" class="error-message" /></td>
		</tr>
		
		<tr>
			<td>Danh mục sản phẩm *</td>
			<td><form:input path="categoryId" /></td>
			<td><form:errors path="categoryId" class="error-message" /></td>
		</tr>
		
		<tr>
			<td>Mô tả sản phẩm *</td>
			<td><form:input path="description" /></td>
			<td><form:errors path="description" class="error-message" /></td>
		</tr>

		<tr>
			<td>Đơn giá *</td>
			<td><form:input path="price" /></td>
			<td><form:errors path="price" class="error-message" /></td>
		</tr>
		
		<tr>
			<td>Giảm giá *</td>
			<td><form:input path="discount" /></td>
			<td><form:errors path="discount" class="error-message" /></td>
		</tr>
		
		<tr>
			<td>Hình ảnh</td>
			<td><img
				src="${pageContext.request.contextPath}/productImage?id=${productForm.id}"
				width="100" /></td>
			<td></td>
		</tr>
		<tr>
			<td>Tải ảnh lên</td>
			<td><form:input type="file" path="fileData" /></td>
			<td></td>
		</tr>


		<tr>
			<td>&nbsp;</td>
			<td><input type="submit" value="Gửi" /> <input type="reset"
				value="Reset" /></td>
		</tr>
	</table>
</form:form>
