<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    
 
 
 <form:form method="POST" modelAttribute="cartForm"
 action="${pageContext.request.contextPath}/shopping-cart/other-info">
 	<table>
 		<tr>
	 		<td>Thông tin thêm</td>
	 		<td><form:input path="description" /></td>
 		</tr>
 		
 		<tr>
 			<td>
 				<input type="submit" value="Tiếp tục" />
 				<a href="javascript:history.go(-1)">
 					<button>Trở về</button>
 				</a>
 				
 			</td>
 		</tr>
 	</table>
 
 
 </form:form>  