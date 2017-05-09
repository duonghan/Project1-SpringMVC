<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="en_US" scope="session" />

<div>Thông tin hóa đơn</div>

<div>
	<h3>Thông tin khách hàng:</h3>
	<ul>
		<li>Họ tên: ${orderInfo.customerName}</li>
		<li>Email: ${orderInfo.customerEmail}</li>
		<li>Số điện thoại: ${orderInfo.customerPhone}</li>
		<li>Địa chỉ: ${orderInfo.customerAddress}</li>
	</ul>
	<h3>Tổng giá trị đơn hàng:</h3>
	<ul>
		<li>Tổng: <span> <fmt:formatNumber
					value="${orderInfo.amount}" type="currency" />
		</span></li>
	</ul>
</div>

<br />

<table border="1" style="width: 100%">
	<tr>
		<th>Mã sản phẩm</th>
		<th>Tên sản phẩm</th>
		<th>Số lượng</th>
		<th>Đơn giá</th>
		<th>Tổng tiền</th>
	</tr>
	<c:forEach items="${orderInfo.details}" var="orderDetailInfo">
		<tr>
			<td>${orderDetailInfo.productid}</td>
			<td>${orderDetailInfo.productname}</td>
			<td>${orderDetailInfo.quanity}</td>
			<td><fmt:formatNumber value="${orderDetailInfo.price}"
					type="currency" /></td>
			<td><fmt:formatNumber value="${orderDetailInfo.amount}"
					type="currency" /></td>
		</tr>
	</c:forEach>
</table>
<c:if test="${paginationResult.totalPages > 1}">
	<div>
		<c:forEach items="${paginationResult.navigationPages}" var="page">
			<c:if test="${page != -1 }">
				<a href="orderList?page=${page}">${page}</a>
			</c:if>
			<c:if test="${page == -1 }">
				<span> ... </span>
			</c:if>
		</c:forEach>

	</div>
</c:if>
