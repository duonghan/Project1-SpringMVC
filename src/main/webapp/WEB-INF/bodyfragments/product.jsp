<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${not empty errorMessage }">
	<div>${errorMessage}</div>
</c:if>

<!-- Day la trang SP. Khi nguoi dung nhan SAVE ==>> No chuen toi /product. Với POST -->
<!-- Vay GET duoc the hien o cho nao a, cứ gõ 1 trang bat ky ==> No la GET, 

 POST thuong duoc chi dinh trong <form> thẻ form...==> VÍ dụ:
 vậy đói với trang này là bao gồm cả POST và get
 POSt khi mình SUBmit còn get là hiển thị trang à a
 dung vay

Mọi thao tac cua ngươi dung deu la GET.

Còn khi người ta Click vao Form nhập dữ liệu, .... Rồi nhấn Subit
nếu form đó chỉ rõ method = POST thì nó sẽ gọi tới trang dc submit với POST!!
Người dùng ko tự tạo ra POST dc, mà do form này quết đinh
Mọi thaoo tac ví dụ gõ lên trình dueetj: http://dantri.com ==> Đều là GET!!

vậy giiar sử khi e k có POST mà vẫn submit thì có đc k a ==> Lúc đó nó sẽ gọi tới hàm GET.
 -->
 <!-- còn cái này a, cái này cần thiết cho mục đích các form có upload gì đó
 Trong bài này là upload ảnh. Có thể xem thêm trong ví dụ sau:
 OK A 
 Neu khong co cai nay ==> Ko the upload, vi no khong biet cach ma hoa du lieu binary ==> thanh text
 de chuyen di, cái này nói với trình duyệt chuyển dữ liệu ảnh thành text, đính kèm vào trong 
 POST để gửi đi. Vì các dữ liệu đính kèm thường rất lớn. Chỉ có khác biệt vậy
 Nếu là form thông thường, ko liên quan upload, ko cần chỉ định kiểu mã hóa này.
 ok a ạ
  -->
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
