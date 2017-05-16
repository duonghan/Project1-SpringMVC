<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="page-title">Đăng nhập (For Employee, Manager)</div>

<div class="login-container" valign="top">

	<h3>Nhập tài khoản và mật khẩu</h3>
	<br>
	<!-- /login?error=true -->
	<c:if test="${param.error == 'true'}">
		<div style="color: red; margin: 10px 0px;">

			Login Failed!!!<br /> Reason :
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}

		</div>
	</c:if>

	<form method="POST"
		action="${pageContext.request.contextPath}/j_spring_security_check">
		<table>
			<tr>
				<td>Tài khoản *</td>
				<td><input name="userName" /></td>
			</tr>

			<tr>
				<td>Mật khẩu *</td>
				<td><input type="password" name="password" /></td>
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="Login" /> <input type="reset"
					value="Reset" /></td>
			</tr>
		</table>
	</form>

	<span>${error }</span>

</div>
