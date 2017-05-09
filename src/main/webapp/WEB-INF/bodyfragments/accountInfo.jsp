<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="page-title">Account Info</div>

<div class="account-container">
	<form:form method="POST" modelAttribute="customerForm"
		action="${pageContext.request.contextPath}/shoppingCartCustomer">

		<h1>Thông tin tài khoản ${pageContext.request.userPrincipal.name}</h1>


		<ul>
			<li>Họ Tên: ${customerInfo.getName}</li>
			<li>Role:
				<ul>
					<c:forEach items="${userDetails.authorities}" var="auth">
						<li>${auth.authority }</li>
					</c:forEach>
				</ul>
			</li>
		</ul>
		
		</form:form>
</div>
