<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<form:form modelAttribute="cartLine">
<div class="Cright">
	<div class="CRP0">
		<div class="CRP1">
			<img
				src="${pageContext.request.contextPath}/productImage?id=${cartLine.productInfo.id}"
				alt="${cartLine.productInfo.name}">
		</div>
		<div class="CRP2">
			<p class="nameP">
				<strong>${cartLine.productInfo.name }</strong>
			</p>
			<!--  -->
			<hr />
			<div class="sanpham">
				<p>
					<strong>Tên sản phẩm:</strong> ${cartLine.productInfo.name }
				</p>
				<p>
					<strong>Mã sản phẩm:</strong> ${cartLine.productInfo.id }
				</p>
				<p>
					<strong>Thương hiệu:</strong> Nike
				</p>
				<p>
					<strong>Tình trạng kho:</strong> Còn hàng
				</p>
				<p>
					<strong>Giá:</strong><span style="color: #ff0000"> <fmt:formatNumber
							value="${cartLine.productInfo.price} " type="number"
							pattern="###,###,### VNĐ" maxFractionDigits="0" /></span>
				</p>
				<a><strong>Chọn size:</strong> <span style="color: #ff0000;">*</span></a>
				<div class="form-inputP">
					<form:select path="size" class="gt">
						<form:option value="" label="- Chọn một -" />
						<form:options items="${sizeForm}" />
					</form:select>
				</div>
				<a><strong>Số lượng:</strong></a> <span class="size2"><input
					type="number" name="" step="1" min="0" max="100"
					style="width: 100px; height: 27px; border: 2px solid #ccc;" /></span> <span><div
						class="buttonP">
						<a href="${pageContext.request.contextPath}/buyProduct?id=${cartLine.productInfo.id}">Mua hàng</a>
					</div> </span>
			</div>
		</div>
	</div>
	<div class="CRP5">
		<div class="title">
			<p>
				<strong> CHI TIẾT</strong>
			</p>
		</div>
		<p>
			<strong><i>Giày Nike Air Zoom Vomero 11</i></strong> là một trong
			những đôi giày chạy có tính năng êm ái và thoải mái nhất của Nike.
			Đôi giày được thiết kế với chất liệu sang hơn phiên bản tiền thân
			trước đó, trong khi vẫn giữ nguyên sự phối hợp thần kì của phần đệm
			giày mềm mại và tốc độ đáp ứng nhiệt tình. <strong><i>Giày
					Nike Air Zoom Vomero 11</i></strong> chắc chắn là một đôi giày chạy bộ lý tưởng
			để chọn lựa. <br> <br> <strong><i>Giày Nike
					Air Zoom Vomero 11</i></strong> chính hãng tại Myshoes.vn được nhập khẩu trực
			tiếp tại Mỹ qua đường hàng không. Full box, đầy đủ phụ kiện.
		</p>
		<img src="images/01.1.jpg"> <img src="images/01.2.jpg">
	</div>
</div>
</form:form>