<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>
	<!--  Day chinh la the form Spring se tao ra tren HTML -->

	<form:form method="POST" modelAttribute="accountForm"
		action="${pageContext.request.contextPath}/signup">


		<!-- Form Name -->
		<!-- Nen dat theo cach cua Spring -->
		<!-- 
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
				<td><form:input path="username" /></td>
				<td><form:errors path="username" /></td>
			</tr>

			<tr>
				<td>Mật khẩu *</td>
				<td><form:input type="password" path="password" /></td>
				<td><form:errors path="password" /></td>
			</tr>

			<tr>
				<td>Số điện thoại</td>
				<td><form:input path="phone" /></td>
				<td><form:errors path="phone" /></td>
			</tr>

			<tr>
				<td>Địa chỉ</td>
				<td><form:input path="address" /></td>
				<td><form:errors path="address" /></td>
			</tr>

			<tr>
				<td>Giới tính</td>
				<td><form:select path="gender">
						<form:option value="" label="- Chọn một -" />
						<form:options items="${genderMap}" />
					</form:select></td>
				<td><form:errors path="gender" /></td>
			</tr>

			<tr>
				<td><input type="submit" value="Đăng ký" /></td>
			</tr>
		</table>



 -->

<div class="Cright">
    <br> <a style="font-size: 25px; color: #ff0000; padding: 100px;"><strong>ĐĂNG
				KÝ TÀI KHOẢN</strong></a>
		<hr>
			<div class="CRS0">
				<div class="CRS1">
					<br> <br>
					 <a class="loginS"><strong>SIGN UP</strong></a> <br>
					<br> <a class="nameS"><strong>Họ và tên:</strong><span
						style="color: #ff0000;">*</span></a>
					<div class="form-inputS">
						<form:input path="name" placeholder="Nguyễn Văn A" />
						<form:errors path="name" />
					</div>
					<a class="nameS"><strong>Email:</strong><span
						style="color: #ff0000;">*</span></a>
					<div class="form-inputS">
						<form:input path="email" placeholder="example@gmail.com" />
						<form:errors path="email" />
					</div>
					
					<a class="nameS"><strong>Mật khẩu:</strong><span
						style="color: #ff0000;">*</span></a>
						
					<div class="form-inputS">
						<form:input type="password" path="password" placeholder="********" />
						<form:errors path="password" />
					</div>

					<a class="nameS"><strong>Số điện thoại:</strong></a>
					<div class="form-inputS">
						<form:input path="phone" placeholder="(+84)xxxxxxxxx" />
						<form:errors path="phone" />
					</div>

					<a class="nameS"><strong>Địa chỉ:</strong></a>
					<div class="form-inputS">
						<form:input path="address" placeholder="Address" />
						<form:errors path="address" />
					</div>

					<a class="nameS"><strong>Giới tính:</strong></a>

					<div >
						<form:select path="gender" class="gt">
							<form:option value="" label="- Chọn một -" />
							<form:options items="${genderMap}" />
						</form:select>
					</div>
					<input class="button1" type="submit" value="Đăng ký" />
				</div>
				<div class="CRS3">
					<br> <br> <br> <a
						style="margin-left: 30px; font-size: 20px; color: #bf0000;"><strong><i>Bạn
								đã có tài khoản</i></strong></a> <br> <br>
					<p>
						Nếu bạn đã có tài khoản tại website của Myshoes.vn, bạn vui lòng
						Đăng nhập tài khoản<span><a class="CRS11" href="#"><i>
									My shoe shop.</i></a></span>
					</p>
					<div class="button2">
						<a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
					</div>
				</div>
			</div>
</div>
	</form:form>
</div>


<span class="error-message">${errorMessage }</span>





