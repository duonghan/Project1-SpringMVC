<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="CL1">
	<strong>DANH MỤC SẢN PHẨM</strong>
</div>
<div class="CL2">

	<form:form>
		<ul>
			<c:forEach var="cateInfo" items="${listCategory}">
				<li><a
					href="${pageContext.request.contextPath}/category?id=${cateInfo.id}">
						Giầy thể thao ${cateInfo.name}</a>
						
				<!-- Can sua chua doan nay -->
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<ul class="CL3">
	            	<li><a style="color: red;" href="${pageContext.request.contextPath}/category/edit?id=${cateInfo.id}">Chỉnh sửa</a></li>
	            	<li><a style="color: red;" href="${pageContext.request.contextPath}/category/delete?id=${cateInfo.id}">Xóa</a></li>
	         		</ul>
				</security:authorize> 
				</li>
			</c:forEach>
			<security:authorize access="hasRole('ROLE_ADMIN')">
				<li><a style="color: red;"
					href="${pageContext.request.contextPath}/category/edit"> Thêm danh mục</a></li>
			</security:authorize>
		</ul>
	</form:form>
</div>
<img src="${pageContext.request.contextPath}/images/logo2.png">
<div class="CL1">
	<strong>HỖ TRỢ TRỰC TUYẾN</strong>
</div>
<a href="#"><img
	src="${pageContext.request.contextPath}/images/logo14.png" alt=""></a>
<div class="CL4">
	<img src="${pageContext.request.contextPath}/images/L1.jpg" alt="">
	<img src="${pageContext.request.contextPath}/images/L2.jpg" alt="">
	<img src="${pageContext.request.contextPath}/images/L3.jpg" alt="">
	<img src="${pageContext.request.contextPath}/images/L4.jpg" alt="">
	<img src="${pageContext.request.contextPath}/images/L5.jpg" alt="">
	<img src="${pageContext.request.contextPath}/images/L6.jpg" alt="">
	<img src="${pageContext.request.contextPath}/images/L7.jpg" alt="">
	<img src="${pageContext.request.contextPath}/images/L8.jpg" alt="">
	<img src="${pageContext.request.contextPath}/images/L9.jpg" alt="">
</div>
<!--.Cleft-->