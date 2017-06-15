<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>${erros }</h2>
<div class="Cright">
<div class="CREP0">
<br>
<br> 
<a style="font-size: 25px; color: #ff0000; padding: 50px;"><strong>CẬP NHẬT THÔNG TIN NGƯỜI DÙNG</strong></a>
<br>
<br>
<br>
<form:form method="POST" 
	modelAttribute="userAccountForm"
	action="${pageContext.request.contextPath}/profile/edit">

		
		<div class="CREP1">
		    <a class="nameEP"><strong>Họ tên: </strong><span style="color: #ff0000;">*</span></a>
			<div class="form-inputEP">
			  <form:input path="name" />
			  <form:errors path="name" />
			</div>
			
			<a class="nameEP"><strong>Email: </strong><span style="color: #ff0000;">*</span></a>
			<div class="form-inputEP">
			<form:input path="email" />
			<form:errors path="email"/>
			</div>


			<a class="nameEP"><strong>Giới tính:</strong></a>

					<div >
						<form:select path="gender" class="gt">
							<form:option value="" label="- Chọn một -" />
							<form:options items="${genderMap}" />
						</form:select>
					</div>
		
			<a class="nameEP" ><strong>Số điện thoại: </strong><span style="color: #ff0000;">*</span></a>
			<div class="form-inputEP">
			<form:input path="phone" />
			<form:errors path="phone"/>
			</div>
		

		
			<a class="nameEP"><strong>Địa chỉ: </strong><span style="color: #ff0000;">*</span></a>
			<div class="form-inputEP">
			<form:input path="address" />
			<form:errors path="address"/>
			</div>
		</div>
        <div class="CREP2">
	      <div class="buttonEP1">
			 <a href="${pageContext.request.contextPath}/login">Gửi</a>
		  </div>
		  <div class="buttonEP2">
			 <a href="${pageContext.request.contextPath}/login">Reset</a>
		  </div>
	    </div>
</form:form>
</div>
</div>