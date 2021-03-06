<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="en_US" scope="session" />

<div class="page-title">Danh sách đơn hàng</div>

<div>Tổng đơn hàng: ${paginationResult.totalRecords}</div>

<table border="1" style="width: 100%">
	<tr>
		<th>Mã đơn hàng</th>
		<th>Ngày tháng</th>
		<th>Tên khách hàng</th>
		<th>Địa chỉ</th>
		<th>Email</th>
		<th>Giá trị</th>
		<th>Xem chi tiết</th>
	</tr>
	<c:forEach items="${paginationResult.list}" var="orderInfo">
		<tr>
			<td>${orderInfo.orderNum}</td>
			<td><fmt:formatDate value="${orderInfo.created}"
					pattern="dd-MM-yyyy HH:mm" /></td>
			<td>${orderInfo.customerName}</td>
			<td>${orderInfo.customerAddress}</td>
			<td>${orderInfo.customerEmail}</td>
			<td style="color: red;"><fmt:formatNumber
					value="${orderInfo.amount}" type="number" 
					pattern="###,###,### VNĐ" maxFractionDigits="0"/>
			</td>
			
			<td><a
				href="${pageContext.request.contextPath}/order?id=${orderInfo.id}">
					Xem chi tiết</a></td>
		</tr>
	</c:forEach>
</table>

<c:if test="${paginationResult.totalPages > 1}">
	<div>
		<c:forEach items="${paginationResult.navigationPages}" var="page">
			<c:if test="${page != -1 }">
				<a href="${pageContext.request.contextPath}/order/list?page=${page}" class="nav-item">${page}</a>
			</c:if>
			<c:if test="${page == -1 }">
				<span class="nav-item"> ... </span>
			</c:if>
		</c:forEach>
	</div>
</c:if>
