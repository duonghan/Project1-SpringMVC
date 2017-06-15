<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form method="POST"
		action="${pageContext.request.contextPath}/j_spring_security_check">
	<div class="Cright">
		<br> <a style="font-size: 25px; color: #ff0000; padding: 100px;"><strong>ĐĂNG
				NHẬP TÀI KHOẢN</strong></a>
		<hr>
		<div class="CRL0">
			<div class="CRL1">
				<br> <br> <a class="loginL"><strong>LOGIN</strong></a> <br>
				<br>
					<!-- /login?error=true -->
					<c:if test="${param.error == 'true'}">
						<div style="color: red; margin: 10px 0px;">
				
							Tài khoản hoặc mật khẩu không chính xác, vui lòng thử lại!!!<br /> 
							<!-- Reason :
							${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} -->
				
						</div>
					</c:if>
					
					
					<a class="nameL"><strong>Tài khoản:</strong></a>
					<div class="form-inputL">
						<input type="text" name="userName" placeholder="Tài khoản">
					</div>
					<a class="nameL"><strong>Mật khẩu:</strong></a>
					<div class="form-inputL">
						<input type="password" name="password" placeholder="********">
					</div>
				
				<br>
				<!--thêm1trangnữanhé-->
				<!-- <a class="CRL11" href="Quenmatkhau.html" title="">Quên mật khẩu?</a> -->
				<div>
				<input class="button1L" type="submit" value="Đăng nhập" />
				</div>
				<!--trang_account.html-->
			</div>
			<div class="CRL3">
				<br> <br> <br> <a
					style="margin-left: 30px; font-size: 20px; color: #bf0000;"><strong><i>Bạn
							chưa có tài khoản?</i></strong></a> <br> <br>
				<p>
					Bằng cách tạo một tài khoản, bạn có thể mua hàng nhanh hơn, được
					cập nhật các sản phẩm mới liên tục và nhiều ưu đãi khác tại<span><a
						class="CRL11" href="#"><i> My shoe shop.</i></a></span>
				</p>
				<div class="button2L">
					<a href="${pageContext.request.contextPath}/signup">Đăng ký</a>
				</div>
			</div>
		</div>
		<hr>
		<br> <br>
	</div>
</form>



